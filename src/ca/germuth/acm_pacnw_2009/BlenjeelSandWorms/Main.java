package ca.germuth.acm_pacnw_2009.BlenjeelSandWorms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Blenjeel Sand Worms and Color Wriggles
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem B
 * 
 * This problem can be solved with slightly complex BFS. You must keep track
 * of the entire worms body, and try all movements from both the head and tail.
 * The worm can only move to squares which 
 * (1) - are contained within the input board
 * (2) - don't contain two of the same number
 * (3) - don't already contain one other part of the worm.
 * One trick with (3) is that the worm can move to the square where its tail
 * is located since, when moving, the tail will no longer be there. Or vice
 * versa with head-tail. For ex.
 * 
 * O-O                  O O
 * 	 |   can move to    | | 
 * O-O                  O-O
 * 
 * @author Aaron
 */
class Main {
	static int ROWS;
	static int COLS;
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		while(true){
			ArrayList<String> input = new ArrayList<String>();
			String in = s.nextLine().trim();
			
			if(in.equals("end")){
				break;
			}
			while(!in.isEmpty()){
				input.add(in);
				in = s.nextLine().trim();
			}
			
			ROWS = input.size();
			COLS = input.get(0).length();
			
			char[][] board = new char[input.size()][input.get(0).length()];
			for(int i = 0; i < input.size(); i++){
				board[i] = input.get(i).toCharArray();
			}
			
			LinkedList<State> openList = new LinkedList<State>();
			ArrayList<Point> startingState = new ArrayList<Point>();
			for(int i = 0; i < board.length; i++){
				startingState.add(new Point(0, i));
			}
			openList.add(new State(startingState, 0));
			
			HashMap<String, Integer> previousStates = new HashMap<String, Integer>();
			//can put in reverse list as well
			previousStates.put(startingState.toString(), 0);
			
			int minWriggles = Integer.MAX_VALUE;
			while(!openList.isEmpty()){
				State curr = openList.pop();
				ArrayList<Point> body = curr.sections;
				
				boolean endState = true;
				for(int i = 0; i < body.size(); i++){
					if(body.get(i).col != COLS - 1){
						endState = false;
						break;
					}
				}
				
				if(endState){
					if(curr.wriggles < minWriggles){
						minWriggles = curr.wriggles;
						break;
					}
					continue;
				}
				
				//try to wriggle with each end of worm
				for(int end = 0; end < 2; end++){
					//try once to move head, once to move tail
					
					Point head = null;
					switch(end){
					case 0: head = body.get(0); break;
					case 1: head= body.get(body.size() - 1); break;
					}
					
					//try to move up, down, left, right with top 
					for(int dir = 0; dir < 4; dir++){
						Point next = null;
						switch(dir){
						//up
						case 0: next = new Point(head.col, head.row-1); break;
						//down
						case 1: next = new Point(head.col, head.row+1); break;
						//left
						case 2: next = new Point(head.col-1, head.row); break;
						//right
						case 3: next = new Point(head.col+1, head.row); break;
						}
						
						if(within(next) && !overlap(body, next, end)){
							//move up
							//head moves up one
							//all others move to where i-1 is
							ArrayList<Point> newLocation = new ArrayList<Point>();
							newLocation.add(next);
							
							//if we are moving head
							if(end == 0){
								//skip the last one
								for(int i = 0; i < body.size() - 1; i++){
									Point p = body.get(i);
									newLocation.add(new Point(p.col, p.row));
								}
							}//we are moving tail
							else{
								//skip the first one
								for(int i = body.size() - 1; i >= 1; i--){
									Point p = body.get(i);
									newLocation.add(new Point(p.col, p.row));
								}
							}
							
							boolean validLocation = true;
							HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
							//test to make sure worm doesn't have same color
							for(int i = 0; i < newLocation.size(); i++){
								Point p = newLocation.get(i);
								if(counts.containsKey(board[p.row][p.col])){
									validLocation = false;
									break;
								}
								counts.put(board[p.row][p.col], 1);
							}
							
							if(validLocation){
								if(!previousStates.containsKey(newLocation.toString()) || 
										(curr.wriggles + 1 < previousStates.get(newLocation.toString()))){
									previousStates.put(newLocation.toString(), curr.wriggles + 1);
									openList.add(new State(newLocation, curr.wriggles + 1));
								}
							}
						}
					}
				}
			}
			
			if(minWriggles == Integer.MAX_VALUE){
				System.out.println(-1);
			}else{
				System.out.println(minWriggles);				
			}
		}
		s.close();
	}
	
	public static boolean overlap(ArrayList<Point> body, Point newHead, int end){
		//don't move to a position the worm is already in
		if(!body.contains(newHead)){
			return false;
		}else{
			//we are moving the head, allow us to replace tail
			if(end == 0){
				if(newHead.equals(body.get(body.size() - 1))){
					return false;
				}
			}
			//we are moving tail, allow us to replace head
			else{
				if(newHead.equals(body.get(0))){
					return false;
				}
			}
		}
		return true;
		
	}
	public static boolean within(Point p){
		int col = p.col;
		int row = p.row;
		if(col < 0 || row < 0){
			return false;
		}
		if(row >= ROWS || col >= COLS){
			return false;
		}
		return true;
	}
}class State{
	ArrayList<Point> sections = new ArrayList<Point>();
	int wriggles;
	public State(ArrayList<Point> sec, int wrig){
		sections = sec;
		wriggles = wrig;
	}
}class Point{
	int col;
	int row;
	public Point(int c, int r){
		col = c;
		row = r;
	}
	
	@Override
	public String toString() {
		return row + " " + col;
	}

	@Override
	public boolean equals(Object arg0) {
		Point an = (Point)arg0;
		return (col == an.col && row == an.row);
	}
}
