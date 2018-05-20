 package wars.util;

import axe.util.Math;
import axe.util.Vector;

public class StaticLine
{

	protected float _x1;
	protected float _y1; 
	protected float _x2;
	protected float _y2;
	protected float _angle;
	protected float _lengthSq;
	protected float _length;
	protected float _lengthSqInv;
	protected float _lengthInv;
	protected Vector _diff;
	protected Vector _normal;
	// The tangent is a unit vector which points from p2 to p1
	protected Vector _tangent;
	

	public StaticLine(Vector start, Vector end)
	{
		this(start.x, start.y, end.x, end.y);
	}
	
	public StaticLine(float x1, float y1, float x2, float y2)
	{
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
		_normal = new Vector();
		_tangent = new Vector();
		_diff = new Vector();
		recompute();
	}
	
	public float x1() {
		return _x1;
	}
	
	public float x2() {
		return _x2;
	}
	
	public float y1() {
		return _y1;
	}
	
	public float y2() {
		return _y2;
	}
	
	public void set(float x1, float y1, float x2, float y2)
	{
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
		recompute();
	}

	public void setStart(float x1, float y1)
	{
		_x1 = x1;
		_y1 = y1;
		recompute();
	}
	
	public void setEnd(float x2, float y2)
	{
		_x2 = x2;
		_y2 = y2;
		recompute();
	}
	
	public float getAngle()
	{
		return _angle;
	}
	
	public float getReverseAngle()
	{
		if (_angle > 180)
			return _angle - 180;
		return _angle + 180;
	}
	
	public Vector getNormal(Vector v)
	{
		Vector closest = closest(v);
		closest.sub(v);
		closest.normal();
		return closest;
	}
	
	public Vector getTangent(Vector v)
	{
		return _tangent;
	}
	
	public Vector getDiff()
	{
		return _diff;
	}
	
	public float getLength()
	{
		return _length;
	}
	
	public float getLengthSq()
	{
		return _lengthSq;
	}
	
	public float getLengthInverse()
	{
		return _lengthInv;
	}
	
	public float getLengthSqInverse()
	{
		return _lengthSqInv;
	}
	
	public float getDot()
	{
		return _lengthSq;
	}
	
	public Vector getPoint(float delta)
	{
		return new Vector(_x1 + delta * _diff.x,
						  _y1 + delta * _diff.y);
	}
	
	public float distance(float x, float y)
	{
		return (_diff.y * (_x1 - x) - _diff.x * (_y1 - y)) * _lengthInv;
	}
	
	public float distance(Vector v)
	{
		return (_diff.y * (_x1 - v.x) - _diff.x * (_y1 - v.y)) * _lengthInv;
	}
	
	public boolean isBelow(Vector v)
	{
		return _diff.x * (_y1 - v.y) > _diff.y * (_x1 - v.x);
	}
	
	public boolean inLineBounds(Vector v)
	{
		double d = ((v.x - _x1) * _diff.x + (v.y - _y1) * _diff.y);
		return (d < _lengthSq && d > 0.0);
	}
	
	public boolean inLineBounds(Vector v, double radius)
	{
		return (Vector.distanceSq(v, closest(v)) <= radius * radius);
	}
	
	public Vector project(Vector v)
	{
		float delta = ((v.x - _x1) * _diff.x + (v.y - _y1) * _diff.y) * _lengthSqInv;
		return getPoint(delta);
	}
	
	public Vector closest(Vector v)
	{
		float delta = ((v.x - _x1) * _diff.x + (v.y - _y1) * _diff.y) * _lengthSqInv;
		if (delta > 1.0) delta = 1f;
		if (delta < 0.0) delta = 0f;
		return getPoint(delta);
	}
	
	public double getDelta(Vector v)
	{
		return ((v.x - _x1) * _diff.x + (v.y - _y1) * _diff.y) * _lengthSqInv;
	}
	
	public Vector getNormal() {
		return _normal;
	}
	
	public Vector innerVector(Vector v)
	{
		Vector w = new Vector(v);
		w.rotate(_tangent);
		w.abs();
		return w;	
	}
	
	protected void recompute()
	{
		if (_x1 == _x2 && _y1 == _y2)
			throw new RuntimeException();
		
		_angle = Math.angle(_x1, _y1, _x2, _y2);

		_diff.x = _x2 - _x1;
		_diff.y = _y2 - _y1;

		_lengthSq = _diff.x * _diff.x + _diff.y * _diff.y;
		_lengthSqInv = 1f / _lengthSq;
		_length = (float)StrictMath.sqrt(_lengthSq);
		_lengthInv = 1f / _length;
		
		if (_length != 0.0) 
		{
			_normal.x = -_diff.y * _lengthInv;
			_normal.y = _diff.x * _lengthInv;
			_tangent.x = -_normal.y; 
			_tangent.y = _normal.x;
		}
	}
	
}
