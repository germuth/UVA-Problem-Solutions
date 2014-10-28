package ca.germuth.acm_pacnw_2012.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Magic Multiple
 * ACM ICPC Pacific Northwest Regionals 2012
 * Problem C
 * 
 * This was a bit of tricky one. 
 * At the surface, you have 60 seconds to perform a simple a BFS through
 * a limited 20 x 20 map. But the only way to have the solution accepted
 * in time is to remember states you have already been to. Which means
 * every time you add a state you need to check all previously visited sites
 * and make sure you haven't been to that state before. This is tricky part, since
 * there are a large number of possible states and comparing each state can
 * take a long time. To speed this up dramatically, you can store the current
 * state in a single integer, as a bitarray. Then you just need to test each
 * integer for equality to see if you have been to that state before.
 * 
 * In my solution, the bitarray was split up as follows: (as seen by static filter ints)
 * digits 0-19: contain the map
 * 		The problem description stated at most 12 ., 1 C, 1 G, and 6 Ps. This means
 * 		there are at most 20 different spots the cube can be, so we have 20 digits. If it 
 * 		is one, it means that position is painted.
 * digits 20-26: holds the sides of the cube. For my solution, it goes 
 * 	25 - Top, 24 - Front, 23 - Down, 22 - Back, 21 - Left, 20 - Right. Again a 1 represents
 * 		painted. 
 * digits 26-31: hold the current position of the cube. Since there are only 20 possible spots,
 * 		we need 5 digits to holds numbers up to 10010 or 20.
 * @author Germuth
 */
class Main {
	static int cubePos_filter =      0b1111100000000000000000000000000;
	static int cubeSides_filter =    0b0000011111100000000000000000000;
	static int map_filter =          0b0000000000011111111111111111111;

	static char[][] map;
	static int[][] indexArray;
	static LinkedList<Integer> openList;
	static HashMap<Integer, Integer> numberMoves;
	static HashMap<Integer, Pair> indexToPair;

	public static void main (String args[])
    {
		Scanner s = new Scanner(System.in);

		while (s.hasNextLine()) {
			String line = s.nextLine();
			
			//important, don't forget
			if(line.isEmpty()){
				break;
			}

			ArrayList<String> inputLines = new ArrayList<String>();
			while (!line.isEmpty() && s.hasNextLine()) {
				inputLines.add(line);

				line = s.nextLine();
			}

			// all input received, translate to char array
			map = new char[inputLines.size()][inputLines.get(0).length()];
			for (int i = 0; i < inputLines.size(); i++) {
				map[i] = inputLines.get(i).toCharArray();
			}

			// input recorded, now create links between index and map coordinates
			// because there are only 20 possible spots, give each one a number, for ex.
			// 0 <-> (0,1), 1 <-> (3,4) 
			
			// goes from index to coordinate
			indexToPair = new HashMap<Integer, Pair>();
			// goes from coordinates to index
			indexArray = new int[map.length][map[0].length];

			int goalState = 0;
			// all 6 sides of cube must be colored in goal state
			goalState += (63 << 20);
			int startState = 0;

			int index = 0;
			// loop through input, and assign startingState and goal State
			// also add the links from index to coords and back
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					switch (map[i][j]) {
						case 'C':
							linkIndex(i, j, index);
							startState += (index << 26);
							index++;
							break;
						case 'G':
							linkIndex(i, j, index);
							goalState += (index << 26);
							index++;
							break;
						case 'P':
							linkIndex(i, j, index);
							startState += (1 << index);
							index++;
							break;
						case '.':
							linkIndex(i, j, index);
							index++;
							break;
					}
				}
			}
			
