package ship.battle;

import java.util.Random;
import java.util.Vector;


public class Simulator 
{

	public static final int PLAYER_COUNT = 2;
	private PlayerState[] players;

	public Simulator(int width, int height, Player player1, Player player2)
	{
		this.players = new PlayerState[PLAYER_COUNT];
		this.players[0] = new PlayerState(width, height, player1);
		this.players[1] = new PlayerState(width, height, player2);
	}

	public void simulate() 
	{
		boolean init1 = players[0].initialize();
		boolean init2 = players[1].initialize();

		if (init1 && init2)
		{
			start();
		}
		else if (!init1 && !init2)
		{
			System.out.println("Both players failed to initialize, Tie!");
		}
		else if (!init1)
		{
			System.out.format("%s wins by default!\n", players[0].name);
		}
		else if (!init2)
		{
			System.out.format("%s wins by default!\n", players[0].name);
		}
	}

	private void start()
	{
		// The index of the first player to start with.
		int curr = Math.abs(new Random().nextInt() % PLAYER_COUNT);
		// The index of the next player.
		int next = (curr + 1) % PLAYER_COUNT;

		// Who's playing first?
		System.out.format("%s is making the first move!\n", players[curr].name);

		// Loop until the next player is dead
		while (!players[next].isDead()) 
		{
			// Setup next move
			Board opponent = players[next].board;
			Vector<Move> moves = new Vector<Move>();
			moves.addAll(players[curr].moves);
			Vector<Ship> sunk = new Vector<Ship>();
			sunk.addAll(players[curr].sunkShips);

			// Make move
			Coord bombed = players[curr].player.bomb(opponent, moves, sunk);

			// We need a non-null coordinate.
			if (bombed != null) {
				// The coordinate must exist on the board.
				if (opponent.onField(bombed.x, bombed.y)) {
					// Get the previous state
					State previous = opponent.getState(bombed.x, bombed.y);
					// Only proceed if the cell is empty
					if (previous == State.Empty)
					{
						// Get the ship hit (if any)
						Ship hit = opponent.getShip(bombed.x, bombed.y);
						State newState = null;
						String message = null;

						if (hit == null) {
							newState = State.Miss;
							message = players[curr].player.getMissMessage(bombed);
						}
						else {
							opponent.setState(bombed.x, bombed.y, State.Hit);

							if (opponent.isSunk(hit)) {
								// Sink the ship.
								if (!opponent.sink(hit)) {
									System.out.println("Error");
									System.exit(0);
								}
								// Add the sunk ship to the current player
								players[curr].sunkShips.add(hit);
								newState = State.Sunk;
								message = players[curr].player.getSunkMessage(hit.type);
							}
							else {
								newState = State.Hit;
								message = players[curr].player.getHitMessage(bombed, hit.type);
							}
						}

						// Update the state on the board
						opponent.setState(bombed.x, bombed.y, newState);

						// Add move to the users stack
						players[curr].moves.add(new Move(bombed, newState));

						// Print out the message
						if (message != null) {
							System.out.format("%s: %s\n", players[curr].name, message);	
						}
					}
				}
			}

			// Next player
			curr = next;
			next = (next + 1) % PLAYER_COUNT;
		}

		System.out.format("%s lost.\n", players[next].name);
		printStats(players[next], players[curr]);
		
		System.out.format("%s won!\n", players[curr].name);
		printStats(players[curr], players[next]);
	}
	
	private void printStats(PlayerState player, PlayerState opponent) 
	{
		int moves = player.moves.size();
		int hits = opponent.board.getStateCount(State.Hit) + opponent.board.getStateCount(State.Sunk);
		int sinks = player.sunkShips.size();
		int miss = opponent.board.getStateCount(State.Miss);
		int total = hits + sinks + miss;
		
		System.out.format("Moves[%d] Hits[%d] SinkenShips[%d] Misses[%d]\n", moves, hits, sinks, miss);
		System.out.format("Accurracy: %.2f%%\n", (double)hits / total * 100.0);
		System.out.println(opponent.board);
	}

}