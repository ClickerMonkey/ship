import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheatSheet 
{

	public static void main(String[] args) {
		// DATE REGEX (shows validating + parsing)
		// (1[0-2]|[1-9]) = 10,11,12 OR 1,2,3,4,5,6,7,8,9
		// ([0-5][0-9]) = 00,01,02 ... 59
		// ([ap]m) = am,pm
		Pattern patDate = Pattern.compile("^(1[0-2]|[1-9]):([0-5][0-9])([ap]m)$");
		Matcher matDate = patDate.matcher("10:25pm");
		if (!matDate.matches()) {
			System.out.println("Date is not valid");
		} else {
			// Skip group[0], its simply the input string
			for (int i = 0; i < matDate.groupCount(); i++) {
				System.out.println(matDate.group(i + 1));
			}
			System.out.println();
		}

		// EQUATION REGEX PARSER (shows finding an expr)
		// ([\\d]+) = 1 or more digits OR
		// [\\*-\\+/] = *,-,+,/
		Pattern patEq = Pattern.compile("(([\\d]+)|[\\*-\\+/])");
		Matcher matEq = patEq.matcher("  3+12 * 3 /  56 * 67 * 8");
		while (matEq.find()) {
			System.out.println(matEq.group());
		}
		System.out.println();

		// TRUNCATING
		System.out.println(truncate(0.4565, 2));			// 0.45
		System.out.println(truncate(23432.49365, 1));	// 23432.4
		System.out.println(truncate(23432.49365, -1));	// 23430.0

		// FORMATING (money)
		DecimalFormat moneyFormat = new DecimalFormat("$###,###.##");
		System.out.println(moneyFormat.format(21343423.455));

		// FORMATING (percent)
		DecimalFormat percentFormat = new DecimalFormat("###.##%");
		System.out.println(percentFormat.format(0.89543));

		// FORMATTING (everything)
		// %s = String
		// %d = int
		// %ld = long
	}

	// Returns the greatest common denominator between a and b. If either are
	// negative their absolute value must be given.
	long gcd(long a, long b) {
		while (b > 0) {
			a %= b;
			a ^= b;
			b ^= a;
			a ^= b;
		}
		return a;
	}

	// Returns the lowest common multiple between a and b.
	long lcm(long a, long b) {
		return (a * b) / gcd(a, b);
	}

	// Returns the lowest prime factor of n. If n is prime itself is returned
	// (unless its 1 then its not prime). This could be used to determine all
	// prime factors of a given number.
	int factor(int n) {
		if ((n & 1) == 0)					// Even numbers factor=2
			return 2;
		int max = (int)Math.sqrt(n);		// Its greatest possible factor
		for (int a = 3; a <= max; a += 2) {
			if (n % a == 0)					// If divisible return factor
				return a;
		}
		return n;							// It's a prime number (or 1)
	}

	// Calculates the factorial of the given integer n.
	// F(n) = n! = n*(n-1)*(n-2)*...*(1)
	long factorial(long n) {
		long f = n;
		while (--n > 1)
			f *= n;
		return f;
	}

	// Calculates the permutation of the given integer n and m. Ordered collection of distinct elements.
	// P(n,m) = n! / (n - m)!
	long permutate(long n, long m) {
		long p = n;
		while (--m >= 1)
			p *= --n;
		return p;
	}

	// Simplifies two given numbers
	void simplify(long[] factors, int i, int j) {
		long gcd = gcd(factors[i], factors[j]);
		factors[i] /= gcd;
		factors[j] /= gcd;
	}

	// Calculates the combination of the given integer n and m. Un-ordered collection of distinct elements.
	// C(n,m) = n! / m!(n - m)!
	long choose(int n, int m) {
		long factors[] = {1, 1, 0, 0};
		if (m > (n >> 1))
			m = n - m;
		for (int i = m; i != 0; i--) {
			factors[3] = n - m + i;
			factors[4] = i;
			simplify(factors, 3, 4);
			simplify(factors, 0, 3);
			simplify(factors, 2, 1);
			factors[0] *= factors[2];
			factors[1] *= factors[3];
		}
		return factors[0] / factors[1];
	}

	// a^n (mod p) = base^power (% mod)
	long fastexp(long base, long power, long mod) {
		long prod = 1;
		long term = base % mod;
		while(power > 0) {
			if((power & 1) == 1) 
				prod = (prod * term) % mod;
			power >>= 1;
			term = (term * term) % mod;
		}
		return prod;
	}

	// F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)
	long fibonacci(int n) {
		long a = 1, b = 1, c;
		for (int i = 3; i <= n; i++) {
			c = a + b;
			a = b;
			b = c;
		}
		return a;
	}

	// Returns true if the given integer is prime.
	boolean isPrime(int n) {
		if (n == 1) return false;
		if (n == 2) return true;
		if ((n & 1) == 0) return false;
		int factor = (int)Math.sqrt(n);
		for (int k = 3; k <= factor; k += 2) {
			if (n % k == 0) {
				return false;
			}
		}
		return true;
	}

	// Truncates the given value by the given places.
	static double truncate(double x, int places) {
		double ten = Math.pow(10, places);
		return Math.floor(x * ten) / ten;
	}


	//===========================================================================
	// A set is an immutable group of unique numbers ordered from smallest to 
	// largest. The values in the set must be ordered for all set operations to
	// function properly, so don't manually change/re-order these!
	//===========================================================================
	public class Set {
		// The ordered set of numbers.
		protected final int[] set;
		// The count of numbers in this set.
		protected int n;
		// Initializes a new set of numbers. The numbers passed in will be ordered
		// and any duplicates will automatically be removed.
		public Set(int ... set) {
			// Sort the given set
			Arrays.sort(this.set = set);
			n = set.length;
			// Remove all duplicated numbers
			if (n > 1) {
				n = removeDuplicates();
			}
		}
		// Removes all duplicates from the set and returns how many unique
		// numbers in the set exist.
		private int removeDuplicates() {
			int j = 0, last = n - 1;
			for (int i = 0; i < n; i++) {
				// This is a unique number, keep it.
				set[j++] = set[i];
				// If the next is a duplicate then skip it.
				while ((i < last) && set[i] == set[i + 1])
					i++;
			}
			return j;
		}
		// Returns true if this set contains the given integer.
		public boolean contains(int x) {
			return Arrays.binarySearch(set, x, 0, n) != -1; 
		}
		// Returns the i'th element
		public int get(int index) {
			return set[index];
		}
	}


	public class Subset extends Set
	{
		private int[] subset;

		@Override
		public int get(int index) {
			return set[subset[index]];
		}
	}


}
