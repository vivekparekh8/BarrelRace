package com.example.barrelhorse;

/*
 * Written By: Gaurav Gandhi, Vivek Parekh
 * Description: Get the user score and save them to the file
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScoreActivity extends Activity{
	TextView score;
	EditText nameOfScorer;
	Button submitScore;
	EditText fnameText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		submitScore = (Button) findViewById(R.id.button1);
		submitScore.setEnabled(false);

		fnameText = (EditText) findViewById(R.id.nameOfScorer);
		
		
		score=(TextView)findViewById(R.id.scoreDisplay);
		score.setText(MainThread.timer);
		
	fnameText.addTextChangedListener(new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (s.length() == 0) {
				submitScore.setEnabled(false);
			} else {
				submitScore.setEnabled(true);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

	});
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void PlayGame(View view)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void saveScore(View view)
	{
		EditText fnameText = (EditText) findViewById(R.id.nameOfScorer);
		String fname = fnameText.getText().toString().trim();	
		String score=MainThread.timerInMillis;
				
		Score c = new Score(fname, score);
		c.saveScore();
		finish();
		
		Intent intent = new Intent(this, HighScores.class);
		startActivity(intent);
		
		
	}
	

}
