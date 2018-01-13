package com.juego;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class LoopProceso extends Thread{
	private static final int FPS=10;
	private VistaJuego vista;
	private boolean ejecucion=false;
	
	public LoopProceso(VistaJuego v){
		this.vista=v;
	}
	
	public void setEjecution(boolean r){
		ejecucion=r;
	}
	
	
	public void run(){
		long ticksPS=1000/FPS;
		long Tiempoinicial;
		long retardo;
		while(ejecucion){
			Canvas c=null;
			Tiempoinicial=System.currentTimeMillis();
			try{
				c=vista.getHolder().lockCanvas();
				synchronized(vista.getHolder()){
					vista.onDraw(c);
				}
			}finally{
				if(c!=null){
					vista.getHolder().unlockCanvasAndPost(c);
				}
			}
			retardo=ticksPS-(System.currentTimeMillis()-Tiempoinicial);
			try{
				if(retardo > 0)
					sleep(retardo);
				else
					sleep(10);
			}catch(Exception e){}
		}
	}
}
