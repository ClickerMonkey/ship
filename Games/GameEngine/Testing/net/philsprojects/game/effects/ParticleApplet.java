package net.philsprojects.game.effects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ICurve;
import net.philsprojects.game.SceneApplet;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.effects.AffectorDamping;
import net.philsprojects.game.effects.AffectorGravity;
import net.philsprojects.game.effects.BaseParticleSystem;
import net.philsprojects.game.effects.IEmitterVelocity;
import net.philsprojects.game.effects.IEmitterVolume;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.Range;
import net.philsprojects.game.effects.VelocityDefault;
import net.philsprojects.game.effects.VelocityDirectional;
import net.philsprojects.game.effects.VelocityOrtho;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.effects.VolumeEllipse;
import net.philsprojects.game.effects.VolumeLine;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Curve;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.SmoothCurve;
import net.philsprojects.game.util.Vector;


public class ParticleApplet extends SceneApplet
{

	public static final long serialVersionUID = 1L;

	public static final int GAME_SIZE = 640;

	public BaseParticleSystem<Particle> _particles = null;

	public Options<String> _textures = new Options<String>(0);

	public Options<IEmitterVolume> _volume = new Options<IEmitterVolume>(0);

	public Options<IEmitterVelocity> _velocity = new Options<IEmitterVelocity>(3);

	public Options<Integer> _types = new Options<Integer>(0);

	public Options<Color> _background = new Options<Color>(0);

	public Options<Color[]> _colorAffect = new Options<Color[]>(0);

	public Options<ICurve> _alphaAffect = new Options<ICurve>(0);

	public Options<ICurve> _sizeAffect = new Options<ICurve>(0);

	public Options<ICurve> _angleAffect = new Options<ICurve>(0);

	public Options<AffectorDamping> _dampingAffect = new Options<AffectorDamping>(7);

	public Options<AffectorGravity> _gravityAffect = new Options<AffectorGravity>(5);

	public Options<Range> _size = new Options<Range>(3);

	public Options<Float> _life = new Options<Float>(2);

	public Options<Options<? extends Object>> _allOptions = new Options<Options<? extends Object>>(0);

	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		textures.setDirectory("net/philsprojects/game/effects/");
		textures.add("Twinkle.png", "Twinkle");
		textures.add("Smoke.dds", "Smoke");
		textures.add("Sphere.png", "Sphere");
		textures.add("Star.png", "Star");
		textures.add("Bubble.png", "Bubble");
		textures.add("NightStar.png", "NightStar");
		textures.add("Splotch.png", "Splotch");
		textures.add("Snowflake.png", "Snowflake");
		textures.add("fire.png", "RealFire");
		textures.add("smoke.png", "RealSmoke");

		graphics.setFont("Courier New", 1, 12);

		/** Different particle texture options. */
		_textures.set("RealFire", "RealSmoke", "Twinkle", "Smoke", "Sphere", "Star", "Bubble", "NightStar", "Splotch", "Snowflake");
		_textures.setNames(" RealFire", "RealSmoke", " Twinkle ", "  Smoke  ", "  Sphere ", "   Star  ", "  Bubble ", "NightStar", " Splotch ", "Snowflake");
		/** Different render types for the particles. */
		_types.set(Particle.ADDITIVE, Particle.FULL, Particle.NEGATIVE, Particle.NORMAL);
		_types.setNames("Additive", "  Full  ", "Negative", " Normal ");
		/**
		 * Different background colors you may change to, to view the system
		 * appearance with different backgrounds.
		 */
		_background.set(Color.black(), Color.blue(), Color.red(), Color.yellow(), Color.green(), Color.white());
		_background.setNames(" Black ", "  Blue ", "  Red  ", " Yellow", " Green ", " White ");

		/** Different emitter volume options for the particle system. */
		_volume.add("Default", new VolumeDefault());
		_volume.add("Ellipse.Outline", new VolumeEllipse(200, 128, true));
		_volume.add("Ellipse.Fill", new VolumeEllipse(200, 128, false));
		_volume.add("Line.None", new VolumeLine(128, 128, 368, 368, VolumeLine.NONE));
		_volume.add("Line.Away", new VolumeLine(128, 128, 368, 368, VolumeLine.AWAY));
		_volume.add("Line.Outwards", new VolumeLine(128, 128, 368, 368, VolumeLine.OUTWARDS));
		_volume.add("Line.Waterfall", new VolumeLine(128, 128, 368, 368, VolumeLine.WATERFALL));

