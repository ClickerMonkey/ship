package engine;

import java.awt.image.BufferedImage;

public class Tile implements TileSource {

	private BufferedImage texture;
	private Rectangle source;
	
	public Tile(BufferedImage texture, int x, int y, int width, int height) {
		this(texture, new Rectangle(x, y, x + width, y + width));
	}
	
	public Tile(BufferedImage texture, Rectangle source) { 
		this.texture = texture;
		this.source = source;
	}
	
	public Rectangle getSource() {
		return source;
	}

	public BufferedImage getTexture() {
		return texture;
	}

}
