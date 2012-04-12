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
	
	ArrayList<Integer> xArray, yArray;
	float nativeWidth, nativeHeight;
	Rect bounds;
	Matrix matrix1 = new Matrix();
	Paint p = new Paint();
	Drawable baseMap;
	Path path = new Path();

	public PathView(Context context, ArrayList<Integer> xArray, ArrayList<Integer> yArray, float screenW, float screenH) {
		super(context);

		this.xArray = xArray;
		this.yArray = yArray;
		this.nativeHeight = screenH;
		this.nativeWidth = screenW;
		
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		
		baseMap = getResources().getDrawable(R.drawable.basemap700);

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
	
	private void makePath(){
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
	
	public void updatePath(ArrayList<Integer> x, ArrayList<Integer> y){			//Clears the current path and updates it
		xArray.clear();
		yArray.clear();
		xArray = null;															//nulled to attempt to have gc remove old array objects
		yArray = null;
		xArray = x;
		yArray = y;
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
