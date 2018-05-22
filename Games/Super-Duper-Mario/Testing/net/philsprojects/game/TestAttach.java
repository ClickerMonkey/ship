package net.philsprojects.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.PathBezier;
import net.philsprojects.game.SceneWindow;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.effects.AffectorBoxConstraint;
import net.philsprojects.game.effects.AffectorGravity;
import net.philsprojects.game.effects.BurstParticleSystem;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.sprites.AttachSprite;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;

import static net.philsprojects.game.Constants.*;

public class TestAttach extends SceneWindow
{

	public static void main(String[] args)
	{
		new TestAttach();
	}

	private static final long serialVersionUID = 678053542L;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 512;

	private PathBezier _path;
	private AttachSprite _ball;
	private AttachSprite _miniBall;
	private AttachSprite _miniminiBall;
	private Sprite _sprite;
	private BurstParticleSystem<Particle> _system;
	// ANIMATION TYPE
	private String[] _modeNames = {"LOOP_FORWARD", "ONCE_FORWARD", "LOOP_BACKWARD", "ONCE_BACKWARD", "PINGPONG"};
	private int[] _modes = {LOOP_FORWARD, ONCE_FORWARD, LOOP_BACKWARD, ONCE_BACKWARD, PINGPONG};
	private int _current = 4;
	// ROTATES WITH
	private boolean _rotatesWith = false;


	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		textures.setDirectory("net/philsprojects/game/");
		textures.add("Ball.png", "Ball");

		tiles.add("Ball", "Ball", LOOP_FORWARD, 10, 10, 65, 65, 0, 0, 0.1f);
		graphics.setForeColor(Color.black());

		_path = new PathBezier("Path", _modes[_current], 
				new Vector (WIDTH / 2f, HEIGHT / 2f), 
				new Vector(-WIDTH, HEIGHT), 
				new Vector(WIDTH * 2, -HEIGHT / 2), 
				new Vector(WIDTH / 2f, HEIGHT / 2f));
		_path.startDuration(4f);

		_ball = new AttachSprite("Ball", 0f, 0f, 50f, 50f, 1, 0f, true, _path, _rotatesWith);
		_ball.addAnimation(tiles.getClone("Ball"));
		_ball.setLocationOffset(-25f, -25f);

		_miniBall = new AttachSprite("MiniBall", 40f, -40f, 20f, 20f, 1, 0f, true, _ball, true);
		_miniBall.addAnimation(tiles.getClone("Ball"));
		_miniBall.setLocationOffset(-10f, -10f);

		_miniminiBall = new AttachSprite("MiniMiniBall", 18f, 18f, 10f, 10f, 1, 0f, true, _miniBall, true);
		_miniminiBall.addAnimation(tiles.getClone("Ball"));
		_miniminiBall.setLocationOffset(-5f, -5f);

		_sprite = new Sprite("Sprite", 25, 25, 50, 50, tiles.getClone("Ball"));
		_sprite.setLocationOffset(-25f, -25f);

		_system = new BurstParticleSystem<Particle>(Particle.class, "BallBurst");
		_system.setManualBurst(true);
		_system.setBurstCount(15, 15);
		_system.setEmitterVolume(new VolumeDefault());
		_system.setEmitterVelocity(new VelocityOutward(300, 400));
		_system.setParticleLife(4f, 5f);
		_system.setParticleAlphas(1f, 0.25f);
		_system.setParticleColors(Color.white(), Color.white());
		_system.setParticleSizes(10, 32, 32, 32, 32, 32);
		_system.setParticleType(Particle.FULL);
		_system.setTile(tiles.getClone("Ball"));
		_system.addAffector(new AffectorBoxConstraint(10, HEIGHT - 10, WIDTH - 10, 10, 0.9f, 0.6f, true));
		_system.addAffector(new AffectorGravity(-300));
		_system.initialize();
		System.out.println("Loaded");
	}

	@Override
	public void draw(GraphicsLibrary graphics)
	{
		_ball.draw();
		_miniBall.draw();
		_miniminiBall.draw();
		_sprite.draw();

		graphics.beginText(WIDTH, HEIGHT);
		graphics.drawString("FPS " + getTimer().getFrameRate(), 10, HEIGHT - 25);
		graphics.drawString("[1] " + _modeNames[_current], 10, HEIGHT - 45);
		graphics.drawString("[2] Rotates With :" + _rotatesWith, 10, HEIGHT - 65);
		graphics.endText();

		_system.draw();
	}

	@Override
	public void update(float deltatime)
	{
		_sprite.update(deltatime);
		_path.update(deltatime);
		_ball.update(deltatime);
		_ball.setAngle(_ball.getAngleOffset() + deltatime * 45f);
		_miniBall.update(deltatime);
		_miniBall.setAngle(_miniBall.getAngleOffset() + deltatime * 180f);
		_miniminiBall.update(deltatime);
		_system.update(deltatime);
	}

	@Override
	public void disposeData()
	{

	}

	@Override
	public void keyDown(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_1)
		{
			_current = (_current + 1) % _modes.length;
			_path.setType(_modes[_current]);
			_path.startDuration(4f);
			_path.reset();
		}
		else if (key.getKeyCode() == KeyEvent.VK_2)
		{
			_rotatesWith = !_rotatesWith;
			_ball.setRotatesWith(_rotatesWith);
		}
	}

	@Override
	public void keyUp(KeyEvent key)
	{
	}

	@Override
	public void mouseDown(MouseEvent mouse)
	{
		_system.setEmitterOffset(mouse.getX(), HEIGHT - mouse.getY());
		_system.burst();
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
	{	return Color.white();
	}

	@Override
	public Rectangle requestCameraBounds()
	{
		return new Rectangle(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public int requestMaximumScreens()
	{
		return 12;
	}

	@Override
	public int requestMaximumSounds()
	{
		return 12;
	}

	@Override
	public int requestMaximumTextures()
	{
		return 5;
	}

	@Override
	public int requestMaximumTiles()
	{
		return 5;
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

	@Override
	public float requestMinDelta() 
	{
		return 0.1f;
	}

}
