package com.usuario;

import com.usuario.models.User;

//ActiveJDBC
import org.javalite.activejdbc.Base;

public class Users
{
/*
	public Users(){
		//super();
	}
*/

	private void connect(){
		if (!Base.hasConnection()){
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/Ingenieria", "reversi", "123");	
			System.out.println("---> Se conecto a la base de datos."+Base.connection()+"\n");
		}		
	}
}

