package com.MGHWayFinder;

import java.util.ArrayList;

public class Node {
	private static final int INFINITY = Integer.MAX_VALUE;
	
	private int x, y;													//coordinates on a map
	private int nodeFloor;												//floor that the Node is on
	private String nodeID, nodeDep, nodeType;							//Node ID in DB, name of Node (UI purposes), type of Node
	private ArrayList<Node> neighbors = new ArrayList<Node>();			//corresponding connections
	private Node pNode = null;											//previous Node in the shortest path, initialized to null
	private double d = INFINITY;										//d is the shortest distance from the starting point to this Node, initialized to "infinity"
	private int pNodeAngle = -1;										//once the dijkstra algorithm runs this will be set to the compass degree of the Node's directionality to the previous waypoint (0 for North, valid values 0-359)
	private double pNodeDist = INFINITY;								//distance to the previous Node (stored to reduce calculations)
	
	public Node(String nID, int x, int y, int floor, String nType, String nDep){
		this.x = x;
		this.y = y;
		this.nodeID = nID;
		this.nodeDep = nDep;
		this.nodeType = nType;
		this.nodeFloor = floor;
	}
	
	public void addNeighbor(Node neighbor){
		neighbors.add(neighbor);
	}
	
	public void setPNode(Node previous){
		pNode = previous;
	}
	
	public void setBestDistance(double dist){
		d = dist;
	}
	
	public void setPNodeAngle(int degree){
		pNodeAngle = degree;
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
	
	public double getPNodeAngle(){
		return pNodeAngle;
	}
	
	public ArrayList<Node> getNeighbors(){
		return neighbors;
	}
	
	public String toString(){
		return nodeID + " - " + nodeDep;
	}
	
	public void reset(){
		d = INFINITY;
		pNode = null;
		pNodeAngle = -1;
	}
}
