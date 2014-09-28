package ca.germuth.acm_pacnw_2013.B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Bone's Battery Problem B
 * 
 * Below solution is not correct. 
 * @author Germuth
 */
class Main {
	
	//this number should be larger than any possible summed distance
	//but not as large as Long.MAX or else it will overflow and ruin everything
	final static long LARGE_NUMBER = 1000000000000000000L;
	//contains shortest path from every node to every other node
	static long[][] distTo;
	//this array holds pathing information used to get shortest paths
	static int[][] pathTo;
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);

		int numCases = s.nextInt();
		for(int a = 0; a < numCases; a++){
			int n = s.nextInt();
			int k = s.nextInt();
			int m = s.nextInt();
			
			long[][] graph = new long[n][n];
			for(int j = 0; j < graph.length; j++){
				Arrays.fill(graph[j], 0);
			}
			for(int j = 0; j < m; j++){
				int to = s.nextInt();
				int from = s.nextInt();
				long d = s.nextLong();
				graph[to][from] = d;
				graph[from][to] = d;
			}
			
			//find all shortest paths
			allPairsShortestPathWithRememberPath(graph);
			//iterate through each path and combine nodes until there are k nodes
			//and then grab the smallest possible battery we could use
			long smallestPossibleBattery = Long.MIN_VALUE;
			for(int i = 0; i < graph.length; i++){
				for(int j = 0; j < graph[i].length; j++){
					if(i == j){
						continue;
					}
					//path from i to j
					ArrayList<Edge> path = reconstructPathWithEdge(i, j);
					while(path.size() > k){
						//combine ith and i+1th node in path
						//find smallest two adjacent edges and join them
						//reducing nodes in path 1 by 1 until can be in k rechargings
						int firstOfSmallest = -1;
						long smallest = Long.MAX_VALUE;
						for(int b = 0; b < path.size() - 1; b++){
							Edge curr = path.get(b);
							Edge next = path.get(b + 1);
							
							if(curr.dis + next.dis < smallest){
								smallest = curr.dis + next.dis;
								firstOfSmallest = b;
							}
						}
						//found smallest, now join them
						Edge fir = path.get(firstOfSmallest);
						Edge sec = path.get(firstOfSmallest + 1);
						fir.dis += sec.dis;
						fir.to = sec.to;
						path.remove(firstOfSmallest + 1);
					}
					
					//now iterate over path and find smallest
					for(int b = 0; b < path.size(); b++){
						if(path.get(b).dis > smallestPossibleBattery){
							smallestPossibleBattery = path.get(b).dis;
						}
					}
					
				}
			}
			
			System.out.println(smallestPossibleBattery);	
		}
			
			

		s.close();
	}
	
	public static void allPairsShortestPath(long[][] graph){
		int n = graph.length;
		//contains shortestPaths
		distTo = new long[n][n];
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph[i].length; j++){
				if(i == j){
					distTo[i][j] = 0;
				}else if(graph[i][j] > 0){
					distTo[i][j] = graph[i][j];
				}else{
					distTo[i][j] = LARGE_NUMBER;
				}
			}
		}
		for (int k=0; k<n; k++)
	         for (int i=0; i<n; i++)
	            for (int j=0; j<n; j++)
	               if (distTo[i][k] + distTo[k][j] < distTo[i][j])
	                  distTo[i][j] = distTo[i][k] + distTo[k][j] ;
	}
	
	public static void allPairsShortestPathWithRememberPath(long[][] graph){
		int n = graph.length;
		//contains shortestPaths
		distTo = new long[n][n];
		//contains paths
		pathTo = new int[n][n];
		for(int[] path: pathTo){
			Arrays.fill(path, -1);
		}
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph[i].length; j++){
				if(i == j){
					distTo[i][j] = 0;
				}else if(graph[i][j] > 0){
					distTo[i][j] = graph[i][j];
					pathTo[i][j] = j;
				}else{
					distTo[i][j] = LARGE_NUMBER;
				}
			}
		}
		for (int k=0; k<n; k++)
	         for (int i=0; i<n; i++)
	            for (int j=0; j<n; j++)
	               if (distTo[i][k] + distTo[k][j] < distTo[i][j]){
	                  distTo[i][j] = distTo[i][k] + distTo[k][j] ;
	                  pathTo[i][j] = pathTo[i][k];
	               }
	}
	
	public static ArrayList<Integer> reconstructPath(int start, int end){
		ArrayList<Integer> path = new ArrayList<Integer>();
		if(pathTo[start][end] == -1){
			return path;
		}
		path.add(start);
		int current = start;
		while(current != end){
			current = pathTo[current][end];
			path.add(current);
		}
		return path;
	}
	
	public static ArrayList<Edge> reconstructPathWithEdge(int start, int end){
		ArrayList<Integer> path = reconstructPath(start, end);
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int i = 0; i < path.size() - 1; i++){
			int curr = path.get(i);
			int next = path.get(i + 1);
			edges.add(new Edge(curr, next, distTo[curr][next]));
		}
		
		return edges;
	}
	
}class Edge{
	long dis;
	int from;
	int to;
	public Edge(int s, int e, long d){
		this.from = s;
		this.to = e;
		this.dis = d;
	}
}