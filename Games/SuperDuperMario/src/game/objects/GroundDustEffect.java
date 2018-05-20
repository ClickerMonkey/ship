package game.objects;

import static game.GameConstants.GROUNDDUST_SIZE;
import static game.GameConstants.PARTICLE_SMOKE;
import static net.philsprojects.game.effects.Particle.FULL;
import net.philsprojects.game.effects.VelocityDefault;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.util.Color;
import game.Level;

/**
 * The dust special effect at Mario's feet when he walks and has no special behavior or he has the star behavior.
 * 
 * @author Philip Diffenderfer
 */
public class GroundDustEffect extends GameEffect
{

    public GroundDustEffect()
    {
	super("GROUND_DUST_EFFECT");
	setTile(PARTICLE_SMOKE);
	setEmitterVolume(new VolumeDefault());
	setEmitterVelocity(new VelocityDefault());
	setBurstDelay(0.2f, 0.2f);
	setBurstCount(1, 1);
	setParticleSizes(GROUNDDUST_SIZE, GROUNDDUST_SIZE * 3);
	setParticleAlphas(0.6f, 0f);
	setParticleColors(Color.delta(Color.gray(), Color.brown(), 0.5f), Color.brown());
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
