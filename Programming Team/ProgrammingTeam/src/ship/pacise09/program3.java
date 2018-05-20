package ship.pacise09;

import java.util.Scanner;
import java.awt.Point;

public class program3
{

	public static void main(String[] args)
	{
		new program3();
	}
	
	public program3()
	{
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine())
		{
			String[] w = new String[4];
			w[0] = sc.nextLine();
			w[1] = sc.nextLine();
			w[2] = sc.nextLine();
			w[3] = sc.nextLine();
			
			test(w[0], w[1], w[2], w[3]);
		}
	}
	
	public void permutation(String[] w)
	{
		int a[] = {0, 1, 2, 3};	
		
	}
	
	public boolean test(String v1, String v2, String h1, String h2)
	{
		int hmax = Math.max(v1.length(), v2.length());
		int vmax = Math.max(h1.length(), h2.length());
		int v1off, v2off, h1off, h2off;

		for (v1off = 0; v1off < vmax - 2; v1off++)
		{
			for (h1off = 0; h1off < hmax - 2; h1off++)
			{
				for (v2off = v1off + 2; v2off < vmax; v2off++)
				{
					for (h2off = h1off + 2; h2off < hmax; h2off++)
					{
						
					}
				}
			}	
		}
	}
	

}