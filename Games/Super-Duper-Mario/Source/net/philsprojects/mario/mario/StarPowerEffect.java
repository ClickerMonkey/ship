package net.philsprojects.mario.mario;

import static net.philsprojects.mario.GameConstants.PARTICLE_NIGHTSTAR;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeEllipse;
import net.philsprojects.game.util.Color;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.objects.GameEffect;

public class StarPowerEffect extends GameEffect
{

	public StarPowerEffect(Mario mario)
	{
		super("STARPOWER_EFFECT");
		setTile(PARTICLE_NIGHTSTAR);
		setParticleType(Particle.FULL);
		setBurstDelay(0.01f, 0.01f);
		setBurstCount(1, 1);
		setParticleLife(0.5f);
		setParticleSizes(32f, 32f);
		setParticleAlphas(1f, 0f);
		setParticleColors(Color.white(), Color.white());
		setEmitterVelocity(new VelocityOutward(100, 150));
		setEmitterVolume(new VolumeEllipse((int)mario.getWidth() / 2, (int)mario.getHeight() / 2, true));
		initialize();
		Level.getInstance().addEffect(this);
	}

	@Override
	public boolean isUserDrawn()
	{
		return false;
	}


}
