package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.util.Iterator;


public class AffectorDamping implements IEmitterAffector
{

	public static final short HEADER = 0x4001;

	public float _dampingX = 0.0f;
	public float _dampingY = 0.0f;

	public AffectorDamping(float velocityXDamping, float velocityYDamping)
	{
		_dampingX = velocityXDamping;
		_dampingY = velocityYDamping;
	}

	public final void affectParticle(Iterator<? extends IParticle> particles, float deltatime)
	{
		IParticle p;
		while (particles.hasNext())
		{
			p = particles.getNext();
			p.setVelocityX(p.getVelocityX() * _dampingX);
			p.setVelocityY(p.getVelocityY() * _dampingY);
		}
	}

	public void read(DataInputStream reader) throws Exception
	{
		short header = reader.readShort();
		if (header != HEADER)
			throw new Exception();
		_dampingX = reader.readFloat();
		_dampingY = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.write(HEADER);
		writer.writeFloat(_dampingX);
		writer.writeFloat(_dampingY);
	}

}
