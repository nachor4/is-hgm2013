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
		System.out.println("\n\nTEST NewUsuario");
		try{
			int res = login.newUsuario("J", "12345", "John2", "algo@algo.com");
			switch (res){
				case 0:
					System.out.println("Se agrego un nuevo Usuario"); 
				break;
				
				case 1:
					System.out.println("Usuario Duplicado"); 
				break;
				
				case 2:
					System.out.println("Error Inesperado"); 
				break;
				
				default:
					System.out.println(res); 
				break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n\nTEST NewUsuario - Repetido");
		try{
			int res = login.newUsuario("J", "12345", "John2", "algo@algo.com");
			switch (res){
				case 0:
					System.out.println("Se agrego un nuevo Usuario"); 
				break;
				
				case 1:
					System.out.println("Usuario Duplicado"); 
				break;
				
				case 2:
					System.out.println("Error Inesperado"); 
				break;
				
				default:
					System.out.println(res); 
				break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		
		
		System.out.println("\n\nTEST CheckPasswor\n\n");
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