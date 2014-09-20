package ca.germuth.uva_10018;

import java.util.Scanner;
/**
 * Reverse and Add 
 * 5.9.2
 * PC/UVA ID: 11502/10018
 * 
 * There are no tricks to this solution, simply perform the reversal and
 * addition over and over. Make sure you give a solution of 0 if the input
 * number is already a palindrome, ex. 11
 * @author Germuth
 */
class Main implements Runnable {

	public static void main(String args[]) // entry point from OS
	{
		Main myWork = new Main(); // Construct the bootloader
		myWork.run(); // execute
	}

	public void run() {
		new myStuff().run();
	}
}

class myStuff implements Runnable {
	public void run() {
		Scanner s = new Scanner(System.in);

		int numCases = s.nextInt();
		for(int i = 0; i < numCases; i++){
			long val = s.nextLong();
			
			int iterations = 0;
			long current = val;
			boolean palindrome = false;
			//start adding it until palindrome
			while(!palindrome){
				if(!isPalindrome(current)){
					//reverse string
					long reverse = reverse(current);
					current += reverse;
				}else{
					System.out.println(iterations + " " + current);
					break;
				}
				iterations++;
			}
		}
		s.close();
	}
	
	public static boolean isPalindrome(long val){
		return val == reverse(val);
	}
	
	public static long reverse(long val){
		String valS = val + "";
		String rev = new StringBuilder(valS).reverse().toString();
		return Long.parseLong(rev);
	}
}