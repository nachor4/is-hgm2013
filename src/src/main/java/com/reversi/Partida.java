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
	// Es la concatenacion de los id de los jugadores con un timeStamp.
	private String id;
	
	// Nivel de dificultad de la partida. 
	private int dificultad; 
	
	// Cada posición del tablero puede estar en 0 (vacía), 1 (hay una ficha negra) o 2 (hay una ficha blanca).
	private int tablero[][] = new int [8][8]; 
	
	// Controla el tiempo que le queda a un jugador para hacer su movimiento.
	private Timer tiempoUltMov;
	
	// Representa el id del jugador Negro.
	public String elNegro;
	
	//Representa las fichas del jugador Blanco. 
	public String elBlanco;
	
	// Lleva la cuenta del total de movimientos realizados hasta el  momento.
	private int cantMovimientos;
	
	// Almacena el estado actual de la partida (INICIADO, JUGANDO, CANCELADO, TERMINADO)
	private EstadoJuego estado;
	
	// Lleva el control de que jugador tiene el turno.
	public String turnoActual;
	
	// Controla que jugador abandona una partida: 0 (Ninguno), 1 (Negro), 2 (Blanco)
	private int abandono = 0;
	
	/* Controla la cantidad de veces que un jugador no hace el movimiento porque se le termina el tiempo.
	 * si llega a 4, la partida se da por CANCELADA y se considera a ambos jugadores como partida abandonada
	 */
	private int cantTimeOut = 0;
	
	// Cantidad de fichas Negras que hay en el tablero.
	private int cantUnos;
	
	// Cantidad dde fichas Blancas que hay en el tablero.  
	private int cantDos; 
	
	private ReversiObserver observer;
	
	/* Se utilizan para buscar piezas amigas y enemigas alrededor de una determinada posición, 
	 * son importantes para buscar movimientos válidos en el tablero.
	 */
	public final Ficha dirUp = new Ficha(0, -1);
	public final Ficha dirDown = new Ficha(0 , 1);
	public final Ficha dirLeft = new Ficha(-1, 0);
	public final Ficha dirRight = new Ficha(1,0);
	public final Ficha dirUpLeft = new Ficha( -1, -1);
	public final Ficha dirUpRight = new Ficha(1, -1);
	public final Ficha dirDownLeft = new Ficha(-1, 1);
	public final Ficha dirDownRight = new Ficha(1,1);
	
	// Definición de la clase temporizador.
	private class Temporizador implements ActionListener {
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
		
		//verifico la información recibida.
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
		
		//Si la información recibida es correcta.
		if (datosOK){
			this.observer = observer;

			//Almaceno los IDs de los jugadores.
			elNegro = idNegro;
			elBlanco = idBlanco;

			//Guardo la configuración de dificultad.
			dificultad = dificultRec; 

			/*
			 * Preparo el ID de la partida
			 * Se calcula concatenando los IDs de los jugadores y sumandole un timestamp, 
			 * luego se calcula un hash para unificar el formato de la cadena y mostrarlo de forma encriptada.
			 */  
			id = elNegro + elBlanco + System.currentTimeMillis(); 
			
			
			/* Inicializamos el tablero, si un casillero esta en 0, significa que esta vacio
			 * si esta en 1 significa que hay una ficha negra y 2 si hay una ficha blanca.
			 */
			for (int x = 0; x < 8; x++) 
				for(int y = 0; y < 8; y++)
					tablero[x][y] = 0;
			// Posiciones del tablero que inicialmente estan ocupadas por fichas de alguno de los jugadores.	
			tablero[3][4] = 1;
			tablero[4][3] = 1;
			tablero[3][3] = 2;
			tablero[4][4] = 2;
		 
			// Inicializo las variables de juego
			cantUnos = 2;
			cantDos = 2;
			cantMovimientos = 0;

			
			// Seteo la variable turno, SIEMPRE inicia moviendo el jugador negro.
			this.turnoActual = elNegro;

			// Inicializacion del TIMER.
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
		}//Fin Inicializacion
				
	}//Fin Partida
		

	/* Agrupamos todas las direcciones que se chequean en un arreglo para luego utilizar este en los metodos checkMov
	 * y invertirFichas, para asi verificar mas facilmente todas las direcciones posibles.
	 */
	final Ficha checkDirecciones[] = {dirUp , dirDown ,
		  dirLeft , dirRight , dirUpLeft , dirUpRight ,  dirDownLeft , dirDownRight } ;

	/* Este metodo verifica que el movimiento recibido como parámetro sea válido, teniendo en cuenta para esto
	 * que jugador lo realiza, en caso de serlo, el metodo retorna true, en caso contrario retorna false.
	 */
	public boolean checkMov(Ficha fich, String jugador) {
		boolean result = false;
		String contrario;
		int cont;
		int yoJug;
		String yo;

		if (jugador == elNegro) {
			contrario = elBlanco;
			cont = 2;
			yoJug = 1;
		}else{
			  contrario = elNegro;
			  yoJug = 2;
			  cont = 1;
		}
			
	   yo = jugador;
	   int x = fich.getX();
	   int y = fich.getY();
	   if (tablero[x][y] != 0){
		   return false;  
	   }else {
        for (int i = 0; i < checkDirecciones.length; i++) {
			 Ficha coordDirecciones = checkDirecciones[i];
				 
			 int xDir = coordDirecciones.getX();
			 int yDir = coordDirecciones.getY();
			 int salto = 2;
				 
			 if ((y + yDir) > -1 && (y+yDir) < 8 && (x+xDir) < 8 && (x+xDir) > -1){
				if(tablero[x+xDir][y+yDir] == cont) {
					while( (y+ (salto * yDir)) > -1 && (y+(salto *yDir)) < 8 
							&& (x + (salto * xDir)) < 8 && (x + (salto * xDir)) > -1) {
						if(tablero[x+salto * xDir][y + salto * yDir] == 0){
							break;
						}	
						if(tablero[x+ salto * xDir][y + salto * yDir] == yoJug){
							return true;
						}
						salto++;	
					} // cierra el while
					
				} // cierra el segundo if
			 
			 } // cierra el primer if 
			 
		 } // cierra el for
		 
	   } // cierra el else
		   
		
		return result;	
	} //Fin metodo checkMov
	
	
	/* Este metodo lleva a cabo efectivamente un movimiento, verificando antes de que quien lo envía sea el
	 * jugador que posee actualmente el turno y que una vez chequeado este haya retornado true, es decir que
	 * se comprobo que es un movimiento válido.
	 */
	public ResultadoMovimiento mover (Ficha ficha, String idJugador) {
				
		if (this.turnoActual != idJugador) {
			System.out.println("No es tu turno, es el turno de: "+this.turnoActual);
			return null;
		}else if (this.checkMov (ficha, idJugador) == false){
			return null;			
		}else{
			tiempoUltMov.stop(); //detenemos el temporizador.
			cantMovimientos++; // actualizamos la cantidad de movimientos realizados en la partida.
			System.out.println("La cantidad de movimientos hechos es: "+cantMovimientos);
			cantTimeOut = 0;
			//Controlamos el estado del juego.
			if (cantMovimientos == 1){
				estado = EstadoJuego.INICIADO; 
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
			}else{
				this.cantDos = result.getBlancas();
				this.cantUnos = result.getNegras();
			}
			
			if (turnoActual == elNegro) turnoActual = elBlanco;
			else turnoActual = elNegro;
			
			this.actualizarTablero();
			tiempoUltMov.restart();
				
			return result;
				} //Fin else.
	}//Fin mover.
	
	/* Invierte las fichas del jugador contrario despues de un movimiento. Chequea todas las direcciones
	 * alrededor de una posición, determina como potencial aquella dirección en la que encuentra fichas enemigas
	 * y continúa la busqueda en dicha direccion hasta encontrar una posición vacía o una que contenga una ficha
	 * propia, luego convierte todas las posiciones en esa dirección hasta llegar a la posición antes mencionada. */
	 
	public ResultadoMovimiento invertirFichas(Ficha ficha, String idJugador) {
		String contrario;
		int contra;
		int yoJug;
		String yo;
		int cont = 0;
		int temp;
		int temp2;
		
		ResultadoMovimiento result = new ResultadoMovimiento( idJugador, this.getCantBlancas(), this.getCantNegras());
		
		if (idJugador == elNegro){
			contrario = elBlanco;
			contra = 2;
			yoJug = 1;
		}else{
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
			
			// Verifica que haya una ficha del jugador contrario al lado de la nuestra.
			if((y+yDir) > -1 && (y+yDir) < 8 && (x+xDir) <8 && (x+xDir) > -1) {
				if(tablero[x+xDir][y+yDir] == contra){
					potencial = true;
				}
			}
			if(potencial == true) {
				int salto = 2;
				
				while( (y + (salto * yDir)) > -1 && (y+(salto * yDir)) < 8 &&  (x+(salto * xDir)) < 8 && (x + (salto * xDir)) > -1){
					// Verifico si encuentra una ficha propia.
					if(tablero[x+(salto * xDir)][y + (salto * yDir)] == 0) break;
					
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
				} //Fin while 
			} // Fin if	
		
		} // Fin for
		
		if (idJugador == this.elNegro) {
			result.setCantNegras(this.cantUnos + result.getModificaciones().size());
			result.setCantBlancas(this.cantDos - (result.getModificaciones().size()-1));
		}else{
			result.setCantBlancas(this.cantDos + result.getModificaciones().size());
			result.setCantNegras(this.cantUnos - (result.getModificaciones().size()-1));
		}
		
		System.out.println("Cantidad de fichas invertidas: "+cont);
		return result;
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
		}else{
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
		
		for (int y = 0; y < tablero.length; y++) 
			for(int x = 0; x < tablero[y].length; x++){
				Ficha testFicha = new Ficha(x , y);
				boolean valido = checkMov(testFicha, idJugador);
				if(valido == true) arrMovValidos.add(testFicha);
			}
		
		return arrMovValidos;
	} // Fin movimientosValidos.
	
	public UserScoring scoring(String idJugador){
		
		UsuarioApp user = new UsuarioApp(idJugador);
		UserScoring userScoring = new UserScoring();
		userScoring.ganadas = user.getGanada();
		userScoring.perdidas = user.getPerdida();
		userScoring.abandonadas = user.getAbandonada();
		
		if(this.estado == EstadoJuego.TERMINADO && this.cantUnos > this.cantDos && idJugador == elNegro){
		   userScoring.estaPartida = 3;
		}else if(this.estado == EstadoJuego.TERMINADO && this.cantUnos > this.cantDos && idJugador == elBlanco){
			  userScoring.estaPartida = -1;
		 }else if(this.estado == EstadoJuego.TERMINADO && this.cantUnos < this.cantDos && idJugador == elBlanco){
			  userScoring.estaPartida = 3;
		 }else if(this.estado == EstadoJuego.TERMINADO && this.cantUnos < this.cantDos && idJugador == elNegro){
			  userScoring.estaPartida = -1;
		 }else if(this.abandono == 1 && idJugador == this.elNegro) userScoring.estaPartida = -3;
		  else if(this.abandono == 1 && idJugador == this.elBlanco) userScoring.estaPartida = 3;
		  else if(this.abandono == 2 && idJugador == this.elBlanco) userScoring.estaPartida = -3;
		  else if(this.abandono == 1 && idJugador == this.elNegro) userScoring.estaPartida = 3;
		  else if(this.abandono == 3) userScoring.estaPartida = -3;
			 
					
		
		userScoring.scoreMas = Math.max(0, (user.getGanada() * 3) - user.getPerdida() - (user.getAbandonada() * 3));
		
		return userScoring;
	} //Fin scoring.
	
	
	public void timeout(){
		tiempoUltMov.stop();

		cantTimeOut++;
		
		if (turnoActual == elNegro)turnoActual = elBlanco;
		else turnoActual = elNegro;
		
		try{
			if(cantTimeOut < 4){
				System.out.println("ACTUALIZAR -- TIMEOUT");
				observer.actualizar(MotivoActualizar.TIMEOUT,this.id);
				tiempoUltMov.restart();
			}else{
				System.out.println("ACTUALIZAR -- CANCELADO");
				abandono = 3; // indica que ambos jugadores han abandonado la partida.
				observer.actualizar(MotivoActualizar.CANCELADO, this.id);
				ResultadoPartida result = ResultadoPartida.ABANDONO;
				UsuarioApp elBlanquito = new UsuarioApp(elBlanco);
				UsuarioApp elNegrito = new UsuarioApp(elNegro);
				elBlanquito.saveResult (result);
				elNegrito.saveResult (result);
				
			}
		}catch(Exception e){System.out.println(e);}
			
	}
	
	public String getId(){
		return this.id;	
	}
	
	public int getCantBlancas(){
		return cantDos;
	}
	
	public int getCantNegras(){
		return cantUnos;
	}
	
	public String whoIsBlancas(){
		return elBlanco;
	}
	
	public String whoIsNegras(){
		return elNegro;
	}
	
	public void setUnos(int negras){
			cantUnos = negras;
	}
	
	public void setDos(int blancas){
			cantDos = blancas;
	}
	
	public String jugadorActual(){
		return turnoActual;	
	}
	
	public EstadoJuego estadoJuego(){
		return estado;
	}
	
	
}	

