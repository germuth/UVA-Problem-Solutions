package ca.germuth.acm_southeastusa_2013.div2.A;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//TODO add explanation
//basically, extend each line segment to be sure it could intersect circle,
//then for each, line segment that intersects circle, add one section,
//for each line segment which intersects another line, add one section. 
//HOWEVER care must be taken that the point at which the line segments intersect
//is within the circle, therefore after determing the line-circle intersect,
//i shrink the lines to only have bounds within the circle
public class Main {
	static Point intersection1;
	static Point intersection2;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			int cr = s.nextInt();
			if (cr == 0) {
				break;
			}
			int cx = s.nextInt();
			int cy = s.nextInt();
			int n = s.nextInt();
			ArrayList<Line> lines = new ArrayList<Line>();
			for (int i = 0; i < n; i++) {
				lines.add(new Line(new Point(s.nextInt(), s.nextInt()),
						new Point(s.nextInt(), s.nextInt())));
			}

			// enlargen any line segment so it is effectily the entire line
			// input space is 1000x1000 but circle could be at corner with
			// radius 1000
			// so about 2000x2000
			// extend each linesegment so it is ~4000 long
			for (int i = 0; i < lines.size(); i++) {
				Line l = lines.get(i);

				// extend by 2000 in each direction
				l = extendLine(l.A, l.B, 4000);
				l = extendLine(l.B, l.A, 6000);

				lines.remove(i);
				lines.add(i, l);
			}
			// now all line segments will, if they intersect the circle, be
			// guaranteed to cross both lines of the circle

			boolean[] intersect = new boolean[lines.size()];
			Arrays.fill(intersect, false);

			// take out any line segments which do not intersect with circle
			for (int i = 0; i < lines.size(); i++) {
				Line l = lines.get(i);
				FindLineCircleIntersections(cx, cy, cr, l.A, l.B);
				if (intersection1 != null && intersection2 != null) {
					// it intersects
					intersect[i] = true;
					l.A = intersection1;
					l.B = intersection2;
				} else {
					intersect[i] = false;
				}
				intersection1 = null;
				intersection2 = null;
			}

			int circleParts = 1;
			// each line intersection adds 1, and then adds another 1 for each
			// other line segment it intersects
			for (int i = 0; i < lines.size(); i++) {

				if (!intersect[i]) {
					continue;
				}

				circleParts++;
				// go over each line already added
				for (int j = 0; j < i; j++) {

					if (!intersect[j]) {
						continue;
					}

					Line l1 = lines.get(i);
					Line l2 = lines.get(j);
					Line2D line1 = new Line2D.Double(l1.A.X, l1.A.Y, l1.B.X,
							l1.B.Y);
					Line2D line2 = new Line2D.Double(l2.A.X, l2.A.Y, l2.B.X,
							l2.B.Y);
					if (line2.intersectsLine(line1)) {
						circleParts++;
					}
				}
			}

			System.out.println(circleParts);
		}
		s.close();
	}

	private static Line extendLine(Point a, Point b, double length) {
		double lengthAB = Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2);
		lengthAB = Math.sqrt(lengthAB);

		double x = b.X + (b.X - a.X) / lengthAB * length;
		double y = b.Y + (b.Y - a.Y) / lengthAB * length;

		return new Line(a, new Point(x, y));
	}

	// Find the points of intersection.
	private static int FindLineCircleIntersections(int cx, int cy, int radius,
			Point point1, Point point2) {
		double dx, dy, A, B, C, det, t;

		dx = point2.X - point1.X;
		dy = point2.Y - point1.Y;

		A = dx * dx + dy * dy;
		B = 2 * (dx * (point1.X - cx) + dy * (point1.Y - cy));
		C = (point1.X - cx) * (point1.X - cx) + (point1.Y - cy)
				* (point1.Y - cy) - radius * radius;

		det = B * B - 4 * A * C;
		if ((A <= 0.0000001) || (det < 0)) {
			// No real solutions.
			intersection1 = null;
			intersection2 = null;
			return 0;
		} else if (det == 0) {
			// One solution.
			t = -B / (2 * A);
			intersection1 = new Point(point1.X + t * dx, point1.Y + t * dy);
			intersection2 = null;
			return 1;
		} else {
			// Two solutions.
			t = (float) ((-B + Math.sqrt(det)) / (2 * A));
			intersection1 = new Point(point1.X + t * dx, point1.Y + t * dy);
			t = (float) ((-B - Math.sqrt(det)) / (2 * A));
			intersection2 = new Point(point1.X + t * dx, point1.Y + t * dy);
			return 2;
		}
	}
}

class Point {
	double X;
	double Y;

	public Point(double x, double y) {
		X = x;
		Y = y;
	}
}

class Line {
	Point A;
	Point B;

	public Line(Point a, Point b) {
		A = a;
		B = b;
	}
}
