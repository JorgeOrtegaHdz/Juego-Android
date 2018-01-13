package com.juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.juego.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VistaJuego extends SurfaceView{
	private SurfaceHolder contenedor;
	private Bitmap PNG;
	private LoopProceso juegoLoop;
	//private Sprite duende;
	private List<Sprite> sprites=new ArrayList<Sprite>();
	private List<TemporalSprite> temporal=new ArrayList<TemporalSprite>();
	private long ultimoClick;
	
	public VistaJuego(Context contexto){
		super(contexto);
		juegoLoop=new LoopProceso(this);
		contenedor = getHolder();
	    contenedor.addCallback(new SurfaceHolder.Callback(){
	    	
	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder){
	    	/*boolean r=true;
	    	juegoLoop.setEjecution(false);
	    	while(r){
	    		try{
	    			juegoLoop.join();
	    			r=false;
	    		}catch(InterruptedException e){}
	    	}*/
	    }
	    @SuppressLint("WrongCall")
	    @Override
		public void surfaceCreated(SurfaceHolder holder){
	    	creaSprite();
	    	juegoLoop.setEjecution(true);
	    	juegoLoop.start();
	    	    	
	    }
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}
	});
	PNG = BitmapFactory.decodeResource(getResources(),R.drawable.manchas);
	//duende=new Sprite(this,bmp);
}
	private void creaSprite(){
		sprites.add(creaSprite(R.drawable.cat_gray));
		sprites.add(creaSprite(R.drawable.sprite_guero));
		sprites.add(creaSprite(R.drawable.sprite_dark));
		sprites.add(creaSprite(R.drawable.cupu_blanco));
		sprites.add(creaSprite(R.drawable.cat_black));
		sprites.add(creaSprite(R.drawable.cat_white));
		sprites.add(creaSprite(R.drawable.sprite_azul));
	}
	
	private Sprite creaSprite(int recurso){
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), recurso);
		return new Sprite(this,bmp);
	}
	
	@SuppressLint("WrongCall")
	protected void onDraw(Canvas c){
		c.drawColor(Color.WHITE);
		//duende.onDraw(c);
		for(int i=temporal.size()-1;i>=0; i--){
			temporal.get(i).onDraw(c);
		}
		for(Sprite sprite:sprites){
			sprite.onDraw(c);
		}
	}
	public boolean onTouchEvent(MotionEvent e){
		if(System.currentTimeMillis()-ultimoClick > 500){
			ultimoClick=System.currentTimeMillis();
			synchronized(getHolder()){
				for(int i=sprites.size()-1;i>=0;i--){
					Sprite sprite=sprites.get(i);
					if(sprite.isCollition(e.getX(),e.getY())){
						sprites.remove(sprite);
						temporal.add(new TemporalSprite(temporal,this,
								e.getX(),e.getY(),PNG));
						break;
					}
				}
			}
		}
		return true;
	}

}
