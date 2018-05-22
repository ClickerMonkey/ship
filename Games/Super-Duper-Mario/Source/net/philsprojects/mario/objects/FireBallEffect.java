package net.philsprojects.mario.objects;

import static net.philsprojects.game.effects.Particle.FULL;
import static net.philsprojects.mario.GameConstants.FIREBALL_HALFSIZE;
import static net.philsprojects.mario.GameConstants.PARTICLE_SMOKE;
import net.philsprojects.game.effects.VelocityDefault;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.util.Color;
import net.philsprojects.mario.Level;

/**
 * The special effect thats attached to the FireBall projectile.
 * 
 * @author Philip Diffenderfer
 */
public class FireBallEffect extends GameEffect
{

	public FireBallEffect()
	{
		super("FIREBALL_EFFECT");
		setTile(PARTICLE_SMOKE);
		setEmitterVolume(new VolumeDefault());
		setEmitterVelocity(new VelocityDefault());
		setBurstDelay(0.01f, 0.01f);
		setBurstCount(1, 1);
		setParticleSizes(FIREBALL_HALFSIZE * 3, FIREBALL_HALFSIZE);
		setParticleAlphas(0.8f, 0.6f);
		setParticleColors(new Color(1f, 0.75f, 0f), Color.orange(), Color.red());
		setParticleLife(0.08f);
		setParticleType(FULL);
		initialize();
		Level.getInstance().addEffect(this);
	}

	@Override
	public boolean isUserDrawn()
	{
		return false;
	}

}
