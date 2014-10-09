package ca.germuth.acm_pacnw_2012.K;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Tile Cut Problem K
 * 
 * This problem was an application of maximum flow. You created a source
 * node and connected it to all 'W's, and sink node connected from all 'N's. 
 * Then hook up the letters in the graph with edge flow capacity of 1. 
 * One important note, is that the total flow going through a letter
 * must be 1, which means just having all edges with total of 1 is not sufficient
 * since multiple edges could lead in and out of a node. Therefore, each
 * letter is constructed as 2 nodes, an input and output node, with a single
 * edge of capacity 1 inbetween them.
 * @author Germuth
 */
class Main {
	static char[][] map;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (s.hasNextLine()) {
			String line = s.nextLine();

			if (line.isEmpty()) {
				break;
			}

			ArrayList<String> input = new ArrayList<String>();
			while (!line.isEmpty() && s.hasNextLine()) {
				input.add(line);

				line = s.nextLine();
				if (!s.hasNextLine() && !line.isEmpty()) {
					input.add(line);
				}
			}

			map = new char[input.size()][input.get(0).length()];
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j] = input.get(i).charAt(j);
				}
			}

			// construct graph
			// 0 is source, n-1 is sink, everything else is input and output for
			// W,I,Ns
			// input nodes are 0 <-> n-1
			// output nodes are n <-> 2n-1
			int inputToOutput = map.length * map[0].length;
			int numNodes = 2 + 2 * inputToOutput;
			int[][] graph = new int[numNodes][numNodes];
			for (int[] nodeNeighbour : graph) {
				Arrays.fill(nodeNeighbour, 0);
			}

			// add edges
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					char current = map[i][j];
					int currentIndex = 1 + (i * map[i].length) + j;

					// for each letter connect input to output edge
					graph[currentIndex][currentIndex + inputToOutput] = 1;

					if (current == 'W') {
						graph[0][currentIndex] = 1;
					}
					if (current == 'N') {
						graph[currentIndex + inputToOutput][graph[0].length - 1] = 1;
					}

					// search to right and bottom
					if (withinBounds(i, j + 1)) {
						char right = map[i][j + 1];
						int rightIndex = 1 + i * map[i].length + (j + 1);

						if (current == 'W' && right == 'I') {
							graph[currentIndex + inputToOutput][rightIndex] = 1;
						} else if (current == 'I' && right == 'W') {
							graph[rightIndex + inputToOutput][currentIndex] = 1;
						} else if (current == 'I' && right == 'N') {
							graph[currentIndex + inputToOutput][rightIndex] = 1;
						} else if (current == 'N' && right == 'I') {
							graph[rightIndex + inputToOutput][currentIndex] = 1;
						}
					}
					// bottom
					if (withinBounds(i + 1, j)) {
						char below = map[i + 1][j];
						int belowIndex = 1 + (i + 1) * map[i].length + j;

						if (current == 'W' && below == 'I') {
							graph[currentIndex + inputToOutput][belowIndex] = 1;
						} else if (current == 'I' && below == 'W') {
							graph[belowIndex + inputToOutput][currentIndex] = 1;
						} else if (current == 'I' && below == 'N') {
							graph[currentIndex + inputToOutput][belowIndex] = 1;
						} else if (current == 'N' && below == 'I') {
							graph[belowIndex + inputToOutput][currentIndex] = 1;
						}
					}

				}
			}
			System.out.println(maxFlow(graph, 0, graph.length - 1));
		}
		s.close();
	}

	public static boolean withinBounds(int i, int j) {
		if (i < 0) {
			return false;
		}
		if (i > map.length - 1) {
			return false;
		}
		if (j < 0) {
			return false;
		}
		if (j > map[i].length - 1) {
			return false;
		}
		return true;
	}

	public static int maxFlow(int[][] cap, int source, int sink) {
		int maxFlow = 0;
		// create graph of current flow
		int[][] flow = new int[cap.length][cap[0].length];
		for (int[] node : flow) {
			Arrays.fill(node, 0);
		}
		// create graph of residual flow
		int[][] resFlow = new int[cap.length][cap[0].length];
		for (int i = 0; i < cap.length; i++) {
			for (int j = 0; j < cap[i].length; j++) {
				resFlow[i][j] = cap[i][j];
			}
		}

		ArrayList<Integer> path = findPath(resFlow, source, sink);
		while (path != null) {

			// find min cost of path
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < path.size() - 1; i++) {
				int currNode = path.get(i);
				int nextNode = path.get(i + 1);
				min = Math.min(min, resFlow[currNode][nextNode]);
			}

			// update residual path
			for (int i = 0; i < path.size() - 1; i++) {
				int currNode = path.get(i);
				int nextNode = path.get(i + 1);

				// update flow graph
				flow[currNode][nextNode] += min;

				// update residual graph
				// add back edges of capacity min
				resFlow[nextNode][currNode] += min;
				// update forward edges
				// resFlow[currNode][nextNode] = cap[currNode][nextNode] - resFlow[nextNode][currNode];
				resFlow[currNode][nextNode] -= min;
			}

			maxFlow += min;

			path = findPath(resFlow, source, sink);
		}
		return maxFlow;
	}

	public static ArrayList<Integer> findPath(int[][] g, int source, int sink) {
		LinkedList<ArrayList<Integer>> openList = new LinkedList<ArrayList<Integer>>();

		// keep track of visited nodes
		boolean[] visited = new boolean[g.length];
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		ArrayList<Integer> start = new ArrayList<Integer>();
		start.add(source);
		openList.add(start);

		while (!openList.isEmpty()) {
			// DFS OR BFS??
			ArrayList<Integer> currPath = openList.pop();
			int currNode = currPath.get(currPath.size() - 1);

			visited[currNode] = true;
			if (currNode == sink) {
				return currPath;
			}

			// for each neighbor
			for (int i = 0; i < g[currNode].length; i++) {
				// if adj matrix contains 0 or less, there is no edge there
				if (g[currNode][i] > 0) {
					int neighbour = i;
					if (!visited[neighbour]) {
						ArrayList<Integer> newPath = new ArrayList<Integer>(
								currPath);
						newPath.add(neighbour);
						openList.add(newPath);
					}
				}
			}
		}
		return null;
	}
}