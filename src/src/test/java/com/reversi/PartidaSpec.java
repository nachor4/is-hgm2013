package com.reversi;

import com.reversi.ReversiObserver;
import com.reversi.models.PartidaModel;
//import com.usuario.Login;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.javalite.test.jspec.JSpec.the;

public class PartidaSpec{

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
    public void shouldValidateMandatoryFields(){ /*
		
		ReversiObserver observ = new ReversiObserver();
        Partida partida = new Partida("guille", "nico", 1, observ);

        //check errors
        the(partida).shouldNotBe("valid");
        the(partida.errors().get("elNegro")).shouldBeEqual("value is missing");        
        the(partida.errors().get("elBlanco")).shouldBeEqual("value is missing");  
        the(partida.errors().get("dificultad")).shouldBeEqual("value is missing");
        the(partida.errors().get("ReversiObserver")).shouldBeEqual("value is missing");
                
        
                
        //set missing values
        partida.set("elNegro","nico");		
		partida.set("elBlanco", "guille");
        partida.set("dificultad", 1);
        partida.set("ReversiObserver", observ);        
        
		
						
						
		
        //all is good:
        the(partida).shouldBe("valid");  */
 
    }
}
