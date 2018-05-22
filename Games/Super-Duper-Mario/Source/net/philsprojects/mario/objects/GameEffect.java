package net.philsprojects.mario.objects;

import net.philsprojects.game.effects.BurstParticleSystem;
import net.philsprojects.game.effects.Particle;

/**
 * The base special effect for the game where it holds an isUserDrawn() flag thats used for custom drawing.
 * 
 * @author Philip Diffenderfer
 */
public abstract class GameEffect extends BurstParticleSystem<Particle>
{

	public abstract boolean isUserDrawn();

	public GameEffect(String name)
	{
		super(Particle.class, name);
	}

	public GameEffect(String name, int bursts)
	{
		super(Particle.class, name, bursts);
	}

}
