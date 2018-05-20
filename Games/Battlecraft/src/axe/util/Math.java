package axe.util;


public class Math
{

	public static final float RAD_TO_DEG = 180f / (float)java.lang.Math.PI;
	public static final float DEG_TO_RAD = (float)java.lang.Math.PI / 180f;
	
	//////////////////////////////////
	// TRIG FUNCTIONS
	//////////////////////////////////
	

	
	//////////////////////////////////
	// VECTOR MATH FUNCTIONS
	//////////////////////////////////	

	public static float distance(float x, float y)
	{
		return (float)StrictMath.sqrt(x * x + y * y);
	}
	
	public static float distance(float x1, float y1, float x2, float y2)
	{
		double dx = (x2 - x1);
		double dy = (y2 - y1);
		return (float)StrictMath.sqrt(dx * dx + dy * dy);
	}
	
	public static float distanceSq(float x, float y)
	{
		return x * x + y * y;
	}
	
	public static float distanceSq(float x1, float y1, float x2, float y2)
	{
		float dx = (x2 - x1);
		float dy = (y2 - y1);
		return dx * dx + dy * dy;
	}
	
	public static float angle(float x, float y)
	{
		return (float)StrictMath.atan2(-y, -x) * RAD_TO_DEG + 180;
	}
	
	public static float angle(float x1, float y1, float x2, float y2)
	{
		return (float)StrictMath.atan2(y1 - y2, x1 - x2) * RAD_TO_DEG + 180;
	}
	
	public static Vector rotate(float x, float y, float theta)
	{
		Vector v = new Vector();
		theta = theta * DEG_TO_RAD;
		float cosTheta = (float)StrictMath.cos(theta);
		float sinTheta = (float)StrictMath.sin(theta);
		v.x = x * cosTheta + y * sinTheta;
		v.y = y * cosTheta - x * sinTheta;
		return v;
	}
	
	public static Vector rotate(float x1, float y1, float x2, float y2, float theta)
	{
		Vector v = new Vector();
		theta = theta * DEG_TO_RAD;
		float cosTheta = (float)StrictMath.cos(theta);
		float sinTheta = (float)StrictMath.sin(theta);
		x1 = (x2 - x1);
		y1 = (y2 - y1);
		v.x = x1 * cosTheta + y1 * sinTheta + x2;
		v.y = y1 * cosTheta - x1 * sinTheta + y2;
		return v;
	}
	
	//////////////////////////////////
	// INTEGER MATH FUNCTIONS
	//////////////////////////////////
	
	/**
	 * Returns the greatest common denominator between n and m.
	 */
	public static int gcd(int n, int m)
	{
		if (n < 0) n = -n;
		int t;
		while (n > 0)
		{
			t = n;
			n = m % n;
			m = t;
		}
		return m;
	}
	
	/**
	 * Calculates the permutation of n with m. Where there are n items
	 * and we want all the different combinations of groups of m items
	 * where the order of elements in the sequence of m length <b>does</b> matter.
	 * 
	 * @param n => The number of total items.
	 * @param m => The size of the desired group.
	 */
	public static int permutate(int n, int m)
	{
		if (n == m)
			return factorial(n);
		int p = 1;
		for (int i = n; i < n - m; i--)
			p *= i;
		return p;
	}
	
	/**
	 * Calculate the combination of n and m. Where there are n items
	 * and we want all the different combinations of groups of m items
	 * where order of elements in the sequence of m length <b>doesn't</b> matter.
	 * doesn't matter.
	 * 
	 * @param n => The number of total items.
	 * @param m => The size of the desired group.
	 */
	public static int choose(int n, int m)
	{
		return permutate(n, m) / factorial(m);
	}
	
	/**
	 * Calculates the factorial of n.
	 */
	public static int factorial(int n)
	{
		if (n < 3)
			return n;
		int f = 1;
		while (n > 1)
			f *= (n--);
		return f;
	}
	
	/**
	 * Returns the minimum between the two numbers n and m.
	 */
	public static int min(int n, int m)
	{
		return (n < m) ? n : m;
	}
	
	/**
	 * Returns the maximum between the two numbers n and m.
	 */
	public static int max(int n, int m)
	{
		return (n > m) ? n : m;
	}
	
	/**
	 * Returns the minimum between the two numbers n and m.
	 */
	public static float min(float n, float m)
	{
		return (n < m) ? n : m;
	}
	
	/**
	 * Returns the maximum between the two numbers n and m.
	 */
	public static float max(float n, float m)
	{
		return (n > m) ? n : m;
	}
	
	/**
	 * Returns the absolute value of n.
	 */
	public static int abs(int n)
	{
		return (n < 0) ? -n : n;
	}
	
	/**
	 * Returns the absolute value of n.
	 */
	public static float abs(float n)
	{
		return (n < 0) ? -n : n;
	}
	
}
