package com.reversi;

import com.usuario.UsuarioApp;

import com.usuario.models.User;

import javax.swing.*;

import java.sql.Timestamp;

import java.util.Date;

public class Partida
{
	private String id;
	
	private int dificultad;
	
	private double timeUltMov;
	
	private byte[] tablero;
	
	public UsuarioApp elNegro;
	
	public UsuarioApp elBlanco;
	
	public ReversiObserver reversiObserver;
		
	public Partida(string idNegro, string idBlanco, int dificultRec, ReversiObserver observerRecibido){
		try {
	    if(idNegro != null) {
	     UsuarioApp jugadorNegro = new UsuarioApp();
	     jugadorNegro = jugadorNegro.findById(idNegro); }
	     if (jugadorNegro != null)
			System.out.println ("Jugador encontrado\n");
	}
	catch(Execption e){
	    throw new IllegalArgumentException ("El idNegro recibido no existe\n");
	    
	}
	
	//Aqui controlo que el idBlanco no sea null
	try { if(idBlanco != null) {
		    UsuarioApp jugadorBlanco = new UsuarioApp();	
		    jugadorBlanco = jugadorBlanco.findById(idBlanco); }
		    if (jugadorBlanco != null)
				System.out.println ("Jugador encontrado\n");
	}
	catch (Exception e){
	    throw new IllegalArgumentException ("El idBlanco recibido no existe\n");
	    
	}
	
	//Aqui controlo que el nivel de dificultad sea un valor valido (0, 1 o 2)
	try{
		// Que dificultad sea igual a cero significa que el jugador tendra 30 seg para realizar su movimiento
	    if(dificultRec == 0 || dificultRec == 1 || dificultRec == 2) 
		  dificultad.setDificultad(dificultRec);  
		}
	catch (Exception e){
	    throw new IllegalArgumentException ("El nivel de dificultad recibido no es válido\n");
	    }
	}
	
	public boolean checkMov() {
		// TODO : to implement
		return false;	
	}
		
	public String jugadorActual() {
		// TODO : to implement
		return UsuarioApp.getId();	
	}
	
	public int estadoJuego() {
		// TODO : to implement
		return 0;	
	}
	
	public String getId() {
		// TODO : to implement
		return "";	
	}
	
	public void setDificultad (int difRec) {
		 int dificultad = new difRec;
		//return dificultad;
		}
	
}

