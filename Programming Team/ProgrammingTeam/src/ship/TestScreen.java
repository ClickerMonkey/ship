package ship;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputListener;

public class TestScreen extends Screen implements ScreenListener, KeyListener, MouseInputListener, MouseWheelListener
{

	public static void main(String[] args) {
		showWindow(new TestScreen(), "Test Screen");
	}

	//===========================================================================
	// Input

	class MouseState {
		int x0, y0;				// The current location of the mouse
		int x1, y1;				// The previous location of the mouse
		int dx, dy;				// The difference between the previous and current location of the mouse
		int wheel;				// Returns the direction of the wheel
		int focusX, focusY;	// This point is saved when the right button the mouse is pressed.
		boolean leftDown0, rightDown0;	// The current state of the mouse buttons
		boolean leftDown1, rightDown1;	// The previous state of the mouse buttons
		MouseEvent event;		// The last event passed to the state
		// Initializes a new MouseState.
		public MouseState() {
		}
		// Sets the event updating the current and previous location of the mouse
		// and saving the previous state of the mouse buttons.
		public void setEvent(MouseEvent e) {
			event = e;
			x1 = x0;
			y1 = y0;
			x0 = e.getX();
			y0 = e.getY();
			dx = x1 - x0;
			dy = y1 - y0;
			leftDown1 = leftDown0;
			rightDown1 = rightDown0;
		}
		// Saves the current mouse location as a focus point
		public void focus() {
			focusX = x0;
			focusY = y0;
		}
		// Returns whether the left mouse button has changed state since the last
		// event. The current state of the mouse is leftDown0
		public boolean leftChanged() {
			return leftDown0 != leftDown1;
		}
		// Returns whether the right mouse button has changed state since the last
		// event. The current state of the mouse is rightDown0
		public boolean rightChanged() {
			return rightDown0 != rightDown1;
		}
		// Returns if atleast one of the mouse buttons is down
		public boolean isButtonDown() {
			return leftDown0 || rightDown0;
		}
		// Returns if the mouse is dragging (holding a button down and moving)
		public boolean isDragging() {
			return isButtonDown() && (dx != 0 || dy != 0);
		}
	}

	//===========================================================================
	// GUI Components
	class Location {
		int x, y;
		Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	class Size {
		int width;
		int height;
		Size(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}
	class Rectangle {
		int left, top, right, bottom;
		Rectangle() {
			this(0, 0, 0, 0);
		}
		Rectangle(int left, int top, int right, int bottom) {
			set(left, top, right, bottom);
		}
		void set(Rectangle r) {
			set(r.left, r.top, r.right, r.bottom);
		}
		void set(int left, int top, int right, int bottom) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;	
		}
		boolean contains(int x, int y) {
			return !(x < left || x > right || y < top || y > bottom);
		}
		// Returns true if this rectangle is equivalent to the given recctangle
		boolean equals(Rectangle r) {
			return !(left != r.left || right != r.right || top != r.top || bottom != r.bottom);
		}
		// Returns true if this rectangle contains the given rectangle
		boolean contains(Rectangle r) {
			return !(r.left < left || r.right > right || r.top < top || r.bottom > bottom);
		}
		// Sets this rectangle as the union of the two given rectangles
		void union(Rectangle a, Rectangle b) {
			left = Math.min(a.left, b.left);
			top = Math.min(a.top, b.top);
			right = Math.max(a.right, b.right);
			bottom = Math.max(a.bottom, b.bottom);
		}
		// Unions this rectangle with a given rectangle
		public void union(Rectangle r) {
			if (r.left < left) {
				left = r.left;
			}
			if (r.right > right) {
				right = r.right;
			}
			if (r.top < top) {
				top = r.top;
			}
			if (r.bottom > bottom) {
				bottom = r.bottom;
			}
		}
		// Returns true if this rectangle intersects the given rectangle
		public boolean intersects(Rectangle r) {
			return !(right < r.left || left > r.right || top > r.bottom || bottom < r.top);
		}
		// Sets this rectangle as the intersection of the two given rectangles
		public void intersect(Rectangle a, Rectangle b) {
			left = Math.max(a.left, b.left);
			top = Math.max(a.top, b.top);
			right = Math.min(a.right, b.right);
			bottom = Math.min(a.bottom, b.bottom);
		}
		// Sets this rectangle as the intersection of itself and the given rectangle
		public void intersect(Rectangle r) {
			if (r.left > left) {
				left = r.left;
			}
			if (r.top > top) {
				top = r.top;
			}
			if (r.right < right) {
				right = r.right;
			}
			if (r.bottom < bottom) {
				bottom = r.bottom;
			}
		}
		int width() {
			return right - left;
		}
		int height() {
			return bottom - top;
		}
		Rectangle copy() {
			return new Rectangle(left, top, right, bottom);
		}
	}
	class Extent {
		int offset;
		float anchor;
		Extent(float anchor) {
			this.anchor = anchor;
		}
		Extent(int value, int size, float anchor) {
			this.anchor = anchor;
			this.reset(value, size);
		}
		void reset(int value, int size) {
			this.offset = value - (int)(size * anchor);
		}
		int value(int size) {
			return (int)(size * anchor) + offset;
		}
	}
	class Anchor {
		float x, y;
		Anchor(float x, float y) {
			this.x = x;
			this.y = y;
		}
		Rectangle getBox(Rectangle container, Size boxSize) {
			float cw = container.width() * x;
			float bw = boxSize.width * x;
			float ch = container.height() * y;
			float bh = boxSize.height * y;
			Rectangle box = new Rectangle();
			box.left = (int)(cw - bw) + container.left;
			box.top = (int)(ch + bh) + container.top;
			box.right = (int)(cw + bw) + container.left;
			box.bottom = (int)(ch - bh) + container.top;
			return box;
		}
		int getLeft(Rectangle container, int width) {
			return (int)((container.width() * x) - (width * x) + container.left);
		}
		int getTop(Rectangle container, int height) {
			return (int)((container.height() * y) + (height * y) + container.top);
		}
		int getRight(Rectangle container, int width) {
			return (int)((container.width() * x) + (width * x) + container.left);
		}
		int getBottom(Rectangle container, int height) {
			return (int)((container.height() * y) + (height * y) + container.top);
		}
		Anchor copy() {
			return new Anchor(x, y);
		}
	}

