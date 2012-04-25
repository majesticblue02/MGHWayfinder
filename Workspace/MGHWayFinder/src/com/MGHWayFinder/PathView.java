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
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PathView extends View{
	
	private ArrayList<Integer> xArray, yArray;							//arraylists used to hold x,y coords of node points
	private int vWidth, vHeight;										//screen width and height (used for scaling)
	
	private Paint p = new Paint();										//paint used to stroke path
	private Path path = new Path();										//diplay path
	
	private BitmapDrawable dMap;										//bitmap drawable used to draw to canvas
	private Bitmap bMap;												//bitmap background
	private BitmapFactory.Options op = new BitmapFactory.Options();;	//options used to define inputstream creation of bitmap
	private InputStream is;
	private Rect bounds;
	private RectF boundsF;												//outer bounds of background bm
	public Matrix matrix= new Matrix();								//matrix used to scale canvas
	public Matrix savedMatrix = new Matrix();
	
	final int ANIMATIONSTEP = 25;										//used for animation
	final int ANIMATIONTOTAL = 500;
	int transX, transY;
	
	private final String[] source = {"","floor1color.png","floor2color.png"};
	
	private boolean isnull;										//boolean
	
	
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
	
	//Used to determine view size
	@Override
	protected void onMeasure(int wMeasureSpec, int hMeasureSpec){
		super.onMeasure(wMeasureSpec, hMeasureSpec);
		int w = MeasureSpec.getSize(wMeasureSpec);
		int h = MeasureSpec.getSize(hMeasureSpec);
		setMeasuredDimension(w,h/2);
	}
	
	//initialize pathview object
	protected void iniPV(){
		setFocusable(true);
		
		p.setColor(Color.BLACK);
		p.setStrokeWidth(4);
		p.setStyle(Style.STROKE);
		
		op.inPreferredConfig = Bitmap.Config.RGB_565;
		op.inPurgeable = true;
		
		isnull = false;
		
		this.requestLayout();
	}
	
	
	
	public void updatePathView(ArrayList<Integer> xArray, ArrayList<Integer> yArray, int floor, AssetManager am) {

		this.xArray = xArray;
		this.yArray = yArray;
		
		loadFloorMap(floor,am);
		invalidate();
	}
	
	private void loadFloorMap(int floor, AssetManager am){
		
		try{
			is = am.open(source[floor]);
			bMap = BitmapFactory.decodeStream(is, null, op);
			dMap = new BitmapDrawable(this.getResources(), bMap);
			bounds = new Rect(0, 0, dMap.getIntrinsicWidth(), dMap.getIntrinsicHeight());
			dMap.setBounds(bounds);
		} catch(IOException e){
			Toast.makeText(getContext(), "ERROR LOADING FLOOR MAP", 2000).show();
		}
	}
	

	private void scale(){
		if(((float)vWidth/boundsF.right) > ((float)vHeight/boundsF.bottom))
			matrix.postScale(((float)vHeight/boundsF.bottom), ((float)vHeight/boundsF.bottom));
		else
			matrix.postScale(((float)vWidth/boundsF.right), ((float)vWidth/boundsF.right));		
	}
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		vWidth = getMeasuredWidth();
		vHeight = getMeasuredHeight();
		
		if(!isnull){															//TODO REMOVE IF BLOCK & isnull
			canvas.setMatrix(matrix);
			dMap.draw(canvas);
		
			path.reset();
			makePath();
			canvas.drawPath(path, p);
		}
		
		
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
		invalidate();															//invalidated to rerun onDraw call
	}
	
	public void recycleImage(){
		bMap.recycle();
		dMap = null;
		path.reset();
	}
	
	public Rect getImageBounds(){
		return bounds;
	}
	
	public Point getCenterPoint(){
		Point out = new Point();
		
		if(vWidth == 0 || vHeight ==0)
			out = null;
		else
			out.set(vWidth/2, vHeight/2);
		
		return out;
	}
	
	public synchronized void setCenterPoint(Node n){
		Point centerPoint;
		if(getCenterPoint() != null){
			centerPoint= getCenterPoint();
			
			float[] center = new float[]{(float)centerPoint.x, (float)centerPoint.y};
			
			matrix.mapPoints(center);
			
			transX = -1*(int)(n.getX() - center[0]);// (ANIMATIONTOTAL/ANIMATIONSTEP);
			transY = -1*(int)(n.getY() - center[1]);// (ANIMATIONTOTAL/ANIMATIONSTEP);
			//Thread ani = new Thread(animate, "translation animation");
			//ani.start();
			
			matrix.postTranslate(transX, transY);
		}
		
		
	}
	
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
    	private int i = 0;
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
