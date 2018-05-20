package gerry.draw;

import gerry.Viewport;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class AbstractDrawer implements Drawer {

	private Viewport view;
	private Color[] colors;
	
	protected AbstractDrawer(int colorCount) {
		this.colors = new Color[colorCount];
	}
	
	@Override
	public BufferedImage create() {
		return create(view);
	}

	@Override
	public void save(File location) throws IOException {
		ImageIO.write(create(), getExtension(location), location);
	}

	private String getExtension(File location) {
		String path = location.getAbsolutePath().toUpperCase();
		return path.substring(path.lastIndexOf('.') + 1);
	}

	@Override
	public Color getColor(int x) {
		return colors[x];
	}
	
	@Override
	public void setColor(int x, Color c) {
		colors[x] = c;
	}

	@Override
	public boolean hasColor(int x) {
		return !(x < 0 || x >= colors.length) && (colors[x] != null);
	}
	
	@Override
	public Viewport getView() {
		return view;
	}

	@Override
	public void setViewport(Viewport view) {
		this.view = view;
	}

}
