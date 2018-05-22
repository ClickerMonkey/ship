package net.philsprojects.game.effects;

import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ICurve;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.effects.BurstParticleSystem;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.Range;


public class TestBurst extends ParticleApplet
{

	private static final long serialVersionUID = 1L;

	private BurstParticleSystem<Particle> _burst = null;

	private Options<Range> _count = new Options<Range>(3);

	private Options<Range> _delay = new Options<Range>(4);

	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		super.load(textures, sounds, screens, tiles, graphics);

		/** Different burst count options. */
		_count.add("1-1", new Range(1, 1));
		_count.add("1-2", new Range(1, 2));
		_count.add("2-4", new Range(2, 4));
		_count.add("4-8", new Range(4, 8));
		_count.add("8-16", new Range(8, 16));
		_count.add("16-24", new Range(16, 24));
		_count.add("24-32", new Range(24, 32));
		_count.add("32-40", new Range(32, 40));
		_count.add("40-48", new Range(40, 48));
		_count.add("48-56", new Range(48, 56));
		_count.add("56-64", new Range(56, 64));
		_count.add("64-80", new Range(64, 80));
		_count.add("80-96", new Range(80, 96));
		_count.add("96-112", new Range(96, 112));
		_count.add("112-128", new Range(112, 128));

		/** Different burst delay options. */
		_delay.add("   5ms", new Range(0.005f, 0.005f));
		_delay.add("  10ms", new Range(0.01f, 0.01f));
		_delay.add("17.5ms", new Range(0.0175f, 0.0175f));
		_delay.add("  25ms", new Range(0.025f, 0.025f));
		_delay.add("  50ms", new Range(0.05f, 0.05f));
		_delay.add(" 100ms", new Range(0.1f, 0.1f));
		_delay.add(" 200ms", new Range(0.2f, 0.2f));
		_delay.add(" 400ms", new Range(0.4f, 0.4f));
		_delay.add(" 600ms", new Range(0.6f, 0.6f));
		_delay.add(" 750ms", new Range(0.75f, 0.75f));
		_delay.add("    1s", new Range(1f, 1f));
		_delay.add(" 1.25s", new Range(1.25f, 1.25f));
		_delay.add("  1.5s", new Range(1.5f, 1.5f));
		_delay.add("    2s", new Range(2f, 2f));
		_delay.add("  3.5s", new Range(3.5f, 3.5f));
		_delay.add("    5s", new Range(5f, 5f));

		_allOptions.add("     Burst Count", _count);
		_allOptions.add("     Burst Delay", _delay);

		_burst = new BurstParticleSystem<Particle>(Particle.class, "Effect");
		_burst.setLocation(GAME_SIZE / 2f, GAME_SIZE / 2f);
		_burst.setBurstDelay(_delay.get());
		_burst.setBurstCount(_count.get());
		_burst.setParticleLife(_life.get(), _life.get());
		_burst.setTile(_textures.get());
		_burst.setEmitterVelocity(_velocity.get());
		_burst.setEmitterVolume(_volume.get());
		_burst.setParticleType(_types.get());
		_burst.setParticleAlphas(_alphaAffect.get());
		_burst.setParticleColors(_colorAffect.get());
		ICurve c = _sizeAffect.get().getClone();
		c.scale(_size.get().randomFloat());
		_burst.setParticleSizes(c);
		_burst.setParticleAngles(_angleAffect.get());
		_burst.addAffector(_dampingAffect.get());
		_burst.addAffector(_gravityAffect.get());
		_burst.initialize();

		_particles = _burst;
	}

	@Override
	public void dispose()
	{
		super.dispose();
		_count = null;
		_delay = null;
	}


	@Override
	public void keyDown(KeyEvent key)
	{
		super.keyDown(key);
		if (key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (_allOptions.get() == _count)
				_burst.setBurstCount(_count.next());
			else if (_allOptions.get() == _delay)
				_burst.setBurstDelay(_delay.next());
		}
		else if (key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (_allOptions.get() == _count)
				_burst.setBurstCount(_count.previous());
			else if (_allOptions.get() == _delay)
				_burst.setBurstDelay(_delay.previous());
		}
		else if (key.getKeyCode() == KeyEvent.VK_S)
			save();
	}

	public void save()
	{
		try
		{
			FileOutputStream stream = new FileOutputStream(_particles.getName() + ".ini");
			OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
			for (int i = 0; i < _allOptions.size(); i++)
			{
				writer.write(_allOptions.getName(i) + ": " + _allOptions.getItem(i).getName() + "\n");
			}
			writer.flush();
			writer.close();
			writer = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
