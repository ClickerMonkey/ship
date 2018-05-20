package com.wmd.util;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class verifies that a given class has the structure required for
 * serializability
 * 
 * @author Merlin
 * 
 */
public class SerializabilityVerifier
{
	private Class<?> classToCheck;

	/**
	 * @param classToCheck
	 */
	public SerializabilityVerifier(Class<?> classToCheck)
	{
		this.classToCheck = classToCheck;
	}

	/**
	 * Runs the entire series of checks for the structure required for
	 * serializability
	 */
	public void verifyStructureForSerializability()
	{
		if (!isSerializable(classToCheck))
		{
			fail(classToCheck.getName() + " must Implement IsSerializable");
		}
		Vector<Field> storedFields = getStoredFields(classToCheck);
		for (Field fieldToCheck : storedFields)
		{
			verifyGetterAndSetter(fieldToCheck, classToCheck);
		}
		verifyNoArgConstructor(classToCheck);
	}
	
	private boolean isSerializable(Class<?> iface)
	{
		if (iface == IsSerializable.class)
		{
			return true;
		}
		Class<?>[] list = iface.getInterfaces();
		for (Class<?> item : list) 
		{
			if (isSerializable(item)) 
			{
				return true;
			}
		}
		return false;
	}

	private Vector<Field> getStoredFields(Class<?> classToCheck)
	{
		Vector<Field> storedFields = new Vector<Field>();
		Vector<String> nonStoredFieldNames = getNonStoredFieldNames();

		Field[] allFields = classToCheck.getDeclaredFields();
		for (int i = 0; i < allFields.length; i++)
		{
			if ((allFields[i].getName() != "NON_SERIALIZED_FIELDS")
					&& (!nonStoredFieldNames.contains(allFields[i].getName()))
					&& !(Modifier.isFinal(allFields[i].getModifiers())))
			{
				storedFields.add(allFields[i]);
			}
		}
		return storedFields;
	}

	private Vector<String> getNonStoredFieldNames()
	{
		Vector<String> results = new Vector<String>();
		String[] nonStoredFieldNames = null;

		try
		{
			nonStoredFieldNames = (String[]) classToCheck.getDeclaredField(
					"NON_SERIALIZED_FIELDS").get(null);
			for (int i = 0; i < nonStoredFieldNames.length; i++)
			{
				results.add(nonStoredFieldNames[i]);
			}
		} catch (Exception e)
		{
			// don't worry if there's an exception - you don't have to have
			// NON_SERIALIZED_FIELDS
		}
		return results;
	}

	private void verifyNoArgConstructor(Class<?> classToCheck)
	{
		try
		{
			classToCheck.getDeclaredConstructor(new Class[0]);
		} catch (NoSuchMethodException e)
		{
			fail(classToCheck.getName() +" is missing no argument constructor");
		} catch (Exception e)
		{
			fail();
		}
	}

	private void verifyGetterAndSetter(Field fieldToCheck, Class<?> classToCheck)
	{
		StringBuffer capitalizedFieldName = new StringBuffer(fieldToCheck
				.getName());
		Class<?> type = fieldToCheck.getType();
		
		char firstChar = capitalizedFieldName
				.charAt(0);
		if ((firstChar >= 'a') && (firstChar <= 'z'))
		{
				capitalizedFieldName.setCharAt(0, (char) (firstChar - ('a' - 'A')));
		}
		try
		{
			if (type.toString().equals("boolean"))
				classToCheck.getDeclaredMethod("is" + capitalizedFieldName,
						new Class[0]);
			else
				classToCheck.getDeclaredMethod("get" + capitalizedFieldName,
						new Class[0]);
			classToCheck.getDeclaredMethod("set" + capitalizedFieldName,
					fieldToCheck.getType());
		} catch (NoSuchMethodException e)
		{
			fail(classToCheck.getName() + " is missing a getter or setter for " + fieldToCheck
					+ " which is not marked as not being serialized");
		} catch (Exception e)
		{
			fail();
		}
	}
}
