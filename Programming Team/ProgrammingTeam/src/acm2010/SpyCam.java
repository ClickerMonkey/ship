package acm2010;

import java.util.Scanner;


public class SpyCam
{

	public static void main(String[] args) {
		new SpyCam();
	}
	
	class Rect {
		char letter;
		int left, top, right, bottom;
		Rect parent;
		Rect(char letter, int x, int y) {
			left = right = x;
			top = bottom = y;
		}
		void include(int x, int y) {
			if (x < left) left = x;
			if (x > right) right = x;
			if (y < top) top = y;
			if (y > bottom) bottom = y;
		}
		boolean contains(Rect r) {
			return (r.left >= left && r.top >= top && r.right <= right && r.bottom <= bottom);
		}
		boolean intersects(Rect r) {
			return !(r.left > right || r.right < left || r.top > bottom || r.bottom < top);
		}
	}

	int width, height;
	char[][] map;
	
	public SpyCam() {
		Scanner input = new Scanner(System.in);
		
		height = input.nextInt();
		width = input.nextInt();
		input.nextLine();
		
		while (!(height == 0 && width == 0)) {
	
			map = new char[height][width];
			for (int y = 0; y < height; y++) {
				map[y] = input.nextLine().toCharArray();
			}
			
			solve(map);
			
			height = input.nextInt();
			width = input.nextInt();
			input.nextLine();
		}
	}
	
	void solve(char[][] map) {
		// Find the highest letter
		int max = 0; //a
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[y][x] != '.') {
					max = Math.max(max, map[y][x] - 'a');
				}
			}
		}
		max++;
		
		// Build each rect
		Rect[] rects = new Rect[max];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[y][x] != '.') {
					int index = map[y][x] - 'a';
					if (rects[index] == null) {
						rects[index] = new Rect(map[y][x], x, y);
					}
					else {
						rects[index].include(x, y);
					}
				}
			}
		}
		
		// Put each rect in its parent container
		for (int i = 0; i < max - 1; i++) {
			for (int j = i + 1; j < max; j++) {
				if (rects[i].contains(rects[j]) && rects[j].parent == null) {
					rects[j].parent = rects[i];
				}
			}
		}
		
		// Expand each rect
		for (int i = 0; i < max; i++) {
			Rect r = rects[i];
			char notA = '.';
			char notB = (r.parent != null ? r.parent.letter : r.letter);

			// To the left
			while (r.left > 0 && open(r.left, r.top, r.left, r.bottom, notA, notB)) {
				r.left--;
			}
			
			// To the right
			while (r.right < width - 1 && open(r.right, r.top, r.right, r.bottom, notA, notB)) {
				r.right++;
			}
			
			// To the top
			while (r.top > 0 && open(r.left, r.top, r.right, r.top, notA, notB)) {
				r.top--;
			}
			
			// To the top
			while (r.bottom < height - 1 && open(r.left, r.bottom, r.right, r.bottom, notA, notB)) {
				r.bottom++;
			}
		}
		
		// Check for overlap
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < max; i++) {
			Rect r = rects[i];
			if (!covered(r)) {
				sb.append(r.letter);
			}
		}
		
		System.out.format("Uncovered: %s\n", sb);
	}
	
	boolean open(int sx, int sy, int ex, int ey, char notA, char notB) {
		for (int y = sy; y <= ey; y++) {
			for (int x = sx; x <= ex; x++) {
				if (map[y][x] == notA || map[y][x] == notB) {
					return false;
				}
			}
		}
		return true;
	}
	
	boolean covered(Rect r) {
		for (int y = r.top; y <= r.bottom; y++) {
			for (int x = r.left; x <= r.right; x++) {
				if (map[y][x] != r.letter) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
