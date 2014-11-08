package ca.germuth.acm_greaterNY_2013.IslandsInDataStream;

import java.util.Scanner;

/**
 * Islands in the Data Stream
 * ACM ICPC Greater New York Regionals 2013
 * Problem A
 * 
 * Since there are only ever 15 integers in the list, 
 * you can simply try all possible subsequences and test them 
 * for validity.
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();
		for (int a = 0; a < cases; a++) {
			int caseNumber = s.nextInt();
			int[] input = new int[15];

			for (int i = 0; i < input.length; i++) {
				input[i] = s.nextInt();
			}

			int numIslands = 0;
			// for each possible length
			for (int l = 1; l <= 13; l++) {
				// try all islands of length l
				int length = l;
				for (int i = 1; i < input.length - length; i++) {
					int startSeq = i;
					int endSeq = i + (length - 1);

					int before = input[startSeq - 1];
					int after = input[endSeq + 1];

					boolean isIsland = true;
					// iterate through sequence and see if it is larger
					for (int j = startSeq; j <= endSeq; j++) {
						if (input[j] <= before || input[j] <= after) {
							isIsland = false;
							break;
						}
					}

					if (isIsland) {
						numIslands++;
					}
				}
			}

			System.out.println(caseNumber + " " + numIslands);
		}
		s.close();
	}
}
