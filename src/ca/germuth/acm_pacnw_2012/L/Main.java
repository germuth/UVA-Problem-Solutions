package ca.germuth.acm_pacnw_2012.L;

import java.util.Scanner;
/**
 * Magic Multiple Problem L
 * 
 * Simple character substitution.
 * @author Germuth
 */
class Main {
	static Scanner s;
	static char[] vowel = {'a', 'i', 'y', 'e', 'o', 'u'};
	static char[] vowel2 = {'A', 'I', 'Y', 'E', 'O', 'U'};
	static char[] const1 = {'b', 'k', 'x', 'z', 'n', 'h', 'd', 'c', 'w', 'g', 'p', 'v', 'j', 'q', 't', 's', 'r', 'l', 'm', 'f'};
	static char[] const2 = {'B', 'K', 'X', 'Z', 'N', 'H', 'D', 'C', 'W', 'G', 'P', 'V', 'J', 'Q', 'T', 'S', 'R', 'L', 'M', 'F'};
	public static void main(String args[]) {
		s = new Scanner(System.in);

		while(s.hasNextLine()){
			String line = s.nextLine();
			String output = "";
			for(int i = 0; i < line.length(); i++){
				char curr = line.charAt(i);
				
				if(Character.isAlphabetic(curr)){
					//vowel lowercase
					boolean isVowel = false;
					for(int j = 0; j < vowel.length; j++){
						if(vowel[j] == curr){
							curr = vowel[(j + 3) % vowel.length];
							isVowel = true;
							break;
						}
					}
					//vowel uppercase
					for(int j = 0; j < vowel2.length; j++){
						if(vowel2[j] == curr){
							curr = vowel2[(j + 3) % vowel2.length];
							isVowel = true;
							break;
						}
					}
					if(!isVowel){
						//const lowercase
						for(int j = 0; j < const1.length; j++){
							if(const1[j] == curr){
								curr = const1[(j + 10) % const1.length];
								break;
							}
						}
						
						//vowel uppercase
						for(int j = 0; j < const2.length; j++){
							if(const2[j] == curr){
								curr = const2[(j + 10) % const2.length];
								break;
							}
						}
					}
				}
				output += curr;
			}
			System.out.println(output);	
		}
		s.close();
	}
}