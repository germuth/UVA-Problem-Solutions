package ca.germuth.acm_pacnw_2012.J;

import java.util.Scanner;
/**
 * Temple Build
 * ACM ICPC Pacific Northwest Regionals 2012
 * Problem J
 * 
 * Can be solved with one dimensional dynamic programming. Start with height
 * = 0 and solve for the optimal configuration, then slowly increase height, 
 * solving for optimal volume using each block type.
 * One trick is that the highest height might not have the highest volume.
 * 
 * @author Aaron
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			int height = s.nextInt();
			int bottom = s.nextInt();
			int top = s.nextInt();
			int cube1 = s.nextInt();
			int cube2 = s.nextInt();
			int cube3 = s.nextInt();

			long[] blocks = new long[3];
			blocks[0] = (long)cube1;
			blocks[1] = (long)cube2;
			blocks[2] = (long)cube3;

			// divide by half
			double topD = top / 2.0;
			double bottomD = bottom / 2.0;
			double min = Math.min(topD, bottomD);
			topD -= min;
			bottomD -= min;

			// slope = rise / run = (minus height) / bottom
			//for run can't just minus bottom - top
			//problem only hints that bottom larger than top
			//just to be safe
			double slope = -height / (Math.max(bottomD, topD) - Math.min(bottomD, topD));

			// want to include 0 as well
			long[] bestVolumeSoFar = new long[height + 1];

			for (int h = 0; h <= height; h++) {

				long currentVolume = bestVolumeSoFar[h];

				// for each block
				for (int b = 0; b < blocks.length; b++) {
					long blockSize = blocks[b];
					long blockVolume = blockSize*blockSize*blockSize;

					int newHeight = h + (int)blockSize;
					
					// only consider if within height
					if (newHeight <= height) {
						// get width at current height through linear interpolation
						// x = y - b / m
						double x = ((newHeight) - (height)) / slope;

						double width = (x + min) * 2;
//						double width = x * 2;

						//num of blocks that will fit across
						long numBlocks = (long) (width / blockSize);
						//need 2D plane of blocks
						numBlocks = numBlocks * numBlocks;
						
						long volumeAdded = numBlocks*blockVolume;
						
						long totalVolume = currentVolume + volumeAdded;
						
						if (bestVolumeSoFar[newHeight] < totalVolume) {
							bestVolumeSoFar[newHeight] = totalVolume;
						}
					}
				}
			}

			long max = Long.MIN_VALUE;
			// find best height
			for (int i = 0; i < bestVolumeSoFar.length; i++) {
				if (bestVolumeSoFar[i] > max) {
					max = bestVolumeSoFar[i];
				}
			}
			System.out.println(max);
		}
		s.close();
	}
}