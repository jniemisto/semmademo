package com.example.semmademo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	
	public enum GameState {
		READY, RUNNING, PAUSED, LOST;
	}
	
    private RefreshHandler mRedrawHandler = new RefreshHandler();
	private long mMoveDelay = 50;
	private List<Circle> mCircles;
	private Circle mPlayer;
	private GameState mState;
	private GameClock mClock;
	private OnGameEndEventListener mOnGameEndEventListener;
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		mClock = new GameClock();
		initPlayer();
		initCircles();
		mState = GameState.READY;
	}

	private void initPlayer() {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		mPlayer = new Circle(20, 100, 100, paint);
	}

	private void initCircles() {
		mCircles = new ArrayList<Circle>();
		Paint circlePaint = new Paint();
		circlePaint.setARGB(255, 0, 0, 255);
		Random random = new Random();
		int numCircles = 3+random.nextInt(3);
		for (int i=0;i<numCircles;i++) {
			int radius = random.nextInt(35)+5;
			int speedX = random.nextInt(20)-10;
			int speedY = random.nextInt(20)-10;
			Circle circle = new Circle(radius, 300, 550, speedX, speedY, 450, 700, circlePaint);
			mCircles.add(circle);
		}
	}
	
	public void setState(GameState newState) {
		GameState oldState = mState;
		mState = newState;
		if (oldState != GameState.RUNNING && newState == GameState.RUNNING) {
			mClock.start();
			update();
		}
		if (newState != GameState.RUNNING) {
			mClock.pause();
		}
		if (mState == GameState.LOST && mOnGameEndEventListener != null) {
			mOnGameEndEventListener.onGameEnd(mClock.getTime()/1000.0);
		}
	}
	
	public void setGameEndEventListener(OnGameEndEventListener onGameEndEventListener) {
		mOnGameEndEventListener = onGameEndEventListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(mPlayer.getX(), mPlayer.getY(), mPlayer.getRadius(), mPlayer.getPaint());
		for (Circle circle : mCircles) {
			canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), circle.getPaint());
		}
	}
	
	protected void update() {
		if (mState == GameState.RUNNING) {
			for (Circle circle : mCircles) {
				circle.update();
				if (circle.collisionWith(mPlayer)) {
					setState(GameState.LOST);
				}
			}
			mRedrawHandler.sleep(mMoveDelay);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        if (mState == GameState.RUNNING) {
        	mPlayer.setX((int)event.getX());
        	mPlayer.setY((int)event.getY());
        	invalidate();
        }
        if (mState == GameState.READY || mState == GameState.PAUSED) {
        	setState(GameState.RUNNING);
        }
		return super.onTouchEvent(event);
	}
	
    /**
     * Create a simple handler that we can use to cause animation to happen.  We
     * set ourselves as a target and we can use the sleep()
     * function to cause an update/invalidate to occur at a later date.
     */
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            GameView.this.update();
            GameView.this.invalidate();
        }

        public void sleep(long delayMillis) {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }
}
