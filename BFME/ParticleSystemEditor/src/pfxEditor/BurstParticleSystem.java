package pfxEditor;
/**
 * A particle system where a certain number of particles are emitted on a
 * certain interval range. It may be controlled with only a certain number of
 * bursts or it may emit continually until disabled.
 * 
 * @author Philip Diffenderfer
 */
public class BurstParticleSystem extends BaseParticleSystem {
	/**
	 * Applies for the maximum amount of particles and the amount of bursts a
	 * system can have.
	 */
	public static final int INFINITE = -1;

	/** The range of the interval for the number of seconds between bursts. */
	protected Range _burstDelays = new Range(0f);
	/** The range for the number of particles emitted each burst. */
	protected Range _burstCounts = new Range(0);
	/** A list of the living particles. */
	protected LinkedList<Particle> _particles;

	/**
	 * Maximum number of particles allowed or if infinite are allowed then its
	 * -1.
	 */
	private int _maxParticles = INFINITE;
	/** Keeps track of how many seconds have passed since last burst. */
	private float _time = 0f;
	/** How many seconds of delay in-between the current bursts. */
	private float _burstDelay = 0f;
	/** How many particles are to be emitted after the delay is up. */
	private int _burstCount = 0;
	/** How many bursts remain in a system that has a preset number of bursts. */
	private int _bursts = INFINITE;

	/**
	 * Default Constructor. Initializes a particle system with no maximum of
	 * particles allowed and an infinite amount of bursts.
	 */
	public BurstParticleSystem() {
	}

	/**
	 * Constructor that initializes a particle system with a set amount of
	 * bursts but no restriction on max particles allowed.
	 * 
	 * @param bursts
	 *            The number of bursts that are emitted.
	 */
	public BurstParticleSystem(int bursts) {
		_bursts = bursts;
	}

	/**
	 * Constructor that initializes a particle system with a set amount of
	 * bursts and is restricted with how many particles are allowed to be living
	 * at one time.
	 * 
	 * @param bursts
	 *            The number of bursts that are emitted.
	 * @param maxParticles
	 *            The maximum number of particles allowed to live at one time.
	 */
	public BurstParticleSystem(int bursts, int maxParticles) {
		_bursts = bursts;
		_maxParticles = maxParticles;
	}

	/**
	 * 
	 * @param minBursts
	 * @param maxBursts
	 * @param maxParticles
	 */
	public BurstParticleSystem(int minBursts, int maxBursts, int maxParticles) {
		_bursts = Range.random(minBursts, maxBursts);
		_maxParticles = maxParticles;
	}

	/**
	 * Draws all of the particles from oldest to newest where the oldest are in
	 * the back.
	 */
	@Override
	protected void onDraw() {

	}

	/**
	 * Initializes the starting burst delay and burst count and clears the list
	 * of particles.
	 */
	@Override
	protected void onInitialize() {
		_burstDelay = _burstDelays.randomFloat();
		_burstCount = _burstCounts.randomInteger();
		_time = _burstDelay;
		_particles.clear();
	}

	/**
	 * Updates the system, emits particles if needed, and removes the dead
	 * particles.
	 */
	@Override
	protected void onUpdate(float deltatime) {
		if (_bursts == 0)
			return;
		_time += deltatime;
		if (_time >= _burstDelay) {
			/** Don't release more then allowed. */
			if (_maxParticles != -1
					&& _burstCount + _particles.getSize() > _maxParticles)
				_burstCount = _maxParticles - _particles.getSize();
			/** Release new particles */
			for (int i = 0; i < _burstCount; i++)
				_particles.add(newParticle());
			/**
			 * Reset the time, the new burst delay and burst count and decrement
			 * bursts remaining.
			 */
			_time -= _burstDelay;
			_burstDelay = _burstDelays.randomFloat();
			_burstCount = _burstCounts.randomInteger();
			_bursts--;
		}
		if (_particles.getSize() == 0)
			return;
		/** Update each particle and remove dead ones. */
		LinkedList<Particle>.Node node = _particles.getFirstNode();
		LinkedList<Particle>.Node next = null;
		while (node != null) {
			next = node.next;
			node.element.update(deltatime);
			if (node.element.isDead())
				_particles.remove(node);
			node = next;
		}
		/** Apply their visual changes. */
		affectParticles(deltatime);
	}

	/**
	 * 
	 * @param min
	 * @param max
	 */
	public void setBurstCount(int min, int max) {
		setBurstCount(new Range(min, max));
	}

	/**
	 * 
	 * @param burstCount
	 */
	public void setBurstCount(Range burstCount) {
		_burstCounts = burstCount;
	}

	/**
	 * 
	 * @param min
	 * @param max
	 */
	public void setBurstDelay(float min, float max) {
		setBurstCount(new Range(min, max));
	}

	/**
	 * 
	 * @param burstDelay
	 */
	public void setBurstDelay(Range burstDelay) {
		_burstDelays = burstDelay;
	}

	/**
	 * 
	 * @param min
	 * @param max
	 */
	public void setTotalBursts(int min, int max) {
		setTotalBursts(new Range(min, max));
	}

	/**
	 * 
	 * @param totalBursts
	 */
	public void setTotalBursts(Range totalBursts) {
		_bursts = totalBursts.randomInteger();
	}

	/**
	 * 
	 * @param maxParticles
	 */
	public void setMaxParticles(int maxParticles) {
		_maxParticles = maxParticles;
	}

	/**
	 * Returns an iterator to go through the living particles.
	 */
	@Override
	protected Iterator<Particle> getIterator() {
		return _particles.getIterator();
	}

	/**
	 * Returns the number of particles living.
	 */
	@Override
	public int getParticlesAlive() {
		return _particles.getSize();
	}

	/**
	 * Returns the range for the number of particles emitted each burst.
	 */
	public Range getBurstCount() {
		return _burstCounts;
	}

	/**
	 * Returns the range of the interval for the number of seconds between
	 * bursts.
	 */
	public Range getBurstDelay() {
		return _burstDelays;
	}

	/**
	 * Returns the current delay in seconds between the last burst and the next
	 * one.
	 */
	public float getCurrentBurstDelay() {
		return _burstDelay;
	}

	/**
	 * Returns the next amount of particles to be emitted once the burst delay
	 * is up.
	 */
	public int getCurrentBurstCount() {
		return _burstCount;
	}

	/**
	 * Returns how many more bursts this system has before it dies, or -1 if it
	 * continually releases particles.
	 */
	public int getBurstsRemaining() {
		return _bursts;
	}

	/**
	 * Returns the time in seconds since last burst.
	 */
	public float getTime() {
		return _time;
	}

	/**
	 * Returns the maximum number of particles
	 */
	public int getMaxParticles() {
		return _maxParticles;
	}

	/**
	 * Used to create burst particle systems in the code where a function in
	 * brackets loads all the data and has options of modifying it. After the
	 * data has been loaded then the <code>initialize()</code> function must
	 * be called at the very end of the <code>loadSystem()</code> function.
	 * 
	 * @author Philip Diffenderfer
	 */
	public static abstract class Adapter extends BurstParticleSystem {
		public Adapter() {
			super();
			loadSystem();
		}

		public Adapter(int bursts) {
			super(bursts);
			loadSystem();
		}

		public Adapter(int bursts, int maxParticles) {
			super(bursts, maxParticles);
			loadSystem();
		}

		public abstract void loadSystem();

	}

}
