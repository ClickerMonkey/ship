package ship.pacise10;

import java.util.*;

public class problem4 {
	public static void main(String[] args) {
		new problem4();
	}

	ArrayList<String> permutes;

	problem4() {
		Scanner in = new Scanner(System.in);
		int A = in.nextInt();
		int B = in.nextInt();
		int caseNum = 1;
		while (A != 0 && B != 0) { 

			int total = 0;
			for (int i = A; i <= B; i++) {
				if (isPermutable(i)) {
					total++;
				}
			}
			
			System.out.format("Case %d: %d numbers\n", caseNum, total);
			
			A = in.nextInt();
			B = in.nextInt();
			caseNum++;
		}
	}

	boolean isPermutable(int n) {
		String s = String.valueOf(n);
		if (s.contains("0") || s.contains("4") || s.contains("6") || s.contains("8") ) {
			return false;
		}
		if (s.length() > 1 && (s.contains("2") || s.contains("5"))) {
			return false;
		}

		permutes = new ArrayList<String>();
		permutate("", s);

		int unique = removeDuplicates(permutes);
		for (int i = 0; i < unique; i++) {
			int value = Integer.parseInt(permutes.get(i));
			if (!isPrime(value)) {
				return false;
			}
		}
		return true;
	}

	boolean isPrime(int n) {
		if (n == 1) return false;
		if (n == 2) return true;
		if (n % 2 == 0) return false;
		int max = (int)Math.sqrt(n);
		for (int i = 3; i <= max; i+=2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	long factorial(long n) {
		long x = n;
		while (--x > 1) n *= x;
		return n;
	}

	void permutate(String set, String dyn) {
		if (dyn.length() == 1) {
			permutes.add(set + dyn);
		} else {
			for (int i = 0; i < dyn.length(); i++) {
				String newSet = set + dyn.charAt(i);
				String newDyn = dyn.substring(0, i) + dyn.substring(i + 1);
				permutate(newSet, newDyn);
			}
		}
	}

	int removeDuplicates(ArrayList<String> set) {
		Collections.sort(set);
		int n = set.size() - 1;
		int unique = 0;
		for (int i = 0; i <= n; i++) {
			String value = set.get(i);
			set.set(unique++, value);
			while (i < n && set.get(i + 1).equals(value)) {
				i++;
			}
		}
		return unique;
	}

	
}
