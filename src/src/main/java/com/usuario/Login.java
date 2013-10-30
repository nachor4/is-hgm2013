package com.usuario;

import com.usuario.models.User;

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends Users
{

	
	private String id;

	public Login(String id) {
		
		connect();
		
		User login = new User();
		
		try{
			login = login.findById(id);
		}catch(Exception e){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}
		
		this.id = id;
    }  
	
	
	public String getID() {
		// Retorna el ID del Usuario
        
		User login = new User();	
		login = login.findById(id);
		
		if (login == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return login.getString("id");
	}  	
		
	
	public String newUsuario(String id, String password, String name, String email, int won, int lost, int abandoned) {
		// Agrega un nuevo Usuario
		
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
		
		User login = new User();
		
		login = login.findById(id);

		if (login == null){
			
            System.out.println("Ingreso un ID de Usuario invalido");			
			
			return false;		
		
		} else { 
			
			//String pass_usu = login.getString("password");
		
		    //String pass_ing = DigestUtils.md5Hex(password);
		
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
