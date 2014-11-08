package ca.germuth.acm_pacnw_2008.PencilsFrom19thCentury;

import java.util.Scanner;

/**
 * Pencils from 19th Century
 * ACM ICPC Pacific NorthWest Regionals 2008
 * Problem E
 * 
 * This problem would have a hard problem but N was limited at only 256.
 * Basically, we have to find all integer solutions for 
 * 
 * a + b + c = N (there are N coins) as well as
 * 4a + b/2 + c/4 = N (coins add up to $N)
 * 
 * Since N was so small, we could explicitly try all values for a, b, c 
 * in the required time limit. 
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int caseNum = 1;
		while (true) {
			int N = s.nextInt();

			if (N == 0) {
				break;
			}

			System.out.println("Case " + caseNum + ": " + N + " pencils for "
					+ N + " cents");
			// 4a + b/2 + c/4 = N
			int count = 0;
			//use doubles to avoid integer division rounding
			for (double a = 1; a < N - 2; a++) {
				for (double b = 1; b < N - 2; b++) {
					for (double c = 1; c < N - 2; c++) {
						if (a + b + c == N && 4.0 * a + b / 2.0 + c / 4.0 == N) {
							if (count != 0) {
								System.out.println("");
							}
							System.out.println((int) a + " at four cents each");
							System.out.println((int) b + " at two for a penny");
							System.out
									.println((int) c + " at four for a penny");
							count++;
						}
					}
				}
			}

			if (count < 1) {
				System.out.println("No solution found.");
			}
			System.out.println("");
			caseNum++;
		}
		s.close();
	}
}