	class RectangleList 
	{

		public static final int DEFAULT_INCREASE = 16;

		private int increase = DEFAULT_INCREASE;
		private int total = 0;
		private Rectangle[] rects = null;

		//
		public RectangleList(int initialCapacity) {
			rects = new Rectangle[initialCapacity];
			while (--initialCapacity >= 0) {
				rects[initialCapacity] = new Rectangle();
			}
		}

		// Returns and removes the last dirty rectangle added
		public Rectangle popDirty() {
			return (total == 0 ? null : rects[--total]);
		}

		// Adds the rectangle to be redrawn
		public void invalidate(Rectangle r) 
		{
			// Compare against all pre-existing rectangles
			for (int i = 0; i < total; i++) {
				// If the given rectangle intersects...
				if (r.intersects(rects[i])) {
					// Intersect, join them and return
					rects[i].union(r);
					return;
				}
			}

			// The given rectangle does not intersect any pre-existing ones, add
			// it to the list of rectangles.
			ensureCapacity(1);
			add(r);
		}

		// Adds the rectangle to the list
		private void add(Rectangle r) {
			rects[total++].set(r);
		}

		// Ensures that the list has enough room to add x more rectangles
		private void ensureCapacity(int x) 
		{
			// Get the current capacity of the array
			int capacity = rects.length;

			// If we need more space to add x rectangles then...
			if (total + x > capacity) {
				// Determine the new capacity size (it will always fit atleast x
				// more rectangles
				int newcapacity = Math.max(capacity + increase, capacity + x);

				// Resize the rectangle array
				rects = Arrays.copyOf(rects, newcapacity);

				// Instantiate each rectangle added
				while (--newcapacity >= capacity) {
					rects[newcapacity] = new Rectangle();
				}
			}
		}
		// Returns true if there are any dirty rectangles
		public boolean hasDirty() {
			return (total != 0);
		}
		// Clears list of all rectangles
		public void clear() {
			total = 0;
		}
	}
	class ControlGraphics {

		Rectangle bounds = new Rectangle();
		Rectangle clip = new Rectangle();
		RectangleList dirties = new RectangleList(32);
		Graphics2D gr;

		Control container;
		Composite oldComposite = null;

		int tx, ty;

		public ControlGraphics(Control container) {
			this.container = container;
			this.dirties.invalidate(container.getBounds());
		}

		void invalidate(LayerBase base) {
			bounds.set(base.getBounds());
			if (base instanceof Control) {
				Control c = (Control)base;
				bounds.left += c.getActualX();
				bounds.top += c.getActualY();
			} else {
				bounds.left += tx;
				bounds.top += ty;
			}
			dirties.invalidate(bounds);
		}

		void translateTo(Rectangle bounds) {
			tx += bounds.left;
			ty += bounds.top;
		}

		void translateFrom(Rectangle bounds) {
			tx -= bounds.left;
			ty -= bounds.top;
		}

		void setFont(Font font) {
			gr.setFont(font);
		}

		FontMetrics getFontMetrics() {
			return gr.getFontMetrics();
		}

		void drawString(String text, int left, int bottom, Color foreColor) {
			gr.setColor(foreColor);
			gr.drawString(text, left + tx, bottom + ty);
		}

