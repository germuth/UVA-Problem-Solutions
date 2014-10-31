package ca.germuth.acm_pacnw_2013.B;

import java.util.Arrays;
import java.util.Scanner;
/**
 * Bone's Battery 
 * ACM ICPC Pacific NorthWest 2013 Regionals
 * Problem B
 * 
 * The first observation is that running all-pairs-shortest-paths
 * solves the problem for k = 1. We can then run apsp once for each k. These apsp's
 * will replace the shortest path from node i to node j with another path
 * from node i to node k to node j if the 2nd path has a smaller maximum
 * edge weight. This is repeated once for each k.
 * You must make sure that the graph you are editing in the 2nd step, is 
 * different from the graph you are reading from. If you use the same graph,
 * it is possible you will try to use an updated path twice, causing some problems
 *  
 * @author Germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int n = s.nextInt();
			int K = s.nextInt();
			int edges = s.nextInt();

			long[][] graph = new long[n][n];
			for (long[] sec : graph) {
				Arrays.fill(sec, INFINITY);
			}
			// distance to yourself is 0
			for (int i = 0; i < n; i++) {
				graph[i][i] = 0;
			}
			for (int i = 0; i < edges; i++) {
				int from = s.nextInt();
				int to = s.nextInt();
				long dis = s.nextInt();
				graph[from][to] = dis;
				graph[to][from] = dis;
			}

			// running all pairs shortest paths, solves the problem for k = 1
			// we simply would have to iterate over the graph and find largest
			// edge
			apsp(graph);

			// perform this once for each k
			// each time reducing one edge
			for (int k = 1; k < K; k++) {
				// graph for k is the one looked at
				// graph is the one updated
				// this way we can't use an updated edge twice in one run
				long[][] graphForK = new long[n][n];
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						graphForK[i][j] = graph[i][j];
					}
				}
				for (int source = 0; source < n; source++) {
					for (int dest = 0; dest < n; dest++) {
						// for each source - destination pair
						if (source == dest) {
							continue;
						}
						// consider trying a new intermediate node
						for (int intermediate = 0; intermediate < n; intermediate++) {
							if (intermediate == source || intermediate == dest) {
								continue;
							}
							// if the maximum edge weight in the new path is
							// less than the maximum edge weight in the current
							// path
							// then replace it
							long maxEdgeCurrent = graph[source][dest];
							long maxEdgeNew = Math.max(
									graphForK[source][intermediate],
									graphForK[intermediate][dest]);
							if (maxEdgeNew < maxEdgeCurrent) {
								graph[source][dest] = maxEdgeNew;
							}
						}
					}
				}
			}

			// search through graph for largest edge
			long maxEdge = Long.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (graph[i][j] > maxEdge) {
						maxEdge = graph[i][j];
					}
				}
			}
			System.out.println(maxEdge);

		}
		s.close();
	}

	// this number has to be larger than any of the edges we will encounter
	// but small enough that 2*INFINITY will not overflow
	static long INFINITY = 2000000000000000000l;

	public static void apsp(long[][] dist) {
		int n = dist.length;
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					long currPath = dist[i][j];
					long newPath = dist[i][k] + dist[k][j];
					if (newPath < currPath) {
						dist[i][j] = newPath;
					}
				}
	}
}