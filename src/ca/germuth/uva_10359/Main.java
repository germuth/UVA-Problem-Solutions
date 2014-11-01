package ca.germuth.uva_10359;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Tiling
 * UVA 10359
 * 
 * This problem is tricky. First start by not considered sideways blocks, and
 * think of the ways to arrange only single and double blocks such as the
 * following: | is single block, | | is double block, = is blocks stacked.
 * 
 * || - | | - || - || - | | - || - ... etc
 * 
 * how many ways are there to arrange this, if the list is n size. Well first
 * off we know there can be at most n || blocks, and at most n/2 | | blocks.
 * Rather than solving this for any amount of double (| |) blocks, we can solve
 * it for a given number of | | blocks, x. if there are x blocks of size 2, then
 * the total amount of blocks in the list will be n - x. Some of these will be
 * ||, and some will be | |. out of the n-x positions, we have to choose x of
 * them to contain a | | bigger block. This means there are (n-x chooose x) wasy
 * to arrange this simplified amount. Now, we have to add in = blocks. Each
 * block of size 2 could be either a | | or a =, and keep everything we have
 * done the same. this means, for each x included, we have 2 possibilities. This
 * means for a given x the amount of solutions are: (n-x choose x) * 2^x Now we
 * can simply try all values of x (from 0 to n/2) and sum the entire result.
 * 
 * @author germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (s.hasNext()) {
			int n = s.nextInt();

			System.out.println(solve(n).toString());
		}
		s.close();
	}

	public static BigInteger solve(int n) {
		int half = n / 2;
		if (n % 2 != 0) {
			half++;
		}
		BigInteger ans = new BigInteger("0");
		for (int x = 0; x <= half; x++) {
			BigInteger secondTerm = new BigInteger("2").pow(x);
			ans = ans.add(combination(n - x, x).multiply(secondTerm));
		}

		return ans;
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