package com.example.semmademo;

public class GameClock {

	private long mStartTime;
	private long mPausedAt;
	private boolean mRunning;
	
	public GameClock() {
		mRunning = false;
		mStartTime = 0;
		mPausedAt = 0;
	}
	
	public void start() {
		if (!mRunning) {
			mStartTime = System.currentTimeMillis() - mPausedAt;	
		}
		mRunning = true;
	}
	
	public void pause() {
		if (mRunning) {
			mPausedAt = System.currentTimeMillis()-mStartTime;
		}
		mRunning = false;
	}
	
	public long getTime() {
		if (mRunning) {
			return System.currentTimeMillis()-mStartTime;
		}
		return mPausedAt;
	}
}
