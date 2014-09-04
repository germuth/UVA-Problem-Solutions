package ca.germuth.uva_846;

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

class Main implements Runnable {
	static String ReadLn( int maxLength ) { 
		byte line[] = new byte[maxLength];
		int length = 0;
		int input = -1;
		try {
			while ( length < maxLength ) {// Read until maxlength
				input = System.in.read();
				if ( ( input < 0 ) || ( input == '\n' ) )
					break; // or until end of line input
				line[length++] += input;
			}

			if ( ( input < 0 ) && ( length == 0 ) )
				return null; // eof
			return new String( line, 0, length );
		}
		catch ( IOException e ) {
			return null;
		}
	}

	public static void main( String args[] ) // entry point from OS
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
	HashMap<Integer, Integer> map;
	@Override
	public void run() {

		String numberOfTrys = Main.ReadLn(100);
		StringTokenizer t0 = new StringTokenizer(numberOfTrys);
		int numOfTrys = Integer.parseInt(t0.nextToken());
		while ( numOfTrys > 0 ) {
			String input = Main.ReadLn( 100 );
			StringTokenizer t1 = new StringTokenizer( input );
			int a = Integer.parseInt( t1.nextToken() );
			int b = Integer.parseInt( t1.nextToken() );
			System.out.println( answer( b - a ) );
			numOfTrys--;
		}

	}
	private static int answer(int  n){
		int i = 0;
		boolean everySecondTry = false;
		int stepsTaken =  1; 
		while( n > 0 ){
			n = n - stepsTaken;
			//increment steps Taken every second time
			if( everySecondTry ){
				stepsTaken++;
			}
			//make opposite of previous value
			everySecondTry = !everySecondTry;
			i++;
		}
		return i;
	}
}
