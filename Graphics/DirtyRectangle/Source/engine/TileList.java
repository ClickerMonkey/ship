package engine;

import java.awt.image.BufferedImage;

public class TileList implements TileSource {

	private Tile[] tiles;
	private int[] order;
	private int current;
	
	public Tile getTile(int index) {
		return tiles[order[index]];
	}
	
	public void setTile(int index) {
		current = index;
	}
	
	public int size() {
		return order.length;
	}
	
	public Rectangle getSource() {
		return getTile(current).getSource();
	}

	public BufferedImage getTexture() {
		return getTile(current).getTexture();
	}

}
