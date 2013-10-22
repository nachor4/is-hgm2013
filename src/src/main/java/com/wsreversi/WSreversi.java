package com.wsreversi;
import com.wsreversi.Juego;
import com.reversi.ReversiObserver;

import java.util.Set;
import java.util.HashSet;


/**
 * Para acceder a los juegos cuando el websocket recibe un mensaje, los busca mediante el ID de la sesssion que origino el evento en el hashmap de juegos
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class WSreversi extends ReversiObserver
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Set<Juego> indiceSesiones;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
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
	
	public void onMessage() {
		// TODO : to implement	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void onOpen() {
		// TODO : to implement	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void onClose() {
		// TODO : to implement	
	}
	
	public void actualizar(){
		// TODO : to implement	
	}
}

