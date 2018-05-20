package net.philsprojects.game.effects;

import net.philsprojects.game.*;
import net.philsprojects.game.sprites.*;
import net.philsprojects.game.util.*;

import com.sun.opengl.util.texture.Texture;


public abstract class BaseParticleSystem<T extends IParticle> implements ISystemNode
{
	public static final short HEADER = 0x4004;

	protected IEmitterVolume _volume = null;
	protected IEmitterVelocity _velocity = null;
	protected LinkedList<IEmitterAffector> _affectors = null;

	protected float _angle = 0f;
	protected Vector _scale = Vector.one();
	protected Vector _offset = Vector.zero();

	private boolean _alive = false;
	private boolean _visible = true;
	private float _life = 0.0f;
	private float _time = 0.0f;
	protected float _creationDelay = 0.0f;
	protected Range _lifetime = new Range(-1);
	protected Class<T> _particleType;

	protected boolean _enabled = false;
	protected String _name = null;
	protected ISpriteTile _tile = null;
	protected Vector _location = Vector.zero();

	protected Range _particleLife;
	protected int _type = Particle.NORMAL;

	protected BoundingBox _bounds = BoundingBox.zero();
	protected boolean _showBounds = false;
	protected float _boundsAlphaMin = 0.2f;


	protected ICurve _particleColorR = new SmoothCurve(1f, new float[] { 1f, 1f });
	protected ICurve _particleColorG = new SmoothCurve(1f, new float[] { 1f, 1f });
	protected ICurve _particleColorB = new SmoothCurve(1f, new float[] { 1f, 1f });
	protected ICurve _particleSizes = new SmoothCurve(1f, new float[] { 32f, 32f });
	protected ICurve _particleAlphas = new SmoothCurve(1f, new float[] { 1f, 0f });
	protected ICurve _particleAngles = new SmoothCurve(1f, new float[] { 0f, 0f });

	protected BaseParticleSystem(Class<T> particleType)
	{
		_particleType = particleType;
		_volume = new VolumeDefault();
		_velocity = new VelocityDefault();
		_affectors = new LinkedList<IEmitterAffector>();
	}

	public final void initialize()
	{
		// Set the life of this System and enable it.
		_life = _lifetime.randomFloat();
		_time = 0f;
		_alive = false;
		_enabled = true;
		// Call on sub-class to initialize the rest.
		onInitialize();
	}

	public final void draw()
	{
		if (!_visible || _tile == null)
			return;
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		final Camera c = Camera.getInstance();
		_bounds.clear(_offset.x, _offset.y);
		g.setupParticle(_type);
		g.setTexture(_tile.getTexture());
		g.setSource(_tile.getSource());

		g.beginTransform();
		g.translate(_location.x - c.getX(), _location.y - c.getY());
		g.rotate(_angle);
		g.scale(_scale.x, _scale.y);

		Iterator<T> iter = getIterator();
		float size = 0f, angle = 0f, alpha = 0f, red = 0f, green = 0f, blue = 0f, delta = 0f;
		IParticle p = null;
		while (iter.hasNext())
		{
			p = iter.getNext();
			delta = p.getLife() / p.getLifeTime();
			size = _particleSizes.getValue(delta);
			angle = _particleAngles.getValue(delta);
			alpha = _particleAlphas.getValue(delta);
			red = _particleColorR.getValue(delta);
			green = _particleColorG.getValue(delta);
			blue = _particleColorB.getValue(delta);

			if (alpha >= _boundsAlphaMin)
				_bounds.include(p.getX(), p.getY());

			if (p.isCustomDraw())
				p.draw();
			else
				g.drawParticle(p.getX(), p.getY(), size, angle, alpha, red, green, blue);
		}
		if (_showBounds)
			g.drawRectangle(_bounds, Color.black(), true);
		onDraw(g);

		g.endTransform();
	}

	public final void update(float deltatime)
	{
		_time += deltatime;
		if (_time >= _creationDelay)
		{
			_alive = true;
			if (_time - _creationDelay >= _life && _life != -1)
			{
				_alive = false;
			}
		}
		if (_alive)
		{
			if (_tile != null)
				_tile.update(deltatime);
			onUpdate(deltatime);
		}
	}

	protected abstract void onInitialize();

	protected abstract void onDraw(GraphicsLibrary g);

	protected abstract void onUpdate(float deltatime);


	protected abstract Iterator<T> getIterator();


	public abstract int getParticlesAlive();

	public abstract void dispose();


