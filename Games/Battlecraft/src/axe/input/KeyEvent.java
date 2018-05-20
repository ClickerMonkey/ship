package axe.input;

import org.lwjgl.input.Keyboard;

public class KeyEvent {

	public int key;
	public int keyChar;
	public boolean state;
	public long nanos;
	
	public KeyEvent() {
	}
	
	public void update() {
		key = Keyboard.getEventKey();
		keyChar = Keyboard.getEventCharacter();
		state = Keyboard.getEventKeyState();
		nanos = Keyboard.getEventNanoseconds();
	}
	
}
