package com.usuario;

import com.usuario.models.User;
//import com.usuario.UsuarioApp;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.javalite.test.jspec.JSpec.the;

public class UsuarioAppSpec{

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
    public void shouldValidateMandatoryFields(){

        User usuarioapp = new User();

        //check errors
        the(usuarioapp).shouldNotBe("valid");
        the(usuarioapp.errors().get("id")).shouldBeEqual("value is missing");        
        the(usuarioapp.errors().get("password")).shouldBeEqual("value is missing");
        the(usuarioapp.errors().get("name")).shouldBeEqual("value is missing");        
        the(usuarioapp.errors().get("email")).shouldBeEqual("value is missing");
        the(usuarioapp.errors().get("won")).shouldBeEqual("value is missing");        
        the(usuarioapp.errors().get("lost")).shouldBeEqual("value is missing");        
        the(usuarioapp.errors().get("abandoned")).shouldBeEqual("value is missing");        

        //set missing values
        usuarioapp.set("id", "J");
        usuarioapp.set("password", "12345");        
        usuarioapp.set("name", "John");
		usuarioapp.set("email", "algo@algo.com");
		usuarioapp.set("won", 0);		
		usuarioapp.set("lost", 0);				
		usuarioapp.set("abandoned", 0);				
		
        //all is good:
        the(usuarioapp).shouldBe("valid");  
 
    }
}
