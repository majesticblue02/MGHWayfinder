package com.MGHWayFinder;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

public class pathDraw extends Activity{
	PathView pv;
	ImageView iv;
	Bundle bundle;
	ArrayList<Integer> xPoints = new ArrayList<Integer>();
	ArrayList<Integer> yPoints = new ArrayList<Integer>();
	Drawable baseMap, pathWay;
	Rect bounds;
	int width, height;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);  
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
		
		bundle = getIntent().getExtras();

		for(String it:bundle.getString("x").split(",")){
			xPoints.add(Integer.parseInt(it));
		}
		for(String it:bundle.getString("y").split(",")){
			yPoints.add(Integer.parseInt(it));
		}
		
		pv = new PathView(this.getApplicationContext(),xPoints,yPoints,width,height);
		pv.setBackgroundColor(Color.WHITE);
		setContentView(pv);
		
		
	 }



}
