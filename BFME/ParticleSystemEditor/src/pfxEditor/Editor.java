package pfxEditor;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;

public class Editor extends Scene {

	private static final long serialVersionUID = 3427186729L;

	public static void main(String[] args)
	{
		new Editor();
	}
	
	public Editor()
	{
		super();
	}
	
	@Override
	public void draw(GL gl) {

	}

	@Override
	public void keyDown(KeyEvent key) {

	}

	@Override
	public void keyUp(KeyEvent key) {

	}

	@Override
	public void load(TextureLibrary textures, int width, int height) {

	}

	@Override
	public void mouseDown(MouseEvent mouse) {

	}

	@Override
	public void mouseMove(MouseEvent mouse) {

	}

	@Override
	public void mouseUp(MouseEvent mouse) {

	}

	@Override
	public Color requestBackgroundColor() {
		return Color.white();
	}

	@Override
	public int requestMaximumTextures() {
		return 12;
	}

	@Override
	public int requestSceneHeight() {
		return 512;
	}

	@Override
	public int requestSceneWidth() {
		return 768;
	}

	@Override
	public int requestUpdatesPerSecond() {
		return 4;
	}

	@Override
	public void update(float deltatime) {

	}

}
