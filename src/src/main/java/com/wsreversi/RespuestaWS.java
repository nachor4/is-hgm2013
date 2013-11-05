package com.wsreversi;

//GSON: Librería JSON (comunicacion con los websocket clients)
import com.google.gson.Gson;

//Librerías Java
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;


/**
 * 
 */

public class RespuestaWS
{
	private HashMap<String, Object> map = new HashMap<String, Object>(); //Id Partida, Juego
	
	/**
	 * 
	 */
	public RespuestaWS(String type){
		map.put("operacion", type);
	}

	public String toString(){
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	public void addAttr(String name, Object data){
		map.put(name,data);
	}
}
