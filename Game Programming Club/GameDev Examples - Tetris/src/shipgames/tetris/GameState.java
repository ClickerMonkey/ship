package shipgames.tetris;

/**
 * The different game states in a game.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum GameState 
{
	// The title is up and the title screen music is playing.
	TitleScreen,
	// Blocks are falling and the user can manuever it.
	Playing,
	// The movement of the falling block ceases.
	Paused,
	// The user has lost the game and the blocks are filling up the screen.
	GameOver	
}
