package pfxEditor;
public final class Math {

	public static float PI = 3.1415926535f;

	// private static float[] cosTable;
	// private static float[] sinTable;

	static {
		// cosTable = new float[3600];
		// sinTable = new float[3600];
		// for (int i = 0; i < 3600; i++)
		// {
		// cosTable[i] = (float)java.lang.StrictMath.cos(toRadian(i / 10));
		// sinTable[i] = (float)java.lang.StrictMath.sin(toRadian(i / 10));
		// }
	}

	private Math() {

	}

	public static float sqrt(float number) {
		return (float) java.lang.Math.sqrt(number);
	}

	public static float sin(float degree) {
		return (float) java.lang.StrictMath.sin(toRadian(degree));
	}

	public static float cos(float degree) {
		return (float) java.lang.StrictMath.cos(toRadian(degree));
	}

	public static float distance(float x1, float y1, float z1, float x2,
			float y2, float z2) {
		return (float) java.lang.StrictMath.sqrt(distanceSquared(x1, y1, z1,
				x2, y2, z2));
	}

	public static float distance(Vector v1, Vector v2) {
		return distance(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(),
				v2.getZ());
	}

	public static float distance(Vector v) {
		return distance(0, 0, 0, v.getX(), v.getY(), v.getZ());
	}

	public static float distanceSquared(float x1, float y1, float z1, float x2,
			float y2, float z2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2)
				* (z1 - z2);
	}

	public static float distanceSquared(Vector v1, Vector v2) {
		return distanceSquared(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2
				.getY(), v2.getZ());
	}

	public static float distanceSquared(Vector v) {
		return distanceSquared(0, 0, 0, v.getX(), v.getY(), v.getZ());
	}

	public static float angle(float x1, float y1, float x2, float y2) {
		return 180 - toDegree((float) java.lang.StrictMath.atan2(y2 - y1, x1
				- x2));
	}

	public static float angle(Vector v1, Vector v2) {
		return angle(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}

	public static float angle(Vector v) {
		return angle(0, 0, v.getX(), v.getY());
	}

	public static Vector midVector(float x1, float y1, float z1, float x2,
			float y2, float z2) {
		return new Vector((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2);
	}

	public static Vector midVector(Vector v1, Vector v2) {
		return midVector(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(),
				v2.getZ());
	}

	public static Vector closestToVector(Vector v, Vector v1, Vector v2) {
		return (distanceSquared(v, v1) < distanceSquared(v, v2)) ? v1 : v2;
	}

	public static Vector farthestFromVector(Vector v, Vector v1, Vector v2) {
		return (distanceSquared(v, v1) > distanceSquared(v, v2)) ? v1 : v2;
	}

	public static Vector angledVector(float yaw, float pitch, float distance) {
		return new Vector(cos(yaw) * cos(pitch) * distance, sin(yaw)
				* cos(pitch) * distance, sin(pitch) * distance);
	}

	public static Vector averageVector(Vector[] vectors) {
		Vector average = Vector.zero();
		for (Vector v : vectors)
			average = Vector.add(average, v);
		return Vector.divide(average, vectors.length);
	}

	// public static Vector rotateVector(Vector vector, float yaw, float pitch)
	// {
	// return angledVector(angle(vector) + angle, distance(vector));
	// }
	//
	// public static Vector rotateVector(Vector origin, Vector vector, float
	// yaw, float pitch) {
	// return Vector.add(origin, angledVector(angle(origin, vector) + angle,
	// distance(origin, vector)));
	// }
	//
	// public static Vector[] rotateVectors(Vector origin, Vector[] vectors,
	// float angle) {
	// for (int i = 0; i < vectors.length; i++)
	// vectors[i] = rotateVector(origin, vectors[i], angle);
	// return vectors;
	// }

	public static float toDegree(float theta) {
		return theta * 180 / PI;
	}

	public static float toRadian(float theta) {
		return theta / 180 * PI;
	}

	public static float min(float a, float b) {
		return (a < b) ? a : b;
	}

	public static int min(int a, int b) {
		return (a < b) ? a : b;
	}

	public static float max(float a, float b) {
		return (a > b) ? a : b;
	}

	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public static float abs(float a) {
		return (a < 0.0f) ? -a : a;
	}

	public static int abs(int a) {
		return (a < 0.0f) ? -a : a;
	}

	public static float floor(float a) {
		return (float) java.lang.StrictMath.floor(a);
	}

	public static float remainder(float a) {
		return a - floor(a);
	}

	public static float ceil(float a) {
		return (float) java.lang.StrictMath.ceil(a);
	}

	public static int round(float a) {
		return java.lang.StrictMath.round(a);
	}

	public static int cut(int a, int min, int max) {
		if (a < min)
			return min;
		if (a > max)
			return max;
		return a;
	}

	public static float cut(float a, float min, float max) {
		if (a < min)
			return min;
		if (a > max)
			return max;
		return a;
	}

	public static float mod(float a, float mod) {
		while (a >= mod)
			a -= mod;
		while (a < 0)
			a += mod;
		return a;
	}

}
