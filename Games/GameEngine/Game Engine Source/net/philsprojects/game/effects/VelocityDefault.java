package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class VelocityDefault implements IEmitterVelocity
{



	public VelocityDefault()
	{
	}

	public void initialVelocity(IParticle p)
	{
		p.setVelocityX(0f);
		p.setVelocityY(0f);
	}

	public void read(DataInputStream reader) throws Exception
	{
		// TODO Auto-generated method stub

	}

	public void write(DataOutputStream writer) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
