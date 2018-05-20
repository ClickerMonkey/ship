package net.philsprojects.data;

import static org.junit.Assert.*;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.data.store.MemoryStore;

public class TestStores extends BaseTest 
{

	@Test
	public void testGet()
	{
		assertNull( Stores.get("temp0") );
		
		Stores.put(new MemoryStore("temp0", 32));
		
		assertNotNull( Stores.get("temp0") );
	}
	
	@Test
	public void testAlias() 
	{
		MemoryStore store = new MemoryStore("temp1", 32);
		
		assertNull( Stores.get("temp1") );
		assertNull( Stores.put(store, "alias1") );
		assertNull( Stores.get("temp1") );
		assertSame( store, Stores.get("alias1") );
	}
	
	@Test
	public void testRemove() 
	{
		MemoryStore store = new MemoryStore("temp2", 32);
		
		assertNull( Stores.remove("temp2") );
		assertNull( Stores.put(store) );
		assertSame( store, Stores.remove("temp2") );
	}
	
	@Test
	public void testRemoveAlias() 
	{
		MemoryStore store = new MemoryStore("temp3", 32);

		assertNull( Stores.remove("temp3") );
		assertNull( Stores.remove("alias3") );
		assertNull( Stores.put(store, "alias3") );
		assertNull( Stores.remove("temp3") );
		assertSame( store, Stores.remove("alias3") );
	}
	
}
