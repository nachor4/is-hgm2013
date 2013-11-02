package com.wsreversi;

import com.reversi.Partida;
import javax.websocket.Session;


/**
 * 
 */

public class Jugador 
{
	public String  userId;
	public Partida partida;
	public Session pear;	
	
	/**
	 * 
	 */
	public Jugador(String XuserId, Partida Xpartida, Session Xpear){
		userId = XuserId;
		partida = Xpartida;
		pear = Xpear;
	}

}
