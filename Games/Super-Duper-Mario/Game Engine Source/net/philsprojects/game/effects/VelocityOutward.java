package net.philsprojects.game.effects;

import net.philsprojects.game.util.Math;

public class VelocityOutward implements IEmitterVelocity
{

	private Range speeds;

	public VelocityOutward(float speedMin, float speedMax)
	{
		speeds = new Range(speedMin, speedMax);
	}

	public void initialVelocity(IParticle p)
	{
		final float angle = p.getInitialAngle();
		final float speed = speeds.randomFloat();
		p.setVelocityX(Math.cos(angle) * speed);
		p.setVelocityY(Math.sin(angle) * -speed);
	}

}
