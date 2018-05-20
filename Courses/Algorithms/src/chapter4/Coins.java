package chapter4;

public class Coins
{

	public static void main(String[] args)
	{
		Coins c = new Coins();
		c.coins = new int[] {4, 4, 4, 4, 3, 4, 4, 4, 4, 4, 4};
		System.out.println("Lowest: " + c.divideIntoThree(0, c.coins.length - 1));
		System.out.println("Comparisons: " + c.comparisons);
	}
	
	public int comparisons = 0;
	public int[] coins;
	
	public int divideIntoThree(int low, int high)
	{
		int total = high - low + 1;
		System.out.format("Low: %d High: %d Total: %d\n", low, high, total);
		if (total == 1)
			return coins[low];
		if (total == 2)
			return min(coins[low], coins[high]);
		
		int left = divideIntoThree(low, low + total / 3 - 1);
		int middle = divideIntoThree(low + total / 3, low + 2 * total / 3 - 1);
		int right = divideIntoThree(low + 2 * total / 3, low + total - 1);
		
		return min(left, min(middle, right));
	}
	
	public int min(int a, int b)
	{
		comparisons++;
		return (a < b ? a : b);
	}
}
