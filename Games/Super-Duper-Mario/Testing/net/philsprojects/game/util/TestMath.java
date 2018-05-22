package net.philsprojects.game.util;

import static net.philsprojects.game.util.Math.abs;
import static net.philsprojects.game.util.Math.angle;
import static net.philsprojects.game.util.Math.angledVector;
import static net.philsprojects.game.util.Math.averageVector;
import static net.philsprojects.game.util.Math.ceil;
import static net.philsprojects.game.util.Math.closestToVector;
import static net.philsprojects.game.util.Math.cos;
import static net.philsprojects.game.util.Math.cut;
import static net.philsprojects.game.util.Math.distance;
import static net.philsprojects.game.util.Math.distanceSquared;
import static net.philsprojects.game.util.Math.farthestFromVector;
import static net.philsprojects.game.util.Math.max;
import static net.philsprojects.game.util.Math.min;
import static net.philsprojects.game.util.Math.mod;
import static net.philsprojects.game.util.Math.remainder;
import static net.philsprojects.game.util.Math.rotateVector;
import static net.philsprojects.game.util.Math.rotateVectors;
import static net.philsprojects.game.util.Math.round;
import static net.philsprojects.game.util.Math.sin;
import static net.philsprojects.game.util.Math.sqrt;
import static org.junit.Assert.assertEquals;

import net.philsprojects.game.util.Vector;

import org.junit.Test;


public class TestMath
{

	@Test
	public void testBasic()
	{
		// abs
		assertEquals(4.5f, abs(-4.5f), 0.00001);
		assertEquals(4.5f, abs(4.5f), 0.00001);
		assertEquals(4, abs(-4));
		assertEquals(4, abs(4));
		// ceil
		assertEquals(5, ceil(4.1f), 0.00001);
		assertEquals(5, ceil(4.9f), 0.00001);
		assertEquals(5, ceil(4.00001f), 0.00001);
		// cos
		assertEquals(0.5f, cos(60.0f), 0.00001);
		assertEquals(1.0f, cos(0.0f), 0.00001);
		assertEquals(0.0f, cos(90.0f), 0.00001);
		// sin
		assertEquals(0.5f, sin(30.0f), 0.00001);
		assertEquals(1.0f, sin(90.0f), 0.00001);
		assertEquals(0.0f, sin(0.0f), 0.00001);
		// floor
		assertEquals(5, ceil(4.1f), 0.00001);
		assertEquals(5, ceil(4.9f), 0.00001);
		assertEquals(5, ceil(4.99999f), 0.00001);
		// max
		assertEquals(4.2f, max(4.2f, 4.1999f), 0.00001);
		assertEquals(4.9f, max(4.9f, -4.9f), 0.00001);
		assertEquals(67, max(67, 45));
		assertEquals(-10, max(-23, -10));
		// min
		assertEquals(4.1999f, min(4.2f, 4.1999f), 0.00001);
		assertEquals(-4.9f, min(4.9f, -4.9f), 0.00001);
		assertEquals(45, min(67, 45));
		assertEquals(-23, min(-23, -10));
		// remainder
		assertEquals(0.12f, remainder(4.12f), 0.00001);
		assertEquals(0.0f, remainder(4.0f), 0.00001);
		assertEquals(0.12023f, remainder(4.12023f), 0.00001);
		// sqrt
		assertEquals(2.0f, sqrt(4.0f), 0.00001);
		assertEquals(4.0f, sqrt(16.0f), 0.00001);
		assertEquals(16.0f, sqrt(256.0f), 0.00001);
		// cut
		assertEquals(3.4f, cut(4.0f, 0.0f, 3.4f), 0.00001);
		assertEquals(0.0f, cut(-4.0f, 0.0f, 3.4f), 0.00001);
		assertEquals(2.3f, cut(2.3f, 0.0f, 3.4f), 0.00001);
		// mod
		assertEquals(0.5f, mod(3.5f, 3.0f), 0.00001);
		assertEquals(2.5f, mod(-3.5f, 3.0f), 0.00001);
		assertEquals(2.3f, mod(22.3f, 4.0f), 0.00001);
	}

	@Test
	public void testVectorMath()
	{
		Vector[] v = new Vector[10];
		// distance
		assertEquals(10, distance(0, 0, 10, 0), 0.00001);
		assertEquals(10, distance(5, 5, 5, 15), 0.00001);
		assertEquals(10, distance(5, 5, 5, -5), 0.00001);
		// distanceSquared
		assertEquals(100, distanceSquared(0, 0, 10, 0), 0.00001);
		assertEquals(100, distanceSquared(5, 5, 5, 15), 0.00001);
		assertEquals(100, distanceSquared(5, 5, 5, -5), 0.00001);
		// getAngle
		v[0] = new Vector(5, 5);
		v[1] = new Vector(10, 10);
		v[2] = new Vector(0, 10);
		v[3] = new Vector(0, 0);
		v[4] = new Vector(10, 0);
		assertEquals(45.0f, angle(v[0], v[1]), 0.00001);
		assertEquals(135.0f, angle(v[0], v[2]), 0.00001);
		assertEquals(225.0f, angle(v[0], v[3]), 0.00001);
		assertEquals(315.0f, angle(v[0], v[4]), 0.00001);
		// angledVector
		v[0] = Vector.zero();
		v[1] = angledVector(45, 5);
		assertEquals(45.0f, angle(v[0], v[1]), 0.00001);
		assertEquals(5.0f, distance(v[0], v[1]), 0.00001);
		// closestToVector
		v[0] = Vector.zero();
		v[1] = new Vector(5, 0);
		v[2] = new Vector(0, 6);
		v[3] = closestToVector(v[0], v[1], v[2]);
		assertEquals(v[3], v[1]);
		// farthestFromVector
		v[3] = farthestFromVector(v[0], v[1], v[2]);
		assertEquals(v[3], v[2]);
		// rotateVector
		v[0] = new Vector(2, 3);
		v[1] = rotateVector(Vector.zero(), v[0], 90);
		v[2] = rotateVector(Vector.zero(), v[0], 180);
		assertEquals(-3, round(v[1].x));
		assertEquals(2, round(v[1].y));
		assertEquals(-2, round(v[2].x));
		assertEquals(-3, round(v[2].y));
		// rotateVectors
		v = new Vector[] { new Vector(5, 0), new Vector(0, 5), new Vector(-5, 0), new Vector(0, -5) };
		rotateVectors(Vector.zero(), v, 45);
		assertEquals(45.0f, angle(v[0]), 0.00001);
		assertEquals(5.0f, distance(v[0]), 0.00001);
		assertEquals(135.0f, angle(v[1]), 0.00001);
		assertEquals(5.0f, distance(v[1]), 0.00001);
		assertEquals(225.0f, angle(v[2]), 0.00001);
		assertEquals(5.0f, distance(v[2]), 0.00001);
		assertEquals(315.0f, angle(v[3]), 0.00001);
		assertEquals(5.0f, distance(v[3]), 0.00001);
		// averageVector
		Vector a = averageVector(v);
		assertEquals(0.0f, a.x, 0.00001);
		assertEquals(0.0f, a.y, 0.00001);
	}

}
