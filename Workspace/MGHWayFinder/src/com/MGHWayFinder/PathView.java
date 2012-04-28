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
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class PathView extends View{
	
	private ArrayList<Integer> xArray, yArray;									//arraylists used to hold x,y coords of node points
	private int vWidth, vHeight;												//screen width and height (used for scaling)
	private AssetManager am;
	
	private Paint p = new Paint();												//paint used to stroke path
	private Path path = new Path();												//display path
	
	private BitmapDrawable dMap;												//bitmap drawable used to draw to canvas
	private Bitmap bMap;														//bitmap background
	private BitmapFactory.Options op = new BitmapFactory.Options();;			//options used to define inputstream creation of bitmap
	private InputStream is;
	private Rect bounds;														//outer bounds of background bm
	public Matrix matrix= new Matrix();											//matrix used to scale canvas
	public Matrix savedMatrix = new Matrix();
	private float[] mValues = new float[9];										//USED TO RETRIEVE MATRIX VALUES (TRANSLATION & SCALE VALUES)
	
	final int ANIMATIONSTEP = 25;													//used for animation
	final int ANIMATIONTOTAL = 500;
	float transX, transY;
	
	private final String[] source = {"","floor1color.png","floor2color.png"};		//STRNIG[] OF FLOOR PLAN FILE NAMES, STORED IN ORDER OF FLOOR	
	
	public PathView(Context context){
		super(context);
		iniPV();
	}
	
	//Called when XML is inflated
	public PathView(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
		iniPV();
	}
	
	//Called when XML is inflated
	public PathView(Context context, AttributeSet attrs){
		super(context, attrs);
		iniPV();
	}
	
	//Used to determine view size (called during onDraw)
	@Override
	protected void onMeasure(int wMeasureSpec, int hMeasureSpec){
		super.onMeasure(wMeasureSpec, hMeasureSpec);
		int w = MeasureSpec.getSize(wMeasureSpec);
		int h = MeasureSpec.getSize(hMeasureSpec);
		
		setMeasuredDimension(w,h);		
	}
	
	//INITIALIZE PATHVIEW OBJECT
	protected void iniPV(){
		setFocusable(true);
		
		p.setColor(Color.BLACK);																//PAINT USED TO STROKE PATHWAY
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		
		op.inPreferredConfig = Bitmap.Config.RGB_565;											//BITMAP FACTORY OPTIONS FOR PULLING IN FLOOR PLAN
		op.inDensity = 0;
		op.inPurgeable = true;
		
		matrix.setTranslate(0, 67);																//OFFSET TO COMPENSATE FOR ODD 67 PIXEL BUG
		
	}
	
	
	//SETS INSTANCE ARRAYS, ASSET MANAGER, AND DRAWS THE VIEW
	public void makePathView(ArrayList<Integer> xArray, ArrayList<Integer> yArray, int floor, AssetManager am) {

		this.xArray = xArray;																	
		this.yArray = yArray;
		this.am = am;
		
		loadFloorMap(floor);
		invalidate();
	}
	
	//CLEARS THE CURRENT PATH AND UPDATES IT
	public void updatePath(ArrayList<Integer> x, ArrayList<Integer> y, int floor){			
		xArray = null;																		//nulled to attempt to have gc remove old array objects
		yArray = null;
		path.reset();
		xArray = x;
		yArray = y;
		loadFloorMap(floor);
		invalidate();										
	}
	
	//LOAD FLOOR PLAN BASED ON FLOOR NUMBER
	private void loadFloorMap(int floor){
		
		try{
			is = am.open(source[floor]);
			bMap = BitmapFactory.decodeStream(is, null, op);
			dMap = new BitmapDrawable(this.getResources(), bMap);
			bounds = new Rect(0, 0, dMap.getIntrinsicWidth(), dMap.getIntrinsicHeight());
			dMap.setBounds(bounds);
			is.close();
		} catch(IOException e){
			Toast.makeText(getContext(), "ERROR LOADING FLOOR MAP", 2000).show();
		}
	}
	
	//INITIALIZATION SCALE (FIT TO VIEWABLE AREA)
	private void iniScale(){
		if(((float)vWidth/(float)bounds.right) > ((float)vHeight/(float)bounds.bottom))
			matrix.postScale(((float)vHeight/(float)bounds.bottom), ((float)vHeight/(float)bounds.bottom));
		else
			matrix.postScale(((float)vWidth/(float)bounds.right), ((float)vWidth/(float)bounds.right));		
	}
	
	//ONDRAW CALL (CALLED BY VM TO DRAW TO SCREEN)
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		vWidth = getMeasuredWidth();
		vHeight = getMeasuredHeight();
		
		canvas.setMatrix(matrix);
		
		dMap.draw(canvas);														//draw the floor plan to the canvas
		path.reset();															//reset the path
		makePath();																//redraw the path
		canvas.drawPath(path, p);
	}
	
	//DRAW PATH USING COORDINATES
	protected void makePath(){
		int x,y;
		x = xArray.get(0);
		y = yArray.get(0);

		path.addCircle(x, y, 5, Path.Direction.CW);											//ADD A CIRCLE TO THE START NODE
		path.moveTo(x, y);
		
		for(int i = 0; i < xArray.size(); i++){												//LOOP THROUGH ARRAY OF COORDINATES AND DRAW LINES BETWEEN THEM
			x = xArray.get(i);
			y = yArray.get(i);
			path.lineTo(x, y);
		}
		
		path.addCircle(x, y, 5, Path.Direction.CW);											//ADD A CIRCLE TO THE END NODE
		path.close();
	}
	
	//RECYCLE IMAGE OBJECTS
	public void recycleImage(){
		bMap.recycle();
		dMap = null;
		path.reset();
	}
	
	//GET IMAGE BOUNDS
	public Rect getImageBounds(){
		return bounds;
	}
	
	//GET CENTER POINT OF PHYSICAL VIEW
	private Point getCenterPoint(){
		Point out = new Point();
		
		if(vWidth == 0 || vHeight ==0)
			out = null;
		else
			out.set(vWidth/2, vHeight/2);
		
		return out;
	}
	
	//MOVE VIEW TO CENTER OVER NODE n
	public synchronized void setCenterPoint(Node n){
		Point centerPoint;
		float currentX, currentY;
		int nodeX = -1*n.getX();
		int nodeY = -1*n.getY();
		savedMatrix.set(matrix);
		
		if(getCenterPoint() != null){
			centerPoint= getCenterPoint();
			
			matrix.getValues(mValues);
			currentX = mValues[Matrix.MTRANS_X];
			currentY = mValues[Matrix.MTRANS_Y];
			
			currentX -= centerPoint.x;
			currentY -= (centerPoint.y + 67);
			
			
			
			transX = (nodeX - currentX) / (ANIMATIONTOTAL/ANIMATIONSTEP);
			transY = (nodeY - currentY) / (ANIMATIONTOTAL/ANIMATIONSTEP);
			Thread ani = new Thread(animate, "translation animation");
			ani.start();
			
			//matrix.postTranslate(transX, transY);
			//invalidate();
		}	
	}
	
	//USED TO ANIMATE MOVEMENT
	private void step(){
		matrix.postTranslate(transX, transY);
		invalidate();
	}
	
	private Handler handler = new Handler() {
		 @Override
		 public void handleMessage(Message msg) {
		     step();
		 }
	};
	
	Runnable animate = new Runnable(){
    	public void run(){
    		try {
    			for(int i = 0; i <= (ANIMATIONTOTAL/ANIMATIONSTEP); i++){
    				Thread.sleep(ANIMATIONSTEP);
    				handler.sendEmptyMessage(i);
    			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    };

}
