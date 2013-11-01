package com.wsreversi;

import com.reversi.Partida;
import javax.websocket.Session;


/**
 * 
 */

public class Juego
{
	public Partida partida;
	public Session blancas;
	public Session negras;
	
	/**
	 * 
	 */
	public Juego(Partida Xpartida, Session Xblancas, Session Xnegras){
		partida = Xpartida;
		blancas = Xblancas;
		negras = Xnegras;
	}

}
