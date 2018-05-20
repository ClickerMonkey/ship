package net.philsprojects.game;

import static net.philsprojects.game.Constants.BACKWARD;
import static net.philsprojects.game.Constants.FORWARD;
import static net.philsprojects.game.Constants.LOOP_BACKWARD;
import static net.philsprojects.game.Constants.LOOP_FORWARD;
import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;
import static net.philsprojects.game.Constants.PINGPONG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import net.philsprojects.game.IAttach;
import net.philsprojects.game.ISocket;
import net.philsprojects.game.PathSpline;
import net.philsprojects.game.util.Vector;

import org.junit.Test;


public class TestPathSpline
{

	@Test
	public void testMethods()
	{
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 15.0f) };
		AttachItem a = new AttachItem();
		PathSpline s = new PathSpline("Spline1", LOOP_FORWARD, v);
		a.setSocket(s);
		s.startSpeed(2.0f);
		// SplinePath
		assertEquals(LOOP_FORWARD, s.getAnimationType());
		assertEquals(FORWARD, s.getDirection());
		assertEquals(14.142136f, s.getLength(), 0.00001);
		assertEquals("Spline1", s.getName());
		assertEquals(45.0f, s.getAngle(), 0.00001);
		assertEquals(2.0f, s.getSpeed(), 0.00001);
		// Attached
		assertEquals(s, a.getSocket());

		s = new PathSpline("Spline1", LOOP_BACKWARD, v);
		assertEquals(BACKWARD, s.getDirection());
	}

	@Test
	public void testLOOP_FORWARD()
	{
		// 20 |
		// 15 | o
		// 10 | |
		// 5 | o----o
		// 0<-+----------->
		// 0 5 10 15
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 5.0f), new Vector(15.0f, 15.0f) };
		PathSpline s = new PathSpline("s", LOOP_FORWARD, v);
		s.startSpeed(5.0f);

		s.update(0.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		// Starts at the beginning again
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		// Starts at the beginning again
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
	}

	@Test
	public void testONCE_FORWARD()
	{
		// 20 |
		// 15 | o
		// 10 | |
		// 5 | o----o
		// 0<-+----------->
		// 0 5 10 15
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 5.0f), new Vector(15.0f, 15.0f) };
		PathSpline s = new PathSpline("s", ONCE_FORWARD, v);
		s.startSpeed(5.0f);

		s.update(0.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		// Prove that it stopped and its still at the last position
		assertFalse(s.isEnabled());
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
	}

	@Test
	public void testLOOP_BACKWARD()
	{
		// 20 |
		// 15 | o
		// 10 | |
		// 5 | o----o
		// 0<-+----------->
		// 0 5 10 15
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 5.0f), new Vector(15.0f, 15.0f) };
		PathSpline s = new PathSpline("s", LOOP_BACKWARD, v);
		s.startSpeed(5.0f);

		s.update(0.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		// restarts at the end
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		// restarts at the end again
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
	}

	@Test
	public void testONCE_BACKWARD()
	{
		// 20 |
		// 15 | o
		// 10 | |
		// 5 | o----o
		// 0<-+----------->
		// 0 5 10 15
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 5.0f), new Vector(15.0f, 15.0f) };
		PathSpline s = new PathSpline("s", ONCE_BACKWARD, v);
		s.startSpeed(5.0f);

		s.update(0.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		// stops
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		assertFalse(s.isEnabled());
	}

	@Test
	public void testPINGPONG()
	{
		// 20 |
		// 15 | o
		// 10 | |
		// 5 | o----o
		// 0<-+----------->
		// 0 5 10 15
		Vector[] v = { new Vector(5.0f, 5.0f), new Vector(15.0f, 5.0f), new Vector(15.0f, 15.0f) };
		PathSpline s = new PathSpline("s", PINGPONG, v);
		s.startSpeed(5.0f);
		s.update(0.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(15.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		// Bounce back (<-Backwards)
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(10.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(5.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		// Bounce back (->Forwards)
		assertEquals(10.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
		s.update(1.0f);
		assertEquals(15.0f, s.getLocation().x, 0.00001);
		assertEquals(5.0f, s.getLocation().y, 0.00001);
	}

	private class AttachItem implements IAttach
	{
		private float _angle = 0.0f;
		private boolean _rotatesWith = true;
		private Vector _offset = Vector.zero();
		private Vector _location = Vector.zero();
		private ISocket _socket = null;

		public float getAngleOffset()
		{
			return 0.0f;
		}

		public Vector getOffset()
		{
			return _offset;
		}

		public boolean getRotatesWith()
		{
			return _rotatesWith;
		}

		public ISocket getSocket()
		{
			return _socket;
		}

		public void setAngle(float angle)
		{
			_angle = angle;
		}

		public Vector getLocation()
		{
			return _location;
		}

		public float getAngle()
		{
			return _angle;
		}

		public void setSocket(ISocket socket)
		{
			_socket = socket;
		}

		public void setOffset(float x, float y)
		{
		}

		public void attach(ISocket socket, float offsetX, float offsetY, float angleOffset, boolean rotatesWith)
		{
		}

		public void setRotatesWith(boolean rotatesWith)
		{
		}
	}

}