			int num = findNumberOfMoves(startState, goalState);
			if (num != 0) {
				System.out.println(num);
			} else {
				System.out.println("-1");
			}
		}
		s.close();
    }
	
	public static int findNumberOfMoves(int startState, int goalState) {
		// ready to openList through
		openList = new LinkedList<Integer>();
		openList.add(startState);

		// keep track of visited states and how long it took to get there
		numberMoves = new HashMap<Integer, Integer>();
		numberMoves.put(startState, 0);
		
		int numberOfMoves = 0;
		while (!openList.isEmpty()) {
			int currentState = openList.pop();
			
			//test if goal state
			if (currentState == goalState) {
				//extract number of moves performed from state
				numberOfMoves = numberMoves.get(currentState);
				break;
			}

			//get cube current position and translate into (x, y)
			int currentIndex = (currentState & cubePos_filter) >> 26;
			Pair currP = indexToPair.get((int) currentIndex);

			// try all 4 directions to move
			// up
			searchNeighbour(currentState, currP.x - 1, currP.y, Direction.UP);
			// down
			searchNeighbour(currentState, currP.x + 1, currP.y, Direction.DOWN);
			// left
			searchNeighbour(currentState, currP.x, currP.y - 1, Direction.LEFT);
			// right
			searchNeighbour(currentState, currP.x, currP.y + 1, Direction.RIGHT);
		}

		return numberOfMoves;
	}
	
	public static void linkIndex(int i, int j, int index){
		indexArray[i][j] = index;
		indexToPair.put(index, new Pair(i, j));
	}
	
	public static void searchNeighbour(int state, int x, int y, Direction direction) {
		if (withinBounds(x, y) && map[x][y] != '#') {
			int newState = roll(direction, state, indexArray[x][y]);
			// swap floor and cube face
			newState = swap(newState);

			// get number of moves and add one
			Integer moves = numberMoves.get(state);
			if (moves == null) {
				moves = 0;
			}

			if (!numberMoves.containsKey(newState)) {
				numberMoves.put(newState, moves + 1);
				openList.add(newState);
			}
		}
	}

	public static int swap(int state) {
		int bottomOfCube = 22; // 22nd bit
		int currentLocation = (state & cubePos_filter) >> 26;

		// perform bit swap
		//return bitSwap(state, bottomOfCube, currentLocation);
		return bitSwap(state, bottomOfCube, currentLocation);
	}
	
	public static int bitSwap(int number, int bit1, int bit2) {
	    int bit1pos = 1 << bit1;
	    int bit2pos = 1 << bit2;

	    if ((number & bit1pos) == 0 ^ (number & bit2pos) == 0) {
	      number ^= bit1pos | bit2pos;
	    }

	    return number;
	  }

	static int[][] rollMap = { { 3, 0, 1, 2, 4, 5 }, { 1, 2, 3, 0, 4, 5 },
			{ 4, 1, 5, 3, 2, 0 }, { 5, 1, 4, 3, 0, 2 } };

	public static int roll(Direction d, int currentState, int newLocation) {
		int[] roll = null;
		int cubeSides = (currentState & cubeSides_filter) >> 20;
		switch (d) {
			case UP:
				roll = rollMap[0];
				break;
			case DOWN:
				roll = rollMap[1];
				break;
			case LEFT:
				roll = rollMap[2];
				break;
			case RIGHT:
				roll = rollMap[3];
				break;
		}

		int newCubeSides = 0;
		for (int i = 0; i < roll.length; i++) {
			int match = cubeSides & (1 << i);
			if (match != 0) {
				newCubeSides += 1 << roll[i];
			}
		}

		// map the same for now
		int newState = (currentState & map_filter);
		// add new sides
		newState += (newCubeSides << 20);
		// add new location
		newState += (newLocation << 26);
		return newState;
	}

	public static boolean withinBounds(int x, int y) {
		if (x < 0) {
			return false;
		}else if (x >= map.length) {
			return false;
		}else if (y < 0) {
			return false;
		}else if (y >= map[0].length) {
			return false;
		}
		return true;
	}

}

class Pair {
	int x;
	int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

enum Direction {
	UP, DOWN, LEFT, RIGHT
}