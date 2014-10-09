package ca.germuth.acm_pacnw_2013.L;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Languages Problem L
 * 
 * Fairly simple string manipulation, make sure you handle case!
 * @author Germuth
 */
class Main {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 
//		Scanner s = null;
//		try {
//			s = new Scanner(new File("test.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int numL = Integer.parseInt(s.nextLine());
		HashMap<String, String> wordToL = new HashMap<String, String>();
		for(int i = 0; i < numL; i++){
			String line = s.nextLine();
			String[] words = line.split(" ");
			for(int j = 1; j < words.length; j++){
				wordToL.put(words[j].toLowerCase(), words[0]);
			}
		}
		//blank line
		s.nextLine();
		
		while(s.hasNextLine()){
			String line = s.nextLine();
			line = line.toLowerCase();
			line = line.replace(",", " ");
			line = line.replace(".", " ");
			line = line.replace("!", " ");
			line = line.replace(";", " ");
			line = line.replace("?", " ");
			line = line.replace("(", " ");
			line = line.replace(")", " ");
			//just in case
			line = line.replace("[", " ");
			line = line.replace("]", " ");
			line = line.replace("{", " ");
			line = line.replace("}", " ");
			String[] words = line.split(" ");
			for(int i = 0; i < words.length; i++){
				if(words[i].isEmpty()){
					continue;
				}
				if(wordToL.containsKey(words[i])){
					System.out.println(wordToL.get(words[i]));
					break;
				}
			}
			
		}
		s.close();
	}
}