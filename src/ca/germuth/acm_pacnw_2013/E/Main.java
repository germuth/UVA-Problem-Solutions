package ca.germuth.acm_pacnw_2013.E;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Enterprising Escape Problem E
 * 
 * Used BFS to search all states. Remembered the shortest distance to each space
 * during execution to avoid duplicates. Accepted with running time of 59.8 seconds (whew)
 * @author Germuth
 */
class Main {
	static HashMap<Character, Long> duration = new HashMap<Character, Long>();
	static long[][] graph;
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in); 

		int numCases = s.nextInt();
		for(int a = 0; a < numCases; a++){
			
			int klingonClass = s.nextInt();
			int w = s.nextInt();
			int h = s.nextInt();
			
			for(int i = 0; i < klingonClass; i++){
				duration.put(s.next().charAt(0), s.nextLong());
			}
			
			graph = new long[h][w];
			Point startPos = null;
			for(int i = 0; i < h; i++){
				String in = s.next();
				char[] line = in.toCharArray();
				for(int j = 0; j < line.length; j++){
					if(line[j] == 'E'){
						startPos = new Point(i, j);
					}else{
						graph[i][j] = duration.get(line[j]);						
					}
				}
			}
			
			System.out.println(shortestPath(startPos));
		}
		s.close();
	}
	
	public static long shortestPath(Point start){
		LinkedList<Point> openList = new LinkedList<Point>();
		openList.add(start);
		
		long[][] minSoFar = new long[graph.length][graph[0].length];
		for(int i = 0; i < minSoFar.length; i++){
			Arrays.fill(minSoFar[i], Long.MAX_VALUE);
		}
		minSoFar[start.x][start.y] = 0;
		long minimum = Long.MAX_VALUE;
		
		while(!openList.isEmpty()){
			Point curr = openList.pop();
			
			int x = curr.x;
			int y = curr.y;
			
			long distSoFar = minSoFar[x][y];
			//search to right
            if(withinBounds(x, y+1)){
            	int newX = x;
            	int newY = y+1;
            	if(distSoFar + graph[newX][newY] < minSoFar[newX][newY]){
            		minSoFar[newX][newY] = distSoFar + graph[newX][newY];
            		openList.add(new Point(newX, newY));
            	}
            }
            else if(distSoFar < minimum){
            	minimum = distSoFar;
            	continue;
            }
            //left
            if(withinBounds(x, y-1)){
            	int newX = x;
            	int newY = y-1;
            	if(distSoFar + graph[newX][newY] < minSoFar[newX][newY]){
            		minSoFar[newX][newY] = distSoFar + graph[newX][newY];
            		openList.add(new Point(newX, newY));
            	}
            }
            else if(distSoFar < minimum){
            	minimum = distSoFar;
            	continue;
            }
            //above
            if(withinBounds(x-1, y)){
            	int newX = x-1;
            	int newY = y;
            	if(distSoFar + graph[newX][newY] < minSoFar[newX][newY]){
            		minSoFar[newX][newY] = distSoFar + graph[newX][newY];
            		openList.add(new Point(newX, newY));
            	}
            }
            else if(distSoFar < minimum){
            	minimum = distSoFar;
            	continue;
            }
            //bottom
            if(withinBounds(x+1, y)){
            	int newX = x+1;
            	int newY = y;
            	if(distSoFar + graph[newX][newY] < minSoFar[newX][newY]){
            		minSoFar[newX][newY] = distSoFar + graph[newX][newY];
            		openList.add(new Point(newX, newY));
            	}
            }
            else if(distSoFar < minimum){
            	minimum = distSoFar;
            	continue;
            }
		}
		return minimum;
	}

	public static boolean withinBounds(int i, int j) {
		if (i < 0) {
			return false;
		}
		if (i > graph.length - 1) {
			return false;
		}
		if (j < 0) {
			return false;
		}
		if (j > graph[i].length - 1) {
			return false;
		}
		return true;
	}
}

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}


