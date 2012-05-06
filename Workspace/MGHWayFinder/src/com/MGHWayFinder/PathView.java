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
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/* This class is used to load a bitmap which corresponds with a given floor.
 * It then draws a path determined by coordinates stored in the xArray and yArray ArrayLists.
 * 
 */
public class PathView extends View{
	
	private ArrayList<Integer> xArray, yArray;									//ARRAYLISTS WHICH HOLD X AND Y COORDINATES FOR PLOTTING THE PATH
	private boolean STARTMAP, ENDMAP;
	private int vWidth, vHeight;												//SCREEN HEIGHT AND WIDTH, AS MEASURED BY ONMEASURE
	private AssetManager am;													//ASSETMANAGER FOR GRABBING ASSETS
	
	private Paint p = new Paint();												//PAINT USED TO STROKE PATH
	private Paint s = new Paint();												//PAINT USED FOR START CIRCLE
	private Paint c = new Paint();												//PAINT USED FOR CURRENT MARKER
	private Paint e = new Paint();												//PAINT USED FOR END CIRCLE
	private Path path = new Path();												//DISPLAY PATH
	private int curX = 0;
	private int curY = 0;
	
	private BitmapDrawable dMap;												//BACKGROUND BITMAP DRAWN TO THE CANVAS
	private Bitmap bMap;														//BACKGROUND BITMAP
	private BitmapFactory.Options op = new BitmapFactory.Options();;			//OPTIONS USED TO DEFINE BITMAP STREAM
	private InputStream is;
	private Rect bounds;														//OUTTER BOUNDS OF FLOOR MAP BITMAP
	public Matrix matrix= new Matrix();											//MATRIX USED TO SCALE CANVAS
	public Matrix savedMatrix = new Matrix();									//MATRIX USED TO STORE DURING INTERMEDIATE TRANSLATIONS
	private float[] mValues = new float[9];										//USED TO RETRIEVE MATRIX VALUES (TRANSLATION & SCALE VALUES)
	
	private final int ANIMATIONSTEP = 25;										//TIME (MILLIS) TAKEN IN BETWEEN EACH ANIMATION STEP
	private final int ANIMATIONTOTAL = 500;										//TOTAL TIME TAKEN (MILLIS) TO MOVE THROUGH ANIMATION
	private float transX, transY;												//INTERMITTENT STEPS IN THE TRANSLATION
	
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
		p.setStrokeWidth(8);
		p.setStyle(Style.STROKE);
		
		s.setColor(Color.GREEN);
		s.setStyle(Style.FILL);
		
		e.setColor(Color.RED);
		e.setStyle(Style.FILL);
		
		c.setColor(Color.BLUE);
		c.setStrokeWidth(10);
		c.setStyle(Style.STROKE);
		
		op.inPreferredConfig = Bitmap.Config.RGB_565;											//BITMAP FACTORY OPTIONS FOR PULLING IN FLOOR PLAN
		op.inDensity = 0;
		op.inPurgeable = true;
		
