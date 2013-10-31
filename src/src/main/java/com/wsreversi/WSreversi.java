package com.wsreversi;
import com.wsreversi.Juego;
import com.reversi.Partida;
import com.reversi.ReversiObserver;

//Dependencias para el servidor
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;
import java.util.Properties;


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
 * Para acceder a los juegos cuando el websocket recibe un mensaje, los busca mediante el ID de la sesssion que origino el evento en el hashmap de juegos
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

@ServerEndpoint("/wsreversi")
public class WSreversi extends ReversiObserver
{
	private HashMap<String, Juego> indiceSesiones; //Id Session, Juego
	private HashMap<String, Juego> indicePartida; //Id Partida, Juego
	
	//Colas de usuarios por niveles de dificultad
	private Queue<Session> niveFacil;
	private Queue<Session> niveMedio;
	private Queue<Session> niveDificil;
	
	//<SessionID, userId>
	private HashMap<String, String> pendientes; //Conexiones establecidas que no han iniciado una partida
	
	public WSreversi(){}
	
	/**
	 * <!-- begin-user-doc -->
	 * Guardo la session hasta que se inicie la partida
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@OnOpen
	public void onOpen(Session session) {
		/* El websocket no recibe parametros cuando se inicia la conexion,
		 * por esta razón no hago nada hasta que se recibe el mensaje con la 
		 * operacion "init". Ese es el punto de partida para los procesos.
		 */
	}	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@OnMessage
	public void onMessage(String message, Session session) {		
		
		//Preparo la información para procesarla
		
		Gson gson = new Gson();
		Properties jMessage;
		
		try{
			jMessage = gson.fromJson(message, Properties.class);
		}catch (Exception e){ //envio mensaje error -- No se pudo parsear el json
			System.out.println(e.getMessage());
			this.msgError(session, "mx0", "JSON no vá");
			return;
		}
	   
		//Proceso las opciones
		switch(jMessage.getProperty("operacion").toLowerCase()){
			case "init": //{id,nivel}
				//Agregrego el usuario a la cola y actualizo en pendientes
				String userId = jMessage.getProperty("id");
				String nivel  = jMessage.getProperty("nivel");
				
				this.doConnect(userId, nivel.toLowerCase(), session);
			break;
			
			case "move":
				
			break;
			
			case "quit":
				
			break;
			
			default:
				this.msgError(session, "mx1", "Operación no válida");
			break;
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
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
	}


	private void encolar(Queue cola, int nivelInt, Session session){
		cola.add(session);
		this.procesarCola(cola, nivelInt);
	}
	
	private void procesarCola(Queue cola, int nivelInt){
		//reviso las colas. Si algujna tiene 2 o más elementos, los quito y les inicio la partida
		
		if (cola.size() > 1){ //Proceso la cola solo si tiene dos o mas elementos
			Session sessionBlanco = (Session)cola.peek();
			Session sessionNegro = (Session)cola.peek();
			
			//(String idNegro, String idBlanco, int dificultRec, ReversiObserver observer){
			Partida partida = new Partida(
				this.pendientes.get(sessionNegro.getId()), //Obtengo el idUsurio
				this.pendientes.get(sessionBlanco.getId()), //Obtengo el idUsurio
				nivelInt, //Nivel de juego
				this //observer!
			);
		}
	}
	
	private void doConnect(String userId, String nivel, Session session){
		
		if (!nivel.matches("[facil|medio|dificil]")){
			//TODO: mensaje de error
			this.msgError(session,"mc1","Nivel no válido");
			return;
		}
		
		//Corroboro que no tenga un juego iniciado
		if (this.indiceSesiones.containsKey(userId)){
			
			int nivelInt;
			Queue cola;
			
			//Agrego los usuarios a las colas de nivel
			switch (nivel){
				case "facil": 
					nivelInt = 0;
					cola = this.niveFacil;
				break;
				
				case "medio": 
					nivelInt = 1;
					cola = this.niveMedio;
				break;
				
				case "dificil": 
					nivelInt = 2;
					cola = this.niveDificil;
				break;
				
				default:
					nivelInt = -1;
					cola = null;
				break;
			}	

				this.pendientes.put(session.getId(), userId);				
				this.encolar(cola, nivelInt, session);
								
		}else{
			//El jugador tiene una partida iniciada
			this.msgError(session, "mc2", "No se puede iniciar una partida para este jugador");
		}
		
	}
	
	private void doMove(){
	}
	
	private void doQuit(){
	}
}

