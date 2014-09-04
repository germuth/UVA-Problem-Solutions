package ca.germuth.uva_10310;

import java.io.IOException;
import java.util.StringTokenizer;

class Main implements Runnable {
	static String ReadLn( int maxLength ) { // utility function to read from
		// stdin,
		// Provided by Programming-challenges, edit for style only
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

	@Override
	public void run() {

		while ( true ) {

			String input = Main.ReadLn( 255 );
			StringTokenizer t2 = new StringTokenizer( input );
			int numHoles = Integer.parseInt( t2.nextToken() );

			double gopherX = Double.parseDouble( t2.nextToken() );
			double gopherY = Double.parseDouble( t2.nextToken() );
			double dogX = Double.parseDouble( t2.nextToken() );
			double dogY = Double.parseDouble( t2.nextToken() );

			Hole[] holes = new Hole[numHoles];
			
			for ( int i = 0; i < numHoles; i++ ) {
				String hole = Main.ReadLn(255);
				StringTokenizer t3 = new StringTokenizer( hole );
				double x = Double.parseDouble(t3.nextToken() );
				double y = Double.parseDouble(t3.nextToken() );
				Hole h = new Hole(x,y);
				holes[i] = h;
			}
			
			boolean found = false;
			
			forloop:
			for(int i = 0; i < holes.length; i++){
				Hole h = holes[i];
				double x = h.getX();
				double y = h.getY();
				
				double gopherD = distance( gopherX, gopherY, x, y);
				double dogD = distance( dogX, dogY, x, y);
				
				if(gopherD * 2 <= dogD  ){
					System.out.print("The gopher can escape through the hole at (");
					System.out.format("%.3f", x);
					System.out.print(",");
					System.out.format("%.3f", y);
					System.out.println(").");
					found = true;
					break forloop;
				}
			}
			
			if(!found){
				System.out.println("The gopher cannot escape.");
			}
			
			String empty = Main.ReadLn( 255 );
		}
	}
	
	private double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) );
	}
}
class Hole{
	private double x;
	private double y;
	public Hole(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
