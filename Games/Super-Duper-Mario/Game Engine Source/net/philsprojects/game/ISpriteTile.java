package net.philsprojects.game;

import net.philsprojects.game.util.Rectangle;

public interface ISpriteTile extends IName, ITexture, IUpdate, IBinary, IClone<ISpriteTile>
{

	public Rectangle getSource();

	public float getAnimationSpeed();

	public void setAnimationSpeed(float speed);

	public void reset();

	public ISpriteTile getClone();

	public void dispose();

}
