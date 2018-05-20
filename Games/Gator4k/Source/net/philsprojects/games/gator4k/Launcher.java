package net.philsprojects.games.gator4k;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Launcher 
{

	public static void main(String[] args) 
	{
		final Applet applet = new G();
		
		Stub stub = new Stub(applet);
		applet.setStub(stub);
		applet.setPreferredSize(new Dimension(500, 400));
		
		Frame frame = new Frame("Gator4k");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				applet.stop();
				applet.destroy();
				System.exit(0);
			}
		});
		
		frame.add("Center", applet);
		frame.setVisible(true);
		
		applet.init();
		applet.start();
		frame.pack();
	}
	
}
