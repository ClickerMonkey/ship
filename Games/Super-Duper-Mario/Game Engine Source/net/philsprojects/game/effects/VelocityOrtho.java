package net.philsprojects.game.effects;


public class VelocityOrtho implements IEmitterVelocity
{

	private Range velocityX;
	private Range velocityY;

	public VelocityOrtho(float velocityXMin, float velocityXMax, float velocityYMin, float velocityYMax)
	{
		velocityX = new Range(velocityXMin, velocityXMax);
		velocityY = new Range(velocityYMin, velocityYMax);
	}

	public void initialVelocity(IParticle p)
	{
		p.setVelocityX(velocityX.randomFloat());
		p.setVelocityY(velocityY.randomFloat());
	}

}
