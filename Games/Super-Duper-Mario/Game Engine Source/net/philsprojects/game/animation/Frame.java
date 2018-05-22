package net.philsprojects.game.animation;

import net.philsprojects.game.IClone;
import net.philsprojects.game.util.Vector;

public class Frame implements IClone<Frame>
{

	protected Vector _location = Vector.zero();
	protected float[] _angles = null;
	protected float[] _distances = null;

	public void updateSkeleton(Skeleton s)
	{
		Joint[] joints = s.getJoints();
		for (int i = 0; i < joints.length; i++)
		{
			joints[i].setAngle(_angles[i]);
			joints[i].setDistance(_distances[i]);
		}
	}

	public float[] getAngles()
	{
		return _angles;
	}

	public float[] getDistances()
	{
		return _distances;
	}

	public Vector getLocation()
	{
		return _location;
	}

	public Frame getClone()
	{
		Frame clone = new Frame();
		clone._angles = net.philsprojects.game.util.Helper.copy(_angles);
		clone._distances = net.philsprojects.game.util.Helper.copy(_distances);
		clone._location = _location.getClone();
		return clone;
	}


	public static void setSkeleton(Frame start, Frame end, float delta, Skeleton s)
	{
		s.getLocation().x = (start._location.x - end._location.x) * delta + start._location.x;
		s.getLocation().y = (start._location.y - end._location.y) * delta + start._location.y;
		Joint[] joints = s.getJoints();
		for (int i = 0; i < joints.length; i++)
		{
			joints[i].setAngle((end._angles[i] - start._angles[i]) * delta + start._angles[i]);
			joints[i].setDistance((end._distances[i] - start._distances[i]) * delta + start._distances[i]);
		}
	}

}
