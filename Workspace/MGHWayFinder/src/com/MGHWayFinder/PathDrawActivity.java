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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class PathDrawActivity extends Activity implements OnTouchListener{
	PathView pv;
	Bundle bundle;
	ArrayList<Integer> xPoints = new ArrayList<Integer>();
	ArrayList<Integer> yPoints = new ArrayList<Integer>();
	int sWidth, sHeight, floor;
	AssetManager am;
	Button center;
	
	Hashtable<String, Node> localHash = MGHWayFinderActivity.masterHash;
	Dijkstra dijkstra;
	Node sNode, eNode, bNode;
	String startnID, endnID;
	ArrayList<Node> nodePath;
	
	
	Matrix m;
	Matrix savedM;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int MODE = NONE;
	Point sPoint = new Point();
	Rect imageBounds;

	@Override
	public synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		center = (Button)findViewById(R.id.buttonCenter);
		
		
        pv = (PathView)findViewById(R.id.pathView);
        am = getAssets();
        
		//updateBundle(); TODO Delete
      
    //GET START AND END NODEID FROM BUNDLE
        bundle = getIntent().getExtras();
        startnID = bundle.getString("StartnID");
        endnID = bundle.getString("EndnID");
        
    //GET NODES FROM HASHTABLE
    	sNode = localHash.get(startnID);														
		eNode = localHash.get(endnID);
        
		floor = sNode.getNodeFloor();
		
        if(sNode.getNodeFloor() != eNode.getNodeFloor()){
        	calcPath();
        	bNode = dijkstra.getBreakNode().getPreviousNode();										//set bNode to the last node on the first floor of travel
        	
        	nodePath = dijkstra.getPath(bNode);										//calculate path from start node to intermediate node bNode
        } else {
        	calcPath();
        	nodePath = dijkstra.getPath(eNode);
        }
        
        for(Node n:nodePath){
        	xPoints.add(0, n.getX());
        	yPoints.add(0, n.getY());
        }
        
		pv.updatePathView(xPoints, yPoints, floor, am);
		
		pv.setBackgroundColor(Color.WHITE);
		
		pv.setOnTouchListener(this);
		
		center.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v){
						pv.setCenterPoint(sNode);
					}
				}
		);	
	
	 }
	
	private void calcPath(){
	//CALCULATE ALL PATHS FROM START NODE
		if(dijkstra == null){
			dijkstra = new Dijkstra(sNode);
		} else{
			dijkstra.reset();
			dijkstra.restart(sNode);
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Toast.makeText(getApplicationContext(), "PAUSED", 1000).show();
		pv.recycleImage();
		System.gc();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Toast.makeText(getApplicationContext(), "RESUMED", 1000).show();
	}

	/* TODO DELETE
	
	protected void updateBundle(){
		bundle = getIntent().getExtras();													//get info passed from starting intent
		
		floor = bundle.getInt("floor");														//working floor number
		delim = bundle.getString("delim");													//delimiter used to concat string of values

		for(String it:bundle.getString("xString").split(delim)){							//rebuilding arrays
			xPoints.add(Integer.parseInt(it));
		}
		for(String it:bundle.getString("yString").split(delim)){
			yPoints.add(Integer.parseInt(it));
		}
	} */
	
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
		
		return true;
	}
	
}
