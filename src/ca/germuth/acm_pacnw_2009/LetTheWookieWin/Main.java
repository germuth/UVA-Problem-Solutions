package ca.germuth.acm_pacnw_2009.LetTheWookieWin;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Let The Wookie Win
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem E
 * 
 * Simple brute force since the crossword is limited to size of 5x5.
 * just go through each potential spot and make exclude ones that
 * (1) - you would win
 * (2) - he would not win
 * and you should get only one spot leftover. 
 * make sure you include all possible 8 diagonal series of 4
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		boolean last = false;
		while(true){
			if(last == true){
				break;
			}
			
			String in = s.nextLine();
			ArrayList<String> input = new ArrayList<String>();
			while(!in.isEmpty() && !in.equals("Finished")){
				input.add(in);
				in = s.nextLine();
			}
			if(in.equals("Finished")){
				last = true;
			}
			
			ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
			char[][] board = new char[5][5];
			for(int i = 0; i < input.size(); i++){
				String temp = input.get(i);
				board[i][0] = temp.charAt(0);
				board[i][1] = temp.charAt(2);
				board[i][2] = temp.charAt(4);
				board[i][3] = temp.charAt(6);
				board[i][4] = temp.charAt(8);
			}
			for(int r = 0; r < 5; r++){
				for(int c = 0; c < 5; c++){
					if(board[r][c] == '*'){
						possibleMoves.add(r*5 + c + 1);
					}
				}
			}
			
			ArrayList<Integer> trimmedMoves = new ArrayList<Integer>();
			//for each possible move, make sure i won't win by placing it there
			for(int i = 0; i < possibleMoves.size(); i++){
				int possibleMove = possibleMoves.get(i);
				if(!testForWin(board, possibleMove, 'O')){
					trimmedMoves.add(possibleMove);
				}
			}
			ArrayList<Integer> trimmedMoves2 = new ArrayList<Integer>();
			//for each possible move, make sure he will win
			for(int i = 0; i < trimmedMoves.size(); i++){
				int possibleMove = trimmedMoves.get(i);
				if(!testForWin(board, possibleMove, 'X')){
					trimmedMoves2.add(possibleMove);
				}
			}
			
			System.out.println(trimmedMoves2.get(0));
		}
		s.close();
	}
	public static boolean testForWin(char[][] board, int editPos, char edit){
		int row = (editPos - 1) / 5;
		int column = editPos - row*5 - 1;
		
		board[row][column] = edit;
		
		boolean willWin = false;
		//check for horizontal
		for(int r = 0; r < board.length; r++){
			int numO = 0;
			for(int c = 0; c < board[r].length; c++){
				if(board[r][c] == edit){
					numO++;
				}else{
					numO = 0;
				}
				if(numO >= 4){
					willWin = true;
					break;
				}
			}
		}
		//check vertical
		for(int c = 0; c < board[0].length; c++){
			int numO = 0;
			for(int r = 0; r < board.length; r++){
				if(board[r][c] == edit){
					numO++;
				}else{
					numO = 0;
				}
				if(numO >= 4){
					willWin = true;
					break;
				}
			}
		}
		//diagonal
		//possibilities are 
		//6-12-18-24
		//1-7-13-19-25
		//2-8-14-20
		
		//4-8-12-16
		//5-9-13-17-21
		//10-14-18-22
		int[][] diagonals = {{6,12,18,24}, {1,7,13,19,25}, {2,8,14,20}, {4,8,12,16}, {5,9,13,17,21}, {10,14,18,22}};
		for(int diags = 0; diags < diagonals.length; diags++){
			int numO = 0;
			int[] currDiag = diagonals[diags];
			for(int pos = 0; pos < currDiag.length; pos++){
				int position = currDiag[pos];
				int row2 = (position - 1) / 5;
				int column2 = position - row2*5 - 1;
				
				if(board[row2][column2] == edit){
					numO++;
				}else{
					numO = 0;
				}
				if(numO >= 4){
					willWin = true;
					break;
				}
			}
		}
		
		board[row][column] = '*';
		
		return willWin;
	}
}
