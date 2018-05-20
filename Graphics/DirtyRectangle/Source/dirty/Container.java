package dirty;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Container extends JPanel {

	
	private DirtyRectangles rectangles;
	private Canvas[] canvas;
	
	
	public void dirty(Rectangle dirty) {
		rectangles.invalidate(dirty);
	}
	
	public void paint(Graphics g) {
		Graphics2D gr = (Graphics2D)g;
		rectangles.redraw(gr, canvas);
	}
	
	
}
