package com.MGHWayFinder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class PathView extends View{
	Paint p = new Paint();
	ArrayList<Integer> xArray, yArray;
	Matrix scale = new Matrix();

	float nativeWidth, nativeHeight;
	Drawable baseMap;
	Rect bounds;

	Path path = new Path();

	
	public PathView(Context context, ArrayList<Integer> xArray, ArrayList<Integer> yArray, float width, float height) {
		super(context);
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		this.xArray = xArray;
		this.yArray = yArray;
		this.nativeHeight = height;
		this.nativeWidth = width;
		
		baseMap = getResources().getDrawable(R.drawable.basemap700);

		bounds = new Rect(0, 0, baseMap.getIntrinsicWidth(), baseMap.getIntrinsicHeight());
		baseMap.setBounds(bounds);
		
		if((nativeWidth/(float)bounds.right) > (nativeHeight/(float)bounds.bottom))
			scale.postScale((nativeHeight/(float)bounds.bottom), (nativeHeight/(float)bounds.bottom));
		else
			scale.postScale((nativeWidth/(float)bounds.right), (nativeWidth/(float)bounds.right));
	}
	
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.save();

		canvas.setMatrix(scale);
		baseMap.draw(canvas);
		makePath();
		
		canvas.drawPath(path, p);
	}
	
	private void makePath(){
		int x,y;
		x = xArray.get(0);
		y = yArray.get(0);
		
		path.addCircle(x, y, 5, Path.Direction.CW);
		path.moveTo(x, y);
		
		for(int i = 0; i < xArray.size(); i++){
			x = xArray.get(i);
			y = yArray.get(i);
			path.lineTo(x, y);
		}
		
		path.addCircle(x, y, 5, Path.Direction.CW);
		path.close();
	}
	
}
