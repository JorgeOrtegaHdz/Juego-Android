package com.juego;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TemporalSprite {
	public float x;
	public float y;
	private Bitmap png;
	private int vida=15;
	private List<TemporalSprite> temporales;
	
	public TemporalSprite(List<TemporalSprite> t, VistaJuego v,
			float x, float y, Bitmap p){
		this.x=Math.min(Math.max(x - p.getWidth() / 2, 0),
				v.getWidth() - p.getWidth());
		this.y=Math.min(Math.max(y - p.getHeight() / 2, 0),
				v.getHeight() - p.getHeight());
		this.png=p;
		this.temporales=t;
		
	}
	public void onDraw(Canvas c){
		update();
		c.drawBitmap(png, x, y, null);
	}
	
	public void update(){
		if(--vida < 1){
			temporales.remove(this);
		}
	}
}
