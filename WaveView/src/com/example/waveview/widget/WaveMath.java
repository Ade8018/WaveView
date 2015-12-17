package com.example.waveview.widget;

public class WaveMath {
	private float mWaveAmplitude;
	private int mW;
	private int mRadius;
	private float mFillPercent;

	public void setRadius(int radius) {
		mRadius = radius;
	}

	public int getNextAmplitude() {
		if (mWaveAmplitude > 0) {
			mWaveAmplitude -= 0.05;
		}
		return (int) mWaveAmplitude;
	}

	public int getNextW() {
		mW += 12;
		if (mW >= mRadius * 4) {
			mW = 0;
		}
		return mW;
	}

	public void setWaveAmplitude(int wa) {
		mWaveAmplitude = wa;
		checkAmplitude();
	}

	public void addWaveAmplitude(int count) {
		mWaveAmplitude += count;
		checkAmplitude();
	}

	private void checkAmplitude() {
		if (mWaveAmplitude < 0) {
			mWaveAmplitude = 0;
		} else if (mWaveAmplitude > getLargestWaveAmplitude()) {
			mWaveAmplitude = getLargestWaveAmplitude();
		}
	}

	public void setFillPercent(float percent) {
		if (percent < 0 || percent > 1)
			throw new IllegalArgumentException();
		mFillPercent = percent;
	}

	public int getLargestWaveAmplitude() {
		return (int) (mRadius * 2 * ((0.5f - Math.abs(mFillPercent - 0.5f)) * 0.2f));
	}

	public float getFillPercent() {
		return mFillPercent;
	}
}
