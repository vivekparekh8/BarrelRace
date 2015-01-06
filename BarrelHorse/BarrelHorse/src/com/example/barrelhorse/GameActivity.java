package com.example.barrelhorse;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/*
 * Written By: Gaurav Gandhi, Vivek Parekh
 * Description: Activity That Contains Game Panel
 */

public class GameActivity extends Activity implements SensorEventListener {
    /** Called when the activity is first created. */
	
	private static final String TAG = GameActivity.class.getSimpleName();
	private SensorManager sensorManager = null;
	public static long lastUpdate=0;
	public static int x,y;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.finish();
		
	}
	
	public static int x_screenSize, y_screenSize;
	public static int BarrelRadius, BallDiameter;
	public static Vibrator v;
	MainGamePanel gamepannel;
	
	public static int GAME_SPEED=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);        
        if(GAME_SPEED==1)
        	sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
        else
        	sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_FASTEST);	
        
        lastUpdate = System.currentTimeMillis();
        
        x_screenSize=getScreenSize().x;
        y_screenSize=getScreenSize().y;
    
        BarrelRadius=x_screenSize/15;
        BallDiameter=x_screenSize/10;
        
        gamepannel=new MainGamePanel(this);
        setContentView(gamepannel);
        
        v = (Vibrator)this.getSystemService(Service.VIBRATOR_SERVICE);
		
        Log.d(TAG, "View added");
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		sensorManager.unregisterListener(this);
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	
	/*
	 * Written By: Ashish Kalel, Vivek Parekh
	 * Description: Calculates the new value of X and Y based on sensor
	 */

	
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // the values you were calculating originally here were over 10000!
     
			x -=(int) sensorEvent.values[0];
			y +=(int) sensorEvent.values[1];
	   }
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub		
	}    
	public Point getScreenSize()
    {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        Point size = new Point();
        display.getSize(size);
        
        return size;
    }
	/*
	 * Written By: Ashish Kalel, Vivek Parekh
	 * Description: On back button prressed thread is stopped
	 */

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.out.println("BACK PRESSED");
		MainThread.finishedGame=true;
		this.finish();
	}
	
	public static void showNextScreen()
	{
		System.out.println("Here Show Next Screen");
	//	Intent intent =new Intent(this, ScoreActivity.class);
		
	}
	
}