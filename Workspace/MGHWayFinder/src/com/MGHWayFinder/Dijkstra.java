package com.MGHWayFinder;

import java.util.ArrayList;

public class Dijkstra {
    
	private static final int INFINITY = Integer.MAX_VALUE;
	private ArrayList<Node> S = new ArrayList<Node>();					//list of settled Nodes		(shortest distance found)
	private ArrayList<Node> Q = new ArrayList<Node>();					//list of unsettled Nodes	(distances not yet found)
	
	private Node STARTNode;												//ALL POTENTIAL PATHS ARE BUILT FROM THE ORIGIN Node
	private static final int OFFSET = 90;								//MAP "ORIGIN" of Y+ = 0*, INSTEAD OF X+
	private String nID = null;

	public Dijkstra(Node START){
		this.STARTNode = START;
		dijkstraAlgorithm(START);
		calculateNodeAngles();
	}
	
	public boolean reset(){												//clears all info in the dijkstra table, prepping for a new run
		for(Node n: S)
			n.reset();
		STARTNode = null;
		S.clear();
		Q.clear();
		return true;
	}
	
	public void restart(Node START){									//recalculates all paths from Node START
		this.STARTNode = START;
		dijkstraAlgorithm(START);
		calculateNodeAngles();
	}
	 
    public ArrayList<Node> getPath(Node END){
    	ArrayList<Node> P = new ArrayList<Node>();
    	
    	P.add(END);														//initialize end Node
		while(P.get(0) != STARTNode)									//loop backwards from end Node until beginning Node
			P.add(0, P.get(0).getPreviousNode());						//reverse stacking of Nodes	
		return P;
    }
    
    public Node getStart(){
    	return this.STARTNode;
    }
    
	protected void dijkstraAlgorithm(Node START){
		Node u;															//Node place holder in the loop
		
		Q.add(START);													//starts by adding the starting point to the Q of unsettled Nodes 	(EMPTY BEFORE ADD)
		START.setBestDistance(0);										//initializes starting distance of the start Node to 0				(BEST DISTANCE FROM STARTING POINT = 0)
		
		while(!Q.isEmpty()){											//loops so long as there are elements in Q 							(ELEMENTS ARE REMOVED FROM Q IN getMinimumNode() AND ADDED in relaxNeighbors())
			u = getMinimumNode();										//set u to the min Node distance in ArrayList Q
			S.add(u);													//add u to the ArrayList S											(NodeS WITH MINIMUM DISTANCES FOUND)
			relaxNeighbors(u);											//tests neighbor Nodes, see function below							
		}
	}
	
	protected void relaxNeighbors(Node v){
		Node o = null;
		double dist;
		
		for(int i = 0; i < v.getNeighbors().size(); i++){						//loop through neighbors of Node v
			o = v.getNeighbors().get(i);
			if(!S.contains(o)) {												//only look at neighbors NOT in S
				if(o.getNodeFloor() != v.getNodeFloor()){						//connections between floors are treated as the same node on different floors
					dist = 0;
					
				}
				else
					dist = cDistance(v, o);										//calculate distance between v and o
				if(o.getBestDistance() > (v.getBestDistance() + dist)){			//shorter distance found
					o.setBestDistance(dist + v.getBestDistance());				//set best distance of Node o from start
					o.setPNode(v);												//set best previous Node to v
					o.setPNodeDistance(dist);									//set intermediate distance from o-v
					Q.add(o);													//add Node o to Q
				}
				
			}
		}
	}
	
	protected double cDistance(Node a, Node b){									//calculates distances between Nodes
		double x = a.getX()-b.getX();
		double y = a.getY()-b.getY();
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	protected Node getMinimumNode(){												//returns the Node from the arrayList Q with the smallest distance from the starting point
		Node out = null;
		double min = INFINITY;
		
		for(int i = 0; i < Q.size(); i++){
			if(Q.get(i).getBestDistance() < min){
				min = Q.get(i).getBestDistance();
				out = Q.get(i);
			}
		}
		Q.remove(out);															//removes the minimum Node from Q
		return out;
	}
	
	protected void calculateNodeAngles(){											//called after full table is built, calculates all of the Nodes angles to their previous Node in the shortest path.
		int angle;
		int x, y;
    	for(Node it:S){
    		if(it != STARTNode){
    			x = (it.getX() - it.getPreviousNode().getX());
    			y = (it.getY() - it.getPreviousNode().getY());
    			
    			angle = (int)Math.round(Math.toDegrees(Math.atan2(y, x)));
    			angle += OFFSET;
    			it.setPNodeAngle(angle);
    		}
    	}
    }
	
}
