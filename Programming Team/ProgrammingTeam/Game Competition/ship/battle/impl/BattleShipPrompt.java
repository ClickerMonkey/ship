package ship.battle.impl;

import ship.battle.Player;
import ship.battle.Simulator;


public class BattleShipPrompt
{

	public static void main(String[] args) {
		Player player1 = new PromptPlayer();
		Player player2 = new Terminator();
		Simulator sims = new Simulator(10, 10, player1, player2);
		sims.simulate();
	}
	
}
