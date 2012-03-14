package com.finalProject.wpAlgorithm;

import java.util.ArrayList;

public class node {
	private static final int INFINITY = Integer.MAX_VALUE;
	private int x, y;													//coordinates on a map
	private String name;													//name of node
	private ArrayList<node> neighbors = new ArrayList<node>();			//corresponding connections
	private node pNode = null;											//previous node in the shortest path, initialized to null
	private int d = INFINITY;											//d is the shortest distance from the starting point to this node, initialized to "infinity"
	
	public node(String n, int x, int y){
		this.x = x;
		this.y = y;
		this.name = n;
	}
	
	public void addNeighbor(node neighbor){
		neighbors.add(neighbor);
	}
	
	public void setPNode(node previous){
		pNode = previous;
	}
	
	public void setBestDistance(int dist){
		d = dist;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getBestDistance(){
		return d;
	}
	
	public node getPreviousNode(){
		return pNode;
	}
	
	public ArrayList<node> getNeighbors(){
		return neighbors;
	}
	
	public String toString(){
		return name;
	}
	
	public void reset(){
		d = INFINITY;
		pNode = null;
	}
}
