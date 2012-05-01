package com.MGHWayFinder;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class List extends ArrayAdapter<Node> {
	
	ArrayList<Node> nodes;
	ArrayList<String> nodeList;
	//private ArrayAdapter<String> adapt;
	
	public List(Context context, int textViewResourceId, ArrayList<Node> nodes) {
        super(context, textViewResourceId, nodes);
        this.nodes = nodes;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row, null);
        }
        

                
        
        return v;
}
	

}
