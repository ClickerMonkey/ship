package net.philsprojects.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.ChangerColor;
import net.philsprojects.game.Constants;
import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ICurve;
import net.philsprojects.game.Landscape;
import net.philsprojects.game.SceneApplet;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.TiledLayer;
import net.philsprojects.game.effects.BurstParticleSystem;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeEllipse;
import net.philsprojects.game.sprites.AnimatedSprite;
import net.philsprojects.game.sprites.SpriteTileAnimated;
import net.philsprojects.game.sprites.SpriteTileFramed;
import net.philsprojects.game.sprites.SpriteTileStatic;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.SmoothCurve;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.game.util.Math.*;

public class TestApplet extends SceneApplet
{

	private static final long serialVersionUID = 2964126328747033678L;

	private static final int GAME_WIDTH = 768;
	private static final int GAME_HEIGHT = 512;
	private static final Color BACKGROUND = new Color(79, 139, 225);

	private Landscape farHills, nearHills;
	private TiledLayer map;
	private AnimatedSprite mario;


	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		textures.setDirectory("net/philsprojects/game/");
		textures.add("Hills1.png", "FarHills");
		textures.add("Hills2.png", "NearHills");
		textures.add("TileSheet.png", "TileSheet");
		textures.add("MarioNormal.png", "Mario");
		textures.add("MarioFire.png", "MarioFire");
		textures.add("MarioStar.png", "MarioStar");
		textures.add("MarioIce.png", "MarioIce");
		textures.add("Star.png", "Star");

		GraphicsLibrary.getInstance().setupSprite();

		nearHills = new Landscape(new SpriteTileStatic("", "NearHills", 0, 0, 512, 246), 512, 246, -20, 0.5f, 0.5f);
		farHills = new Landscape(new SpriteTileStatic("", "FarHills", 0, 0, 512, 246), 512, 246, 40, 0.25f, 0.25f);

		/** Setup up all game tiles. */
		tiles.add(new SpriteTileAnimated("Goomba.Walking", "TileSheet", Constants.LOOP_FORWARD, 2, 2, 40, 40, 120, 120, 0.2f));
		tiles.add(new SpriteTileAnimated("Question.Scrolling", "TileSheet", Constants.LOOP_BACKWARD, 5, 5, 40, 40, 0, 160, 0.1f));
		tiles.add(new SpriteTileAnimated("Coin.Spinning", "TileSheet", Constants.LOOP_FORWARD, 4, 4, 40, 40, 0, 200, 0.1f));
		tiles.add(new SpriteTileStatic("Star", "TileSheet", 200, 0, 40, 40));
		tiles.add(new SpriteTileStatic("GreenMushroom", "TileSheet", 200, 120, 40, 40));
		tiles.add(new SpriteTileStatic("RedMushroom", "TileSheet", 200, 40, 40, 40));
		tiles.add(new SpriteTileStatic("Exclamation", "TileSheet", 120, 80, 40, 40));
		tiles.add(new SpriteTileStatic("Hard", "TileSheet", 160, 80, 40, 40));
		tiles.add(new SpriteTileStatic("Block", "TileSheet", 200, 80, 40, 40));
		tiles.add(new SpriteTileStatic("PipeLeft", "TileSheet", 120, 40, 40, 40));
		tiles.add(new SpriteTileStatic("PipeRight", "TileSheet", 160, 40, 40, 40));
		tiles.add(new SpriteTileStatic("PipeTopLeft", "TileSheet", 120, 0, 40, 40));
		tiles.add(new SpriteTileStatic("PipeTopRight", "TileSheet", 160, 0, 40, 40));
		// Map tiles are set up in North.East.South.West for what they are
		// Either G(Grass) D(Dirt) A(Air)
		tiles.add(new SpriteTileStatic("GADD", "TileSheet", 0, 0, 40, 40));
		tiles.add(new SpriteTileStatic("DDDD", "TileSheet", 40, 0, 40, 40));
		tiles.add(new SpriteTileStatic("GDDA", "TileSheet", 80, 0, 40, 40));
		tiles.add(new SpriteTileStatic("DADD", "TileSheet", 0, 40, 40, 40));
		tiles.add(new SpriteTileStatic("DDAD", "TileSheet", 40, 40, 40, 40));
		tiles.add(new SpriteTileStatic("DDDA", "TileSheet", 80, 40, 40, 40));
		tiles.add(new SpriteTileStatic("DGDD", "TileSheet", 0, 80, 40, 40));
		tiles.add(new SpriteTileStatic("GDDD", "TileSheet", 40, 80, 40, 40));
		tiles.add(new SpriteTileStatic("DDDG", "TileSheet", 80, 80, 40, 40));
		tiles.add(new SpriteTileStatic("GDAA", "TileSheet", 0, 120, 40, 40));
		tiles.add(new SpriteTileStatic("GDAD", "TileSheet", 40, 120, 40, 40));
		tiles.add(new SpriteTileStatic("GAAD", "TileSheet", 80, 120, 40, 40));
		// Mario's animations
		tiles.add(new SpriteTileStatic("Mario.Standing", "Mario", 0, 0, 167, 212));
		tiles.add(new SpriteTileStatic("Mario.Jumping", "Mario", 334, 424, 167, 212));
		tiles.add(new SpriteTileStatic("Mario.Falling", "Mario", 167, 424, 167, 212));
		tiles.add(new SpriteTileFramed("Mario.Throwing", "Mario", Constants.ONCE_FORWARD, 3, 167, 212, 0.05f, new int[] { 0, 5, 6, 6, 6, 5, 0 }));
		tiles.add(new SpriteTileFramed("Mario.Running", "Mario", Constants.LOOP_FORWARD, 3, 167, 212, 0.05f, new int[] { 0, 3, 4, 3, 0, 1, 2, 1 }));

