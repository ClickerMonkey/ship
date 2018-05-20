package net.philsprojects.data.store;

import org.junit.Test;

import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.TestStore;
import net.philsprojects.data.error.StoreAccessException;

public class TestMemoryStore extends TestStore 
{

	@Test
	public void testDefaults()
	{
		MemoryStore ms = new MemoryStore("testDefaults.dat"); 
		testDefaults(ms);
		ms.close();
	}
	
	@Test
	public void testOpen()
	{
		MemoryStore ms = new MemoryStore("testOpen.dat");
		testOpen(ms);
		ms.delete();
	}
	
	@Test
	public void testCapacity()
	{
		MemoryStore ms = new MemoryStore("testCreate.dat", StoreAccess.ReadWrite, 20);
		testCapacity(ms);
		ms.delete();
	}
	
	@Test
	public void testCreate()
	{
		MemoryStore ms = new MemoryStore("testCreate.dat");
		testCreate(ms);
		ms.delete();
	}
	
	@Test
	public void testByteArray()
	{
		MemoryStore ms = new MemoryStore("testByteArray.dat", StoreAccess.ReadWrite, 20);
		testByteArray(ms);
		ms.delete();
	}
	
	@Test(expected = StoreAccessException.class)
	public void testByteArrayAccess()
	{
		MemoryStore ms = new MemoryStore("testByteArrayAccess.dat", StoreAccess.ReadOnly, 20);
		try {
			testByteArray(ms);	
		}
		finally {
			ms.open(StoreAccess.ReadWrite);
			ms.delete();
		}
	}
	
	@Test
	public void testByteSection()
	{
		MemoryStore ms = new MemoryStore("testByteSection.dat", StoreAccess.ReadWrite, 20);
		testByteSection(ms);
		ms.delete();
	}
	
	@Test(expected = StoreAccessException.class)
	public void testByteSectionAccess()
	{
		MemoryStore ms = new MemoryStore("testByteSectionAccess.dat", StoreAccess.ReadOnly, 20);
		try {
			testByteSection(ms);	
		}
		finally {
			ms.open(StoreAccess.ReadWrite);
			ms.delete();
		}
	}
	
	@Test
	public void testByteBuffer()
	{
		MemoryStore ms = new MemoryStore("testByteBuffer.dat", StoreAccess.ReadWrite, 20);
		testByteBuffer(ms);
		ms.delete();
	}
	
	@Test(expected = StoreAccessException.class)
	public void testByteBufferAccess()
	{
		MemoryStore ms = new MemoryStore("testByteBufferAccess.dat", StoreAccess.ReadOnly, 20);
		try {
			testByteBuffer(ms);	
		}
		finally {
			ms.open(StoreAccess.ReadWrite);
			ms.delete();
		}
	}
	
}
