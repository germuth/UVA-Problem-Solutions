package ca.germuth.uva_120;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	private static int[] stack;

	public void run() {

		while (true) {
			String line = Main.ReadLn(1000000);

			Scanner s2 = new Scanner(line);

			ArrayList<Integer> theStack = new ArrayList<Integer>();

			while (s2.hasNextInt()) {
				theStack.add(s2.nextInt());
			}

			stack = new int[theStack.size()];

			for (int i = 0; i < theStack.size(); i++) {
				stack[i] = theStack.get(i);
			}

			System.out.print(stack[0]);

			for (int i = 1; i < stack.length; i++) {
				System.out.print(" " + stack[i]);
			}

			System.out.println("");

			int j = stack.length - 1;

			while (j >= 0) {
				int i = findLargestIndex(j);

				if (i == j) {
					j--;
				} else if (i == 0) {
					flip(j);
					j--;
				} else {
					flip(i);
					flip(j);
					j--;
				}
			}

			System.out.println("0");
		}
	}

	public static int findLargestIndex(int j) {
		int maximumIndex = 0;

		for (int i = 0; i <= j; i++) {
			if (stack[i] > stack[maximumIndex]) {
				maximumIndex = i;
			}
		}
		return maximumIndex;
	}

	public static void flip(int flipPos) {

		System.out.print((stack.length - (flipPos)) + " ");

		int firstIndex = 0;

		int temp = 0;
		while (firstIndex < flipPos) {
			temp = stack[firstIndex];
			stack[firstIndex] = stack[flipPos];
			stack[flipPos] = temp;
			firstIndex++;
			flipPos--;
		}
	}
}
