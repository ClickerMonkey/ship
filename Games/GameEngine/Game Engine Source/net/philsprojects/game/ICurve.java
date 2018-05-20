package net.philsprojects.game;

public interface ICurve extends IClone<ICurve>
{

	public float getValue(float time);

	public float getDuration();

	public void scale(float scale);

	public ICurve getClone();

}
