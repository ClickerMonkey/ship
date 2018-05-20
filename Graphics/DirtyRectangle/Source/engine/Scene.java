package engine;

import java.awt.Component;

public class Scene {

	public static final int SPRITE_CAPACITY = 32;
	
	private Component parent;
	private Rectangle bounds;
	
	private Graphics gr;
	private RectangleList dirties;
	
	private int spriteCount;
	private Sprite[] sprites;
	
	private Timer timer;
	private TimeUnit elapsed;
	
	public Scene(Component parent) {
		this.parent = parent;
		this.bounds = new Rectangle();
		this.parentResized();
		
		this.gr = new Graphics(parent);
		
		this.dirties = new RectangleList(16);
		
		this.sprites = new Sprite[SPRITE_CAPACITY];
		this.spriteCount = 0;
		
		this.timer = new Timer();
	}
	
	public void start() {
		timer.start();
		elapsed = timer.elapsed();
	}

	//
	public void update() {
		timer.update();
		
		Rectangle oldDirty = new Rectangle();
		Sprite sprite;
		
		for (int i = 0; i < spriteCount; i++) {
			
			// Get the current sprite and skip it if it's null
			if ((sprite = sprites[i]) == null) {
				continue;
			}
			
			// If this sprite is enabled....
			if (sprite.isEnabled()) {
				
				// If this sprite is visible...
				if (sprite.isVisible()) {
					
					// Keep track of its previous bounds
					oldDirty.set(sprite.getBounds());
					
					// Update the sprite
					sprite.update(elapsed);
					
					// Did the sprite move or become dirty?
					if (sprite.isDirty()) {
						// Invalidate previous and current bounds
						dirties.invalidate(oldDirty);
						dirties.invalidate(sprite.getBounds());
						// Clear its dirty flag
						sprite.clearDirty();
					}
				}
				// This sprite is not visible
				else {
					// Update the sprite
					sprite.update(elapsed);
				}
			}
		}
	}
	
	//
	public void draw(Graphics gr) {
		
		Sprite sprite;
		Rectangle dirty, bounds;
		
		while ((dirty = dirties.popDirty()) != null) {
			gr.setClip(dirty);
			for (int i = 0; i < spriteCount; i++) {
				if ((sprite = sprites[i]) == null) {
					continue;
				}
				bounds = sprite.getBounds();
				if (sprite.isVisible() && dirty.intersects(bounds)) {
					sprite.draw(gr);
				}
			}
		}
	}
	
	// Forces a redraw on the entire screen
	public void invalidate() {
		dirties.invalidate(bounds);
	}
	
	//
	protected void parentResized() {
		int width = parent.getWidth();
		int height = parent.getHeight();
		
		bounds.right = width;
		bounds.bottom = height;
	}
	
	//
	public Graphics getGraphics() {
		return gr;
	}
	
	//
	public void addSprite(Sprite s) {
		sprites[spriteCount++] = s;
	}
	
}
