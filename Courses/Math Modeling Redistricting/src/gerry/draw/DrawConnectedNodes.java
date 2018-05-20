package gerry.draw;

import gerry.Node;
import gerry.Pixel;
import gerry.Viewport;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawConnectedNodes extends AbstractDrawer
{
	public static final int NEIGHBOR = 0;
	public static final int BACKGROUND = 1;
	
	private List<Node> nodes;
	
	public DrawConnectedNodes(List<Node> nodes) {
		super(2);
		this.nodes = nodes;
	}

	@Override
	public BufferedImage create(Viewport view) {
		BufferedImage img = new BufferedImage(view.width, view.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (hasColor(BACKGROUND)) {
			gr.setColor(getColor(BACKGROUND));
			gr.fillRect(0, 0, view.width, view.height);
		}
		gr.setColor(getColor(NEIGHBOR));
		for (Node n : nodes) {
			Pixel np = view.convert(n.city.x, n.city.y);
			for (Node m : n.neighbors) {
				Pixel mp = view.convert(m.city.x, m.city.y);
				gr.drawLine(np.x, np.y, mp.x, mp.y);
			}
		}
		gr.dispose();
		return img;
	}
	
	
}
