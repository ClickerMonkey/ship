package net.philsprojects.game.effects;

import net.philsprojects.game.util.Math;

public class VolumeLine implements IEmitterVolume
{

	public static final byte WATERFALL = 0;
	public static final byte AWAY = 1;
	public static final byte OUTWARDS = 2;
	public static final byte NONE = 3;

	private float _startX = 0f;
	private float _startY = 0f;
	private float _endX = 0f;
	private float _endY = 0f;
	private float _angle = 0f;
	private byte _type = WATERFALL;

	public VolumeLine(float x1, float y1, float x2, float y2, byte emitterType)
	{
		_startX = x1;
		_startY = y1;
		_endX = x2;
		_endY = y2;
		_angle = Math.angle(_startX, _startY, _endX, _endY);
		_type = emitterType;
	}

	public void initialVolume(IParticle p)
	{
		final float delta = Range.random(0f, 1f);
		p.setX((_endX - _startX) * delta + _startX);
		p.setY((_endY - _startY) * delta + _startY);

		switch (_type)
		{
			case WATERFALL:
				/* Point in-front of line. */
				p.setInitialAngle(_angle);
				break;
			case AWAY:
				switch (Range.random(1, 2))
				{
					case 1: /* Point in-front of line. */
						p.setInitialAngle(_angle);
						break;
					case 2: /* Point in-back of line. */
						p.setInitialAngle(_angle + 180);
						break;
				}
				break;
			case OUTWARDS:
				if (delta < 0.1f)
				{ /* Start of line. */
					p.setInitialAngle(Range.random(_angle, _angle + 180));
				}
				else if (delta > 0.9f)
				{ /* End of line. */
					p.setInitialAngle(Range.random(_angle - 180, _angle));
				}
				else
				{
					switch (Range.random(1, 2))
					{
						case 1: /* Point in-front of line. */
							p.setInitialAngle(_angle + 90);
							break;
						case 2: /* Point in-back of line. */
							p.setInitialAngle(_angle + 270);
							break;
					}
				}
				break;
			case NONE:

				break;
		}
	}

}
