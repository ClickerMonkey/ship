package ship.boxes.impl;

import ship.boxes.Player;
import ship.boxes.Simulator;


public class DotsAndBoxes
{

	public static void main(String[] args) 
	{
		final int WIDTH = 6;
		final int HEIGHT = 6;

		// NOTE! Any player after a RandomPlayer will always WIN!!
		final Player player1 = new DefensivePlayer("Defense1");
		final Player player2 = new DefensivePlayer("Defense2");
//		final Player player3 = new DefensivePlayer("Defense3");
//		final Player player4 = new RandomPlayer("Random");
		
		Simulator sims = new Simulator(WIDTH, HEIGHT, player1, player2/*, player3, player4*/);
		sims.simulate(1000);
	}
	
}