		matrix.setTranslate(0, 67);																//OFFSET TO COMPENSATE FOR ODD 67 PIXEL BUG
		
	}
	
	//SETS INSTANCE ARRAYS, ASSET MANAGER, AND DRAWS THE VIEW
	public void makePathView(ArrayList<Integer> xArray, ArrayList<Integer> yArray, int floor, AssetManager am, int startFloor, int endFloor) {

		this.xArray = xArray;																	
		this.yArray = yArray;
		this.am = am;
		this.STARTMAP = (floor == startFloor);
		this.ENDMAP = (floor == endFloor);
		
		loadFloorMap(floor);
		invalidate();																			//CALLED TO TELL VM TO REDRAW
	}
	
	//CLEARS THE CURRENT PATH AND UPDATES IT
	public void updatePath(ArrayList<Integer> x, ArrayList<Integer> y, int floor, int startFloor, int endFloor){			
		this.xArray = null;																			//nulled to attempt to have gc remove old array objects
		this.yArray = null;
		this.path.reset();
		this.xArray = x;
		this.yArray = y;
		this.STARTMAP = (floor == startFloor);
		this.ENDMAP = (floor == endFloor);
		
		loadFloorMap(floor);
		invalidate();																			//CALLED TO TELL VM TO REDRAW
	}
	
	//LOAD FLOOR PLAN BASED ON FLOOR NUMBER
	private void loadFloorMap(int floor){
		
		try{
			is = am.open(source[floor]);														//A BITMAP FACTORY IS USED IN CONJUNCTION WITH AN INPUTSTREAM TO HELP CONTROL THE SIZE OF THE IMAGE
			bMap = BitmapFactory.decodeStream(is, null, op);									//THE BMF OPTIONS HELP CONTROL THE RESULTING IMAGE SIZE IN ANDROID
			dMap = new BitmapDrawable(this.getResources(), bMap);
			bounds = new Rect(0, 0, dMap.getIntrinsicWidth(), dMap.getIntrinsicHeight());
			dMap.setBounds(bounds);
			is.close();
		} catch(IOException e){
			Toast.makeText(getContext(), "ERROR LOADING FLOOR MAP", 2000).show();
		}
	}
	
	//ONDRAW CALL (CALLED BY VM TO DRAW TO SCREEN)
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		vWidth = getMeasuredWidth();
		vHeight = getMeasuredHeight();														//ONMEASURE CALLED BEFORE ONDRAW - VARIABLES USED FOR CENTERING THE SCREEN
		
		canvas.setMatrix(matrix);															//APPLY THE TRANSFORMATION MATRIX TO THE CANVAS
		
		dMap.draw(canvas);																	//DRAW THE FLOOR PLAN TO THE CANVAS
		path.reset();																		//RESET THE WALKING PATH
		makePath();																			//REMAKE THE WALKING PATH

		canvas.drawPath(path, p);															//DRAW THE PATH TO THE CANVAS
		if(STARTMAP)
			canvas.drawCircle(xArray.get(0), yArray.get(0), 10, s);
		if(ENDMAP)
			canvas.drawCircle(xArray.get(xArray.size()-1), yArray.get(yArray.size()-1), 10, e);
		canvas.drawCircle(curX, curY, 15, c);
		
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
			path.addCircle(x, y, 5, Path.Direction.CW);
			path.moveTo(x, y);
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
	
	//TRANSLATE VIEW TO CENTER OVER NODE n
	public synchronized void setCenterPoint(Node n){
		Point centerPoint;
		float currentX, currentY;
		int nodeX = (int)-1*n.getX();
		int nodeY = (int)-1*n.getY();
		
		if(getCenterPoint() != null){
			centerPoint= getCenterPoint();
			
			matrix.getValues(mValues);															//PULL OUT X AND Y TRANSFORMATION VALUES FROM MATRIX
			currentX = mValues[Matrix.MTRANS_X];
			currentY = mValues[Matrix.MTRANS_Y];
			
			currentX -= centerPoint.x;
			currentY -= (centerPoint.y + 67);													//COMPENSATING FOR 67 PIXEL BUG
			
			
			
			transX = ((nodeX - currentX)) / (ANIMATIONTOTAL/ANIMATIONSTEP);						//SET EACH INTERMEDIATE X TRANSLATION
			transY = ((nodeY - currentY)) / (ANIMATIONTOTAL/ANIMATIONSTEP);						//SET EACH INTERMEDIATE Y TRANSLATION
			Thread ani = new Thread(animate, "translation animation");
			ani.start();
		}	
		
		curX = -nodeX;
		curY = -nodeY;
		
		
	}
	
	//USED TO ANIMATE TRANSLATION
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
    				Thread.sleep(ANIMATIONSTEP);												//SLEEP FOR EACH STEP, THEN TRANSLATE
    				handler.sendEmptyMessage(i);
    			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    };

}
