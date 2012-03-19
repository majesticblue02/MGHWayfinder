package com.DBProto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class DBProtoActivity extends Activity {
    /** Called when the activity is first created. */
    
    DBHelper db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Hashtable<String, node> test;
        ArrayList<String> keys;
        String out = "";
        TextView tv;
        
        db = new DBHelper(this.getApplicationContext());
        
        tv = (TextView) this.findViewById(R.id.textArea);
        
        initializeDB();
        test = buildFloorNodes(1);
        
        keys = new ArrayList<String>(test.keySet());
        
        for(String key: keys){
        	out += key + "\n";
        }
        
        tv.setText(out);
        
    }
    
    private void initializeDB(){
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
    
    private Hashtable<String, node> buildFloorNodes(int floor){					//Constructs and returns a hashtable of nodes, keyed by their nID's
    	node nodeM;
    	Hashtable<String, node> ht = new Hashtable<String, node>();
    	ArrayList<String> nodeIDs = new ArrayList<String>();
    	Cursor cursor;
    	
    	cursor = db.getFloorNodes(floor);										//Query DB for nodes on a given floor
        cursor.moveToFirst();
        
        while(!cursor.isAfterLast()){											//Fill Hashtable for given floor
        	nodeM = new node(	cursor.getString(1),								//nID
        						cursor.getInt(2),									//x
        						cursor.getInt(3),									//y
        						cursor.getInt(4),									//nFloor
        						cursor.getString(5),								//nType
        						cursor.getString(6));								//nDep
        	
        	ht.put(nodeM.getNodeID(), nodeM);									//add node to hashtable with corresponding nodeID as the key
        	nodeIDs.add(nodeM.getNodeID());										//build string of nodeID's to query neighbor table
        	
        	cursor.moveToNext();												//move to next record
        }
        
        buildNeighborNodes(ht, nodeIDs);

        return ht;
    }
    
    private void buildNeighborNodes(Hashtable<String, node> ht, ArrayList<String> nIDs){
    	String stringM, stringN;
    	node nodeM, nodeN;
    	Cursor cursor = db.getNodeNeighbors(nIDs);
    	
    	cursor.moveToFirst();
        
        while(!cursor.isAfterLast()){
        	stringM = cursor.getString(1);							//"Master" nodeID
        	stringN = cursor.getString(2);							//"Neighbor" nodeID
        	
        	nodeM = ht.get(stringM);								//Retrieve master node from hashtable
        	nodeN = ht.get(stringN);								//Retrieve neighbor node from hashtable
        	
        	nodeM.addNeighbor(nodeN);								//Add neighbor node to master node
        	
        	cursor.moveToNext();
        }
    }
}