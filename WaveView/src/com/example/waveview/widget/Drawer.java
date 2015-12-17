package com.example.waveview.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.example.waveview.ContextHelper;
import com.example.waveview.R;

public class Drawer {
	private Path mPathBackWave;
	private Path mPathForeWave;
	private Path mPathCoverBottom;
	private Path mPathCoverTop;
	private Path mPathClearAll;
	private Paint mPaintTransparent;
	private Paint mPaintForeWave;
	private Paint mPaintBackWave;
	private Paint mPaint;
	private Paint mPaintText;
	private int mDiameter;
	private int mCanvasWidth;
	private int mCanvasHeight;
	private float mTextSizeNumber;
	private float mTextSizeSignPercent;
	private float mTextSizeText;
	private Bitmap mFrameBm;
	private int mPaddingHorizontalCircle;
	private int mPaddingVerticalCircle;
	private int mPaddingHorizontalFrame;
	private int mPaddingVerticalFrame;
	private float mPiDd;
	private int mNumberX;
	private int mNumberY;
	private int mPercentSignOffsetY;
	private int mPercentSignOffsetXWhenOneNumber;
	private int mPercentSignOffsetXWhenTwoNumbers;
	private int mPercentSignOffsetXWhenThreeNumbers;
	private int mTextX;
	private int mTextY;
	private String mText;

	public void setMeasurement(int canvasWidth, int canvasHeight) {
		mCanvasWidth = canvasWidth;
		mCanvasHeight = canvasHeight;
		mDiameter = (int) (canvasHeight >= canvasWidth ? (canvasWidth * 0.86f)
				: (canvasHeight * 0.86f));
		mPaddingHorizontalCircle = (int) ((mCanvasWidth - mDiameter) * 0.5f);
		mPaddingVerticalCircle = (int) ((mCanvasHeight - mDiameter) * 0.5f);
		mPaddingHorizontalFrame = (int) (canvasHeight >= canvasWidth ? 0
				: (canvasWidth - canvasHeight) * 0.5f);
		mPaddingVerticalFrame = (int) (canvasHeight >= canvasWidth ? ((canvasHeight - canvasWidth) * 0.5f)
				: 0);
		mTextSizeNumber = mDiameter * 0.33f;
		mTextSizeSignPercent = mDiameter * 0.17f;
		mTextSizeText = mDiameter * 0.1f;
		mPiDd = (float) (Math.PI / mDiameter);
		mText = ContextHelper.getContext().getResources()
				.getString(R.string.used);
		init();
	}

	public int getDiameter() {
		return mDiameter;
	}

