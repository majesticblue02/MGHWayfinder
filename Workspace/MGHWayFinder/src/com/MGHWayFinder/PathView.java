package com.MGHWayFinder;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class PathView extends View{
	
	ArrayList<Integer> xArray, yArray;															//arraylists used to hold x,y coords of node points
	float nativeWidth, nativeHeight;															//screen width and height (used for scaling)
	int floorInt;																				//floor number used to look up background bm to load
	Rect bounds;																				//outer bounds of background bm
	Matrix matrix1 = new Matrix();
	Paint p = new Paint();																		//paint used to stroke path
	Drawable baseMap;
	Path path = new Path();
	
	int[] images = {(int)(R.drawable.basemap700)};												//array of image locations

	public PathView(Context context, ArrayList<Integer> xArray, ArrayList<Integer> yArray, float screenW, float screenH, int floor) {
		super(context);

		this.xArray = xArray;
		this.yArray = yArray;
		this.nativeHeight = screenH;
		this.nativeWidth = screenW;
		this.floorInt = floor;
		
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		
		baseMap = getResources().getDrawable(images[floor-1]);

		bounds = new Rect(0, 0, baseMap.getIntrinsicWidth(), baseMap.getIntrinsicHeight());
		baseMap.setBounds(bounds);
		
		
		//scale view based on background image size
		if((nativeWidth/(float)bounds.right) > (nativeHeight/(float)bounds.bottom))
			matrix1.postScale((nativeHeight/(float)bounds.bottom), (nativeHeight/(float)bounds.bottom));
		else
			matrix1.postScale((nativeWidth/(float)bounds.right), (nativeWidth/(float)bounds.right));
	}
	
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.save();

		canvas.setMatrix(matrix1);
		baseMap.draw(canvas);
		makePath();
		
		canvas.drawPath(path, p);
	}
	
	//draws path using arrays
	protected void makePath(){
		int x,y;
		x = xArray.get(0);
		y = yArray.get(0);

		path.addCircle(x, y, 5, Path.Direction.CW);								//adds a circle to the path start
		path.moveTo(x, y);
		
		for(int i = 0; i < xArray.size(); i++){
			x = xArray.get(i);
			y = yArray.get(i);
			path.lineTo(x, y);
		}
		
		path.addCircle(x, y, 5, Path.Direction.CW);
		path.close();
	}
	
	
	public void updatePath(ArrayList<Integer> x, ArrayList<Integer> y, int floor){			//Clears the current path and updates it
		xArray = null;															//nulled to attempt to have gc remove old array objects
		yArray = null;
		path.reset();
		xArray = x;
		yArray = y;
		this.floorInt = floor;
		invalidate();															//invalidated to rerun onDraw call
	}
	
	public void scale(float dx, float dy){
		matrix1.postScale(dx,dy);
		invalidate();
	}
	
	public void translate(float dx, float dy){
		matrix1.postTranslate(dx, dy);
		invalidate();
	}
	
	public void rotate(float degrees){
		matrix1.postRotate(degrees);
		invalidate();
	}
}
