package ship.acm_10_25_08_shipred;
//hello world
import java.util.Scanner;

public class CloseEnough
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		double fats[] = new double[10];
		double carbs[] = new double[10];
		double prots[] = new double[10];

		double calories, temp;

		while (true)
		{
			calories = sc.nextDouble();

			double temp1 = sc.nextDouble();
			double temp2 = sc.nextDouble();
			double temp3 = sc.nextDouble();
			if (calories == 0 && temp1 == 0 && temp2 == 0 && temp3 == 0)
				break;

			// math for rounding error
			double change = -0.5;
			for (int index = 0; index < 10; index++)
			{
				fats[index] = temp1 + change;
				carbs[index] = temp2 + change;
				prots[index] = temp3 + change;
				
				if(fats[index]<0)
					fats[index]=0;
				if(carbs[index]<0)
					carbs[index]=0;
				if(prots[index]<0)
					prots[index]=0;
				
				change = change + 0.1;
			}

			boolean done = false;

			for (int fatC = 0; fatC < 10; fatC++)
			{
				if (done)
					break;
				for (int carbC = 0; carbC < 10; carbC++)
				{
					if (done)
						break;
					for (int protC = 0; protC < 10; protC++)
					{
						temp = (fats[fatC] * 9.0) + (carbs[carbC] * 4.0)
								+ (prots[protC] * 4.0);
						if (temp >= (calories - 0.5) && temp <= (calories + 0.4))
						{
							done = true;
							System.out.println("yes");
							break;
						}
					}
				}
			}

			if (!done)
				System.out.println("no");
		}
	}

}
