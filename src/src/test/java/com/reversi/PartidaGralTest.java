package com.reversi;
import com.reversi.ReversiObserver;

import com.reversi.Partida;

import org.javalite.activejdbc.Base;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


public class PartidaGralTest {

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Ingenieria", "reversi", "123");
	    System.out.println("---> Se conecto a la base de datos."+Base.connection()+"\n");        
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }
    
    @Test
    public void testTest(){
		
//AUX Vars
        String id;
        
////////TEST Partida
		System.out.println("\n\nTEST Partida\n\n");
		ReversiObserver observ = new ReversiObserver();
	    Partida parti = new Partida("guille", "nico", 1, observ);
	    Ficha ficha = new Ficha(3, 2);
	    Ficha ficha2 = new Ficha(4,2);
	    Ficha ficha3 = new Ficha(5,2);
	    Ficha ficha4 = new Ficha(4,1);
	            
        //getID
		try{
			System.out.println("El ID de la partida es: "+parti.getId());
			//parti.actualizarTablero();
			parti.mover(ficha, "nico");
			
			System.out.println("\n\nGuessing");
			ResultadoMovimiento resultado = parti.mover(ficha, "guille");
			
			if (resultado == null) System.out.println("Movimiento no válido!");
 			System.out.println(resultado);
 			
			parti.mover(ficha2, "nico");
			parti.mover(ficha3, "guille");
			parti.mover(ficha4, "guille");
			        
		}catch(Exception e){
			System.out.println("getID invalido");
		}
		
	//test UserScoring
	try{ 
		UserScoring usuario = new UserScoring();
		usuario = parti.scoring("guille");
		System.out.println("ganadas: "+usuario.gGanadas());
		System.out.println("perdidas: "+usuario.gPerdidas());
		System.out.println("abandonadas: "+usuario.gAbandonadas());
		System.out.println("scoreMas: "+usuario.gScoreMas());
		System.out.println("estaPartida: "+usuario.gEstaPartida());
		
		
	} catch(Exception e) {
		System.out.println("");
		}        		
		
	
	try{
		Thread.sleep(5000);
	}catch (InterruptedException ex) {/*skyp*/}


    
    }
	
}


	
