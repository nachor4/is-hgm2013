package com.wsreversi;
import com.wsreversi.Juego;
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
	
	//<sessionID,cola>
	private HashMap<String,String> pendientes; //Conexiones establecidas que no han iniciado una partida
	
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
		this.pendientes.put(session.getId(), "");
	}	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@OnMessage
	public void onMessage(String message, Session session) {		
		
		Gson gson = new Gson();
		//try{
			Properties jMessage = gson.fromJson(message, Properties.class);
		//}catch{
			//envio mensaje error -- No se pudo parsear el json
			//return;
//		}
	   
		switch(jMessage.getProperty("operacion")){
			case "connect":
				//Agregrego el usuario a la cola y actualizo en pendientes
				
				String userId = jMessage.getProperty("id");
				String nivel  = jMessage.getProperty("nivel");
				
				if (this.pendientes.containsKey(userId)){
					
					String cola = this.pendientes.get(userId);
					
					
					
					
				}else{
					//TODO: unexpected error. invalid operation
					
				}
				
				
				
				
			break;
			
			case "move":
			break;
			
			case "quit":
			break;
			
			default:
				//envio mensaje error -- operacion no válida session.send("")
				//return;
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
		 * Si la session @Pendientes, la elimino. Si la sesion esta inciada pero sin pareja, la elimino de las colas
		 * Si la session !@Pendientes, busco el ID de la partida. Cierro la partida y elimino las referencias de los índices.
		 */
	}
	
	public void actualizar(){
		// TODO : to implement	
	}
}

