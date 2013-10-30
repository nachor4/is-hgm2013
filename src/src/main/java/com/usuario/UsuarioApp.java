package com.usuario;

import java.util.*;
import java.io.*;

import com.usuario.models.User;
import com.usuario.ResultadoPartida;

import java.lang.String;


public class UsuarioApp extends Users
{

    private String id;

    public UsuarioApp(String id) {
		
		connect();
		
		User user = new User();
		
		try{
			user = user.findById(id);
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
		    return user.getString("id");
	}  


    public String getName() {
        //Retorno el nombre del Usuario
        
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return user.getString("name");
    }
   
    
    public int getGanada() {
        //Retorno las partidas ganadas
        
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return user.getInteger("won");
    }  
    
    
    public int getPerdida() {
        //Retorno las partidas Perdidas
        
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return user.getInteger("lost");
    }      
       
    
    public int getAbandonada() {
        //Retorno las partidas Abandonadas
        
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else 
		    return user.getInteger("abandoned");
    }                  


    public boolean saveResult(ResultadoPartida resultado) {
        // Guardo el resultado de una partida
        
        connect();
        
		User u = new User();	
		
		u = u.findById(id);
		
		if (u == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
			
	    } else

          if (resultado == ResultadoPartida.GANO) {

              int g = u.getInteger("won");

              g++;          

              u.set("won", g);

              u.saveIt();
            
              System.out.println("Se han actualizado las partidas GANADAS");

            return true;

        } else if (resultado == ResultadoPartida.PERDIO) {

                    int p = u.getInteger("lost");

                    p++;

                    u.set("lost", p);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas PERDIDAS");                    

                    return true;

        } else if (resultado == ResultadoPartida.ABANDONO) {

                    int a = u.getInteger("abandoned");

                    a++;

                    u.set("abandoned", a);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas ABANDONADAS");                    

                    return true;
        } else {

            System.out.println("El USUARIO ingresado no es correcto!"); 

            return false;
        }

    }

}
