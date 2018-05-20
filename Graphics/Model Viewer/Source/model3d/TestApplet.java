package model3d;

import java.util.Random;

public class TestApplet extends CanvasApplet 
{
	class Line {
		int[] x;
		int[] y;
		Line(int x1, int y1, int x2, int y2) {
			x = new int[] {x1, x2};
			y = new int[] {y1, y2};
		}
		Line clip(Clipper clipper) {
			Line clipped = new Line(x[0], y[0], x[1], y[1]);
			if (!clipper.clipLine(clipped.x, clipped.y)) {
				return null;
			}
			return clipped;
		}
		void draw(Canvas canvas) {
			canvas.drawLine(x[0], y[0], x[1], y[1]);
		}
	}
	
	private int x, y, width, height;
	private Clipper clipper;
	private Line[] lines;
	
	@Override
	public void initApplet(Canvas canvas) {
		canvas.setBackground(0, 0, 0);

		x = 50;
		y = 50;
		width = canvas.getWidth() - 100;
		height = canvas.getHeight() - 100;
		
		clipper = new Clipper();
		clipper.setClipRect(x, y, width, height);
		
		Random rnd = new Random();
		int lineCount = 20;
		lines = new Line[lineCount];
		while (--lineCount >= 0) {
			int x1 = rnd.nextInt(getWidth());
			int y1 = rnd.nextInt(getHeight());
			int x2 = rnd.nextInt(getWidth());
			int y2 = rnd.nextInt(getHeight());
			lines[lineCount] = new Line(x1, y1, x2, y2);
		}
	}
	
	@Override
	public void drawApplet(Canvas canvas) {
		canvas.clear();

		canvas.setColor(0, 255, 0);
		for (Line l : lines) {
			l.draw(canvas);
		}

		canvas.setColor(0, 0, 0);
		canvas.fillRect(x, y, width, height);
		
		canvas.setColor(255, 255, 255);
		canvas.drawRect(x, y, width, height);
		
		for (Line l : lines) {
			Line clipped = l.clip(clipper);
			if (clipped != null) {
				canvas.setColor(255, 0, 0);
				clipped.draw(canvas);
			} else {
				canvas.setColor(0, 0, 255);
				l.draw(canvas);
			}
		}
		
		dirty();
	}


}
