package shipgames.pongbasic;

/**
 * The Game State for the pong game.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum GameState 
{
	// The title is up.
	TitleScreen,
	// The ball is in motion.
	Playing,
	// The bars and ball stop
	Paused,
	// Waiting to reserve the ball.
	Waiting
}