		void draw(Tile tile, Rectangle destination) {
			Rectangle source = tile.source;
			int x1 = destination.left + tx;
			int y1 = destination.top + ty;
			int x2 = destination.right + tx;
			int y2 = destination.bottom + ty;

			gr.drawImage(tile.texture,
					x1, y1, x2, y2, 
					source.left, source.top, source.right, source.bottom, null);

		}

		void setAlpha(int alpha) {
			oldComposite = gr.getComposite();
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
		}

		void resetAlpha() {
			gr.setComposite(oldComposite);
		}

		void outline(Rectangle bounds) {
			gr.drawRect(bounds.left + tx, bounds.top + ty, bounds.width(), bounds.height());
		}

		void draw(Graphics2D gr) {
			this.gr = gr;

			tx = ty = 0;

			while (dirties.hasDirty()) {
				clip.set(dirties.popDirty());
//				gr.setClip(clip.left, clip.top, clip.width(), clip.height());
				container.draw(this);
//				gr.setColor(Color.red);
//				gr.fillRect(clip.left, clip.top, clip.width(), clip.height());
//				System.out.format("(%d %d %d %d)\n", clip.left, clip.top, clip.right, clip.bottom);
			}
		}

		Rectangle getClip() {
			return clip;
		}
	}

	class Control implements LayerBase {
		Extent left, top, right, bottom;
		Rectangle bounds;

		String name;

		Control parent;
		ArrayList<Control> children;

		Layer[] layers;

		ControlGraphics graphics;

		boolean enabled = true;
		boolean visible = true;
		boolean dirty = true;

		Control(Control parent, int x, int y, int width, int height, int layerCount) {
			this.parent = parent;
			if (parent != null) parent.children.add(this);
			this.children = new ArrayList<Control>();
			this.bounds = new Rectangle(x, y, x + width, y + height);
			this.left = new Extent(0f);
			this.top = new Extent(0f);
			this.right = new Extent(0f);
			this.bottom = new Extent(0f);
			this.updateAnchors();

			this.layers = new Layer[layerCount];
		}
		void setGraphics(ControlGraphics graphics) {
			this.graphics = graphics;
		}
		void setLayer(int index, Layer layer) {
			layers[index] = layer;
		}
		Layer getLayer(int index) { 
			return layers[index];
		}
		LayerText getTextLayer(int index) {
			Layer l = layers[index];
			return (l != null && (l instanceof LayerText) ? (LayerText)l : null);
		}
		LayerImage getImageLayer(int index) {
			Layer l = layers[index];
			return (l != null && (l instanceof LayerImage) ? (LayerImage)l : null);
		}
		void draw(ControlGraphics gr) {
			if (!visible) {
				return;
			}
			// Clip drawing to only this control
			gr.translateTo(bounds);
			// Perform this controls specific drawing routine.
			if (dirty) {
				onDraw(gr);
				dirty = false;
			}
			// Draw the children controls now!
			for (Control child : children) {
				child.draw(gr);
			}
			// Reset the clip
			gr.translateFrom(bounds);
		}
		void update(TimeUnit elapsed) {
			if (!enabled) {
				return;
			}

			graphics.translateTo(bounds);
			onUpdate(elapsed);

			for (Layer layer : layers) {
				if (layer != null && layer.enabled) {
					layer.update(elapsed);
				}
			}

			for (Control child : children) {
				child.update(elapsed);
			}
			graphics.translateFrom(bounds);
		}
		void onUpdate(TimeUnit elapsed) {

		}
		void onDraw(ControlGraphics gr) {
			for (Layer l : layers) {
				if (l != null && l.visible) {
					l.draw(gr);
				}
			}
		}
		Control getControl(int x, int y) {
			if (!contains(x, y))
				return null;
			if (!children.isEmpty()) {
				x -= bounds.left;
				y -= bounds.top;
				for (Control child : children) {
					Control next = child.getControl(x, y);
					if (next != null) {
						return next;
					}
				}
			}
			return this;
		}
		void invalidate() {
			graphics.invalidate(this);
			dirty = true;
		}
		void setAnchors(float left, float top, float right, float bottom) {
			this.left.anchor = left;
			this.top.anchor = top;
			this.right.anchor = right;
			this.bottom.anchor = bottom;
			updateAnchors();
		}
		void resize() {
			int width = bounds.width();
			int height = bounds.height();
			if (parent != null) {
				int w = parent.bounds.width();
				int h = parent.bounds.height();
				bounds.left = left.value(w);
				bounds.top = top.value(h);
				bounds.right = right.value(w);
				bounds.bottom = bottom.value(h);
			}
			if (width != bounds.width() || height != bounds.height()) {
				invalidate();
				for (Layer l : layers) {
					if (l != null) {
						l.baseChanged();
					}
				}
				for (Control child : children) {
					child.resize();
				}
			}
		}
		void updateAnchors() {
			if (parent != null) {
				int width = parent.bounds.width();
				int height = parent.bounds.height();
				left.reset(bounds.left, width);
				top.reset(bounds.top, height);
				right.reset(bounds.right, width);
				bottom.reset(bounds.bottom, height);
			}
		}
		boolean contains(int x, int y) {
			return bounds.contains(x, y);
		}
		boolean contains(MouseState state) {
			int left = getActualX();
			int top = getActualY();
			int right = left + bounds.width();
			int bottom = right + bounds.height();
			return !(state.x0 < left || state.x0 > right || state.y0 < top || state.y0 > bottom);
		}

