package net.philsprojects.game.effects;


public class VolumeDefault implements IEmitterVolume
{

	public VolumeDefault()
	{
	}

	public void initialVolume(IParticle p)
	{
		p.setInitialAngle(Range.random(0f, 360f));
		p.setX(0f);
		p.setY(0f);
	}

}
