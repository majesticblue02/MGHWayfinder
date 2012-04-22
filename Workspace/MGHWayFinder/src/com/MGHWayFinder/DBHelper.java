package com.MGHWayFinder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static String PACKAGE = "com.MGHWayFinder";
    private static String DB_PATH = "/data/data/" + PACKAGE + "/databases/";
    private static String DB_NAME = "MGHDB.sqlite";
 
    private SQLiteDatabase db; 
    private final Context myContext;
 
    public DBHelper(Context context) {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
    public void createDataBase() throws IOException{										//creates the DB if it does not already exist in the application's db folder
 
    	if(!checkDB()){
        	this.getReadableDatabase();
 
        	try {
    			copyDB();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    private boolean checkDB(){																//checks for the existance of the db in the program's db folder
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String p = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(p, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    	
    	}
 
    	if(checkDB != null) checkDB.close();
    	
    	return checkDB != null ? true : false;
    }
 
    private void copyDB() throws IOException{													//copies db from asset folder into the program's db folder
    	
    	String oFileName = DB_PATH + DB_NAME;
    	InputStream iStream = myContext.getAssets().open(DB_NAME);
    	OutputStream oStream = new FileOutputStream(oFileName);
    	byte[] buffer = new byte[1024];
    	int length;
    	
    	while ((length = iStream.read(buffer))>0){
    		oStream.write(buffer, 0, length);
    	}

    	oStream.flush();
    	oStream.close();
    	iStream.close();
    }
 
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(db != null)
    		    db.close();
 
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
	
//////////////////SQL SELECT STATEMENTS/////////////////////////
	
	public Cursor selectAllNids(){
		return db.rawQuery("SELECT nID FROM tblNode", null);
	}
	
	public Cursor selectFloorNodes(int floor){																//returns a recordset containing all Nodes on a given floor
		return db.rawQuery("SELECT * FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor selectNodeNeighbors(String NodeID){											//returns a recordset containing all neighbor Nodes of the string[] of Node ID's
		String w = "'" + NodeID + "'";
		return db.rawQuery("SELECT * FROM tblNeighbors WHERE mNode = " + w, null );
	}
 
	public Cursor selectNodeType(int nFloor, ArrayList<String> nType){										//returns a recordset containing all Nodes with a given nType[]
		String w = concatOr(nType, "nType");

		return db.rawQuery("SELECT * FROM tblNode WHERE nType = " + w, null);
	}
	
	public int getNodeFloor(String nID){																//returns the floor a given Node is on
		int out;
		Cursor q = db.rawQuery("SELECT nFloor FROM tblNode WHERE nID = '" + nID + "'", null);
		q.moveToFirst();
		out = q.getInt(0);
		q.close();
		return out;
	}
	
	public Cursor selectNode(String nID){																//searches for a particular Node
		return db.rawQuery("SELECT * FROM tblNode WHERE nID = '" + nID + "'", null);
	}
	
	public Cursor selectValidNodeTypes(int floor){															//returns valid Node types in a given floor
		return db.rawQuery("SELECT DISTINCT nType FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor selectDepNodes(String nDep){																//returns Nodes in a given department
		return db.rawQuery("SELECT * FROM tblNode WHERE nDep = '" + nDep + "'", null);
	}
	
	public Cursor selectFloorDeps(int floor){																//returns departments on a given floor
		return db.rawQuery("SELECT DISTINCT nDep FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor selectDepFloors(String nDep){															//returns the floor(s) a department is on
		return db.rawQuery("SELECT DISTINCT nFloor FROM tblNode WHERE nDep = '" + nDep + "'", null);
	}
	
	public Cursor selectInterFloor(int floorA, int floorB){												//returns connecting relationships between floors A and B										
		return db.rawQuery(	"SELECT mNode, nNode " +
							"FROM tblInterFloor " +
							"INNER JOIN tblNode AS tblmNode " +
								"ON tblmNode.nID = tblInterFloor.mNode " +
							"INNER JOIN tblNode AS tblnNode " +
								"ON tblnNode.nID = tblInterFloor.nNode " +
							"WHERE (tblmNode.nFloor = " + floorA + " OR tblmNode.nFloor = " + floorB + ")" + 
								"AND (tblnNode.nFloor = " + floorA + " OR tblnNode.nFloor = " + floorB + ")", null);
	}
	
////////////////////////////////PROGRAM METHODS////////////////////////////////////
	
	public ArrayList<String> getAllNids(){
		ArrayList<String> out = new ArrayList<String>();
		Cursor cursor;
		
		cursor = this.selectAllNids();
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			out.add(cursor.getString(0));
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return out;
	}
	
	
	public Hashtable<String, Node> buildFloorNodes(int floor){					//Constructs and returns a hashtable of Nodes, keyed by their nID's
    	Node NodeM;
    	Hashtable<String, Node> ht = new Hashtable<String, Node>();
    	ArrayList<String> NodeIDs = new ArrayList<String>();
    	Cursor cursor;
    	
    	cursor = this.selectFloorNodes(floor);										//Query DB for Nodes on a given floor
        cursor.moveToFirst();
        
        while(!cursor.isAfterLast()){											//Fill Hashtable for given floor
        	NodeM = new Node(	cursor.getString(1),								//nID
        						cursor.getInt(2),									//x
        						cursor.getInt(3),									//y
        						cursor.getInt(4),									//nFloor
        						cursor.getString(5),								//nType
        						cursor.getString(6));								//nDep
        	
        	ht.put(NodeM.getNodeID(), NodeM);									//add Node to hashtable with corresponding NodeID as the key
        	NodeIDs.add(NodeM.getNodeID());										//build string of NodeID's to query neighbor table
        	
        	cursor.moveToNext();												//move to next record
        }
        
        buildNeighborNodes(ht, NodeIDs);
        cursor.close();
        return ht;
    }
    
    protected void buildNeighborNodes(Hashtable<String, Node> ht, ArrayList<String> nIDs){
    	String stringM, stringN;
    	Node NodeM, NodeN;
    	Cursor cursor;
    	
    	for(String it:nIDs){
    		cursor = this.selectNodeNeighbors(it);
    		
    		cursor.moveToFirst();
            
            while(!cursor.isAfterLast()){
            	stringM = cursor.getString(1);							//"Master" NodeID
            	stringN = cursor.getString(2);							//"Neighbor" NodeID
            	
            	NodeM = ht.get(stringM);								//Retrieve master Node from hashtable
            	NodeN = ht.get(stringN);								//Retrieve neighbor Node from hashtable
            	
            	NodeM.addNeighbor(NodeN);								//Add neighbor Node to master Node
            	
            	cursor.moveToNext();
            }
            cursor.close();
    	}
    }
    
    public void buildInterFloor(int floorA, int floorB, Hashtable<String, Node> ht){
    	String stringM, stringN;
    	Node nodeM, nodeN;
    	Cursor cursor = selectInterFloor(floorA, floorB);
    	
    	cursor.moveToFirst();
    	
    	while(!cursor.isAfterLast()){
    		stringM = cursor.getString(0);
    		stringN = cursor.getString(1);
    		
    		nodeM = ht.get(stringM);								//Retrieve master Node from hashtable
        	nodeN = ht.get(stringN);								//Retrieve neighbor Node from hashtable
        	
        	nodeM.addNeighbor(nodeN);								//Add neighbor Node to master Node
        	
        	cursor.moveToNext();
    	}
    	
    	cursor.close();
    	
    }
	
	
	
	
	
////////////INTERNAL METHODS//////////////////////////
	
	private String concatOr(ArrayList<String> sIn, String cName){										//concats an array of strings into a single string seperated by OR's
		String sOut = "";
		
		if (sIn.size() >= 1) {
			sOut = "'" + sIn.get(0) + "'";
			
			if (sIn.size() > 1){
			for(int i = 1; i < sIn.size(); i++)
				sOut += " OR " + cName + " = " + "'" + sIn.get(i) + "'";
			}
		}
		return sOut;
	}
	
}
