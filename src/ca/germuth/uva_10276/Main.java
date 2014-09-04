package ca.germuth.uva_10276;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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
		}
		catch (IOException e) {
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
	@Override
	public void run() {

		Scanner s = new Scanner(System.in);
		// int numOfTrials = Integer.parseInt(Main.ReadLn(100000));
		int numOfTrials = s.nextInt();
		int currentTrial = 0;

		while (currentTrial < numOfTrials) {
			// int numOfPegs = Integer.parseInt(Main.ReadLn(1000000));
			int numOfPegs = s.nextInt();
			System.out.println(solve(numOfPegs));

			currentTrial++;
		}
	}

	private static int solve(int pegs) {
		LinkedList<int[]> openList = new LinkedList<int[]>();

		int[] start = new int[pegs + 1];
		start[0] = 1;
		openList.add(start);
		int maxSoFar = 0;

		while (!openList.isEmpty()) {

			// generate children
			int[] parent = openList.pop();

			boolean[] viablePegs = checkViable(parent);

			boolean flag = false;
			for (int i = 0; i < viablePegs.length; i++) {
				if (viablePegs[i]) {
					flag = true;
				}
			}

			if (!flag && parent[0] - 1 > maxSoFar) {
				maxSoFar = parent[0] - 1;
			}
			for (int i = 0; i < viablePegs.length; i++) {

				if (viablePegs[i] == true) {
					int[] temp = new int[parent.length];
					System.arraycopy(parent, 0, temp, 0, parent.length);
					// int [] temp = parent;
					temp[i + 1] = temp[0];
					temp[0]++;
					openList.add(temp);
				}

			}
		}
		return maxSoFar;

	}

	private static boolean[] checkViable(int[] parent) {
		boolean[] viablePegs = new boolean[parent.length - 1];
		int count = 0;
		boolean flag = false;
		for (int i = 1; i < parent.length; i++) {

			if (parent[i] > 0 && checkPerfectSquare(parent[0] + parent[i])) {
				viablePegs[i - 1] = true;
				flag = true;
			}

			if (parent[i] == 0 && count == 0 && !flag) {
				viablePegs[i - 1] = true;
				count++;
			}

		}
		return viablePegs;
	}

	private static boolean checkPerfectSquare(int n) {
		double temp = Math.sqrt(n);
		if (temp == Math.floor(temp)) {
			return true;
		}
		return false;
	}
}
