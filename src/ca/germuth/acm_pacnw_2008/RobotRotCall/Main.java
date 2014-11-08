package ca.germuth.acm_pacnw_2008.RobotRotCall;

import java.util.HashMap;
import java.util.Scanner;
/**
 * Robot Roll Call
 * ACM ICPC Pacific Northwest 2008 Regionals 
 * Problem B
 * 
 * No tricks here. Simply place all of the dictionary words in the a hashmap.
 * Then go through each word in the input lines and test if the hashmap
 * contains that word. The input lines can be split into words
 * with String.split("[\\W]"). \\W is the regular expression to split
 * by all non-word or number characters
 * @author Aaron
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int cases = s.nextInt();

		for (int a = 0; a < cases; a++) {
			int words = s.nextInt();

			String[] inWords = new String[words];
			HashMap<String, Boolean> dictionary = new HashMap<String, Boolean>();
			for (int i = 0; i < words; i++) {
				inWords[i] = s.next();
				dictionary.put(inWords[i], false);
			}

			int lines = s.nextInt();
			s.nextLine();// clear the current line
			for (int i = 0; i < lines; i++) {
				String in = s.nextLine();
				String[] tokens = in.split("[\\W]");
				for (int j = 0; j < tokens.length; j++) {
					String token = tokens[j];
					if (dictionary.containsKey(token)) {
						dictionary.put(token, true);
					}
				}
			}

			System.out.println("Test set " + (a + 1) + ":");
			for (int i = 0; i < inWords.length; i++) {
				if (dictionary.get(inWords[i])) {
					System.out.println(inWords[i] + " is present");
				} else {
					System.out.println(inWords[i] + " is absent");
				}
			}
			System.out.println("");

		}
		s.close();
	}
}
