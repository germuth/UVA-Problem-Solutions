package ca.germuth.uva_00701;

import java.util.Scanner;

/**
 * The Archaeologist's Dilemma 
 * PCA 110503/ UVA 00701 
 * 5.9.3
 *
 * Explanation here:
 * https://codingstrife.wordpress.com/2013/07/28/solution-uva701-pc110503-the-archaeologists-dilemma/
 * 
 * @author germuth
 *
 */
public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (true) {
			int in = s.nextInt();
			double lowK, highK;
			double T = Math.floor(Math.log10(in) + 2);
			while (true) {
				lowK = Math.ceil(log2(in) + T * log2(10));
				highK = Math.floor(log2(in + 1) + T * log2(10));
				if (lowK == highK) {
					break;
				}
				T++;
			}
			System.out.println((long) lowK);
		}
	}

	public static double log2(double in) {
		return Math.log(in) / Math.log(2);
	}
}