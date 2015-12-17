package com.example.waveview;

import com.example.waveview.widget.WaveView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	WaveView wb;
	float f = 0.05f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ContextHelper.init(getApplicationContext());
		setContentView(R.layout.activity_main);
		wb = (WaveView) findViewById(R.id.wb);
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				f += 0.03f;
				wb.setFillPercent(f <= 1 ? f : 1);
				wb.wave();
			}
		});
	}

}
