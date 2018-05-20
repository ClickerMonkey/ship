package net.philsprojects.game.animation;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.NameHashTable;
import net.philsprojects.game.util.Vector;

import static net.philsprojects.game.util.Math.*;

public class SkeletonCreator
{

	protected float _defaultDistance;
	protected float _defaultAngle;
	protected Skeleton _skeleton;
	protected Joint _joint;
	protected NameHashTable<Joint> _joints;
	protected boolean _lockedAngles = false;
	protected boolean _lockedDistances = false;
	protected boolean _childrenRotate = false;



	public SkeletonCreator(String name)
	{
		_joints = new NameHashTable<Joint>(63);
		_skeleton = new Skeleton(name);
	}



	public boolean setCurrentJoint(String name)
	{
		return setCurrentJoint(_joints.get(name));
	}

	public boolean setCurrentJoint(Joint j)
	{
		if (j == null)
			return false;
		_joint = j;
		return true;
	}



	public boolean addJoint(String name)
	{
		return addJoint(new Joint(name, _defaultDistance, _defaultAngle));
	}

	public boolean addJoint(Joint j)
	{
		// Make sure we are not adding a joint with a similar name.
		if (j == null || _joints.exists(j.getName()))
			return false;
		j.setParent(_joint);
		j.setLevel(_joint.getLevel() + 1);
		_joints.add(j);
		updateSkeletonJoints();
		return true;
	}



	public void setDistance(Vector point)
	{
		if (_lockedDistances)
			return;
		// Since the distance of the current joint isn't locked set its new one.
		_joint.setDistance(distance(_joint.getLocation(), point));
		// Update the whole skeleton now thats something changed.
		updateSkeleton();
	}

	public void setAngle(Vector point)
	{
		if (_lockedAngles)
			return;
		// Since the angle of the current joint isn't locked set its new one.
		float oldAngle = _joint.getAngle();
		float newAngle = angle(_joint.getLocation(), point);
		_joint.setAngle(newAngle);
		// If the children of this joint all rotate with it.
		if (_childrenRotate)
		{
			// Add the difference between the old and new angle to each child joint
			float incAngle = newAngle - oldAngle;
			Iterator<Joint> iter = getChildren();
			Joint current = null;
			while (iter.hasNext()) {
				current = iter.getNext();
				current.setAngle(current.getAngle() + incAngle);
			}
		}
		// Update the whole skeleton now thats something changed.
		updateSkeleton();
	}



	public void setLockAngle(boolean lockedAngle)
	{
		_lockedAngles = lockedAngle;
	}

	public void setLockDistance(boolean lockedDistance)
	{
		_lockedDistances = lockedDistance;
	}

	public void setChildrenRotate(boolean childrenRotate)
	{
		_childrenRotate = childrenRotate;
	}



	public Joint getCurrent()
	{
		return _joint;
	}

	public Skeleton getSkeleton()
	{
		return _skeleton;
	}

	public boolean doesLockAngles()
	{
		return _lockedAngles;
	}

	public boolean doesLockDistances()
	{
		return _lockedDistances;
	}

	public boolean doesChildrenRotate()
	{
		return _childrenRotate;
	}


	public void updateSkeleton()
	{
		_skeleton.update();
	}

	public void updateSkeletonJoints()
	{
		int total = _joints.getSize();
		// Gets each joint and stores them in an array.
		Joint[] hashJoints = getJoints();
		// Goes through each level of the joints and sets them in the correct order.
		Joint[] skeletonJoints = new Joint[total];
		int currentLevel = 0;
		int set = 0;
		while (set < total)
		{
			//For each Joint on  level 'currentLevel' set it!
			for (int i = 0; i < total; i++)
				if (hashJoints[i].getLevel() == currentLevel)
					skeletonJoints[set++] = hashJoints[i];
			// Increase the level each repetition.
			currentLevel++;
		}
		_skeleton.setJoints(skeletonJoints);
	}



	protected Iterator<Joint> getChildren()
	{
		LinkedList<Joint> queue = new LinkedList<Joint>();
		LinkedList<Joint> children = new LinkedList<Joint>();
		Joint[] hashJoints = getJoints();
		Joint current = null;
		// Start off using the current joint
		queue.add(_joint);
		// Do a breadth-first search of the joints
		while (queue.getSize() > 0)
		{
			// The number of joints in the current depth
			int size = queue.getSize();
			for (int i = 0; i < size; i++)
			{
				current = queue.removeFirst();
				// For each joint add the ones that their parents name matches the
				// name of the current joint at the depth.
				for (int j = 0; j < hashJoints.length; j++)
				{
					if (hashJoints[i].getParentName().equals(current.getName()))
					{
						queue.add(hashJoints[i]);
						children.add(hashJoints[i]);
					}
				}
			}
		}
		return children.getIterator();
	}

	protected Joint[] getJoints()
	{
		Joint[] hashJoints = new Joint[_joints.getSize()];
		// Sets the hashJoints
		int index = 0;
		for (Iterator<Joint> i = _joints.getIterator(); i.hasNext(); index++)
			hashJoints[index] = i.getNext();
		return hashJoints;
	}

}
