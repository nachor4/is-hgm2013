package org.usuario;

import java.org.usuario.Login;

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
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/reversi_hgm", "root", "");
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

//TEST User  
		System.out.println("\n\nTEST Login");

/*		
		* id = Login.newUsuario("J", "12345", "John2","algo@algo.com");
		* System.out.println("Agregar LOGIN: ID->" + "id");
		* 
*/ 

		try{
			Login.newUsuario("J", "12345", "John2","algo@algo.com");
		}catch(Exception e){
			System.out.println("Agregar LOGIN invalido: Genero Excepcion OK");
		}
		
		System.out.println("\n\n-> END Test General\n\n");
    }

}
