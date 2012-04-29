package com.MGHWayFinder;

import java.util.ArrayList;

/*
 * This class defines an object which calculates all possible paths from STARTNode, through all of it's corresponding neighbor relationships.
 * It stores this "table" by setting the "shortest step" node previous to the current node as a variable in the Node class.
 * Paths are then queried by stepping backwards from Node to Node until the STARTNode is reached.
 */


public class Dijkstra {
    
	private static final int INFINITY = Integer.MAX_VALUE;
	private ArrayList<Node> S = new ArrayList<Node>();					//LIST OF SETTLED NODES		(SHORTEST DISTANCE FROM STARTNODE FOUND)
	private ArrayList<Node> Q = new ArrayList<Node>();					//LIST OF UNSETTLED NDOES	(SHORTEST DISTANCES NOT YET FOUND)
	
	private Node STARTNode;												//ALL POTENTIAL PATHS ARE BUILT FROM THE ORIGIN Node
	private Node breakNode = null;										//BREAKNODE USED FOR INTERFLOOR CALCULATIONS / PATHS

	public Dijkstra(Node START){
		this.STARTNode = START;
		dijkstraAlgorithm(START);
	}
	
	//RESETS ALL OF THE VARIABLES IN THE DIJKSTRA TABLES, PREPPING FOR A NEW RUN
	public boolean reset(){
		for(Node n: S)
			n.reset();
		STARTNode = null;
		breakNode = null;
		S.clear();
		Q.clear();
		return true;
	}
	
	//RECALCULATES ALL PATHS FROM THE NEW START NODE
	public void restart(Node START){
		if(STARTNode != null)
			reset();
		this.STARTNode = START;
		dijkstraAlgorithm(START);
	}
	 
	//RETURNS AN ARRAYLIST OF THE PATH FROM START TO END IN ORDER OF 0 -> END
    public ArrayList<Node> getPath(Node END){
    	ArrayList<Node> P = new ArrayList<Node>();
    	
    	P.add(END);																		//initialize end Node
		while(P.get(0) != STARTNode)													//loop backwards from end Node until beginning Node
			P.add(0, P.get(0).getPreviousNode());										//reverse stacking of Nodes	
		
		calculatePathAngleDist(P);														//CALCULATES NODE ANGLES
		
		if(STARTNode.getNodeFloor() != END.getNodeFloor()){								//SET BREAKNODE IN THIS CURRENT PATH
			for(int i = P.indexOf(STARTNode)+1; i <= P.indexOf(END); i++){
				if(P.get(i).getNodeFloor() != P.get(i).getPreviousNode().getNodeFloor())
					this.breakNode = P.get(i);
			}
		}
		return P;
    }
    
    public Node getStart(){
    	return this.STARTNode;
    }
    
    public Node getBreakNode(){
		return breakNode;
	}
    
    //MAIN DIJKSTRA LOOP
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
				dist = 0;														//THEREFOR, THE DISTANCE BETWEEN THEM IS EXPRESSED AS 0
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
}
