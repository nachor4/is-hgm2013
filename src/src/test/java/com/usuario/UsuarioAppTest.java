package com.usuario;

import com.usuario.Login;
import com.usuario.UsuarioApp;
import com.usuario.ResultadoPartida;

import org.javalite.activejdbc.Base;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


public class UsuarioAppTest {

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Ingenieria", "reversi", "123");       
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
        
        
////////TEST UsuarioApp
		System.out.println("\n\n**********TEST UsuarioApp**********\n\n");
		
		//Creacion de Usuario UsuarioApp
	    UsuarioApp usu = new UsuarioApp("nico");
	    
        
        //getID
		System.out.println("\n\nTEST getID");        
		try{
			System.out.println("\nEl ID del Usuario es: "+usu.getID());        
		}catch(Exception e){
			System.out.println("getID invalido");
		}   
		     
        
        //getName
		System.out.println("\n\nTEST getName");         
		try{
		    System.out.println("\nEl NOMBRE del Usuario es: "+usu.getName());
		}catch(Exception e){
			System.out.println("getName invalido");
		}       
		
        
        //getGananada
		System.out.println("\n\nTEST getGanada");         
		try{
		    System.out.println("\nLa cantidad de partidas GANADAS es: "+usu.getGanada());
		}catch(Exception e){
			System.out.println("getGanada invalido");
		}          
		 
        
        //getPerdida  
		System.out.println("\n\nTEST getPerdida");         
		try{
		    System.out.println("\nLa cantidad de partidas PERDIDAS es: "+usu.getPerdida()); 
		}catch(Exception e){
			System.out.println("getPerdida invalido");
		}       
		            
        
        //getAbandonada
		System.out.println("\n\nTEST getAbandonada");         
		try{
		    System.out.println("\nLa cantidad de partidas ABANDONADAS es: "+usu.getAbandonada()); 
		}catch(Exception e){
			System.out.println("getAbandonada invalido");
		}                           
		    	
        
        //saveResult                
		System.out.println("\n\nTEST saveResult\n");         
        ResultadoPartida resultado = ResultadoPartida.GANO;   
		try{
            usu.saveResult(resultado);       
		    System.out.println("\nLa cantidad de partidas GANADAS es: "+usu.getGanada());   
		}catch(Exception e){
			System.out.println("saveResult invalido");
		}        
		
		System.out.println("\n\n->**********END Test UsuarioApp**********\n\n");		
		
	
    }

}
