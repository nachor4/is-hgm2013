package com.wsreversi;
import com.wsreversi.Juego;
import com.wsreversi.Jugador;

//Reversi
import com.reversi.Partida;
import com.reversi.Ficha;
import com.reversi.ReversiObserver;
import com.reversi.ResultadoMovimiento;

//Librerías Java
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.Queue;
import java.util.Properties;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashSet;

//WebSocket
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

//GSON: Librería JSON (comunicacion con los websocket clients)
import com.google.gson.Gson;
/*
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
*/

/**
 * Para acceder a los juegos cuando el websocket recibe un mensaje, los busca mediante el ID de la 
 * sesssion que origino el evento en el hashmap de juegos
 */

@ServerEndpoint("/wsreversi")
public class WSreversi extends ReversiObserver
{
	//Tengo que hacer un pearing de Juegos para saber quien es el compañero de cada jugador y su partida
	private static HashMap<String, Jugador> indiceSesiones; //Id Session, Juego
	private static HashMap<String, Juego> indicePartida; //Id Partida, Juego
	
	//Colas de usuarios por niveles de dificultad
	private static Queue<Session> niveFacil;
	private static Queue<Session> niveMedio;
	private static Queue<Session> niveDificil;
	
	//<SessionID, userId> 
	
	private static HashMap<String, String> idUsers; //Map: Session->userId de cola. Aún no han iniciado una partida --TEMPORAL
	//userId
	private static Set<String> jugadores;
	
	public WSreversi(){
		if (indiceSesiones == null) indiceSesiones = new HashMap<String, Jugador>();
		if (indicePartida == null) indicePartida = new HashMap<String, Juego>();
		
		if (niveFacil == null) niveFacil = new ConcurrentLinkedQueue<Session>();
		if (niveMedio == null) niveMedio = new ConcurrentLinkedQueue<Session>();
		if (niveDificil == null) niveDificil = new ConcurrentLinkedQueue<Session>();
		
		if (idUsers == null) idUsers = new HashMap<String, String>();
		if (jugadores == null) jugadores = new HashSet<String>();
	}
	
	/**
	 */
	@OnOpen
	public void onOpen(Session session) {
		/* 
		 * El websocket no recibe parametros cuando se inicia la conexion,
		 * por esta razón no hago nada hasta que se recibe el mensaje con la 
		 * operacion "init". Ese es el punto de partida para los procesos.
		 */
		 
		 System.out.println("Conexion: " + session.getId());
	}	

	/**
	 * 
	 */
	@OnMessage
	public void onMessage(String message, Session session)
	throws IOException, InterruptedException {
		
		//:TEST
		System.out.println("\n\nMensaje: " + message);
		
		//Preparo la información para procesarla
		Gson gson = new Gson();
		Properties jMessage;
		
		try{
			jMessage = gson.fromJson(message, Properties.class);
		}catch (Exception e){ //envio mensaje error -- No se pudo parsear el json
			System.out.println("Error al procesar el JSON:" + e.getMessage());
			msgError(session, "mx0", "JSON no válido");
			return;
		}

		//:TEST
		System.out.println("Operacion: " + jMessage.getProperty("operacion").toLowerCase());
	   
		//Proceso las opciones
		switch(jMessage.getProperty("operacion").toLowerCase()){
			case "init": //{id,nivel}
				
				String userId = jMessage.getProperty("id");
				String nivel  = jMessage.getProperty("nivel");
				
				if (userId == null || nivel == null){
					msgError(session,"mc0","Información incompleta");
				}else doConnect(userId, nivel.toLowerCase(), session);
				
			break;
			
			case "move":
				
				String posX = jMessage.getProperty("posx");
				String posY  = jMessage.getProperty("posy");
				
				if (posX == null || posY == null){
					msgError(session,"mm0","Información incompleta");
				}else doMove(session, posX, posY);
				
			break;
			
			case "quit":
				doQuit(session);
			break;
			
			default:
				msgError(session, "mx1", "Operación no válida");
			break;
		}
	}
	
	/**
	 */
	@OnClose
	public void onClose(Session session)
	throws IOException, InterruptedException {				
		doQuit(session);
	}
	
	//implements abstract
	public void actualizar(){
		/**
		 * TODO:
		 * 
		 * Escenarios: 
		 * 		- Cambio de turno por timeout: notifico a las interfases
		 * 		- No hay mas movimientos: notifico las interfases y envio los RESULTADOS
		 * 		- Partida Cancelada: Notifico a las interfases y envio RESULTADOS 
		 * 
		 * RESULTADOS: aka scores de los usuarios
		 * */	
	}
	
	private void msgError(Session session, String id, String mensaje)
	throws IOException, InterruptedException {
		//TODO : Enviar mensaje de error
		//:TEST
		System.out.println("\nmsgError: id: "+id+" | mensaje: "+mensaje);		
		
		session.getBasicRemote().sendText("{\"status\":\"error\",\"data\":\"("+id+") "+mensaje+"\"}");		
	}


