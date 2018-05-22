package net.philsprojects.game.util;

import net.philsprojects.game.util.Timer;

import org.junit.Test;


public class TestTimer
{

	@Test
	public void testTimer()
	{
		Timer t = new Timer(4);
		t.start();
		t.update();
		System.out.println(new java.text.DecimalFormat("0.000000000000").format(t.getDeltatime()));
		t.update();
		System.out.println(new java.text.DecimalFormat("0.000000000000").format(t.getDeltatime()));
		t.update();
		System.out.println(new java.text.DecimalFormat("0.000000000000").format(t.getDeltatime()));
		t.update();
		System.out.println(new java.text.DecimalFormat("0.000000000000").format(t.getDeltatime()));
		t.update();
		System.out.println(new java.text.DecimalFormat("0.000000000000").format(t.getDeltatime()));
	}

}