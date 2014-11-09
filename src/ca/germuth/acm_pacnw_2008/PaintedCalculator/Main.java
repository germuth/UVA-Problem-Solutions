package ca.germuth.acm_pacnw_2008.PaintedCalculator;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Painted Calculator
 * ACM ICPC Pacific North West Regionals 2008
 * Problem K
 * 
 * The calculator can only have a maximum of 2000 values (from -999 to 999). This is small 
 * enough that we can brute force, and go over every possible value on the calculator
 * and calculate the amperage. If the amperage matches one of our input values,
 * keep it in our list. Then with a potential list of x, y, and z values, 
 * we can iterate over each possible triplet and calculate whether
 * x operator y = z
 * @author Aaron
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int trial = 1;
		while (true) {
			int x = s.nextInt();

			if (x == 0){
				break;
			}
			int y = s.nextInt();
			int z = s.nextInt();

			// possible values for x, y, z
			ArrayList<Integer> xVals = new ArrayList<Integer>();
			ArrayList<Integer> yVals = new ArrayList<Integer>();
			ArrayList<Integer> zVals = new ArrayList<Integer>();
			for (int number = -999; number <= 999; number++) {
				int test = number;
				int mA = 0;
				if (test < 0) {
					mA += 5;
					test *= -1;
				}
				// hundred value
				int hundred = test / 100;
				if(hundred != 0){
					mA += digitToMA(hundred);
					test -= hundred * 100;					
				}
				// tens value
				int ten = test / 10;
				if(ten != 0 || hundred != 0){
					mA += digitToMA(ten);
					test -= ten * 10;					
				}
				// ones value
				mA += digitToMA(test);

				if (mA == x) {
					xVals.add(number);
				}
				if (mA == y) {
					yVals.add(number);
				}
				if (mA == z) {
					zVals.add(number);
				}
			}

			int matches = 0;
			// possible values for y
			// possible values for z

			for (int xInd = 0; xInd < xVals.size(); xInd++) {
				for (int yInd = 0; yInd < yVals.size(); yInd++) {
					for (int zInd = 0; zInd < zVals.size(); zInd++) {

						int xNumber = xVals.get(xInd);
						int yNumber = yVals.get(yInd);
						int zNumber = zVals.get(zInd);

						int addition = xNumber + yNumber;
						if (addition == zNumber) {
							matches++;
						}
						if (xNumber - yNumber == zNumber) {
							matches++;
						}
						int multiplication = xNumber*yNumber;
						if (multiplication == zNumber) {
							matches++;
						}
						if(yNumber != 0){
							if (xNumber / yNumber == zNumber) {
								matches++;
							}							
						}
					}
				}
			}

			if (matches == 0) {
				System.out.println("Case " + trial + ": " + "No solutions.");
			} else if (matches == 1) {
				System.out.println("Case " + trial + ": " + matches
						+ " solution.");
			} else {
				System.out.println("Case " + trial + ": " + matches
						+ " solutions.");
			}
			trial++;

		}
		s.close();
	}

	// one section is 5 amps
	/*
	 * 0 -> 6 -> 30 mA 1 -> 2 -> 10 mA 2 -> 5 -> 25 mA 3 -> 5 -> 25 mA 4 -> 4 ->
	 * 20 mA 5 -> 5 -> 25 mA 6 -> 6 -> 25 mA 7 -> 3 -> 15 mA 8 -> 7 -> 35 mA 9
	 * -> 6 -> 30 mA
	 */
	public static int digitToMA(int digit) {
		switch (digit) {
		case 0:
			return 30;
		case 1:
			return 10;
		case 2:
			return 25;
		case 3:
			return 25;
		case 4:
			return 20;
		case 5:
			return 25;
		case 6:
			return 30;
		case 7:
			return 15;
		case 8:
			return 35;
		case 9:
			return 30;
		}
		return 0;
	}
}
