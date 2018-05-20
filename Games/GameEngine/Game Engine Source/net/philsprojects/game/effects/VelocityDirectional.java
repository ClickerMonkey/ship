package net.philsprojects.game.effects;

import net.philsprojects.game.util.Math;

public class VelocityDirectional implements IEmitterVelocity
{

	private Range angles;
	private Range speeds;

	public VelocityDirectional(float angleMin, float angleMax, float speedMin, float speedMax)
	{
		angles = new Range(angleMin, angleMax);
		speeds = new Range(speedMin, speedMax);
	}

	public void initialVelocity(IParticle p)
	{
		final float angle = angles.randomFloat();
		final float speed = speeds.randomFloat();
		p.setVelocityX(Math.cos(angle) * speed);
		p.setVelocityY(Math.sin(angle) * speed);
	}

	public final void setAngles(float angleMin, float angleMax)
	{
		angles = new Range(angleMin, angleMax);
	}

}
