package pfxEditor;
/* 
                    +Z
                     0
         ____________|________
         \           |       |\
         |\          |       | \
         | \ +Y     top      |  \
         |  \ 0              |   \
         |   \ \             |    \
         |    \_\__________________\
         |     | \           |     |
         |_____|__\__________|     |
         \     |  back       \ r   |
        l \    |              \ i  |
 -X0-----e-\   |               \-g-|-----0+X
          f \  |    bottom      \ h|
           t \ |     |           \ t
              \|__________________\|
                     | front
                     |    \
                     0     \
                     -Z     \
                             0
                             -Y
 */
public class BoundingBox {

	/** X-Plane */
	private float _left = 0;
	private float _right = 0;
	/** Y-Plane */
	private float _top = 0;
	private float _bottom = 0;
	/** Z-Plane */
	private float _front = 0;
	private float _back = 0;

	public BoundingBox() {
	}

	public BoundingBox(float left, float top, float right, float bottom,
			float front, float back) {
		_left = left;
		_top = top;
		_right = right;
		_bottom = bottom;
		_front = front;
		_back = back;
	}

	public final void clear(float x, float y, float z) {
		_left = _right = x;
		_top = _bottom = z;
		_front = _back = y;
	}

	public final void include(float x, float y, float z) {
		if (x < _left)
			_left = x;
		if (x > _right)
			_right = x;
		if (z > _top)
			_top = z;
		if (z < _bottom)
			_bottom = z;
		if (y > _back)
			_back = y;
		if (y < _front)
			_front = y;
	}

	public final void include(float x, float y, float z, float size) {
		if (x < _left)
			_left = x;
		if (x + size > _right)
			_right = x + size;
		if (z + size > _top)
			_top = z + size;
		if (z < _bottom)
			_bottom = z;
		if (y + size > _back)
			_back = y + size;
		if (y < _front)
			_front = y;
	}

	public final void include(Particle p) {
		float size = p.size / 2f;
		if (p.location.getX() < _left)
			_left = p.location.getX();
		if (p.location.getX() + size > _right)
			_right = p.location.getX() + size;
		if (p.location.getZ() + size > _top)
			_top = p.location.getZ() + size;
		if (p.location.getZ() < _bottom)
			_bottom = p.location.getZ();
		if (p.location.getY() + size > _back)
			_back = p.location.getY() + size;
		if (p.location.getY() < _front)
			_front = p.location.getY();
	}

	public void setLeft(float left) {
		_left = left;
	}

	public void setTop(float top) {
		_top = top;
	}

	public void setRight(float right) {
		_right = right;
	}

	public void setBottom(float bottom) {
		_bottom = bottom;
	}

	public void setFront(float front) {
		_front = front;
	}

	public void setBack(float back) {
		_back = back;
	}

	public float getLeft() {
		return _left;
	}

	public float getTop() {
		return _top;
	}

	public float getRight() {
		return _right;
	}

	public float getBottom() {
		return _bottom;
	}

	public float getFront() {
		return _front;
	}

	public float getBack() {
		return _back;
	}

	public float getWidth() {
		return _right - _left;
	}

	public float getHeight() {
		return _top - _bottom;
	}

	public float getDepth() {
		return _back - _front;
	}

	@Override
	public String toString() {
		return String.format(
				"{Left<%s> Top<%s> Right<%s> Bottom<%s> Front<%s> Back<%s>}",
				_left, _top, _right, _bottom, _front, _back);
	}

	public static BoundingBox zero() {
		return new BoundingBox(0f, 0f, 0f, 0f, 0f, 0f);
	}

	public static BoundingBox one() {
		return new BoundingBox(1f, 1f, 1f, 1f, 1f, 1f);
	}

}
