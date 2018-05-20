package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.util.Iterator;


public class AffectorBoxConstraint implements IEmitterAffector
{

	public static final short HEADER = 0x4000;

	private int _left;
	private int _top;
	private int _right;
	private int _bottom;
	private float _dampingX = 1.0f;
	private float _dampingY = 1.0f;

	// private boolean sizeIncluded = false;

	public AffectorBoxConstraint(int boxLeft, int boxTop, int boxRight, int boxBottom, float dampingX, float dampingY, boolean includesSize)
	{
		_left = boxLeft;
		_top = boxTop;
		_right = boxRight;
		_bottom = boxBottom;
		_dampingX = dampingX;
		_dampingY = dampingY;
		// sizeIncluded = includesSize;
	}

	public final void affectParticle(Iterator<? extends IParticle> particles, float deltatime)
	{
		IParticle p;
		while (particles.hasNext())
		{
			p = particles.getNext();
			int half = 0; // (int)(sizeIncluded ? p.size / 2.0f : 0);
			if (p.getX() - half < _left)
			{
				p.setX(_left + half);
				p.setVelocityX(p.getVelocityX() * -1);
			}
			if (p.getX() + half > _right)
			{
				p.setX(_right - half);
				p.setVelocityX(p.getVelocityX() * -1);
			}
			if (p.getY() + half > _top)
			{
				p.setY(_top - half);
				p.setVelocityY(p.getVelocityY() * -_dampingY);
				p.setVelocityX(p.getVelocityX() * _dampingX);
			}
			if (p.getY() - half < _bottom)
			{
				p.setY(_bottom + half);
				p.setVelocityY(p.getVelocityY() * -_dampingY);
				p.setVelocityX(p.getVelocityX() * _dampingX);
			}
		}
	}

	public void read(DataInputStream reader) throws Exception
	{
		short header = reader.readShort();
		if (header != HEADER)
			throw new Exception();
		_left = reader.readInt();
		_right = reader.readInt();
		_top = reader.readInt();
		_bottom = reader.readInt();
		_dampingX = reader.readFloat();
		_dampingY = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.write(HEADER);
		writer.writeInt(_left);
		writer.writeInt(_right);
		writer.writeInt(_top);
		writer.writeInt(_bottom);
		writer.writeFloat(_dampingX);
		writer.writeFloat(_dampingY);
	}

}
