package com.reversi;

import com.reversi.ReversiObserver;

import com.reversi.ResultadoMovimiento;

import com.reversi.Ficha;

import com.usuario.UsuarioApp;

import com.reversi.EstadoJuego;

import com.usuario.Users;

import org.javalite.activejdbc.Base;

//import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

//import com.reversi.Temporizador;

import com.usuario.models.User;

import java.lang.String;

import java.util.*;

import java.io.*;

import java.util.Date;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

public class Partida {
	
	private String id;
	
	private int dificultad;
	
	private ReversiObserver observer = new ReversiObserver();
	
	//private Temporizador tiempoUltMov = new Temporizador();
		
	public String elNegro;  // los hice public para poder accederlo en el timer
	
	private String elBlanco; // los hice public para poder accederlo en el timer
	
	private int tablero[][] = new int [8][8];
	
	private int cantMovimientos; 
	
	public String turnoActual; // los hice public para poder accederlo en el timer
	
	private int cantUnos; // Cantidad de fichas Negras que hay en el tablero.
	
	private int cantDos; // Cantidad dde fichas Blancas que hay en el tablero.
		
	public final Ficha dirUp = new Ficha(0, -1);
	public final Ficha dirDown = new Ficha(0 , 1);
	public final Ficha dirLeft = new Ficha(-1, 0);
	public final Ficha dirRight = new Ficha(1,0);
	public final Ficha dirUpLeft = new Ficha( -1, -1);
	public final Ficha dirUpRight = new Ficha(1, -1);
	public final Ficha dirDownLeft = new Ficha(-1, 1);
	public final Ficha dirDownRight = new Ficha(1,1);
		
	public Partida (String idNegro, String idBlanco, int dificultRec, ReversiObserver observer){
		cantUnos = 0;
		cantDos = 0;
		
	} // Cierra el constructor 

	final Ficha checkDirecciones[] = {dirUp , dirDown ,
 dirLeft , dirRight , dirUpLeft , dirUpRight ,  dirDownLeft , dirDownRight } ;

	public boolean checkMov(Ficha fich, String jugador) {
		boolean result = false;
		String contrario;
		int cont;
		int yoJug;
		String yo;
			if (jugador == elNegro) {
				contrario = elBlanco;
				cont = 2;
				yoJug = 1;}
				else {
					contrario = elNegro;
					yoJug = 2;
					cont = 1;
					}
			
		   yo = jugador;
		   int x = fich.getX();
		   int y = fich.getY();
		   if (tablero[x][y] != 0) {
			 return false;  
		   } else {
			   for (int i = 0; i < checkDirecciones.length; i++) {
				 Ficha coordDirecciones = checkDirecciones[i];
				 
				 int xDir = coordDirecciones.getX();
				 int yDir = coordDirecciones.getY();
				 int salto = 2;
				 
				 if ((y + yDir) > -1 && (y+yDir) < 8 && (x+xDir) < 8 && (x+xDir) > -1) {
					if(tablero[x+xDir][y+yDir] == cont) {
						while( (y+ (salto * yDir)) > -1 && (y+(salto *yDir)) < 8 && (x + (salto * xDir)) < 8 && (x + (salto * xDir)) > -1) {
							if(tablero[x+salto * xDir][y + salto * yDir] == 0) {
								break;
							}	
							if(tablero[x+ salto * xDir][y + salto * yDir] == yoJug) {
								return true;
							}
							salto++;	
						} // cierra el while
					} // cierra el segundo if
				 
				 } // cierra el primer if 
				} // cierra el for
			  } // cierra el else
		   
		
		return result;	
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
	
	public ResultadoMovimiento mover (Ficha ficha, String idJugador) {
				
		//ResultadoMovimiento result = new ResultadoMovimiento ();
		ReversiObserver rever = new ReversiObserver();
		Partida part = new Partida("guille", "nico", 1, rever);
		if (part.checkMov (ficha, idJugador) == false) {
			throw new IllegalArgumentException ("El movimiento es inválido");
			} else {
			ResultadoMovimiento result = new ResultadoMovimiento (part.invertirFichas(ficha, idJugador));
			this.cantDos = result.getBlancas();
			this.cantUnos = result.getNegras();
			part.actualizarTablero();
				if (turnoActual == elNegro) {
					turnoActual = elBlanco;
				}
					else {
					turnoActual = elNegro;
					}
				part.actualizarTablero();	
				return result;
			  }
		}
	
	public ResultadoMovimiento invertirFichas(Ficha ficha, String idJugador) {
		String contrario;
		int contra;
		int yoJug;
		String yo;
		int cont = 0;
		int temp;
		int temp2;
		
		ResultadoMovimiento result = new ResultadoMovimiento( idJugador, this.getCantBlancas(), this.getCantNegras());
		
		if (idJugador == elNegro) {
			contrario = elBlanco;
			contra = 2;
			yoJug = 1;
		}
			else {
				contrario = elNegro;
				yoJug = 2;
				contra = 1;
			}
			
		   yo = idJugador;
		int x = ficha.getX();
		int y = ficha.getY();
		for(int i = 0; i < checkDirecciones.length; i++) {
			Ficha coordDirecciones = checkDirecciones[i];
			int xDir = coordDirecciones.getX();
			int yDir = coordDirecciones.getY();
			boolean potencial = false;
			if((y+yDir) > -1 && (y+yDir) < 8 && (x+xDir) <8 && (x+xDir) > -1) {
				if(tablero[x+xDir][y+yDir] == cont){
					potencial = true;
				}
			}
			if(potencial == true) {
				int salto = 2;
				while( (y + (salto * yDir)) > -1 && (y+(salto * yDir)) < 8 &&  (x+(salto * xDir)) < 8 && (x + (salto * xDir)) > -1){
					if(tablero[x+(salto * xDir)][y + (salto * yDir)] == 0){
						break;
					}
					if(tablero[x+(salto * xDir)][y + (salto * yDir)] == yoJug){
						for (int k = 0; k < salto; k++) {
							tablero[x+k*xDir][y+k*yDir] = yoJug;							
							Ficha fichaResultado = new Ficha(x+k*xDir, y+k*yDir);
							result.getModificaciones()[cont] = fichaResultado;
							cont++;
							if(yoJug == 1){
								
								temp = result.getNegras();
								result.setCantNegras(temp++);
								
								temp2 = result.getBlancas();
								result.setCantBlancas(temp2--);
							}							
								else {
									temp2 = result.getBlancas();
									result.setCantBlancas(temp2++);
									temp = result.getNegras();
									result.setCantNegras(temp--);
								}
						}
					break;
					}
					salto++;
				} 
			}	
		
		}
		return result;
}
	/*

	 */
	 
	public String getId() {
		Partida parti = new Partida(elNegro, elBlanco, dificultad , observer);
		return this.id;	
	}	
				
	public void actualizarTablero() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++){
				if (x == 7) {
				   System.out.println(" "+this.tablero[x][y]); 
				}
					else {
						System.out.println(" "+this.tablero[x][y]);
					} 
				 
			}
		}
	}
	
	public int getCantBlancas() {
		return cantDos;
	}
	
	public int getCantNegras() {
		return cantUnos;
	}

}	

