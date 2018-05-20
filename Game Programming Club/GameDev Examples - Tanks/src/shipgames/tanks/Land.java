package shipgames.tanks;

import shipgames.Util;

import java.awt.Graphics2D;


public class Land 
{

	private int width;
	private int height;
	private int maxHeight;
	private int[] land;
	
	public Land(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.land = new int[width];
	}
	
	public void generate()
	{
		int start = Util.random(height / 4, height / 2);
		int x = 0;
		maxHeight = 0;
		land[0] = start;
		while (x < width)
		{
			int wave = Util.random(5, 20);
			int freq = Util.random(-15, 15);

			if (land[x] > maxHeight)
				maxHeight = land[x];
			
			double delta;
			for (int w = 0; w < wave && x + w < width; w++)
			{
				delta = (double)w / wave;
				land[x + w] = land[x] + (int)(delta * freq); 	
			}
			
			x += wave - 1;
		}
	}
	
	public void draw(Graphics2D gr)
	{
		for (int x = 0; x < width; x++)
			gr.drawLine(x, 0, x, land[x]);
	}
	
	public boolean intersects(int x, int y)
	{
		// If the x is past the left side then
		if (x < 0) x = 0;
		if (x >= width) x = width - 1;
		
		return (y < land[x]);
	}
	
	public void remove(int x, int l, int radius)
	{
		final float DEPTH_SCALE = 0.7f;
		// The index of the current land strip
		// to remove 'land' from.
		int index;
		// The half height of the circle
		int height;
		// Radius squared
		int radius2 = radius * radius;
		// The y coordinate
		int y = land[(x < 0 ? 0 : (x >= width ? width - 1 : x))];
		// 
		if (x >= 0 && x < width)
			land[x] = Math.min(land[x], (int)(land[x] - radius * DEPTH_SCALE));
			     
		for (int i = 1; i < radius; i++)
		{
			height = (int)(Math.sqrt(radius2 - i * i) * DEPTH_SCALE);

			// The left side of the circle
			index = x + i;
			if (index >= 0 && index < width)
				land[index] = Math.min(land[index], y - height);
			// The right side of the circle
			index = x - i;
			if (index >= 0 && index < width)
				land[index] = Math.min(land[index], y - height);
		}
	}

	public int getY(int x)
	{
		return land[x];
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
}
