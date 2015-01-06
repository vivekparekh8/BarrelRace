package com.example.barrelhorse;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
 * Written By: Gaurav Gandhi, Ashish Kalel
 * Description: Shows high score in a listview
 */

public class HighScores extends ActionBarActivity {

	ListView listview;
	ArrayList<Score> scoreBuffer= new ArrayList<Score>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
	
		listview= (ListView)findViewById(R.id.listViewScores);
		scoreBuffer=Score.getScores();
		
		String values[]=new String[scoreBuffer.size()];
		int i=0;
		for (Score  score : scoreBuffer) {
			values[i]=(i+1)+" "+score.getFirstName()+ " "+score.getScoreTime();
			i++;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              R.layout.list_item_layout, R.id.name, values);
	    
		listview.setAdapter(new ArrayAdapter<Score>(this,R.layout.list_item_layout,R.id.name,scoreBuffer){
			
			public View getView(int position, View convertView, ViewGroup parent) {
				
			View v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
			        
			if(position==0)
			{
			    ImageView im= (ImageView)v.findViewById(R.id.trophy);
			    	im.setBackgroundResource(R.drawable.trophy);
			}
			else if(position==1)
			{
				ImageView im= (ImageView)v.findViewById(R.id.trophy);
		    	im.setBackgroundResource(R.drawable.silver);
			}
			else if(position==2)
			{
				ImageView im= (ImageView)v.findViewById(R.id.trophy);
		    	im.setBackgroundResource(R.drawable.bronze);
		    }
			
			TextView name=(TextView)v.findViewById(R.id.name);
	    	TextView rank=(TextView)v.findViewById(R.id.rank);
	    	TextView timer=(TextView)v.findViewById(R.id.time);
			 
			rank.setText((position+1)+"");
			name.setText(scoreBuffer.get(position).getFirstName());
			timer.setText(scoreBuffer.get(position).getScoreTime());
				
			if (position % 2 == 1) {
				    v.setBackgroundColor(Color.parseColor("#8A8A8A"));  
			} 
			else 
			{
					v.setBackgroundColor(Color.parseColor("#ADADAD"));
			}
					return v;
			}
		});
		
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}
	
	
	private static class ViewHolder {
	    public ImageView im;
	    public TextView name,rank,timer;


	    public ViewHolder(View v)
	    {
	    	im=(ImageView)v.findViewById(R.id.trophy);
	    	name=(TextView)v.findViewById(R.id.name);
	    	rank=(TextView)v.findViewById(R.id.rank);
	    	timer=(TextView)v.findViewById(R.id.time);
	    }
	    
	}
}
