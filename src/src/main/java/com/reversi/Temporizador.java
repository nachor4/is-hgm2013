package com.reversi;

import java.io.IOException;

import java.util.Timer;

import java.util.TimerTask;

public class Temporizador {
	
	Timer timer = new Timer();
	
	public int segundos;
	
	public boolean frozen;
	
	class MiTarea extends TimerTask {
		
		public void run() {
			segundos--;
			}
			
		public void Start(int pSeg) throws Exception {
			frozen = false;
			timer.schedule(new MiTarea(), pSeg*1000, 0);
			}
		
		public void Stop() {
			frozen = true;			
			}
			
		public void Reset(int pSeg) {
			frozen = true;
			segundos = pSeg;
			}
			
			
			
		}
	
	
	
	
}
