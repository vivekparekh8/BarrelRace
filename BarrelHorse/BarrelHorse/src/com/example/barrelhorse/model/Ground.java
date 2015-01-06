/**
 * 
 */
package com.example.barrelhorse.model;

/*
 * Written By: Ashish Kalel
 * Description: Drawing ground on the surface view
 */

import com.example.barrelhorse.*;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ground {

	//Ground Draw.....draw
		
	public Barrel b1,b2,b3;		//3 barrels
	private int startPoint_x, startPoint_y;
	Bitmap b;
	private int left,right;
	private int top,bottom;
	
	private int width;
	private int height;
	
	
	
	public Ground(int left,int top, int right, int bottom, Bitmap b) {
		this.left=left;
		this.right=right;
		this.top=top;
		this.bottom=bottom;
		this.b=b;
		this.width=this.right-this.left;
		this.height=this.bottom-this.top;
	
		System.out.println(this.left +"......"+this.width);
		
		b1=new Barrel((this.left+this.width)/2-GameActivity.BarrelRadius,(int) (this.top+height*0.20));
		
		System.out.println("Barrel: "+b1.getCenter_x() +"......"+b1.getCenter_y());
		
		b2=new Barrel(b1.getCenter_x()-10-2*b1.getRadius()-2*b1.getRadius(), (int) (b1.getCenter_y()+0.35*this.height));
		b3=new Barrel(b1.getCenter_x()+10+2*b1.getRadius(),(int) (b1.getCenter_y()+0.35*this.height));
	
	}
	
	public void draw(Canvas canvas) {
		//canvas.drawBitmap(b,x,y,null);
			
		canvas.drawBitmap(b, left, top, null);
		
		Paint p1 = new Paint();
		Paint p2 = new Paint();
		Paint p3 = new Paint();
	
		p1.setAntiAlias(true);
		p1.setColor(b1.getMyColor());
		p1.setStyle(Paint.Style.STROKE); 
		p1.setStrokeWidth(4.5f);
	
		p2.setAntiAlias(true);
		p2.setColor(b2.getMyColor());
		p2.setStyle(Paint.Style.STROKE); 
		p2.setStrokeWidth(4.5f);
		
		p3.setAntiAlias(true);
		p3.setColor(b3.getMyColor());
		p3.setStyle(Paint.Style.STROKE); 
		p3.setStrokeWidth(4.5f);
		
		
		// opacity
		//p.setAlpha(0x80); //
		
		canvas.drawCircle(b1.getCenter_x(), b1.getCenter_y(), b1.getRadius(), p1);
		canvas.drawCircle(b2.getCenter_x(), b2.getCenter_y(), b2.getRadius(), p2);
		canvas.drawCircle(b3.getCenter_x(), b3.getCenter_y(), b3.getRadius(), p3);
		
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
