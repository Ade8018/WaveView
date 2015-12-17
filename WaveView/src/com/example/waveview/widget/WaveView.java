package com.example.waveview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class WaveView extends SurfaceView implements Callback {
	public static final String TAG = WaveView.class.getName();
	private SurfaceHolder mSurfh;
	private boolean mIsRunning;
	private Drawer mDrawer;
	private WaveMath mWaveMath;
	private boolean mIsInited;

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSurfh = getHolder();
		setZOrderOnTop(true);
		mSurfh.setFormat(PixelFormat.TRANSPARENT);
		mSurfh.addCallback(this);
		mWaveMath = new WaveMath();
		mDrawer = new Drawer();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		init();
	}

	private void init() {
		if (mIsInited) {
			return;
		}
		mIsInited = true;
		mDrawer.setMeasurement(getMeasuredWidth(), getMeasuredHeight());
		mWaveMath.setRadius((int) (mDrawer.getDiameter() * 0.5f));
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mIsRunning = true;
		new Draw().start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsRunning = false;
	}

	public class Draw extends Thread {
		@Override
		public void run() {
			while (mIsRunning) {
				Canvas canv = null;
				try {
					synchronized (mSurfh) {
						canv = mSurfh.lockCanvas();
						if (canv != null)
							mDrawer.draw(canv, mWaveMath.getNextW(),
									(int) mWaveMath.getNextAmplitude(),
									mWaveMath.getFillPercent());
					}
				} finally {
					if (canv != null) {
						mSurfh.unlockCanvasAndPost(canv);
					}
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mWaveMath.addWaveAmplitude(5);
		}
		return super.onTouchEvent(event);
	}

	public void wave() {
		mWaveMath.setWaveAmplitude(999);
	}

	public void setFillPercent(float percent) {
		mWaveMath.setFillPercent(percent);
		mDrawer.setPaintColorByFillPercent(mWaveMath.getFillPercent() > 0.8);
	}
}
