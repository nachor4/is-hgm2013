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
			throw new IllegalArgumentException("Ingreso un ID de USUARIO no válido.");
		}else 
		    System.out.println("El ID del Usuario es: "+id);
		    return user.getString("id");
	}  


    public String getName() {
        //Retorno el nombre del Usuario
        connect();
        
		User user = new User();	
		
		user = user.findById(id);
		
		if (user == null){
			throw new IllegalArgumentException("Ingreso un ID de USUARIO no válido.");
		}else 
		    //System.out.println("El NOMBRE del Usuario es: "+name);
		    return user.getString("name");
    }
    
/*    
    
    public int getGan() {
        //Retorno las partidas Ganadas
		User user = new User();	
		user = user.findById(id);
        return use.get("won");
    }    
    
    public int getPer() {
        //Retorno las partidas Perdidas

        return lost;
    }  
    
    public int getAba() {
        //Retorno las partidas Abandonadas

        return abandoned;
    }            


    public boolean saveResult(Resultado resultado) {
        // Guardo el resultado de una partida

        UsuarioApp u = new UsuarioApp(id);

        if (resultado == Resultado.GANADAS) {

            int g = u.getGan();

            g++;

            u.set("won", g);

            u.saveIt();
            
            System.out.println("Se han actualizado las partidas GANADAS");

            return true;

        } else if (resultado == Resultado.PERDIDAS) {

                    int p = u.getPer();

                    p++;

                    u.set("lost", p);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas PERDIDAS");                    

                    return true;

        } else if (resultado == Resultado.ABANDONADAS) {

                    int a = u.getAba();

                    a++;

                    u.set("abandoned", a);

                    u.saveIt();
                    
                    System.out.println("Se han actualizado las partidas ABANDONADAS");                    

                    return true;
        } else {

            System.out.println( "\n" + "el resultado ingresado no es correcto ");

            return false;
        }

    }
*/

}
