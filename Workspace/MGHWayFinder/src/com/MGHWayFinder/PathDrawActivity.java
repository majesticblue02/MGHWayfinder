package com.MGHWayFinder;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PathDrawActivity extends Activity implements OnTouchListener{
	PathView pv;
	Bundle bundle;
	ArrayList<Integer> xPoints = new ArrayList<Integer>();
	ArrayList<Integer> yPoints = new ArrayList<Integer>();
	int sWidth, sHeight, floor;
	AssetManager am;
	Button center;
	
	//PATH CALCULATION VARIABLES
	Hashtable<String, Node> localHash = MGHWayFinderActivity.masterHash;
	Dijkstra dijkstra;
	Node sNode, eNode, bNode;
	String startnID, endnID;
	ArrayList<Node> fullNodePath;
	ArrayList<Node> walkNodePath = new ArrayList<Node>();
	
	//IMAGEVIEW - TOUCH EVENT VARIABLES
	Matrix m;
	Matrix savedM;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int MODE = NONE;
	Point sPoint = new Point();
	Rect imageBounds;
	
	TextView tvX, tvY;
	float[] mValues = new float[9];
	
	//ui things from calum
		Button next;
		Button prev;
		int index = 0;

	@Override
	public synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		//center = (Button)findViewById(R.id.buttonCenter);
		tvX = (TextView)findViewById(R.id.tvX);
		tvY = (TextView)findViewById(R.id.tvY);
		next = (Button)findViewById(R.id.btnNext);
		prev = (Button)findViewById(R.id.btnPrev);
		
        pv = (PathView)findViewById(R.id.pathView);
        am = getAssets();
      
    //GET START AND END NODEID FROM BUNDLE
        bundle = getIntent().getExtras();
        startnID = bundle.getString("StartnID");
        endnID = bundle.getString("EndnID");
        
    //GET NODES FROM HASHTABLE
    	sNode = localHash.get(startnID);														
		eNode = localHash.get(endnID);
        
		floor = sNode.getNodeFloor();
		
        if(sNode.getNodeFloor() != eNode.getNodeFloor()){							//WHEN CALCULATING AN INTERFLOOR PATH, WE NEED TO BREAK IT UP INTO INDIVIDUAL FLOORS FIRST
        	calcPath();
        	bNode = dijkstra.getBreakNode();										//SET BNODE TO THE FIRST NODE ON THE SECOND FLOOR OF TRAVEL (WE CAN GET AT IT'S PREDECESSOR VIA .getPreviousNode()
        	
        	fullNodePath = dijkstra.getPath(bNode.getPreviousNode());					//CALCULATE PATH FROM START NODE TO FINAL NODE ON FIRST FLOOR
        } else {
        	calcPath();
        	fullNodePath = dijkstra.getPath(eNode);
        }
        
        buildWalkNodePath();
        
        for(Node n:fullNodePath){
        	xPoints.add(0, n.getX());
        	yPoints.add(0, n.getY());
        }
        
		pv.makePathView(xPoints, yPoints, floor, am);
		pv.setBackgroundColor(Color.WHITE);
		pv.setOnTouchListener(this);
		
		//buttons for movement
		buildWalkNodePath();
		
//		center.setOnClickListener(
//				new OnClickListener(){
//					public void onClick(View v){
//						pv.setCenterPoint(sNode);
//					}
//				}
//		);	
		
		next.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v){
						if( index < (walkNodePath.size() - 1)){
							index += 1;
							Node nextNode = walkNodePath.get(index);
							pv.setCenterPoint(nextNode);
						}
					}
				}
		);	
		
		prev.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v){
						if( index  > 0){
							index -= 1;
							Node prevNode = walkNodePath.get(index);
							pv.setCenterPoint(prevNode);
						}
					}
				}
		);	
	
	 }

	//CALCULATE ALL PATHS FROM START NODE
	private void calcPath(){
		if(dijkstra == null){
			dijkstra = new Dijkstra(sNode);
		} else{
			dijkstra.reset();
			dijkstra.restart(sNode);
		}
	}
	
	//HANDLES TOUCH EVENTS - TRANSLATING AND SCALING PATHVIEW OBJECT
	public boolean onTouch(View v, MotionEvent e) {
		PathView view = (PathView) v;
		m = view.matrix;
		savedM = view.savedMatrix;
		
		
		switch(e.getAction() & MotionEvent.ACTION_MASK){
			case MotionEvent.ACTION_DOWN:
				savedM.set(m);
				sPoint.set((int)e.getX(), (int)e.getY());
				MODE = DRAG;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				MODE = NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				if (MODE == DRAG) {
					m.set(savedM);
					m.postTranslate(e.getX() - sPoint.x, e.getY() - sPoint.y);
					view.invalidate();
				}
				break;
		}
		
	//TESTING PURPOSES
		m.getValues(mValues);
		tvX.setText(Float.toString(mValues[Matrix.MTRANS_X]));
		tvY.setText(Float.toString(mValues[Matrix.MTRANS_Y]));
		
		return true;
	}
	
	
	public void buildWalkNodePath(){
		Node currentNode, nextNode;
		double runningDist = 0;
		int i;
		
		currentNode = fullNodePath.get(0);
		walkNodePath.add(currentNode);															//INITIALIZE FIRST NODE
		
		for(i = 0; i < fullNodePath.size()-1; i++){												//LOOP THROUGH UP TO SECOND TO LAST NODE, ONLY ADDING CHANGES IN DIRECTION
			currentNode = fullNodePath.get(i);
			nextNode = fullNodePath.get(i+1);
			
			runningDist += currentNode.getNNodeDistance();
			
			if(currentNode.getNNodeAngle() != nextNode.getNNodeAngle()) {
				currentNode.setStepDist(runningDist);
				walkNodePath.add(nextNode);
				runningDist = 0;
			} 
			
			Log.i("step ", Integer.toString(i));
		}
		
		//walkNodePath.add(fullNodePath.get(i));												//ADD THE LAST NODE
		
		
	}
}
