package ca.germuth.acm_southeastusa_2013.div2.D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Electric Car Rally
 * ACM ICPC SouthEast USA Regionals 2013
 * Problem D Division 2
 * 
 * NOT YET ACCEPTED, BUT SHOULD BE CLOSE
 * This problem boils down to a really complex single source-single destination shortest path.
 * So it can be solved with simple BFS. 
 * After running through the test examples, you can see that there are some cases
 * where getting to a node slower, but with more battery is advantageous. Therefore at each node,
 * you consider traversing all edges, and if you got somewhere faster, or with more battery than you remember
 * that state, and will build off of it later. Additionally, if your charging time is an odd number and you leave,
 * you have wasted that last minute charging since you only increase battery every 2 minutes. Therefore in these 
 * cases, you must also consider the possibility that you waited the extra minute as well.
 * 
 * @author Aaron
 */
public class Main {
	static HashMap<Integer, ArrayList<Edge>> edges;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (s.hasNext()) {
			int n = s.nextInt();
			int e = s.nextInt();
			if (n == 0) {
				break;
			}
			Node[] graph = new Node[n];
			for (int i = 0; i < graph.length; i++) {
				graph[i] = new Node(i, new ArrayList<Edge>());
			}

			for (int i = 0; i < e; i++) {
				int from = s.nextInt();
				int to = s.nextInt();

				while (true) {
					int start = s.nextInt();
					int end = s.nextInt();
					int dis = s.nextInt();

					Edge edge = new Edge(from, to, start, end, dis);

					if (dis <= 240) {
						graph[from].edges.add(edge);
						graph[to].edges
								.add(new Edge(to, from, start, end, dis));
					}

					if (end == 1439) {
						break;
					}
				}
			}

			// input has been taken in
			// find shortest path from 0 to n-1
			System.out.println(shortestPath(graph, 0, n - 1) - 720);
		}

		s.close();
	}

	public static int shortestPath(Node[] graph, int start, int end) {
		ArrayList<State> trials = new ArrayList<State>();
		ArrayList<Integer> chargeTimes = new ArrayList<Integer>();
		
		State[] minDistance = new State[graph.length];
		State[] maxBattery = new State[graph.length];
	
		Arrays.fill(minDistance, new State(Integer.MAX_VALUE, 0));
		Arrays.fill(maxBattery, new State(Integer.MAX_VALUE, 0));

		minDistance[start] = new State(720, 240); // noon
		maxBattery[start] = new State(720, 240);

		LinkedList<Integer> openList = new LinkedList<Integer>();
		openList.add(start);

		while (!openList.isEmpty()) {
			int node = openList.pop();

			if (node == end) {
				continue;
			}

			//must be done twice, once for solution with best time, one with best battery
			//unless they are the same
			trials.clear();
			
			if(minDistance[node].equals(maxBattery[node])){
				trials.add(minDistance[node]);
			}else{
				if(minDistance[node].time != Integer.MAX_VALUE){
					trials.add(minDistance[node]);					
				}
				if(maxBattery[node].time != Integer.MAX_VALUE){
					trials.add(maxBattery[node]);					
				}
			}
			
			for(int b = 0; b < trials.size(); b++){
				int currentTime = trials.get(b).time;
				int currentBatt = trials.get(b).battery;

				// this could be made faster by calculating these values for each
				// edge first,
				// and then only adding the fastest one
				for (int i = 0; i < graph[node].edges.size(); i++) {
					Edge e = graph[node].edges.get(i);

					// determine charge time
					// how much we need to charge to traverse this edge, and get
					// there with 0
					int chargeTime = Math.max(0, e.distance - currentBatt) * 2;
					

					//this may be done twice
					//if charge time is odd, then we wasted last minute trying to get higher battery
					//since battery increases only every 2nd minute
					//in some cases, it may be worth it to delay moving down edge, and charge extra
					//minute to get that higher batter
					//the first iteration is normal case
					//second iteration is only done if 1st iteration chargeTime was odd
					for(int ch = 0; ch < 2; ch++){
						
						//if 2nd iteration
						if (ch == 1) {
							if (chargeTime % 2 == 0) {
								continue;
							} else {
								// try waiting one extra minute
								chargeTime++;
							}
						}
						
						int timeAfterCharging = currentTime + chargeTime;
						
						//adjust time to within 0 - 1439
						//and test when we can next take this edge
						int adjustedTime = timeAfterCharging;
						while (adjustedTime >= 1440) {
							adjustedTime -= 1440;
						}
						
						// determine wait time
						int waitTime = 0;
						if (e.endTime < adjustedTime) {
							// must wait until edge is open tomorrow
							waitTime = 1440 - (adjustedTime - e.startTime);
						} else {
							// edge can be done today
							waitTime = Math.max(0, e.startTime - adjustedTime);
						}
						
						int timeAfterChargeAndWait = timeAfterCharging + waitTime;

						// might as well charge extra, as long as we are waiting
						chargeTime += waitTime;
						
						// determine new battery charge
						int newBatteryCharge = currentBatt + chargeTime / 2;
						if (newBatteryCharge > 240) {
							newBatteryCharge = 240;
						}
						
						//traverse the edge
						newBatteryCharge -= e.distance;

						int totalTime = timeAfterChargeAndWait + e.distance;
						// if we got there faster
						if(totalTime < minDistance[e.to].time){
							minDistance[e.to] = new State(totalTime, newBatteryCharge);
							openList.add(e.to);
						}
						//or with more battery
						else if (maxBattery[e.to].battery < newBatteryCharge) {
							maxBattery[e.to] = new State(totalTime, newBatteryCharge);
							openList.add(e.to);
						}
					}
					chargeTimes.clear();
				}
			}

		}

		return minDistance[end].time;
	}
}

class Node {
	int val;
	ArrayList<Edge> edges;

	public Node(int v, ArrayList<Edge> e) {
		val = v;
		edges = e;
	}
}

class Edge {
	int from;
	int to;
	int startTime;
	int endTime;
	int distance;

	public Edge(int f, int t, int s, int e, int d) {
		from = f;
		to = t;
		startTime = s;
		endTime = e;
		distance = d;
	}
} class State {
	int battery;
	int time;
	public State(int t, int b){
		this.battery = b;
		this.time = t;
	}
	@Override
	public boolean equals(Object obj) {
		State o = (State)obj;
		if(battery == o.battery){
			if(time == o.time){
				return true;
			}
		}
		return false;
	}
	
}