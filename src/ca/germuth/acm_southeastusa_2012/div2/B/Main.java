package ca.germuth.acm_southeastusa_2012.div2.B;

import java.util.Scanner;

/**
 * Collision Detection 
 * ACM ICPC SouthEast USA 2012 Regionals 
 * Problem B Division 2
 * 
 * This problem is quite finicky. The first observation is that the time limit
 * is limited to 30 seconds from the end of the observations. This isn't very
 * long, so rather than testing if they will ever intersect, and then limiting
 * to 30 seconds etc, it is probably easier to just "simulate" the next 30
 * seconds after the last observation, and see where the car is at that point.
 * If the cars are ever 20 ft apart, you can report dangerous. The first
 * question is how often do you check the position of the cars. Since cars can
 * move at most 160 ft/sec (if both moving at 80 towards eachother) and you only
 * need accuracy of 20ft, it takes 1/8th of a second at 160 to travel 20 ft.
 * This is 0.125 seconds. So you can check for position of cars every 8th of a
 * second. If you are worried you could check much more often than this though
 * because it stil executes so quickly. Make sure you handle cars with 0
 * acceleration, and 0 startSpeed or any combination of the bunch. Also the cars
 * don't have to actually intersect, they could be running parallel to each
 * other within 20 ft. Finally, only consider times from the last observation to
 * 30 seconds in the future.
 * 
 * @author germuth
 */
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
			// Line car1Path = extendLine(car1_point1, car1_point2, 10000);
			// Line car2Path = extendLine(car2_point1, car2_point2, 10000);

			// THEY DON't HAVE TO INTERSECT
			// Point intersection = getIntersectionPoint(car1Path, car2Path);
			// if(intersection != null){
			// find distance between two points
			double car1_point1_to_point2 = dist(car1_point1, car1_point2);
			double car2_point1_to_point2 = dist(car2_point1, car2_point2);

			// find accelerations using two points
			// Vf^2 = Vi^2 + 2ad
			// a = (Vf^2-Vi^2)/2d
			double car1_accel = (car1_speed2 * car1_speed2 - car1_speed1
					* car1_speed1)
					/ (2.0 * car1_point1_to_point2);
			if (car1_point1_to_point2 == 0) {
				car1_accel = 0;
			}
			double car2_accel = (car2_speed2 * car2_speed2 - car2_speed1
					* car2_speed1)
					/ (2.0 * car2_point1_to_point2);
			if (car2_point1_to_point2 == 0) {
				car2_accel = 0;
			}

			if (car1_accel == 0 && car2_accel == 0 && car1_speed1 == 0
					&& car2_speed1 == 0) {
				// cars not moving
				dangerous = (dist(car1_point1, car2_point1) <= 19.0);
			}

			// simulate cars moving
			// going at 160 ft / sec (max speed of both cars driving at
			// eachother) can only move 40 ft / 0.25 seconds, or 20ft / 0.125
			// seconds
			// this means we much check atleast every 0.125 seconds
			// ill check every 0.01 to be safe
			double time = Math.max(car1_time2, car2_time2);
			for (double t = time; t <= time + 30.0; t += 0.125) {

				// find position of car 1
				double car1_dist = getDistanceAtTime(car1_speed2, car1_accel, t
						- car1_time2);
				// map this distance travelled to x y coordinates
				// line for car is already x long, where x is distance between
				// car1 point 1 and car1 point 2
				// we need to extend/shrink this line to be car1_dist long
				// 2nd point will then be where car1 located at car2 arrival
				Line car1_temp_line = extendLine(car1_point1, car1_point2,
						car1_dist);
				Point car1_pos = car1_temp_line.b;

				// find position of car 1
				double car2_dist = getDistanceAtTime(car2_speed2, car2_accel, t
						- car2_time2);
				Line car2_temp_line = extendLine(car2_point1, car2_point2,
						car2_dist);
				Point car2_pos = car2_temp_line.b;

				// check how close they are
				double distanceBetween = dist(car1_pos, car2_pos);
				if (distanceBetween < 19.0) {
					dangerous = true;
					break;
				}
			}
			// }

			if (dangerous) {
				System.out.println("Dangerous");
			} else {
				System.out.println("Safe");
			}
		}
		s.close();
	}

	public static double getDistanceAtTime(double startSpeed,
			double acceleration, double time) {
		if (time <= 0) {
			return 0;
		}

		// first find velocity at time
		// Vf = Vi + at
		double finalSpeed = startSpeed + acceleration * time;
		if (finalSpeed > 80.0) {
			// how far does it go until reaches top speed
			// Vf^2 = Vi^2 + 2ad
			// d = (Vf^2-Vi^2) / 2a
			// d = (80^2 - Vi^2) /2a
			double dist_while_accel = (80.0 * 80.0 - startSpeed * startSpeed)
					/ (2.0 * acceleration);
			// time it took to reach 80
			// Vf = Vi + at
			// t = (Vf-Vi)/a
			// t = (80-Vi)/a
			double time_to_accel = (80.0 - startSpeed) / acceleration;

			// V = d / t
			// d = V*t
			double dist_after_accel = (80.0 * (time - time_to_accel));
			return dist_while_accel + dist_after_accel;
		} else if (finalSpeed <= 0.0) {
			// how far does it go until reaches 0
			// Vf^2 = Vi^2 + 2ad
			// d = (Vf^2-Vi^2) / 2a
			// d = -Vi^2 /2a
			double dist_while_accel = -(startSpeed * startSpeed)
					/ (2.0 * acceleration);
			return dist_while_accel;
		} else if (acceleration == 0) {
			// V = d / t
			// d = V*t
			return startSpeed * time;
		} else {
			// find distance travelled
			return (finalSpeed * finalSpeed - startSpeed * startSpeed)
					/ (2.0 * acceleration);
		}
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