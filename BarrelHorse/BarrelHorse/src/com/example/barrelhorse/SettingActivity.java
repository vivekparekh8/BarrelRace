package com.example.barrelhorse;
/*
 * Written By: Gaurav Gandhi, Vivek Parekh
 * Description: Show settings to the user, and update the parameters as selected by the user
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SettingActivity extends ActionBarActivity implements android.widget.AdapterView.OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		Spinner spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
		Spinner spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
		Spinner spinnerSpeed = (Spinner) findViewById(R.id.spinnerSpeed);
		
		spinnerColor.setOnItemSelectedListener(this);
		spinnerSize.setOnItemSelectedListener(this);
		spinnerSpeed.setOnItemSelectedListener(this);
		
	}
	//On submit save the settings
	public void onSub(View v)
	{
		Intent it=new Intent(this,MainActivity.class);
		startActivity(it);
		finish();
	}
	//on reset ,restore default settings
	public void onRes(View v)
	{
		Intent it=new Intent(this,MainActivity.class);
		startActivity(it);
		MainGamePanel.BARREL_COLOR=Color.BLACK;
		MainGamePanel.BARREL_SIZE=1;
		GameActivity.GAME_SPEED=1;
		finish();
	}
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		if(parent.getId()==R.id.spinnerColor)
		{
			String item=parent.getItemAtPosition(pos).toString();
			if(item.equalsIgnoreCase("BLUE"))
			{
				MainGamePanel.BARREL_COLOR=Color.BLUE;				
			}
			else if(item.equalsIgnoreCase("BLACK"))
			{
				MainGamePanel.BARREL_COLOR=Color.BLACK;	
			}
			else if(item.equalsIgnoreCase("RED"))
			{
				MainGamePanel.BARREL_COLOR=Color.RED;	
			}
			else if(item.equalsIgnoreCase("MAGENTA"))
			{
				MainGamePanel.BARREL_COLOR=Color.MAGENTA;	
			}
			
		}
		else if(parent.getId()==R.id.spinnerSize)
		{
			String item=parent.getItemAtPosition(pos).toString();
			if(item.equalsIgnoreCase("SMALL"))
			{
				MainGamePanel.BARREL_SIZE=0.80f;
				
			}
			else if(item.equalsIgnoreCase("MEDIUM"))
			{
				MainGamePanel.BARREL_SIZE=1;
			}
			else if(item.equalsIgnoreCase("LARGE"))
			{
				MainGamePanel.BARREL_SIZE=1.20f;	
			}
			
		}
		else if(parent.getId()==R.id.spinnerSpeed)
		{
			String item=parent.getItemAtPosition(pos).toString();
			if(item.equalsIgnoreCase("NORMAL"))
			{
				GameActivity.GAME_SPEED=1;
				
			}
			else if(item.equalsIgnoreCase("FAST"))
			{
				GameActivity.GAME_SPEED=2;
			}
		
		}
		
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
