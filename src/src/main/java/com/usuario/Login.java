package com.usuario;

import com.usuario.models.User;

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends Users
{

	public Login() {
		
		connect();
		
    }  	
			
	
	public String newUsuario(String id, String password, String name, String email, int won, int lost, int abandoned) {
		// Agrega un nuevo Usuario
		
		connect();
		
		User login = new User();
		
        login.set("id", id);	        	
        login.set("password", DigestUtils.md5Hex(password));        
        login.set("name", name);
		login.set("email", email);						
		login.set("won", 0);		
		login.set("lost", 0);				
		login.set("abandoned", 0);				
		
		login.saveIt();
		
		return login.getString("id");
	}	


	public boolean checkPassword(String id, String password) {
		// Chequea el password del Usuario
		
		connect();
		
		User login = new User();
		
		login = login.findById(id);

		if (login == null){
			
            System.out.println("Ingreso un ID de Usuario invalido");			
			
			return false;		
		
		} else { 
		
			if (login.getString("password").equals(DigestUtils.md5Hex(password))) {
        		
		        return true;
				
		   } else {
			   
				System.out.println("El PASSWORD ingresado es invalido");			   
			   
			    return false;
			   
			}
		}  
	}
	
	
	public int resetPassword(String usuario, String email) {
		// Resetea el password
		
		return 0;	
	}
	
}
