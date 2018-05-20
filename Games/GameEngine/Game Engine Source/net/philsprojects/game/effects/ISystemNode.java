package net.philsprojects.game.effects;

import net.philsprojects.game.*;
import net.philsprojects.game.util.Vector;

public interface ISystemNode extends IName, IDraw, IUpdate, IClone<ISystemNode>
{

	public void initialize();

	public void setLocation(float x, float y);

	public void setLocation(Vector location);

	public void setEmitterOffset(float x, float y);

	public void setEmitterOffset(Vector offset);

	public void setEmitterScale(float x, float y);

	public void setEmitterScale(Vector scale);

	public void setEmitterAngle(float angle);


	public void addEmitterOffset(float x, float y);

	public void addEmitterAngle(float angle);

	public void addEmitterScale(float x, float y);


	public Vector getLocation();

	public Vector getEmitterOffset();

	public Vector getEmitterScale();

	public float getEmitterAngle();

}
