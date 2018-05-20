package model3d;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class Canvas {

	private final int width;
	private final int height;
	private final int[] pixels;
	private final float[] depth;
	private final Image image;
	private final MemoryImageSource source;

	private int color;
	private int background;
	
	private final LineDrawer lines;
	private final PolygonDrawer polys;

	public Canvas(Applet applet) {
		
		this.width = applet.getWidth();
		this.height = applet.getHeight();
		this.pixels = new int[width * height];
		this.depth = new float[width * height];
		this.source = new MemoryImageSource(width, height, pixels, 0, width);
		this.source.setAnimated(true);
		this.image = applet.createImage(source);
		
		this.lines = new LineDrawer(this);
		this.polys = new PolygonDrawer(this);
	}
	
	public void invalidate() {
		source.newPixels(0, 0, width, height);
	}

	public void renderTo(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	public void setColor(int r, int g, int b, int a) {
		color = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);  
	}
	public void setColor(int r, int g, int b) {
		color = 0xFF000000 | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	public void setColor(int color) {
		this.color = color;
	}

	public void setBackground(int r, int g, int b, int a) {
		background = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);  
	}
	public void setBackground(int r, int g, int b) {
		background = 0xFF000000 | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	public void setBackground(int color) {
		this.background = color;
	}
	
	public int getColor() {
		return color;
	}

	public void drawPixel(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		pixels[index(x, y)] = color;
	}
	protected void drawPixel(int index) {
		pixels[index] = color;
	}
	public void drawPixel(Vector v) {
		int x = (int)v.x;
		int y = (int)v.y;
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		int i = index(x, y);
		if (v.z > depth[i]) {
			pixels[i] = color;
			depth[i] = v.z;
		}
	}
	
	public void drawScanline(int y, int x1, int x2) {
		lines.drawLineHorizontal(y, x1, x2, Integer.signum(x2 - x1));
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		lines.drawLine(x1, y1, x2, y2);
	}

	public void fillRect(int x, int y, int width, int height) {
		int point = index(x, y);
		while (--height >= 0) {
			for (int i = 0; i < width; i++) {
				drawPixel(point + i);
			}
			point += this.width;
		}
	}
	public void drawRect(int x, int y, int width, int height) {
		int right = x + width;
		int bottom = y + height;
		lines.drawLineHorizontal(y, x, right, 1);
		lines.drawLineVertical(x, y + 1, bottom - 1, 1);
		lines.drawLineVertical(right, y + 1, bottom - 1, 1);
		lines.drawLineHorizontal(bottom, x, right, 1);
	}
	
	public void fillPolygon(int[] x, int[] y) {
		polys.fillPolygon(x, y);
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = background;
		}
	}
	
	public int index(int x, int y) {
		return (y * width) + x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	
}
