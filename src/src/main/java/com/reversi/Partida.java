package com.reversi;

import com.usuario.UsuarioApp;

import com.usuario.models.User;

import javax.swing.*;

import java.sql.Timestamp;

import java.util.Date;

public class Partida
{
	private String id;
	
	private double timeUltMov;
	
	private byte[] tablero;
	
	public UsuarioApp elNegro;
	
	public ReversiObserver reversiObserver;
	
	public UsuarioApp elBlanco;
	
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
	    //throw new Exception;
	    //throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
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
	    //throw new Exception;
	}
	
	//Aqui controlo que el nivel de dificultad sea un valor valido (0, 1 o 2)
	try{
		// Que dificultad sea igual a cero significa que el jugador tendra 30 seg para realizar su movimiento
	    if(dificultRec == 0 || dificultRec == 1 || dificultRec == 2) {
		   dificultad = dificultRec;  
		}
	}
	catch (Exception e){
	    throw new IllegalArgumentException ("El nivel de dificultad recibido no es válido\n");
	    //throw new Exception;
	}
	/*
	try{
	    //crear el objeto UserApp
	}
	catch (Exception e){
	    //sucedio un errero.
	   //throw new Exception;
	} */

	public boolean checkMov() {
		// TODO : to implement
		return false;	
	}
		
	public UsuarioApp jugadorActual() {
		// TODO : to implement
		return new UsuarioApp("id");	
	}
	
	public int estadoJuego() {
		// TODO : to implement
		return 0;	
	}
	
	public String getId() {
		// TODO : to implement
		return "";	
	}
	
}

