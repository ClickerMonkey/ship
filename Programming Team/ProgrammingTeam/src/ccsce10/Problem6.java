package ccsce10;


import java.util.*;

/*
2
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
1 1 1 15 1 29 15 29 29 29 29 15 29 1 15 1

 */
public class Problem6 {

	class Point implements Comparable<Point> {
		int x, y;
		boolean used = false;
		ArrayList<Point> neighbors = new ArrayList<Point>();
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		int distanceSq(Point p) {
			int dx = x - p.x;
			int dy = y - p.y;
			return (dx * dx + dy * dy);
		}
		public int compareTo(Point arg0) {
			return neighbors.size() - arg0.neighbors.size();
		}
	}
	
	public static void main(String[] args) {
		new Problem6();
	}
	
	
	public Problem6() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		Point[] students = new Point[8];
		while (--caseCount >= 0) {
			for (int i = 0; i < 8; i++) {
				students[i] = new Point(input.nextInt(), input.nextInt());
			}

			Point[] OUTLETS = {
					new Point(0, 10),
					new Point(0, 20),
					new Point(30, 10),
					new Point(30, 20),
					new Point(10, 0),
					new Point(20, 0),
					new Point(10, 30),
					new Point(20, 30),
			};
			
			int max = getMax(OUTLETS, students);
			
			System.out.println(max);
		}
	}
	
	public int getMax(Point[] outlets, Point[] students) {
		for (int i = 0; i < 8; i++) {
			students[i].neighbors.clear();
			for (int j = 0; j < 8; j++) {
				if (outlets[j].distanceSq(students[i]) <= 100) {
					students[i].neighbors.add(outlets[j]);
				}
			}
		}
		
		Arrays.sort(students);
		
		int studentCount = 0;
		for (int i = 0; i < 8; i++) {
			ArrayList<Point> neighs = students[i].neighbors;
			for (int k = neighs.size() - 1; k >= 0; k--) {
				if (!neighs.get(k).used) {
					neighs.get(k).used = true;
					studentCount++;
					break;
				}
			}
		}
		return studentCount;
	}
	
}
