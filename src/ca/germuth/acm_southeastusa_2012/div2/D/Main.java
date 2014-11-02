package ca.germuth.acm_southeastusa_2012.div2.D;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Dueling Philosophers 
 * ACM ICPC SouthEast USA 2012 Regionals
 * Problem D Division 2
 * 
 * This problem represents nodes (essay) depending on other nodes (essays). Any cycle in the graph
 * means there is no solution, and whenever there are two choices for the next node, there are
 * multiple solutions. 
 * This can be done with topological sort, grabbing all nodes with degree 0 one at a time.
 * 
 * @author germuth
 */

class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (true) {
			int n = s.nextInt();
			int m = s.nextInt();

			if (n == 0) {
				break;
			}

			Node[] graph = new Node[n];
			for (int i = 0; i < n; i++) {
				graph[i] = new Node(i, 0, new ArrayList<Node>());
			}

			for (int i = 0; i < m; i++) {
				int from = s.nextInt() - 1;
				int to = s.nextInt() - 1;

				graph[from].neighbours.add(graph[to]);
				graph[to].indegree++;
			}
			
			//find all nodes with degree 0
			ArrayList<Node> degree0 = new ArrayList<Node>();
			for(Node no: graph){
				if(no.indegree == 0){
					degree0.add(no);
				}
			}
			
			boolean multipleOptions = false;
			int nodesFound = 0;
			while(!degree0.isEmpty()){
				if(degree0.size() > 1){
					multipleOptions = true;
				}
				//grab node with degree 0 and remove from list
				Node removing = degree0.get(degree0.size() - 1);
				degree0.remove(degree0.size() - 1);
				nodesFound++;
				
				//decrement indegrees of all neighbours
				for(Node neighbour: removing.neighbours){
					neighbour.indegree--;
					if(neighbour.indegree == 0){
						degree0.add(neighbour);
					}
				}
			}
			
			if(nodesFound == n){
				if(multipleOptions){
					System.out.println("2");
				}else{
					System.out.println("1");
				}
			}else{
				System.out.println("0");
			}
		}

		s.close();
	}
}

class Node {
	int val;
	int indegree;
	ArrayList<Node> neighbours;
	boolean useable;

	public Node(int v, int in, ArrayList<Node> neigh) {
		val = v;
		indegree = in;
		neighbours = neigh;
		useable = true;
	}
}
