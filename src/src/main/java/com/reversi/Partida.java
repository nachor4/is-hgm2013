package com.reversi;

import com.usuario.UsuarioApp;

import com.usuario.Users;

import com.reversi.Temporizador;

import com.usuario.models.User;

import com.reversi.models.PartidaModel;

import java.util.*;

import java.io.*;

import java.util.Date;

public class Partida {
	
	enum EstadoJuego { INICIADO , CANCELADO, JUGANDO, FINALIZADO; }
	
	private String id;
	
	private int dificultad;
	
	private Temporizador tiempoUltMov = new Temporizador();
		
	private String elNegro;
	
	private String elBlanco;
	
	private int tablero[][] = new int [8][8];
	
	private int cantMovimientos;
	
	private EstadoJuego estadoJuego; 
	
	private String turnoActual;
		
	public Partida (String idNegro, String idBlanco, int dificultRec, ReversiObserver observerRecibido){
		connect();
		
		UsuarioApp jugadorNegro = new UsuarioApp(idNegro);
		jugadorNegro = jugadorNegro.findById(idNegro);
		//Controlamos que el jugador Negro haya sido creado correctamente.
			if ( jugadorNegro == null ) {
			     throw new IllegalArgumentException ("El idNegro recibido no existe\n");
			     }
			else {
				//Controlamos que el jugador Blanco haya sido creado correctamente.
				UsuarioApp jugadorBlanco = new UsuarioApp(idBlanco);
		        jugadorBlanco = jugadorBlanco.findById(idBlanco); 
				if (jugadoBlanco == null){
					throw new IllegalArgumentException ("El idBlanco recibido no existe\n");
				}
				
				else 
					if(dificultRec != 0 && dificultRec != 1 && dificultRec != 2){
						throw new IllegalArgumentException ("La dificultad recibida no es válida");
					}
					else { dificultad = dificultRec; 
				
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
						String formattedDate = sdf.format(date);
								
						id = idNegro + idBlanco + formattedDate;
						//Inicializamos el tablero, si un casillero esta en 0, significa que esta vacio
						//si esta en 1 significa que hay una ficha negra y 2 si hay una ficha blanca.
						for (int x = 0; x < 8; x++) {
							for(int y = 0; y < 8; y++) {
								tablero[x][y].setValue(0);
							}
						}
						tablero[3][4].setValue(1);
						tablero[4][3].setValue(1);
						tablero[3][3].setValue(2);
						tablero[4][4].setValue(2);
					
						if(dificultad == 0) {
							tiempoUltMov.Start(60);
						}
						else 
							if(dificultad == 1) {
								tiempoUltMov.Start(40);
							}
							else { 
								tiempoUltMov.Start(20); 
							}
					}
	
	} // Cierra el constructor
}
	public boolean checkMov() {
		// TODO : to implement
		return false;	
	}
		
	public String jugadorActual() {
		return turnoActual;	
	}
	
	public int estadoJuego() {
		// TODO : to implement
		return 0;	
	}
	
	public String getId() {
		return id;	
	}	


}	

