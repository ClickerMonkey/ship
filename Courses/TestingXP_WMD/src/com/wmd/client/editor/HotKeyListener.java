package com.wmd.client.editor;

/**
 * A listener for hot key events (a key + holding the Ctrl button)
 * 
 * @author Philip Diffenderfer
 * 
 */
public interface HotKeyListener
{

	/**
	 * Invoked when the given key is pressed while Ctrl is being held down.
	 * 
	 * @param key
	 *            The key pressed.
	 */
	public void onHotKeyPressed(char key);

}
