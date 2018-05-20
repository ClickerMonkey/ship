package ship.dickinson10;

import java.util.Scanner;

/**
 * @author Philip Diffenderfer
 */
public class zoom {

	/* TEAM 10
15 15
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
21 22 23 24 25 26 27 28 29 210 211 212 213 214 215
31 32 33 34 35 36 37 38 39 310 311 312 313 314 315
41 42 43 44 45 46 47 48 49 410 411 412 413 414 415
51 52 53 54 55 56 57 58 59 510 511 512 513 514 515
61 62 63 64 65 66 67 68 69 610 611 612 613 614 615
71 72 73 74 75 76 77 78 79 710 711 712 713 714 715
81 82 83 84 85 86 87 88 89 810 811 812 813 814 815
91 92 93 94 95 96 97 98 99 910 911 912 913 914 915
101 102 103 104 105 106 107 108 109 1010 1011 1012 1013 1014 1015
111 112 113 114 115 116 117 118 119 1110 1111 1112 1113 1114 1115
121 122 123 124 125 126 127 128 129 1210 1211 1212 1213 1214 1215
131 132 133 134 135 136 137 138 139 1310 1311 1312 1313 1314 1315
141 142 143 144 145 146 147 148 149 1410 1411 1412 1413 1414 1415
151 152 153 154 155 156 157 158 159 1510 1511 1512 1513 1514 1515

7 8
7 3
14 14
0 14
14 0
-1 -1

	 */
	
	public static void main(String[] args) {
		new zoom();
	}
	
	zoom() {
		Scanner in = new Scanner(System.in);
		
		// The single cases rows and columnds
		int rows = in.nextInt();
		int cols = in.nextInt();
		in.nextLine();
		
		// The image data.
		int[][] image = new int[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				image[y][x] = in.nextInt();
			}
		}
		in.nextLine();
		in.nextLine();
		
		// The first zoom position (row,col)
		int y = in.nextInt();
		int x = in.nextInt();
		
		// While input exists...
		while (y != -1 || x != -1) {

			// Compute the desired bounds of the zoom box.
			int left = x - 6;
			int top = y - 6;
			int right = x + 6;
			int bottom = y + 6;
			
			// Adjust the left side
			if (left < 0) {
				right -= left;
				left = 0;
			}
			// Adjust the right side
			if (right >= cols) {
				left -= (right - cols + 1);
				right = cols - 1;
			}
			// Adjust the top side
			if (top < 0) {
				bottom -= top;
				top = 0;
			}
			// Adjust the bottom side
			if (bottom >= rows) {
				top -= (bottom - rows + 1);
				bottom = rows - 1;
			}
			
//			System.out.format("%d %d %d %d\n", left, top, right, bottom);
			
			// Print out the desired zoom position.
			System.out.format("(%d,%d)\n", y, x);
			// For every pixel in the determined zoom bounds print it out
			for (int yy = top; yy <= bottom; yy++) {
				for (int xx = left; xx <= right; xx++) {
					if (xx > left) {
						System.out.print(" ");
					}
					System.out.print(image[yy][xx]);
				}
				System.out.println();
			}
			System.out.println();
			
			// Get next zoom position
			y = in.nextInt();
			x = in.nextInt();
		}
	}
	
}