		float speedMult = 1f;
		/** Different emitter velocity options for the particle system. */
		_velocity.add("  Default  ", new VelocityDefault());
		_velocity.add("Directional", new VelocityDirectional(15f, 40f, 100 * speedMult, 200 * speedMult));
		_velocity.add("   Ortho   ", new VelocityOrtho(-200 * speedMult, 200 * speedMult, -50 * speedMult, 50 * speedMult));
		_velocity.add("  Outward1 ", new VelocityOutward(100 * speedMult, 150 * speedMult));
		_velocity.add("  Outward2 ", new VelocityOutward(150 * speedMult, 150 * speedMult));
		_velocity.add("  Outward3 ", new VelocityOutward(200 * speedMult, 400 * speedMult));
		_velocity.add("  Outward4 ", new VelocityOutward(20 * speedMult, 300 * speedMult));
		_velocity.add("  Outward5 ", new VelocityOutward(500 * speedMult, 600 * speedMult));

		/** Different transparency options for the particle system. */
		_alphaAffect.add(" Fade ", new SmoothCurve(1f, new float[] { 1f, 0f }));
		_alphaAffect.add(" Pulse", new SmoothCurve(1f, new float[] { 1f, 0.2f, 1f, 0f }));
		_alphaAffect.add("Linger", new Curve(60, new float[] { 0f, 0.9f, 1f }, new float[] { 1f, 0.9f, 0f }));
		_alphaAffect.add(" Weak ", new SmoothCurve(1f, new float[] { 0.6f, 0f }));

		/** Different color options for the particle system. */
		_colorAffect.add("  White  ", new Color[] { Color.lightgray(), Color.white() });
		_colorAffect.add("  Smoke  ", new Color[] { Color.gray(), Color.black() });
		_colorAffect.add(" Rainbow ", new Color[] { Color.red(), Color.orange(), Color.yellow(), Color.green(), Color.blue(), Color.purple() });
		_colorAffect.add("   Gold  ", new Color[] { Color.gold(), Color.yellow(), Color.white() });
		_colorAffect.add("   Fire  ", new Color[] { Color.red(), Color.orange(), Color.yellow() });
		_colorAffect.add("  Green  ", new Color[] { Color.green(), Color.blue(), Color.white() });
		_colorAffect.add("Patriotic", new Color[] { Color.red(), Color.white(), Color.blue() });
		_colorAffect.add("  Black  ", new Color[] { Color.black(), Color.darkgray() });

		/** Different particle sizes. */
		_size.add("006-012px", new Range(6f, 12f));
		_size.add("012-024px", new Range(12f, 24f));
		_size.add("024-032px", new Range(24f, 32f));
		_size.add("032-048px", new Range(32f, 48f));
		_size.add("048-064px", new Range(48f, 64f));
		_size.add("064-080px", new Range(64f, 80f));
		_size.add("080-100px", new Range(80f, 100f));
		_size.add("100-130px", new Range(100f, 130f));

		/** Different particle lifespan's. */
		_life.add("0.05s", 0.05f);
		_life.add("0.10s", 0.1f);
		_life.add("0.17s", 0.17f);
		_life.add("0.25s", 0.25f);
		_life.add("0.50s", 0.5f);
		_life.add("1.00s", 1f);
		_life.add("1.50s", 1.5f);
		_life.add("2.00s", 2f);
		_life.add("2.50s", 2.5f);
		_life.add("3.00s", 3f);
		_life.add("3.50s", 3.5f);

