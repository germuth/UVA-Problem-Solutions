package ca.germuth.acm_southeastusa_2012.div2.B;

/**
 * Collision Detection
 * ACM ICPC SouthEast USA 2012 Regionals
 * Division 2 Problem B
 * 
 * NOT YET SOLVED
 * Solving for exact solution is bad, use simulation.
 * NOT YET SOLVED
 * @author germuth
 */
import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		while (true) {
			double car1_time1 = s.nextDouble();
			Point car1_point1 = new Point(s.nextDouble(), s.nextDouble());
			double car1_speed1 = s.nextDouble();

			if (car1_speed1 == -1) {
				break;
			}

			double car1_time2 = s.nextDouble();
			Point car1_point2 = new Point(s.nextDouble(), s.nextDouble());
			double car1_speed2 = s.nextDouble();

			double car2_time1 = s.nextDouble();
			Point car2_point1 = new Point(s.nextDouble(), s.nextDouble());
			double car2_speed1 = s.nextDouble();
			double car2_time2 = s.nextDouble();
			Point car2_point2 = new Point(s.nextDouble(), s.nextDouble());
			double car2_speed2 = s.nextDouble();

			boolean dangerous = false;

			// create line segment for cars two points
			// and extend line segment atleast length of entire grid (5000x5000)
			// = 10000 distance
			Line car1Path = extendLine(car1_point1, car1_point2, 10000);
			Line car2Path = extendLine(car2_point1, car2_point2, 10000);

			// test for intersection
			Point intersection = getIntersectionPoint(car1Path, car2Path);
			if (intersection != null) {
				// find distance between two points
				double car1_point1_to_point2 = dist(car1_point1, car1_point2);
				double car2_point1_to_point2 = dist(car2_point1, car2_point2);
				// find accelerations using two points
				// Vf^2 = Vi^2 + 2ad
				// a = (Vf^2-Vi^2)/2d
				double car1_accel = (car1_speed2 * car1_speed2 - car1_speed1
						* car1_speed1)
						/ (2.0 * car1_point1_to_point2);
				double car2_accel = (car2_speed2 * car2_speed2 - car2_speed1
						* car2_speed1)
						/ (2.0 * car2_point1_to_point2);

				// calculate distance from point 1 to intersection
				double car1_dist = dist(car1_point1, intersection);
				double car2_dist = dist(car2_point1, intersection);

				// make sure both cars will reach the intersection (if accel
				// negative, they might stop)
				if (willReach(car1_dist - 20, car1_accel, car1_speed1)
						&& willReach(car2_dist - 20, car2_accel, car2_speed1)) {
					// okay now we are sure both cars will make it to the
					// intersection at somepoint
					// but will they make it there (or atleast within 20ft) in
					// 30 seconds
					if (willReachIn(car1_dist - 20, car1_accel, car1_speed1, 30)
							&& willReachIn(car2_dist - 20, car2_accel,
									car2_speed1, 30)) {

						// calculate at what time they will make it there
						// (arrival Time)
						double car1_arrival = arrivalTime(car1_speed1,
								car1_accel, car1_dist);
						car1_arrival += car1_time1;
						double car2_arrival = arrivalTime(car2_speed1,
								car2_accel, car2_dist);
						car2_arrival += car2_time1;

						// how far did other car make it when car was at
						// intersection
						// d = at^2 /2 + Vi*t
						double car2_dist_at_car1_arrival = car2_accel
								* car1_arrival * car1_arrival / 2.0
								+ car2_speed1 * car1_arrival;
						double car1_dist_at_car2_arrival = car1_accel
								* car2_arrival * car2_arrival / 2.0
								+ car1_speed1 * car2_arrival;

						// map this distance travelled to coordinate of other
						// car
						// line for car is aleady x long, where x is distance
						// between car1 point 1 and car1 point 2
						// we need to extend/shrink this line to be
						// car1_dist_at_car2_arrival long
						// 2nd point will then be where car1 located at car2
						// arrival
						Line car1_temp_line = extendLine(car1_point1,
								car1_point2, car1_dist_at_car2_arrival
										- car1_point1_to_point2);
						Point car1_pos_when_car2_at_intersection = car1_temp_line.b;

						Line car2_temp_line = extendLine(car2_point1,
								car2_point2, car2_dist_at_car1_arrival
										- car2_point1_to_point2);
						Point car2_pos_when_car1_at_intersection = car2_temp_line.b;

						if (dist(car1_pos_when_car2_at_intersection,
								intersection) <= 18.0) {
							dangerous = true;
						} else if (dist(car2_pos_when_car1_at_intersection,
								intersection) <= 18.0) {
							dangerous = true;
						}
					}
				}
			}

			if (dangerous) {
				System.out.println("Dangerous");
			} else {
				System.out.println("Safe");
			}
		}
		s.close();
	}

	public static double arrivalTime(double startSpeed, double acceleration,
			double distance) {
		// must first calculate velocity at intersection (Vf)
		// Vf^2 = Vi^2 + 2ad
		double finalSpeed = startSpeed * startSpeed + 2.0 * acceleration
				* distance;
		if (finalSpeed < 0) {
			finalSpeed = 0;
		}
		finalSpeed = Math.sqrt(finalSpeed);

		double arrivalTime = 0;
		// if either final speed is greater than 80 than they won't actually
		// continue to accelerate past 80
		if (finalSpeed > 80.0) {
			// how far does it go until reaches top speed
			// Vf^2 = Vi^2 + 2ad
			// d = (Vf^2-Vi^2) / 2a
			// d = (80^2 - Vi^2) /2a
			double dist_after_accel = (80.0 * 80.0 - startSpeed * startSpeed)
					/ (2.0 * acceleration);
			double distanceRemaining = distance - dist_after_accel;

			// time it took to reach 80
			// Vf = Vi + at
			// t = (Vf-Vi)/a
			// t = (80-Vi)/a
			double time_to_accel = (80.0 - startSpeed) / acceleration;

			// remaining time it takes to get to intersection
			// now at constant velocity
			// V = d / t
			// t = d / V
			arrivalTime = distanceRemaining / 80.0;
			arrivalTime += time_to_accel;
			return arrivalTime;
		} else {
			// we don't reach 80 until after intersection
			// so acceleration is constant entire time
			// Vf = Vi + at
			// t = (Vf-Vi)/a
			// t = (80-Vi)/a
			double time_to_accel = (finalSpeed - startSpeed) / acceleration;
			return time_to_accel;
		}
	}

	public static boolean willReachIn(double totalDistance,
			double acceleration, double startSpeed, double timeLimit) {
		// calculate how far we will make it in the time limit
		// and whether that distance is greater or equal to total distance
		// calculate final velocity first
		// Vf = Vi + at
		double finalSpeed = startSpeed + acceleration * timeLimit;
		if (finalSpeed < 0) {
			finalSpeed = 0;
		}
		// calculate how far we make it
		// Vf^2 = Vi^2 + 2ad
		// d = (Vf^2-Vi^2) / 2a
		double distanceReached = (finalSpeed * finalSpeed - startSpeed
				* startSpeed)
				/ (2.0 * acceleration);
		if (distanceReached >= totalDistance) {
			return true;
		}
		return false;

	}

	public static boolean willReach(double totalDistance, double acceleration,
			double startSpeed) {
		if (acceleration < 0) {
			// set Vf to 0, and see how long they will make
			// Vf^2 = Vi^2 + 2ad
			// d = (Vf^2-Vi^2)/2a
			// d = -Vi^2/2a
			double distanceBeforeStop = -(startSpeed * startSpeed)
					/ (2.0 * acceleration);

			if (distanceBeforeStop >= totalDistance) {
				return true;
			}
			return false;
		}
		return true;
	}

	public static Point getIntersectionPoint(Line line, Line line2) {
		double px = line.a.x, py = line.a.y, rx = line.b.x - px, ry = line.b.y
				- py;
		double qx = line2.a.x, qy = line2.a.y, sx = line2.b.x - qx, sy = line2.b.y
				- qy;

		double det = sx * ry - sy * rx;
		if (det == 0) {
			return null;
		} else {
			double z = (sx * (qy - py) + sy * (px - qx)) / det;
			if (z == 0 || z == 1)
				return null; // intersection at end point!
			return new Point((px + z * rx), (py + z * ry));
		}
	}

	private static double dist(Point a, Point b) {
		double x1 = a.x;
		double x2 = b.x;
		double y1 = a.y;
		double y2 = b.y;
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	private static Line extendLine(Point a, Point b, double length) {
		double lengthAB = Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
		lengthAB = Math.sqrt(lengthAB);

		double x = b.x + (b.x - a.x) / lengthAB * length;
		double y = b.y + (b.y - a.y) / lengthAB * length;

		return new Line(a, new Point(x, y));
	}

}

class Point {
	double x;
	double y;

	public Point(double xx, double yy) {
		x = xx;
		y = yy;
	}
}

class Line {
	Point a;
	Point b;

	public Line(Point aa, Point bb) {
		a = aa;
		b = bb;
	}
}