		public void onMouseDrag(MouseState state) {
			//			System.out.format("Mouse drag on %s\n", name);
		}
		public void onMouseMove(MouseState state) {
			//			System.out.format("Mouse move on %s\n", name);
		}
		public void onMousePress(MouseState state) {
			//			System.out.format("Mouse press on %s\n", name);
		}
		public void onMouseRelease(MouseState state) {
			//			System.out.format("Mouse release on %s\n", name);
		}
		public void onMouseWheel(MouseState state) {
			//			System.out.format("Mouse wheel on %s\n", name);
		}

		public void onMouseEnter(MouseState state) {
			//			System.out.format("Mouse enter on %s\n", name);
		}
		public void onMouseExit(MouseState state) {
			//			System.out.format("Mouse exit on %s\n", name);
		}

		public void onKeyPress(KeyEvent e) {
			//			System.out.format("Key press on %s\n", name);
		}
		public void onKeyRelease(KeyEvent e) {
			//			System.out.format("Key release on %s\n", name);
		}

		public void onSelect() {
			//			System.out.format("Select on %s\n", name);
		}
		public void onUnselect() {
			//			System.out.format("Select off %s\n", name);
		}

		int getActualX() {
			return (parent == null ? 0 : bounds.left + parent.getActualX());
		}
		int getActualY() {
			return (parent == null ? 0 : bounds.top + parent.getActualY());
		}
		public Rectangle getBounds() {
			return bounds;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	class Tile {
		BufferedImage texture;
		Rectangle source;
		Tile(BufferedImage texture, Rectangle source) {
			this.texture = texture;
			this.source = source;
		}
		Tile copy() {
			return new Tile(texture, source.copy());
		}
	}

	interface LayerBase {
		Rectangle getBounds();
	}
	abstract class Layer {
		boolean visible = true;
		boolean enabled = true;
		Color color;
		LayerBase base;
		Layer(LayerBase base) {
			this.base = base;
		}
		void draw(ControlGraphics gr) {
						if (color == null || color.getAlpha() == 255) {
			onDraw(gr);
						}
						else if (color.getAlpha() > 0) {
							gr.setAlpha(color.getAlpha());
							onDraw(gr);
							gr.resetAlpha();
						}
		}
		abstract void update(TimeUnit elapsed);
		abstract void onDraw(ControlGraphics gr);
		abstract void baseChanged();
		abstract Layer copy(LayerBase base);
	}

	class LayerText extends Layer {
		Extent left, top, right, bottom;
		Rectangle box;
		Anchor anchor;
		String text;
		Color foreColor;
		Font font;

		LayerText(LayerBase base, String text, Anchor anchor, Rectangle padding) {
			super(base);
			this.text = text;
			this.anchor = anchor;
			this.box = new Rectangle();

			int width = base.getBounds().width();
			int height = base.getBounds().height();
			left = new Extent(padding.left, width, 0f);
			top = new Extent(padding.top, height, 0f);
			right = new Extent(width - padding.right, width, 1f);
			bottom = new Extent(height - padding.bottom, height, 1f);
			baseChanged();
		}
		void onDraw(ControlGraphics gr) {
			gr.setFont(font);
			FontMetrics fm = gr.getFontMetrics();

			int left = anchor.getLeft(box, fm.stringWidth(text));
			int bottom = anchor.getBottom(box, fm.getAscent());

			gr.drawString(text, left, bottom, foreColor);
		}
		void update(TimeUnit elapsed) {

		}
		void baseChanged() {
			Rectangle bounds = base.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			box.left = left.value(width);
			box.top = top.value(height);
			box.right = right.value(width);
			box.bottom = bottom.value(height);
		}
		void setText(String text) {
			this.text = text;
			invalidate();
		}
		Layer copy(LayerBase base) {
			int width = base.getBounds().width();
			int height = base.getBounds().height();
			int padleft = box.left;
			int padtop = box.top;
			int padright = width - box.right;
			int padbottom = height - box.bottom;
			Rectangle padding = new Rectangle(padleft, padtop, padright, padbottom);
			LayerText layer = new LayerText(base, text, anchor.copy(), padding);
			layer.font = font;
			layer.foreColor = foreColor;
			return layer;
		}
	}

	abstract class LayerImage extends Layer {
		static final int CENTER = 1;
		static final int LEFT = 2;
		static final int TOP = 4;
		static final int RIGHT = 8;
		static final int BOTTOM = 16;
		static final int TOP_LEFT = 32;
		static final int TOP_RIGHT = 64;
		static final int BOTTOM_LEFT = 128;
		static final int BOTTOM_RIGHT = 256;

		Tile[] tiles;
		Rectangle[] rects;
		int code;
		LayerImage(LayerBase base, int tileCount, int tileCode) {
			super(base);
			code = tileCode;
			tiles = new Tile[tileCount];
			rects = new Rectangle[tileCount];
			for (int i = 0; i < tileCount; i++) {
				rects[i] = new Rectangle();
			}
		}
		void onDraw(ControlGraphics gr) {
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i] != null) {
					gr.draw(tiles[i], rects[i]);
				} else {
					gr.outline(rects[i]);
				}
			}
		}
		void update(TimeUnit elapsed) {
		}
		void setTile(int x, Tile tile) {
			if ((code & x) != 0) {
				tiles[mapTileToIndex(x)] = tile;
			}
		}
		Tile getTile(int x) {
			return ((code & x) == 0 ? null : tiles[mapTileToIndex(x)]);
		}
		abstract int mapTileToIndex(int x);
	}

