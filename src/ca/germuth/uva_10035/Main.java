package ca.germuth.uva_10035;

import java.util.Scanner;
/**
 * Primary Arithmetic 
 * 5.9.1
 * PC/UVA ID: 11501/10035
 * 
 * Solved by simply taking in each number as string and iterating over each character.
 * If the sum is above 9, then a carry must take place
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

		String first = s.next();
		String second = s.next();
		
		long firstVal = Long.parseLong(first);
		long secondVal = Long.parseLong(second);
		while (firstVal != 0 || secondVal != 0) {
			int carries = 0;
			
			//determine which string is longer
			int diff = first.length() - second.length();
			if(diff > 0){
				for(int i = 0; i < diff; i++){
					second = "0" + second;
				}
			}else if(diff < 0){
				for(int i = 0; i < (-diff); i++){
					first = "0" + first;
				}
			}
			
			int carriedValue = 0;
			//now strings same length
			for(int i = first.length() - 1; i >= 0; i--){
				int fir = Character.getNumericValue(first.charAt(i));
				int sec = Character.getNumericValue(second.charAt(i));
				
				int sum = fir + sec + carriedValue;
				if(sum > 9){
					carries++;
					carriedValue = sum / 10;
				}else{
					carriedValue = 0;
				}
			}
			
			if(carries == 0){     
				System.out.println("No carry operation.");
			}else if(carries == 1){
				System.out.println("1 carry operation.");                   
			}else{
				System.out.println(carries + " carry operations.");
			}
			
			first = s.next();
			second = s.next();
			firstVal = Long.parseLong(first);
			secondVal = Long.parseLong(second);
		}
		s.close();
	}
}

