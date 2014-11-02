package ca.germuth.acm_southeastusa_2012.div2.F;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Party Games
 * ACM ICPC SouthEast USA Regionals 2012
 * Problem F Division 2
 * 
 * This problem is simply in theory but has a lot of edge cases. 
 * You can start by simply sorting the array lexigraphically and
 * grab mid and mid + 1 element.
 * Then you iterate character by character through these strings
 * and find the first difference, then (as long as the difference is by more than one charcater)
 * you can just "increment" that letter. However, there are tons of edge 
 * cases and I think the best way to solve this problem is to just try a bunch of cases,
 * handle an edge case, and continue.
 * Some of the cases I used are as follows:
 * 2
 * FRED
 * FREE
 * 2
 * FRED
 * FRF
 * 2
 * FRED
 * FS
 * 2
 * FRZ
 * FRZA
 * 2
 * AZZZ
 * BZZZ
 * 2
 * AZZZ
 * B
 * 2
 * ZZAZZA
 * ZZAZZB
 * 2
 * FZZZZZZZZZ
 * G
 * 2
 * FZZZZZZ
 * GZ
 * @author Aaron
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		while(true){
			int n = s.nextInt();
			
			if(n == 0){
				break;
			}
			
			String[] arr = new String[n];
			for(int i = 0; i < n; i++){
				arr[i] = s.next();
			}
			
			Arrays.sort(arr);
			int mid = arr.length / 2;
			mid--;
			
			String middle = arr[mid];
			String next = arr[mid+1];
			
			//first just use the middle as the splitting string
			String divider = middle;
			String answer = null;
			//but now we have to see if we can make it smaller
			//can only advance further in the alphabet by increasing a letter, but we also only want things that are strictly less size
			//find first character we can change (don't change the very last one though)
			for(int i = 0; i < Math.min(middle.length(), next.length()); i++){
				char m = middle.charAt(i);
				char ne = next.charAt(i);
				if(m != ne && m != 'Z'){
					//we can change this letter
					char newC = (char)(m+1);
					//don't want to change last letter though, should just use divider
					if(newC != ne && i < middle.length() - 1){
						answer = divider.substring(0, i) + newC;
						break;							
					}//if increment equals the character, but the 2nd string is longer we can just take first
					else if(i < next.length() - 1 && i < middle.length() - 1){
						answer = divider.substring(0, i) + ne;
						break;
					}
					//only consider incrementing the next character if there is a next character
					//and if there is a character after that, if there isn't a second character
					//then we are already at original length anyway and should just use middle
					int add = 1;
					char nextAvailable = 'Z';
					while(nextAvailable == 'Z' && i+add < divider.length() - 1){
						nextAvailable = middle.charAt(i+add);
						add++;
					}
					if(nextAvailable != 'Z'){
						add--; //it was added one extra time
						answer = divider.substring(0, i+add) + (char)(divider.charAt(i+add)+1);
						break;						
					}
				}
			}
			
			if(answer == null){
				answer = divider;
			}
	
			System.out.println(answer);
			
		}
		s.close();
	}
}