	class LayerImageStretch extends LayerImage {
		LayerImageStretch(LayerBase base) {
			super(base, 1, CENTER);
			baseChanged();
		}
		void baseChanged() {
			Rectangle bounds = base.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			rects[0].set(0, 0, width, height);
		}
		int mapTileToIndex(int x) {
			return 0;
		}
		Layer copy(LayerBase base) {
			return new LayerImageStretch(base);
		}
	}
	class LayerImageVertical extends LayerImage {
		Extent top, bottom;
		LayerImageVertical(LayerBase base, int topPadding, int bottomPadding) {
			super(base, 3, TOP | CENTER | BOTTOM);
			int height = base.getBounds().height();
			top = new Extent(topPadding, height, 0f);
			bottom = new Extent(height - bottomPadding, height, 1f);
			baseChanged();
		}
		void baseChanged() {
			Rectangle bounds = base.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			int y1 = top.value(height);
			int y2 = bottom.value(height);
			rects[0].set(0, 0, width, y1);
			rects[1].set(0, y1, width, y2);
			rects[2].set(0, y2, width, height);
		}
		int mapTileToIndex(int x) {
			switch (x) {
				case TOP: 		return 0;
				case CENTER: 	return 1;
				case BOTTOM: 	return 2;
				default:		return -1;
			}
		}
		Layer copy(LayerBase base) {
			int top = rects[1].top - rects[0].top;
			int bottom = rects[2].bottom - rects[1].bottom;
			LayerImageVertical layer = new LayerImageVertical(base, top, bottom);
			layer.tiles[0] = tiles[0].copy();
			layer.tiles[1] = tiles[1].copy();
			layer.tiles[2] = tiles[2].copy();
			return layer;
		}
	}
	class LayerImageHorizontal extends LayerImage {
		Extent left, right;
		LayerImageHorizontal(LayerBase base, int leftPadding, int rightPadding) {
			super(base, 3, LEFT | CENTER | RIGHT);
			int width = base.getBounds().width();
			left = new Extent(leftPadding, width, 0f);
			right = new Extent(width - rightPadding, width, 1f);
			baseChanged();
		}
		void baseChanged() {
			Rectangle bounds = base.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			int x1 = left.value(width);
			int x2 = right.value(width);
			rects[0].set(0, 0, x1, height);
			rects[1].set(x1, 0, x2, height);
			rects[2].set(x2, 0, width, height);
		}
		int mapTileToIndex(int x) {
			switch (x) {
				case LEFT: 		return 0;
				case CENTER: 	return 1;
				case RIGHT: 	return 2;
				default:		return -1;
			}
		}
		Layer copy(LayerBase base) {
			int left = rects[1].left - rects[0].left;
			int right = rects[2].right - rects[1].right;
			LayerImageHorizontal layer = new LayerImageHorizontal(base, left, right);
			layer.tiles[0] = tiles[0].copy();
			layer.tiles[1] = tiles[1].copy();
			layer.tiles[2] = tiles[2].copy();
			return layer;
		}
	}
	class LayerImageBorder extends LayerImage {
		Extent left, top, right, bottom;
		LayerImageBorder(LayerBase base, int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
			super(base, 9, TOP_LEFT | TOP | TOP_RIGHT | LEFT | CENTER | RIGHT | BOTTOM_LEFT | BOTTOM | BOTTOM_RIGHT);

			int width = base.getBounds().width();
			int height = base.getBounds().height();

			left = new Extent(leftPadding, width, 0f);
			top = new Extent(topPadding, height, 0f);
			right = new Extent(width - rightPadding, width, 1f);
			bottom = new Extent(height - bottomPadding, height, 1f);
			baseChanged();
		}
		void baseChanged() {
			Rectangle bounds = base.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			int x1 = left.value(width);
			int y1 = top.value(height);
			int x2 = right.value(width);
			int y2 = bottom.value(height);
			rects[0].set(0, 0, x1, y1);
			rects[1].set(x1, 0, x2, y1);
			rects[2].set(x2, 0, width, y1);
			rects[3].set(0, y1, x1, y2);
			rects[4].set(x1, y1, x2, y2);
			rects[5].set(x2, y1, width, y2);
			rects[6].set(0, y2, x1, height);
			rects[7].set(x1, y2, x2, height);
			rects[8].set(x2, y2, width, height);
		}
		int mapTileToIndex(int x) {
			switch (x) {
				case TOP_LEFT: 		return 0;
				case TOP: 			return 1;
				case TOP_RIGHT: 	return 2;
				case LEFT: 			return 3;
				case CENTER: 		return 4;
				case RIGHT: 		return 5;
				case BOTTOM_LEFT: 	return 6;
				case BOTTOM: 		return 7;
				case BOTTOM_RIGHT: 	return 8;
				default:			return -1;
			}
		}
		Layer copy(LayerBase base) {
			int left = rects[1].left - rects[0].left;
			int top = rects[3].top - rects[0].top;
			int right = rects[2].right - rects[1].right;
			int bottom = rects[6].bottom - rects[3].bottom;
			LayerImageBorder layer = new LayerImageBorder(base, left, top, right, bottom);
			for (int i = 0; i < tiles.length; i++) {
				layer.tiles[i] = tiles[i].copy();
			}
			return layer;
		}
	}

