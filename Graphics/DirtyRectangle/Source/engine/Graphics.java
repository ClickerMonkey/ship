package engine;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Graphics {

//	private Component parent;
	
	private TileSource tile;
	private Graphics2D gr;
	
	public Graphics(Component parent) {
//		this.parent = parent;
		this.gr = (Graphics2D)parent.getGraphics();
	}
	
	public void setTile(TileSource source) {
		this.tile = source;
	}
	
	public void draw(Rectangle bounds) {
		
		if (tile == null || bounds == null) {
			return;
		}
		
		BufferedImage texture = tile.getTexture();
		Rectangle source = tile.getSource();
		
		if (texture == null || source == null) {
			return;
		}
		
		gr.drawImage(texture,
				bounds.left, bounds.top, bounds.right, bounds.bottom, 
				source.left, source.top, source.right, source.bottom, null);
	}
	
	public void setClip(Rectangle clip) {
		gr.setClip(clip.left, clip.top, clip.width(), clip.height());
	}

	public Graphics2D getInternal() {
		return gr;
	}
	
}
