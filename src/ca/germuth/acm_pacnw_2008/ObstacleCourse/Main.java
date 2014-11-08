package ca.germuth.acm_pacnw_2008.ObstacleCourse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Obstacle Course
 * ACM ICPC Pacific Northwest Regionals 2008
 * Problem D
 * 
 * This problem was simply a shortest path problem. Can be solved
 * with BFS and saving the minimum cost to each state so far.
 * @author Aaron
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int trial = 1;
		while (true) {
			int n = s.nextInt();

			if (n == 0) {
				break;
			}

			int[][] graph = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					graph[i][j] = s.nextInt();
				}
			}

			int[][] min = new int[n][n];
			for (int[] row : min) {
				Arrays.fill(row, Integer.MAX_VALUE);
			}

			min[0][0] = graph[0][0];
			LinkedList<State> openList = new LinkedList<State>();
			openList.add(new State(0, 0));

			while (!openList.isEmpty()) {
				State curr = openList.pop();

				if (curr.col == n - 1 && curr.row == n - 1) {
					continue;
				}
				// try above
				int col = curr.col;
				int row = curr.row - 1;
				if (within(n, col, row)) {
					if (min[row][col] > min[curr.row][curr.col]
							+ graph[row][col]) {
						min[row][col] = min[curr.row][curr.col]
								+ graph[row][col];
						openList.add(new State(col, row));
					}
				}
				// below
				col = curr.col;
				row = curr.row + 1;
				if (within(n, col, row)) {
					if (min[row][col] > min[curr.row][curr.col]
							+ graph[row][col]) {
						min[row][col] = min[curr.row][curr.col]
								+ graph[row][col];
						openList.add(new State(col, row));
					}
				}
				// left
				col = curr.col - 1;
				row = curr.row;
				if (within(n, col, row)) {
					if (min[row][col] > min[curr.row][curr.col]
							+ graph[row][col]) {
						min[row][col] = min[curr.row][curr.col]
								+ graph[row][col];
						openList.add(new State(col, row));
					}
				}
				// right
				col = curr.col + 1;
				row = curr.row;
				if (within(n, col, row)) {
					if (min[row][col] > min[curr.row][curr.col]
							+ graph[row][col]) {
						min[row][col] = min[curr.row][curr.col]
								+ graph[row][col];
						openList.add(new State(col, row));
					}
				}
			}

			System.out.println("Problem " + trial + ": " + min[n - 1][n - 1]);
			trial++;
		}
		s.close();
	}

	public static boolean within(int n, int x, int y) {
		if (x < 0 || y < 0) {
			return false;
		}
		if (x >= n || y >= n) {
			return false;
		}
		return true;
	}
}

class State {
	int col;
	int row;

	public State(int xx, int yy) {
		col = xx;
		row = yy;
	}
}
