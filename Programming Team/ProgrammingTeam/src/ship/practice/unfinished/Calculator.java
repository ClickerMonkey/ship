package ship.practice.unfinished;

import java.util.ArrayList;
import java.util.Scanner;


public class Calculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = new String(); 
		ArrayList<String> numbers = new ArrayList<String>();
		ArrayList<String> operator = new ArrayList<String>();
		ArrayList<String> input = new ArrayList<String>();
		String[] nums;
		String[] ops;
		while(sc.hasNextLine())
		{
			line=sc.nextLine();
			ops=line.split("\\d+");
			nums = line.split("[\\-\\+\\/\\*]");
			for(int idx=0; idx<ops.length;idx++)
			{
				ops[idx]=ops[idx].trim();
				if(ops[idx].equals(new String("*"))||ops[idx].equals(new String("/"))||
						ops[idx].equals(new String("-"))||ops[idx].equals(new String("+")))
					operator.add(ops[idx]);
				//System.out.print(ops[idx]+ ' ');
			}
			for(int idx=0;idx<nums.length;idx++)
			{
				nums[idx]=nums[idx].trim();
				numbers.add(nums[idx]);
				//System.out.print(nums[idx]+' ');
			}
			for(int idx=0; idx<nums.length;idx++)
			{
				if(ops.length>idx)
				{
					System.out.print(ops[idx]+ ' ');
				}
				System.out.print(nums[idx]+ ' ');
			}
			System.out.print("= ");
			printresult(operator,numbers);
			input.clear();
			numbers.clear();
			operator.clear();
		}
		
	}

	private static void printresult(ArrayList<String> operator, ArrayList<String> numbers) 
	{
		int location;
		int location2;
		Integer op1 = null;
		while(operator.contains(new String("*")))
		{
			location=operator.indexOf(new String("*"));
			op1=getMultValue(numbers,location);
			//numbers.add(location, op1.toString());
			numbers.remove(location+1);
			numbers.remove(location+1);
//			printVals(numbers);
			operator.remove(location);
		}
		while(operator.contains(new String("/")))
		{
			location=operator.indexOf(new String("/"));
			op1=getDivValue(numbers,location);
			//numbers.add(location, op1.toString());
			numbers.remove(location+1);
			numbers.remove(location+1);
//			printVals(numbers);
			operator.remove(location);
		}
		while(operator.contains(new String("+"))||operator.contains(new String("-")))
		{
//			System.out.println("test");
			location=operator.indexOf(new String("+"));
			location2=operator.indexOf(new String("-"));
//			System.out.println(location+"#"+location2);
			//System.out.print(" #"+operator.get(0)+"# ");
			if(location<location2&&location!=-1||location2==-1)
			{
//				System.out.println("plus");
				op1 = getPlusValue(numbers, location);
				//printVals(numbers);
				//numbers.add(location, op1.toString());
				numbers.remove(location+1);
				numbers.remove(location+1);
//				printVals(numbers);
				operator.remove(location);
			}
			else
			{
//				System.out.println("minus");
				op1=getMinusValue(numbers,location2);
			//	numbers.add(location, op1.toString());
				numbers.remove(location2+1);
				numbers.remove(location2+1);
	//			printVals(numbers);
				operator.remove(location2);
			}
		}
		System.out.println(numbers.get(0));
	}

	private static void printVals(ArrayList<String> numbers) {
		for(int idx=0;idx<numbers.size();idx++)
			System.out.print("."+numbers.get(idx)+".");
		System.out.print("!");
	}

	private static Integer getPlusValue(ArrayList<String> numbers, int location) 
	{
		
		Integer x = Integer.parseInt(numbers.get(location))+Integer.parseInt(numbers.get(location+1));
		numbers.add(location, x.toString());
		return x;
	}
	
	private static Integer getMinusValue(ArrayList<String> numbers, int location) 
	{
		Integer x = Integer.parseInt(numbers.get(location))-Integer.parseInt(numbers.get(location+1));
		numbers.add(location, x.toString());
		return x;
	}
	
	private static Integer getMultValue(ArrayList<String> numbers, int location) 
	{
		Integer x = Integer.parseInt(numbers.get(location))*Integer.parseInt(numbers.get(location+1));
		numbers.add(location, x.toString());
		return x;
	}
	
	private static Integer getDivValue(ArrayList<String> numbers, int location) 
	{
		Integer x = Integer.parseInt(numbers.get(location))/Integer.parseInt(numbers.get(location+1));
		numbers.add(location, x.toString());
		return x;
	}

}
