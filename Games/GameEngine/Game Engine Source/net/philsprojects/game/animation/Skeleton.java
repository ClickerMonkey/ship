package net.philsprojects.game.animation;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.util.Vector;


/**
 * A Basic structure for animated/skeletal game objects. The skeleton class is the base
 * joint where all other joints locations are based off their parent's.
 * 
 * @author Philip Diffenderfer
 */
public class Skeleton extends Joint
{    

	// Array of joints
	protected Joint[] _joints = null;


	/**
	 * Initializes a simple empty skeleton with a name.
	 * 
	 * @param name => The name of this skeleton.
	 */
	public Skeleton(String name)
	{
		_name = name;
	}


	/**
	 * Updates each joint starting from the skeleton's main joint to the outer bones.
	 */
	@Override
	public void update()
	{
		for (int i = 0; i < _joints.length; i++)
			_joints[i].update();
	}

	/**
	 * Updates all the Joints parents based off the parents name stored in each joint after loading.
	 */
	public void connectJoints()
	{
		String current;
		// For each joint search for its parent joint.
		for (int i = 0; i < _joints.length; i++)
		{
			// Find the parent for this Joint
			current = _joints[i].getParentName();
			// If the parent is this skeleton then set it.
			if (current.equals("") || current.equals(_name))
			{
				_joints[i].setParent(this);
			}
			else //If the parent is not the skeleton
			{
				// For each joint get the first one that matches the parents name.
				for (int j = 0; j < _joints.length; j++)
				{
					if (i == j)
						continue;
					// If they match set it, and exit out to do the next joint.
					if (current.equals(_joints[j].getName()))
					{
						_joints[i].setParent(_joints[j]);
						break;
					}
				}
			}
		}
	}

	/**
	 * Gets the location of the skeleton, which is also the main joint.
	 * 
	 * @return The reference to the skeleton's location.
	 */
	@Override
	public Vector getLocation()
	{
		return _location;
	}

	/**
	 * Gets the joints of this skeleton.
	 * 
	 * @return The reference to the array of joints in this skeleton.
	 */
	public Joint[] getJoints()
	{
		return _joints;
	}

	/**
	 * Sets the world location of the skeleton.
	 * 
	 * @param location => The location in the world to set.
	 */
	public void setLocation(Vector location)
	{
		_location.set(location);
	}

	/**
	 * Overrides the setLevel method from the joint so the skeleton's main joint level is always 0.
	 */
	@Override
	public void setLevel(int level)
	{
	}

	/**
	 * Overrides the setParent method so this skeleton will always be the base joint.
	 */
	@Override
	public void setParent(Joint parent)
	{
	}

	/**
	 * Sets this skeletons joints.
	 * 
	 * @param joints => The new joints of the skeleton.
	 */
	public void setJoints(Joint[] joints)
	{
		_joints = joints;
	}


	// <==========================================> //
	// <=== IBinary Interface ====================> //
	// <==========================================> //

	/**
	 * Initializes a Skeleton from a binary file.
	 */
	public Skeleton(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	/**
	 * Reads in all skeleton information from a binary file.
	 */
	@Override
	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_location.read(reader);
		int total = reader.readInt();
		_joints = new Joint[total];
		for (int i = 0; i < total; i++)
			_joints[i] = new Joint(reader);
		// Bind up all joints to their parents
		connectJoints();
	}

	/**
	 * Writes all skeleton information to a binary file.
	 */
	@Override
	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		_location.write(writer);
		writer.writeInt(_joints.length);
		for (int i = 0; i < _joints.length; i++)
			_joints[i].write(writer);
	}

}
