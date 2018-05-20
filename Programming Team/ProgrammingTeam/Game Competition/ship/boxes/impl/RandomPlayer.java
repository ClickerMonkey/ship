package ship.boxes.impl;

import java.util.ArrayList;
import java.util.Random;

import ship.boxes.Field;
import ship.boxes.Line;
import ship.boxes.Player;
import ship.boxes.Side;

/**
 * A player which chooses a random line on the field.
 * 
 * @author Philip Diffenderfer
 *
 */
public class RandomPlayer implements Player
{

	// The name of the basic player.
	public final String name;
	public final Random rnd = new Random();
	
	/**
	 * Instantiates a new Basic Player.
	 */
	public RandomPlayer(String name)
	{
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Line getLine(Field field)
	{
		int width = field.getWidth();
		int height = field.getHeight();
		
		// A set of lines which are options.
		ArrayList<Line> lines = new ArrayList<Line>();
		Line line;
		
		// Vertical lines (left/right)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				line = new Line(x, y, Side.Left);
				if (!field.hasLine(line)) {
					lines.add(line);
				}
			}
			line = new Line(width - 1, y, Side.Right);
			if (!field.hasLine(line)) {
				lines.add(line);
			}
		}
		
		// Horizontal lines (top/bottom)
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				line = new Line(x, y, Side.Top);
				if (!field.hasLine(line)) {
					lines.add(line);
				}
			}
			line = new Line(x, height - 1, Side.Bottom);
			if (!field.hasLine(line)) {
				lines.add(line);
			}
		}

		// If nodes is empty (it should never be) return null
		if (lines.isEmpty()) {
			return null;
		}

		// Get random line.
		return lines.get(rnd.nextInt(lines.size()));
	}

}
