package ship.battle;

import java.util.Random;
import java.util.Vector;


public class SuperSimulator 
{

	public static final int PLAYER_COUNT = 2;

	private int width;
	private int height;
	private int ties;
	private int fullgames;
	private int[] wins;
	private int[] losses;
	private int[] forfeits;
	private float[][] accurracy;
	private PlayerFactory[] factory;
	private PlayerState[] players;
	
	public SuperSimulator(int width, int height, PlayerFactory factory1, PlayerFactory factory2)
	{
		this.width = width;
		this.height = height;
		this.wins = new int[PLAYER_COUNT];
		this.losses = new int[PLAYER_COUNT];
		this.forfeits = new int[PLAYER_COUNT];
		this.players = new PlayerState[PLAYER_COUNT];
		this.factory = new PlayerFactory[PLAYER_COUNT];
		this.factory[0] = factory1;
		this.factory[1] = factory2;
	}

	// Runs a simulation x number of times, use a random generator to determine
	// first move?
	public void simulate(int times, boolean random) 
	{
		accurracy = new float[PLAYER_COUNT][times];
		
		Random rnd = new Random();
		int value = 0;
		while (--times >= 0) {
			if (!random) {
				value = (value + 1) % PLAYER_COUNT;
			} else {
				value = rnd.nextInt(PLAYER_COUNT);
			}
			simulate(value % PLAYER_COUNT);
		}
	}
	
	private class Statistic {
		public double min, max, sum, mean, stddev, variance;
		public Statistic(float[] values, int total) {
			min = Double.MAX_VALUE;
			max = -Double.MAX_VALUE;
			for (int i = 0; i < total; i++) {
				sum += values[i];
				min = Math.min(min, values[i]);
				max = Math.max(max, values[i]);
			}
			mean = sum / total;
			for (int i = 0; i < total; i++) {
				variance += (mean - values[i]) * (mean - values[i]);
			}
			variance /= total;
			stddev = Math.sqrt(variance);
		}
	}
	
	// Prints out statistics for a set of simulations.
	public void printStatistics()
	{
		System.out.format("Full games: %d\n", fullgames);
		System.out.format("Ties: %d\n", ties);

		Statistic stat0 = new Statistic(accurracy[0], fullgames);
		System.out.format("Player [%s]\n", players[0].name);
		System.out.format("\tWins[%d] Losses[%d] Forfeits[%d] ", 
				wins[0], losses[0], forfeits[0]);
		System.out.format("Accurracy[%.2f%%] Stddev[%.2f%%] Max[%.2f%%] Min[%.2f%%]\n",
				stat0.mean * 100.0, stat0.stddev * 100.0, stat0.max * 100.0, stat0.min * 100.0);
			
		Statistic stat1 = new Statistic(accurracy[1], fullgames);
		System.out.format("Player [%s]\n", players[1].name);
		System.out.format("\tWins[%d] Losses[%d] Forfeits[%d] ", 
				wins[1], losses[1], forfeits[1]);
		System.out.format("Accurracy[%.2f%%] Stddev[%.2f%%] Max[%.2f%%] Min[%.2f%%]\n",
				stat1.mean * 100.0, stat1.stddev * 100.0, stat1.max * 100.0, stat1.min * 100.0);
	}
	
	public void simulate(int curr) 
	{
		players[0] = new PlayerState(width, height, factory[0].createPlayer());
		players[1] = new PlayerState(width, height, factory[1].createPlayer());
		
		boolean init1 = players[0].initialize();
		boolean init2 = players[1].initialize();

		if (init1 && init2) {
			start(curr);
		}
		else if (!init1 && !init2) {
			ties++;
		}
		else if (!init1) {
			forfeits[0]++;
			wins[1]++;
		}
		else if (!init2) {
			forfeits[1]++;
			wins[0]++;
		}
	}

	private void start(int firstPlayer)
	{
		int curr = firstPlayer;
		int next = (curr + 1) % PLAYER_COUNT;
		while (!players[next].isDead()) 
		{
			Board opponent = players[next].board;
			Vector<Move> moves = new Vector<Move>();
			moves.addAll(players[curr].moves);
			Vector<Ship> sunk = new Vector<Ship>();
			sunk.addAll(players[curr].sunkShips);

			Coord bombed = players[curr].player.bomb(opponent, moves, sunk);
			if (bombed != null) {
				if (opponent.onField(bombed.x, bombed.y)) {
					State previous = opponent.getState(bombed.x, bombed.y);
					if (previous == State.Empty)
					{
						Ship hit = opponent.getShip(bombed.x, bombed.y);
						State newState = null;
						if (hit == null) {
							newState = State.Miss;
						}
						else {
							opponent.setState(bombed.x, bombed.y, State.Hit);
							if (opponent.isSunk(hit)) {
								opponent.sink(hit);
								players[curr].sunkShips.add(hit);
								newState = State.Sunk;
							}
							else {
								newState = State.Hit;
							}
						}
						opponent.setState(bombed.x, bombed.y, newState);
						players[curr].moves.add(new Move(bombed, newState));
					}
				}
			}
			curr = next;
			next = (next + 1) % PLAYER_COUNT;
		}

		losses[next]++;
		accurracy[next][fullgames] = getAccurracy(players[next], players[curr]);
		
		wins[curr]++;
		accurracy[curr][fullgames] = getAccurracy(players[curr], players[next]);
		
		fullgames++;
	}
	
	private float getAccurracy(PlayerState player, PlayerState opponent) 
	{
		float hits = opponent.board.getStateCount(State.Hit) + 
						 opponent.board.getStateCount(State.Sunk);
		float miss = opponent.board.getStateCount(State.Miss);
		return hits / (hits + miss);
	}

}