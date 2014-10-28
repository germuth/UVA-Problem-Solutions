package ca.germuth.uva_10198;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Counting
 * 6.6.3
 * PC/UVA 110603/101+9
 * 
 * Solve it by hand for n=2, 3, and 4, and you may start to see recurence
 * relationship. Think about it dynamically, If we want to solve for all the ones
 * which add up to 5, we can use:
 * 	 all the ones that add up to 4, with a 1 or 4 at the begining plus 
 *   all the ones that add up to 3, with a 2 at the beginning,
 *   and finally, all the ones that add up to 2, with a 3 at the beginning.
 * In summary, 
 * f(n) = f(n-1) + f(n-2) + f(n-3) + f(n-4) 
 * but since 1 == 4 
 * f(n) = f(n-1) + f(n-2) + f(n-3) + f(n-1) 
 * f(n) = 2*f(n-1) + f(n-2) + f(n-3)
 * 
 * @author germuth
 */
class Main {
	static HashMap<Integer, BigInteger> ans = new HashMap<Integer, BigInteger>();

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (s.hasNextInt()) {
			int num = s.nextInt();

			System.out.println(solve(num).toString());
		}
		s.close();
	}

	static BigInteger solve(int n) {
		if (ans.containsKey(n)) {
			return ans.get(n);
		} else {
			if (n == 1) {
				return new BigInteger("2");
			} else if (n == 2) {
				return new BigInteger("5");
			} else if (n == 3) {
				return new BigInteger("13");
			} else {
				// f(n) = f(n-1) + f(n-2) + f(n-3) + f(n-4)
				// f(n) = 2*f(n-1) + f(n-2) + f(n-3)
				BigInteger an = solve(n - 1).multiply(new BigInteger("2"))
						.add(solve(n - 2)).add(solve(n - 3));
				ans.put(n, an);
				return an;
			}
		}
	}
}
