package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.util.Iterator;


public class AffectorVelocity implements IEmitterAffector
{

	public static final short HEADER = 0x4003;

	public float _velocityX;
	public float _velocityY;
	public float _accelerationX;
	public float _accelerationY;

	public AffectorVelocity(float velX, float velY, float accelX, float accelY)
	{
		_velocityX = velX;
		_velocityY = velY;
		_accelerationX = accelX;
		_accelerationY = accelY;
	}

	public final void affectParticle(Iterator<? extends IParticle> particles, float deltatime)
	{
		IParticle p;
		while (particles.hasNext())
		{
			p = particles.getNext();
			p.setVelocityX(p.getVelocityX() + _velocityX * deltatime + (_accelerationX * p.getLife()));
			p.setVelocityY(p.getVelocityY() + _velocityY * deltatime + (_accelerationY * p.getLife()));
		}
	}

	public void read(DataInputStream reader) throws Exception
	{
		short header = reader.readShort();
		if (header != HEADER)
			throw new Exception();
		_velocityX = reader.readFloat();
		_velocityY = reader.readFloat();
		_accelerationX = reader.readFloat();
		_accelerationY = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.write(HEADER);
		writer.writeFloat(_velocityX);
		writer.writeFloat(_velocityY);
		writer.writeFloat(_accelerationX);
		writer.writeFloat(_accelerationY);
	}

}
