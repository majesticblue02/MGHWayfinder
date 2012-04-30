package com.MGHWayFinder;

import java.util.ArrayList;

/*
 * This class defines an object representing a position on the map, a "Node".
 * It stores it's coordinates on the map, floor, a unique ID, department it is located in, and a type.
 * It also stores an ArrayList of its neighbor Nodes and other values used to calculate pathways in Dijkstra
 */

public class Node {
	private static final int INFINITY = Integer.MAX_VALUE;
	
	private int x, y;													//coordinates on a map
	private int nodeFloor;												//floor that the Node is on
	private String nodeID, nodeDep, nodeType;							//Node ID in DB, name of Node (UI purposes), type of Node
	private ArrayList<Node> neighbors = new ArrayList<Node>();			//corresponding connections
	private Node pNode = null;											//previous Node in the shortest path, initialized to null
	private double d = INFINITY;										//d is the shortest distance from the starting point to this Node, initialized to "infinity"
	private double pNodeDist = INFINITY;								//distance to the previous Node (stored to reduce calculations)
	
	private double nNodeAngle = -1;										//ONCE A PATH HAS BEEN BUILT (START TO END) THE ANGLE TO THE NEXT NODE IS STORED HERE
	private double nNodeDist = -1;										//ONCE A PATH HAS BEEN BUILT (START TO END) THE DISTANCE TO THE NEXT NODE IS STORED HERE
	private double stepDist = -1;										//ONCE A PATH HAS BEEN BUILT (START TO END) THE DISTANCE TO THE NEXT STEP NODE IS STORED HERE
	
	//CONSTRUCTOR
	public Node(String nID, int x, int y, int floor, String nType, String nDep){
		this.x = x;
		this.y = y;
		this.nodeID = nID;
		this.nodeDep = nDep;
		this.nodeType = nType;
		this.nodeFloor = floor;
	}
	
	//GETTERS AND SETTERS
	public void addNeighbor(Node neighbor){
		neighbors.add(neighbor);
	}
	
	public void setPNode(Node previous){
		pNode = previous;
	}
	
	public void setBestDistance(double dist){
		d = dist;
	}
	
	public void setNNodeAngle(double degree){
		nNodeAngle = degree;
	}
	
	public void setNNodeDistance(double dist){
		nNodeDist = dist;
	}
	
	public void setPNodeDistance(double dist){
		pNodeDist = dist;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getNodeID(){
		return nodeID;
	}
	
	public String getNodeDepartment(){
		return nodeDep;
	}
	
	public String getNodeType(){
		return nodeType;
	}
	
	public int getNodeFloor(){
		return nodeFloor;
	}
	
	public double getBestDistance(){
		return d;
	}
	
	public Node getPreviousNode(){
		return pNode;
	}
	
	public double getPNodeDistance(){
		return pNodeDist;
	}
	
	public double getNNodeDistance(){
		return nNodeDist;
	}
	
	public double getNNodeAngle(){
		return nNodeAngle;
	}
	
	public ArrayList<Node> getNeighbors(){
		return neighbors;
	}
	
	public String toString(){
		return "Floor " + nodeFloor + " - " + nodeDep;
	}
	
	public double getStepDist() {
		return stepDist;
	}

	public void setStepDist(double stepDist) {
		this.stepDist = stepDist;
	}
	
	//NODE FUNCTIONS
	public void reset(){													//USED TO RESET NODE VALUES RELATING TO DIJKSTRA CALCULATIONS
		d = INFINITY;
		pNode = null;
		pNodeDist = INFINITY;
		nNodeDist = -1;
		nNodeAngle = -1;
		stepDist = -1;
	}
}
