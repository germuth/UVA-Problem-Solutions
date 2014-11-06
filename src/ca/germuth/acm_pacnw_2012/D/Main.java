package ca.germuth.acm_pacnw_2012.D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;
/**
 * Partition
 * ACM ICPC Pacific Northwest 2012 Regionals
 * Problem D
 * 
 * NOT YET SOLVED
 * need to go through and make sure the sorting order and removal from left and right make sense
 * becaue i got wrong answer
 * 
 * Any line dividing the area in half, must cross through the center.
 * This problem can be solved by sorting each point by its polar angle to the center point. From
 * there you must search for n/2 adjacent points in the sorted list, which can be split off 
 * from the others by a straight line through the center. However, it could "loop" around
 * the array. To avoid this, you can start by partitioning the points into two equal areas
 * such as the left and right half. You can then slowly add points one by one from the smaller
 * of these into the other. Care must be taken when selecting which point to remove and 
 * add to the other set. 
 * CONTINUIE HERE WHEN YOU SOLVE IT 
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			StringTokenizer st = new StringTokenizer(buf.readLine());
			int n = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			
			if(n == 0){
				break;
			}
			
			ArrayList<Point> point = new ArrayList<Point>();
			for(int i = 0; i< n; i++){
				st = new StringTokenizer(buf.readLine());
				point.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
			}
			
			final Point mid = new Point(w/2, h/2);
			
			//sort points by angle
			Collections.sort(point, new Comparator<Point>() {
				@Override
				public int compare(Point b, Point c) {
					double dx1 = b.x - mid.x;
					double dy1 = b.y - mid.y;
					double dx2 = c.x - mid.x;
					double dy2 = c.y - mid.y;

					if (dy1 >= 0 && dy2 < 0)
						return -1; // q1 above; q2 below
					else if (dy2 >= 0 && dy1 < 0)
						return +1; // q1 below; q2 above
					else if (dy1 == 0 && dy2 == 0) { // 3-collinear and
														// horizontal
						if (dx1 >= 0 && dx2 < 0)
							return -1;
						else if (dx2 >= 0 && dx1 < 0)
							return +1;
						else
							return 0;
					} else {
						double area = dx1 * dy2 - dy1 * dx2;
						if (area < 0)
							return 1;
						else if (area > 0)
							return -1;
						else
							return 0;
					}
				}
			});
			
			//split into two sets that we know can be separated by straight line
			//left of mid and right of mid
			LinkedList<Point> left = new LinkedList<Point>();
			LinkedList<Point> right = new LinkedList<Point>();
			for(int i = 0; i < n; i++){
				if(point.get(i).x <= mid.x){
					left.add(point.get(i));
				}else{
					right.add(point.get(i));
				}
			}
			
			//now we move points one at a time from right to left
			while(left.size() < right.size()){
				//we must take the leftmost point from right, and add to left
				//the leftmost point will have 
				//comparator returns 1 if p1 -> p2 is left turn
				//which means p1 is greater than p2
				//which means the arrays are ordered from right to left
				//meaning we want to remove the last element from right
				left.add(right.removeLast());
			}
			//now do same thing other way if left was bigger than right
			while(right.size() < left.size()){
				//want rightmost point from left, which will be first
				right.add(left.removeFirst());
			}
			
			//ALWAYS USE ITERATORS (implicitly) WHEN ITERATING OVER LINKED LIST
			for(Point p: left){
				System.out.println((int)p.x + " " + (int)p.y);
			}
		}
//		s.close();
	}

}class Point{
	double x;
	double y;
	public Point(double xx, double yy){
		x = xx;
		y = yy;
	}
}
