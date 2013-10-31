package com.usuario;

import com.usuario.models.User;

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends Users
{

	public Login() {
		
		connect();
		
    }  	
    
    public String getName(String id) {
        //Retorno el nombre del Usuario Login
        
        connect();
        
		User login = new User();	
		
		login = login.findById(id);
		
		if (login == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return login.getString("name");
    }    
			
	
	public int newUsuario(String id, String password, String name, String email) {
		// Agrega un nuevo Usuario
		
		connect();
		
		User login = new User();
		
		if (login.findById(id) == null) {		
		
			try{
				
				login.set("id", id);	        	
				login.set("password", DigestUtils.md5Hex(password));        
				login.set("name", name);
				login.set("email", email);				
			
				login.insert();
			
				return 0;
			
			}catch(Exception e){
			
				return 2;
			} 
						
	    } else		
	    
	        return 1;
 
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
	
	
	public int resetPassword(String id) {
		// Resetea el password
		
		connect();
		
		User login = new User();
		
		login = login.findById(id);
		
		if (login != null) {		
		
			try{	  
				      	
				login.set("password", DigestUtils.md5Hex("12345"));
			
				login.saveIt();
			
				return 0;
			
			}catch(Exception e){
			
				return 2;
			} 
						
	    } else		
	    
	        return 1;
 
	}
	
}
