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


public class GeneralTest {

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
        
////////TEST UsuarioApp
		System.out.println("\n\nTEST UsuarioApp\n\n");
		
		//Usuario valido
	    UsuarioApp usu = new UsuarioApp("nico");
        
        //getID
		try{
			System.out.println("El ID del Usuario es: "+usu.getID());        
		}catch(Exception e){
			System.out.println("getID invalido");
		}        
        
        //getName
		try{
		    System.out.println("El NOMBRE del Usuario es: "+usu.getName());
		}catch(Exception e){
			System.out.println("getName invalido");
		}       
        
        //getGananada
		try{
		    System.out.println("La cantidad de partidas GANADAS es: "+usu.getGanada());
		}catch(Exception e){
			System.out.println("getGanada invalido");
		}           
        
        //getPerdida  
		try{
		    System.out.println("La cantidad de partidas PERDIDAS es: "+usu.getPerdida()); 
		}catch(Exception e){
			System.out.println("getPerdida invalido");
		}                   
        
        //getAbandonada
		try{
		    System.out.println("La cantidad de partidas ABANDONADAS es: "+usu.getAbandonada()); 
		}catch(Exception e){
			System.out.println("getAbandonada invalido");
		}                           
        
        //saveResult                
        ResultadoPartida resultado = ResultadoPartida.GANO;   
		try{
            usu.saveResult(resultado);
		    System.out.println("La cantidad de partidas GANADAS es: "+usu.getGanada());   
		}catch(Exception e){
			System.out.println("saveResult invalido");
		}        
		
		System.out.println("\n\n-> END Test UsuarioApp\n\n");				    
		

////////TEST Login  
		System.out.println("\n\nTEST Login\n\n");
		
		//Usuario Login valido
	    Login login = new Login("nico");		
		 
        //getID
		try{
			System.out.println("El ID del Usuario Login es: "+login.getID());        
		}catch(Exception e){
			System.out.println("getID invalido");
		} 			

		//newUsuario
		try{
			System.out.println("El ID del Nuevo Usuario es: "+login.newUsuario("J", "12345", "John2", "algo@algo.com", 0, 0, 0));
		}catch(Exception e){
			System.out.println("Agregar Nuevo Usuario invalido");
		}
				
		//checkPassword
		try{
			if (login.checkPassword("nico", "13588") == true) {
			   System.out.println("El PASSWORD ingresado coincide con el ID ingresado");} 			
		}catch(Exception e){
			System.out.println("Chequear PASSWORD invalido");
		}
		
		System.out.println("\n\n-> END Test Login\n\n");		
		
	
    }

}
