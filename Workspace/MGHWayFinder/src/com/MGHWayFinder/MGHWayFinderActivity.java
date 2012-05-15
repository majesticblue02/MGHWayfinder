package com.MGHWayFinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.graphics.PorterDuff;

public class MGHWayFinderActivity extends Activity {
	
	
	public static Hashtable<String, Node> masterHash = new Hashtable<String,Node>();			//MASTER HASH TABLE CONTAINING ALL VALID NODES
	
	private DBHelper db;
	private Spinner start, end;
	private Button go;
	private Button startQR;
	
	//START & END VARIABLES
	private String startSelect, endSelect;
	private ArrayAdapter<String> validDestinationsAA, startDestinationsAA;
	private ArrayList<String> allNodeIds, startDestinations;
	private ArrayList<String> validDestinations;
	private Hashtable<String, String> validDestinationsHT;
	private Hashtable<String, String> startHash;
	private boolean staffMode = false;
    
	// FLOOR PLAN VIEWER UI ELEMENTS
    private Spinner mapSpin;
	private ImageView viewMap;
	private String[] floors = {"Floor 1", "Floor 2"};
	private ArrayAdapter floorsAA;
	private String[] mapPath = {"floor1color.png", "floor2color.png"};
	private InputStream is;
	private BitmapFactory.Options op = new BitmapFactory.Options();
	private Bitmap mapBM;
	private AssetManager am;
	
	
	// DIRECTORY LISTVIEW VARIABLES & ELEMENTS
	private TextView dirHeading;
	private Spinner deptSpinner;
	private ArrayList<String> departments;
	private ArrayList<String> deptMembers;
	private Button findButton;
	private ArrayAdapter<String> deptMemberAdapter;
	private ListView lv;
	
	//OPTIONS MENU
	final int MENU1 = Menu.FIRST + 1;
	final int MENU2 = Menu.FIRST + 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        //setTitle("Delfin Wayfinder");
        
        db = new DBHelper(this.getApplicationContext());
        initializeDB();
        
        allNodeIds = db.getAllNids();
        Collections.sort(allNodeIds);	
        
        //tabs
        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        
        TabHost.TabSpec spec;
        
        Resources res = getResources();

//////////////////DIRECTIONS TAB//////////////////////
        //tab setup
        spec=tabs.newTabSpec("directions");
        spec.setContent(R.id.directionsTab);
        spec.setIndicator("Navigate", res.getDrawable(R.drawable.ic_tab_navigate));
        tabs.addTab(spec);
///////////////////UI ELEMENTS////////////////////////
        start = (Spinner)findViewById(R.id.startSpin);
        setStartSpinner();
        end = (Spinner)findViewById(R.id.endSpin);
        setEndSpinner();
        go = (Button)findViewById(R.id.goButton);
   
        
        go.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		startPathDraw();
        	}}); 
        
        //scan buttons
    	startQR = (Button)findViewById(R.id.scanStart);
    	startQR.setOnClickListener(new OnClickListener(){
    	    public void onClick(View v) {
    	    	Intent scanStart = new Intent("com.google.zxing.client.android.SCAN");   
    	        scanStart.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
    	        startActivityForResult(scanStart, 0);
    	    }});
    	
        
        //UI SETUP STUFF
        
//////////////////MAP TAB//////////////////////
//tab setup
    spec=tabs.newTabSpec("map");
    spec.setContent(R.id.mapTab);
    spec.setIndicator("Map", res.getDrawable(R.drawable.ic_tab_map));
    tabs.addTab(spec);
    
    View mapLayout = findViewById(R.id.mapTab);
    mapLayout.setBackgroundColor(Color.argb(255, 255, 255, 255));
    
    //stuff for map tab
	viewMap = (ImageView)findViewById(R.id.mapView);
	mapSpin = (Spinner)findViewById(R.id.mapSpinner);
	
	floorsAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, floors);
	mapSpin.setAdapter(floorsAA);
	
	am = getAssets();
	op.inPurgeable= true;
	
	mapSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
		//TODO
	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	    	try{
				is = am.open(mapPath[position]);											//A BITMAP FACTORY IS USED IN CONJUNCTION WITH AN INPUTSTREAM TO HELP CONTROL THE SIZE OF THE IMAGE
				mapBM = BitmapFactory.decodeStream(is, null, op);									//THE BMF OPTIONS HELP CONTROL THE RESULTING IMAGE SIZE IN ANDROID
				is.close();
				
				viewMap.setImageBitmap(mapBM);
			} catch(IOException e){
				
			}
	    }

	    public void onNothingSelected(AdapterView<?> parentView) {
	        //do nothing
	    }

	});

        


//////////////////DIRECTORY TAB////////////////////// ------KUNAL 
//tab setup
spec=tabs.newTabSpec("directory");
spec.setContent(R.id.dirTab);
spec.setIndicator("Directory", res.getDrawable(R.drawable.ic_tab_directory));
tabs.addTab(spec);


