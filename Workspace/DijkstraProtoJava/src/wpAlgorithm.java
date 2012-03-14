import java.util.*;

public class wpAlgorithm {
	private static final int INFINITY = Integer.MAX_VALUE;
	private static final int OFFSET = 90;
	
	static node n108Q, n108P_0, n108P, n108A1_0, n108A1_1, n108A1_2, 
	n108A1_3, n108A1_4, n108A1_5, n108A1_6, n108A1_7, n108A1_8, n108A1_9, 
	n108A1_10, n108A1_11, n108A1_12, n108O, n108L, n108K, n108I, n108J, nT1, 
	nT2, n108C, n108D, n108G, n108B, n108E, n108F, n108H, n108, n108_0, n108_1, 
	n108_2, n100C1, n100C2, nEL;

	static ArrayList<node> MAP = new ArrayList<node>();					
	
	static ArrayList<node> S = new ArrayList<node>();					//list of settled nodes		(shortest distance found)
	static ArrayList<node> Q = new ArrayList<node>();					//list of unsettled nodes	(distances not yet found)
	
	static node PATHSTART, PATHEND;												//****CHANGE THESE VALUES TO TEST, SET TO A VALID NODE
	

	public static void main(String[] args) {
		ArrayList<node> P = new ArrayList<node>();						//FINAL PATH
		String s;														//Test out string
		
		createMap2();
		
		PATHSTART = nEL;
		PATHEND = nT1;
		
		
		dijkstra(PATHSTART,PATHEND);
		
		P.add(PATHEND);													//initialize end node
		while(P.get(0) != PATHSTART){									//loop backwards from end node until beginning node
			P.add(0, P.get(0).getPreviousNode());						//reverse stacking of nodes
		}
		
		s = P.get(0).toString();										//first step in path
		
		calculateNodeAngles();
		
		for(int i = 1; i < P.size(); i++){
			s = s + " - " + P.get(i).getPNodeAngle() + " -> " + P.get(i).toString();
		}
		System.out.println(s);
	}
	
	private static void calculateNodeAngles(){
		int angle;
		int x, y;
    	for(node it:S){
    		if(it != PATHSTART){
    			x = (it.getX() - it.getPreviousNode().getX());
    			y = (it.getY() - it.getPreviousNode().getY());
    			
    			angle = (int)Math.round(Math.toDegrees(Math.atan2(y, x)));
    			angle += OFFSET;
    			it.setPNodeAngle(angle);
    		}
    	}
    }
	
	public static void dijkstra(node start, node goal){
		
		node u;															//node place holders in the loop
		
		Q.add(start);													//starts by adding the starting point to the Q of unsettled nodes
		start.setBestDistance(0);										//initializes starting distance to 0
		
		while(!Q.isEmpty()){
			u = getMinimumNode();										//set u to the min node distance in ArrayList Q
			S.add(u);													//add u to the ArrayList S
			relaxNeighbors(u);											//tests neighbor nodes, see function below
			
		}
	}
	
	public static void relaxNeighbors(node v){
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
	
	public static int cDistance(node a, node b){								//calculates distances on the fly, could be stored to reduce calculations
		double x = a.getX()-b.getX();
		double y = a.getY()-b.getY();
		return (int)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public static node getMinimumNode(){										//returns the node in a list of nodes with the smallest distance from the starting point
		node out = null;
		double min = INFINITY;
		for(int i = 0; i < Q.size(); i++){
			if(Q.get(i).getBestDistance() < min){
				min = Q.get(i).getBestDistance();
				out = Q.get(i);
			}
		}
		Q.remove(out);															//removes the minimum node from Q
		return out;
	}
	
	
	public static void createMap2(){
		
		//NODE CREATION
		n108Q = new node("108Q", 1428, 352);
		n108P_0 = new node("108P_1", 1523, 352);
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
		n108_2 = new node("108_2", 1453, 483);
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
	}
}
