package gerry.anim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gerry.Box;
import gerry.Pixel;
import gerry.Viewport;

public class Display extends JPanel
{
	
	public static Display create(Viewport view, Color fill) {
		Display display = new Display(view);
		display.start(fill);
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(display);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		return display;
	}
	

	private Viewport view;
	private BufferedImage buffer;
	private Graphics2D graphics;
	private Rectangle dirty;
	private boolean clean;
	
	public Display(Viewport view) {
		this.view = view;
		this.setPreferredSize(new Dimension(view.width, view.height));
		this.setSize(getPreferredSize());
		this.setDoubleBuffered(true);
	}
	
	public Viewport getView() {
		return view;
	}
	
	public void start(Color fill) {
		buffer = new BufferedImage(view.width, view.height, BufferedImage.TYPE_INT_BGR);
		graphics = buffer.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(fill);
		graphics.fillRect(0, 0, view.width, view.height);
		dirty = new Rectangle();
		clean = false;
	}
	
	public void clear(Color fill) {
		if (graphics != null) {
			graphics.setColor(fill);
			graphics.fillRect(0, 0, view.width, view.height);
			invalidate();
		}
	}
	
	public final void update(Graphics g) { 
		paint(g);
	}

	public final void paint(Graphics g) {
		if (g == null || buffer == null) {
			return;
		}
		g.drawImage(buffer, 0, 0, this);
		g.dispose();
	}
	
	private void clean() {
		if (clean) {
			Graphics gr = getGraphics();
			int l = dirty.x - 1;
			int r = l + dirty.width + 2;
			int t = dirty.y - 1;
			int b = t + dirty.height + 2;
			gr.drawImage(buffer, l, t, r, b, l, t, r, b, null);
			clean = false;
		}
	}
	
	private void dirty(int x0, int y0, int x1, int y1) {
		clean = true;
		dirty.x = Math.min(x0, x1);
		dirty.y = Math.min(y0, y1);
		dirty.width = Math.abs(x1 - x0);
		dirty.height = Math.abs(y1 - y0);
	}
	
	private void refresh(int x0, int y0, int x1, int y1) {
		dirty(x0, y0, x1, y1);
		clean();
	}
	
	public void setLine(float x0, float y0, float x1, float y1, Color outline) {
		clean();
		Pixel p0 = view.convert(x0, y0);
		Pixel p1 = view.convert(x1, y1);
		graphics.setColor(outline);
		graphics.drawLine(p0.x, p0.y, p1.x, p1.y);
		refresh(p0.x, p0.y, p1.x, p1.y);
	}
	
	public void setPath(GeneralPath path, Color fill, Color outline) {
		clean();
		if (fill != null) {
			graphics.setColor(fill);
			graphics.fill(path);
		}
		if (outline != null) {
			graphics.setColor(outline);
			graphics.draw(path);
		}
		Rectangle bounds = path.getBounds();
		refresh(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
	}
	
	public void showLine(float x0, float y0, float x1, float y1, Color outline) {
		clean();
		Pixel p0 = view.convert(x0, y0);
		Pixel p1 = view.convert(x1, y1);
		Graphics gr = getGraphics();
		gr.setColor(outline);
		gr.drawLine(p0.x, p0.y, p1.x, p1.y);
		gr.dispose();
		dirty(p0.x, p0.y, p1.x, p1.y);
	}
	
	public void showPath(GeneralPath path, Color fill, Color outline) {
		clean();
		Graphics2D gr = (Graphics2D)getGraphics();
		if (fill != null) {
			gr.setColor(fill);
			gr.fill(path);
		}
		if (outline != null) {
			gr.setColor(outline);
			gr.draw(path);
		}
		gr.dispose();
		Rectangle bounds = path.getBounds();
		dirty(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
	}
	

	public static void main(String[] args) {
		Box bounds = new Box(0, 0);
		bounds.include(-10, -10);
		bounds.include(10, 10);
		
		Viewport view = Viewport.fromWidth(bounds, 300);
		
		Display display = new Display(view);
		display.start(Color.black);
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(display);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		try {
			Thread.sleep(1000);
			display.setLine(-5, 0, 5, 2, Color.red);
//			Thread.sleep(1000);
			display.showLine(1, 5, 2, -5, Color.blue);
//			Thread.sleep(1000);
			display.showLine(-1, 5, -2, -5, Color.green);
//			Thread.sleep(1000);
			display.setLine(2, 5, 3, -5, Color.white);
		}
		catch (Exception e) {
		}
	}
	
	
	
}