		/** Different velocity damping modifier options. */
		_dampingAffect.add("0.6000", new AffectorDamping(0.6f, 0.6f));
		_dampingAffect.add("0.7000", new AffectorDamping(0.7f, 0.7f));
		_dampingAffect.add("0.8000", new AffectorDamping(0.8f, 0.8f));
		_dampingAffect.add("0.9000", new AffectorDamping(0.9f, 0.9f));
		_dampingAffect.add("0.9900", new AffectorDamping(0.99f, 0.99f));
		_dampingAffect.add("0.9990", new AffectorDamping(0.999f, 0.999f));
		_dampingAffect.add("0.9999", new AffectorDamping(0.9999f, 0.9999f));
		_dampingAffect.add(" None ", new AffectorDamping(1f, 1f));
		_dampingAffect.add("1.0001", new AffectorDamping(1.0001f, 1.0001f));
		_dampingAffect.add("1.0010", new AffectorDamping(1.001f, 1.001f));
		_dampingAffect.add("1.0100", new AffectorDamping(1.01f, 1.01f));
		_dampingAffect.add("1.1000", new AffectorDamping(1.1f, 1.1f));
		_dampingAffect.add("1.2000", new AffectorDamping(1.2f, 1.2f));
		_dampingAffect.add("1.3000", new AffectorDamping(1.3f, 1.3f));
		_dampingAffect.add("1.4000", new AffectorDamping(1.4f, 1.4f));

		/** Different gravity modifier options. */
		_gravityAffect.add("-500", new AffectorGravity(-500f));
		_gravityAffect.add("-400", new AffectorGravity(-400f));
		_gravityAffect.add("-300", new AffectorGravity(-300f));
		_gravityAffect.add("-200", new AffectorGravity(-200f));
		_gravityAffect.add("-100", new AffectorGravity(-100f));
		_gravityAffect.add("None", new AffectorGravity(0f));
		_gravityAffect.add("+100", new AffectorGravity(100f));
		_gravityAffect.add("+200", new AffectorGravity(200f));
		_gravityAffect.add("+300", new AffectorGravity(300f));
		_gravityAffect.add("+400", new AffectorGravity(400f));
		_gravityAffect.add("+500", new AffectorGravity(500f));

		/** Different size modifier options. */
		_sizeAffect.add("   x1  ", new SmoothCurve(1f, new float[] { 1f, 1f }));
		_sizeAffect.add("   x2  ", new SmoothCurve(1f, new float[] { 1f, 2f }));
		_sizeAffect.add("   x3  ", new SmoothCurve(1f, new float[] { 1f, 3f }));
		_sizeAffect.add("   x4  ", new SmoothCurve(1f, new float[] { 1f, 4f }));
		_sizeAffect.add("   >0  ", new SmoothCurve(1f, new float[] { 1f, 0f }));
		_sizeAffect.add("   /2  ", new SmoothCurve(1f, new float[] { 1f, 0.5f }));
		_sizeAffect.add("   /4  ", new SmoothCurve(1f, new float[] { 1f, 0.25f }));
		_sizeAffect.add(" PulseB", new SmoothCurve(1f, new float[] { 1f, 2f, 1f }));
		_sizeAffect.add(" PulseA", new SmoothCurve(1f, new float[] { 1f, 0f, 1f }));
		_sizeAffect.add(" CurveA", new SmoothCurve(1f, new float[] { 1f, 0f, 2f, 0f, 1f }));
		_sizeAffect.add(" CurveB", new SmoothCurve(1f, new float[] { 1f, 2f, 0f, 2f, 1f }));

		/** Different angle modifier options. */
		_angleAffect.add(" Static", new SmoothCurve(1f, new float[] { 0f, 0f }));
		_angleAffect.add(" 1Spin ", new SmoothCurve(1f, new float[] { 0f, 360f }));
		_angleAffect.add(" 2Spin ", new SmoothCurve(1f, new float[] { 0f, 720f }));
		_angleAffect.add(" 3Spin ", new SmoothCurve(1f, new float[] { 0f, 1080f }));
		_angleAffect.add(" 4Spin ", new SmoothCurve(1f, new float[] { 0f, 1440f }));
		_angleAffect.add(" 5Spin ", new SmoothCurve(1f, new float[] { 0f, 1800f }));
		_angleAffect.add("  1-1  ", new SmoothCurve(1f, new float[] { 0f, 270f, 360f, 270f, 0f }));
		_angleAffect.add("  1-2  ", new SmoothCurve(1f, new float[] { 0f, 540f, 720f, 540f, 0f }));
		_angleAffect.add("  1-3  ", new SmoothCurve(1f, new float[] { 0f, 768f, 1080f, 768f, 0f }));
		_angleAffect.add("  1-4  ", new SmoothCurve(1f, new float[] { 0f, 1080f, 1440f, 1080f, 0f }));
		_angleAffect.add("  1-5  ", new SmoothCurve(1f, new float[] { 0f, 1350f, 1800f, 1350f, 0f }));

