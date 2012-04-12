package com.MGHWayFinder;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PathDrawActivity extends Activity{
	PathView pv;
	Bundle bundle;
	ArrayList<Integer> xPoints = new ArrayList<Integer>();
	ArrayList<Integer> yPoints = new ArrayList<Integer>();
	int sWidth, sHeight, floor;
	String delim;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();								//used to retrieve screen size for proper scaling
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        sHeight = displaymetrics.heightPixels;
        sWidth = displaymetrics.widthPixels;
		
		bundle = getIntent().getExtras();													//get info passed from starting intent
		
		floor = bundle.getInt("floor");														//working floor number
		delim = bundle.getString("delim");													//delimiter used to concat string of values

		for(String it:bundle.getString("xString").split(delim)){							//rebuilding arrays
			xPoints.add(Integer.parseInt(it));
		}
		for(String it:bundle.getString("yString").split(delim)){
			yPoints.add(Integer.parseInt(it));
		}
		
		pv = new PathView(this.getApplicationContext(),xPoints,yPoints,sWidth,sHeight,floor);
		pv.setBackgroundColor(Color.WHITE);
		setContentView(pv);
	 }



}
