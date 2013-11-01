package com.wsreversi;
import com.wsreversi.Juego;
import com.reversi.Partida;
import com.reversi.ReversiObserver;

//Dependencias para el servidor
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.Queue;
import java.util.Properties;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashSet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

//Para realizar las comunicaciones con el browser
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * Para acceder a los juegos cuando el websocket recibe un mensaje, los busca mediante el ID de la 
 * sesssion que origino el evento en el hashmap de juegos
 */

@ServerEndpoint("/wsreversi")
public class WSreversi extends ReversiObserver
{
	//Tengo que hacer un pearing de Juegos para saber quien es el compañero de cada jugador y su partida
	private static HashMap<String, Juego> indiceSesiones; //Id Session, Juego
	private static HashMap<String, Juego> indicePartida; //Id Partida, Juego
	
	//Colas de usuarios por niveles de dificultad
	private static Queue<Session> niveFacil;
	private static Queue<Session> niveMedio;
	private static Queue<Session> niveDificil;
	
	//<SessionID, userId>
	private static HashMap<String, String> pendientes; //Conexiones establecidas que no han iniciado una partida
	//userId
	private static Set<String> jugadores;
	
	public WSreversi(){
		System.out.println("\n\n\n\n WSreversi");
		if (indiceSesiones == null) indiceSesiones = new HashMap<String, Juego>();
		if (indicePartida == null) indicePartida = new HashMap<String, Juego>();
		
		if (niveFacil == null) niveFacil = new ConcurrentLinkedQueue<Session>();
		if (niveMedio == null) niveMedio = new ConcurrentLinkedQueue<Session>();
		if (niveDificil == null) niveDificil = new ConcurrentLinkedQueue<Session>();
		
		if (pendientes == null) pendientes = new HashMap<String, String>();
		if (jugadores == null) jugadores = new HashSet<String>();
	}
	
	/**
	 * Guardo la session hasta que se inicie la partida
	 */
	@OnOpen
	public void onOpen(Session session) {
		/* El websocket no recibe parametros cuando se inicia la conexion,
		 * por esta razón no hago nada hasta que se recibe el mensaje con la 
		 * operacion "init". Ese es el punto de partida para los procesos.
		 */
		 
		 System.out.println("Conexion: " + session.getId());
	}	

	/**
	 */
	@OnMessage
	public void onMessage(String message, Session session) {		

		//:TEST
		System.out.println("\n\n\n\nMensaje: " + message);
		
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
				//Agregrego el usuario a la cola y actualizo en pendientes
				String userId = jMessage.getProperty("id");
				String nivel  = jMessage.getProperty("nivel");
				
				doConnect(userId, nivel.toLowerCase(), session);
			break;
			
			case "move":
				
			break;
			
			case "quit":
				
			break;
			
			default:
				msgError(session, "mx1", "Operación no válida");
			break;
		}
	}
	
	/**
	 */
	@OnClose
	public void onClose(Session session) {
		// TODO : to implement	
		/*
		 * Si la session @Pendientes, la elimino. Si la session esta inciada pero sin pareja, la elimino de las colas
		 * Si la session !@Pendientes, busco el ID de la partida. Cierro la partida y elimino las referencias de los índices.
		 */
	}
	
	//implements abstract
	public void actualizar(){
		// TODO : to implement	
	}
	
	private void msgError(Session session, String id, String mensaje){
		//TODO : Enviar mensaje de error
		//:TEST
		System.out.println("\nmsgError: id: "+id+" | mensaje: "+mensaje);		
	}


	private void encolar(Queue cola, int nivelInt, Session session){
		cola.add(session);
		procesarCola(cola, nivelInt);
	}
	
	private void procesarCola(Queue cola, int nivelInt){
		//reviso las colas. Si algujna tiene 2 o más elementos, los quito y les inicio la partida
		
		if (cola.size() > 1){ //Proceso la cola solo si tiene dos o mas elementos
			Session sessionBlanco = (Session)cola.poll();
			Session sessionNegro = (Session)cola.poll();
		
			Juego juego = new Juego(
				new Partida( //(String idNegro, String idBlanco, int dificultRec, ReversiObserver observer){
					pendientes.get(sessionNegro.getId()), //Obtengo el idUsurio
					pendientes.get(sessionBlanco.getId()), //Obtengo el idUsurio
					nivelInt, //Nivel de juego
					this //observer!
				),
				sessionBlanco, 
				sessionNegro				
			);
		
			System.out.println("indiceSesiones: "+indiceSesiones.size()+ " | indicePartida: " + indicePartida.size());
			
			indiceSesiones.put(sessionBlanco.getId(), juego);
			indiceSesiones.put(sessionNegro.getId(),  juego);
			indicePartida.put (juego.partida.getId(), juego);
			
			System.out.println("indiceSesiones: "+indiceSesiones.size()+ " | indicePartida: " + indicePartida.size());
			System.out.println("idNegro: " + pendientes.get(sessionNegro.getId()) + " | idBlanco: " + pendientes.get(sessionBlanco.getId()) +" | Nivel:" + nivelInt + " | partidaID" + juego.partida.getId()); 
			
			pendientes.remove(sessionNegro.getId());
			pendientes.remove(sessionBlanco.getId());
			
			System.out.println("Pendintes Size: " + pendientes.size());
		}
	}
	
	private void doConnect(String userId, String nivel, Session session){
		//:TEST
		System.out.println("\ndoConnect: userId: "+ userId + " | nivel: " + nivel + " | session: "+session.getId());
		
		if (!nivel.matches("facil|medio|dificil")){
			//TODO: mensaje de error
			msgError(session,"mc1","Nivel no válido");
			return;
		}

		if (!jugadores.contains(userId)){
			jugadores.add(userId);
			
			int nivelInt;
			Queue cola;
			
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
				break;
			}	
			
			pendientes.put(session.getId(), userId);			
			encolar(cola, nivelInt, session);
								
		}else{
			//El jugador tiene una partida iniciada
			msgError(session, "mc2", "No se puede iniciar una partida para este jugador");
		}
	
	}//end doConnect()
	
	private void doMove(){
	}
	
	private void doQuit(){
	}
}

