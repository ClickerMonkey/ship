package net.philsprojects.games.gator4k;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.Hashtable;

public class Stub implements AppletStub 
{

	private Hashtable<String, String> properties;
	private Applet applet;

	public Stub(Applet applet) {
		this.applet = applet;
		this.properties = new Hashtable<String, String>();
	}
	
	public void set(String name, Object value) {
		properties.put(name, value.toString());
	}
	
	@Override
	public void appletResize(int width, int height) {
		applet.resize(width, height);
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public URL getCodeBase() {
		return null;
	}

	@Override
	public URL getDocumentBase() {
		return null;
	}

	@Override
	public String getParameter(String name) {
		return properties.get(name);
	}

	@Override
	public boolean isActive() {
		return true;
	}

}
