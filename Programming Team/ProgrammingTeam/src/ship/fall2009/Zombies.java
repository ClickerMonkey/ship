package ship.fall2009;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Zombies
{

	/* INPUT
1 exit
0
1 fork
2 exit
0
1 fork
2 exit
1 wait
0
1 fork
1 wait
2 exit
0
1 fork
2 fork
2 fork
2 exit
4 exit
0
0


	 */
	
	public static void main(String[] args) {
		new Zombies();
	}
	
	Process[] processes;
	Process OS;			// The operating system process
	Process single;	// The initial single process
	int nextId;			// The next avaible ID 
	
	Zombies() {
		Scanner in = new Scanner(System.in);
		
		int process = in.nextInt();
		int cases = 1;
		// Case that starts with a 0 is the end of input
		while (process != 0) {
			
			ArrayList<Integer> running = new ArrayList<Integer>();
			ArrayList<Integer> zombie = new ArrayList<Integer>();
			ArrayList<Integer> waiting = new ArrayList<Integer>();
			
			OS = new Process(0, null);
			single = new Process(1, OS);
			
			processes = new Process[1000];
			processes[0] = OS;
			processes[1] = single;
			nextId = 2;
			
			running.add(1);
			
			// Command that starts with 0 is the end of the case
			while (process != 0) {
				// Read the command {fork, exit, wait}
				String command = in.next();
				
				// Fork a child from the current process
				if (command.equals("fork")) {
					
					// Get the parent process and create the child
					Process parent = processes[process];
					Process child = new Process(nextId, parent);
					
					// Add the child to the parent, processes array, and running processes
					parent.children.add(child);
					processes[nextId] = child;
					running.add(nextId);
					
					// Increment process Id
					nextId++;
					
				} 
				// Exit the current process
				else if (command.equals("exit")) {
					
					// Get the current process and the parent
					Process current = processes[process];
					Process parent = current.parent;
					
					// Is the parent waiting for an exit?
					if (parent.waiting) {
						
						// Remove from running
						running.remove(new Integer(process));
						// Toggle parent waiting
						parent.waiting = false;
						waiting.remove(new Integer(parent.id));
						
					} else {
						
						// Remove from running
						running.remove(new Integer(process));

						// If parent is running but NOT waiting then its a zombie!
						if (running.contains(parent.id)) {
							zombie.add(process);	
						}
					}
					
					// If any children are zombies then reap them
					for (Process child : current.children) {
						zombie.remove(new Integer(child.id));
					}
				} 
				// Wait the current process
				else if (command.equals("wait")) {
					
					// Get the current process and the parent
					Process current = processes[process];
					boolean childFound = false;
					
					// Check if any children are zombie
					for (Process child : current.children) {
						// If this child is a zombie, remove it and break
						if (zombie.remove(new Integer(child.id))) {
							childFound = true;
							break;
						}
					}
					
					// If no children were zombies then add to waiting
					if (!childFound) {
						current.waiting = true;
						waiting.add(process);
					}
				}
				
				process = in.nextInt();
			}
			
			// Sort all lists by ID
			Collections.sort(running);
			Collections.sort(zombie);
			Collections.sort(waiting);
			
			int runningCount = running.size();
			int zombieCount = zombie.size();
			int waitingCount = waiting.size();
			
			System.out.format("Case %d:\n", cases);
			// If no processes exist, SAY IT!
			if (runningCount == 0 && zombieCount == 0 && waitingCount == 0) {
				System.out.println("\tNo processes.");
			}
			else {
				
				// Print out all running processes
				System.out.format("\t%d Running: ", runningCount);
				if (runningCount > 0) {
					System.out.print(running.get(0));
					for (int i = 1; i < runningCount; i++)
						System.out.print(", " + running.get(i));
				}
				System.out.println();

				// Print out all zombie processes
				System.out.format("\t%d Zombie: ", zombieCount);
				if (zombieCount > 0) {
					System.out.print(zombie.get(0));
					for (int i = 1; i < zombieCount; i++)
						System.out.print(", " + zombie.get(i));
				}
				System.out.println();

				// Print out all waiting processes
				System.out.format("\t%d Waiting: ", waitingCount);
				if (waitingCount > 0) {
					System.out.print(waiting.get(0));
					for (int i = 1; i < waitingCount; i++)
						System.out.print(", " + waiting.get(i));
				}
				System.out.println();
			}
			System.out.println();
			
			process = in.nextInt();
			cases++;
		}
	}
	
	// Hold ID, parent, all children, and if the process is waiting for a child
	// to exit.
	class Process {
		int id;
		Process parent;
		ArrayList<Process> children;
		boolean waiting;
		
		Process(int id, Process parent) {
			this.id = id;
			this.parent = parent;
			this.children = new ArrayList<Process>();
		}
	}
	
}
