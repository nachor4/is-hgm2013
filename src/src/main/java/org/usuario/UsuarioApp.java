package java.org.usuario;

import java.util.*;
import java.io.*;

import java.org.usuario.models.User;

import java.org.usuario.Resultado;

import java.lang.String;


public class UsuarioApp extends User 
{
	
	private String id;
	
		
	public UsuarioApp(String id) {
		//super();
		
		UsuarioApp usuarioapp = new UsuarioApp(id);
	}
	

	
    public static String add(
        String id,
		String name, 
		String email)
    {
		UsuarioApp usuarioapp = new UsuarioApp(id);
		
        usuarioapp.set("id", id);		
        usuarioapp.set("name", name);
		usuarioapp.set("email",email);		
		
		usuarioapp.saveIt();
		
		return usuarioapp.getString("id");

    }	
    
/* PREGUNTAR A NACHO!!!
     
    public static void delete(String id){
		UsuarioApp usuarioapp = new UsuarioApp();
		usuarioapp = usuarioapp.findById(id);

		if (usuarioapp == null){
			throw new IllegalArgumentException("Ingreso un ID de USER no válido.");
		}		
		
		usuarioapp.deleteCascade();
    }  
    
     
    public static boolean modify (
        String id,
		String name, 
		String email)
	{
		Usuarioapp usuarioapp = new Usuarioapp();
		usuarioapp = usuarioapp.findById(id);
		
		if (usuarioapp == null){
			throw new IllegalArgumentException("Ingreso un ID de USER no válido.");
		}		
		
        usuarioapp.set("name", name);
		usuarioapp.set("email",email);		
		
		usuarioapp.saveIt();		
		
		return usuarioapp.saveIt();
    }   
	
*/
	
	public String getName() {
		//Busco el usuario
		
        return id;
	}
	
	
	private boolean saveResult(Resultado resultado) {
		// Guardo el resultado de una partida
		
        UsuarioApp u = new UsuarioApp(id);		
		
		if (resultado == Resultado.ganadas) {
		
//			ganadas = new Integer(u.ganadas.intValue() + 1);
			
			u.set("ganadas", ganadas);
			
			u.saveIt();
			
			return true; 
		
		} else if (resultado == Resultado.perdidas) {
			
//					perdidas = new Integer(u.perdidas.intValue() + 1);
				
					u.set("perdidas", perdidas);
				
					u.saveIt();
				
					return true; 
				
		} else if (resultado == Resultado.abandonadas) {
				
//					abandonadas = new Integer(u.abandonadas.intValue() + 1);
					
					u.set("abandonadas", abandonadas);
					
					u.saveIt();
					
					return true; 
		} else {
			
			System.out.println( "\n" + "el resultado ingresado no es correcto ");
		}
					
	}
	
}
