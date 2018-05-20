import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class P6_ElectionTime {

	public static void main(String[] arg) {
		new P6_ElectionTime();
	}

	private static final Comparator<Job> queueComparator = new Comparator<Job>() {
		public int compare(Job o1, Job o2) {
			return (o1.submitTime - o2.submitTime);
		}
	};
	private static final Comparator<Job> currentComparator = new Comparator<Job>() {
		public int compare(Job o1, Job o2) {
			return (int)(o1.endTime - o2.endTime);
		}
	};
	private static final Comparator<Output> outputComparator = new Comparator<Output>() {
		public int compare(Output o1, Output o2) {
			int dif;

			dif = (int)(o1.time - o2.time);
			if (dif != 0)
				return dif;

			dif = o1.id - o2.id;
			if (dif != 0)
				return dif;

			return (o1.type - o2.type);
		}
	};

	private ArrayList<Job> small = new ArrayList<Job>();
	private ArrayList<Job> reg_short = new ArrayList<Job>();
	private ArrayList<Job> reg_small = new ArrayList<Job>();
	private ArrayList<Job> reg_med = new ArrayList<Job>();
	private ArrayList<Job> big = new ArrayList<Job>();
	private ArrayList<Job> premium = new ArrayList<Job>();
	private PriorityQueue<Job> current = new PriorityQueue<Job>(256, currentComparator);
	private HashSet<Job> allJobs = new HashSet<Job>();

	public P6_ElectionTime() {
		Scanner sc = new Scanner(System.in);

		Job job;

		while (sc.hasNextLine()) {
			sc.nextLine(); // Skip BEGIN JOB

			job = new Job();
			job.id = sc.nextInt();
			job.submitTime = sc.nextInt();
			job.processors = sc.nextInt();
			job.requestTime = sc.nextInt();
			job.requiredTime = sc.nextInt();
			job.exitVal = sc.nextInt();
			sc.nextLine(); // Skip newline

			String next = sc.nextLine();
			job.premium = next.equals("PREMIUM REQUESTED");

			if (job.premium)
				sc.nextLine(); // Skip END JOB

			allJobs.add(job);
		}

		ArrayList<Output> output = new ArrayList<Output>();

		for (Job j : allJobs) {
			if (j.premium) {
				premium.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "premium"));
			} else if (j.processors <= 256 && j.requestTime <= 3*60) {
				small.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "small"));
			} else if (j.processors <= 2044 && j.requestTime <= 6*60) {
				reg_short.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "reg_short"));
			} else if (j.processors <= 2044 && j.requestTime <= 24*60) {
				reg_small.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "reg_small"));
			} else if (j.processors <= 4092) {
				reg_med.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "reg_med"));
			} else {
				big.add(j);
				output.add(new Output(SUBMIT, j.submitTime, j.id, "big"));
			}
		}

		ArrayList[] queues = new ArrayList[] {
				big, reg_med, reg_small, reg_short, small
		};

		int resources = 20000;
		long now = 0;
		while (allJobs.size() > 0) {
			
			// Premium Queue
			int waiting = 0;
			if (premium.size() > 0) {

				Collections.sort(premium, queueComparator);
				LinkedList<Job> remove = new LinkedList<Job>();
				for (Job j : premium) {
					if (j.submitTime > now)
						break;

					waiting++;

					if (j.processors <= resources) {
						remove.add(j);
						j.endTime = now + j.requiredTime - 1;

						current.add(j);
						resources -= j.processors;

						output.add(new Output(SELECT, now, j.id, now, now - j.submitTime));
					}
				}
				for (Job j: remove)
					premium.remove(j);
			}

			// None in the Premium Queue
			if (waiting == 0) {
				for (ArrayList<Job> queue : queues) {
					Collections.sort(queue, queueComparator);
					
					LinkedList<Job> remove = new LinkedList<Job>();
					for (Job j : queue) {
						if (j.submitTime > now)
							break;
						
						if (j.processors <= resources) {
							remove.add(j);
							j.endTime = now + j.requiredTime - 1;

							current.add(j);
							resources -= j.processors;

							output.add(new Output(SELECT, now, j.id, now, now - j.submitTime));
						}
					}
					for (Job j: remove)
						queue.remove(j);
				}
			}

			Job jj;
			while (current.size() != 0 && (jj = current.peek()).endTime == now) {
				current.poll();
				output.add(new Output(FINISHED, now, jj.id, jj.exitVal, jj.requiredTime * jj.processors));
				allJobs.remove(jj);
				resources += jj.processors;
			}
			
			now++;
		}

		Collections.sort(output, outputComparator);

		for (Output out : output)
			System.out.println(out);
	}
	private static class Job {
		int id;
		int submitTime;
		int processors;
		long requestTime;
		long requiredTime;
		int exitVal;
		boolean premium;

		long endTime; //startTime + requiredTime
	}
	public static final int SUBMIT = 0;
	public static final int SELECT = 1;
	public static final int FINISHED = 2;
	private String[] MESSAGES = new String[] {
			"%d: Job %d submitted into queue %s",
			"%d: Job %d selected for execution at time %d after waiting %d",
			"%d: Job %d finished with exit status %d after executing for total of %d"
	};
	private class Output {
		private long time;
		private int id;
		private int type;
		private Object[] args;
		public Output(int type, Object ... args) {
			this.type = type;
			this.args = args;
			this.time = Long.parseLong(args[0].toString());
			this.id = Integer.parseInt(args[1].toString());
		}
		public String toString() {
			return String.format(MESSAGES[type],  args);
		}
	}
}
