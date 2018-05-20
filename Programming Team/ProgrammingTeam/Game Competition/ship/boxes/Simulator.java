package ship.boxes;

import java.util.Random;


/**
 * A simulator for the Dots-and-Boxes game.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Simulator
{

	// A random number generator for determing the first player of each sim
	private final Random rnd = new Random();
	// The width of the simulation field (number of columns).
	private int width;
	// The height of the simulation field (number of rows).
	private int height;
	// The current simulation field.
	private FieldImpl field;
	// The set of player states.
	private final PlayerState[] states;
	
	/**
	 * Instantatiates a new Simulation given the fields dimensions and list
	 * of participating players.
	 * 
	 * @param fieldWidth The number of columns on the field.
	 * @param fieldHeight The number of rows on the field.
	 * @param players The participants.
	 */
	public Simulator(int fieldWidth, int fieldHeight, Player ... players) 
	{
		width = fieldWidth;
		height = fieldHeight;
		states = new PlayerState[players.length];
		for (int i = 0; i < players.length; i++) {
			states[i] = new PlayerState(players[i], i + 1);
		}
	}
	
	/**
	 * Runs the simulation a given number of times.
	 * 
	 * @param times The number of times to run the simulation.
	 */
	public void simulate(int times) 
	{
		int[] captured = new int[states.length];
		int[] moves = new int[states.length];
		int[] wins = new int[states.length];
		int[] ties = new int[states.length];
		int[] loses = new int[states.length];
		
		while (--times >= 0) 
		{
			// Do a single simulation
			simulate();
			
			// Tally up the number of captures and moves.
			for (int i = 0; i < states.length; i++) {
				captured[i] += states[i].captured;
				moves[i] += states[i].moves;
			}
			
			// Get the index of the winner, and how many people tied him. If no
			// ties existed then matches = 1.
			int winner = 0;
			int matches = 1;
			for (int i = 1; i < states.length; i++) {
				int d = states[i].captured - states[winner].captured;
				if (d > 0) {
					winner = i;
					matches = 1;
				}
				else if (d == 0) {
					matches++;
				}
			}
			
			// A single winner
			if (matches == 1) {
				// Tally winner...
				wins[winner]++;
				// For every other player tally loss
				for (int i = 0; i < states.length; i++) {
					if (i != winner) {
						loses[i]++;
					}
				}
			}
			// Ties exist
			else {
				// For each player either tally a tie or a loss.
				for (int i = 0; i < states.length; i++) {
					int d = states[i].captured - states[winner].captured;
					if (d == 0) {
						ties[i]++;
					}
					else {
						loses[i]++;
					}
				}
			}
		}
		
		// Print off each players statistics
		for (int i = 0; i < states.length; i++) {
			PlayerState state = states[i];
			System.out.format("%s\n\t", state.player.getName());
			System.out.format("Wins[%d] ", wins[i]);
			System.out.format("Loses[%d] ", loses[i]);
			System.out.format("Ties[%d] ", ties[i]);
			System.out.format("Capture%%[%.2f] ", (float)captured[i] / moves[i]);
			System.out.println();
		}
	}
	
	/**
	 * Runs a single simulation of the game.
	 */
	public void simulate()
	{
		int total = width * height;
		int next = rnd.nextInt(states.length);
		int captured = 0;

		// Create new field.
		field = new FieldImpl(width, height);
		
		// Reset player states
		for (PlayerState state : states) {
			state.clear();
		}
		
		// Go until all boxes are captured.
		while (captured < total) {
			captured += states[next].play(field);
//			System.out.println("BOARD:\n" + field);
			next = (next + 1) % states.length;
		}
	}
	
	/**
	 * Prints out the statistics of the last game.
	 */
	public void printStatistics()
	{
		PlayerState winner = states[0];
		for (PlayerState state : states) {
			if (state.captured > winner.captured) {
				winner = state;
			}
			System.out.format("%s: ", state.player.getName());
			System.out.format("captured[%d] ", state.captured);
			System.out.format("moves[%d] ", state.moves);
			System.out.format("invalid[%d] ", state.invalids);
			System.out.println();
		}
		System.out.format("Winner = %s\n", winner.player.getName());
	}
	
}
