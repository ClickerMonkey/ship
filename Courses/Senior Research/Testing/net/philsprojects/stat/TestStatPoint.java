package net.philsprojects.stat;

import static org.junit.Assert.*;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.data.Store;
import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.store.MemoryStore;

public class TestStatPoint extends BaseTest 
{

	@Test
	public void testReadAndWrite()
	{
		Store store = new MemoryStore("Testing", 300);
		store.open(StoreAccess.ReadWrite);
		
		StatPoint sp1 = new StatPoint();
		sp1.add(4f);
		sp1.add(5f);
		sp1.add(3f);
		sp1.write(store);
		
		sp1.add(1f);
		sp1.write(24, store);
		
		assertEquals( 4, sp1.getTotal() );
		assertEquals( 3.25, sp1.getAverage(), 0.0000001 );
		assertEquals( 5.0, sp1.getMax(), 0.0000001 );
		assertEquals( 1.0, sp1.getMin(), 0.0000001 );
		
		StatPoint sp2 = new StatPoint();
		sp2.read(store);
		
		assertEquals( 3, sp2.getTotal() );
		assertEquals( 4.0, sp2.getAverage(), 0.0000001 );
		assertEquals( 5.0, sp2.getMax(), 0.0000001 );
		assertEquals( 3.0, sp2.getMin(), 0.0000001 );
		
		sp2.read(24, store);

		assertEquals( 4, sp2.getTotal() );
		assertEquals( 3.25, sp2.getAverage(), 0.0000001 );
		assertEquals( 5.0, sp2.getMax(), 0.0000001 );
		assertEquals( 1.0, sp2.getMin(), 0.0000001 );
	}
	
}
