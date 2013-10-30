package com.reversi;

import com.usuario.UsuarioApp;

import com.reversi.EstadoJuego;

import com.usuario.Users;

import org.javalite.activejdbc.Base;

//import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

//import com.reversi.Temporizador;

import com.usuario.models.User;

import java.lang.String;

import com.reversi.models.PartidaModel;

import java.util.*;

import java.io.*;

import java.util.Date;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

public class Partida {
	
	private String id;
	
	private int dificultad;
	
	//private Temporizador tiempoUltMov = new Temporizador();
		
	public String elNegro;  // los hice public para poder accederlo en el timer
	
	public String elBlanco; // los hice public para poder accederlo en el timer
	
	private int tablero[][] = new int [8][8];
	
	private int cantMovimientos; 
	
	public String turnoActual; // los hice public para poder accederlo en el timer
		
	public Partida (String idNegro, String idBlanco, int dificultRec, ReversiObserver observerRecibido){
		
		
		
		User jugadorNegro = new User();
		jugadorNegro = jugadorNegro.findById(idNegro);
		
		User jugadorBlanco = new User();
		jugadorBlanco = jugadorBlanco.findById(idBlanco);
		
		//Controlamos que el jugador Negro haya sido creado correctamente.
			if ( jugadorNegro == null ) {
			     throw new IllegalArgumentException ("El idNegro recibido no existe\n");
			     }
				else if (jugadorBlanco == null){
						throw new IllegalArgumentException ("El idBlanco recibido no existe\n");
					 }
				
					else if(dificultRec != 0 && dificultRec != 1 && dificultRec != 2){
							throw new IllegalArgumentException ("La dificultad recibida no es válida");
							}
					else { 
						
						dificultad = dificultRec; 
						
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
						String formattedDate = sdf.format(date);
								
						id = idNegro + idBlanco + formattedDate;
						//Inicializamos el tablero, si un casillero esta en 0, significa que esta vacio
						//si esta en 1 significa que hay una ficha negra y 2 si hay una ficha blanca.
						for (int x = 0; x < 8; x++) {
							for(int y = 0; y < 8; y++) {
								tablero[x][y] = 0;
						 }
					 }
					 
					tablero[3][4] = 1;
					tablero[4][3] = 1;
					tablero[3][3] = 2;
					tablero[4][4] = 2;
					
						if(dificultad == 0) {
						/*	int seg = 60;
							Timer tiempo = new Timer (seg, new ActionListener() {
								public void actionPerformed(ActionEvent ev) {
									if (this.turnoActual == this.elNegro) {  
										this.turnoActual = this.elBlanco; 
									}
										else {
										this.turnoActual = this.elNegro;
										}
									}
								});
							tiempo.Start(); */
						}
							else 
								if(dificultad == 1) {
								/*	int seg = 40;
									Timer tiempo = new Timer (seg, new ActionListener() {
									public void actionPerformed(ActionEvent ev) {
										if (this.turnoActual == this.elNegro) {  
											this.turnoActual = this.elBlanco; 
										}
											else {
												this.turnoActual = this.elNegro;
											}
										}
									});
									
									tiempo.Start(); */
								}
								else { 
									  /*  int seg = 20;
										Timer tiempo = new Timer (seg, new ActionListener() {
										public void actionPerformed(ActionEvent ev) {
										if (this.turnoActual == this.elNegro) {  
											this.turnoActual = this.elBlanco; 
										}
											else {
												this.turnoActual = this.elNegro;
											}
										}
										});
										tiempo.Start(); */
									}
						this.turnoActual = this.elNegro;
						cantMovimientos = 0;
					}
	
	} // Cierra el constructor

	public boolean checkMov() {
		// TODO : to implement
		return false;	
	}
		
	public String jugadorActual() {
		return turnoActual;	
	}
	
	public EstadoJuego estadoJuego() {
		if (cantMovimientos == 1) {
			return EstadoJuego.INICIADO; // El juego esta INICIADO;
				}
			else if(cantMovimientos > 1 ) /*&& cantMovimientos < 60 && (cantValidos(idNegro) != 0 || cantValidos(idBlanco) != 0))*/ {
					return EstadoJuego.JUGANDO;}	//El juego esta en estado JUGANDO.
					else if (cantMovimientos == 60) { /*|| (cantValidos(idNegro) == 0 && cantValidos(idBlanco) == 0) */
							return EstadoJuego.FINALIZADO; }// el juego esta en estado Finalizado.
							else {
								return EstadoJuego.CANCELADO; // El juego fue CANCELADO.
							}
			
	}
	
	public String getId() {
		return id;	
	}	
	
	public int getCantNegras() {
		return 0;
		}
	

}	