		map = new TiledLayer(22, 0);
		map.setTileSize(40, 40);
		map.setTexture("TileSheet");
		/** Set Map Elements. */
		// Index, SpriteTile, Right, Left, Top, Bottom
		map.setElement(1, tiles.get("GADD"), true, true, false, false);
		map.setElement(2, tiles.get("DDDD"), false, false, false, false);
		map.setElement(3, tiles.get("GDDA"), true, false, false, true);
		map.setElement(4, tiles.get("DADD"), false, true, false, false);
		map.setElement(5, tiles.get("DDAD"), false, false, true, false);
		map.setElement(6, tiles.get("DDDA"), false, false, false, true);
		map.setElement(7, tiles.get("DGDD"), false, false, false, false);
		map.setElement(8, tiles.get("GDDD"), true, false, false, false);
		map.setElement(9, tiles.get("DDDG"), false, false, false, false);
		map.setElement(10, tiles.get("GDAA"), true, false, false, false);
		map.setElement(11, tiles.get("GDAD"), true, false, false, false);
		map.setElement(12, tiles.get("GAAD"), true, false, false, false);
		map.setElement(13, tiles.get("Coin.Spinning"), false, false, false, false, 0.75f);
		map.setElement(14, tiles.get("Question.Scrolling"), true, true, true, true);
		map.setElement(15, tiles.get("Hard"), true, true, true, true);
		map.setElement(16, tiles.get("Block"), true, true, true, true);
		map.setElement(17, tiles.get("Exclamation"), true, true, true, true);
		map.setElement(18, tiles.get("PipeLeft"), true, true, true, true);
		map.setElement(19, tiles.get("PipeRight"), true, true, true, true);
		map.setElement(20, tiles.get("PipeTopLeft"), true, true, true, true);
		map.setElement(21, tiles.get("PipeTopRight"), true, true, true, true);
		/** Draw a map to test. */
		byte[][] data = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 16, 16, 16, 14, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 13, 0, 0, 0, 13, 0, 0, 13, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 20, 21, 0, 0, 3, 8, 8, 1, 0, 0, 3, 8, 8, 9, 4, 0, 0, 10, 11, 11, 12, 0 }, { 0, 18, 19, 0, 0, 6, 2, 2, 4, 0, 0, 6, 2, 2, 2, 4, 0, 0, 0, 0, 0, 0, 0 },
				{ 8, 8, 8, 8, 8, 9, 2, 2, 4, 0, 0, 6, 2, 2, 2, 4, 0, 0, 0, 0, 0, 0, 0 } };
		map.setLayerData(data);

		float scale = 0.3f;
		mario = new AnimatedSprite("Mario", 50, 114, 167 * scale, 212 * scale, 5);
		mario.addAnimation(tiles.get("Mario.Jumping"));
		mario.addAnimation(tiles.get("Mario.Falling"));
		mario.addAnimation(tiles.get("Mario.Throwing"));
		mario.addAnimation(tiles.get("Mario.Running"));
		mario.addAnimation(tiles.get("Mario.Standing"));
		mario.playAnimation("Mario.Standing");

		starPowerEffects = new BurstParticleSystem.Adapter<Particle>(Particle.class, "Effect")
		{
			@Override
			public void loadSystem()
			{
				setLocation(mario.getX() + mario.getWidth() / 2f, mario.getY() + mario.getHeight() / 2f);
				setTile("Star");
				setParticleType(Particle.FULL);
				setBurstDelay(0.01f, 0.01f);
				setBurstCount(3, 3);
				setParticleLife(0.5f);
				setParticleSizes(32f, 32f);
				setParticleAlphas(1f, 0f);
				setParticleAngles(0f, 90f, 270f);
				setParticleColors(Color.red(), Color.orange(), Color.yellow(), Color.green(), Color.blue(), Color.purple());
				setEmitterVelocity(new VelocityOutward(100, 150));
				setEmitterVolume(new VolumeEllipse((int)mario.getWidth(), (int)mario.getHeight(), true));
			}
		};

	}

	@Override
	public void dispose()
	{
		map = null;
		mario = null;
		farHills = null;
		nearHills = null;
		if (starPowerEffects != null)
		{
			starPowerEffects.dispose();
			starPowerEffects = null;
		}
	}

	// Star Power
	private BurstParticleSystem<Particle> starPowerEffects = null;
	private ChangerColor starPower = new ChangerColor("StarPower", LOOP_FORWARD, 0.4f, new Color[] { Color.white(), Color.red(), Color.orange(), Color.yellow(), Color.green(), Color.blue(),
			Color.purple() });
	private boolean starPowerON = false;
	// Fire power
	private BurstParticleSystem<Particle> firePowerEffects = null;
	private boolean firePowerON = false;
	// Mario's variables
	private float runSpeed = 230f;
	private float jumpDuration = 0.75f;
	private float jumpTime = 0f;
	private ICurve jumpCurve = new SmoothCurve(jumpDuration, new float[] { 600, 320, 240, 40 });
	private float fallTime = 0f;
	private ICurve fallCurve = new SmoothCurve(jumpDuration, new float[] { 40, 240, 320, 600 });

	private int direction = NONE;

	private boolean jumping = false;
	private boolean falling = false;
	private boolean jumpLock = false;

	public void jump(boolean gettingAir)
	{
		if (!jumping && !jumpLock && gettingAir)
			jumpTime = 0f;
		if (falling)
			jumping = false;
		else
			jumping = gettingAir;
	}

	public void fall(boolean isFalling)
	{
		if (!isFalling)
		{
			if (jumpLock)
			{
				jumpLock = false;
				jumping = false;
			}
			fallTime = 0f;
		}
		falling = isFalling;
	}

	public void move(float deltatime)
	{
		if (direction != NONE)
			mario.setX(mario.getX() + runSpeed * deltatime * direction);
		if (jumping)
		{
			jumpLock = true;
			falling = false;
			if (jumpTime < jumpDuration)
			{
				mario.setY(mario.getY() + jumpCurve.getValue(jumpTime) * deltatime);
				jumpTime += deltatime;
			}
			else
			{
				jumping = false;
			}
		}
		if (falling)
		{
			mario.setY(mario.getY() - fallCurve.getValue(fallTime) * deltatime);
			fallTime += deltatime;
		}
	}

	@Override
	public void update(float deltatime)
	{
		move(deltatime);
		if (direction != NONE)
			mario.playAnimation("Mario.Running");
		else
			mario.playAnimation("Mario.Standing");
		if (mario.getY() > 114)
			fall(true);
		else
		{
			fall(false);
			mario.setY(114);
		}
		if (falling)
			mario.playAnimation("Mario.Falling");
		if (jumping)
			mario.playAnimation("Mario.Jumping");
		mario.setX(mod(mario.getX(), GAME_WIDTH));
		mario.setY(mod(mario.getY(), GAME_HEIGHT));
		if (starPowerON)
		{
			starPower.update(deltatime);
			mario.setShade(starPower.getColor());
			starPowerEffects.update(deltatime);
			starPowerEffects.setLocation(mario.getX() + mario.getWidth() / 2f, mario.getY() + mario.getHeight() / 2f);
		}
		mario.update(deltatime);
		map.update(deltatime);
	}

	@Override
	public void draw(GraphicsLibrary graphics)
	{
		graphics.setupSprite();
		farHills.draw(graphics);
		nearHills.draw(graphics);
		map.draw(graphics);
		if (starPowerON)
			starPowerEffects.draw();
		if (firePowerON)
			firePowerEffects.draw();
		mario.draw();
		graphics.beginText(GAME_WIDTH, GAME_HEIGHT);
		graphics.drawString(String.valueOf(getTimer().getFrameRate()), 10, GAME_HEIGHT - 15);
		graphics.endText();
	}

	@Override
	public void keyDown(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_1)
		{
			mario.changeTexture("Mario");
			// firePowerON = false;
			starPowerON = false;
			mario.setShade(Color.white());
		}
		else if (key.getKeyCode() == KeyEvent.VK_2)
		{
			mario.changeTexture("MarioFire");
			// firePowerON = true;
			starPowerON = false;
			mario.setShade(Color.white());
		}
		else if (key.getKeyCode() == KeyEvent.VK_3)
		{
			mario.changeTexture("MarioStar");
			// firePowerON = false;
			starPowerON = true;
			starPower.reset();
			starPower.start();
			starPower.update(0f);
			starPowerEffects.initialize();
		}
		else if (key.getKeyCode() == KeyEvent.VK_4)
		{
			mario.changeTexture("MarioIce");
			starPowerON = false;
			mario.setShade(Color.white());
		}
		else if (key.getKeyCode() == KeyEvent.VK_5)
		{
			mario.setSize(167 * 0.3f, 212 * 0.3f);
		}
		else if (key.getKeyCode() == KeyEvent.VK_6)
		{
			mario.setSize(167 * 0.6f, 212 * 0.6f);
		}
		else if (key.getKeyCode() == KeyEvent.VK_7)
		{
			mario.setSize(167, 212);
		}

		if (key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			direction = BACKWARD;
			mario.setFlip(FLIP_X);
		}
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			direction = FORWARD;
			mario.setFlip(FLIP_NONE);
		}
		else if (key.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump(true);
		}
	}

	@Override
	public void keyUp(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			direction = NONE;
		}
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			direction = NONE;
		}
		else if (key.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump(false);
		}
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
	public Rectangle requestCameraBounds()
	{
		return new Rectangle(0, 0, GAME_WIDTH, GAME_HEIGHT);
	}

	@Override
	public int requestMaximumTextures()
	{
		return 11;
	}

	@Override
	public int requestUpdatesPerSecond()
	{
		return 4;
	}

	@Override
	public int requestAppletHeight()
	{
		return GAME_HEIGHT;
	}

	@Override
	public int requestAppletWidth()
	{
		return GAME_WIDTH;
	}

	@Override
	public Color requestBackgroundColor()
	{
		return BACKGROUND;
	}

	@Override
	public int requestMaximumSounds()
	{
		return 128;
	}

	@Override
	public int requestMaximumScreens()
	{
		return 128;
	}

	public int requestMaximumTiles()
	{
		return 128;
	}

}
