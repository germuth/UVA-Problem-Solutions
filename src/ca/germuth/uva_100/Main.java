package ca.germuth.uva_100;

import java.io.IOException;
import java.util.Scanner;

class Main implements Runnable {
	static String ReadLn(int maxLength) { // utility function to read from
		// stdin,
		// Provided by Programming-challenges, edit for style only
		byte line[] = new byte[maxLength];
		int length = 0;
		int input = -1;
		try {
			while (length < maxLength) {// Read until maxlength
				input = System.in.read();
				if ((input < 0) || (input == '\n'))
					break; // or until end of line input
				line[length++] += input;
			}

			if ((input < 0) && (length == 0))
				return null; // eof
			return new String(line, 0, length);
		} catch (IOException e) {
			return null;
		}
	}

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
		
		while(true){
			String input = Main.ReadLn(100000);
			Scanner s = new Scanner(input);
			int first = s.nextInt(); 
			int second = s.nextInt(); 
			
			int smallest; 
			int largest; 
			
			if(first > second){
				largest = first; 
				smallest = second;
			}
			else{
				largest = second;
				smallest = first; 
			}
			int count = 0; 
			int largestCount = 0; 
			
			for(int i = smallest; i <=largest; i++){
				int num = i; 
				count = 0; 
				while(num != 1){
					
					if(num % 2 == 0){
						num = num / 2; 
					}
					else{
						num = 3*num + 1;
					}
					count++; 
				}
				if(count > largestCount){
					largestCount = count; 
				}
			}
			
			System.out.println(first+ " " + second + " " + (largestCount+1) );
		}
	}
}
