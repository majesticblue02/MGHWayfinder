package com.finalProject.wpAlgorithm;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class DijkstraProtoActivity extends Activity {
    
	private  final int INFINITY = Integer.MAX_VALUE;
	node n108Q, n108P_0, n108P, n108A1_0, n108A1_1, n108A1_2, 
	n108A1_3, n108A1_4, n108A1_5, n108A1_6, n108A1_7, n108A1_8, n108A1_9, 
	n108A1_10, n108A1_11, n108A1_12, n108O, n108L, n108K, n108I, n108J, nT1, 
	nT2, n108C, n108D, n108G, n108B, n108E, n108F, n108H, n108, n108_0, n108_1, 
	n108_2, n100C1, n100C2, nEL;											//individual nodes			(used for example)
	
	 ArrayList<node> S = new ArrayList<node>();									//list of settled nodes		(shortest distance found)
	 ArrayList<node> Q = new ArrayList<node>();									//list of unsettled nodes	(distances not yet found)
	 ArrayList<node> map = new ArrayList<node>();								//full array of all nodes	(used for filling UI element)
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        createMap();															//FILLS OUT MAP
        
        //UI ELEMENTS
		Button go = (Button)this.findViewById(R.id.goButton);
		final Spinner start = (Spinner)this.findViewById(R.id.spinStart);
		final Spinner end = (Spinner)this.findViewById(R.id.spinEnd);
		final TextView path = (TextView)this.findViewById(R.id.path);
		
		//FILL SPINNERS
		ArrayAdapter<node> adapter = new ArrayAdapter<node>(this, android.R.layout.simple_spinner_item, map);
		start.setAdapter(adapter);
		end.setAdapter(adapter);
		
		go.setOnClickListener(new OnClickListener() {
			public void onClick(View v1) {
			
				node PATHSTART, PATHEND;
				ArrayList<node> P = new ArrayList<node>();						//FINAL PATH
				String s;
				
				PATHSTART = (node)start.getSelectedItem();
				PATHEND = (node)end.getSelectedItem();
	
				dijkstra(PATHSTART,PATHEND);
		
				P.add(PATHEND);													//initialize end node
				while(P.get(0) != PATHSTART){									//loop backwards from end node until beginning node
					P.add(0, P.get(0).getPreviousNode());						//reverse stacking of nodes
				}
		
				s = P.get(0).toString();										//first step in path
		
				for(int i = 1; i < P.size(); i++){
					s = s + " -> " + P.get(i).toString();
				}
				path.setText(s);
				
				
				//CLEAR ALL PREVIOUS DATA FOR NEXT LOOP
				P.clear();												//this needs to be cleared so when the loop runs again, it is starting with a fresh path
				S.clear();												//not sure that this needs to be cleared, I was running into issues and chose the shotgun approach
				Q.clear();												//not sure that this needs to be cleared, I was running into issues and chose the shotgun approach
				map.clear();											//I don't know why, but the spinner elements were having issues - this fixed it.
				s = "";													//reset path string
				resetNodes();											//THIS IS CRITCAL
				createMap();
			}
		});
    }
	
	public void dijkstra(node start, node goal){
		
		node u;															//node place holder in the loop
		
		Q.add(start);													//starts by adding the starting point to the Q of unsettled nodes 	(EMPTY BEFORE ADD)
		start.setBestDistance(0);										//initializes starting distance of the start node to 0				(BEST DISTANCE FROM STARTING POINT = 0)
		
		while(!Q.isEmpty()){											//loops so long as there are elements in Q 							(ELEMENTS ARE REMOVED FROM Q IN getMinimumNode() AND ADDED in relaxNeighbors())
			u = getMinimumNode();										//set u to the min node distance in ArrayList Q
			S.add(u);													//add u to the ArrayList S											(NODES WITH MINIMUM DISTANCES FOUND)
			relaxNeighbors(u);											//tests neighbor nodes, see function below							
		}
	}
	
	public void relaxNeighbors(node v){
		node o = null;
		int dist;
		for(int i = 0; i < v.getNeighbors().size(); i++){						//loop through neighbors of node v
			o = v.getNeighbors().get(i);
			if(!S.contains(o)) { 												//only look at neighbors NOT in S
				dist = cDistance(v, o);											//calculate distance between v and o
				if(o.getBestDistance() > (v.getBestDistance() + dist)){			//shorter distance found
					o.setBestDistance(dist + v.getBestDistance());				//set best distance of node o
					o.setPNode(v);												//set best previous node to v
					Q.add(o);													//add node o to Q
				}
			}
		}
	}
	
	public int cDistance(node a, node b){								//calculates distances on the fly, could be stored to reduce calculations		(I SUGGEST KEEPING THIS FOR OTHER REASONS)
		double x = a.getX()-b.getX();
		double y = a.getY()-b.getY();
		return (int)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public node getMinimumNode(){										//returns the node from the arrayList Q with the smallest distance from the starting point
		node out = null;
		int min = INFINITY;
		for(int i = 0; i < Q.size(); i++){
			if(Q.get(i).getBestDistance() < min){
				min = Q.get(i).getBestDistance();
				out = Q.get(i);
			}
		}
		Q.remove(out);															//removes the minimum node from Q
		return out;
	}
	
	public void resetNodes(){
		for(node it:map){
			it.reset();
		}
	}
	
	
	public void createMap(){
		
	// INITIALIZING THE NODES FOR THE MAP <-> THIS COULD BE LOOPED OUT OF A DB
		n108Q = new node("108Q", 1428, 352);
		n108P_0 = new node("108P_0", 1523, 352);
		n108P = new node("108P", 1523, 413);
		n108A1_0 = new node("108A1_0", 1523, 483);
		n108A1_1 = new node("108A1_1", 1560, 483);
		n108A1_2 = new node("108A1_2", 1582, 483);
		n108A1_3 = new node("108A1_3", 1594, 483);
		n108A1_4 = new node("108A1_4", 1614, 483);
		n108A1_5 = new node("108A1_5", 1665, 483);
		n108A1_6 = new node("108A1_6", 1680, 483);
		n108A1_7 = new node("108A1_7", 1695, 483);
		n108A1_8 = new node("108A1_8", 1710, 483);
		n108A1_9 = new node("108A1_9", 1730, 483);
		n108A1_10 = new node("108A1_10", 1744, 483);
		n108A1_11 = new node("108A1_11", 1781, 483);
		n108A1_12 = new node("108A1_12", 1835, 483);
		n108O = new node("108O", 1582, 413);
		n108L = new node("108L", 1710, 413);
		n108K = new node("108K", 1744, 413);
		n108I = new node("108I", 1835, 413);
		n108J = new node("108J", 1781, 456);
		nT1 = new node("T1", 1680, 456);
		nT2 = new node("T2", 1614, 456);
		n108C = new node("108C", 1594, 504);
		n108D = new node("108D", 1665, 504);
		n108G = new node("108G", 1781, 504);
		n108B = new node("108B", 1560, 547);
		n108E = new node("108E", 1695, 542);
		n108F = new node("108F", 1730, 542);
		n108H = new node("108H", 1835, 542);
		n108 = new node("108", 1523, 637);
		n108_0 = new node("108_0", 1523, 573);
		n108_1 = new node("108_1", 1453, 573);
		n108_2 = new node("108_2", 1453, 413);
		n100C1 = new node("100C1", 1523, 697);
		n100C2 = new node("100C2", 1193, 697);
		nEL = new node("EL", 1193, 483);

	//NEIGHBORING
		n108Q.addNeighbor(n108P_0);
		n108P_0.addNeighbor(n108Q);
		n108P_0.addNeighbor(n108P);
		n108P.addNeighbor(n108P_0);
		n108P.addNeighbor(n108A1_0);
		n108A1_0.addNeighbor(n108P);
		n108A1_0.addNeighbor(n108_2);
		n108A1_0.addNeighbor(n108A1_1);
		n108A1_1.addNeighbor(n108A1_0);
		n108A1_1.addNeighbor(n108B);
		n108A1_1.addNeighbor(n108A1_2);
		n108A1_2.addNeighbor(n108A1_1);
		n108A1_2.addNeighbor(n108O);
		n108A1_2.addNeighbor(n108A1_3);
		n108A1_3.addNeighbor(n108A1_2);
		n108A1_3.addNeighbor(n108C);
		n108A1_3.addNeighbor(n108A1_4);
		n108A1_4.addNeighbor(n108A1_3);
		n108A1_4.addNeighbor(nT2);
		n108A1_4.addNeighbor(n108A1_5);
		n108A1_5.addNeighbor(n108A1_4);
		n108A1_5.addNeighbor(n108D);
		n108A1_5.addNeighbor(n108A1_6);
		n108A1_6.addNeighbor(n108A1_5);
		n108A1_6.addNeighbor(nT1);
		n108A1_6.addNeighbor(n108A1_7);
		n108A1_7.addNeighbor(n108A1_6);
		n108A1_7.addNeighbor(n108E);
		n108A1_7.addNeighbor(n108A1_8);
		n108A1_8.addNeighbor(n108A1_7);
		n108A1_8.addNeighbor(n108L);
		n108A1_8.addNeighbor(n108A1_9);
		n108A1_9.addNeighbor(n108A1_8);
		n108A1_9.addNeighbor(n108F);
		n108A1_9.addNeighbor(n108A1_10);
		n108A1_10.addNeighbor(n108A1_9);
		n108A1_10.addNeighbor(n108K);
		n108A1_10.addNeighbor(n108A1_11);
		n108A1_11.addNeighbor(n108A1_10);
		n108A1_11.addNeighbor(n108J);
		n108A1_11.addNeighbor(n108G);
		n108A1_11.addNeighbor(n108A1_12);
		n108A1_12.addNeighbor(n108A1_11);
		n108A1_12.addNeighbor(n108I);
		n108A1_12.addNeighbor(n108H);
		n108O.addNeighbor(n108A1_2);
		n108L.addNeighbor(n108A1_8);
		n108K.addNeighbor(n108A1_10);
		n108I.addNeighbor(n108A1_12);
		n108J.addNeighbor(n108A1_11);
		nT1.addNeighbor(n108A1_6);
		nT2.addNeighbor(n108A1_4);
		n108C.addNeighbor(n108A1_3);
		n108D.addNeighbor(n108A1_5);
		n108G.addNeighbor(n108A1_11);
		n108B.addNeighbor(n108A1_1);
		n108E.addNeighbor(n108A1_7);
		n108F.addNeighbor(n108A1_9);
		n108H.addNeighbor(n108A1_12);
		n108.addNeighbor(n100C1);
		n108.addNeighbor(n108_0);
		n108_0.addNeighbor(n108);
		n108_0.addNeighbor(n108_1);
		n108_1.addNeighbor(n108_0);
		n108_1.addNeighbor(n108_2);
		n108_2.addNeighbor(n108_1);
		n108_2.addNeighbor(n108A1_0);
		n100C1.addNeighbor(n100C2);
		n100C1.addNeighbor(n108);
		n100C2.addNeighbor(nEL);
		n100C2.addNeighbor(n100C1);
		nEL.addNeighbor(n100C2);		
		
	//MAP FILLING
		map.add(n108Q);
		map.add(n108P_0);
		map.add(n108P);
		map.add(n108A1_0);
		map.add(n108A1_1);
		map.add(n108A1_2);
		map.add(n108A1_3);
		map.add(n108A1_4);
		map.add(n108A1_5);
		map.add(n108A1_6);
		map.add(n108A1_7);
		map.add(n108A1_8);
		map.add(n108A1_9);
		map.add(n108A1_10);
		map.add(n108A1_11);
		map.add(n108A1_12);
		map.add(n108O);
		map.add(n108L);
		map.add(n108K);
		map.add(n108I);
		map.add(n108J);
		map.add(nT1);
		map.add(nT2);
		map.add(n108C);
		map.add(n108D);
		map.add(n108G);
		map.add(n108B);
		map.add(n108E);
		map.add(n108F);
		map.add(n108H);
		map.add(n108);
		map.add(n108_0);
		map.add(n108_1);
		map.add(n108_2);
		map.add(n100C1);
		map.add(n100C2);
		map.add(nEL);
	}
}
