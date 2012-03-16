package com.DBProto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.Toast;

public class DBProtoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Cursor RSa, RSn;
        
        DBHelper db = new DBHelper(this.getApplicationContext());
        
        Hashtable<String, node> aFloorHash = new Hashtable<String, node>();
        ArrayList<String> nString = new ArrayList<String>();
        node nodeM, nodeN;
        String stringM, stringN;
        
        CharSequence text = "got here";
        Toast toast = Toast.makeText(this.getApplicationContext(), text, 2000);
        

        
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
        
        toast.show();
        
        RSa = db.getFloorNodes(1);											//Query DB for nodes on a given floor
        RSa.moveToFirst();
        
        while(!RSa.isAfterLast()){											//Fill Hashtable for given floor
        	nodeM = new node(	RSa.getString(1),								//nID
        						RSa.getInt(2),									//x
        						RSa.getInt(3),									//y
        						RSa.getInt(4),									//nFloor
        						RSa.getString(5),								//nType
        						RSa.getString(6));								//nDep
        	
        	aFloorHash.put(nodeM.getNodeID(), nodeM);
        	nString.add(nodeM.getNodeID());									//Build Array of NodeID's for finding neighbors
        	
        	RSa.moveToNext();
        }
        
        RSn = db.getNodeNeighbors(nString);									//Query for relevant neighbors
        RSn.moveToFirst();
        
        while(!RSn.isAfterLast()){
        	stringM = RSn.getString(1);										//"Master" nodeID
        	stringN = RSn.getString(2);										//"Neighbor" nodeID
        	
        	nodeM = aFloorHash.get(stringM);								//Retrieve master node from hashtable
        	nodeN = aFloorHash.get(stringN);								//Retrieve neighbor node from hashtable
        	
        	nodeM.addNeighbor(nodeN);										//Add neighbor node to master node
        	
        }
        
        
        
        
    }
}