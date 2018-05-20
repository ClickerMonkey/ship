package game.objects;

import static game.GameConstants.*;
import static net.philsprojects.game.effects.Particle.FULL;
import net.philsprojects.game.effects.VelocityOutward;
import net.philsprojects.game.effects.VolumeEllipse;
import net.philsprojects.game.util.Color;
import game.Level;

/**
 * The special effect that happens when Mario picks up a Coin.
 * 
 * @author Philip Diffenderfer
 */
public class CoinEffect extends GameEffect
{
    
    public CoinEffect(float x, float y)
    {
	super("COIN_EFFECT", 5);
	setLocation(x, y);
	setTile(PARTICLE_NIGHTSTAR);
	setEmitterVolume(new VolumeEllipse(20, 20, true));
	setEmitterVelocity(new VelocityOutward(100, 200));
	setBurstDelay(0.01f, 0.01f);
	setBurstCount(5, 5);
	setParticleSizes(28, 8);
	setParticleAlphas(1f, 0.3f);
	setParticleColors(Color.yellow(), Color.white());
	setParticleLife(0.75f);
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
