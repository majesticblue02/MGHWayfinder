package com.MGHWayFinder;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List extends ListActivity {
	ArrayList<String> nodeList = new ArrayList<String>();
	private ArrayAdapter<String> adapt;
	
	
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listview);
	        
	        //nuts and bolts
	        adapt = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, nodeList);
	        setListAdapter(adapt);
	        
	        
	               
	    }
	    
	    	//start activity based on list item selected
	        @Override
	    	protected void onListItemClick(ListView l, View v, int position, long id) {
	    		super.onListItemClick(l, v, position, id);
	    		

	    	}
}
