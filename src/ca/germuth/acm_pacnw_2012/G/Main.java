package ca.germuth.acm_pacnw_2012.G;

import java.util.Scanner;

/**
 * Saruman's Level Up Problem G
 * 
 * Ex. 64. 64 in binary is 0100 0000. 
 * There are 6 binary digits of numbers to consider, and the number itself.
 * With 6 possible spots, there are (6 choose 3) + (6 choose 6) numbers
 * where there are a multiple of 3 1s. This process is repeated to count 
 * the total number of ones. For each '1' in the binary number, you pretend 
 * to set it 0, and count the number of 1s. This ensures you only ever include
 * binary numbers which are smaller than the input value.
 * Worked Example of 41.
 * 41 -> 0010 1001
 * 0010 1001 = 1
 * 000_ ____ = 5 choose 3 
 * 0010 0___ = 3 choose 2 (2 because we already have a 1)
 * 0010 1000 = 0
 *           -----
 *             14 in total
 * @author Germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (s.hasNextLine()) {
			long in = Long.parseLong(s.nextLine());

			// total length of number in bits
			int length = Long.toBinaryString(in).length();

			long answer = 0;
			int onesSoFar = 0;
			int bitsSoFar = 0;

			// iterate through answer bit by bit
			// for each 1, set it to 0, and compute combination below it
			for (long i = 1l << (length - 1); i != 0; i /= 2) {
				bitsSoFar++;

				// we found a 1
				if ((in & i) != 0) {
					// record number of ones in 'in' itself
					onesSoFar++;

					int spacesLeft = (length - bitsSoFar);
					int numOfOnesToPlace = (3 - onesSoFar) + 1;
					while (numOfOnesToPlace < 0) {
						numOfOnesToPlace += 3;
					}

					for (; numOfOnesToPlace <= spacesLeft; numOfOnesToPlace += 3) {
						answer += combination(spacesLeft, numOfOnesToPlace);
					}
				}
			}
			if (Long.bitCount(in) % 3 == 0) {
				answer++;
			}
			System.out.println("Day " + in + ": Level = " + answer);
		}
		s.close();
	}

	// computes n choose k for big integers
	private static long combination(int n, int k) {
		long answer = 1;
		for (int i = 0; i < k; i++) {
			answer = answer * (n - i) / (i + 1);
		}
		return answer;
	}
}