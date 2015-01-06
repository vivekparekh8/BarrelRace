package com.example.barrelhorse;
/*
 * Written By: Ashish Kalel
 * Description: Show how to play
 */

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowInfo extends ActionBarActivity {

	TextView text;
	String instructions="<h2 align='center'>Barrel Race game</h2><p>A barrel race is a rodeo event in which the rider starts at a gate and must ride completely around three barrels. That is, he or she must circle each of the barrels. The objective is to get the fastest time without knocking over any of the barrels.</p><h4>Rules:</h4><p>1.  Your “horse” (in our case, ball) starts out at the gate, & you must maneuver him around the three barrels & back through the gate. You do this by tilting your device one way or another.</p><p>2.  Touching any of the barrels means you lose, and must start over.</p><p>3.  Hitting the fences around the course will add 5 seconds to your time, but you get to continue the race.</p><p>4.  The timer starts when you touch the horse and ends when your horse is entirely through the gate.</p>";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_info);
		text=(TextView) findViewById(R.id.info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_info, menu);
		
		text.setText(Html.fromHtml(instructions));
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
}
