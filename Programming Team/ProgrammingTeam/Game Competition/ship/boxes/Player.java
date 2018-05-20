package ship.boxes;

/**
 * An abstract player of the Dots-and-boxes game.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Player
{

	/**
	 * Returns the reference name of this player.
	 */
	public String getName();
	
	/**
	 * Given a field of lines and boxes return a position to which your player
	 * would like to place their next line. If a line already exists here or the
	 * Line returned is null then the player will lose their turn. When a line
	 * is placed on the field the field is automatically updated by capturing
	 * any appropriate boxes. If any boxes are captured by putting this line 
	 * down this player will get another turn (invokation of this method).
	 * 
	 * @param field The game Field.
	 * @return The line this player wants to place.
	 */
	public Line getLine(Field field);
	
}
