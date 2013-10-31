package com.usuario;

import java.util.*;
import java.io.*;

import com.usuario.models.User;
import com.usuario.ResultadoPartida;

import java.lang.String;


public class UsuarioApp extends Users
{

    private String id;

    public UsuarioApp(String uid) {
		
		connect();		
		User user = new User();
		user = user.findById(uid);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido.");
		}else{
			id = uid;
		}
    }  
    
    
	public String getID() {
		// Retorna el ID del Usuario
		
		connect();        
		User user = new User();	
		user = user.findById(id);	
		
		if (user == null){		
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en getID.");
		}else 	
		    return user.getString("id");
	}  


    public String getName() {
        //Retorno el nombre del Usuario
        
        connect();
		User user = new User();	
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en getName.");
		}else 
		    return user.getString("name");
    }
   
    
    public int getGanada() {
        //Retorno la cantidad de partidas ganadas
        
        connect();
		User user = new User();	
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en getGanada.");
		}else 
		    return user.getInteger("won");
    }  
    
    
    public int getPerdida() {
        //Retorno la cantidad de partidas Perdidas
        
        connect();
		User user = new User();	
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en getPerdida.");
		}else 
		    return user.getInteger("lost");
    }      
       
    
    public int getAbandonada() {
        //Retorno la cantidad de partidas Abandonadas
        
        connect();
		User user = new User();	
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en getAbandonada.");
		}else 
		    return user.getInteger("abandoned");
    }                  


    public boolean saveResult(ResultadoPartida resultado) {
        // Actualizo el resultado de una partida
        
        boolean resul;
        
        connect();
		User u = new User();	
		u = u.findById(id);			
 
		if (u == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO invalido en saveResult.");
			
	    }else{	
							    
			switch (resultado){
  	
				case GANO:
					int g = u.getInteger("won");
					g++;          
					u.set("won", g);
					u.saveIt();
              
					System.out.println("Se han actualizado las partidas GANADAS");
					resul = true;
				break;		  
		  
				case PERDIO:
					int p = u.getInteger("lost");
					p++;
					u.set("lost", p);
					u.saveIt();
                    
					System.out.println("Se han actualizado las partidas PERDIDAS"); 
					resul = true;		
				break;
		  
				case ABANDONO:
					int a = u.getInteger("abandoned");
					a++;
					u.set("abandoned", a);
					u.saveIt();
                    
					System.out.println("Se han actualizado las partidas ABANDONADAS"); 
					resul = true;		
				break;
				
				default:
					System.out.println("El RESULTADO ingresado no es correcto!"); 
					resul = false;	
				break;				
			}//end switch

			return resul;
			
		}//end else

    }//end saveResult

}
