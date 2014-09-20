package ca.germuth.uva_10004;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Bicoloring
 * 9.6.1
 * PC/UVA ID: 110901/10004
 * 
 * Since there are only two possible colors, you can't guess the wrong color.
 * Therefore you just pick a color and start assigned all adjacent nodes with the 
 * opposite color, with standard breadth first search. if ever you find conflicting
 * nodes, it is not bicolorable
 * @author Germuth
 */
public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int nodes = s.nextInt();

		while (nodes != 0) {
			int edges = s.nextInt();
			Node[] allNodes = new Node[nodes];
			for (int i = 0; i < allNodes.length; i++) {
				allNodes[i] = new Node(i);
			}

			for (int i = 0; i < edges; i++) {
				int from = s.nextInt();
				int to = s.nextInt();

				allNodes[from].neighbours.add(to);
				allNodes[to].neighbours.add(from);
			}

			if (color(allNodes)) {
				System.out.println("BICOLORABLE.");
			} else {
				System.out.println("NOT BICOLORABLE.");
			}

			nodes = s.nextInt();
		}
	}

	public static boolean color(Node[] allNodes) {
		Color[] colors = new Color[allNodes.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = Color.NONE;
		}

		Node start = allNodes[0];
		LinkedList<Node> openList = new LinkedList<Node>();
		openList.add(start);

		colors[0] = Color.WHITE;

		while (!openList.isEmpty()) {
			Node curr = openList.pop();

			for (int neighbour : curr.neighbours) {
				// node not yet colored
				if (colors[neighbour] == Color.NONE) {
					// color the opposite, and add
					colors[neighbour] = opposite(colors[curr.value]);

					openList.add(allNodes[neighbour]);

				}
				// bi coloring failed
				else if (colors[neighbour] == colors[curr.value]) {
					return false;
				} else {
					// color is okay
				}
			}
		}
		return true;
	}

	public static Color opposite(Color c) {
		if (c.equals(Color.BLACK)) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}
}

class Node {
	int value;
	ArrayList<Integer> neighbours;

	public Node(int v) {
		value = v;
		neighbours = new ArrayList<Integer>();
	}
}

enum Color {
	WHITE, BLACK, NONE
}
