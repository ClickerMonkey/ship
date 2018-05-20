package model.util;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class Canvas {

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int[] buffer;
	private final Image image;
	private final MemoryImageSource source;

	private int color;
	private int background;
	
	private final LineDrawer lines;
	private final PolygonDrawer polys;

	public Canvas(Applet applet, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buffer = new int[width * height];
		this.source = new MemoryImageSource(width, height, buffer, 0, width);
		this.source.setAnimated(true);
		this.image = applet.createImage(source);
		
		this.lines = new LineDrawer(this);
		this.polys = new PolygonDrawer(this);
	}
	
	public void invalidate() {
		source.newPixels(0, 0, width, height);
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
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
		buffer[index(x, y)] = color;
	}
	public void drawPixel(int index) {
		buffer[index] = color;
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
		lines.drawLineHorizontal(height, x, right, 1);
	}
	
	public void fillPolygon(int[] x, int[] y) {
		polys.fillPolygon(x, y);
	}
	
	public void clear() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = background;
		}
	}
	
	public int index(int x, int y) {
		return (y * width) + x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
