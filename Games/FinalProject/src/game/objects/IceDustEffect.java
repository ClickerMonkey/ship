package game.objects;

import static game.GameConstants.ICEDUST_SIZE;
import static game.GameConstants.PARTICLE_SMOKE;
import static net.philsprojects.game.effects.Particle.ADDITIVE;
import net.philsprojects.game.effects.VelocityDefault;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.util.Color;
import game.Level;

/**
 * The dust special effect at Mario's feet when he walks and has the Ice Power Behavior.
 * 
 * @author Philip Diffenderfer
 */
public class IceDustEffect extends GameEffect
{

    public IceDustEffect()
    {
	super("ICE_DUST_EFFECT");
	setTile(PARTICLE_SMOKE);
	setEmitterVolume(new VolumeDefault());
	setEmitterVelocity(new VelocityDefault());
	setBurstDelay(0.1f, 0.1f);
	setBurstCount(1, 1);
	setParticleSizes(ICEDUST_SIZE, ICEDUST_SIZE * 3);
	setParticleAlphas(0.8f, 0.2f);
	setParticleColors(new Color(0f, 0.75f, 0.75f), Color.black());
	setParticleLife(0.35f);
	setParticleType(ADDITIVE);
	setManualBurst(true);
	initialize();
	Level.getInstance().addEffect(this);
    }
    
    @Override
    public boolean isUserDrawn()
    {
	// TODO Auto-generated method stub
	return false;
    }

}