	private void init() {
		RectF rectCircle = null;
		rectCircle = new RectF(mPaddingHorizontalCircle,
				mPaddingVerticalCircle,
				mCanvasWidth - mPaddingHorizontalCircle, mCanvasHeight
						- mPaddingVerticalCircle);
		RectF rectCanvas = new RectF(0, 0, mCanvasWidth, mCanvasHeight);

		mPaint = new Paint();

		mPathCoverBottom = new Path();
		mPathCoverBottom.addArc(rectCircle, 0, 180);
		mPathCoverBottom.lineTo(0, mCanvasHeight);
		mPathCoverBottom.lineTo(mCanvasWidth, mCanvasHeight);
		mPathCoverBottom.close();

		mPathCoverTop = new Path();
		mPathCoverTop.addArc(rectCircle, 180, 180);
		mPathCoverTop.lineTo(mCanvasWidth, 0);
		mPathCoverTop.lineTo(0, 0);
		mPathCoverTop.close();

		mPathClearAll = new Path();
		mPathClearAll.addRect(rectCanvas, Direction.CCW);

		mPaintTransparent = new Paint();
		mPaintTransparent.setAntiAlias(true);
		mPaintTransparent.setAlpha(0);
		PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
		mPaintTransparent.setXfermode(mode);

		mPathBackWave = new Path();
		mPathForeWave = new Path();

		mPaintForeWave = new Paint();
		mPaintBackWave = new Paint();
		mPaintForeWave.setAntiAlias(true);
		mPaintForeWave.setColor(0xff32dc14);
		mPaintBackWave.setAntiAlias(true);
		mPaintBackWave.setColor(0x9932dc14);

		mPaintText = new Paint();
		mPaintText.setAntiAlias(true);
		mPaintText.setTextAlign(Align.CENTER);
		mPaintText.setColor(Color.WHITE);
		mPaintText.setShadowLayer(2, 0, 0, 0xee666666);
		mPaintText.setTypeface(Typeface.createFromAsset(ContextHelper
				.getContext().getAssets(), "fonts/Lanting_black.TTF"));

		// frame
		mFrameBm = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				ContextHelper.getContext().getResources(),
				R.drawable.waveball_frame), mCanvasWidth - 2
				* mPaddingHorizontalFrame, mCanvasHeight - 2
				* mPaddingVerticalFrame, true);

		// text offset
		mPaintText.setTextSize(mTextSizeNumber);
		float numberWidth = mPaintText.measureText("a");
		mPaintText.setTextSize(mTextSizeSignPercent);
		float percentSignWidth = mPaintText.measureText("%");

		mNumberX = mCanvasWidth / 2 - (int) (percentSignWidth * 0.5f);
		mNumberY = (int) (mCanvasHeight * 0.5f + mDiameter * 0.12f);
		mPercentSignOffsetXWhenOneNumber = mCanvasWidth / 2
				+ (int) (numberWidth * 0.5f);
		mPercentSignOffsetXWhenTwoNumbers = mCanvasWidth / 2
				+ (int) numberWidth;
		mPercentSignOffsetXWhenThreeNumbers = mCanvasWidth / 2
				+ (int) (numberWidth * 1.5f);
		mPercentSignOffsetY = (int) (mCanvasHeight * 0.5f + mDiameter * 0.12f);

		mTextX = (int) (mCanvasWidth * 0.5f);
		mTextY = (int) (mCanvasHeight * 0.5f + mDiameter * 0.25f);
	}

	public void setPaintColorByFillPercent(boolean isGreaterThan80Percent) {
		if (isGreaterThan80Percent) {
			mPaintForeWave.setColor(0xffff8400);
			mPaintBackWave.setColor(0x99ff8400);
		} else {
			mPaintForeWave.setColor(0xff32dc14);
			mPaintBackWave.setColor(0x9932dc14);
		}
	}

	public void draw(Canvas canvas, int wOfForeWave, int amplitude,
			float fillPercent) {
		int waveHeight = mCanvasHeight
				- ((int) (mDiameter * fillPercent) + mPaddingVerticalCircle);
		if (waveHeight > 0) {
			mPathBackWave.reset();
			mPathForeWave.reset();
			mPathBackWave.moveTo(mCanvasWidth - mPaddingHorizontalCircle,
					mCanvasHeight);
			mPathForeWave.moveTo(mCanvasWidth - mPaddingHorizontalCircle,
					mCanvasHeight);
			mPathBackWave.lineTo(mPaddingHorizontalCircle, mCanvasHeight);
			mPathForeWave.lineTo(mPaddingHorizontalCircle, mCanvasHeight);
			int rightx = mDiameter + mPaddingHorizontalCircle;
			for (int i = mPaddingHorizontalCircle; i <= rightx; i++) {
				mPathForeWave.lineTo(
						i,
						(float) (waveHeight + amplitude
								* Math.cos((float) (i + wOfForeWave) * mPiDd)));
				mPathBackWave.lineTo(
						i,
						(float) (waveHeight - amplitude
								* Math.cos((float) (i + wOfForeWave) * mPiDd)));
			}
			mPathBackWave.close();
			mPathForeWave.close();
			canvas.drawPath(mPathClearAll, mPaintTransparent);
			canvas.drawPath(mPathBackWave, mPaintBackWave);
			canvas.drawPath(mPathForeWave, mPaintForeWave);
			canvas.drawPath(mPathCoverBottom, mPaintTransparent);
			canvas.drawPath(mPathCoverTop, mPaintTransparent);

			// 文字部分
			int percentInt = (int) (fillPercent * 100);
			mPaintText.setTextSize(mTextSizeNumber);
			String percent = String.format("%s", String.valueOf(percentInt));
			canvas.drawText(percent, mNumberX, mNumberY, mPaintText);
			mPaintText.setTextSize(mTextSizeSignPercent);
			int textX = 0;
			if (percentInt < 10) {
				textX = mPercentSignOffsetXWhenOneNumber;
			} else if (percentInt < 100) {
				textX = mPercentSignOffsetXWhenTwoNumbers;
			} else if (percentInt == 100) {
				textX = mPercentSignOffsetXWhenThreeNumbers;
			}
			canvas.drawText("%", textX, mPercentSignOffsetY, mPaintText);
			mPaintText.setTextSize(mTextSizeText);
			canvas.drawText(mText, mTextX, mTextY, mPaintText);
			// 边框
			canvas.drawBitmap(mFrameBm, mPaddingHorizontalFrame,
					mPaddingVerticalFrame, mPaint);
		}
	}
}
