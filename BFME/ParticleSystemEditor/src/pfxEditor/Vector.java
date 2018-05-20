package pfxEditor;
public class Vector {

	private float _x;
	private float _y;
	private float _z;

	public Vector() {
		_x = _y = _z = 0f;
	}

	public Vector(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}

	public void add(Vector v) {
		_x += v._x;
		_y += v._y;
		_z += v._z;
	}

	public void add(float v) {
		_x += v;
		_y += v;
		_z += v;
	}

	public void add(float x, float y, float z) {
		_x += x;
		_y += y;
		_z += z;
	}

	public void multiply(Vector v) {
		_x *= v._x;
		_y *= v._y;
		_z *= v._z;
	}

	public void multiply(float v) {
		_x *= v;
		_y *= v;
		_z *= v;
	}

	public void subtract(Vector v) {
		_x -= v._x;
		_y -= v._y;
		_z -= v._z;
	}

	public void subtract(float v) {
		_x -= v;
		_y -= v;
		_z -= v;
	}

	public void divide(Vector v) {
		_x /= (v._x == 0 ? 1f : v._x);
		_y /= (v._y == 0 ? 1f : v._y);
		_z /= (v._z == 0 ? 1f : v._z);
	}

	public void divide(float v) {
		if (v == 0)
			return;
		_x /= v;
		_y /= v;
		_z /= v;
	}

	public void set(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}

	public void set(Vector v) {
		_x = v._x;
		_y = v._y;
		_z = v._z;
	}

	public void setX(float x) {
		_x = x;
	}

	public void setY(float y) {
		_y = y;
	}

	public void setZ(float z) {
		_z = z;
	}

	public float getX() {
		return _x;
	}

	public float getY() {
		return _y;
	}

	public float getZ() {
		return _z;
	}

	public Vector clone() {
		return new Vector(_x, _y, _z);
	}

	public String toString() {
		return String.format("{%s, %s, %s}", _x, _y, _z);
	}

	public static Vector zero() {
		return new Vector(0f, 0f, 0f);
	}

	public static Vector one() {
		return new Vector(1f, 1f, 1f);
	}

	public static Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1._x - v2._x, v1._y - v2._y, v1._z - v2._z);
	}

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1._x + v2._x, v1._y + v2._y, v1._z + v2._z);
	}

	public static Vector add(Vector v, float value) {
		return new Vector(v._x + value, v._y + value, v._z + value);
	}

	public static Vector multiply(Vector v1, Vector v2) {
		return new Vector(v1._x * v2._x, v1._y * v2._y, v1._z * v2._z);
	}

	public static Vector multiply(Vector v, float value) {
		return new Vector(v._x * value, v._y * value, v._z * value);
	}

	public static Vector divide(Vector v1, Vector v2) {
		return new Vector(v2._x != 0 ? v1._x / v2._x : 0, v2._y != 0 ? v1._y
				/ v2._y : 0, v2._z != 0 ? v1._z / v2._z : 0);
	}

	public static Vector divide(Vector v, float value) {
		return (value == 0 ? null : new Vector(v._x / value, v._y / value, v._z
				/ value));
	}

	public static float distance(Vector v1, Vector v2) {
		return Math.distance(v1, v2);
	}

	public static float distanceSquared(Vector v1, Vector v2) {
		return Math.distanceSquared(v1, v2);
	}

	public static Vector delta(Vector v1, Vector v2, float delta) {
		return add(multiply(subtract(v2, v1), delta), v1);
	}

}
