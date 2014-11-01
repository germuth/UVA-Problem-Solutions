package ca.germuth.uva_10079;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Pizza Cutting 
 * UVA 10079
 * 
 * After solving first couple of cases, you can see that recurrence relation is
 * f(n) = f(n-1) + n However, it is too slow to simply use dynamic programming
 * and solve up to 210000000. Therefore we need a closed form. However, since
 * f(n) is the sum of all natural numbers plus 1: f(5) = 1 + 2 + 3 + 4 + 5 (+ 1)
 * = 16, closed forms are quite numerous. For example f(n) = (n+1 choose 2) + 1
 * OR f(n-1) = n(n-1)/2 + 1 (number of edges in fully connected graph) and
 * others
 * 
 * @author germuth
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		// for(int i = 1000; i < 210000000; i += 1000){
		// solve(i);
		// }
		while (true) {
			int n = s.nextInt();

			if (n < 0) {
				break;
			}

			System.out.println(combination(n + 1, 2).add(new BigInteger("1"))
					.toString());
		}
		s.close();
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
