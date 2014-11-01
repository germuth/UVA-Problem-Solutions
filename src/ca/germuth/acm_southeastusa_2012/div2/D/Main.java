package ca.germuth.acm_southeastusa_2012.div2.D;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Dueling Philospohers 
 * ACM ICPC SouthEast USA 2012 Regionals
 * Problem D Division 2
 * 
 * TODO: REDO WITH O(n) TOPO SORT, AND PUT IN DRIVE DOC
 * 
 * Done with topological sort.
 * Need to redo this one with proper O(n) topological sort
 * then add to graph algorithms
 * 
 * @author germuth
 */

class Main {
	static boolean multiple = false;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (true) {
			multiple = false;
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

			int nodesFound = 0;
			int result = getNodeWithInDegree0(graph);
			while (result != -2) {
				nodesFound++;
				result = getNodeWithInDegree0(graph);
			}

			if (nodesFound == n && multiple) {
				System.out.println("2");
			} else if (nodesFound == n) {
				System.out.println("1");
			} else {
				System.out.println("0");
			}
			// if(nodesFound == n){
			// System.out.println("1");
			// }else if(result == -2){
			// System.out.println("0");
			// }else if(multiple){
			// System.out.println("2");
			// }else{
			// System.out.println("0");
			// }
		}

		s.close();
	}

	// if 0 -> n then we found one node at that index
	// if -1, then we found multiple
	// if -2, then we found none
	public static int getNodeWithInDegree0(Node[] graph) {
		int foundAt = -5;
		for (int i = 0; i < graph.length; i++) {
			if (!graph[i].useable) {
				continue;
			}
			if (graph[i].indegree == 0) {
				// we found multiple!
				if (foundAt != -5) {
					multiple = true;
					break;
				}
				foundAt = i;
			}
		}

		if (foundAt != -5) {
			// first one we found
			// mark as not used and remove his edges
			graph[foundAt].useable = false;
			for (Node n : graph[foundAt].neighbours) {
				n.indegree--;
			}
			return foundAt;
		}
		return -2;
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
