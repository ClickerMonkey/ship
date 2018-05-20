package net.philsprojects.data.store;

import org.junit.Test;

import net.philsprojects.data.StoreAccess;
import net.philsprojects.data.TestStore;
import net.philsprojects.data.error.StoreAccessException;

public class TestMappedStore extends TestStore 
{

	@Test
	public void testDefaults()
	{
		MappedStore ms = new MappedStore("testDefaults.dat"); 
		testDefaults(ms);
		ms.close();
	}

	@Test
	public void testOpen()
	{
		MappedStore ms = new MappedStore("testOpen.dat");
		testOpen(ms);
		ms.delete();
	}

	@Test
	public void testCapacity()
	{
		MappedStore ms = new MappedStore("testCreate.dat", StoreAccess.ReadWrite, 20);
		testCapacity(ms);
		ms.delete();
	}

	@Test
	public void testCreate()
	{
		MappedStore ms = new MappedStore("testCreate.dat");
		testCreate(ms);
		ms.delete();
	}

	@Test
	public void testDelete()
	{
		MappedStore ms = new MappedStore("testDelete.dat", StoreAccess.ReadWrite, 20);
		testDelete(ms);
	}

	@Test
	public void testByteArray()
	{
		MappedStore ms = new MappedStore("testByteArray.dat", StoreAccess.ReadWrite, 20);
		testByteArray(ms);
		ms.delete();
	}

	@Test(expected = StoreAccessException.class)
	public void testByteArrayAccess()
	{
		MappedStore ms = new MappedStore("testByteArrayAccess.dat", StoreAccess.ReadWrite, 20);
		try {
			ms.open(StoreAccess.ReadOnly);
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
		MappedStore ms = new MappedStore("testByteSection.dat", StoreAccess.ReadWrite, 20);
		testByteSection(ms);
		ms.delete();
	}

	@Test(expected = StoreAccessException.class)
	public void testByteSectionAccess()
	{
		MappedStore ms = new MappedStore("testByteSectionAccess.dat", StoreAccess.ReadWrite, 20);
		try {
			ms.open(StoreAccess.ReadOnly);
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
		MappedStore ms = new MappedStore("testByteBuffer.dat", StoreAccess.ReadWrite, 20);
		testByteBuffer(ms);
		ms.delete();
	}

	@Test(expected = StoreAccessException.class)
	public void testByteSectionBuffer()
	{
		MappedStore ms = new MappedStore("testByteBufferAccess.dat", StoreAccess.ReadWrite, 20);
		try {
			ms.open(StoreAccess.ReadOnly);
			testByteBuffer(ms);	
		}
		finally {
			ms.open(StoreAccess.ReadWrite);
			ms.delete();
		}
	}

	@Test
	public void testOpenAccess()
	{
		MappedStore ms1 = new MappedStore("testOpenAccess.dat");
		MappedStore ms2 = new MappedStore("testOpenAccess.dat");
		try {
			testOpenAccess(ms1, ms2);
		}
		finally {
			ms2.close();
			ms1.open(StoreAccess.ReadWrite);
			ms1.delete();
		}
	}

	@Test
	public void testSetAccessClosed()
	{
		MappedStore ms1 = new MappedStore("testSetAccessClosed.dat");
		MappedStore ms2 = new MappedStore("testSetAccessClosed.dat");
		try {
			testSetAccessClosed(ms1, ms2);	
		}
		finally {
			ms2.close();
			ms1.open(StoreAccess.ReadWrite);
			ms1.delete();	
		}	
	}

	@Test
	public void testSetAccess()
	{
		MappedStore ms1 = new MappedStore("testSetAccess.dat");
		MappedStore ms2 = new MappedStore("testSetAccess.dat");
		try {
			testSetAccess(ms1, ms2);	
		}
		finally {
			ms2.close();
			ms1.open(StoreAccess.ReadWrite);
			ms1.delete();
		}
	}

}