	protected final void affectParticles(float deltatime)
	{
		Iterator<T> particles = getIterator();
		Iterator<IEmitterAffector> affectors = _affectors.getIterator();
		while (affectors.hasNext())
		{
			affectors.getNext().affectParticle(particles, deltatime);
			particles.reset(); // Resets the particle Iterator back to the
			// beginning
		}
	}

	protected final T newParticle()
	{
		T p = null;
		try
		{
			p = _particleType.getConstructor(new Class[] {}).newInstance(new Object[] {});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (p == null)
			return null;

		p.setLifeTime(_particleLife.randomFloat());

		_volume.initialVolume(p);
		_velocity.initialVelocity(p);

		p.setX(p.getX() + _offset.x);
		p.setY(p.getY() + _offset.y);

		return p;
	}

	public final void showBounds()
	{
		_showBounds = true;
	}

	public final void hideBounds()
	{
		_showBounds = false;
	}

	public final void addAffector(IEmitterAffector affector)
	{
		if (affector == null)
			return;
		_affectors.add(affector);
	}

	public final void overrideAffector(IEmitterAffector affector)
	{
		Object type = affector.getClass();
		for (int i = 0; i < _affectors.getSize(); i++)
		{
			if (_affectors.get(i).getClass().equals(type))
			{
				_affectors.set(i, affector);
				return;
			}
		}
	}

	public final void move(float x, float y)
	{
		_offset.add(x, y);
	}

	public final void move(Vector delta)
	{
		_offset.add(delta);
	}

	public final void translate(float x, float y)
	{
		_location.add(x, y);
	}

	public final void translate(Vector delta)
	{
		_location.add(delta.x, delta.y);
	}



	public final void setEmitterVolume(IEmitterVolume emitterVolume)
	{
		if (emitterVolume == null)
			return;
		_volume = emitterVolume;
	}

	public final void setEmitterVelocity(IEmitterVelocity emitterVelocity)
	{
		if (emitterVelocity == null)
			return;
		_velocity = emitterVelocity;
	}

	public final void setParticleColors(ICurve particleColorR, ICurve particleColorG, ICurve particleColorB)
	{
		if (particleColorR == null || particleColorG == null || particleColorB == null)
			return;
		_particleColorR = particleColorR;
		_particleColorG = particleColorG;
		_particleColorB = particleColorB;
	}

	public final void setParticleColors(Color... colors)
	{
		if (colors == null)
			return;
		int count = colors.length;
		float[] r = new float[count];
		float[] g = new float[count];
		float[] b = new float[count];
		for (int i = 0; i < count; i++)
		{
			r[i] = colors[i].getR();
			g[i] = colors[i].getG();
			b[i] = colors[i].getB();
		}
		_particleColorR = new SmoothCurve(1f, r);
		_particleColorG = new SmoothCurve(1f, g);
		_particleColorB = new SmoothCurve(1f, b);
	}

	public final void setParticleColors(int depth, float[] times, Color[] colors)
	{
		if (!(times == null || colors == null))
			return;
		int count = colors.length;
		float[] r = new float[count];
		float[] g = new float[count];
		float[] b = new float[count];
		for (int i = 0; i < count; i++)
		{
			r[i] = colors[i].getR();
			g[i] = colors[i].getG();
			b[i] = colors[i].getB();
		}
		_particleColorR = new Curve(depth, times, r);
		_particleColorG = new Curve(depth, times, g);
		_particleColorB = new Curve(depth, times, b);
	}

	public final void setParticleAlphas(ICurve particleAlphas)
	{
		if (particleAlphas == null)
			return;
		_particleAlphas = particleAlphas;
	}

	public final void setParticleAlphas(float... alphas)
	{
		if (alphas == null)
			return;
		_particleAlphas = new SmoothCurve(1f, alphas);
	}

	public final void setParticleAlphas(int depth, float[] times, float[] alphas)
	{
		if (times == null || alphas == null)
			return;
		_particleAlphas = new Curve(depth, times, alphas);
	}

	public final void setParticleSizes(ICurve particleSizes)
	{
		if (particleSizes == null)
			return;
		_particleSizes = particleSizes;
	}

	public final void setParticleSizes(float... sizes)
	{
		if (sizes == null)
			return;
		_particleSizes = new SmoothCurve(1f, sizes);
	}

	public final void setParticleSizes(int depth, float[] times, float[] sizes)
	{
		if (times == null || sizes == null)
			return;
		_particleSizes = new Curve(depth, times, sizes);
	}

	public final void setParticleAngles(ICurve particleAngles)
	{
		if (particleAngles == null)
			return;
		_particleAngles = particleAngles;
	}

	public final void setParticleAngles(float... angles)
	{
		if (angles == null)
			return;
		_particleAngles = new SmoothCurve(1f, angles);
	}

	public final void setParticleAngles(int depth, float[] times, float[] angles)
	{
		if (times == null || angles == null)
			return;
		_particleAngles = new Curve(depth, times, angles);
	}



	public final float getParticleAngle(float delta)
	{
		return _particleAngles.getValue(delta);
	}

	public final float getParticleSize(float delta)
	{
		return _particleSizes.getValue(delta);
	}

	public final float getParticleAlpha(float delta)
	{
		return _particleAlphas.getValue(delta);
	}

	public final Color getParticleColor(float delta)
	{
		return new Color(_particleColorR.getValue(delta), _particleColorG.getValue(delta), _particleColorB.getValue(delta));
	}



	public final void setTile(ISpriteTile tile)
	{
		_tile = tile;
	}

	public final void setTile(String texture)
	{
		Texture tex = TextureLibrary.getInstance().get(texture);
		_tile = new SpriteTileStatic(texture, texture, 0, 0, tex.getWidth(), tex.getHeight());
	}

	public final void setCreationDelay(float delay)
	{
		if (!_alive)
		{
			_creationDelay = delay;
			_time = 0.0f;
		}
	}

	public final void setLifeTime(float lifeMin, float lifeMax)
	{
		_lifetime = new Range(lifeMin, lifeMax);
	}

	public final void setLifeTime(float life)
	{
		_lifetime = new Range(life, life);
	}

	public final void setVisible(boolean visible)
	{
		_visible = visible;
	}

	public final void setLocation(float x, float y)
	{
		_location.set(x, y);
	}

	public final void setLocation(Vector location)
	{
		_location.set(location);
	}

	public final void setEmitterOffset(float x, float y)
	{
		_offset.set(x, y);
	}

	public final void setEmitterOffset(Vector offset)
	{
		_offset.set(offset);
	}

	public final void setEmitterAngle(float angle)
	{
		_angle = angle;
	}

	public final void setEmitterScale(Vector scale)
	{
		_scale.set(scale);
	}

	public final void setEmitterScale(float x, float y)
	{
		_scale.set(x, y);
	}

	public final void addEmitterOffset(float x, float y)
	{
		_offset.add(x, y);
	}

	public final void addEmitterAngle(float angle)
	{
		_angle += angle;
	}

	public final void addEmitterScale(float x, float y)
	{
		_scale.add(x, y);
	}

	public final void setParticleLife(float lifeMin, float lifeMax)
	{
		_particleLife = new Range(lifeMin, lifeMax);
	}

	public final void setParticleLife(float life)
	{
		_particleLife = new Range(life, life);
	}

	public final void setParticleType(int particleType)
	{
		_type = particleType;
	}


	public final float getX()
	{
		return _location.x;
	}

	public final float getY()
	{
		return _location.y;
	}

	public final float getOffsetX()
	{
		return _offset.x;
	}

	public final float getOffsetY()
	{
		return _offset.y;
	}

	public final float getActualX()
	{
		return _location.x + _offset.x;
	}

	public final float getActualY()
	{
		return _location.y + _offset.y;
	}

	public final float getScreenX()
	{
		return (_location.x + _offset.x) - Camera.getInstance().getX();
	}

	public final float getScreenY()
	{
		return Camera.getInstance().getHeight() - ((_location.y + _offset.y) - Camera.getInstance().getY());
	}

	public final BoundingBox getBounds()
	{
		return _bounds;
	}

	public String getName()
	{
		return _name;
	}

	public final ISpriteTile getTile()
	{
		return _tile;
	}

	public final IEmitterVolume getEmitterVolume()
	{
		return _volume;
	}

	public final IEmitterVelocity getEmitterVelocity()
	{
		return _velocity;
	}

	public final Vector getLocation()
	{
		return _location;
	}

	public final Vector getEmitterOffset()
	{
		return _offset;
	}

	public final float getEmitterAngle()
	{
		return _angle;
	}

	public final Vector getEmitterScale()
	{
		return _scale;
	}

	public boolean isVisible()
	{
		return _visible;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public final float getSystemTime()
	{
		return _time;
	}

	public final float getSystemLife()
	{
		return _life;
	}

	public final boolean isAlive()
	{
		return _alive;
	}

}
