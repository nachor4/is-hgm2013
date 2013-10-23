package com.usuario;

import com.usuario.models.User;
//import com.usuario.Login;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.javalite.test.jspec.JSpec.the;

public class LoginSpec{

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

        User login = new User();
        
/*        

        //check errors
        the(login).shouldNotBe("valid");
        the(login.errors().get("id")).shouldBeEqual("value is missing");        
        the(login.errors().get("password")).shouldBeEqual("value is missing");
        the(login.errors().get("name")).shouldBeEqual("value is missing");        
        the(login.errors().get("email")).shouldBeEqual("value is missing");
        the(login.errors().get("won")).shouldBeEqual("value is missing");        
        the(login.errors().get("lost")).shouldBeEqual("value is missing");        
        the(login.errors().get("abandoned")).shouldBeEqual("value is missing");        

        //set missing values
        login.set("id", "J");
        login.set("password", "12345");        
        login.set("name", "John");
		login.set("email", "algo@algo.com");
		login.set("won", 0);		
		login.set("lost", 0);				
		login.set("abandoned", 0);				
		
        //all is good:
        the(login).shouldBe("valid");
        * 
*/     
 
    }
}
