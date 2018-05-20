package ship.practice.unfinished;
//import java.util.Arrays;
import java.util.Scanner;
//import java.util.Comparator;
import java.awt.geom.Point2D;



public class HerdingFrosh
{
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int total = sc.nextInt();

		Point2D points[] = new Point2D[total];
		for (int i = 0; i < total; i++)
			points[i] = new Point2D.Double(sc.nextDouble(), sc.nextDouble());

//		/* Graham Scan Algorithm for Convex Hulls */
//		Point2D pivot = new Point2D.Double(0.0, 0.0);
//		// Sort points by angle then distance from the pivot.
//		double minAngle = 0.0, minDistance = 0.0;
//		int minIndex = 0;
		for (int i = 0; i < total - 1; i++)
		{
//			minIndex = i;
			for (int j = i + 1; j < total; j++)
			{

			}
		}
	}

	public static double distance(Point2D p1, Point2D p2)
	{
		return Point2D.distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double angle(Point2D p1, Point2D p2)
	{
		return Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()) * 180 / Math.PI;
	}


}
