package ca.germuth.acm_pacnw_2012.B;

import java.util.Scanner;

/**
 * Magic Multiple 
 * ACM ICPC Pacific Northwest Regionals 2012
 * Problem B
 * 
 * Use boolean array to keep track of which digits we have encountered.
 * No Special tricks here
 * @author Germuth
 */
class Main {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);

		while (s.hasNext()) {
			int n = s.nextInt();

			boolean[] digits = new boolean[10];

			boolean allTrue = false;
			long iteration = 1;
			while (!allTrue) {
				String nVal = (n * iteration) + "";
				for (int i = 0; i < nVal.length(); i++) {
					char c = nVal.charAt(i);
					int charVal = Character.getNumericValue(c);
					digits[charVal] = true;
				}

				boolean all = true;
				for (int i = 0; i < digits.length; i++) {
					if (!digits[i]) {
						all = false;
						break;
					}
				}

				allTrue = all;

				iteration++;
			}

			// iteration will still be incremented in the case of a success
			System.out.println(iteration - 1);
		}
	}
}
