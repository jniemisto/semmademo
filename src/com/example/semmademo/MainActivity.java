package com.example.semmademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
	    showScore();
	}

	private void showScore() {
		Intent intent = getIntent();
	    String message = intent.getStringExtra(GameActivity.SCORE);
	    TextView scoreText = (TextView) findViewById(R.id.scoretext);
	    if (message != null && !message.equals("")) {
	    	scoreText.setText("Selvisit "+message+" sekuntia!");
	    }
	}
	
    public void startGame(View view) {
    	Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
