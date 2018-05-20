package net.philsprojects.game.effects;

import net.philsprojects.game.util.Math;

public class VolumeEllipse implements IEmitterVolume
{

	private short halfWidth = 0;
	private short halfHeight = 0;
	private boolean outline = false;

	public VolumeEllipse(int width, int height, boolean isOutline)
	{
		halfWidth = (short)(width / 2.0f);
		halfHeight = (short)(height / 2.0f);
		outline = isOutline;
	}

	public void initialVolume(IParticle p)
	{
		float angle = Range.random(0f, 360f);
		p.setInitialAngle(360 - angle);
		if (outline)
		{
			p.setX(Math.cos(angle) * halfWidth);
			p.setY(Math.sin(angle) * halfHeight);
		}
		else
		{
			p.setX(Math.cos(angle) * Range.random(0, halfWidth));
			p.setY(Math.sin(angle) * Range.random(0, halfHeight));
		}
	}

}