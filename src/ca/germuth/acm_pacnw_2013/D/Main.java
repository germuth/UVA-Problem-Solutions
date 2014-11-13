package ca.germuth.acm_pacnw_2013.D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Delta Quadrant D
 * 
 * NOT YET SOLVED!!!
 * 
 * Key observation here is that there are n nodes, and n-1 edges, and the graph is connected. 
 * this means it is impossible for graph to have a cycle, which means the only way to reach x nodes,
 * and return where you got is to travel the edge once each way.

 * @author Germuth
 */
class Main {
	static HashMap<Character, Long> duration = new HashMap<Character, Long>();
	static long[][] graph;
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 

		int numCases = s.nextInt();
		for(int a = 0; a < numCases; a++){
			
			int n = s.nextInt();
			int k = s.nextInt();
			graph = new long[n][n];
			for(int i = 0; i < graph.length; i++){
				Arrays.fill(graph[i], -1);
			}
			for(int i = 0;i < n-1; i++){
				int to = s.nextInt();
				int from = s.nextInt();
				int d = s.nextInt();
				graph[to][from] = d;
				graph[from][to] = d;
			}
			
			
			System.out.println(0);
		}
		s.close();
	}
}



