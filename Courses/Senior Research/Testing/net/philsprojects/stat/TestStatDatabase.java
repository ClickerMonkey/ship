package net.philsprojects.stat;

import static org.junit.Assert.*;

import java.util.Set;

import net.philsprojects.BaseTest;
import net.philsprojects.data.store.factory.MemoryStoreFactory;

import org.junit.Before;
import org.junit.Test;

public class TestStatDatabase extends BaseTest 
{

	private StatService service;
	private StatFormat format;
	private StatGroup group;
	
	@Before
	public void testBefore()
	{
		service = StatService.get();
		
		format = new StatFormat(2);
		format.set(0, 50, 40);		// every 50ms for 2 sec
		format.set(1, 1000, 60); 	// every 1 sec for 1 min
		format.compile();
		
		group = new StatGroup("test");
		group.setFactory(new MemoryStoreFactory());
		group.setEnableDefault(true);
		group.setFormatDefault(format);
	}
	
	@Test
	public void testAdd()
	{
		final int TOTAL = 1000;
		float[] data = random(TOTAL);
		
		StatDatabase db1 = group.take("db1");
		
		for (float x : data) {
			db1.add(x);
			sleep(1);
		}
		waitForEvents(db1);
		
		contains(data, db1.getArchive(0));
		contains(data, db1.getArchive(1));
		
		for (StatPoint sp : db1.getArchive(0)) {
			System.out.println(sp);
		}
		
		Set<StatDatabase> dbs = group.delete(StatTarget.This);
		for (StatDatabase db : dbs) {
			assertFalse( db.exists() );
		}
	}
	
	@Test
	public void testMultipleTargets()
	{
		float[] data = {-45.367f};
		
		StatDatabase db2 = group.take("db2");
		StatDatabase db3 = group.take("db3");
		
		StatEvent se = db2.getEvent(data[0]);
		se.addTarget(db3);
		se.process();
		
		waitForEvents(db2, db3);

		contains(data, db2.getArchive(0));
		contains(data, db2.getArchive(1));
		contains(data, db3.getArchive(0));
		contains(data, db3.getArchive(1));
		
		Set<StatDatabase> dbs = group.delete(StatTarget.All);
		for (StatDatabase db : dbs) {
			assertFalse( db.exists() );
		}
	}
	
	
	private void contains(float[] data, StatArchive archive) {
		long dataTotal = data.length;
		double dataSum = 0.0;
		float dataMin = Float.MAX_VALUE;
		float dataMax = -Float.MAX_VALUE;
		for (float x : data) {
			dataSum += x;
			dataMin = Math.min(dataMin, x);
			dataMax = Math.max(dataMax, x);
		}
		
		long pointTotal = 0;
		double pointSum = 0.0;
		float pointMin = Float.MAX_VALUE;
		float pointMax = -Float.MAX_VALUE;
		for (StatPoint sp : archive) {
			pointTotal += sp.getTotal();
			pointSum += sp.getSum();
			pointMin = Math.min(pointMin, sp.getMin());
			pointMax = Math.max(pointMax, sp.getMax());
		}
		
		assertEquals( pointTotal, dataTotal );
		assertEquals( pointSum, dataSum, 0.000001 );
		assertEquals( pointMin, dataMin, 0.000001 );
		assertEquals( pointMax, dataMax, 0.000001 );
	}
	
	private void waitForEvents(StatDatabase ... dbs) {
		sleep(100);
		do {
			sleep(100);
		} while (service.getEventQueue().size() > 0);
		
		for (StatDatabase db : dbs) {
			db.getStore().flush();
		}
		sleep(100);
	}
	
	private float[] random(int count) {
		float[] data = new float[count];
		while (--count >= 0) {
			data[count] = rnd.nextFloat();
		}
		return data;
	}
	
	
}
