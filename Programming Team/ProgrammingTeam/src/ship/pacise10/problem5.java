package ship.pacise10;

import java.util.*;

public class problem5 {
	public static void main(String[] args) {
		new problem5();
	}

	problem5() {
		Scanner in = new Scanner(System.in);
		TreeMap<String, String> map = new TreeMap<String, String>();
		boolean translated = false;
		while (in.hasNextLine()) {
			String line = in.nextLine().trim();
			if (line.length() == 0) {
				translated = true;
				continue;
			}
			if (line.equals(".")) {
				break;
			}
			if (!translated) {
				String[] part = line.split(" ");
				map.put(part[1], part[0]);
			} else {
				String trans = map.get(line);
				if (trans == null) {
					trans = "duh!?";
				}
				System.out.println(trans);
			}
		}
	}
}
