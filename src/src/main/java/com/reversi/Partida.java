package com.reversi;
import com.reversi.ReversiObserver;
import com.reversi.ResultadoMovimiento;
import com.reversi.Ficha;
import com.reversi.EstadoJuego;
import com.reversi.UserScoring;

import com.usuario.UsuarioApp;
import com.usuario.ResultadoPartida;
import com.usuario.models.User;
import org.javalite.activejdbc.Base;

import javax.swing.Timer;
import java.awt.event.*;

import java.lang.String;
import java.lang.Math;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;


public class Partida {
	
	private String id;
	private int dificultad;
	private ReversiObserver observer;
	
	private Timer tiempoUltMov;	
	private int cantTimeOut = 0;
	
	public String elNegro; 
	public String elBlanco;
	
	private int tablero[][] = new int [8][8];
	
	private int cantMovimientos; 
	public String turnoActual; 
	
	private int cantUnos; // Cantidad de fichas Negras que hay en el tablero.
	private int cantDos; // Cantidad dde fichas Blancas que hay en el tablero.
	
	private EstadoJuego estado;
	
	private int abandono = 0;
		
	public final Ficha dirUp = new Ficha(0, -1);
	public final Ficha dirDown = new Ficha(0 , 1);
	public final Ficha dirLeft = new Ficha(-1, 0);
	public final Ficha dirRight = new Ficha(1,0);
	public final Ficha dirUpLeft = new Ficha( -1, -1);
	public final Ficha dirUpRight = new Ficha(1, -1);
	public final Ficha dirDownLeft = new Ficha(-1, 1);
	public final Ficha dirDownRight = new Ficha(1,1);
	
	
	private class Temporizador implements ActionListener {  //estaba private
		private Partida partida;

		public void actionPerformed(ActionEvent e) {
			  partida.timeout();
		}
		public Temporizador (Partida p) {
			this.partida = p;
		}
		
	} 
	

	public Partida (String idNegro, String idBlanco, int dificultRec, ReversiObserver observer){
		boolean datosOK = true;

		UsuarioApp jugadorNegro = new UsuarioApp (idNegro);
		UsuarioApp jugadorBlanco = new UsuarioApp(idBlanco);
		
		//verifico la info recibida
		if (jugadorNegro == null){
			datosOK = false;
			throw new IllegalArgumentException ("El idNegro recibido no existe\n");
		}else if(jugadorBlanco == null){
			datosOK = false;
			throw new IllegalArgumentException ("El idBlanco recibido no existe\n");
		}else if (dificultRec != 0 && dificultRec != 1 && dificultRec != 2){
			datosOK = false;
			throw new IllegalArgumentException ("La dificultad recibida no es válida");
		}
		
		//Si la info recibida es correcta
		if (datosOK){
			this.observer = observer;

			//Almaceno los IDs de los jugadores
			elNegro = idNegro;
			elBlanco = idBlanco;

			//Guardo la configuración de dificultad
			dificultad = dificultRec; 

			/**
			 * Preparo el ID de la partida
			 * Se calcula concatenando los IDs de los jugadores y sumandole un timestamp, 
			 * luego se calcula un hash para unificar el formato de la cadena y mostrarlo de forma encriptada
			 **/  
			id = elNegro + elBlanco + System.currentTimeMillis(); 
			
			
			//Inicializamos el tablero, si un casillero esta en 0, significa que esta vacio
			//si esta en 1 significa que hay una ficha negra y 2 si hay una ficha blanca.
			for (int x = 0; x < 8; x++) 
				for(int y = 0; y < 8; y++)
					tablero[x][y] = 0;
		 
			//Inicializo las variables de juego
			cantUnos = 2;
			cantDos = 2;
			cantMovimientos = 0;

			//Incializacion del tablero
			tablero[3][4] = 1;
			tablero[4][3] = 1;
			tablero[3][3] = 2;
			tablero[4][4] = 2;
			
			this.turnoActual = elNegro;

			//Inicializacion del TIMER
			int tiempo = 0;
			
			switch (dificultad){
				//case 0: tiempo = 0; break;
				case 1: tiempo = 60000; break;
				case 2: tiempo = 30000; break;
			}
						
			if (tiempo > 0){
				tiempoUltMov = new Timer (tiempo, new Temporizador(this));
				tiempoUltMov.setRepeats(false);
				tiempoUltMov.start(); 
			}
		}//End Inicializacion
				
	}//Fin Partida
		


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
	
	
	public EstadoJuego estadoJuego() {
		return estado;
	}
		