//inflate widgets
	dirHeading = (TextView)findViewById(R.id.dirHeading);
	deptSpinner = (Spinner)findViewById(R.id.deptSpinner);
	findButton = (Button)findViewById(R.id.findButton);
	lv = (ListView)findViewById(R.id.list);

	departments = db.getAllDepartments();
	deptMembers = db.getAllDeptMembers(departments.get(0));

	ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments); //for department spinner
	aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	deptSpinner.setAdapter(aa);

	deptMemberAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deptMembers); 		// for ListItems
	lv.setAdapter(deptMemberAdapter); //doesnt work 
	
	lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
		//@Override
		public void onItemClick(AdapterView<?> a, View v, int i, long l){
			//super.onItemClick(a, v, i,l);
			String itemClicked = (String)lv.getItemAtPosition(i);
			String lastName = itemClicked.substring(0, itemClicked.indexOf(","));
			String firstName = itemClicked.substring(itemClicked.indexOf(",") + 2);
			String phoneNumber = db.getMemberPhoneNo(firstName, lastName);
			Toast.makeText(MGHWayFinderActivity.this, phoneNumber, Toast.LENGTH_LONG).show();

		}
	});

	findButton.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
			String selectedDept = (String)deptSpinner.getSelectedItem(); 										//get value of department from spinner
			deptMembers = db.getAllDeptMembers(selectedDept);			 										//assign to deptMembers all of the members that are in that department
			deptMemberAdapter.clear();
			for(String it:deptMembers)
				deptMemberAdapter.add(it);
		}
	});
///////////////////UI ELEMENTS////////////////////////
//stuff for directory tab



//////////////////HELP TAB//////////////////////
//tab setup
spec=tabs.newTabSpec("help");
spec.setContent(R.id.helpTab);
spec.setIndicator("Help", res.getDrawable(R.drawable.ic_tab_help));
tabs.addTab(spec);
///////////////////UI ELEMENTS////////////////////////
//help tab stuff
View helpLayout = findViewById(R.id.helpTab);
helpLayout.setBackgroundColor(Color.argb(255, 0, 0, 0));



    }//end of oncreate    
    
    //OPTIONS MENU////////////////
	//options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU1, Menu.NONE, "Staff Mode");
		menu.add(Menu.NONE, MENU2, Menu.NONE, "Patient Mode");

		return (super.onCreateOptionsMenu(menu));
	}
	
	//resolve menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case MENU1:
			staffMode = true;
			setEndSpinner();
			//setStartSpinner();
			return true;
		case MENU2:
			staffMode = false;
			setEndSpinner();
			//setStartSpinner();
			return true;
		}
		return staffMode;	
	}
	
	//end start set
	public void setStartSpinner(){
        	startHash = db.getAllSpins();
        	startDestinations = new ArrayList<String>();
        	for(String it:startHash.keySet()){
        		startDestinations.add(it);
        	}
        	Collections.sort(startDestinations);																				//SORT ALPHABETICALLY
        	startDestinationsAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, startDestinations);
        	start.setAdapter(startDestinationsAA);
	}
	
	//end spinner set
	public void setEndSpinner(){
        if(staffMode){																											//CHECKS FOR PROGRAM MODE, SETS AVAILABLE DESTINATIONS ACCORDINGLY
        	end.setAdapter(startDestinationsAA);
        } else {
        	validDestinationsHT = db.getValidDestinations();
        	validDestinations = new ArrayList<String>();
        	for(String it:validDestinationsHT.keySet()){
        		validDestinations.add(it);
        	}
        	Collections.sort(validDestinations);																				//SORT ALPHABETICALLY
        	validDestinationsAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, validDestinations);
        	end.setAdapter(validDestinationsAA);
        }
	}

    
    //receive scan result back from scanner intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent scanStart) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String startnID = scanStart.getStringExtra("SCAN_RESULT");	//get the result from extra
                //test code
                Log.v("QR", startnID);
                
                
                //start.setSelection(startHash.get(startnID));
                //set spinner
            	for(int i=0; i < allNodeIds.size(); i++){
            		if(startnID.equals(allNodeIds.get(i))){
            			start.setSelection(i);
            		}
            	} 
            } else if (resultCode == Activity.RESULT_CANCELED) {
            	Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    //BUILDS THE NODE SET FROM THE SELECTED NODES, THEN STARTS THE PATHDRAWACTIVITY	
    protected void startPathDraw() {
    	startSelect = (String)start.getSelectedItem();
    	endSelect = (String)end.getSelectedItem();
    	
    	String startNId, endNId;
    	int startFloor, endFloor;
    	
    	startNId = startHash.get(startSelect);
    	startFloor = db.getNodeFloor(startNId);
    	
    	if(staffMode){																				//CHECK FOR USAGE MODE
    		endNId = endSelect;
    	} else {
    		//TODO add start here
    		startNId = startHash.get(startSelect);
    		endNId = validDestinationsHT.get(endSelect);
    	}

		endFloor = db.getNodeFloor(endNId);
    	
    //BUILD NODE SET
    	if(startFloor != endFloor){																	//If start and end are on different floors, build master node set of both floors
    		masterHash.putAll(db.buildFloorNodes(startFloor));
    		masterHash.putAll(db.buildFloorNodes(endFloor));										
   
    		db.buildInterFloor(startFloor, endFloor, masterHash);									//Create interconnections between these two floors only
    	} else {
    		masterHash.putAll(db.buildFloorNodes(startFloor));
    	}		
	//START PATHDRAWACTIVITY
		Intent drawPath = new Intent(this, PathDrawActivity.class);
		drawPath.putExtra("StartnID", startNId);
		drawPath.putExtra("EndnID", endNId);
        startActivity(drawPath);    	
	}
    
    //INITIALIZE DB
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
    
    public void contextDestination(){
    	//use this and onclick leading to it to create and open context menu with
    	//end destinations
    	Toast.makeText(this, "Context Menu", Toast.LENGTH_LONG).show();
    }
    
    
    //hello from calum - testing git
    
}//end of class
