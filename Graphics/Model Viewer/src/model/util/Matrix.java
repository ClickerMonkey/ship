package model.util;

public class Matrix {

	public static final double EPSILON = 0.0000001;
	
	protected double m00, m01, m02;
	protected double m10, m11, m12;
	protected double m20, m21, m22;
	protected double tx, ty, tz;

	public Matrix() {
		reset();
	}
	
	public void reset() {
		m00 = m11 = m22 = 1.0;
		m01 = m02 = m10 = m12 = m20 = m21 = 0.0;
		tx = ty = tz = 0.0;
	}
	
	public Point transform(Point p) {
		Point out = new Point();
		transform(p, out);
		return out;
	}
	
	public void transform(Point p, Point out) {
		out.x = (p.x * m00) + (p.y * m01) + (p.z * m02) + tx;
		out.y = (p.x * m10) + (p.y * m11) + (p.z * m12) + ty;
		out.z = (p.x * m20) + (p.y * m21) + (p.z * m22) + tz;
	}
	
	public void getX(Point p) {
		p.x = m00;
		p.y = m10;
		p.z = m20;
	}
	
	public void getY(Point p) {
		p.x = m01;
		p.y = m11;
		p.z = m21;
	}
	
	public void getZ(Point p) {
		p.x = m02;
		p.y = m12;
		p.z = m22;
	}

	public void setEulerAngles(double yaw, double pitch, double roll) {
		double sinX = Math.sin(pitch);
		double cosX = Math.cos(pitch);
		double sinY = Math.sin(yaw);
		double cosY = Math.cos(yaw);
		double sinZ = Math.sin(roll);
		double cosZ = Math.cos(roll);
		m00 = (cosZ * cosY);
		m01 = (cosZ * -sinY * -sinX + sinZ * cosX);
		m02 = (cosZ * -sinY * cosX + sinZ * sinX);
		m10 = (-sinZ * cosY);
		m11 = (-sinZ * -sinY * -sinX + cosZ * cosX);
		m12 = (-sinZ * -sinY * cosX + cosZ * sinX);
		m20 = sinY;
		m21 = cosY * -sinX;
		m22 = cosY * cosX;
		tx = ty = tz = 0.0;
	}

	public void setEulerAngles(double yaw, double pitch) {
		double sinX = Math.sin(pitch);
		double cosX = Math.cos(pitch);
		double sinY = Math.sin(yaw);
		double cosY = Math.cos(yaw);
		m00 = cosY;
		m01 = sinY * sinX;
		m02 = -sinY * cosX;
		m10 = 0.0;
		m11 = cosX;
		m12 = sinX;
		m20 = sinY;
		m21 = cosY * -sinX;
		m22 = cosY * cosX;
		tx = ty = tz = 0.0;
	}
	
	public void rotate(double theta, double vx, double vy, double vz) 
	{
		// Dot product of the rotation vector
		double dot = (vx * vx) + (vy * vy) + (vz * vz);
		
		// If the vector isn't normalized then normalize it.
		if (!equal(dot, 1.0)) {
			double invlength = 1.0 / Math.sqrt(dot);
			vx *= invlength;
			vy *= invlength;
			vz *= invlength;
		}
		
		// Cache values used in quaternion matrix calculation
		double cos, sin, mcos, vx_mcos, vy_mcos, vz_mcos, vx_sin, vy_sin, vz_sin;
		cos = Math.cos(theta);
		sin = Math.sin(theta);
		mcos = 1.0 - cos;
		vx_mcos = vx * mcos;
		vy_mcos = vy * mcos;
		vz_mcos = vz * mcos;
		vx_sin = vx * sin;
		vy_sin = vy * sin;
		vz_sin = vz * sin;

		// Copy current matrix state into A
		double a00, a01, a02, a10, a11, a12, a20, a21, a22;
		a00 = m00; a01 = m01; a02 = m02;
		a10 = m10; a11 = m11; a12 = m12;
		a20 = m20; a21 = m21; a22 = m22;
		
		// Derived quaternion matrix into B
		double b00, b01, b02, b10, b11, b12, b20, b21, b22;
		b00 = vx * vx_mcos + cos;
		b10 = vy * vx_mcos + vz_sin;
		b20 = vz * vx_mcos - vy_sin;
		b01 = vx * vy_mcos - vz_sin;
		b11 = vy * vy_mcos + cos;
		b21 = vz * vy_mcos + vx_sin;
		b02 = vx * vz_mcos + vy_sin;
		b12 = vy * vz_mcos + vx_sin;
		b22 = vz * vz_mcos + cos;
		
		// Matrix multiplication (A x B)
		m00 = (a00 * b00) + (a01 * b10) + (a02 * b20);
		m10 = (a10 * b00) + (a11 * b10) + (a12 * b20);
		m20 = (a20 * b00) + (a21 * b10) + (a22 * b20);
		m01 = (a00 * b01) + (a01 * b11) + (a02 * b21);
		m11 = (a10 * b01) + (a11 * b11) + (a12 * b21);
		m21 = (a20 * b01) + (a21 * b11) + (a22 * b21);
		m02 = (a00 * b02) + (a01 * b12) + (a02 * b22);
		m12 = (a10 * b02) + (a11 * b12) + (a12 * b22);
		m22 = (a20 * b02) + (a21 * b12) + (a22 * b22);
	}

