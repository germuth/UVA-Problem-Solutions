package ca.germuth.uva_116;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
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
	public static long[][] cyclinder;
	public static int height;
	public static int length;
	public static ArrayList<Integer>[][] path;
	public static long[][] cost;

	@Override
	public void run() {

		while ( true ) {

			String coord = Main.ReadLn( 255 );
			//Scanner s = new Scanner(coord);
			//height = Integer.parseInt(s.next()); 
			//length = Integer.parseInt(s.next()); 
			StringTokenizer t1 = new StringTokenizer( coord );
			height = Integer.parseInt( t1.nextToken() );
			length = Integer.parseInt( t1.nextToken() );

			cyclinder = new long[height][length];
			/**
			for ( int i = 0; i < height; i++ ) {
				String line = Main.ReadLn( 1000 );
				StringTokenizer t2 = new StringTokenizer( line );
				for ( int j = 0; j < length; j++ ) {
					cyclinder[i][j] = Integer.parseInt( t2.nextToken() );
				}
			}
			*/
			ArrayList<Integer> input = new ArrayList<Integer>();
			while(input.size() != (height*length)){
				String line = Main.ReadLn(1000);
				StringTokenizer t2 = new StringTokenizer( line );
				while(t2.hasMoreTokens()){
					input.add(Integer.parseInt(t2.nextToken()));
				}
			}
			
			int a = 0;
			for(int i = 0; i < height; i++){
				for(int j = 0; j < length; j++){
					cyclinder[i][j] = input.get(a);
					a++;
				}
			}
			
			cost = new long[height][length];
			for(int i= 0; i < height; i++){
				for( int j = 0; j < length; j++){
					cost[i][j] = Long.MAX_VALUE;
				}
			}
			path = new ArrayList[height][length]; 
			for(int i = 0; i < height; i++){
				cost[i][0] = cyclinder[i][0];
				path[i][0] = new ArrayList<Integer>();  
				path[i][0].add(i+1);
			}
			for (int i = 1; i < length; i++) {
				for (int j = 0; j < height; j++) {
					long upper = cost(j - 1, i - 1);
					long middle = cost(j, i - 1);
					long lower = cost(j + 1, i - 1);
					
					long min = -1;
					ArrayList<Integer> patht = null;
					if (smallerThanBoth(lower, upper, middle)) {
						min = lower;
						patht = path(j + 1, i - 1);					
					}
					else if (smallerThanBoth(middle, lower, upper)) {
						min = middle;
						patht = path(j, i - 1);
					}
					else if (smallerThanBoth(upper, lower, middle)){
						min = upper;
						patht = path(j - 1, i - 1);
					}
					else{
						if(upper == middle && middle == lower){
							ArrayList<Integer> upperPath = path(j - 1, i - 1);
							ArrayList<Integer> middlePath = path(j, i - 1);
							ArrayList<Integer> lowerPath = path(j + 1, i - 1);
							if(lessThan(lowerPath, middlePath) && lessThan(lowerPath, upperPath)){
								min = lower;
								patht = lowerPath;
							}
							else if(lessThan(middlePath, upperPath) && lessThan(middlePath, lowerPath)){
								min = middle;
								patht = middlePath;
							}
							else{
								min = upper;
								patht = upperPath;
							}
						}
						else if(upper == middle){
							ArrayList<Integer> upperPath = path(j - 1, i - 1);
							ArrayList<Integer> middlePath = path(j, i - 1);
							if(lessThan(upperPath, middlePath)){
								min = upper;
								patht = upperPath;
							}
							else{
								min = middle;
								patht = middlePath;
							}
						}
						else if(upper == lower){
							ArrayList<Integer> upperPath = path(j - 1, i - 1);
							ArrayList<Integer> lowerPath = path(j + 1, i - 1);
							if(lessThan(upperPath, lowerPath)){
								min = upper;
								patht = upperPath;
							}
							else{
								min = lower;
								patht = lowerPath;
							}
						}
						else if(middle == lower){
							ArrayList<Integer> middlePath = path(j, i - 1);
							ArrayList<Integer> lowerPath = path(j + 1, i - 1);
							if(lessThan(middlePath, lowerPath)){
								min = middle;
								patht = middlePath;
							}
							else{
								min = lower;
								patht = lowerPath;
							}
						}
					}
					
					long theCost = cyclinder[j][i] + min;
					ArrayList<Integer> thePath = new ArrayList<Integer>();
					for(int k = 0; k < patht.size(); k++){
						thePath.add(patht.get(k));
					}
					thePath.add(j + 1);
					if(theCost < cost[j][i]){
						cost[j][i] = theCost;
						path[j][i] = thePath;
					}
				}
			}
			long min = Long.MAX_VALUE;
			int index = -1;
			for(int i = 0; i < height; i++){
				long temp = cost(i, length - 1);
				if(min > cost(i, length - 1)){
					min = cost(i, length - 1);
					index = i;
				}
				else if(min == cost(i, length - 1)){
					if(lessThan(path[i][length - 1], path[index][length - 1])){
						index = i;
					}
				}
			}
			
			ArrayList<Integer> answer = path(index, length - 1);
			for(int i = 0; i < answer.size() - 1; i++){
				System.out.print(answer.get(i) + " ");
			}
			System.out.println(answer.get(answer.size() - 1));
			System.out.println(cost(index, length - 1));
		}

	}
	
	private static boolean lessThan(ArrayList<Integer> first, ArrayList<Integer> second){
		if(first.size() != second.size()){
			System.out.println("FIRST " + first); 
			System.out.println("SECOND " + second);
			System.err.println("WHY ARE THE PATHS NOT EQUAL");
		}
		for(int i = 0; i < first.size(); i++){
			int one = first.get(i);
			int two = second.get(i);
			if(one > two){
				return false;
			}
			else if(one < two){
				return true;
			}
		}
		return false;
	}
	
	private static boolean smallerThanBoth(long test, long first, long second){
		if(test < first && test < second){
			return true;
		}
		return false;
	}
	
	private static long cost(long y, long x){
		while(y >= height){
			y -= height;
		}
		while(y < 0){
			y += height;
		}
		while(x >= length){
			x -= length;
		}
		while(x < 0){
			x += length;
		}
		return cost[(int) y][(int) x];
	}
	
	private static ArrayList<Integer> path(int y, int x){
		while(y >= height){
			y -= height;
		}
		while(y < 0){
			y += height;
		}
		while(x >= length){
			x -= length;
		}
		while(x < 0){
			x += length;
		}
		return path[y][x];
	}
	
	class Path{
		private ArrayList<Integer> path = new ArrayList<Integer>();
		private int x; 
		private int y;
		private int cost;
		public Path(int x, int y, ArrayList<Integer> path, int cost){
			this.x = x;
			this.y = y;
			this.path = path; 
			this.path.add(y);
			this.cost = cost;
		}
		public ArrayList<Integer> getPath() {
			return path;
		}
		public void setPath(ArrayList<Integer> path) {
			this.path = path;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
	}
}