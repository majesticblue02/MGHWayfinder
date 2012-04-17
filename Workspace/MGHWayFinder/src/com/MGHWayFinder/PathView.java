package com.MGHWayFinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class PathView extends View{
	
	ArrayList<Integer> xArray, yArray;															//arraylists used to hold x,y coords of node points
	float nativeWidth, nativeHeight;															//screen width and height (used for scaling)
	int floorInt, mapHeight, mapWidth;																				//floor number used to look up background bm to load
	Rect bounds;																				//outer bounds of background bm
	Matrix matrix = new Matrix();
	Matrix pMatrix = new Matrix();
	Paint p = new Paint();																		//paint used to stroke path
	Bitmap bMap;
	BitmapDrawable dMap;
	BitmapFactory.Options op = new BitmapFactory.Options();
	Path path = new Path();
	InputStream is;

	public PathView(Context context, ArrayList<Integer> xArray, ArrayList<Integer> yArray, float screenW, float screenH, int floor, AssetManager am) {
		super(context);

		this.xArray = xArray;
		this.yArray = yArray;
		this.nativeHeight = screenH;
		this.nativeWidth = screenW;
		this.floorInt = floor;
		
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		
		try{
			is = am.open("basemap700.png");					//TODO change to open specific floor
		} catch(IOException e){
			
		}

		op.inPreferredConfig = Bitmap.Config.RGB_565;
		op.inPurgeable = true;
		
		bMap = BitmapFactory.decodeStream(is, null, op);
		dMap = new BitmapDrawable(this.getResources(), bMap);
		bounds = new Rect(0, 0, dMap.getIntrinsicWidth(), dMap.getIntrinsicHeight());
		dMap.setBounds(bounds);
		
		//scale view based on background image size
		if((nativeWidth/(float)bounds.right) > (nativeHeight/(float)bounds.bottom))
			matrix.postScale((nativeHeight/(float)bounds.bottom), (nativeHeight/(float)bounds.bottom));
		else
			matrix.postScale((nativeWidth/(float)bounds.right), (nativeWidth/(float)bounds.right));
		
		pMatrix.postScale(700f/1434f, 2148f/4400f);
	}
	
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);

		canvas.setMatrix(matrix);
		dMap.draw(canvas);
		
		makePath();
		path.transform(pMatrix);
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
	
	public void setMatrix(Matrix m){
		matrix = m;
		invalidate();
	}
	
	public void recycleImage(){
		dMap = null;
		bMap.recycle();
		System.gc();
	}
}
