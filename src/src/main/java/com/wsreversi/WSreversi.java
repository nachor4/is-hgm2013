package com.wsreversi;
import com.wsreversi.Juego;
import com.reversi.ReversiObserver;

import java.util.Set;
import java.util.HashSet;

//Dependencias para el servidor
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 * Para acceder a los juegos cuando el websocket recibe un mensaje, los busca mediante el ID de la sesssion que origino el evento en el hashmap de juegos
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

@ServerEndpoint("/wsreversi")
public class WSreversi extends ReversiObserver
{
	public Set<Juego> indiceSesiones;
	public Set<Juego> indicePartida;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public WSreversi(){
		//super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		// TODO : to implement	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@OnOpen
	public void onOpen(Session session) {
		// TODO : to implement	
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
	}
	
	public void actualizar(){
		// TODO : to implement	
	}
}

