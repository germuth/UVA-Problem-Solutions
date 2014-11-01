package ca.germuth.uva_10784;

import java.util.Scanner;

/**
 * Diagonals 
 * UVA 10784
 * 
 * f(n) = N = n(n-3)/2 
 * now rearrange for n, with quadratic formula, we get
 * n = 3 +- sqrt(9 + 8N) / 2
 * 
 * We want to take the positive root. And round up, since we are looking
 * for the closest integer solution. Rounding down would give an n-gon
 * with 1 less diagonal
 * @author germuth
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int curr = 1;
		while (true) {
			long N = s.nextLong();
			if (N == 0) {
				break;
			}

			double root1 = (3.0 + Math.sqrt(9.0 + 8.0 * N)) / 2.0;
			double root2 = (3.0 - Math.sqrt(9.0 + 8.0 * N)) / 2.0;
			double actualRoot = Math.max(root1, root2);

			System.out.println("Case " + curr + ": "
					+ (int) Math.ceil(actualRoot));
			curr++;
		}
		s.close();
	}
}
