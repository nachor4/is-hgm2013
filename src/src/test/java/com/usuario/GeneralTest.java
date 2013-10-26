package com.usuario;

import com.usuario.Login;
import com.usuario.UsuarioApp;
import com.usuario.Resultado;

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
        Resultado result;
        
////////TEST UsuarioApp
		System.out.println("\n\nTEST UsuarioApp");
		
		//Usuario valido
        UsuarioApp usu = new UsuarioApp("nico");
        
		//Usuario invalido
        //UsuarioApp usu1 = new UsuarioApp("zzz");        
        
        //getID
		System.out.println("El ID del Usuario es: "+usu.getID());        
        
        //getName
		System.out.println("El NOMBRE del Usuario es: "+usu.getName());                
        
        //getGananada
		System.out.println("La cantidad de partidas GANADAS es: "+usu.getGanada());          
        
        //getPerdida  
		System.out.println("La cantidad de partidas PERDIDAS es: "+usu.getPerdida()); 
        
        //getAbandonada
		System.out.println("La cantidad de partidas ABANDONADAS es: "+usu.getAbandonada());        
        
        //getAba                
        Resultado resultado = Resultado.GANO;   
        usu.saveResult(resultado);
		System.out.println("La cantidad de partidas GANADAS es: "+usu.getGanada());          
        
/*        		

		//saveResult GANADAS
		result = Resultado.GANADAS;
		
		try{
			this.saveResult(result);
		}catch(Exception e){
			System.out.println("saveResult GANADAS invalido");
		}	
		
		//saveResult PERDIDAS
		result = Resultado.PERDIDAS;		
		
		try{
			UsuarioApp.saveResult(result);
		}catch(Exception e){
			System.out.println("saveResult PERDIDAS invalido");
		}			
		
		//saveResult ABANDONADAS
		result = Resultado.ABANDONADAS;		
		
		try{
			UsuarioApp.saveResult(result);
		}catch(Exception e){
			System.out.println("saveResult ABANDONADAS invalido");
		}
		   

////////TEST Login  
		System.out.println("\n\nTEST Login");

		//newUsuario
		try{
			Login.newUsuario("J", "12345", "John2", "algo@algo.com", 0, 0, 0);
		}catch(Exception e){
			System.out.println("Agregar LOGIN invalido: Genero Excepcion OK");
		}
		
		//check valido
		try{
			Login.check("J", "12345");
		}catch(Exception e){
			System.out.println("Chequear PASSWORD invalido");
		}
		
		//check invalido
		try{
			Login.check("J", "12348");
		}catch(Exception e){
			System.out.println("Chequear PASSWORD valido");
		}		
		
		System.out.println("\n\n-> END Test Login\n\n");		
		
	
*/
    }

}