	private void doConnect(String userId, String nivel, Session session)
	throws IOException, InterruptedException {

		if (!jugadores.contains(userId)){
			int nivelInt;
			Queue cola;
			Boolean ok = true;
			
			//Agrego los usuarios a las colas de nivel
			switch (nivel){
				case "facil": 
					nivelInt = 0;
					cola = niveFacil;
				break;
				
				case "medio": 
					nivelInt = 1;
					cola = niveMedio;
				break;
				
				case "dificil": 
					nivelInt = 2;
					cola = niveDificil;
				break;
				
				default:
					nivelInt = -1;
					cola = null;
					ok = false;
				break;
			}	
			
			if (ok){
				jugadores.add(userId);
				idUsers.put(session.getId(), userId);			
				encolar(cola, nivelInt, session);
			}else msgError(session,"mc1","Nivel no válido");

								
		}else{
			//El jugador tiene una partida iniciada
			msgError(session, "mc2", "No se puede iniciar una partida para este jugador");
		}
	
	}//end doConnect()

	private void encolar(Queue cola, int nivelInt, Session session)
	throws IOException, InterruptedException {
		cola.add(session);
		procesarCola(cola, nivelInt);
	}
	
	private void procesarCola(Queue cola, int nivelInt)
	throws IOException, InterruptedException {
		//reviso las colas. Si algujna tiene 2 o más elementos, los quito y les inicio la partida
		
		if (cola.size() > 1){ //Proceso la cola solo si tiene dos o mas elementos
			Session sessionNegro = (Session)cola.poll();
			Session sessionBlanco = (Session)cola.poll();
		
			Juego juego = new Juego(
				new Partida( //(String idNegro, String idBlanco, int dificultRec, ReversiObserver observer){
					idUsers.get(sessionNegro.getId()), //Obtengo el idUsurio
					idUsers.get(sessionBlanco.getId()), //Obtengo el idUsurio
					nivelInt, //Nivel de juego
					this //observer!
				),
				sessionBlanco, 
				sessionNegro				
			);
					
			indicePartida.put (juego.partida.getId(), juego);

			indiceSesiones.put(sessionBlanco.getId(), new Jugador(idUsers.get(sessionBlanco.getId()), juego.partida, sessionNegro));
			indiceSesiones.put(sessionNegro.getId(),  new Jugador(idUsers.get(sessionNegro.getId()),  juego.partida, sessionBlanco));
						
			idUsers.remove(sessionNegro.getId());
			idUsers.remove(sessionBlanco.getId());
	
			//TODO: definir
			sessionBlanco.getBasicRemote().sendText("Juego Iniciado! Espera a que tu compañero juegue");
			sessionNegro.getBasicRemote().sendText("Juego Iniciado! Hace tu movida");
		}
	}


	private void doMove(Session session, String posX, String posY)
	throws IOException, InterruptedException {		
		/**
		 * TODO:
		 * Dada una session, obtengo la partida y el Id del jugador
		 * me fijo que el jugador actual es el que intenta hacer el movimiento o envio mensaje de error
		 * 
		 * Hago el movimiento. Envio resultado y notifico al contrincante
		 */
		 
		 if (posX.matches("0|1|2|3|4|5|6|7") && posY.matches("0|1|2|3|4|5|6|7")){
			Jugador jugador = indiceSesiones.get(session.getId());
			
			if (jugador.partida.jugadorActual() == jugador.userId){
				
				ResultadoMovimiento resultado = jugador.partida.mover(
					new Ficha(Integer.parseInt(posX),Integer.parseInt(posY)),
					jugador.userId
				);
				
				if (resultado != null){
					//TODO :
					/**
					 * Enviar actualizaciones de tablero.
					 * Notificar que tiene el turno
					 * 
					 * 
					 */
					 
				}else msgError(session,"dm3","No se puede realizar este movimiento");
				
			}else msgError(session,"dm2","No es tuturno!");
			
		 }else msgError(session,"dm1","Posición no válida");
		 
	}
	
	private void doQuit(Session session)
	throws IOException, InterruptedException {			
		/**
		 * TODO:
		 * busco la partida. la finalizo. envio los datos de finalizacion al contrincante
		 *  
		 * elimino todas las participaciones en los índices.
		 * 
		 * cierro las conexiones
		 * 
		 * Nota: idem onClose()
		 */		
		 
		 //Obtengo los jugadores
		 Jugador jugador = indiceSesiones.get(session.getId());
		 Jugador contrincante = indiceSesiones.get(jugador.pear.getId());
		 
		 //Termino la partida
		 //jugador.partida.finalizar(jugador.userId);
		 
		 //jugador.partida.scoring(jugador.idUsers);
		 //jugador.partida.scoring(contrincate.idUsers);
		 
		 
		 jugadores.remove(jugador.userId);
		 jugadores.remove(contrincante.userId);
		 
		 indiceSesiones.remove(session.getId());
		 indiceSesiones.remove(jugador.pear.getId());
		 
		 indicePartida.remove(jugador.partida.getId());
		 
		 jugador.pear.close();
		 session.close();
		 
		 //TODO : Envair Resultados 
	}
}

