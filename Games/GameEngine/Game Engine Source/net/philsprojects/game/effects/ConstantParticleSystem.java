package net.philsprojects.game.effects;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.Array;
import net.philsprojects.game.util.Iterator;

public class ConstantParticleSystem<T extends IParticle> extends BaseParticleSystem<T>
{

	public static final int DEFAULT_SIZE = 128;

	private int _count = 0;
	private Array<T> _particles;

	public ConstantParticleSystem(Class<T> particleType, String name)
	{
		this(particleType, name, DEFAULT_SIZE);
	}

	public ConstantParticleSystem(Class<T> particleType, String name, int maxParticles)
	{
		super(particleType);
		_count = maxParticles;
		_name = name;
		_particles = new Array<T>(maxParticles);
	}

	@Override
	public int getParticlesAlive()
	{
		return _count;
	}

	@Override
	protected void onDraw(GraphicsLibrary g)
	{
	}

	@Override
	public void dispose()
	{
		_particles = null;
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < _count; i++)
			_particles.set(i, newParticle());
	}

	@Override
	protected void onUpdate(float deltatime)
	{
		/** Update each particle and if dead then reset it as a new particle. */
		for (int i = 0; i < _count; i++)
		{
			_particles.get(i).update(deltatime);
			if (_particles.get(i).isDead())
				_particles.set(i, newParticle());
		}
		affectParticles(deltatime);
	}

	protected Iterator<T> getIterator()
	{
		return _particles.getIterator();
	}

	public IParticle getParticle(int index)
	{
		return _particles.get(index);
	}

	public ConstantParticleSystem<T> getClone()
	{
		ConstantParticleSystem<T> clone = new ConstantParticleSystem<T>(_particleType, _name + "#");
		clone._affectors = _affectors.getReferencedClone();
		clone._angle = _angle;
		clone._boundsAlphaMin = _boundsAlphaMin;
		clone._count = _count;
		clone._creationDelay = _creationDelay;
		clone._enabled = _enabled;
		clone._lifetime = _lifetime.getClone();
		clone._location = _location.getClone();
		clone._offset = _offset.getClone();
		clone._particleAlphas = _particleAlphas;
		clone._particleAngles = _particleAngles;
		clone._particleColorB = _particleColorB;
		clone._particleColorG = _particleColorG;
		clone._particleColorR = _particleColorR;
		clone._particleAlphas = _particleAlphas;
		clone._particleLife = _particleLife.getClone();
		clone._particleSizes = _particleSizes;
		clone._scale = _scale.getClone();
		clone._showBounds = _showBounds;
		clone._tile = _tile.getClone();
		clone._type = _type;
		clone._velocity = _velocity;
		clone._volume = _volume;
		return clone;
	}

	public static abstract class Adapter<T extends IParticle> extends ConstantParticleSystem<T>
	{
		public Adapter(Class<T> particleType, String name)
		{
			super(particleType, name);
			loadSystem();
		}

		public Adapter(Class<T> particleType, String name, int maxParticles)
		{
			super(particleType, name, maxParticles);
			loadSystem();
		}

		public abstract void loadSystem();
	}

}