	class Box {
		Extent left, top, right, bottom;
		Rectangle bounds;
		LayerBase base;
		// Initializes a new Box
		Box(LayerBase base, int left, int top, int right, int bottom) {
			this.base = base;
			this.bounds = new Rectangle(left, top, right, bottom);
			this.left = new Extent(0f);
			this.top = new Extent(0f);
			this.right = new Extent(0f);
			this.bottom = new Extent(0f);
			this.updateExtents();
		}
		// Should be called when the size or location of the base is changed
		void baseChange() {
			int width = base.getBounds().width();
			int height = base.getBounds().height();
			bounds.left = left.value(width);
			bounds.top = top.value(height);
			bounds.right = right.value(width);
			bounds.bottom = bottom.value(height);
		}
		// Should be called when an extends anchor is changed
		void updateExtents() {
			int width = base.getBounds().width();
			int height = base.getBounds().height();
			left.reset(bounds.left, width);
			top.reset(bounds.top, height);
			right.reset(bounds.right, width);
			bottom.reset(bounds.bottom, height);
		}
	}

	FrameCounter counter;

	ControlGraphics graphics;
	Control container;
	Control button1;
	Control button2;
	Control panel1;
	Control label1;
	Control label2;
	Control label3;
	Control label4;

	public TestScreen() {
		super(512, 512);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);

		setListener(this);
		setBackground(Color.black);
		//		setFrameRate(30);

		counter = new FrameCounter();

		container = new Control(null, 0, 0, 512, 512, 0);
		container.setName("container");

		graphics = new ControlGraphics(container);
		container.setGraphics(graphics);

		panel1 = new Control(container, 20, 120, 472, 372, 1);
		panel1.setGraphics(graphics);
		panel1.setName("panel1");
		panel1.setAnchors(0.0f, 0.0f, 1.0f, 1.0f);

		BufferedImage gui = null;
		try {
			gui = ImageIO.read(getClass().getClassLoader().getResource("ship/Frame.png"));
			System.out.println("GUI image loaded");
		} catch (Exception e) {
			System.out.println("Error loading gui image");
		}
		LayerImageBorder frame = new LayerImageBorder(panel1, 8, 8, 8, 8);
		frame.setTile(LayerImage.TOP_LEFT, 		new Tile(gui, new Rectangle(0, 0, 8, 8)));
		frame.setTile(LayerImage.TOP, 			new Tile(gui, new Rectangle(8, 0, 9, 8)));
		frame.setTile(LayerImage.TOP_RIGHT, 	new Tile(gui, new Rectangle(9, 0, 17, 8)));
		frame.setTile(LayerImage.LEFT, 			new Tile(gui, new Rectangle(0, 8, 8, 9)));
		frame.setTile(LayerImage.CENTER, 		new Tile(gui, new Rectangle(8, 8, 9, 9)));
		frame.setTile(LayerImage.RIGHT, 			new Tile(gui, new Rectangle(9, 8, 17, 9)));
		frame.setTile(LayerImage.BOTTOM_LEFT, 	new Tile(gui, new Rectangle(0, 9, 8, 17)));
		frame.setTile(LayerImage.BOTTOM, 		new Tile(gui, new Rectangle(8, 9, 9, 17)));
		frame.setTile(LayerImage.BOTTOM_RIGHT, new Tile(gui, new Rectangle(9, 9, 17, 17)));
		frame.color = new Color(255, 255, 255, 180);
		panel1.setLayer(0, frame);


