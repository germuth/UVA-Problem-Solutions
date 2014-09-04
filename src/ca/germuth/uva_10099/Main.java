package ca.germuth.uva_10099;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

class Main implements Runnable {
	static String ReadLn(int maxLength) { // utility function to read from
		// stdin,
		// Provided by Programming-challenges, edit for style only
		byte line[] = new byte[maxLength];
		int length = 0;
		int input = -1;
		try {
			while (length < maxLength) {// Read until maxlength
				input = System.in.read();
				if ((input < 0) || (input == '\n'))
					break; // or until end of line input
				line[length++] += input;
			}

			if ((input < 0) && (length == 0))
				return null; // eof
			return new String(line, 0, length);
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String args[]) // entry point from OS
	{
		Main myWork = new Main(); // Construct the bootloader
		myWork.run(); // execute
	}

	@Override
	public void run() {
		new myStuff().run();
	}
}

class myStuff implements Runnable {
	private static HashMap<String, Long> map = new HashMap<String, Long>();
	private static int numberOfVerticies = 0;
	private static long[] highestFlowAtEachNode;

	@Override
	public void run() {
		int a = 1;

		String input = Main.ReadLn(255);
		StringTokenizer t = new StringTokenizer(input);
		while (Integer.parseInt(t.nextToken()) != 0) {

			StringTokenizer t1 = new StringTokenizer(input);
			numberOfVerticies = Integer.parseInt(t1.nextToken());
			int numberEdges = Integer.parseInt(t1.nextToken());

			String edge;
			StringTokenizer token;

			for (int i = 0; i < numberEdges; i++) {
				edge = Main.ReadLn(255);
				token = new StringTokenizer(edge);

				int startCity = Integer.parseInt(token.nextToken());
				int endCity = Integer.parseInt(token.nextToken());
				long cost = Long.parseLong(token.nextToken());

				map.put("" + startCity + ", " + endCity, cost);
				map.put("" + endCity + ", " + startCity, cost);
			}

			myStuff.highestFlowAtEachNode = new long[numberOfVerticies + 1];
			for (int i = 0; i < highestFlowAtEachNode.length; i++) {
				myStuff.highestFlowAtEachNode[i] = Integer.MIN_VALUE;
			}

			String path = Main.ReadLn(255);
			StringTokenizer t6 = new StringTokenizer(path);
			int startCity = Integer.parseInt(t6.nextToken());
			int endCity = Integer.parseInt(t6.nextToken());
			int numPassengers = Integer.parseInt(t6.nextToken());

			int answer;

			if (startCity != endCity) {
				/**
				 * long touristsPerTrip = graph[start][end]; long trips =
				 * touristCount / touristsPerTrip; if(touristCount %
				 * touristsPerTrip != 0) { trips++; }
				 */
				long minCost = openList(startCity, endCity) - 1;

				answer = (int) Math.ceil(1.0 * numPassengers / minCost);
			} else {
				answer = 0;
			}
			System.out.println("Scenario #" + a);
			System.out.println("Minimum Number of Trips = " + answer);
			System.out.println("");

			a++;
			myStuff.map.clear();
			myStuff.numberOfVerticies = 0;
			myStuff.highestFlowAtEachNode = null;

			input = Main.ReadLn(255);
			t = new StringTokenizer(input);
		}
	}

	private static long openList(int start, int end) {
		State starting = new State(Integer.MAX_VALUE, start);
		LinkedList<State> openList = new LinkedList<State>();
		openList.add(starting);
		long maxCost = Integer.MIN_VALUE;

		while (!openList.isEmpty()) {

			State parent = openList.pop();

			if (parent.getVertex() == end) {
				if (maxCost < parent.getCost()) {
					maxCost = parent.getCost();
				}
			} else {
				for (int i = 1; i <= myStuff.numberOfVerticies; i++) {
					// if it's a real edge
					if (compatibleEdge(parent, i)) {
						// cost of new edge
						long newCost = getCost(parent, i);
						// set parent cost if cheaper
						if (newCost > parent.getCost()) {
							newCost = parent.getCost();
						}

						// update array
						if (newCost > myStuff.highestFlowAtEachNode[i]) {
							myStuff.highestFlowAtEachNode[i] = newCost;
							openList.add(new State(newCost, i));
						}
					}
				}
			}
		}
		return maxCost;
	}

	private static long getCost(State parent, int vertex) {
		return map.get("" + parent.getVertex() + ", " + vertex);
	}

	private static boolean compatibleEdge(State parent, int vertex) {
		if (parent.getVertex() == vertex) {
			return false;
		}
		if (map.get("" + parent.getVertex() + ", " + vertex) == null) {
			return false;
		}
		return true;
	}
}

class State {
	private long cost;
	private int vertex;

	public String toString() {
		return "State. cost: " + this.cost + " vertex: " + this.vertex + " ";
	}

	public State(long c, int v) {
		this.cost = c;
		this.vertex = v;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
}

class Pair {
	private int first;
	private int second;

	public boolean equals(Pair another) {
		if (this.first == another.first && this.second == another.second) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Pair. (" + this.first + ")(" + this.second + ") ";
	}

	public Pair(int f, int s) {
		this.first = f;
		this.second = s;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
}
