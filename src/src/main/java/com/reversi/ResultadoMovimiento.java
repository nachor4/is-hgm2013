package com.reversi;

import com.reversi.Ficha;
import java.util.*;

public class ResultadoMovimiento {

private String usuario;
private int cantBlancas;
private int cantNegras;
private ArrayList<Ficha> modificaciones = new ArrayList<Ficha>();



	public ResultadoMovimiento(String idUsuario, int blancas, int negras) {
		usuario = idUsuario;
		cantBlancas = blancas;
		cantNegras = negras;
	}
	
	public ResultadoMovimiento(final ResultadoMovimiento result) {
		cantBlancas = result.cantBlancas;
		cantNegras = result.cantNegras;
		modificaciones = result.modificaciones;
	}
		
	public int getBlancas() {
		//System.out.println("Cant Blancas despues del movimiento: " +cantBlancas); 
		return cantBlancas;
	}
	
	public int getNegras() {
		//System.out.println("Cant Negras despues del movimiento: " +cantNegras);
		return cantNegras;
	}
	
	public ArrayList<Ficha> getModificaciones() {
		System.out.println("Estas son las fichas modificadas:\n");
		for(int x = 0; x < modificaciones.size(); x++) {
		System.out.println(" ( "+modificaciones.get(x).getX()+", "+modificaciones.get(x).getY()+") ");	
		}
		return modificaciones;
	}
	
	public void setCantBlancas(int blancas) {
		cantBlancas = blancas;
	}
	
	public void setCantNegras(int negras) {
		cantNegras = negras;
	}


}
