/**
 * 
 */
package com.example.barrelhorse.model;

import com.example.barrelhorse.GameActivity;
import com.example.barrelhorse.MainGamePanel;
import com.example.barrelhorse.MainThread;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Written By: Vivek Parekh
 * Description: Class for drawing gate rectangle
 */

public class Rectangle {

	//Ground Draw.....draw
		
	private int startPoint_x, startPoint_y;
	Bitmap b;
	private int left,right;
	private int top,bottom;
	
	private int width;
	private int height;
	
	
	
	public Rectangle(int left,int top, int right, int bottom, Bitmap b) {
		this.left=left;
		this.right=right;
		this.top=top;
		this.bottom=bottom;
		this.b=b;
		this.width=this.right-this.left;
		this.height=this.bottom-this.top;
	}
	
	public void draw(Canvas canvas) {
		//canvas.drawBitmap(b,x,y,null);
			
		canvas.drawBitmap(b, left, top, null);
		
		Paint p = new Paint();
	
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		p.setStyle(Paint.Style.STROKE); 
		p.setStrokeWidth(4.5f);
		// opacity
		//p.setAlpha(0x80); //
	}

	public void drawText(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextScaleX((float) 1);
		paint.setTextSize(GameActivity.BallDiameter/2);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		canvas.drawText("Tap On Screen To Play",GameActivity.x_screenSize/2,bottom+50 ,paint);
	}
	/**
	 * @return the startPoint_x
	 */
	public int getStartPoint_x() {
		return startPoint_x;
	}

	/**
	 * @param startPoint_x the startPoint_x to set
	 */
	public void setStartPoint_x(int startPoint_x) {
		this.startPoint_x = startPoint_x;
	}

	/**
	 * @return the startPoint_y
	 */
	public int getStartPoint_y() {
		return startPoint_y;
	}

	/**
	 * @param startPoint_y the startPoint_y to set
	 */
	public void setStartPoint_y(int startPoint_y) {
		this.startPoint_y = startPoint_y;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * @return the bottom
	 */
	public int getBottom() {
		return bottom;
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	
	// Update X Y, on the basis of sensor values 
	
		
}
