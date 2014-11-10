package ca.germuth.acm_pacnw_2009.LaserTurrentMaintainence;

import java.util.Arrays;
import java.util.Scanner;
/**
 * Laser Turret Maintenance
 * ACM ICPC Pacific Northwest Regionals 2009
 * Problem F
 * 
 * Another brute force solution. The matrix size is limited to 20x20 which is quite small.
 * The only hard part about this solution is the output parameters. Becareful with numbers
 * with more than 3 digits, as there should no longer be two spaces in front of them
 * but only one. For ex. output should be 
 *   1  3 12  3  1
 *   2  2  2  3  5
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		while(true){
			int n = s.nextInt();
			
			if(n == 0){
				break;
			}
			
			int[][] board = new int[n][n];
			for(int[] row: board){
				Arrays.fill(row, 0);
			}
			for(int i = 0; i < n; i++){
				board[i][s.nextInt()] = 1;
			}
			
			printMatrix(board);
			int[][] spin = spinMatrix(board);
			printMatrix(spin);//90
			spin = spinMatrix(spin);
			printMatrix(spin);//180
			spin = spinMatrix(spin);
			printMatrix(spin);//270
			//vert
			int[][] vert = new int[n][n];
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					vert[i][(n-1)-j] = board[i][j];
				}
			}
			printMatrix(vert);
			//anti-diag
			int[][] adiag = new int[n][n];
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					adiag[(n-1)-j][(n-1)-i] = board[i][j];
				}
			}
			printMatrix(adiag);
			//hor
			int[][] hor = new int[n][n];
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					hor[(n-1)-i][j] = board[i][j];
				}
			}
			printMatrix(hor);
			//diag
			int[][] diag = new int[n][n];
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					diag[j][i] = board[i][j];
				}
			}
			printMatrix(diag);
			System.out.println("");
		}
		s.close();
	}
	
	
	
	public static int[][] spinMatrix(int[][] board){
		int n = board.length;
		int[][] spun = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				spun[j][(n-1)-i] = board[i][j];
			}
		}
		return spun;
	}
	public static void printMatrix(int[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				if(board[i][j] != 0){
					System.out.printf("%3d", j);
//					output += "  " + j; 
				}
			}
		}
		System.out.println("");
	}
}
