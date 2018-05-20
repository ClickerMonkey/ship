package net.philsprojects.data;

import static org.junit.Assert.*;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.data.store.MemoryStore;
import net.philsprojects.data.var.FloatVar;

public class TestDataArray extends BaseTest 
{

	@Test
	public void testConstructor() 
	{
		DataArray<FloatVar> arr = DataArray.create(FloatVar.class, 9, true);
		
		assertEquals( 36, arr.getSize() );
		assertNull( arr.getStore() );
		assertEquals( 0, arr.getLocation() );
	}
	
	@Test
	public void testLazyGetSet() 
	{
		Store store = new MemoryStore("temporary", 36);
		store.open(StoreAccess.ReadWrite);
		
		DataArray<FloatVar> arr = DataArray.create(FloatVar.class, 9, true);
		arr.setStore(store);
		
		assertEquals( 0.0f, arr.get(0).get(), 0.00001 );
		
		arr.set(0, new FloatVar(5.6f));
		assertEquals( 5.6f, arr.get(0).get(), 0.00001 );
		
		arr.set(8, new FloatVar(-34.5f));
		assertEquals( -34.5f, arr.get(8).get(), 0.00001 );
	}
	
	@Test
	public void testNonLazyGetSet() 
	{
		Store store = new MemoryStore("temporary", 36);
		store.open(StoreAccess.ReadWrite);
		
		DataArray<FloatVar> arr1 = DataArray.create(FloatVar.class, 9, false);
		arr1.setStore(store);

		assertNull( arr1.get(0) );
		assertNull( arr1.get(1) );
		assertNull( arr1.get(2) );
		
		arr1.read();

		assertEquals( 0.0f, arr1.get(0).get(), 0.00001 );
		assertEquals( 0.0f, arr1.get(1).get(), 0.00001 );
		assertEquals( 0.0f, arr1.get(2).get(), 0.00001 );
		assertEquals( 0.0f, arr1.get(3).get(), 0.00001 );
		
		arr1.get(0).set(6.7f);
		assertEquals( 6.7f, arr1.get(0).get(), 0.00001 );
		arr1.write();
		
		DataArray<FloatVar> arr2 = DataArray.create(FloatVar.class, 9, false);
		arr2.setStore(store);
		arr2.read();

		assertEquals( 6.7f, arr2.get(0).get(), 0.00001 );
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCopy() 
	{
		DataArray<FloatVar> arr1 = DataArray.create(FloatVar.class, 9, false);
		arr1.set(0, new FloatVar(2.1f));
		arr1.set(1, new FloatVar(-5.3f));
		
		DataArray<FloatVar> arr2 = (DataArray<FloatVar>)arr1.copy();
		
		assertEquals( arr1.getLength(), arr2.getLength() );
		assertEquals( arr1.getSize(), arr2.getSize() );

		assertEquals( 2.1f, arr2.get(0).get(), 0.00001 );
		assertEquals( -5.3f, arr2.get(1).get(), 0.00001 );
	}
	
}
