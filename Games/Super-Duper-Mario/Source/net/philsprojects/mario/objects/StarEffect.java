package net.philsprojects.mario.objects;

import static net.philsprojects.game.effects.Particle.FULL;
import static net.philsprojects.mario.GameConstants.PARTICLE_NIGHTSTAR;
import static net.philsprojects.mario.GameConstants.STAR_HEIGHT;
import static net.philsprojects.mario.GameConstants.STAR_WIDTH;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeEllipse;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Curve;
import net.philsprojects.mario.Level;

/**
 * The special effect thats attached to the Star item.
 * 
 * @author Philip Diffenderfer
 */
public class StarEffect extends GameEffect
{

	public StarEffect()
	{
		super("STAR_EFFECT");
		setTile(PARTICLE_NIGHTSTAR);
		setEmitterVolume(new VolumeEllipse((int)STAR_WIDTH / 2, (int)STAR_HEIGHT / 2, true));
		setEmitterVelocity(new VelocityOutward(100, 100));
		setBurstDelay(0.01f, 0.01f);
		setBurstCount(1, 1);
		setParticleSizes(28, 14);
		setParticleAlphas(new Curve(60, new float[] { 0f, 0.9f, 1f }, new float[] { 1f, 0.9f, 0f }));
		setParticleColors(Color.white(), Color.white());
		setParticleLife(0.25f);
		setParticleType(FULL);
		initialize();
		Level.getInstance().addEffect(this);
	}

	@Override
	public boolean isUserDrawn()
	{
		return true;
	}

}
