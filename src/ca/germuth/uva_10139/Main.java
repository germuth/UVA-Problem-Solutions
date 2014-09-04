package ca.germuth.uva_10139;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
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
	private static HashMap map = new HashMap();

	@Override
	public void run() {

		while (true) {
			String input = Main.ReadLn(255);
			StringTokenizer t1 = new StringTokenizer(input);
			long n = Long.parseLong(t1.nextToken());
			long m = Long.parseLong(t1.nextToken());

			if (m != 0) {
				if (m != n) {
					// first break m longo it's prime factors
					ArrayList<Long> primeFactorization = primeFactorization(m); // {2,
																				// 2,
																				// 2,
																				// 3}
					countPrimes(primeFactorization);

					if (divisible(m, n)) {
						System.out.println(m + " divides " + n + "!");
					} 
					else {
						System.out.println(m + " does not divide " + n + "!");
					}
				}
				else {
					System.out.println(m + " divides " + n + "!");
				}
				
			} 
			else {
				System.out.println(m + " does not divide " + n + "!");
			}
			map.clear();
		}
	}

	private static boolean divisible(long m, long n) {
		Set<Long> set = map.keySet();
		Iterator<Long> i = set.iterator();
		while (i.hasNext()) {
			long element = (long) i.next();
			long numberOfTimesInM = (long) map.get(element);

			long numberOfTimesInN = 0;
			long pow = element;
			
			while (pow < n) {
				numberOfTimesInN += (n / element);
				pow *= element;
			}

			if (numberOfTimesInN < numberOfTimesInM) {
				return false;
			}
		}
		return true;
	}

	private static void countPrimes(ArrayList<Long> array) {
		for (long i = 0; i < array.size(); i++) {
			long element = array.get((int)i);
			Object prev = map.get(element);
			if (prev == null) {
				map.put(element, (long)1);
			} else {
				map.put(element, (long)((long) prev) + 1);
			}
		}
	}

	private static ArrayList<Long> primeFactorization(long m) {
		ArrayList<Long> allFactors = new ArrayList<Long>();
		for (long i = 2; i <= m / i; i++) {
			while (m % i == 0) {
				allFactors.add(i);
				m /= i;
			}
		}
		if (m > 1) {
			allFactors.add(m);
		}
		return allFactors;
	}
}
