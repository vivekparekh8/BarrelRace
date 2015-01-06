/**
 * 
 */
package com.example.barrelhorse.model;

import com.example.barrelhorse.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/*
 * Written By: Gaurav Gandhi
 * Description: Class for drawing ball
 */


public class Ball {

	//Circle.....draw
		
	private Bitmap bitmap,b;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private boolean touched;	
	
	private float xVelocity, yVelocity;
	
	public int ballCenter_x, ballCenter_y;
	
	public Ball(int x, int y,Bitmap b) {
		this.x = x;
		this.y = y;
		this.b=b;
		
		ballCenter_x=x+15;
		ballCenter_y=y+15;
	
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	

	//Draw circle here
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(b,x,y,null);
		
		
		Paint timerPaint = new Paint();
		timerPaint.setColor(Color.CYAN);
		timerPaint.setTextAlign(Paint.Align.LEFT);
		timerPaint.setTextScaleX((float) 2.2);
		timerPaint.setTextSize(GameActivity.BallDiameter/2);
		timerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		timerPaint.setStrokeWidth(2);
		timerPaint.setAntiAlias(true);
		canvas.drawText(MainThread.timer, 10, 30, timerPaint);		
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	
	// Update X Y, on the basis of sensor values 
	public void update(int x, int y) {
		if (!touched) {
			this.x=x;
			this.y=y;
			
			ballCenter_x=x+15;
			ballCenter_y=y+15;
			
		}
	}
	
	
}
