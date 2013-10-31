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
		return cantBlancas;
	}
	
	public int getNegras() {
		return cantNegras;
	}
	
	public Ficha [] getModificaciones() {
		return modificaciones;
	}
	
	public void setCantBlancas(int blancas) {
		cantBlancas = blancas;
	}
	
	public void setCantNegras(int negras) {
		cantNegras = negras;
	}


}
