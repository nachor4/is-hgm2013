package com.usuario;

import com.usuario.models.User;

//ActiveJDBC
import org.javalite.activejdbc.Base;

public class Users
{

	protected static void connect(){
		if (!Base.hasConnection()){
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Ingenieria", "reversi", "123");
		}		
	}
}