		label1 = new Control(panel1, 20, 20, 80, 33, 4) {
			boolean mouseOver = false;
			public void onUpdate(TimeUnit elapsed) {
				Layer l = getLayer(2);

				int red = l.color.getRed();
				int blue = l.color.getBlue();
				int green = l.color.getGreen();
				int alpha = l.color.getAlpha();

				if (mouseOver) {
					alpha = (int)Math.min(255, alpha + elapsed.seconds * 1000);
				} else {
					alpha = (int)Math.max(0, alpha - elapsed.seconds * 1000);
				}

				if (alpha != l.color.getAlpha()) {
					l.color = new Color(red, green, blue, alpha);
					invalidate();
				}
			}
			public void onMouseEnter(MouseState state) {
				mouseOver = true;
			}
			public void onMouseExit(MouseState state) {
				mouseOver = false;
			}
			public void onMousePress(MouseState state) {
				getLayer(0).visible = false;
				getLayer(1).visible = true;
				invalidate();
			}
			public void onMouseRelease(MouseState state) {
				getLayer(1).visible = false;
				getLayer(0).visible = true;
				invalidate();
			}
		};
		label1.setGraphics(graphics);
		label1.setName("label1");

		LayerImageHorizontal buttonUp = new LayerImageHorizontal(label1, 5, 5);
		buttonUp.setTile(LayerImage.LEFT, 		new Tile(gui, new Rectangle(0, 17, 5, 50)));
		buttonUp.setTile(LayerImage.CENTER, 	new Tile(gui, new Rectangle(5, 17, 6, 50)));
		buttonUp.setTile(LayerImage.RIGHT, 		new Tile(gui, new Rectangle(6, 17, 11, 50)));
		label1.setLayer(0, buttonUp);

		LayerImageHorizontal buttonDown = new LayerImageHorizontal(label1, 5, 5);
		buttonDown.setTile(LayerImage.LEFT, 	new Tile(gui, new Rectangle(11, 17, 16, 50)));
		buttonDown.setTile(LayerImage.CENTER, 	new Tile(gui, new Rectangle(16, 17, 17, 50)));
		buttonDown.setTile(LayerImage.RIGHT, 	new Tile(gui, new Rectangle(17, 17, 22, 50)));
		buttonDown.visible = false;
		label1.setLayer(1, buttonDown);

		LayerImageStretch buttonOver = new LayerImageStretch(label1);
		buttonOver.setTile(LayerImage.CENTER, new Tile(gui, new Rectangle(0, 51, 63, 73)));
		buttonOver.color = new Color(255, 255, 255, 0);
		label1.setLayer(2, buttonOver);

		LayerText text = new LayerText(label1, "Press me", new Anchor(0.5f, 0.5f), new Rectangle(5, 5, 6, 7));
		text.foreColor = Color.white;
		text.font = new Font("Arial", Font.BOLD, 13);
		label1.setLayer(3, text);

		button1 = new Control(container, 20, 20, 120, 40, 1);
		button1.setGraphics(graphics);
		button1.setName("button1");
		button1.setLayer(0, buttonUp.copy(button1));

		button2 = new Control(container, 196, 70, 120, 40, 1);
		button2.setGraphics(graphics);
		button2.setName("button2");
		button2.setAnchors(0.5f, 0.0f, 0.5f, 0.0f);
		button2.setLayer(0, buttonUp.copy(button2));


		label2 = new Control(panel1, 372, 20, 80, 30, 1);
		label2.setGraphics(graphics);
		label2.setName("label2");
		label2.setAnchors(1.0f, 0.0f, 1.0f, 0.0f);
		label2.setLayer(0, buttonUp.copy(label2));

		label3 = new Control(panel1, 20, 80, 432, 40, 2);
		label3.setGraphics(graphics);
		label3.setName("label3");
		label3.setAnchors(0.0f, 0.0f, 1.0f, 0.0f);
		label3.setLayer(0, buttonUp.copy(label3));
		label3.setLayer(1, text.copy(label3));
		label3.getTextLayer(1).setText("This is a really looonnng button!");

