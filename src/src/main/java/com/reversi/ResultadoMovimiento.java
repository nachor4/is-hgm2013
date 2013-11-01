package com.reversi;

import com.reversi.Ficha;

public class ResultadoMovimiento {

private String usuario;
private int cantBlancas;
private int cantNegras;
private Ficha modificaciones [] = new Ficha [64];


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
		System.out.println("Cant Blancas despues del movimiento: " +cantBlancas +" \n");
		return cantBlancas;
	}
	
	public int getNegras() {
		System.out.println("Cant Negras despues del movimiento: " +cantNegras +" \n");
		return cantNegras;
	}
	
	public Ficha [] getModificaciones() {
		System.out.println("Estas son las fichas modificadas:\n");
		for(int x = 0; x < modificaciones.length; x++) {
		System.out.println(" ( "+modificaciones[x].getX()+", "+modificaciones[x].getY()+") ");	
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
