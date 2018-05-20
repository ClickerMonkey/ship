package net.philsprojects.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.*;
import net.philsprojects.game.controls.Button;
import net.philsprojects.game.controls.ControlSystem;
import net.philsprojects.game.controls.SchemeStretch;
import net.philsprojects.game.sprites.SpriteTileStatic;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;


public class TestControlSystem extends SceneWindow
{

	public static void main(String[] args)
	{
		new TestControlSystem();
	}

	private static final long serialVersionUID = 2631727625310970614L;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 512;
	public static final String NUMBERS = "Numbers";
	public static final String CONTROLS = "Controls";
	private ControlSystem _controls;
	private Button _button;
	private Text _text;

	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		textures.setDirectory("net/philsprojects/game/controls/");
		textures.add("Numbers.png", NUMBERS);
		textures.add("Buttons.png", CONTROLS);

		_controls = ControlSystem.getInstance();
		_controls.setLocation(0, 0);
		_controls.setSize(WIDTH, HEIGHT);
		_controls.setTexture(CONTROLS);

		_button = new Button("TestButton", 100, HEIGHT - 100, 120, 28);
		_button.setNormalScheme(new SchemeStretch(new SpriteTileStatic("", "", 0, 0, 120, 40)));
		_button.setMouseOverScheme(new SchemeStretch(new SpriteTileStatic("", "", 0, 40, 120, 80)));
		_button.setMouseDownScheme(new SchemeStretch(new SpriteTileStatic("", "", 0, 80, 120, 120)));
		_controls.addChild(_button);

		_text = new Text(NUMBERS, "0123456789", 0, 0, 32, 36, 3, 20, 23);
	}

	@Override
	public void update(float deltatime)
	{
		_controls.update(deltatime);
	}

	@Override
	public void draw(GraphicsLibrary graphics)
	{
		_controls.draw();
		_text.draw(String.valueOf((int)getTimer().getFrameRate()), 10, HEIGHT - 30, false);
		Vector v = _controls.getMouseState().getCurrentPosition();
		_text.draw(v.x + "    " + v.y, 10, HEIGHT - 50, false);
	}

	@Override
	public void disposeData()
	{

	}



	@Override
	public void keyDown(KeyEvent key)
	{

	}

	@Override
	public void keyUp(KeyEvent key)
	{

	}

	@Override
	public void mouseDown(MouseEvent mouse)
	{

	}

	@Override
	public void mouseMove(MouseEvent mouse)
	{

	}

	@Override
	public void mouseUp(MouseEvent mouse)
	{

	}




	@Override
	public Color requestBackgroundColor()
	{
		return new Color(147, 204, 234); // CornFlower Blue
	}

	@Override
	public Rectangle requestCameraBounds()
	{
		return new Rectangle(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public int requestMaximumScreens()
	{
		return 0;
	}

	@Override
	public int requestMaximumSounds()
	{
		return 0;
	}

	@Override
	public int requestMaximumTextures()
	{
		return 5;
	}

	@Override
	public int requestMaximumTiles()
	{
		return 0;
	}

	@Override
	public int requestUpdatesPerSecond()
	{
		return 4;
	}

	@Override
	public int requestWindowHeight()
	{
		return HEIGHT;
	}

	@Override
	public int requestWindowWidth()
	{
		return WIDTH;
	}

}
