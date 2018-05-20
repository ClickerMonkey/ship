package ship.battle.impl;

import ship.battle.Player;
import ship.battle.PlayerFactory;
import ship.battle.SuperSimulator;


public class BattleShip
{
	
	public static void main(String[] args) 
	{
//		final Player player1 = new PhilsPlayer();
////		final Player player2 = new RandomPlayer("Loser");
////		final Player player2 = new PhilsOtherPlayer();
//		final Player player2 = new PhilsBestPlayer();
//		final int width = 10;
//		final int height = 10;
//		
//		new Simulator(width, height, player1, player2).simulate();

		final PlayerFactory factory1 = new PlayerFactory() {
			public Player createPlayer() {
//				return new RandomPlayer("Random");
				return new SkyNetPlayer();
//				return new PhilsOtherPlayer();
//				return new Terminator();
			}
		};
		
		final PlayerFactory factory2 = new PlayerFactory() {
			public Player createPlayer() {
				return new Terminator();
			}
		};
		
		final int width = 10;
		final int height = 10;
		
		final SuperSimulator simulator = new SuperSimulator(width, height, factory1, factory2);
		simulator.simulate(10000, false);
		simulator.printStatistics();
	}

}
