package ca.germuth.uva_10067;

import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Main implements Runnable {
	static String ReadLn(int maxLength) {
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

	public void run() {
		new myStuff().run();
	}
}

class myStuff implements Runnable {
	private static boolean[] listOfStates;
	private static LinkedList<Wheels> openList;

	public void run() {
	
		int numOfTrials = Integer.parseInt(Main.ReadLn(100000));
		int currentTrial = 0;

		while (currentTrial < numOfTrials) {
			openList = new LinkedList<Wheels>();
			String empty = Main.ReadLn(100000);

			String currentState = Main.ReadLn(1000000);
			StringTokenizer t1 = new StringTokenizer(currentState);

			listOfStates = new boolean[10000];

			Wheels firstState = new Wheels(Integer.parseInt(t1.nextToken()),
					Integer.parseInt(t1.nextToken()), Integer.parseInt(t1
							.nextToken()), Integer.parseInt(t1.nextToken()), 0);
			listOfStates[firstState.getVal()] = true;

			String goalInput = Main.ReadLn(1000000);
			StringTokenizer t2 = new StringTokenizer(goalInput);
			Wheels goalState = new Wheels(Integer.parseInt(t2.nextToken()),
					Integer.parseInt(t2.nextToken()), Integer.parseInt(t2
							.nextToken()), Integer.parseInt(t2.nextToken()), 0);

			int forbiddenNum = Integer.parseInt(Main.ReadLn(100000));
			for (int i = 0; i < forbiddenNum; i++) {
				String forbiddenInput = Main.ReadLn(10000);
				StringTokenizer t3 = new StringTokenizer(forbiddenInput);
				Wheels tmp = new Wheels(Integer.parseInt(t3.nextToken()),
						Integer.parseInt(t3.nextToken()), Integer.parseInt(t3
								.nextToken()),
						Integer.parseInt(t3.nextToken()), 0);
				listOfStates[tmp.getVal()] = true;
			}

			int answer = findNumSteps(firstState, goalState);
			System.out.println(answer);

			currentTrial++;
		}
	}

	public int findNumSteps(Wheels firstState, Wheels goalState) {

		openList.add(firstState);
		while (!openList.isEmpty()) {

			Wheels current = openList.pop();
			Wheels[] children = new Wheels[8];

			if (current.equals(goalState)) {
				return current.steps;
			}

			children[0] = new Wheels(current, 0, 0, 0, 1);
			children[1] = new Wheels(current, 0, 0, 0, -1);
			children[2] = new Wheels(current, 0, 0, 1, 0);
			children[3] = new Wheels(current, 0, 0, -1, 0);
			children[4] = new Wheels(current, 0, 1, 0, 0);
			children[5] = new Wheels(current, 0, -1, 0, 0);
			children[6] = new Wheels(current, 1, 0, 0, 0);
			children[7] = new Wheels(current, -1, 0, 0, 0);

			for (Wheels w : children) {
				if (listOfStates[w.getVal()] == false) {
					openList.add(w);
					listOfStates[w.getVal()] = true;
				}
			}

		}

		return -1;
	}
}

class Wheels {
	public int thousands;
	public int hundreds;
	public int tens;
	public int ones;
	public int steps;

	public Wheels(int th, int h, int t, int o, int step) {
		this.thousands = th;
		this.hundreds = h;
		this.tens = t;
		this.ones = o;
		this.steps = step;
	}

	public Wheels(Wheels parent, int th, int h, int t, int o) {

		this.thousands = parent.thousands + th;
		this.hundreds = parent.hundreds + h;
		this.tens = parent.tens + t;
		this.ones = parent.ones + o;
		this.steps = parent.steps + 1;
		if (this.thousands == 10) {
			this.thousands = 0;
		}
		if (this.thousands == -1) {
			this.thousands = 9;
		}
		if (this.hundreds == 10) {
			this.hundreds = 0;
		}
		if (this.hundreds == -1) {
			this.hundreds = 9;
		}
		if (this.tens == 10) {
			this.tens = 0;
		}
		if (this.tens == -1) {
			this.tens = 9;
		}
		if (this.ones == 10) {
			this.ones = 0;
		}
		if (this.ones == -1) {
			this.ones = 9;
		}
	}

	public boolean equals(Wheels wheel2) {
		if (this.thousands == wheel2.thousands
				&& this.hundreds == wheel2.hundreds && this.tens == wheel2.tens
				&& this.ones == wheel2.ones) {

			return true;
		}

		return false;
	}

	public int getVal() {
		return 1000 * thousands + 100 * hundreds + 10 * tens + ones;
	}
}
