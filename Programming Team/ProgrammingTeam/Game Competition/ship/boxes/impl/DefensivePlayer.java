package ship.boxes.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ship.boxes.Box;
import ship.boxes.Field;
import ship.boxes.Line;
import ship.boxes.Player;
import ship.boxes.Side;

/**
 * A player which asses the potential damage of each line. If a line will result
 * in closing a box then that line is chosen. After that a line will be chosen
 * based on its likeliness to help the opponent.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DefensivePlayer implements Player
{

	/**
	 * A basic node (a line and its potential damage).
	 */
	private class Node implements Comparable<Node> 
	{
		private final Line line;
		private final int value;
		public Node(Line line, int value) {
			this.line = line;
			this.value = value;
		}
		public int compareTo(Node o) {
			return value - o.value;
		}
	}
	
	// The name of the basic player.
	public final String name;
	public final Random rnd = new Random();
	
	/**
	 * Instantiates a new Basic Player.
	 */
	public DefensivePlayer(String name)
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
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		// Vertical lines (left/right)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				append(nodes, field, x, y, Side.Left);
			}
			append(nodes, field, width - 1, y, Side.Right);
		}
		
		// Horizontal lines (top/bottom)
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				append(nodes, field, x, y, Side.Top);
			}
			append(nodes, field, x, height - 1, Side.Bottom);
		}

		// If nodes is empty (it should never be) return null
		if (nodes.isEmpty()) {
			return null;
		}
		
		// Sort nodes in ascending (by value).
		Collections.sort(nodes);
		
		// Count how many nodes exist with a value 3 (starting at last node)
		int end = nodes.size() - 1;
		int lastIndex = end;
		while (lastIndex > 0 && nodes.get(lastIndex).value == 3) {
			lastIndex--;
		}
		// If any have been found pick randomly from them
		if (lastIndex < end) {
			int count = end - lastIndex;
			int indexFromLast = rnd.nextInt(count);
			return nodes.get(end - indexFromLast).line;
		}
		
		// Pick from any of the nodes with the same value as the first
		Node first = nodes.get(0);
		int count = 1;
		while (count <= end && nodes.get(count).value == first.value) {
			count++;
		}
		int indexFromFirst = rnd.nextInt(count);
		return nodes.get(indexFromFirst).line;
	}

	/**
	 * Appends the line to the list of nodes if a line hasn't been placed there.
	 */
	private void append(ArrayList<Node> nodes, Field field, int x, int y, Side side)
	{
		Line line = new Line(x, y, side);
		Node node = getNode(line, field);
		if (node != null) {
			nodes.add(node);
		}
	}

	/**
	 * Gets a node for the given line. If the line is already on the field then
	 * null is returned. The value of the node is the maximum between the number
	 * of lines of each box on either side of the line.
	 */
	private Node getNode(Line line, Field field)
	{
		if (field.hasLine(line)) {
			return null;
		}
		int value = 0;

		Box before = field.getBefore(line);
		if (before != null) {
			value = Math.max(value, before.lines());
		}

		Box after = field.getAfter(line);
		if (after != null) {
			value = Math.max(value, after.lines());
		}
		
		return new Node(line, value);	
	}
	
}
