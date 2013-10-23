package com.usuario;

import com.usuario.models.User;

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends User
{
	
	private String id;

	public Login(){
		//super();
	}		
	
	public static String newUsuario(String id, String password, String name, String email, int won, int lost, int abandoned) {
		// Agrega un nuevo Usuario
		
		Login login = new Login();
		
        login.set("id", id);	        	
        login.set("password", DigestUtils.md5(password));        
        login.set("name", name);
		login.set("email", email);						
		login.set("won", 0);		
		login.set("lost", 0);				
		login.set("abandoned", 0);				
		
		login.saveIt();
		
		return login.getString("id");
	}	

	
	public boolean check(String id, String password) {
		// Chequea el password del Usuario
		
		Login login = new Login();
		
		login = login.findById(id);

		if (login == null){
			
			return false;		
		
		} else 
		
			if (login.get("password") == DigestUtils.md5(password)) {
        		
				return true;
				
		   } else {
		    
		        return false;
		}  
	}

	
	
	public int reset(String usuario, String email) {
		// Resetea el password
		
		return 0;	
	}
	
	
	public String getID() {
		// Retorna el ID del Usuario
		
		return id;	
	}
	
}