package com.example.semmademo;

import android.graphics.Paint;

public class Circle {

	private final int mRadius;
	private int mPositionX;
	private int mPositionY;
	private int mSpeedX;
	private int mSpeedY;
	private final Paint mPaint;
	private final int mWorldWidth;
	private final int mWorldHeight;

	public Circle(final int radius, final int positionX, final int positionY, final int speedX, final int speedY, final int worldWidth, final int worldHeight, final Paint paint) {
		this.mRadius = radius;
		this.mPositionX = positionX;
		this.mPositionY = positionY;
		this.mSpeedX = speedX;
		this.mSpeedY = speedY;
		this.mWorldWidth = worldWidth;
		this.mWorldHeight = worldHeight;
		this.mPaint = paint;
	}
	
	public Circle(final int radius, final int positionX, final int positionY, Paint paint) {
		this(radius,positionX,positionY,0,0,0,0,paint);
	}
	
	public int getX() {
		return mPositionX;
	}

	public int getY() {
		return mPositionY;
	}
	
	public void setX(int x) {
		mPositionX = x;
	}
	
	public void setY(int y) {
		mPositionY = y;
	}
	
	public Paint getPaint() {
		return mPaint;
	}
	
	public int getRadius() {
		return mRadius;
	}
	
	public void update() {
		mPositionX += mSpeedX;
		if (mPositionX > mWorldWidth-mRadius || mPositionX < mRadius) {
			mSpeedX = -1 * mSpeedX;
		}
		mPositionY += mSpeedY;
		if (mPositionY > mWorldHeight - mRadius || mPositionY < mRadius) {
			mSpeedY = -1 * mSpeedY;
		}
	}
	
	public boolean collisionWith(Circle circle) {
		Double distance = Math.sqrt(Math.pow(circle.getX() - mPositionX, 2) + Math.pow(circle.getY()-mPositionY, 2));
		return distance < mRadius + circle.getRadius();
	}
}
