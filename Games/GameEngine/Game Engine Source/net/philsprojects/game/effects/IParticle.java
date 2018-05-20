package net.philsprojects.game.effects;

import net.philsprojects.game.IDraw;
import net.philsprojects.game.IUpdate;

public interface IParticle extends IDraw, IUpdate
{

	public float getX();

	public float getY();

	public float getInitialAngle();

	public float getVelocityX();

	public float getVelocityY();

	public float getLifeTime();

	public float getLife();

	public boolean isDead();

	public boolean isCustomDraw();


	public void setLifeTime(float lifetime);

	public void setX(float x);

	public void setY(float y);

	public void setInitialAngle(float angle);

	public void setVelocityX(float x);

	public void setVelocityY(float y);

}
