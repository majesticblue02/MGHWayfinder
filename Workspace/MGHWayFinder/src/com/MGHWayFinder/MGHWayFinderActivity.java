package com.MGHWayFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class MGHWayFinderActivity extends Activity {
    /** Called when the activity is first created. */
	
	Spinner start, end;
	TextView tvPath;
	ImageView ivPath;
	Button go;
	
	Dijkstra dPath;
	String sPath;
	ArrayAdapter<Node> aAdapter;
	DBHelper db;
    ArrayList<Node> aFloor = new ArrayList<Node>();
    ArrayList<Node> aPath;
    Hashtable<String, Node> hash;
    //ArrayList<Node> bFloor = new ArrayList<Node>();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        db = new DBHelper(this.getApplicationContext());
        initializeDB();
        hash = db.buildFloorNodes(1);
        for(Node n:hash.values())
        	aFloor.add(n);
        
///////////////////UI ELEMENTS////////////////////////
        start = (Spinner)findViewById(R.id.startSpin);
        end = (Spinner)findViewById(R.id.endSpin);
        tvPath = (TextView)findViewById(R.id.tvNP);
        ivPath = (ImageView)findViewById(R.id.imageView);
        go = (Button)findViewById(R.id.goButton);
        
        aAdapter = new ArrayAdapter<Node>(this, android.R.layout.simple_spinner_item, aFloor);
        start.setAdapter(aAdapter);
        end.setAdapter(aAdapter);
        
        go.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		calculatePath();
        	}}); 
    }
    
    private void calculatePath() {
		dPath = new Dijkstra((Node)start.getSelectedItem());
		aPath = dPath.getPath((Node)end.getSelectedItem());

		sPath = aPath.get(0).getNodeID();
		
		for(int i = 1; i < aPath.size(); i++){
			sPath += " -> " + aPath.get(i).getNodeID();
		}
		
		tvPath.setText(sPath);
	}
    
    private synchronized void initializeDB(){
    	try { 
        	db.createDataBase();
        } 
        catch (IOException ioe) {
        	throw new Error("Unable to create database");
        }
 
        try {
        	db.openDataBase();
        } 
        catch(SQLException sqle){
        	throw sqle;
        }
    }
}