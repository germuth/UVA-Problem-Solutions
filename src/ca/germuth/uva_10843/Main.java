package ca.germuth.uva_10843;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Anne's game 
 * UVA 10843
 * 
 * This problem can be reduced to counting the number of trees that can be made
 * from n nodes. The formula for this is well known at n^n-2. Therefore if you
 * use bigIntegers and remember to mod 2000000011 you are good. Also when n = 1
 * the answer is 1;
 * 
 * @author germuth
 */
class Main {
	static BigInteger DIVISOR = new BigInteger("2000000011");

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int n = s.nextInt();

			BigInteger ans = null;
			if (n == 1) {
				ans = new BigInteger("1");
			} else {
				ans = new BigInteger(n + "");
				ans = ans.pow(n - 2);
			}

			System.out.println("Case #" + (a + 1) + ": "
					+ ans.remainder(DIVISOR).toString());
		}
		s.close();
	}
}
