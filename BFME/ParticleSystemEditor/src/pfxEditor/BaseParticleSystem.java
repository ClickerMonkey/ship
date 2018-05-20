package pfxEditor;
public abstract class BaseParticleSystem implements IName, IDraw, ITexture {

	private IEmitterVolume _volume = null;
	private IEmitterVelocity _velocity = null;
	private LinkedList<IEmitterAffector> _affectors = null;

	private float _creationDelay = 0.0f;
	private Range _lifetime = new Range(-1);
	private float _life = 0.0f;
	private float _time = 0.0f;
	private boolean _creating = false;
	private boolean _visible = true;

	protected String _name = null;
	protected String _texture = null;
	protected Vector _location = Vector.zero();

	protected Range _particleLife;
	protected Range _particleSize;
	protected int _type = Particle.NORMAL;

	protected BoundingBox _bounds = BoundingBox.zero();
	protected boolean _showBounds = false;

	protected BaseParticleSystem() {
		_volume = new VolumeDefault();
		_velocity = new VelocityDefault();
		_affectors = new LinkedList<IEmitterAffector>();
	}

	public final void initialize() {
		_life = _lifetime.randomFloat();
		_time = 0f;
		_creating = false;
		onInitialize();
	}

	public final void draw() {
		/*
		 * if (_bounds.isOnScreen() && _visible) { final GraphicsLibrary g =
		 * GraphicsLibrary.getInstance(); g.setupParticle(_type);
		 * g.setTexture(_texture); g.clearSource(); g.beginTransform(); //
		 * g.translate(x - Camera.getInstance().getX(), y - //
		 * Camera.getInstance().getY()); // if (showBounds) //
		 * g.drawRectangle(bounds, Color.white()); onDraw(g); g.endTransform(); }
		 */
	}

	public final void update(float deltatime) {
		_time += deltatime;
		if (_time >= _creationDelay) {
			_creating = true;
			if (_time - _creationDelay >= _life && _life != -1) {
				_creating = false;
			}
		}
		if (_creating) {
			onUpdate(deltatime);
		}
	}

	protected abstract void onInitialize();

	protected abstract void onDraw();

	protected abstract void onUpdate(float deltatime);

	protected abstract Iterator<Particle> getIterator();

	public abstract int getParticlesAlive();

	protected final void affectParticles(float deltatime) {
		Iterator<Particle> particles = getIterator();
		Iterator<IEmitterAffector> affectors = _affectors.getIterator();
		while (affectors.hasNext()) {
			affectors.getNext().affectParticle(particles, deltatime);
			particles.reset();
		}
	}

	protected final Particle newParticle() {
		Particle p = new Particle(_particleLife.randomFloat());

		_volume.initialVolume(p);
		_velocity.initialVelocity(p);

		p.location.add(_location);
		p.size = _particleSize.randomFloat();

		return p;
	}

	public final void showBounds() {
		_showBounds = true;
	}

	public final void hideBounds() {
		_showBounds = false;
	}

	public final void addAffector(IEmitterAffector affector) {
		_affectors.add(affector);
	}

	public final void overrideAffector(IEmitterAffector affector) {
		String type = affector.getClass().getCanonicalName();
		for (int i = 0; i < _affectors.getSize(); i++) {
			if (_affectors.get(i).getClass().getCanonicalName().equals(type)) {
				_affectors.set(i, affector);
				return;
			}
		}
	}

	public final void setEmitterVolume(IEmitterVolume emitterVolume) {
		_volume = emitterVolume;
	}

	public final void setEmitterVelocity(IEmitterVelocity emitterVelocity) {
		_velocity = emitterVelocity;
	}

	public final void setTexture(String textureName) {
		_texture = textureName;
	}

	public final void setCreationDelay(float delay) {
		if (!_creating) {
			_creationDelay = delay;
			_time = 0.0f;
		}
	}

	public final void setLifeTime(float lifeMin, float lifeMax) {
		_lifetime = new Range(lifeMin, lifeMax);
	}

	public final void setLifeTime(float life) {
		_lifetime = new Range(life, life);
	}

	public final void setLocation(float x, float y, float z) {
		_location.set(x, y, z);
	}

	public final void setLocation(Vector location) {
		_location.set(location);
	}

	public final void slide(float deltaX, float deltaY, float deltaZ) {
		_location.add(deltaX, deltaY, deltaZ);
	}

	public final void slide(Vector delta) {
		_location.add(delta);
	}

	public final void translate(float deltaX, float deltaY, float deltaZ) {
		_location.add(deltaX, deltaY, deltaZ);
		Iterator<Particle> particles = getIterator();
		while (particles.hasNext()) {
			particles.getNext().location.add(deltaX, deltaY, deltaZ);
		}
	}

	public final void translate(Vector delta) {
		translate(delta.getX(), delta.getY(), delta.getZ());
	}

	public final void setParticleLife(float lifeMin, float lifeMax) {
		_particleLife = new Range(lifeMin, lifeMax);
	}

	public final void setParticleLife(float life) {
		_particleLife = new Range(life, life);
	}

	public final void setParticleSize(float sizeMin, float sizeMax) {
		_particleSize = new Range(sizeMin, sizeMax);
	}

	public final void setParticleSize(float size) {
		_particleSize = new Range(size, size);
	}

	public final void setParticleSize(Range sizes) {
		_particleSize = sizes;
	}

	public final void setParticleType(int particleType) {
		_type = particleType;
	}

	public final IEmitterVolume getEmitterVolume() {
		return _volume;
	}

	public final IEmitterVelocity getEmitterVelocity() {
		return _velocity;
	}

	public final String getTexture() {
		return _texture;
	}

	public final float getSystemTime() {
		return _time;
	}

	public final float getSystemLife() {
		return _life;
	}

	public final boolean isCreatingParticles() {
		return _creating;
	}

	public final float getX() {
		return _location.getX();
	}

	public final float getY() {
		return _location.getY();
	}

	public final float getZ() {
		return _location.getZ();
	}

	public final BoundingBox getBounds() {
		return _bounds;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean isVisible() {
		return _visible;
	}

	public void toggleVisibility() {
		_visible = !_visible;
	}

}