	public ResultadoMovimiento mover (Ficha ficha, String idJugador) {
				
		if (this.turnoActual != idJugador) {
			System.out.println("No es tu turno, es el turno de: "+this.turnoActual);
			return null;
			//throw new IllegalArgumentException("No es tu turno");
			
			
		}else if (this.checkMov (ficha, idJugador) == false) {
			//throw new IllegalArgumentException ("El movimiento es inválido");
			return null;
					
		}  else {
			tiempoUltMov.stop();
			cantMovimientos++;
			System.out.println("La cantidad de movimientos hechos es: "+cantMovimientos);
			cantTimeOut = 0;
			if (cantMovimientos == 1){
				estado = EstadoJuego.INICIADO; // El juego esta INICIADO;
				}else if (cantMovimientos > 1 && cantMovimientos < 60 && (movimientosValidos(elNegro).size() > 0 || movimientosValidos(elBlanco).size() > 0) ){
					estado = EstadoJuego.JUGANDO;
					}else if (cantMovimientos == 60 || (movimientosValidos(elNegro).size() == 0 && movimientosValidos(elBlanco).size() == 0)) { 
						System.out.println("ACTUALIZAR -- END");
			try{
				estado = EstadoJuego.TERMINADO;
				tiempoUltMov.stop();
				observer.actualizar(MotivoActualizar.END, this.id);
			}catch(Exception e){System.out.println(e);}
			
		
		} 
			
			ResultadoMovimiento result = new ResultadoMovimiento (this.invertirFichas(ficha, idJugador));
					
					if (idJugador == this.elNegro) {
						this.cantDos = result.getBlancas();
						this.cantUnos = result.getNegras();							
					}
						else {
							this.cantDos = result.getBlancas();
							this.cantUnos = result.getNegras();
						}
					if (turnoActual == elNegro) {
						turnoActual = elBlanco;
					}
						else {
							turnoActual = elNegro;
						}
					this.actualizarTablero();
					tiempoUltMov.restart();
				
					return result;
				}
	}//end fn
	
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
				if(tablero[x+xDir][y+yDir] == contra){
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
							if (tablero[x+k*xDir][y+k*yDir] != yoJug){
								tablero[x+k*xDir][y+k*yDir] = yoJug;
								result.getModificaciones().add(new Ficha(x+k*xDir, y+k*yDir));
							}
							
						} 
						
						break;
					}
					salto++;
				} 
			}	
		
		}
		
		if (idJugador == this.elNegro) {
			result.setCantNegras(this.cantUnos + result.getModificaciones().size());
			result.setCantBlancas(this.cantDos - (result.getModificaciones().size()-1));
		} else {
			result.setCantBlancas(this.cantDos + result.getModificaciones().size());
			result.setCantNegras(this.cantUnos - (result.getModificaciones().size()-1));
			}
		
		System.out.println("Cantidad de fichas invertidas: "+cont);
		return result;
}


	public String getId() {
		return this.id;	
	}	
				
	public void actualizarTablero() {
		for (int x = 0; x < this.tablero.length; x++) {
			for(int y=0; y < this.tablero.length; y++){
				System.out.print(" "+this.tablero[x][y]+ " ");
			}
		System.out.println();
		}
	}

	
	public void finalizar(String idAbandonador) { 
		
		UsuarioApp unJugador = new UsuarioApp(idAbandonador);
		UsuarioApp otroJugador;
		if(idAbandonador == this.elBlanco) {
			otroJugador = new UsuarioApp(elNegro);
			abandono = 2;
		} else {
			otroJugador = new UsuarioApp(elBlanco);
			abandono = 1;
			}		
				
		ResultadoPartida result = ResultadoPartida.ABANDONO;
		unJugador.saveResult (result);
		ResultadoPartida result2 = ResultadoPartida.GANO;
		otroJugador.saveResult (result2);
		estado = EstadoJuego.CANCELADO;
		tiempoUltMov.stop();
		
	} 
	
	public ArrayList<Ficha> movimientosValidos (String idJugador) {
		ArrayList<Ficha> arrMovValidos = new ArrayList<Ficha>();
		
		for (int y = 0; y < tablero.length; y++) {
			for(int x = 0; x < tablero[y].length; x++){
				Ficha testFicha = new Ficha(x , y);
				boolean valido = checkMov(testFicha, idJugador);
				if(valido == true) {
					arrMovValidos.add(testFicha);
				}
			}
		}
	return arrMovValidos;
	}
	
	public UserScoring scoring(String idJugador) {
		UsuarioApp user = new UsuarioApp(idJugador);
		UserScoring userScoring = new UserScoring();
		userScoring.ganadas = user.getGanada();
		userScoring.perdidas = user.getPerdida();
		userScoring.abandonadas = user.getAbandonada();
		
		if(this.estado == this.estado.TERMINADO && this.cantUnos > this.cantDos && idJugador == elNegro) {
			userScoring.estaPartida = 3;
		} else if(this.estado == this.estado.TERMINADO && this.cantUnos > this.cantDos && idJugador == elBlanco){
				userScoring.estaPartida = -1;	
			} else if(this.abandono == 1 && idJugador == elNegro) {
					userScoring.estaPartida = -3;
					}
					else {
						userScoring.estaPartida = 3;
					}
		
		userScoring.scoreMas = Math.max(0, (user.getGanada() * 3) - user.getPerdida() - (user.getAbandonada() * 3));
		
		return userScoring;
	}
	
	
	public void timeout(){
		tiempoUltMov.stop();

		cantTimeOut++;
		
		if (turnoActual == elNegro) turnoActual = elBlanco;
		else turnoActual = elNegro;
		
		try{
			if(cantTimeOut < 4){
				System.out.println("ACTUALIZAR -- TIMEOUT");
				observer.actualizar(MotivoActualizar.TIMEOUT,this.id);
				tiempoUltMov.restart();
			}else{
				System.out.println("ACTUALIZAR -- CANCELADO");
				observer.actualizar(MotivoActualizar.CANCELADO, this.id);
				
			}
		}catch(Exception e){System.out.println(e);}
			
	}
	
	


	public int getCantBlancas() {
		return cantDos;
	}
	
	public int getCantNegras() {
		return cantUnos;
	}
	
	public String whoIsBlancas() {
		return elBlanco;
	}
	
	public String whoIsNegras() {
		return elNegro;
	}
	
	public void setUnos(int negras) {
			cantUnos = negras;
	}
	
	public void setDos(int blancas) {
			cantDos = blancas;
	}
	
	public String jugadorActual() {
		return turnoActual;	
	}
	
	
}	

