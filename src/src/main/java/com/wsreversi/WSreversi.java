package com.wsreversi;
import com.wsreversi.*;

//Reversi
import com.reversi.*;

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
	//Map: Session->userId de cola. Aún no han iniciado una partida --TEMPORAL
	private static HashMap<String, String> idUsers; 

	//userId -- Mantengo este listado para que un mismo usuario no inicie mas de una partida
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
	 * Recibe los mensajes del FrontEnd y los analiza para luego procesarlos como corresponda
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
			//System.out.println("Error al procesar el JSON:" + e.getMessage());
			msgError(session, "mx0", "JSON no válido");
			return;
		}

		//:TEST
		System.out.println("Operacion: " + jMessage.getProperty("operacion").toUpperCase());

		//Proceso las opciones
		switch(jMessage.getProperty("operacion").toUpperCase()){
			case "INIT": //{id,nivel}
				
				String userId = jMessage.getProperty("id");
				String nivel  = jMessage.getProperty("nivel");
				
				if (userId == null || nivel == null){
					msgError(session,"mc0","Información incompleta");
				}else doConnect(userId, nivel.toLowerCase(), session);
				
			break;
			
			case "MOVE":
				
				String posX = jMessage.getProperty("posx");
				String posY  = jMessage.getProperty("posy");
				
				if (posX == null || posY == null){
					msgError(session,"mm0","Información incompleta");
				}else doMove(session, posX, posY);
				
			break;
			
			case "QUIT":
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
	public void actualizar(MotivoActualizar motivo, String partidaId)
	throws IOException, InterruptedException {				
		/**
		 * 
		 * Escenarios: 
		 * 		- Cambio de turno por timeout: notifico a las interfases
		 * 		- No hay mas movimientos: notifico las interfases y envio los RESULTADOS
		 * 		- Partida Cancelada: Notifico a las interfases y envio RESULTADOS 
		 * 
		 * RESULTADOS: aka scores de los usuarios
		 * */	
		 
		Juego juego = indicePartida.get(partidaId);
		boolean clean = false;
		
		System.out.println(motivo);
				 
		switch (motivo){
			case TIMEOUT:
				//El jugador que tenía el turno lo perdio. Debo avisar a ambos
				Session turnoAcutal;
				Session turnoAtenrior;
				
				if (juego.partida.jugadorActual() == juego.partida.whoIsBlancas()){
					turnoAcutal = juego.blancas;
					turnoAtenrior = juego.negras;				
				}else{
					turnoAcutal = juego.negras;
					turnoAtenrior = juego.blancas;
				}
				
				RespuestaWS rActual = new RespuestaWS("TIMEOUT");
				RespuestaWS rAnterior = new RespuestaWS("TIMEOUT");
									
				rActual.addAttr("mensaje", "Tu contrincante perdió su turno. Ahora debes jugar tu.");
				rAnterior.addAttr("mensaje", "Epa! se te ha pasado el tiempo");
						
				rActual.addAttr("turno", true);
				rAnterior.addAttr("turno", false);

				turnoAcutal.getBasicRemote().sendText(rActual.toString());						 
				turnoAtenrior.getBasicRemote().sendText(rAnterior.toString());					
				
			break;
			
			case CANCELADO: //Unifico los casos
			case END:
				//Obtengo el Juego
				juego = indicePartida.get(partidaId);

				//Defino los objetos Respuesta
				RespuestaWS jBlancas;
				RespuestaWS jNegras;				
			
				if (motivo == MotivoActualizar.CANCELADO){
					//La partida se cancelo porque los jugadores no estaba jugando.
					jBlancas = new RespuestaWS("CANCEL");
					jNegras = new RespuestaWS("CANCEL");				
				}else{
					//La partida finalizó porque no hay movimientos dispobibles
					jBlancas = new RespuestaWS("END");
					jNegras = new RespuestaWS("END");
				}
													
				jBlancas.addAttr("data", juego.partida.scoring(juego.partida.whoIsBlancas()));
				jNegras.addAttr("data",  juego.partida.scoring(juego.partida.whoIsNegras()));

				juego.blancas.getBasicRemote().sendText(jBlancas.toString());						 
				juego.negras.getBasicRemote().sendText(jNegras.toString());
				
				clean = true;				
			break;
		}
		
		if (clean) cleanSessions(
			juego.partida.whoIsBlancas(), //Id
			juego.partida.whoIsNegras(),  //Id
			juego.blancas, //Session
			juego.negras,  //Session
			juego.partida.getId() 
		);
		 
	}
	
	private void msgError(Session session, String id, String mensaje)
	throws IOException, InterruptedException {
		RespuestaWS j = new RespuestaWS("ERROR");
		j.addAttr("data", "(" + id + ") " + mensaje);
				
		session.getBasicRemote().sendText(j.toString());		
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
		procesarCola(cola, nivelInt, session);
	}
	
	private void procesarCola(Queue cola, int nivelInt, Session session)
	throws IOException, InterruptedException {
		//reviso las colas. Si algujna tiene 2 o más elementos, los quito y les inicio la partida
		
		/**
		 * Verifico que los jugadores aún siguen conectados.
		 * Si bien al momento de recibir un onClose realizo una limpieza
		 * no me combiene tocar la cola porque solo se puede acceder al elemto
		 * de la cabeza. Por esta razón puede contener elementos residuales y debo
		 * verificar la validez de las sesiones.
		 * */
		boolean ok = true;
		Session s;

		do{
			s = (Session)cola.peek();
			ok = s.isOpen();
			if (!ok) cola.remove();
			
		}while (!ok && cola.size() > 0);
		
		
		//Continuo con el proceso normal
		
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
	
			//Aviso a los jugadores que inicio la partida
			RespuestaWS rBlanco = new RespuestaWS("INIT");
			RespuestaWS rNegro = new RespuestaWS("INIT");
								
			rBlanco.addAttr("mensaje", "Juego Iniciado! Espera a que tu compañero juegue");
			rBlanco.addAttr("turno", false);
			
			rNegro.addAttr("mensaje", "Juego Iniciado! Hace tu movida");
			rNegro.addAttr("turno", true);
						
			sessionBlanco.getBasicRemote().sendText(rBlanco.toString());
			sessionNegro.getBasicRemote().sendText(rNegro.toString());
			
		}else{
			//Esperando que otro jugador se conecte
			RespuestaWS espera = new RespuestaWS("MSG");
			espera.addAttr("data", "Ya estas online, espera que se conecte otro jugador");
			
			session.getBasicRemote().sendText(espera.toString());
		}
	}


	private void doMove(Session session, String posX, String posY)
	throws IOException, InterruptedException {		
		/**
		 * Dada una session, obtengo la partida y el Id del jugador
		 * me fijo que el jugador actual es el que intenta hacer el movimiento o envio mensaje de error
		 * 
		 * Hago el movimiento. Envio resultado y notifico al contrincante
		 */
		 
		 String error = "";
		 
		 if (posX.matches("0|1|2|3|4|5|6|7") && posY.matches("0|1|2|3|4|5|6|7")){
			Jugador jugador = indiceSesiones.get(session.getId());
			
			if (jugador.partida.jugadorActual() == jugador.userId){
				
				ResultadoMovimiento resultado = null;
				
				try{
					resultado = jugador.partida.mover(
						new Ficha(Integer.parseInt(posX), Integer.parseInt(posY)),
						jugador.userId
					);
				}catch(Exception e){
					System.out.println(e);
				}
				
				if (resultado != null){
					//Enviar actualizaciones de tablero - Notificar que tiene el turno

					RespuestaWS rJ = new RespuestaWS("MOVER");
					RespuestaWS rC = new RespuestaWS("MOVER");
										
					rJ.addAttr("turno", jugador.partida.jugadorActual() == jugador.userId);
					rC.addAttr("turno", jugador.partida.jugadorActual() != jugador.userId);

					rJ.addAttr("hecho", true);
					rC.addAttr("hecho", true);

					rJ.addAttr("data", resultado);
					rC.addAttr("data", resultado);
				
					System.out.println(rJ.toString());
					System.out.println(rC.toString());
							
					session.getBasicRemote().sendText(rJ.toString());						 
					jugador.pear.getBasicRemote().sendText(rC.toString());					
						 
				}else error = "No se puede realizar este movimiento";
				
			}else error = "No es tuturno!";
			
		 }else error = "Posición no válida";
		 
		 if (error != ""){
			RespuestaWS rtaError = new RespuestaWS("MOVER");
								
			rtaError.addAttr("hecho", false);
			rtaError.addAttr("data", error);
			rtaError.addAttr("ficha", new Ficha(Integer.parseInt(posX),Integer.parseInt(posY)));
					
			session.getBasicRemote().sendText(rtaError.toString());						 
		 }
	}
	
	private void doQuit(Session session)
	throws IOException, InterruptedException {			
		/**
		* busco la partida. la finalizo. envio los datos de finalizacion al contrincante
		* elimino todas las participaciones en los índices.
		* cierro las conexiones
		* Nota: idem onClose()
		*/		

		if (idUsers.containsKey(session.getId())){
			//Aún no inicio la partida -- Jugadores Temporales
			String userId = idUsers.get(session.getId());
			jugadores.remove(userId);
			idUsers.remove(session.getId());
		}else{
		 
			//Obtengo los jugadores
			
				Jugador jugador = indiceSesiones.get(session.getId());
				Jugador contrincante = indiceSesiones.get(jugador.pear.getId()); //para obtener el Id del Contrincante
				
			//Termino la partida
			
				jugador.partida.finalizar(jugador.userId);
				 
				UserScoring resJugador = jugador.partida.scoring(jugador.userId);
				UserScoring resContrincante = jugador.partida.scoring(contrincante.userId);
						
				RespuestaWS rJ = new RespuestaWS("QUIT");
				RespuestaWS rC = new RespuestaWS("QUIT");
									
				rJ.addAttr("data", resJugador);
				rC.addAttr("data", resContrincante);
			
				System.out.println("Se envian los mensajes");
			
				try{	
					System.out.println("Jugador");
					session.getBasicRemote().sendText(rJ.toString());						 
				}catch(Exception e){System.out.println(e);}
				
				try{
					System.out.println("Contrincante");
					jugador.pear.getBasicRemote().sendText(rC.toString());					
				}catch(Exception e){System.out.println(e);}
			
			System.out.println("Se esta por iniciar la limpieza");
			
			cleanSessions(
				jugador.userId,
				contrincante.userId,
				
				session,
				jugador.pear,
				
				jugador.partida.getId()
			);
		}
	}
	
	private void cleanSessions(String j1Id, String j2Id, Session s1, Session s2, String pId)
	throws IOException, InterruptedException {
		jugadores.remove(j1Id);
		jugadores.remove(j2Id);
		 
		indiceSesiones.remove(s1.getId());
		indiceSesiones.remove(s2.getId());
		 
		indicePartida.remove(pId);
		 
		try{
			s2.close();
		}catch(Exception e){}			
		
		try{
			s1.close();
		}catch(Exception e){}
		 
		System.out.println("Ya no quedan muertitos en el placard!");
	}
}

