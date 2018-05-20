package axe.g2f.tile;

import axe.texture.SourceTile;
import axe.texture.Texture;


public class Tilesheet 
{
	
	private Texture texture;
	private SourceTile[] frames;
	
	public Tilesheet(Texture texture, int offsetx, int offsety, int frameWidth, int frameHeight, int columns, int frameCount) 
	{
		this.texture = texture;
		this.frames = new SourceTile[frameCount];
		for (int i = 0; i < frameCount; i++) {
			int x = ((i % columns) * frameWidth) + offsetx;
			int y = ((i / columns) * frameHeight) + offsety;
			frames[i] = texture.getTile(x, y, frameWidth, frameHeight);
		}
	}
	
	public SourceTile get(int index) {
		return frames[index];
	}
	
	public int size() {
		return frames.length;
	}
	
	public void bind() {
		texture.bind();
	}
	
	public Texture texture() {
		return texture;
	}
	
}