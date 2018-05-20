package axe;

import axe.anim.Delta;
import axe.anim.Motion;

public class Scalarf implements Delta<Scalarf>, Motion<Scalarf> {
	public static float PI = (float)Math.PI;
	public static float PI2 = (float)(Math.PI * 2.0);

	public float v;
	public Scalarf() {
	}
	public Scalarf(float v) {
		this.v = v;
	}
	public void set(float v) {
		this.v = v;
	}
	public void add(float s) {
		v += s;
	}
	public void sub(float s) {
		v -= s;
	}
	public void mul(float s) {
		v *= s;
	}
	public void div(float s) {
		if (s != 0.0) {
			v /= s;
		}
	}
	public void max(float s) {
		v = (v > s ? s : v);
	}
	public void min(float s) {
		v = (v < s ? s : v);
	}
	public void clamp(float max, float min) {
		v = (v < min ? min : (v > max ? max : v));
	}
	public void delta(float start, float end, float delta) {
		v = (end - start) * delta + start;
	}
	public void floor() {
		v = (float)StrictMath.floor(v);
	}
	public void ceil() {
		v = (float)StrictMath.ceil(v);
	}
	public void neg() {
		v = -v;
	}
	public void abs() {
		v = (v < 0 ? -v : v);
	}
	public void mod(float s) {
		v -= s * StrictMath.floor(v / s);
	}
	public float cos() {
		return (float)Math.cos(v);
	}
	public float sin() {
		return (float)Math.sin(v);
	}
	public float degrees() {
		return (float)Math.toDegrees(v);
	}
	public float radians() {
		return (float)Math.toRadians(v);
	}
	public float distance(Scalarf value) {
		return Math.abs(v - value.v);
	}
	public void delta(Scalarf start, Scalarf end, float delta) {
		v = (end.v - start.v) * delta + start.v;
	}
	public Scalarf get() {
		return this;
	}
	public void set(Scalarf value) {
		v = value.v;
	}
	public void add(Scalarf value, float scale) {
		v += value.v * scale;
	}
	public void max(Scalarf max) {
		v = StrictMath.min(v, max.v);
	}

	public static float mod(float v, float s) {
		return v - s * (float)StrictMath.floor(v / s);
	}
	public static float clamp(float v, float min, float max) {
		return (v < min ? min : (v > max ? max : v));
	}
	public static float cos(float angle) {
		return (float)StrictMath.cos(angle);
	}
	public static float sin(float angle) { 
		return (float)StrictMath.sin(angle);
	}
}