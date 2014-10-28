package ca.germuth.acm_southeastusa_2013.div2.C;

import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (true) {
			String in = s.nextLine();
			if (in.trim().equals("0")) {
				break;
			}

			// comment taken from solution:

			// All of the satellites have unique ping times, and they all start
			// at 0.
			// So, skipping time 0, if the earliest ping you see is at time k,
			// then there has to be a satellite pinging at k. Furthermore, that
			// has
			// to be the fastest pinging satellite.

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
