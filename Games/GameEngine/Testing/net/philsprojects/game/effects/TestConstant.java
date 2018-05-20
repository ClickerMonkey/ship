package net.philsprojects.game.effects;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.effects.ConstantParticleSystem;
import net.philsprojects.game.effects.Particle;

public class TestConstant extends ParticleApplet
{

	private static final long serialVersionUID = 1L;
	private static final int GAME_SIZE = 512;

	private ConstantParticleSystem<Particle> _constant = null;

	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		super.load(textures, sounds, screens, tiles, graphics);

		_constant = new ConstantParticleSystem<Particle>(Particle.class, "Effect", 128);
		_constant.setLocation(GAME_SIZE / 2f, GAME_SIZE / 2f);
		_constant.setParticleLife(_life.get(), _life.get());
		_constant.setTile(_textures.get());
		_constant.setEmitterVelocity(_velocity.get());
		_constant.setEmitterVolume(_volume.get());
		_constant.setParticleType(_types.get());
		_constant.setParticleAlphas(_alphaAffect.get());
		_constant.setParticleColors(_colorAffect.get());
		_constant.setParticleSizes(_sizeAffect.get());
		_constant.setParticleAngles(_angleAffect.get());
		_constant.addAffector(_dampingAffect.get());
		_constant.addAffector(_gravityAffect.get());
		_constant.initialize();

		_particles = _constant;
	}

}
