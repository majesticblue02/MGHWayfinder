import java.util.ArrayList;

public class node {
	private static final int INFINITY = Integer.MAX_VALUE;
	private int x, y;													//coordinates on a map
	private String name;												//name of node
	private ArrayList<node> neighbors = new ArrayList<node>();			//corresponding connections
	private node pNode = null;											//previous node in the shortest path, initialized to null
	private double d = INFINITY;										//d is the shortest distance from the starting point to this node, initialized to "infinity"
	private int pNodeAngle = -1;									//once the dijkstra algorithm runs this will be set to the compass degree of the node's directionality to the previous waypoint (0 for North, valid values 0-359)
	private double pNodeDist = INFINITY;							//distance to the previous node (stored to reduce calculations)
	
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
	
	public double getBestDistance(){
		return d;
	}
	
	public node getPreviousNode(){
		return pNode;
	}
	
	public double getPNodeDistance(){
		return pNodeDist;
	}
	
	public double getPNodeAngle(){
		return pNodeAngle;
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
