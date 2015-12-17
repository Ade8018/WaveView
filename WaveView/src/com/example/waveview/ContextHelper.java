package com.example.waveview;

import android.content.Context;

public class ContextHelper {
	private static Context sContext;

	public static void init(Context context) {
		sContext = context;
	}

	public static Context getContext() {
		return sContext;
	}
}
