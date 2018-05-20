package ccsce10;


import java.util.*;

/*
3
12:03:21 50
9:52:03 105
11:54:14 370

If case 2 runs 105% faster then normal, the output shows at least one intersection. If there is a single
intersection there should be 20 more intersections (it takes 20 seconds for case 2 to then become 1 second off)

 */
public class Problem2 {

	public static void main(String[] args) {
		new Problem2();
	}
	
	public Problem2() {
		Scanner input = new Scanner(System.in);
		
		int clockCount = input.nextInt();
		
		while (--clockCount >= 0) {
			String time = input.next();
			String[] digits = time.split(":");
			
			int hour = Integer.parseInt(digits[0]);
			int min = Integer.parseInt(digits[1]);
			int sec = Integer.parseInt(digits[2]);
			int offset = (hour * 3600) + (min * 60) + sec;
			
//			System.out.format("%d %d %d\n", hour.get(), min.get(), sec.get());
			
			int error = input.nextInt();
			input.nextLine();
			
			System.out.println(instantsPerWeek(offset, error));
		}
	}
	
	public int instantsPerWeek(int wrongClock, int error) {
		final int week = 7 * 24 * 60 * 60;
		final int hour12 = 12 * 60 * 60;
		int instants = 0;
		int overflow = 0;
		for (int rightClock = 0; rightClock < week; rightClock++) {
			overflow += error;
			while (overflow >= 100) {
				overflow -= 100;
				if ((overflow > 100 || overflow == 0) && wrongClock % hour12 == rightClock % hour12) {
					instants++;
				}
				wrongClock++;
			}
		}
		return instants;
	}
	
}
