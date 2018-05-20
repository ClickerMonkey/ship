package net.philsprojects.game.util;

import net.philsprojects.game.IClone;
import net.philsprojects.game.IName;

public class NameLinkedList<T extends IName> extends LinkedList<T>
{

	public T remove(String name)
	{
		Node n = getFirstNode();
		while (n != null)
		{
			if (n._element.getName().equals(name))
			{
				T element = n._element;
				remove(n);
				return element;
			}
			n = n._next;
		}
		return null;
	}

	public T get(String name)
	{
		Node n = getFirstNode();
		while (n != null)
		{
			if (n._element.getName().equals(name))
			{
				return n._element;
			}
			n = n._next;
		}
		return null;
	}

	public T set(String name, T element)
	{
		Node n = getFirstNode();
		while (n != null)
		{
			if (n._element.getName().equals(name))
			{
				n._element = element;
				return element;
			}
			n = n._next;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public NameLinkedList<T> getClone()
	{
		if (getSize() == 0)
			return new NameLinkedList<T>();

		NameLinkedList<T> clone = new NameLinkedList<T>();
		Iterator<T> i = getIterator();
		while (i.hasNext())
		{
			T element = i.getNext();
			if (element instanceof IClone)
				clone.add(((IClone<T>)element).getClone());
			else
				clone.add(element);
		}
		return clone;
	}

}
