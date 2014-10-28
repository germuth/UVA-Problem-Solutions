package ca.germuth.acm_pacnw_2013.test;

package ca.germuth.uva_10245;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));

		while (true) {
			int n = Integer.parseInt(buf.readLine().trim());
			if (n == 0) {
				break;
			}

			Point[] points = new Point[n];
			
			for (int i = 0; i < n; i++) {
				StringTokenizer str = new StringTokenizer(buf.readLine());
				points[i] = new Point(Double.parseDouble(str.nextToken()),
						Double.parseDouble(str.nextToken()));
			}

			double min = findClosestPair(points);

			if (min > 9999)
				System.out.println("INFINITY");
			else {
				System.out.printf("%.4f", min);
				System.out.println("");
			}
		}
	}

	static double MAX_DISTANCE = Double.MAX_VALUE;
	// static Point[] PX;
	// static Point[] PY;
	static double shortestDist = MAX_DISTANCE;
	static Point point1;
	static Point point2;

	static double findClosestPair(Point[] points) {
		Point[] PX = points;
		Point[] PY = Arrays.copyOf(PX, PX.length);

		// sort by x
		Arrays.sort(PX, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.x, o2.x);
			}
		});

		// sort by y
		Arrays.sort(PY, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.y, o2.y);
			}
		});

		return Math.sqrt(closestPair(PX, PY));
	}

	static double closestPair(Point[] PX, Point[] PY) {
		// two points
		if (PX.length == 2) {
			return getDist(PX[0], PX[1]);
		}
		// three points
		if (PX.length == 3) {
			return Math.min(getDist(PX[0], PX[1]),
					Math.min(getDist(PX[0], PX[2]), getDist(PX[1], PX[2])));
		}
		// find middle point 
		int mid = (PX.length - 1) / 2;
		Point median = PX[mid];
		
		// four or more points, split them up
		// need to form Lx, Ly (left half) and Rx, Ry (right half
		Point[] LX = Arrays.copyOf(PX, mid);
		Point[] LY;
		
		Point[] RX = Arrays.copyOfRange(PX, mid, PX.length);
		Point[] RY;
		
		int l,r = 0;
		for(int i = 0; i < PY.length; i++){
			Point p = PY[i];
			if(p.x <= median.x){
				LY[l++] = p;
			}else{
				RY[r++] = p;
			}
		}
		

		// compute closest pair with both endpoints in left subarray or both in
		// right subarray
		double delta1 = closestPair(start, mid);
		double delta2 = closestPair(mid + 1, end);
		double delta = Math.min(delta1, delta2);
		double closestSplitPair = closestSplitPair(start, mid, mid + 1, end,
				delta);

		return Math.min(delta, closestSplitPair);
	}

	// computes closest pair with one point in left half and one point in right
	// half
	// only considers points which are at most delta x units away
	private static double closestSplitPair(int lStart, int lEnd, int rStart,
			int rEnd, double delta) {
		// last point of left array (lEnd) will be considered the middle by x
		// point
		Point middle = PX[lEnd];

		// nearby array contains all points where x in the interval [x-d, x+d],
		// sorted by y coordinate
		ArrayList<Point> nearby = new ArrayList<Point>();
		for (int i = 0; i < PY.length; i++) {
			// make sure point is within lStart-lEnd or rStart-rEnd

			Point p = PY[i];
			if (p.x >= middle.x - delta && p.x <= middle.x + delta) {
				nearby.add(p);
			}
		}

		double min = delta;
		// for each viable point
		// compare it with the next 8 points in the array
		// proven to solve closestSplitPair if shorter than delta by cool
		// geometry
		for (int i = 0; i < nearby.size() - 8; i++) {
			for (int j = i; j < i + 8; j++) {
				Point ip = nearby.get(i);
				Point jp = nearby.get(j);
				// compute distance between i and j
				double dist = getDist(ip, jp);
				if (dist < min) {
					min = dist;
					point1 = ip;
					point2 = jp;
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
