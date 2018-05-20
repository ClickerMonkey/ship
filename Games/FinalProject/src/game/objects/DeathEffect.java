package game.objects;

import static game.GameConstants.DEATH_EFFECT_SIZE;
import static game.GameConstants.PARTICLE_SMOKE;
import static net.philsprojects.game.effects.Particle.ADDITIVE;
import static net.philsprojects.game.effects.Particle.FULL;
import net.philsprojects.game.effects.VelocityOrtho;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import game.Level;

/**
 * The special effect that occurs when any enemy entity dies. It could differ depending on what killed
 *      the enemy, such as Fire or Ice.
 *      
 * @author Philip Diffenderfer
 */
public class DeathEffect extends GameEffect
{
    
    public static final int DEATH_FIRE = 0;
    public static final int DEATH_ICE = 1;
    public static final int DEATH_OTHER = 2;
    
    public DeathEffect(BoundingBox bounds, int death)
    {
	super("DEATH_EFFECT", 1);
	setTile(PARTICLE_SMOKE);
	setLocation((bounds.getLeft() + bounds.getRight()) / 2f, (bounds.getTop() + bounds.getBottom()) /2f);
	setEmitterVolume(new VolumeDefault());
	setEmitterVelocity(new VelocityOrtho(-150, 150, -50, 50));
	setBurstDelay(0f, 0f);
	setBurstCount(8, 16);
	setParticleSizes(DEATH_EFFECT_SIZE, DEATH_EFFECT_SIZE * 2);
	setParticleAlphas(0.8f, 0f);
	setParticleLife(0.5f);
	if (death == DEATH_FIRE)
	{
	    setParticleColors(new Color(1f, 0.75f, 0f), Color.orange(), Color.red());
	    setParticleType(ADDITIVE);
	}
	else if (death == DEATH_ICE)
	{
	    setParticleColors(new Color(0f, 0.75f, 0.75f), Color.black());
	    setParticleType(ADDITIVE);
	}
	else if (death == DEATH_OTHER)
	{
	    setParticleColors(Color.lightgray(), Color.white());
	    setParticleType(FULL);
	}
	initialize();
	Level.getInstance().addEffect(this);
    }

    @Override
    public boolean isUserDrawn()
    {
	return false;
    }
    

}
