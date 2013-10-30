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
        
        //getID
		try{
			System.out.println("El ID de la partida es: "+parti.getId());        
		}catch(Exception e){
			System.out.println("getID invalido");
		}        		
		
	
    }

}
