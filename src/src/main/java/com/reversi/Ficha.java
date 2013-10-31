package com.reversi;

public class Ficha {

	private int x;
	private int y;

	public Ficha(int coordX, int coordY) {
	  x = coordX;
	  y = coordY;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY() {
		return this.y;	
	}
	
	public void setX(int coordX) {
		x = coordX;
	}
	
	public void setY(int coordY) {
		y = coordY;
	}
	

}
