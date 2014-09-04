package ca.germuth.uva_10213;

import java.io.IOException;
import java.math.BigInteger;
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

		// take in number of cases
		String in = Main.ReadLn(255);
		StringTokenizer t = new StringTokenizer(in);
		int numCases = Integer.parseInt(t.nextToken());

		int currentCase = 0;

		while (currentCase < numCases) {

			String input = Main.ReadLn(255);
			StringTokenizer t1 = new StringTokenizer(input);
			int points = Integer.parseInt(t1.nextToken());
			// answer is 1 by default
			BigInteger answer = new BigInteger("1");
			// the number of four sided polygons we can form
			answer = answer.add(combination(points, 4));
			// number of lines we can form
			answer = answer.add(combination(points, 2));

			System.out.println(answer);

			currentCase++;
		}
	}

	// computes n choose k for big integers
	private static BigInteger combination(int n, int k) {
		BigInteger answer = new BigInteger("1");
		for (int i = 0; i < k; i++) {
			// answer = answer*(n-i)/(i+1)
			answer = answer.multiply(BigInteger.valueOf(n - i)).divide(
					BigInteger.valueOf(i + 1));
		}
		return answer;
	}
}
