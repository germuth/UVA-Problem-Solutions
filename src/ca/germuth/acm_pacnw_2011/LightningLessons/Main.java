package ca.germuth.acm_pacnw_2011.LightningLessons;

import java.util.Scanner;

/**
 * Lightning Lessons 
 * ACM ICPC Pacific Northwest Regionals 2011 
 * Problem F
 * 
 * Fairly simple problem but with some gotchas. "ap!" is a possible output, for ex. 
 * 2 
 * 3 0 0 0 = "ap!" 
 * 1 0 = "ap!" 
 * 
 * Make sure you stop counting as soon as the remaining array is all zero
 * 
 * @author germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();
		for (int a = 0; a < cases; a++) {
			int bolts = s.nextInt();
			int[] amps = new int[bolts];
			for (int i = 0; i < bolts; i++) {
				amps[i] = s.nextInt();
			}

			int cycles = 0;
			// if all zero, then we are already done
			if (!allZero(amps, 0)) {
				for (int i = 0; i < bolts - 1; i++) {
					// perform amplitude change
					for (int j = bolts - 1; j > i; j--) {
						amps[j] = amps[j] - amps[j - 1];
					}
					cycles++;

					// check if lightning bolt is converged
					if (allZero(amps, i + 1)) {
						break;
					}
				}
			}

			if (amps[bolts - 1] > 0) {
				System.out.println("*fizzle*");
			} else if (amps[bolts - 1] < 0) {
				System.out.println("*bunny*");
			} else {
				String zzz = "";
				for (int i = 0; i < cycles; i++) {
					zzz += "z";
				}
				System.out.println(zzz + "ap!");
			}
		}
		s.close();
	}

	// if array all zero in range [from, arr.length]
	public static boolean allZero(int[] arr, int from) {
		for (int i = from; i < arr.length; i++) {
			if (arr[i] != 0) {
				return false;
			}
		}
		return true;
	}
}
