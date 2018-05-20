package axe.g2f.tile;

import axe.texture.SourceTile;

public class Tileset 
{

	private int tileCount;
	private SourceTile[] tiles;
	
	public Tileset(int capacity) {
		tiles = new SourceTile[capacity];
	}
	
	public void add(Tilesheet sheet) {
		for (int i = 0; i < sheet.size(); i++) {
			tiles[tileCount++] = sheet.get(i);
		}
	}

	public void add(Tilesheet sheet, int min, int max) {
		for (int i = min; i <= max; i++) {
			tiles[tileCount++] = sheet.get(i);
		}
	}
	
	public void add(Tilesheet sheet, int ... indices) {
		for (int i = 0; i < indices.length; i++) {
			tiles[tileCount++] = sheet.get(indices[i]);
		}
	}
	
	public SourceTile get(int index) {
		return tiles[index];
	}
	
	public int size() {
		return tileCount;
	}
	
}
