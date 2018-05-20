package axe.texture;

public class SourceTile {
	public Texture texture;
	public float s0, s1, t0, t1;
	public SourceTile() {
		this(null, 0f, 0f, 0f, 0f);
	}
	public SourceTile(Texture texture, float s0, float t0, float s1, float t1) {
		this.texture = texture;
		this.s0 = s0;
		this.t0 = t0;
		this.s1 = s1;
		this.t1 = t1;
	}
	public void set(SourceTile tile) {
		s0 = tile.s0;
		t0 = tile.t0;
		s1 = tile.s1;
		t1 = tile.t1;
		texture = tile.texture;
	}
	public void shift(Source src) {
		shift(src.s, src.t);
	}
	public void shift(float s, float t) {
		s0 += s;
		t0 += t;
		s1 += s;
		t1 += t;
	}
	public void get(Source[] srcs) {
		srcs[0].set(s0, t0);
		srcs[1].set(s0, t1);
		srcs[2].set(s1, t1);
		srcs[3].set(s1, t0);
	}
	public float cs() {
		return (s0 + s1) * 0.5f;
	}
	public float ct() {
		return (t0 + t1) * 0.5f;
	}
	public void bind() {
		if (texture != null) {
			texture.bind();	
		}
	}
}
