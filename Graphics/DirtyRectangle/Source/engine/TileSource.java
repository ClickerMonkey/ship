package engine;

import java.awt.image.BufferedImage;

public interface TileSource {
	public Rectangle getSource();
	public BufferedImage getTexture();
}