		/** Total Options List. */
		_allOptions.add("         Texture", _textures);
		_allOptions.add("        Velocity", _velocity);
		_allOptions.add("          Volume", _volume);
		_allOptions.add("            Type", _types);
		_allOptions.add("      Background", _background);
		_allOptions.add("    Color Scheme", _colorAffect);
		_allOptions.add("    Alpha Scheme", _alphaAffect);
		_allOptions.add(" Damping Modiier", _dampingAffect);
		_allOptions.add("   Size Modifier", _sizeAffect);
		_allOptions.add("  Angle Modifier", _angleAffect);
		_allOptions.add("Gravity Modifier", _gravityAffect);
		_allOptions.add("            Size", _size);
		_allOptions.add("            Life", _life);
	}

	@Override
	public void dispose()
	{
		_allOptions = null;
		_alphaAffect = null;
		_angleAffect = null;
		_background = null;
		_colorAffect = null;
		_dampingAffect = null;
		_gravityAffect = null;
		_life = null;
		_particles = null;
		_size = null;
		_sizeAffect = null;
		_textures = null;
		_types = null;
		_velocity = null;
		_volume = null;
		if (_particles != null)
		{
			_particles.dispose();
			_particles = null;
		}
	}

	@Override
	public void update(float deltatime)
	{
		if (_particles == null) {
			return;
		}
		
		_particles.update(deltatime);
		float deltaX = 0f, deltaY = 0f;
		if (isKeyDown(KeyEvent.VK_D))
			deltaX += 100 * deltatime;
		if (isKeyDown(KeyEvent.VK_A))
			deltaX -= 100 * deltatime;
		if (isKeyDown(KeyEvent.VK_W))
			deltaY += 100 * deltatime;
		if (isKeyDown(KeyEvent.VK_S))
			deltaY -= 100 * deltatime;
		if (deltaX != 0f || deltaY != 0f)
		{
			if (isKeyDown(KeyEvent.VK_SHIFT))
				_particles.move(deltaX, deltaY);
			else
				_particles.translate(deltaX, deltaY);
		}
		if (isKeyDown(KeyEvent.VK_O))
			_particles.addEmitterAngle(90 * deltatime);
		if (isKeyDown(KeyEvent.VK_P))
			_particles.addEmitterAngle(-90 * deltatime);
		if (isKeyDown(KeyEvent.VK_K))
			_particles.addEmitterScale(deltatime, deltatime);
		if (isKeyDown(KeyEvent.VK_L))
			_particles.addEmitterScale(-deltatime, -deltatime);

	}

	@Override
	public void draw(GraphicsLibrary graphics)
	{

		_particles.draw();
		graphics.setBackgroundColor(_background.get());
		graphics.beginText(GAME_SIZE, GAME_SIZE);
		graphics.setForeColor(_background.get().getInverse());
		for (int i = 0; i < _allOptions.size(); i++)
		{
			Options<? extends Object> o = _allOptions.getItem(i);
			if (i == _allOptions.current())
			{
				graphics.setForeColor(_background.get().getInverse().modAlpha(0.65f));
				graphics.drawString(_allOptions.getName(i) + ": { " + o.previousName() + " [" + o.getName() + "] " + o.nextName() + " }", 10, GAME_SIZE - 15 - (i * 15));
				graphics.setForeColor(_background.get().getInverse());
			}
			else
			{
				graphics.drawString(_allOptions.getName(i) + ": " + o.getName().trim(), 10, GAME_SIZE - 15 - (i * 15));
			}
		}
		graphics.drawString("Location :" + _particles.getLocation().toString(), 10, 65);
		graphics.drawString("Emitter Angle :" + _particles.getEmitterAngle(), 10, 50);
		graphics.drawString("Emitter Scale :" + _particles.getEmitterScale().toString(), 10, 35);
		graphics.drawString("Emitter Offset :" + _particles.getEmitterOffset().toString(), 10, 20);
		graphics.drawString(_particles.getParticlesAlive() + " particles at " + (int)getTimer().getFrameRate() + "fps", 10, 5);
		graphics.endText();
	}

	@Override
	public void keyDown(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_DOWN)
			_allOptions.next();
		else if (key.getKeyCode() == KeyEvent.VK_UP)
			_allOptions.previous();
		if (key.getKeyCode() == KeyEvent.VK_1)
			try
		{
				saveSystem("System.data");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		else if (key.getKeyCode() == KeyEvent.VK_2)
			try
		{
				loadSystem("System.data");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (_allOptions.get() == _textures)
				_particles.setTile(_textures.next());
			else if (_allOptions.get() == _volume)
				_particles.setEmitterVolume(_volume.next());
			else if (_allOptions.get() == _velocity)
				_particles.setEmitterVelocity(_velocity.next());
			else if (_allOptions.get() == _types)
				_particles.setParticleType(_types.next());
			else if (_allOptions.get() == _background)
				_background.next();
			else if (_allOptions.get() == _colorAffect)
				_particles.setParticleColors(_colorAffect.next());
			else if (_allOptions.get() == _alphaAffect)
				_particles.setParticleAlphas(_alphaAffect.next());
			else if (_allOptions.get() == _dampingAffect)
				_particles.overrideAffector(_dampingAffect.next());
			else if (_allOptions.get() == _gravityAffect)
				_particles.overrideAffector(_gravityAffect.next());
			else if (_allOptions.get() == _sizeAffect)
			{
				ICurve c = _sizeAffect.next().getClone();
				c.scale(_size.get().randomFloat());
				_particles.setParticleSizes(c);
			}
			else if (_allOptions.get() == _angleAffect)
				_particles.setParticleAngles(_angleAffect.next());
			else if (_allOptions.get() == _size)
			{
				ICurve c = _sizeAffect.get().getClone();
				c.scale(_size.next().randomFloat());
				_particles.setParticleSizes(c);
			}
			else if (_allOptions.get() == _life)
				_particles.setParticleLife(_life.next(), _life.get());
		}
		else if (key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (_allOptions.get() == _textures)
				_particles.setTile(_textures.previous());
			else if (_allOptions.get() == _volume)
				_particles.setEmitterVolume(_volume.previous());
			else if (_allOptions.get() == _velocity)
				_particles.setEmitterVelocity(_velocity.previous());
			else if (_allOptions.get() == _types)
				_particles.setParticleType(_types.previous());
			else if (_allOptions.get() == _background)
				_background.previous();
			else if (_allOptions.get() == _colorAffect)
				_particles.setParticleColors(_colorAffect.previous());
			else if (_allOptions.get() == _alphaAffect)
				_particles.setParticleAlphas(_alphaAffect.previous());
			else if (_allOptions.get() == _dampingAffect)
				_particles.overrideAffector(_dampingAffect.previous());
			else if (_allOptions.get() == _gravityAffect)
				_particles.overrideAffector(_gravityAffect.previous());
			else if (_allOptions.get() == _sizeAffect)
			{
				ICurve c = _sizeAffect.previous().getClone();
				c.scale(_size.get().randomFloat());
				_particles.setParticleSizes(c);
			}
			else if (_allOptions.get() == _angleAffect)
				_particles.setParticleAngles(_angleAffect.previous());
			else if (_allOptions.get() == _size)
			{
				ICurve c = _sizeAffect.get().getClone();
				c.scale(_size.previous().randomFloat());
				_particles.setParticleSizes(c);
			}
			else if (_allOptions.get() == _life)
				_particles.setParticleLife(_life.previous(), _life.get());
		}
	}

	@Override
	public void keyUp(KeyEvent key)
	{
	}

	@Override
	public void mouseDown(MouseEvent mouse)
	{
	}

	private Vector current = new Vector(128f, 128f), last = Vector.zero();

	@Override
	public void mouseMove(MouseEvent mouse)
	{
		last.set(current);
		current.set(mouse.getX(), mouse.getY());
		if (mouse.isShiftDown())
		{
			_particles.move(current.x - last.x, last.y - current.y);
		}
		else if (_velocity.get() instanceof VelocityDirectional)
		{
			VelocityDirectional d = (VelocityDirectional)_velocity.get();
			float angle = 360 - Math.angle(_particles.getScreenX(), _particles.getScreenY(), mouse.getX(), mouse.getY());
			d.setAngles(angle - 20, angle + 20);
		}
	}

	public void saveSystem(String filepath) throws Exception
	{
		FileOutputStream fileStream = new FileOutputStream(filepath);
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject (_particles);
		objectStream.writeObject(_allOptions);
	}

	@SuppressWarnings("unchecked")
	public void loadSystem(String filepath) throws Exception
	{
		FileInputStream fileStream = new FileInputStream(filepath);
		ObjectInputStream objectStream = new ObjectInputStream(fileStream);
		_particles = (BaseParticleSystem<Particle>)objectStream.readObject();
		_allOptions = (Options<Options<? extends Object>>)objectStream.readObject();
	}

	@Override
	public void mouseUp(MouseEvent mouse)
	{

	}

	@Override
	public int requestAppletHeight()
	{
		return GAME_SIZE;
	}

	@Override
	public int requestAppletWidth()
	{
		return GAME_SIZE;
	}

	@Override
	public Color requestBackgroundColor()
	{
		return Color.black();
	}

	@Override
	public Rectangle requestCameraBounds()
	{
		return new Rectangle(0, 0, GAME_SIZE, GAME_SIZE);
	}

	@Override
	public int requestMaximumTextures()
	{
		return 13;
	}

	@Override
	public int requestUpdatesPerSecond()
	{
		return 4;
	}

	@Override
	public int requestMaximumSounds()
	{
		return 0;
	}

	@Override
	public int requestMaximumScreens()
	{
		return 0;
	}

	@Override
	public int requestMaximumTiles()
	{
		return 0;
	}

	/**
	 * Used to store different options that we can iterate over and loop back
	 * through.
	 */
	@SuppressWarnings("serial")
	public class Options<T> implements Serializable
	{

		private LinkedList<T> _items = new LinkedList<T>();

		private LinkedList<String> _itemsNames = new LinkedList<String>();

		private int _currentItem = 0;

		public Options(int current)
		{
			_currentItem = current;
		}

		public void set(T... t)
		{
			for (int i = 0; i < t.length; i++)
			{
				_items.add(t[i]);
			}
		}

		public void setNames(String... names)
		{
			for (int i = 0; i < names.length; i++)
			{
				_itemsNames.add(names[i]);
			}
		}

		public void add(String name, T item)
		{
			_itemsNames.add(name);
			_items.add(item);
		}

		public T get()
		{
			return _items.get(_currentItem);
		}

		public String getName()
		{
			return _itemsNames.get(_currentItem);
		}

		public T next()
		{
			_currentItem++;
			if (_currentItem > _items.getSize() - 1)
				_currentItem = 0;
			return _items.get(_currentItem);
		}

		public T previous()
		{
			_currentItem--;
			if (_currentItem < 0)
				_currentItem = _items.getSize() - 1;
			return _items.get(_currentItem);
		}

		public String nextName()
		{
			return _itemsNames.get(_currentItem == _items.getSize() - 1 ? 0 : _currentItem + 1);
		}

		public String previousName()
		{
			return _itemsNames.get(_currentItem == 0 ? _items.getSize() - 1 : _currentItem - 1);
		}

		public int current()
		{
			return _currentItem;
		}

		public int size()
		{
			return _items.getSize();
		}

		public T getItem(int index)
		{
			return _items.get(index);
		}

		public String getName(int index)
		{
			return _itemsNames.get(index);
		}

	}

}
