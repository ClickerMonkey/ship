package ship.weekly;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;


public class SetMath
{
	
	private static final int MODE_UNION = 0;
	private static final int MODE_INTERSECT = 1;

	public static void main(String[] args) {
		new SetMath();
	}
	
	class Set extends ArrayList<Character> {
	}
	
	SetMath() {
		Scanner in = new Scanner(System.in);

		int caseNum = 1;
		
		String line = in.nextLine().trim();
		while (line.length() > 0) {

			TreeSet<String> set = new TreeSet<String>();
			TreeSet<String> result = new TreeSet<String>();
			int mode = MODE_UNION;
			
			Scanner elements = new Scanner(line);
			while (elements.hasNext()) {
				String e = elements.next().trim();
				
				if (e.equals("+")) {
					set = result;
					result = new TreeSet<String>();
					if (mode == MODE_UNION) {
						result.addAll(set);
					}
					mode = MODE_UNION;
				}
				else if (e.equals("–")) {
					set = result;
					result = new TreeSet<String>();
					if (mode == MODE_UNION) {
						result.addAll(set);
					}
					mode = MODE_INTERSECT;
				}
				else {
					switch (mode) {
						case MODE_UNION:
							result.add(e);
							break;
						case MODE_INTERSECT:
							if (set.contains(e)) {
								result.add(e);
							}
							break;
					}
				}
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("Case#").append(caseNum).append(": {");
			
			int index = 0;
			for (String e : result) {
				if (index > 0)	{
					sb.append(", ");
				}
				sb.append(e);
				index++;
			}
			sb.append("}");
			
			System.out.println(sb);
		
			line = in.nextLine();
			caseNum++;
		}
	}
	
	Set union(Set a, Set b) {
		Set result = new Set();
	
		TreeSet<Character> unique = new TreeSet<Character>();
		for (Character e : a) {
			unique.add(e);
		}
		for (Character e : b) {
			unique.add(e);
		}
		
		for (Character e : unique) {
			result.add(e);
		}
		
		return result;
	}
	
	Set intersection(Set a, Set b) {
		Set result = new Set();
		
		for (Character e : a) {
			if (b.contains(e)) {
				result.add(e);
			}
		}
		
		return result;
	}
	
}
