package com.usuario;

import java.util.*;
import java.io.*;

import com.usuario.models.User;
import com.usuario.Resultado;

import java.lang.String;


public class UsuarioApp extends Users
{

    private String id;

    public UsuarioApp(String id) {
		connect();
		
		User user = new User();
		
		try{
			user = user.findById(id);
			System.out.println("Usuario encontrado!");
		}catch(Exception e){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}
		
		this.id = id;
    }  
    
	public String getID() {
		// Retorna el ID del Usuario
		
        connect();
        
		User user = new User();	
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    System.out.println("El ID del Usuario es: "+id);
		    return user.getString("id");
	}  


    public String getName() {
        //Retorno el nombre del Usuario
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		String nombre;
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    nombre = user.getString("name");
		    System.out.println("El NOMBRE del Usuario es: "+nombre);
		    return user.getString("name");
    }
   
    
    public int getGan() {
        //Retorno las partidas ganadas
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		int ganadas;
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    ganadas = user.getInteger("won");
		    System.out.println("La cantidad de partidas GANADAS es: "+ganadas);
		    return user.getInteger("won");
    }  
    
    
    public int getPer() {
        //Retorno las partidas Perdidas
        
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		int perdidas;
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    perdidas = user.getInteger("lost");
		    System.out.println("La cantidad de partidas PERDIDAS es: "+perdidas);
		    return user.getInteger("lost");
    }      
       
    
    public int getAba() {
        //Retorno las partidas Abandonadas

        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		int abandonadas;
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    abandonadas = user.getInteger("abandoned");
		    System.out.println("La cantidad de partidas ABANDONADAS es: "+abandonadas);
		    return user.getInteger("abandoned");
    }                  


    public boolean saveResult(Resultado resultado) {
        // Guardo el resultado de una partida

        connect();
        
		User u = new User();	
		
		u = u.findById(id);
		
		if (u == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
			
	    } else

          if (resultado == Resultado.GANADAS) {

              int g = u.getInteger("won");

              g++;

              u.set("won", g);

              u.saveIt();
            
              System.out.println("Se han actualizado las partidas GANADAS");

            return true;

        } else if (resultado == Resultado.PERDIDAS) {

                    int p = u.getInteger("lost");

                    p++;

                    u.set("lost", p);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas PERDIDAS");                    

                    return true;

        } else if (resultado == Resultado.ABANDONADAS) {

                    int a = u.getInteger("abandoned");

                    a++;

                    u.set("abandoned", a);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas ABANDONADAS");                    

                    return true;
        } else {

            throw new IllegalArgumentException("El RESULTADO ingresado no es correcto.");

            return false;
        }

    }

}
