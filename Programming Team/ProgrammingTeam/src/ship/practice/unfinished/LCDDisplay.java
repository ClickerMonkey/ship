package ship.practice.unfinished;

import java.util.Scanner;


public class LCDDisplay
{

	public static void main(String[] args)
	{
		new LCDDisplay();
	}
	
	public byte[] nums = {119, 18, 93, 91, 58, 107, 111, 82, 127, 123};
	
	public LCDDisplay()
	{
		Scanner sc = new Scanner(System.in);
		
		int size, value;
		
		while (sc.hasNextInt() && (size = sc.nextInt()) != 0 && (value = sc.nextInt()) != 0)
		{
			int height = 2 * size + 3;
			StringBuffer[] lines = new StringBuffer[height];
			
			int digits = (int)Math.floor(Math.log10(value)) + 1;
			int digit = 0;
			int[] a = new int[7];
			for (int i = 0; i < digits; i++)
			{
				digit = value % 10;
				value = value / 10;

				for (int x = 0; x < 7; x++)
					a[x] = (nums[digit] >> (6 - x)) & 1;

				drawX(lines[0], size, a[0]);
//				drawY(lines[0], size, a[0]);
				drawX(lines[0], size, a[0]);
//				drawY(lines[0], size, a[0]);
				drawX(lines[0], size, a[0]);
				
				
			}
		}
	}
	
	public void drawX(StringBuffer buffer, int size, int a)
	{
		buffer.append(" ");
		for (int i = 0; i < size; i++)
			buffer.append(a == 0 ? " " : "-");
		buffer.append(" ");
	}
}
