package com.reversi;

import com.usuario.UsuarioApp;

import com.usuario.models.User;

import java.util.*;

import java.io.*;

import javax.swing.*;

import java.sql.Timestamp;

import java.util.Date;

public class Partida {
	
	enum EstadoJuego { INICIADO , CANCELADO, JUGANDO, FINALIZADO; }
	
	private String id;
	
	private int dificultad;
	
	private int tiempoUltMov;
	
	private String elNegro;
	
	private String elBlanco;
	
	private byte[] tablero;
	
	private int cantMovimientos;
	
	private EstadoJuego estadoJuego; 
	
	public ReversiObserver reversiObserver;
	
	private String turnoActual;
		
	public Partida (String idNegro, String idBlanco, int dificultRec, ReversiObserver observerRecibido){
		
		try {
			if( idNegro != "" ) {
				UsuarioApp jugadorNegro = new UsuarioApp(idNegro);
				jugadorNegro = jugadorNegro.findById(idNegro); }
			if ( jugadorNegro != null ) {
				System.out.println ("Jugador encontrado\n"); }
			}
				
		catch(Exception e){
			throw new IllegalArgumentException ("El idNegro recibido no existe\n");
	    
			}
	
	//Aqui controlo que el idBlanco no sea null
	try { 
		if( idBlanco == "") {
		    UsuarioApp jugadorBlanco = new UsuarioApp(idBlanco);	
		    jugadorBlanco = jugadorBlanco.findById(idBlanco); 
			}
		  if ( jugadorBlanco != null ) {
			System.out.println ("Jugador encontrado\n"); }
		}
		catch (Exception e){
			throw new IllegalArgumentException ("El idBlanco recibido no existe\n");
	    
		}
	
	//Aqui controlo que el nivel de dificultad sea un valor valido (0, 1 o 2)
	try{
		// Que dificultad sea igual a cero significa que el jugador tendra 30 seg para realizar su movimiento
	    if(dificultRec == 0 || dificultRec == 1 || dificultRec == 2){ 
		// dificultad.setDificultad(dificultRec);  
		 this.dificultad = dificultRec;
		}
		}
	catch (Exception e){
	    throw new IllegalArgumentException ("El nivel de dificultad recibido no es válido\n");
	    }

} // Cierra el constructor
	
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
		return this.id;	
	} 
	
/*	public void setDificultad (int difRec) {
		int dificultad = new difRec;
		//return dificultad;
		}  */
	
}

