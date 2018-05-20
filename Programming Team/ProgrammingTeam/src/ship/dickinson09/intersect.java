package ship.dickinson09;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class intersect {
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		HashSet<Integer> first = new HashSet<Integer>(100);
		HashSet<Integer> second = new HashSet<Integer>(100);
		
		int n;
		
		while ((n = sc.nextInt()) != -1)
			first.add(n);
		while ((n = sc.nextInt()) != -1)
			second.add(n);
		
		HashSet<Integer> inter = new HashSet<Integer>();
		
		Integer[] f = new Integer[first.size()];
		f = first.toArray(f);
		Integer[] s = new Integer[second.size()];
		s = second.toArray(s);

		for (int i = 0; i < f.length; i++)
			if (!inter.contains(f[i]) && second.contains(f[i]))
				inter.add(f[i]);
		
		for (int i = 0; i < s.length; i++)
			if (!inter.contains(s[i]) && first.contains(s[i]))
				inter.add(s[i]);
		
		Integer[] intersection = new Integer[inter.size()];
		intersection = inter.toArray(intersection);
		
		Arrays.sort(intersection);
		
		System.out.format("%d integers appear in both sets.\nThe integers are:\n", intersection.length);
		
		for (int i = 0; i < intersection.length; i++)
			System.out.println(intersection[i]);
	}
}
