package ca.germuth.acm_southeastusa_2013.div2.C;

import java.util.Scanner;

/**
 * Ping!
 * ACM ICPC SouthEast USA 2013 Regionals
 * Problem C Division 2
 * 
 * All of the satellites have unique ping times, and they all start at 0. Therefore the
 * first 1 you see must represent the first satellite. After finding a satellite, you can
 * proceed through a string and "reverse" its mark, by setting all 0s to 1s and vice versa. 
 * This will erase this satellites pings, and then the first 1 you see again is the next satellite. 
 * Proceeding this way, you can find all satellites from fastest to slowest pinging.
 * @author Aaron
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (true) {
			String in = s.nextLine();
			if (in.trim().equals("0")) {
				break;
			}

			String answer = "";
			char[] pings = in.toCharArray();
			// first character is time 0
			// all satellites will ping here
			// start at 1
			for (int i = 1; i < pings.length; i++) {
				if (pings[i] == '1') {
					answer += i + " ";

					// remove this satellite from the string
					for (int j = 1; j < pings.length; j++) {
						if (j % i == 0) {
							if (pings[j] == '0') {
								pings[j] = '1';
							} else {
								pings[j] = '0';
							}
						}
					}
				}
			}
			System.out.println(answer.trim());
		}
		s.close();
	}
}
