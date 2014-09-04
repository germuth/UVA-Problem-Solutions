package ca.germuth.uva_10089;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

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
		
		String input = Main.ReadLn(25);
		String zero = "0";
		while(!input.equals(zero)){
			int packages = Integer.parseInt(input);
			int[][] offsetVectors = new int[packages][2];
			for(int i = 0; i < packages; i++){
				String in = Main.ReadLn(25);
				StringTokenizer t1 = new StringTokenizer(in);
				int x = Integer.parseInt(t1.nextToken());
				int y = Integer.parseInt(t1.nextToken());
				int z = Integer.parseInt(t1.nextToken());
				
				offsetVectors[i][0] = y - x;
				offsetVectors[i][1] = z - x;
			}
			
			double[] tangents = new double[packages];
			
			for(int i = 0; i < packages; i++){
				tangents[i] = Math.atan2( (double) offsetVectors[i][1], (double) offsetVectors[i][0] );
				tangents[i] += Math.PI;
				
			}
			
			Arrays.sort(tangents);
			
			boolean possible = true; 
			
			for(int i = 1; i < packages; i++){
				if(tangents[i] - tangents[i - 1] > Math.PI ){
					possible = false;
				}
			}
			
			if(possible == true){
				if( 2*Math.PI - tangents[ packages - 1 ] + tangents[0] > Math.PI){
					possible = false;
				}
			}
			
			if(possible){
				System.out.println("Yes");
			}
			else{
				System.out.println("No");
			}
			
			input = Main.ReadLn(25);
		}
	}
}
