package ship.acm_10_25_08_shipred;
import java.util.ArrayList;
import java.util.Scanner;


public class ISoar
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		ArrayList<Bounds> bounds;
		Bounds b = null;
		
		double length, left, right;
		// While we can read in a positive length...
		while (sc.hasNextDouble() && (length = sc.nextDouble()) >= 0)
		{
			bounds = new ArrayList<Bounds>();
			
			while ((left = sc.nextDouble()) <= (right = sc.nextDouble()))
			{
				// Clip the sides of the building if they
				// are past the length or less then 0
				right = Math.min(right, length);
				left = Math.max(left, 0.0);
				// Check to see that bounds of the building
				// is inside the highway
				if (left >= length || right <= 0)
					continue;
				// Just add the first bounds
				if (bounds.size() == 0)
				{
					bounds.add(new Bounds(left, right));
				}
				else
				{
					// Check to see if the bounds to add
					// intersects an existing bounds, if it does
					// join them, if not then add it as a new bound.
					boolean intersects = false;
					for (int i = 0; i < bounds.size() && !intersects; i++)
						intersects = bounds.get(i).add(left, right);
					if (!intersects)
						bounds.add(new Bounds(left, right));
				}
			}
			// Compute the tree length;
			double x = 0.0;
			double trees = 0.0;
			for (int i = 0; i < bounds.size(); i++)
			{
				b = bounds.get(i);
				trees += b.left - x;
				x = b.right;
			}
			trees += length - x;
			
			System.out.format("The total planting length is %.1f\n", trees);
		}
	}
	
	private static class Bounds
	{
		public double left;
		public double right;
		
		public Bounds(double l, double r)
		{
			left = l;
			right = r;
		}
		
		public boolean intersects(double l, double r)
		{
			return (r >= left && l <= right);
		}
		
		public boolean add(double l, double r)
		{
			if (!intersects(l, r))
				return false;
			left = Math.min(l, left);
			right = Math.max(r, right);
			return true;
		}
	}
}