package pfxEditor;
// 40 bytes
public class Particle {

	public final static int FULL = 1;
	public final static int ADDITIVE = 2;
	public final static int NEGATIVE = 3;
	public final static int NORMAL = 4;

	public Vector location = Vector.zero();
	public Vector velocity = Vector.zero();
	public float size = 0;
	public float angle = 0;
	public float creationYaw = 0;
	public float creationPitch = 0;
	public float time = 0;
	public float life = 0;
	public Color shade = Color.white();

	public Particle(float lifeTime) {
		life = lifeTime;
	}

	public void update(float deltatime) {
		time += deltatime;
		location = Vector.add(location, Vector.multiply(velocity, deltatime));
	}

	public final boolean isDead() {
		return (time >= life);
	}

	public float lifeDelta() {
		return time / life;
	}

}
