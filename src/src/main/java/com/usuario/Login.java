package com.usuario;

import com.usuario.models.User;

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends User
{

	public Login(){
		//super();
	}		
	
	public static String newUsuario(String id, String password, String name, String email) {
		// Agrega un Usuario
		
		Login login = new Login();
		
        login.set("id", id);	
        
        DigestUtils digest = new DigestUtils();
        	
        login.set("password", DigestUtils.sha1(password));        
        login.set("name", name);
		login.set("email",email);		
		
		login.saveIt();
		
		return login.getString("id");
	}	

	
	public boolean check(String id, String password) {
		// Chequea el password del usuario
		
		Login login = new Login();
		
		if (id == login.id)		
		
           DigestUtils digest = new DigestUtils();
        
           String utils = digest.sha1(password);
           
           if (login.password == utils)
        		
		       return true;	
		   
		   else return false;
		   
		else return false;   
	}

	
	
	public int reset(String usuario, String email) {
		// Resetea el password
		return 0;	
	}
	
	
	public String getID() {
		// TODO : to implement
		
		return "id";	
	}
	
}
