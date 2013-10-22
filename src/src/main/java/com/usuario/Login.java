package java.com.usuario;

import java.com.usuario.models.User;


public class Login extends User
{

	public Login(){
		//super();
		
		Login login = new Login();		
	}		

	
	public boolean check(String usuario, String password) {
		// Chequea el password del usuario
		return false;	
	}
	

	public static String newUsuario(String id, String password, String name, String email) {
		// Agrega un Usuario
		
		Login login = new Login();
		
        login.set("id", id);		
        login.set("password", password);        
        login.set("name", name);
		login.set("email",email);		
		
		login.saveIt();
		
		return login.getString("id");
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
