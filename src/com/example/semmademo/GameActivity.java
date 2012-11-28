package com.example.semmademo;

import com.example.semmademo.GameView.GameState;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity implements OnGameEndEventListener {
	public final static String SCORE = "com.example.semmademo.SCORE";
	private GameView mGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.game_layout);
	    mGameView = (GameView) findViewById(R.id.gameview);
	    mGameView.setGameEndEventListener(this);
	    mGameView.setState(GameState.RUNNING);
	}

	@Override
	protected void onPause() {
		mGameView.setState(GameState.PAUSED);
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		mGameView.setState(GameState.PAUSED);
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		mGameView.setState(GameState.RUNNING);
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		mGameView.setState(GameState.RUNNING);
		super.onResume();
	}
	
	public void onGameEnd(double score) {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra(SCORE, String.valueOf(score));
    	startActivity(intent);	  
	}
	
}
