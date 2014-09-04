package ca.germuth.uva_10010;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
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
	private static char[][] characterMap;
	private static PrintWriter pw; 
	public void run() {
		
		try {
			
			pw = new PrintWriter("output.txt");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String trials = Main.ReadLn(100000);
		Scanner s = new Scanner(trials);
		int trialNum = s.nextInt();

		int a = 0;
		while (a < trialNum) {
			String empty = Main.ReadLn(10000);
			String sizes = Main.ReadLn(100000);

			Scanner s2 = new Scanner(sizes);
			int y = s2.nextInt();
			int x = s2.nextInt();

			characterMap = new char[y][x];

			for (int j = 0; j < y; j++) {

				String line = Main.ReadLn(1000000);
				line = line.toUpperCase();
				for (int i = 0; i < x; i++) {
					characterMap[j][i] = line.charAt(i);
				}
			}

			String numberLine = Main.ReadLn(1000000);
			Scanner s3 = new Scanner(numberLine);
			int numberOfSearchWords = s3.nextInt();

			String[] words = new String[numberOfSearchWords];

			for (int d = 0; d < numberOfSearchWords; d++) {
				words[d] = Main.ReadLn(10000000).toUpperCase();
			}

			for (String theWord : words) {
				
				Pair p = findWord(theWord);

				//if (p != null) {

					while (p.getParent() != null) {
						p = p.getParent();
					}

					int yCo = p.getyPos()+1;
					int xCo = p.getxPos()+1; 
					
					System.out.println( yCo + " " + xCo );
					
					if(theWord.equals(words[words.length-1])){
						
					}
			}
			
			//pw.print("a is "+a);
			if(a != trialNum - 1){

				System.out.println("");
				//pw.println("");
			}
			a++; 

		}
		pw.close(); 
	}

	public static Pair findWord(String word) {
		LinkedList<Pair> openList = new LinkedList<Pair>();

		for (int y = 0; y < characterMap.length; y++) {
			for (int x = 0; x < characterMap[y].length; x++) {

				if (characterMap[y][x] == word.charAt(0)) {
					openList.add(new Pair(x, y, word.charAt(0), 0));
				}
			}
		}

		Direction[] list = { Direction.ABOVE, Direction.BELOW, Direction.LEFT,
				Direction.RIGHT, Direction.UPPERLEFT, Direction.UPPERRIGHT,
				Direction.LOWERLEFT, Direction.LOWERRIGHT };

		while (!openList.isEmpty()) {
			Pair parent = openList.pop();

			for (Direction direction : list) {

				if (parent.getPosition() == word.length() - 1) {

					return parent;
				}

				if (boundChecker(parent.getxPos(), parent.getyPos(), direction)
						&& letterChecker(parent.getxPos(), parent.getyPos(),
								parent.getPosition(), word.charAt(parent
										.getPosition()), word, direction)) {

					if (parent.getPath() == null
							|| parent.getPath() == direction) {
						openList.add(0, new Pair(parent, direction, word));
					}
				}
			}
		}
		return null;
	}

	private static boolean boundChecker(int x, int y, Direction direction) {

		int DirX = direction.getX();
		int DirY = direction.getY();

		if (x + DirX < 0 || x + DirX >= characterMap[0].length) {
			return false;
		}

		if (y + DirY < 0 || y + DirY >= characterMap.length) {
			return false;
		}

		return true;
	}

	private static boolean letterChecker(int x, int y, int position,
			char letter, String word, Direction direction) {

		int DirX = direction.getX();
		int DirY = direction.getY();

		if (word.charAt(position + 1) != characterMap[y + DirY][x + DirX]) {
			return false;
		}

		return true;
	}

}

class Pair {
	private int xPos;
	private int yPos;
	private char letter;
	private int position;
	private Pair parent;
	private Direction path;

	public Pair(int x, int y, char theLetter, int thePosition) {
		this.parent = null;
		this.xPos = x;
		this.yPos = y;
		this.letter = theLetter;
		this.position = thePosition;
		this.path = null;
	}

	public Pair(Pair parent, Direction direction, String word) {
		this.parent = parent;
		this.xPos = parent.getxPos() + direction.getX();
		this.yPos = parent.getyPos() + direction.getY();
		this.position = parent.getPosition() + 1;
		this.letter = word.charAt(this.position);
		this.path = direction;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Pair getParent() {
		return parent;
	}

	public Direction getPath() {
		return path;
	}

}

enum Direction {
	ABOVE(0, -1), BELOW(0, 1), RIGHT(1, 0), LEFT(-1, 0), UPPERLEFT(-1, -1), UPPERRIGHT(
			1, -1), LOWERLEFT(-1, 1), LOWERRIGHT(1, 1);

	private int x;
	private int y;

	private Direction(int theX, int theY) {
		this.x = theX;
		this.y = theY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}