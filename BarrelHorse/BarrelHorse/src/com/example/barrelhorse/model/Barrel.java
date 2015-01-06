package com.example.barrelhorse.model;

/*
 * Written By: Gaurav Gandhi
 * Description: Class for Barrel
 */


import com.example.barrelhorse.*;

import android.graphics.Color;

public class Barrel {
	
	private int x,y;
	private int center_x, center_y;
	public  int radius=20;
	private int myColor=MainGamePanel.BARREL_COLOR;
	/**
	 * @return the x
	 */
	public Barrel(int x, int y)
	{
		this.x=x;
		this.y=y;
		radius=(int)(GameActivity.BarrelRadius*MainGamePanel.BARREL_SIZE);
	
		center_x=this.x+radius;
		center_y=this.y+radius;
	}
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}


	/**
	 * @return the center_x
	 */
	public int getCenter_x() {
		return center_x;
	}


	/**
	 * @param center_x the center_x to set
	 */
	public void setCenter_x(int center_x) {
		this.center_x = center_x;
	}


	/**
	 * @return the center_y
	 */
	public int getCenter_y() {
		return center_y;
	}


	/**
	 * @param center_y the center_y to set
	 */
	public void setCenter_y(int center_y) {
		this.center_y = center_y;
	}


	/**
	 * @return the myColor
	 */
	public int getMyColor() {
		return myColor;
	}


	/**
	 * @param myColor the myColor to set
	 */
	public void setMyColor(int myColor) {
		this.myColor = myColor;
	}
	

}