	public void rotatex(double pitch) {
		double c = Math.cos(pitch);
		double s = Math.sin(pitch);
		double a01 = m01, a11 = m11, a21 = m21;
		m01 = (a01 * c) + (m02 * s);
		m02 = (m02 * c) - (a01 * s);
		m11 = (a11 * c) + (m12 * s);
		m12 = (m12 * c) - (a11 * s);
		m21 = (a21 * c) + (m22 * s);
		m22 = (m22 * c) - (a21 * s);
	}
	
	public void rotatey(double yaw) {
		double c = Math.cos(yaw);
		double s = Math.sin(yaw);
		double a00 = m00, a10 = m10, a20 = m20;
		m00 = (a00 * c) - (m02 * s);
		m02 = (m02 * c) + (a00 * s);
		m10 = (a10 * c) - (m12 * s);
		m12 = (m12 * c) + (a10 * s);
		m20 = (a20 * c) - (m22 * s);
		m22 = (m22 * c) + (a20 * s);
	}
	
	public void rotatez(double roll) {
		double c = Math.cos(roll);
		double s = Math.sin(roll);
		double a00 = m00, a10 = m10, a20 = m20;
		m00 = (a00 * c) + (m01 * s);
		m01 = (m01 * c) - (a00 * s);
		m10 = (a10 * c) + (m11 * s);
		m11 = (m11 * c) - (a10 * s);
		m20 = (a20 * c) + (m21 * s);
		m21 = (m21 * c) - (a20 * s);
	}

	public void translate(double dx, double dy, double dz) {
		tx += (m00 * dx) + (m01 * dy) + (m02 * dz);
		ty += (m10 * dx) + (m11 * dy) + (m12 * dz);
		tz += (m20 * dx) + (m21 * dy) + (m22 * dz);
	}
	
	public void scale(double sx, double sy, double sz) {
		m00 *= sx;  m01 *= sy;  m02 *= sz;
		m10 *= sx;  m11 *= sy;  m12 *= sz;
		m20 *= sx;  m21 *= sy;  m22 *= sz;
	}
	
	public void shearxy(double hx, double hy) {
		m02 += (m00 * hx) + (m01 * hy);
		m12 += (m10 * hx) + (m11 * hy);
		m22 += (m20 * hx) + (m21 * hy);
	}
	
	public void shearxz(double hx, double hz) {
		m01 += (m00 * hx) + (m02 * hz);
		m11 += (m10 * hx) + (m12 * hz);
		m21 += (m20 * hx) + (m22 * hz);
	}
	
	public void shearyz(double hy, double hz) {
		m00 += (m01 * hy) + (m02 * hz);
		m10 += (m11 * hy) + (m12 * hz);
		m20 += (m21 * hy) + (m22 * hz);
	}
	
	private boolean equal(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}
	
	public static Matrix multiply(Matrix a, Matrix b) {
		Matrix m = new Matrix();
		
		m.m00 = (a.m00*b.m00) + (a.m01*b.m10) + (a.m02*b.m20);
		m.m01 = (a.m00*b.m01) + (a.m01*b.m11) + (a.m02*b.m21);
		m.m02 = (a.m00*b.m02) + (a.m02*b.m12) + (a.m02*b.m22);
		m.tx = (a.m00*b.tx) + (a.m01*b.ty) + (a.m02*b.tz) + a.tx;
		
		m.m10 = (a.m10*b.m00) + (a.m11*b.m10) + (a.m12*b.m20);
		m.m11 = (a.m10*b.m01) + (a.m11*b.m11) + (a.m12*b.m21);
		m.m12 = (a.m10*b.m02) + (a.m11*b.m12) + (a.m12*b.m22);
		m.ty = (a.m10*b.tx) + (a.m11*b.ty) + (a.m12*b.tz) + a.ty;
		
		m.m20 = (a.m20*b.m00) + (a.m21*b.m10) + (a.m22*b.m20);
		m.m21 = (a.m20*b.m01) + (a.m21*b.m11) + (a.m22*b.m21);
		m.m22 = (a.m20*b.m02) + (a.m21*b.m12) + (a.m22*b.m22);
		m.tz = (a.m20*b.tx) + (a.m21*b.ty) + (a.m22*b.tz) + a.tz;
			
		return m;
	}
	
}
