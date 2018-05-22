package net.philsprojects.game.animation;

import net.philsprojects.game.util.NameHashTable;

public class CharacterCreator
{


	protected Character _character = null;
	protected NameHashTable<Limb> _limbs = null;
	protected Joint _parentJoint = null;
	protected Joint _childJoint = null;
	protected Limb _limb = null;


	public CharacterCreator(String name, int maximumAnimations)
	{
		_limbs = new NameHashTable<Limb>(63);
		_character = new Character(name, maximumAnimations);
	}


	public void setSkeleton(Skeleton skeleton)
	{
		_character.setSkeleton(skeleton);
	}

	public boolean setParentJoint(String name)
	{
		Joint parent = getJoint(name);
		if (parent == null)
			return false;
		_parentJoint = parent;
		return true;
	}

	public boolean setChildJoint(String name)
	{
		Joint child = getJoint(name);
		if (child == null)
			return false;
		_childJoint = child;
		return true;
	}

	public boolean setLimb(String name)
	{
		Limb limb = _limbs.get(name);
		if (limb == null)
			return false;
		_limb = limb;
		return true;
	}

	public boolean addLimb(String name)
	{
		if (_parentJoint == null || _childJoint == null)
			return false;
		Limb limb = new Limb(name, _parentJoint, _childJoint);
		_limbs.add(limb);
		_limb = limb;
		return true;
	}

	public boolean setWidth(float width)
	{
		if (_limb == null)
			return false;
		_limb.setWidth(width);
		return true;
	}



	protected Joint getJoint(String name)
	{
		// If the skeleton or its joints aren't set then return null.
		if (_character.getSkeleton() == null)
			return null;
		Joint[] joints = _character.getSkeleton().getJoints();
		if (joints == null)
			return null;
		// Go through each joint and find the one with the matching name.
		for (int i = 0; i < joints.length; i++)
			if (joints[i].getName().equals(name))
				return joints[i];
		return null;
	}


}
