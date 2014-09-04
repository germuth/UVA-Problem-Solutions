package ca.germuth.uva_10026;

import java.io.IOException;
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

	@Override
	public void run() {
		new myStuff().run();
	}
}

class myStuff implements Runnable {
	@Override
	public void run() {

		String numT = Main.ReadLn(1000);
		StringTokenizer t1 = new StringTokenizer(numT);
		int numOfTrials = Integer.parseInt(t1.nextToken());
		int currentTrial = 0;

		while (currentTrial < numOfTrials) {

			String empty = Main.ReadLn(255);

			String numJobs = Main.ReadLn(255);
			StringTokenizer t2 = new StringTokenizer(numJobs);
			int numOfJobs = Integer.parseInt(t2.nextToken());

			Job[] jobs = new Job[numOfJobs];

			for (int i = 0; i < numOfJobs; i++) {
				String temp = Main.ReadLn(255);
				StringTokenizer t3 = new StringTokenizer(temp);
				int time = Integer.parseInt(t3.nextToken());
				int cost = Integer.parseInt(t3.nextToken());
				jobs[i] = new Job(time, cost, i + 1);
			}

			jobs = InsertionSort.insertionSort(jobs);

			String answer = "";
			for (Job j : jobs) {
				answer += j.getNumber() + " ";
			}

			answer = answer.trim();
			System.out.println(answer);

			currentTrial++;

			if (currentTrial != numOfTrials) {
				System.out.println("");
			}
		}
	}
}

class Job {
	private int time;
	private int cost;
	private int number;
	private double priority;

	public Job(int t, int c, int num) {
		this.time = t;
		this.cost = c;
		this.number = num;
		this.priority = ((double) this.time) / ((double) this.cost);
	}

	@Override
	public String toString() {
		return this.number + "";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public double getPriority() {
		return this.priority;
	}

	public void setPriority(double pr) {
		this.priority = pr;
	}
}

class InsertionSort {
	public static Job[] insertionSort(Job[] myArray) {

		for (int i = 1; i < myArray.length; i++) {
			double value = myArray[i].getPriority();
			Job index = myArray[i];
			int k = i - 1;

			while (k >= 0 && myArray[k].getPriority() > value) {
				myArray[k + 1] = myArray[k];
				k--;
			}

			myArray[k + 1] = index;
		}

		return myArray;
	}
}
