package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.util.Iterator;


public class AffectorGravity implements IEmitterAffector
{

	public static final short HEADER = 0x4002;

	private float _gravity;

	public AffectorGravity(float particleGravity)
	{
		_gravity = particleGravity;
	}

	public final void affectParticle(Iterator<? extends IParticle> particles, float deltatime)
	{
		IParticle p;
		while (particles.hasNext())
		{
			p = particles.getNext();
			p.setVelocityY(p.getVelocityY() + _gravity * deltatime);
		}
	}

	public void read(DataInputStream reader) throws Exception
	{
		short header = reader.readShort();
		if (header != HEADER)
			throw new Exception();
		_gravity = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.write(HEADER);
		writer.writeFloat(_gravity);
	}

}
