package ca.germuth.uva_10132;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	@Override
	public void run() {
		new myStuff().run();
	}
}

class myStuff implements Runnable {

	@Override
	public void run() {
		
		Scanner s = new Scanner( System.in );
		int cases = Integer.parseInt( s.nextLine() );
		s.nextLine();

		List<String> input = new LinkedList<String>();
		Set<String> answer = new HashSet<String>(){
			//print out first element of the list
			public String toString(){
				Iterator<String> i = this.iterator();
				return (String) i.next();
			}
		};
		
		for ( int t = 0; t < cases; ++t ) {
			while ( s.hasNextLine() ) {
				String line = s.nextLine();
				if ( line.isEmpty() ) {
					break;
				}
				input.add(line);
			}
			Collections.sort(input);
			extracted(input);
			
			for( int i = 0; i < input.size() / 2; i++){
				String first = input.get(i);
				String second = input.get(input.size() - i - 1);
				
				if(answer.isEmpty()){
					answer.add(first + second);
					answer.add(second + first);
				}
				else{
					Set<String> newList = new HashSet<String>();
					newList.add(first + second);
					newList.add(second + first);
					
					answer.retainAll(newList);
				}
			}
			
			System.out.println(answer);
			if(t != cases - 1){
				System.out.println("");
			}
			
			input.clear();
			answer.clear();
		}	
	}

	private void extracted(List<String> input) {
		Collections.sort(input, new Comparator<String>(){

			@Override
			public int compare(String one, String two) {
				if(one.length() > two.length()){
					return 1;
				}
				else if(one.length() < two.length()){
					return -1;
				}
				return 0;
			}
		});
	}
}
