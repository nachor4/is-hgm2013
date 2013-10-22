package org.usuario;

import java.org.usuario.models.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.javalite.test.jspec.JSpec.the;

public class UserSpec{

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

        User user = new User();

        //check errors
        the(user).shouldNotBe("valid");
        the(user.errors().get("name")).shouldBeEqual("value is missing");
        the(user.errors().get("email")).shouldBeEqual("value is missing");

        //set missing values
        user.set("name", "John");
		user.set("email","algo@algo.com");
		
        //all is good:
        the(user).shouldBe("valid");
    }
}
