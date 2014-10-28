package ca.germuth.uva_10029;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Edit Step Ladders 
 * 9.6.5 
 * PC/UVA ID: 110905/10029
 * 
 * Because the dictionary can be up to 25000 words, the simple n^2 solution of
 * all testing for one letter difference from all words to each other word below
 * it is too slow (25000^2 = 625 million). The key here is that the input
 * strings are at most 16 characters long. This means, for each string you can
 * generate all the possible one character changes by trying to add all 26
 * characters in all positions, deleting each character, and changing each
 * character to the other 25 characters. This doesn't sound like it, but it can
 * be done much faster. For each new generated string, you can test whether it
 * is in the given dictionary (HashMap) and lexigraphically below, if it is, you
 * have found one edit step.
 * 
 * @author germuth
 */
class Main {
	static HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		ArrayList<String> dictionaryList = new ArrayList<String>();
		while (s.hasNext()) {
			String next = s.next().trim();
			// if(next.equals("USING CONSOLE")){
			// break;
			// }
			dictionaryList.add(next);
			dictionary.put(next, 0);
		}

		int max = Integer.MIN_VALUE;
		for (int i = 0; i < dictionaryList.size(); i++) {
			String word = dictionaryList.get(i);
			int curr = dictionary.get(word);

			// generate all character changes
			char changeTo = 'a';
			for (int j = 0; j < word.length(); j++) {
				for (int k = 0; k < 26; k++) {
					String changed = word.substring(0, j)
							+ (char) (changeTo + k) + word.substring(j + 1);
					// only consider if lexigraphically after
					if (word.compareTo(changed) < 0) {
						if (dictionary.containsKey(changed)) {
							if (curr + 1 > dictionary.get(changed)) {
								dictionary.put(changed, curr + 1);
							}
						}
					}
				}
			}

			// delete all characters
			for (int j = 0; j < word.length(); j++) {
				String delete = word.substring(0, j) + word.substring(j + 1);
				if (word.compareTo(delete) < 0) {
					if (dictionary.containsKey(delete)) {
						if (curr + 1 > dictionary.get(delete)) {
							dictionary.put(delete, curr + 1);
						}
					}
				}
			}

			// add all characters
			char addTo = 'a';
			for (int j = 0; j < word.length() + 1; j++) {
				for (int k = 0; k < 26; k++) {
					String added = word.substring(0, j) + (char) (addTo + k)
							+ word.substring(j);
					if (word.compareTo(added) < 0) {
						if (dictionary.containsKey(added)) {
							if (curr + 1 > dictionary.get(added)) {
								dictionary.put(added, curr + 1);
							}
						}
					}
				}
			}

			if (curr > max) {
				max = curr;
			}
		}
		// need to count number of words, not number of transitions
		System.out.println(max + 1);
		s.close();
	}
}
