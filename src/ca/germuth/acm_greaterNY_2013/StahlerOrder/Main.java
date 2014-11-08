package ca.germuth.acm_greaterNY_2013.StahlerOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Stahler Order
 * ACM ICPC Greater NY 2013 Regionals
 * Problem D
 * 
 * Need to assign stahler order to each node in the graph.
 * First assign all nodes with indegree 0, an order of 1.
 * Then BFS through the graph and assign new numbers. 
 * Don't assign a number unless all of that nodes
 * parents have been assigned a number.
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int caseNum = s.nextInt();

			int n = s.nextInt();
			int e = s.nextInt();
			ArrayList<Node> graph = new ArrayList<Node>();
			for (int i = 0; i < n; i++) {
				graph.add(new Node(i, new ArrayList<Node>()));
			}
			for (int i = 0; i < e; i++) {
				Node from = graph.get(s.nextInt() - 1);
				Node to = graph.get(s.nextInt() - 1);
				from.neighbours.add(to);
				to.parents.add(from);
			}

			boolean[] visited = new boolean[n];
			Arrays.fill(visited, false);
			int[] stahler = new int[n];
			Arrays.fill(stahler, -1);

			// openlist
			LinkedList<Node> openList = new LinkedList<Node>();
			// start by adding all nodes with degree 0
			for (int i = 0; i < n; i++) {
				if (graph.get(i).parents.size() == 0) {
					openList.add(graph.get(i));
					visited[i] = true;
				}
			}

			while (!openList.isEmpty()) {
				Node curr = openList.pop();

				// if any of my parents aren't visited, gg
				boolean skip = false;
				for (Node parent : curr.parents) {
					if (stahler[parent.val] == -1) {
						skip = true;
						break;
					}
				}
				if (skip) {
					visited[curr.val] = false;
					continue;
				}

				// assign this node a number
				if (curr.parents.size() == 0) {
					stahler[curr.val] = 1;
				} else if (curr.parents.size() == 1) {
					stahler[curr.val] = stahler[curr.parents.get(0).val];
				} else {
					int max = Integer.MIN_VALUE;
					int parentsWithMax = 0;
					for (Node par : curr.parents) {
						if (stahler[par.val] > max) {
							max = stahler[par.val];
							parentsWithMax = 1;
						} else if (stahler[par.val] == max) {
							parentsWithMax++;
						}
					}
					// if just one upstream node has order i, then this node has
					// order i
					if (parentsWithMax == 1) {
						stahler[curr.val] = max;
					} else {
						stahler[curr.val] = max + 1;
					}
				}

				// try all neighbours
				for (Node neighbour : curr.neighbours) {
					if (!visited[neighbour.val]) {
						visited[neighbour.val] = true;
						openList.add(neighbour);
					}
				}
			}

			int max = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				if (stahler[i] > max) {
					max = stahler[i];
				}
			}
			// get rid of cycles
			if (max == -1) {
				max = 0;
			}
			System.out.println(caseNum + " " + max);
		}
		s.close();
	}
}

class Node {
	int val;
	ArrayList<Node> neighbours;
	ArrayList<Node> parents;

	public Node(int v, ArrayList<Node> ne) {
		val = v;
		neighbours = ne;
		parents = new ArrayList<Node>();
	}
}