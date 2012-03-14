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
 
    public void createDataBase() throws IOException{
    	
    	boolean dbExist = checkDB();
 
    	if(!dbExist){
        	this.getReadableDatabase();
 
        	try {
    			copyDB();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    private boolean checkDB(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String p = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(p, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    	
    	}
 
    	if(checkDB != null) checkDB.close();
    	
    	return checkDB != null ? true : false;
    }
 
    private void copyDB() throws IOException{
    	
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
 
	public Cursor getFloorNodes(int floor){													//returns a recordset containing all nodes on a given floor
		return db.rawQuery("SELECT * FROM tblNode WHERE nFloor = " + floor, null);
	}
	
	public Cursor getNodeNeighbors(ArrayList<String> nodeIDs){										//returns a recordset containing all neighbor nodes of the string[] of node ID's
		String w = concatOr(nodeIDs, "mNode");
				
		return db.rawQuery("SELECT * FROM tblNeighbors WHERE mNode = " + w, null);
	}
 
	public Cursor getNodeType(int nFloor, ArrayList<String> nType){									//returns a recordset containing all nodes with a given nType[]
		String w = concatOr(nType, "nType");

		return db.rawQuery("SELECT * FROM tblNode WHERE nType = " + w, null);
	}
	
	public int getNodeFloor(String nID){
		Cursor q = db.rawQuery("SELECT nFloor FROM tblNode WHERE nID = " + nID, null);
		q.moveToFirst();
		return q.getInt(4);
	}
	
	private String concatOr(ArrayList<String> sIn, String cName){									//concats an array of strings into a single string seperated by OR's
		String sOut = "";
		
		if (sIn.size() >= 1) {
			sOut = sIn.get(0);
			
			if (sIn.size() > 1){
			for(int i = 1; i < sIn.size(); i++)
				sOut += " OR " + cName + " = " + sIn.get(i);
			}
		}
		return sOut;
	}
	
}
