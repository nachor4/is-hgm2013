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
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }
    
    @Test
    public void testTest(){
		
//Variables Auxiliares
        String id;
        

////////TEST Login  
		System.out.println("\n\n**********TEST Login**********\n\n");
		
		//Creacion de un Usuario Login
	    Login login = new Login();	
	    
        //Funcion getName
		System.out.println("\n\nTEST getName");        
		try{
		    System.out.println("\nEl NOMBRE del Usuario es: "+login.getName("nico"));
		}catch(Exception e){
			System.out.println("getName invalido");
		}  	
		    				

	    //Funcion newUsuario Valido
		System.out.println("\n\nTEST newUsuario valido");
		try{
			int res = login.newUsuario("Jaja", "12345", "John2", "algo@algo.com");
			switch (res){
				case 0:
					System.out.println("\nSe agrego un nuevo Usuario"); 
				break;
				
				case 1:
					System.out.println("\nUsuario Duplicado"); 
				break;
				
				case 2:
					System.out.println("\nError Inesperado"); 
				break;
				
				default:
					System.out.println(res); 
				break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	    //Funcion newUsuario Repetido		
		System.out.println("\n\nTEST newUsuario - Repetido");
		try{
			int res = login.newUsuario("Jaja", "12345", "John2", "algo@algo.com");
			switch (res){
				case 0:
					System.out.println("\nSe agrego un nuevo Usuario"); 
				break;
				
				case 1:
					System.out.println("\nUsuario Duplicado"); 
				break;
				
				case 2:
					System.out.println("\nError Inesperado"); 
				break;
				
				default:
					System.out.println(res); 
				break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


		//Funcion checkPassword
		System.out.println("\n\nTEST checkPassword");		
		try{
			if (login.checkPassword("nico", "13588") == true) {
			   System.out.println("\nEl PASSWORD ingresado coincide con el ID ingresado");} 			
		}catch(Exception e){
			System.out.println("Chequear PASSWORD invalido");
		}
		
		
		//Funcion resetPassword
		System.out.println("\n\nTEST resetPassword");		
		System.out.println("\nReset PASSWORD existente: "+login.resetPassword("nico"));
		System.out.println("\nReset PASSWORD inexistente: "+login.resetPassword("qwerty"));		
		
		System.out.println("\n\n->**********END Test Login**********\n\n");		
		
	
    }

}
