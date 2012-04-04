package com.DBProto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static String PACKAGE = "com.DBProto";
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
	
	
	public Cursor getFloorNodes(int floor){																//returns a recordset containing all nodes on a given floor
		return db.rawQuery("SELECT * FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor getNodeNeighbors(ArrayList<String> nodeIDs){											//returns a recordset containing all neighbor nodes of the string[] of node ID's
		String w = concatOr(nodeIDs, "mNode");
				
		return db.rawQuery("SELECT * FROM tblNeighbors WHERE mNode = " + w, null);
	}
 
	public Cursor getNodeType(int nFloor, ArrayList<String> nType){										//returns a recordset containing all nodes with a given nType[]
		String w = concatOr(nType, "nType");

		return db.rawQuery("SELECT * FROM tblNode WHERE nType = " + w, null);
	}
	
	public int getNodeFloor(String nID){																//returns the floor a given node is on
		Cursor q = db.rawQuery("SELECT nFloor FROM tblNode WHERE nID = " + nID, null);
		q.moveToFirst();
		return q.getInt(4);
	}
	
	public Cursor searchNode(String nID){																//searches for a particular node
		return db.rawQuery("SELECT * FROM tblNode WHERE nID = " + nID, null);
	}
	
	public Cursor getValidNodeTypes(int floor){															//returns valid node types in a given floor
		return db.rawQuery("SELECT DISTINCT nType FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor getDepNodes(String nDep){																//returns nodes in a given department
		return db.rawQuery("SELECT * FROM tblNode WHERE nDep = " + nDep, null);
	}
	
	public Cursor getFloorDeps(int floor){																//returns departments on a given floor
		return db.rawQuery("SELECT DISTINCT nDep FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor getDepFloors(String nDep){															//returns the floor(s) a department is on
		return db.rawQuery("SELECT DISTINCT nFloor FROM tblNode WHERE nDep = " + nDep, null);
	}
	
	
	
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
