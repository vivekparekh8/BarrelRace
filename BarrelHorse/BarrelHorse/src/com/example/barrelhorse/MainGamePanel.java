/**
 * 
 */
package com.example.barrelhorse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.barrelhorse.model.Ball;
import com.example.barrelhorse.model.Ground;
import com.example.barrelhorse.model.Rectangle;

	/*
	 * Written By: Gaurav Gandhi, Vivek Parekh
	 * Description: Game Logic goes here
	 */

 
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	int i=1;
	private MainThread thread;
	private Ball ball;
	private Ground ground;
	Rectangle bottomrect;
	int ballDiameter=30;
	int xStart, yStart;
	boolean hitBarrel=false;
	boolean GAME_FINISHED=false, GAME_STARTED=false;
	long vibrateTimeFinish=600;
	long vibrateTimeTouch=60;
	long vibrateTimeHit=400;
	long vibrateBarrelCompleted=300;
	
	
	public static int BARREL_COLOR=Color.BLACK;
	public static float BARREL_SIZE=1;
	
	int prev_x=0, prev_y;
	boolean BARREL_1_COMPLETED=false,BARREL_2_COMPLETED=false,BARREL_3_COMPLETED=false;
	
	int [] Barrel1CheckPoints= new int[] {0,0,0,0};
	int barrel1Start=0;
	
	int [] Barrel2CheckPoints= new int[] {0,0,0,0};
	int barrel2Start=0;
	
	int [] Barrel3CheckPoints= new int[] {0,0,0,0};
	int barrel3Start=0;
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		ballDiameter=GameActivity.BallDiameter;
		
		// create ball and load bitmap
		Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		Bitmap grass=BitmapFactory.decodeResource(getResources(), R.drawable.grass);
		Bitmap grass2=BitmapFactory.decodeResource(getResources(), R.drawable.rect_background);
		
		int groundLeft, groundTop, groundRight, groundBottom, groundWidth, groundHeight;
		
		groundLeft=5;
		groundTop= (int)(GameActivity.y_screenSize*0.10);
		groundRight= (int)(GameActivity.x_screenSize-5);
		groundBottom= (int)(GameActivity.y_screenSize*0.80);
		groundHeight=groundBottom-groundTop;
		groundWidth=groundRight-groundLeft;
		
		b=Bitmap.createScaledBitmap(b,ballDiameter,ballDiameter,true);
		grass=Bitmap.createScaledBitmap(grass,groundWidth,groundHeight,true);
		
		ball = new Ball(ballDiameter,ballDiameter,b);
		ground =new Ground(groundLeft,groundTop,groundRight,groundBottom,grass);		

		xStart=ground.getLeft()+ground.getWidth()/2-ballDiameter/2;
		yStart=ground.getBottom();
		
		int rectLeft=xStart-ballDiameter/2;
		int rectTop= groundBottom;
		int rectRight= xStart+ballDiameter+ballDiameter/2;			
		int rectBottom= rectTop+ballDiameter;
		
		int rectHeight=rectBottom-rectTop;
		int rectWidth=rectRight-rectLeft;
		
		grass2=Bitmap.createScaledBitmap(grass2,rectWidth,rectHeight,true);
		
		bottomrect =new Rectangle(rectLeft,rectTop,rectRight,rectBottom,grass2);
		
		GameActivity.x=groundLeft+groundWidth/2-ballDiameter/2;
		GameActivity.y=groundBottom;

		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		System.out.println("Initializing thread");
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	//On surface created initialize game screen
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initializeGraphics(this.getHolder());
		System.out.println("Initializing graphics");
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
		this.setFocusable(false);
		
	}
	
	//on tap  on the screen, start the thread
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		if(!GAME_STARTED)
		{	thread.setRunning(true);
			thread.start();
			GAME_STARTED=true;
		}
		return super.onTouchEvent(event);
	}
	//initial graphics to shown before starting the thread
	public void initializeGraphics(SurfaceHolder sh)
	{
		Canvas canvas=null;
		canvas= sh.lockCanvas();
		update();
		render(canvas);
		bottomrect.drawText(canvas);
		sh.unlockCanvasAndPost(canvas);
	}
	
	//rendering the screen
	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		ground.draw(canvas);
	    bottomrect.draw(canvas);
		ball.draw(canvas);		
	}

	/*
	 * Written By: Gaurav Gandhi, Ashish Kalel, Vivek Parekh
	 * Description: Checking collisions, updating the next values where ball will be drawn next
	 */

	
	//Calculate sensor values,boundary check, collision with barrel check, 
	public void update() {
		
		int x=GameActivity.x, y=GameActivity.y;

		//Check Left Bound
		if(x<ground.getLeft())
			{
			GameActivity.x=ground.getLeft();
			MainThread.touchedFence=true;
			GameActivity.v.vibrate(vibrateTimeTouch);
			System.out.println("X: "+x);		
			}
		//Check top bound
		if(y<ground.getTop())
			{
				GameActivity.y=ground.getTop();
				MainThread.touchedFence=true;
				GameActivity.v.vibrate(vibrateTimeTouch);
			}
		//check right bbound
		if(x+ballDiameter>ground.getRight())
			{
				GameActivity.x=ground.getRight()-ballDiameter;
				MainThread.touchedFence=true;
				GameActivity.v.vibrate(vibrateTimeTouch);
			}
		
		if(y+ballDiameter>ground.getBottom())
		{
			if(x<bottomrect.getLeft()&& !(ball.getY()+ballDiameter>ground.getBottom()))
			{
				GameActivity.y=ground.getBottom()-ballDiameter;
				MainThread.touchedFence=true;
				GameActivity.v.vibrate(vibrateTimeTouch);
			}
			else if(x+ballDiameter>bottomrect.getRight() && !(ball.getY()+ballDiameter>ground.getBottom()))
			{
				GameActivity.y=ground.getBottom()-ballDiameter;
				MainThread.touchedFence=true;
				GameActivity.v.vibrate(vibrateTimeTouch);
			}
			else
			{
				if(x<bottomrect.getLeft())
					GameActivity.x=bottomrect.getLeft();
				if(x+ballDiameter>bottomrect.getRight())
					GameActivity.x=bottomrect.getRight()-ballDiameter;
				if(y+ballDiameter>bottomrect.getBottom())
					GameActivity.y=bottomrect.getBottom()-ballDiameter;
			}
		}
		
		int barrel[][]={{ground.b1.getCenter_x(),ground.b1.getCenter_y()},
						 {ground.b2.getCenter_x(),ground.b2.getCenter_y()},
						 {ground.b3.getCenter_x(),ground.b3.getCenter_y()}};
		
		int newballCenter_x=GameActivity.x+GameActivity.BallDiameter/2, newballCenter_y=GameActivity.y+GameActivity.BallDiameter/2;
		//Verify if ball hit the barrel
		for (int i = 0; i < 3; i++) 
		{

			if ((newballCenter_x - barrel[i][0]) * (newballCenter_x - barrel[i][0])
					+ (newballCenter_y - barrel[i][1]) * (newballCenter_y - barrel[i][1]) <= (GameActivity.BallDiameter/2 + GameActivity.BarrelRadius)
					* (GameActivity.BallDiameter/2 + GameActivity.BarrelRadius)) {
				
					hitBarrel = true;
					System.out.println("BARREL HIT.................");					
					GameActivity.x=ball.getX();
					GameActivity.y=ball.getY();
					GameActivity.v.vibrate(vibrateTimeHit);
					MainThread.touchedBarrel=true;
					
					
					Intent intent =new Intent(this.getContext(), GameOverActivity.class);
					this.getContext().startActivity(intent);
					
					break;
			}
			else
			{
				
				
			}

		}
		ball.update(GameActivity.x, GameActivity.y);
		checkifBarrel1Completed();
		checkifBarrel2Completed();
		checkifBarrel3Completed();
		
		if(isBallInStart())
		{
			if(BARREL_1_COMPLETED && BARREL_2_COMPLETED && BARREL_3_COMPLETED)
			{
				//GAME FINISHED SUCCESSFULLY
				MainThread.finishedGame=true;
				GAME_FINISHED=true;
				this.setFocusable(false);
				System.out.println("Game Completed in: "+MainThread.timer);
			
				GameActivity.v.vibrate(vibrateTimeFinish);
				//GameActivity.showNextScreen();
				Intent intent =new Intent(this.getContext(), ScoreActivity.class);
				this.getContext().startActivity(intent);
			}
		}
	}
	
	/*
	 * Written By: Gaurav Gandhi, Ashish Kalel, Vivek Parekh
	 * Description: Check if barrel 1 is completed, all checkpoints have been crossed
	 */

	public void checkifBarrel1Completed()
	{
		if(BARREL_1_COMPLETED==true)
			return;
		int ball_center_x=ball.ballCenter_x;
		int ball_center_y=ball.ballCenter_y;
		
		int barrelCenter_x=ground.b1.getCenter_x();
		int barrelCenter_y=ground.b1.getCenter_y();
		int barrelRadius=ground.b1.getRadius();
		
		//chk1
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y>=barrelCenter_y+barrelRadius))
			{
				Barrel1CheckPoints[0]=1;
				if(Barrel1CheckPoints[1]!=0 && Barrel1CheckPoints[3]!=0 )
				{
					Barrel1CheckPoints[0]++;
				}
				
				if(isThisStart())
					{
						barrel1Start=0;
					}
				
			}
		//chk 3
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y<=barrelCenter_y-barrelRadius))
			{
				Barrel1CheckPoints[2]++;
				if(isThisStart())
					barrel1Start=2;
			}
		//chk 4
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && (ball_center_x>=barrelCenter_x+barrelRadius))
			{
			 	Barrel1CheckPoints[3]++;
				
				if(isThisStart())
					barrel1Start=3;
			}
		//chk 2
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && ball_center_x<=barrelCenter_x-barrelRadius)
			{
				
				Barrel1CheckPoints[1]++;
				
				if(isThisStart())
					barrel1Start=2;
			}
		boolean completed=false;
		
		if(Barrel1CheckPoints[barrel1Start]>=2)
		{
			for(int i=0;i<4;i++)
				{
					if(i==barrel1Start)
						continue;
					else
						if(Barrel1CheckPoints[i]==0)
						{
							completed=false;
							break;
						}
						else
						{
							completed=true;
						}
				}
		}
		else
			completed=false;
		
		if(completed==true)
			{
				ground.b1.setMyColor(Color.GREEN);
				BARREL_1_COMPLETED=true;
				GameActivity.v.vibrate(vibrateBarrelCompleted);
			}
		}
	//Is this starting checkpoint for barrel
	public boolean isThisStart()
	{
		for(int i=0;i<4;i++)
		{
			if(Barrel1CheckPoints[i]==0)
				continue;
			else
			{
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Written By: Gaurav Gandhi, Ashish Kalel, Vivek Parekh
	 * Description: Check if barrel 2 is completed, all checkpoints have been crossed
	 */

	
	public void checkifBarrel2Completed()
	{
		if(BARREL_2_COMPLETED==true)
			return;

		
		int ball_center_x=ball.ballCenter_x;
		int ball_center_y=ball.ballCenter_y;
		
		int barrelCenter_x=ground.b2.getCenter_x();
		int barrelCenter_y=ground.b2.getCenter_y();
		int barrelRadius=ground.b2.getRadius();
		
		//chk1
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y>=barrelCenter_y+barrelRadius))
			{
				Barrel2CheckPoints[0]=1;
				if(Barrel2CheckPoints[1]!=0 && Barrel2CheckPoints[3]!=0 )
				{
					Barrel2CheckPoints[0]++;
				}
				
				if(isThisStartb2())
					{
						barrel2Start=0;
					
					}
				
			}
		//chk 3
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y<=barrelCenter_y-barrelRadius))
			{
				Barrel2CheckPoints[2]++;
				if(isThisStart())
					barrel2Start=2;
			}
		//chk 4
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && (ball_center_x>=barrelCenter_x+barrelRadius))
			{
			
			
			if(isThisStartb2())
			{
				barrel2Start=3;
				
			}
		
		Barrel2CheckPoints[3]=1;
			
			if(Barrel2CheckPoints[0]!=0 && Barrel2CheckPoints[2]!=0 )
			{
				Barrel2CheckPoints[3]++;
			}	
				
			}
		//chk 2
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && ball_center_x<=barrelCenter_x-barrelRadius)
			{
				
				Barrel2CheckPoints[1]++;
				
				if(isThisStartb2())
					barrel2Start=2;
			}
		boolean completed=false;
		
		if(Barrel2CheckPoints[barrel2Start]>=2)
		{
			for(int i=0;i<4;i++)
				{
					if(i==barrel2Start)
						continue;
					else
						if(Barrel2CheckPoints[i]==0)
						{
							completed=false;
							break;
						}
						else
						{
							completed=true;
						}
				}
		}
		else
			completed=false;
		
		if(completed==true)
			{
				ground.b2.setMyColor(Color.GREEN);
				BARREL_2_COMPLETED=true;
				GameActivity.v.vibrate(vibrateBarrelCompleted);
			}
		
		
	}
	//Is the current checkpoint starting checkpoint for barrel2?
	public boolean isThisStartb2()
	{
		for(int i=0;i<4;i++)
		{
			if(Barrel2CheckPoints[i]==0)
				continue;
			else
			{
				return false;
			}
		}
		return true;
	}	

	/*
	 * Written By: Gaurav Gandhi, Ashish Kalel, Vivek Parekh
	 * Description: Check if barrel 3 is completed, all checkpoints have been crossed
	 */

	public void checkifBarrel3Completed()
	{
		if(BARREL_3_COMPLETED==true)
			return;

		
		int ball_center_x=ball.ballCenter_x;
		int ball_center_y=ball.ballCenter_y;
		
		int barrelCenter_x=ground.b3.getCenter_x();
		int barrelCenter_y=ground.b3.getCenter_y();
		int barrelRadius=ground.b3.getRadius();
		
		//chk1
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y>=barrelCenter_y+barrelRadius))
			{
				Barrel3CheckPoints[0]=1;
				if(Barrel3CheckPoints[1]!=0 && Barrel3CheckPoints[3]!=0 )
				{
					Barrel3CheckPoints[0]++;
				}
				
				if(isThisStartb3())
					{
						barrel3Start=0;
						
					}
				
			}
		//chk 3
		if((ball_center_x>=barrelCenter_x-10 && ball_center_x<=barrelCenter_x+10 ) && (ball_center_y<=barrelCenter_y-barrelRadius))
			{
				Barrel3CheckPoints[2]++;
				if(isThisStart())
					barrel3Start=2;
			}
		//chk 4
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && (ball_center_x>=barrelCenter_x+barrelRadius))
			{
			
			Barrel3CheckPoints[3]++;
			if(isThisStart())
				barrel3Start=3;		
				
			}
		//chk 2
		if((ball_center_y>=barrelCenter_y-10 && ball_center_y<=barrelCenter_y+10 ) && ball_center_x<=barrelCenter_x-barrelRadius)
			{
			
			if(isThisStartb3())
			{
				barrel3Start=1;
			}
			Barrel3CheckPoints[3]=1;
				
			if(Barrel3CheckPoints[0]!=0 && Barrel3CheckPoints[2]!=0 )
				{
					Barrel3CheckPoints[1]++;
				}
			}

		boolean completed=false;
		
		if(Barrel3CheckPoints[barrel3Start]>=2)
		{
			for(int i=0;i<4;i++)
				{
					if(i==barrel3Start)
						continue;
					else
						if(Barrel3CheckPoints[i]==0)
						{
							completed=false;
							break;
						}
						else
						{
							completed=true;
						}
				}
		}
		else
			completed=false;
		
		if(completed==true)
			{
				ground.b3.setMyColor(Color.GREEN);
				BARREL_3_COMPLETED=true;
				GameActivity.v.vibrate(vibrateBarrelCompleted);
			}
		}
	
	public boolean isThisStartb3()
	{
		for(int i=0;i<4;i++)
		{
			if(Barrel3CheckPoints[i]==0)
				continue;
			else
			{
				return false;
			}
		}
		return true;
	}	
	//Is the ball back in start position?
	public boolean isBallInStart()
	{
		int ballTop=ball.getY();
		int bottomRectTop=bottomrect.getTop();
		if(ballTop>=bottomRectTop)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}