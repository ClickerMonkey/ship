package net.philsprojects.data.var;

import static org.junit.Assert.*;

import net.philsprojects.BaseTest;
import net.philsprojects.data.Store;
import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.Var;
import net.philsprojects.data.store.MemoryStore;

public class TestVar extends BaseTest 
{

	protected <T> void testAccessors(Var<T> var, T defaultValue, T value) 
	{
		testAccessors(var, defaultValue, value, value);
	}

	protected <T> void testAccessors(Var<T> var, T defaultValue, T value, T expected) 
	{
		assertEquals( defaultValue, var.getValue() );
		var.setValue(value);
		assertEquals( expected, var.getValue() );
	}
	
	protected <T> void testPersist(Var<T> var1, Var<T> var2, T value)
	{
		testPersist(var1, var2, value, value);
	}
	
	protected <T> void testPersist(Var<T> var1, Var<T> var2, T value, T expected) 
	{
		Store store = new MemoryStore("tmp");
		store.create(StoreAccess.ReadWrite, var1.getSize());

		var1.setStore(store);
		var1.setLocation(0);
		var2.setStore(store);
		var2.setLocation(0);
		
		var1.putValue(value);
		
		assertEquals( expected, var2.takeValue() );
	}
	
}
