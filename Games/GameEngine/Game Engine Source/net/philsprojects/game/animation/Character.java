package net.philsprojects.game.animation;

import net.philsprojects.game.*;
import net.philsprojects.game.util.NameHashTable;

public class Character implements IName, IUpdate, IDraw
{

	protected Skeleton _skeleton = null;
	protected Animation _animation = null;
	protected NameHashTable<Animation> _animations = null;
	protected Limb[] _limbs = null;
	protected String _name = null;
	protected boolean _enabled = true;
	protected boolean _visible = true;


	public Character(String name, int maxAnimations)
	{
		_name = name;
		_animations = new NameHashTable<Animation>(maxAnimations);
	}


	public void update(float deltatime)
	{
		if (!_enabled)
			return;
		_skeleton.update();

		for (int i = 0; i < _limbs.length; i++)
			_limbs[i].update();
	}

	public void draw()
	{
		if (!_visible)
			return;
		for (int i = 0; i < _limbs.length; i++)
			_limbs[i].draw();
	}


	public void setVisible(boolean visible)
	{
		_visible = visible;
	}

	public void setSkeleton(Skeleton skeleton)
	{
		_skeleton = skeleton;
	}

	public boolean addAnimation(Animation animation)
	{
		return _animations.add(animation);
	}

	public void setLimbs(Limb[] limbs)
	{
		_limbs = limbs;
	}



	public String getName()
	{
		return _name;
	}

	public Skeleton getSkeleton()
	{
		return _skeleton;
	}

	public Animation getCurrentAnimation()
	{
		return _animation;
	}

	public NameHashTable<Animation> getAnimations()
	{
		return _animations;
	}

	public Limb[] getLimbs()
	{
		return _limbs;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public boolean isVisible()
	{
		return _visible;
	}




}
