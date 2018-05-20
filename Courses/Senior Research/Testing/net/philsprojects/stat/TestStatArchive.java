package net.philsprojects.stat;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.service.Service;

public class TestStatArchive extends BaseTest 
{

	@Test
	public void testOverwrite()
	{
		StatService service = StatService.get();
		service.waitFor(Service.Running);
		
		StatFormat format = new StatFormat(1);
		format.set(0, 10, 100);
		
		StatDatabase data = StatDatabase.inMemory("test", format);
		data.setEnable(true);
		
		StatArchive archive = data.getArchive(0);
		for (int i = 0; i < 2000; i++) {
			data.add(rnd.nextFloat());
			sleep(1);
		}
		// should be filled with data (approx. 10 stats a point)
		output(archive);
		
		sleep(500);
		data.add(rnd.nextFloat());
		sleep(200);
		// half cleared before the last point
		output(archive);
		
		sleep(2000);
		for (int i = 0; i < 100; i++) {
			data.add(rnd.nextFloat());
		}
		sleep(200);
		// all cleared, the last point(s) should be 100
		output(archive);
	}
	
	private void output(StatArchive archive) 
	{
		for (StatPoint sp : archive) {
			System.out.println(sp);
		}
		System.out.println();
	}
	
}
