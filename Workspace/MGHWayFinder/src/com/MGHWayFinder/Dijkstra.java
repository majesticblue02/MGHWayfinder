package com.MGHWayFinder;

import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
    
	private static final int INFINITY = Integer.MAX_VALUE;
	private ArrayList<Node> S = new ArrayList<Node>();					//list of settled Nodes		(shortest distance found)
	private ArrayList<Node> Q = new ArrayList<Node>();					//list of unsettled Nodes	(distances not yet found)
	
	private Node STARTNode;												//ALL POTENTIAL PATHS ARE BUILT FROM THE ORIGIN Node
	private Node breakNode = null;										//BREAKNODE USED FOR INTERFLOOR CALCULATIONS / PATHS

	public Dijkstra(Node START){
		this.STARTNode = START;
		dijkstraAlgorithm(START);
	}
	
	public boolean reset(){												//clears all info in the dijkstra table, prepping for a new run
		for(Node n: S)
			n.reset();
		STARTNode = null;
		breakNode = null;
		S.clear();
		Q.clear();
		return true;
	}
	
	public void restart(Node START){									//recalculates all paths from Node START
		this.STARTNode = START;
		dijkstraAlgorithm(START);
	}
	 
	//RETURNS AN ARRAYLIST OF THE PATH FROM START TO END IN ORDER OF 0 -> END
    public ArrayList<Node> getPath(Node END){
    	ArrayList<Node> P = new ArrayList<Node>();
    	
    	P.add(END);														//initialize end Node
		while(P.get(0) != STARTNode)									//loop backwards from end Node until beginning Node
			P.add(0, P.get(0).getPreviousNode());						//reverse stacking of Nodes	
		
		calculatePathAngleDist(P);											//CALCULATES NODE ANGLES
		stripIntermediateSteps(P);
		return P;
    }
    
    public Node getStart(){
    	return this.STARTNode;
    }
    
    public Node getBreakNode(){
		return breakNode;
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
	
	//ALGORITHIM USED TO EXPLORE THE NEIGHBORS OF NODE V
	protected void relaxNeighbors(Node v){
		Node o = null;
		double dist;
		
		for(int i = 0; i < v.getNeighbors().size(); i++){						//loop through neighbors of Node v
			o = v.getNeighbors().get(i);
			if(!S.contains(o)) {												//only look at neighbors NOT in S
				if(o.getNodeFloor() != v.getNodeFloor()){						//connections between floors are treated as the same node on different floors
					dist = 0;
					breakNode = o;
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
	
	//CALCULATES DISTANCE BETWEEN TWO NODES
	protected double cDistance(Node a, Node b){	
		double x = a.getX()-b.getX();
		double y = a.getY()-b.getY();
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	//RETURNS THE NODE FROM THE ARRAYLIST Q WITH THE SMALLEST DISTANCE FROM THE STARTING POINT
	protected Node getMinimumNode(){												
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
	
	//CALLED AFTER PATH (START TO FINISH) IS CREATED - CALCULATES NODE -> NODE ANGLES & DISTANCE
	protected void calculatePathAngleDist(ArrayList<Node> path){				
		double angle, dist;
		int dx, dy;
		
		for(int i = 0; i < path.size()-1; i++){
			dx = (path.get(i).getX() - path.get(i+1).getX());						//DELTA X
			dy = (path.get(i).getY() - path.get(i+1).getY());						//DELTA Y
			
			dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));					//DISTANCE CALCULATION
			path.get(i).setNNodeDistance(dist);
			
			angle = Math.toDegrees(Math.atan2(dy, dx));								//ATAN FUNCTION CALCULATES ANGLE
			path.get(i).setNNodeAngle(angle);
		}
    }
	
	protected void stripIntermediateSteps(ArrayList<Node> listIn){
		Node currentNode, nextNode;
		double runningDist = 0;
		int i;
		
		for(i = listIn.size()-2; i > 0; i--){												//LOOP THROUGH UP TO SECOND TO LAST NODE, ONLY ADDING CHANGES IN DIRECTION
			currentNode = listIn.get(i);
			nextNode = listIn.get(i+1);
			
			runningDist += currentNode.getNNodeDistance();
			
			if(currentNode.getNNodeAngle() == nextNode.getNNodeAngle()) {
				listIn.remove(i+1);
				
			} else {
				currentNode.setStepDist(runningDist);
				runningDist = 0;
			}
		}

		listIn.removeAll(Collections.singletonList(null));
	}
}
