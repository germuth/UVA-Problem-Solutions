package ca.germuth.uva_10245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * The Closest Pair Problem
 * 10245
 * 
 * N^2 algorithm too slow. One N*log N implementation is the divide and conquer approach. 
 * Split points in half by x coordinate, and solve for left and right half. Then search 
 * if the shortest distance crosses the left and right half. This search can be made
 * by finding all points within d x-distance of the middle line, and computing the distance.
 * Because of a neat geometric proof, it suffices to only check each point and the next 8 points 
 * in the delta range.
 * 
 * Input can be altered to also be accepted here:
 * http://www.spoj.com/problems/CPP/
 */
class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));

		while (true) {
			int n = Integer.parseInt(buf.readLine().trim());
			if (n == 0) {
				break;
			}

			ArrayList<Point> points = new ArrayList<Point>(n);
			
			for (int i = 0; i < n; i++) {
				StringTokenizer str = new StringTokenizer(buf.readLine());
				points.add( new Point(Double.parseDouble(str.nextToken()),
						Double.parseDouble(str.nextToken())));
			}

			//IF UVA Judge
			if(points.size() == 1){
				System.out.println("INFINITY");
				continue;
			}
			double min = findClosestPair(points);

			if (min > 9999)
				System.out.println("INFINITY");
			else {
				System.out.printf("%.4f", min);
				System.out.println("");
			}
			
			//If SPOJ Judge
//			double min = findClosestPair(points);
//			System.out.printf("%.6f", min);
//			System.out.println("");
		}
	}

	static double findClosestPair(ArrayList<Point> points) {
		ArrayList<Point> PX = points;
		ArrayList<Point> PY = new ArrayList<Point>(points);

		// sort by x
		Collections.sort(PX, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.x, o2.x);
			}
		});

		// sort by y
		Collections.sort(PY, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.y, o2.y);
			}
		});

		return Math.sqrt(closestPair(PX, PY));
	}

	static double closestPair(ArrayList<Point> PX, ArrayList<Point> PY) {
		// two points
		if (PX.size() == 2) {
			return getDist(PX.get(0), PX.get(1));
		}
		// three points
		if (PX.size() == 3) {
			return Math.min(getDist(PX.get(0), PX.get(1)),
					Math.min(getDist(PX.get(0), PX.get(2)),
							getDist(PX.get(1), PX.get(2))));
		}
		// find middle point 
		int mid = (PX.size() - 1) / 2;
		Point median = PX.get(mid);
		
		// four or more points, split them up
		// need to form Lx, Ly (left half) and Rx, Ry (right half)
		ArrayList<Point> LX = new ArrayList<Point>();
		ArrayList<Point> LY = new ArrayList<Point>();
		
		ArrayList<Point> RX = new ArrayList<Point>();
		ArrayList<Point> RY = new ArrayList<Point>();
		
		//populate X list
		for(int i = 0; i < PX.size(); i++){
			if(i > mid){
				RX.add(PX.get(i));
			}else{
				LX.add(PX.get(i));
			}
		}
		
		int l,r = 0;
		for(int i = 0; i < PY.size(); i++){
			Point p = PY.get(i);
			if(p.x <= median.x){
				LY.add(p);
			}else{
				RY.add(p);
			}
		}
		
		// compute closest pair with both endpoints in left subarray or both in
		// right subarray
		double delta1 = closestPair(LX, LY);
		double delta2 = closestPair(RX, RY);
		double delta = Math.min(delta1, delta2);
		double closestSplitPair = closestSplitPair(PX, PY, delta);

		return Math.min(delta, closestSplitPair);
	}

	// computes closest pair with one point in left half and one point in right
	// half
	// only considers points which are at most delta x units away
	private static double closestSplitPair(ArrayList<Point> PX, ArrayList<Point> PY, double delta) {
		// last point of left array (lEnd) will be considered the middle by x
		// point
		Point middle = PX.get((PX.size() - 1)/ 2);

		// if we want to compare this directly to x-coordinates as below, we must square root it
		double deltaRoot = Math.sqrt(delta);
		
		// nearby array contains all points where x in the interval [x-d, x+d],
		// sorted by y coordinate
		ArrayList<Point> nearby = new ArrayList<Point>();
		for (int i = 0; i < PY.size(); i++) {
			// make sure point is within lStart-lEnd or rStart-rEnd

			Point p = PY.get(i);
			if (p.x >= middle.x - deltaRoot && p.x <= middle.x + deltaRoot) {
				nearby.add(p);
			}
		}

		double min = delta;
		// for each viable point
		// compare it with the next 8 points in the array
		// proven to solve closestSplitPair if shorter than delta by cool
		// geometry
		for (int i = 0; i < nearby.size(); i++) {
			for (int j = i + 1; j < i + 8; j++) {
				if(j >= nearby.size()){
					break;
				}
				Point ip = nearby.get(i);
				Point jp = nearby.get(j);
				// compute distance between i and j
				double dist = getDist(ip, jp);
				if (dist < min) {
					min = dist;
//					point1 = ip;
//					point2 = jp;
				}
			}
		}
		return min;
	}

	// computes distance^2 between p1 and p2
	// and assigns it as the best, if it is the best so far
	private static double getDist(Point p1, Point p2) {
		double dis = Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2);
		return dis;
	}
}

class Point {
	double x, y;

	public Point(double xx, double yy) {
		x = xx;
		y = yy;
	}
}
