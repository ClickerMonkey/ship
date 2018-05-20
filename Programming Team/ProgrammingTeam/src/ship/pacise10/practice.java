package ship.pacise10;

import java.util.*;

public class practice {

	public static void main(String[] args) {
		new practice();
	}
	
	class Node {
		int value, power;
		Node (int value) {
			this.value = value;
			this.power = 1;
		}
	}
	
	class DualPipage {
		StringBuilder[] builders = new StringBuilder[2];
		DualPipage() {
			builders[0] = new StringBuilder();
			builders[1] = new StringBuilder();
		}
		void append(int i, Object o) {
			String s = o.toString();
			builders[i].append(s);
			int other = (1 - i);
			for (int k = 0; k < s.length(); k++) {
				builders[other].append(" ");
			}
		}
		void print() {
			System.out.println(builders[0]);
			System.out.println(builders[1]);
		}
	}
	
	practice() {
		Scanner sc = new Scanner(System.in);
		int value = sc.nextInt();
		int caseNum = 1;
		while (value != 0) {
			
			Vector<Node> nodes = new Vector<Node>();
			int x = value;
			while (x > 1) {
				int f = factor(x);
				if (nodes.isEmpty()) {
					nodes.add(new Node(f));
				} else {
					Node n = nodes.lastElement();
					if (n.value == f) {
						n.power++;
					} else {
						nodes.add(new Node(f));
					}
				}
				x /= f;
			}
			
			DualPipage pipe = new DualPipage();
			pipe.append(1, String.format("Case %d: %d=", caseNum, value));
			for (int k = 0; k < nodes.size(); k++) {
				if (k > 0) {
					pipe.append(1, "x ");
				}
				Node node = nodes.get(k);
				pipe.append(1, node.value);
				if (node.power > 1) {
					pipe.append(0, node.power);
				}
			}

			pipe.print();
			System.out.println();	
			value = sc.nextInt();
			caseNum++;
		}
	}
	
	int factor(int n) {
		if (n == 1) return 1;
		if (n % 2 == 0) return 2;
		int max = (int)Math.sqrt(n);
		for (int i = 3; i <= max; i+=2) {
			if (n % i == 0) {
				return i;
			}
		}
		return n;
	}

}
