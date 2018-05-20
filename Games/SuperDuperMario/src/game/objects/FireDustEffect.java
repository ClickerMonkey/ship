package game.objects;

import static game.GameConstants.FIREDUST_SIZE;
import static game.GameConstants.PARTICLE_SMOKE;
import static net.philsprojects.game.effects.Particle.FULL;
import net.philsprojects.game.effects.VelocityDefault;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.util.Color;
import game.Level;

/**
 * The dust special effect at Mario's feet when he walks and has the Fire Power Behavior.
 * 
 * @author Philip Diffenderfer
 */
public class FireDustEffect extends GameEffect
{
    public FireDustEffect()
    {
	super("FIRE_DUST_EFFECT");
	setTile(PARTICLE_SMOKE);
	setEmitterVolume(new VolumeDefault());
	setEmitterVelocity(new VelocityDefault());
	setBurstDelay(0.1f, 0.1f);
	setBurstCount(1, 1);
	setParticleSizes(FIREDUST_SIZE, FIREDUST_SIZE * 3);
	setParticleAlphas(0.6f, 0f);
	setParticleColors(new Color(1f, 0.75f, 0f), Color.orange(), Color.red());
	setParticleLife(0.2f);
	setParticleType(FULL);
	setManualBurst(true);
	initialize();
	Level.getInstance().addEffect(this);
    }
    
    @Override
    public boolean isUserDrawn()
    {
	return false;
    }


}
