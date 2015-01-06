/**
 * 
 */
package com.example.barrelhorse;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/*
 * Written By: Gaurav Gandhi, Vivek Parekh
 * Description: Mainthread of the game
 */

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainGamePanel gamePanel;
	boolean firstrun=true;

	long mStartTime, millis, seconds, minutes;
	public static String timer = "00:00:00";
	public static String timerInMillis="";
	public static boolean touchedFence = false;
	public static boolean touchedBarrel = false;
	public static boolean finishedGame = false;
	
	// flag to hold game state
	private boolean running;

	public void setRunning(boolean running) {
		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		mStartTime = SystemClock.uptimeMillis();
		
		timer = "00:00:00";
		touchedFence = false;
		touchedBarrel = false;
		finishedGame = false;
	}

	/**
	 * @return the mStartTime
	 */
	public long getmStartTime() {
		return mStartTime;
	}

	/**
	 * @param mStartTime the mStartTime to set
	 */
	public void setmStartTime() {
		mStartTime = SystemClock.uptimeMillis();
	}
//This method will update the timer
	public void updateTimer() {
		if (mStartTime > 0) {

			if (touchedFence) {
				mStartTime -= 500;
				touchedFence = false;
			}

			millis = SystemClock.uptimeMillis() - mStartTime;
			timerInMillis=millis+"";
			seconds = (int) (millis / 1000);
			minutes = seconds / 60;
			seconds = seconds % 60;
			millis = millis - minutes * 60 * 1000 - seconds * 1000;
		}
		// If game is not started then timer will be 0:00:00
		timer = minutes + ":" + seconds + ":" + millis;

		if (timer.length() > 8)
			timer = timer.substring(0, 8);
	}

	/**
	 * @return the gamePanel
	 */
	public MainGamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * @param gamePanel the gamePanel to set
	 */
	public void setGamePanel(MainGamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * @return the surfaceHolder
	 */
	public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	/**
	 * @param surfaceHolder the surfaceHolder to set
	 */
	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}
	//Thread run method,update values then render them to the screen
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running && !touchedBarrel && !finishedGame) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			updateTimer();
			
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {

					// update game state
					this.gamePanel.update();
					// render state to the screen
					// draws the canvas on the panel
					this.gamePanel.render(canvas);
				}

			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		} // end finally
	}

	/**
	 * @return the firstrun
	 */
	public boolean isFirstrun() {
		return firstrun;
	}

	/**
	 * @param firstrun the firstrun to set
	 */
	public void setFirstrun(boolean firstrun) {
		this.firstrun = firstrun;
	}
}