		label4 = new Control(panel1, 20, 312, 432, 40, 1);
		label4.setGraphics(graphics);
		label4.setName("label4");
		label4.setAnchors(0.0f, 1.0f, 1.0f, 1.0f);
		label4.setLayer(0, buttonUp.copy(label4));
	}

	public void update(TimeUnit elapsed) {
		counter.update(elapsed);
		container.update(elapsed);
	}

	public void draw(Graphics2D gr) {

		graphics.draw(gr);

//		if (isKeyDown(KeyEvent.VK_C)) {
//			gr.setColor(Color.red);
//			gr.drawOval(state.x0 - 5, state.y0 - 5, 10, 10);
//		}

//		if (focused != null) {
//			int x = under.getActualX();
//			int y = under.getActualY();
//			gr.setColor(Color.red);
//			gr.drawRect(x - 1, y - 1, under.bounds.width() + 2, under.bounds.height() + 2);
//		}

//		gr.setColor(Color.black);
//		gr.fillRect(10, 3, 80, 15);
//		gr.setColor(Color.white);
//		gr.drawString(String.valueOf(counter.getFrameRate()), 10, 15);
	}

	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		container.bounds.right = getWidth();
		container.bounds.bottom = getHeight();
		container.resize();
	}


	MouseState state = new MouseState();
	Control selected = null;
	Control focused = null;

	/**
	 * Handles translating and zooming using the mouse. Translating
	 * is done using the left mouse button and dragging the mouse anywhere.
	 * Zooming is done using the right mouse button and dragging
	 * the mouse up and down to zoom in and out.
	 */
	public void mouseDragged(MouseEvent e) {
		synchronized (this) {
			// Update the mouse state
			state.setEvent(e);

			// If there is a canvas under the mouse then trigger the event.
			if (focused != null) {
				focused.onMouseDrag(state);
			}
		}
	}

	/**
	 * Handles updating the mouse position when the panel is clicked on.
	 * If the panel is clicked by the right button then the focus point
	 * is saved.
	 */
	public void mousePressed(MouseEvent e) {
		synchronized (this) {
			// Update the mouse state
			state.setEvent(e);

			// If the left button has been pressed change the flag
			if (e.getButton() == MouseEvent.BUTTON1) {
				state.leftDown0 = true;
			}
			// If the left button has been pressed change the flag and focus
			if (e.getButton() == MouseEvent.BUTTON3) {
				state.rightDown0 = true;
				state.focus();
			}

			// If there is a canvas under the mouse then trigger the event.			
			if (focused != null) {
				focused.onMousePress(state);	
			}

			// Set the focused canvas
			selected = container.getControl(state.x0, state.y0);
		}
	}

	/**
	 * Signals the release of either the right or left mouse button.
	 */
	public void mouseReleased(MouseEvent e) {		
		synchronized (this) {
			// Update the mouse state
			state.setEvent(e);

			// If the left button has been released change the flag
			if (e.getButton() == MouseEvent.BUTTON1) {
				state.leftDown0 = false;
			}
			// The the right button has been released change the flag
			if (e.getButton() == MouseEvent.BUTTON3) {
				state.rightDown0 = false;
			}

			// If there is a canvas under the mouse then trigger the event.
			if (focused != null) {
				// If the focused canvas does not contain the mouse now (mouse
				// dragged outside of canvas)
				Control under = container.getControl(state.x0, state.y0); 
				if (focused != under) {
					// Notify the last canvas that a button has been released
					// but outside its bounds
					focused.onMouseRelease(state);
					focused.onMouseExit(state);

					// Get the canvas the mouse is over
					focused = under;
					// If there is one then trigger mouse enter event
					if (focused != null) {
						focused.onMouseEnter(state);
					}
				}
				// The focused canvas still contains the mouse
				else {
					focused.onMouseRelease(state);
				}
			}
		}
	}

	/**
	 * Handles zooming in and out of a graph using the mouse wheel. Scrolling
	 * forwards zooms in at the current point, scrolling backwards zooms out
	 * from the current point.
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		synchronized (this) {
			// Update the mouse state
			state.setEvent(e);
			state.wheel = e.getWheelRotation();

			// If there is a canvas under the mouse then trigger the event.
			if (focused != null) {
				focused.onMouseWheel(state);
			}
		}
	}

	/**
	 * Handles setting the cursor of the graph as the mouse moves acrossed it.
	 */
	public void mouseMoved(MouseEvent e) {
		synchronized (this) {
			// Update the mouse state
			state.setEvent(e);

			boolean reset = false;

			// If a canvas is already under focus..
			if (focused != null) {
				// If the mouse is no longer over the canvas then change
				// the canvas which has focus
				Control under = container.getControl(state.x0, state.y0);

				if (focused != under) {
					// Notify last focused canvas that the mouse has left.
					focused.onMouseExit(state);
					// Get the new canvas
					focused = under;
					reset = true;
				} 
			} else {
				// Get the canvas under the mouse
				focused = container.getControl(state.x0, state.y0);
				reset = true;
			}

			// If a canvas is under the mouse then trigger the event.
			if (focused != null) {
				if (reset) {
					focused.onMouseEnter(state);
				} else {
					focused.onMouseMove(state);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}


}
