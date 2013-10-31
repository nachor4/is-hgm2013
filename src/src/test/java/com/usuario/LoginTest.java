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


public class LoginTest {

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
        

////////TEST Login  
		System.out.println("\n\nTEST Login\n\n");
		
		//Usuario Login valido
	    Login login = new Login();	
	    
        //getName
		try{
		    System.out.println("\nEl NOMBRE del Usuario es: "+login.getName("nico"));
		}catch(Exception e){
			System.out.println("getName invalido");
		}  	    				

		//newUsuario
		try{
			if (login.newUsuario("J", "12345", "John2", "algo@algo.com") == 0) {
			    System.out.println("\nSe agrego un nuevo Usuario"); 
			}
		}catch(Exception e){
			System.out.println("Agregar Nuevo Usuario invalido");
		}
				
		//checkPassword
		try{
			if (login.checkPassword("nico", "13588") == true) {
			   System.out.println("\nEl PASSWORD ingresado coincide con el ID ingresado");} 			
		}catch(Exception e){
			System.out.println("Chequear PASSWORD invalido");
		}
		
		//resetPassword
		System.out.println("\nReset PASSWORD existente: "+login.resetPassword("nico"));
		System.out.println("\nReset PASSWORD inexistente: "+login.resetPassword("qwerty"));		
		
		System.out.println("\n\n-> END Test Login\n\n");		
		
	
    }

}
