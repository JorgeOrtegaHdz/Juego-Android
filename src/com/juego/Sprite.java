package com.juego;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("DrawAllocation")
public class Sprite {
	
	private static final int BMP_FILAS=4;
	private static final int BMP_COLS=3;
	private int x=0;
	private int y=0;
	private int velocidad_x=5;
	private int velocidad_y=5;
	private VistaJuego vista;
	private Bitmap bmp;
	private int actualFrame=0;
	private int ancho;
	private int alto;
	int[] DIRECCION_ANIMACION={3,1,0,2};
	
	public Sprite(VistaJuego v,Bitmap b){
		this.ancho=b.getWidth()/BMP_COLS;
		this.alto=b.getHeight()/BMP_FILAS;
		this.vista=v;
		this.bmp=b;
		Random aleatorio=new Random();
		x=aleatorio.nextInt(v.getWidth()-ancho);
		y=aleatorio.nextInt(v.getHeight()-alto);
		velocidad_x=aleatorio.nextInt(8*2);
		velocidad_y=aleatorio.nextInt(8*2);
	}
	private void update(){
		if(x>=vista.getWidth()-ancho-velocidad_x || x + velocidad_x < 0){
			velocidad_x=-velocidad_x;
		}
		if(x<=0) velocidad_x=5;
		x+=velocidad_x;
		if(y>=vista.getHeight()-alto-velocidad_y || y + velocidad_y < 0){
			velocidad_y=-velocidad_y;
		}
		if(y<=0) velocidad_y=5;
		y+=velocidad_y;
		actualFrame=++actualFrame % BMP_COLS;
	}
	
	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas c){
		update();
		int srcX=actualFrame*ancho;
		int srcY=getAnimationFila()*alto;
		Rect src=new Rect(srcX, srcY, srcX+ancho, srcY+alto);
		Rect dst=new Rect(x, y, x + ancho, y+alto);
		c.drawBitmap(bmp, src, dst,null);
	}
	
	private int getAnimationFila(){
		double direccion=(Math.atan2(velocidad_x,velocidad_y)/(Math.PI/2)+2);
		int dir=(int)Math.round(direccion)%BMP_FILAS;
		return DIRECCION_ANIMACION[dir];
	}
	
	public boolean isCollition(float x2, float y2){
		return x2>x && x2<x + ancho && y2>y && y2<y + alto;
	}
}
