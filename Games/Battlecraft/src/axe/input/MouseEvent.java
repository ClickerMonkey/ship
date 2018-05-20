package axe.input;

import org.lwjgl.input.Mouse;

public class MouseEvent {

	public int dx;
	public int dy;
	public int x;
	public int y;
	public int wheel;
	public long nanos;
	
	public int button;
	public boolean buttonState;
	
	public boolean inWindow;
	public int buttonsDown = 0;
	
	public MouseEvent() {
		int x = Mouse.getButtonCount();
		while (--x >= 0) {
			buttonsDown += (Mouse.isButtonDown(x) ? 1 : 0);
		}
		
		inWindow = Mouse.isInsideWindow();
	}
	
	public void update() {
		x = Mouse.getEventX();
		y = Mouse.getEventY();
		dx = Mouse.getEventDX();
		dy = Mouse.getEventDY();
		wheel = Mouse.getEventDWheel();
		nanos = Mouse.getEventNanoseconds();
		button = Mouse.getEventButton();
		buttonState = Mouse.getEventButtonState();
		
		if (button != -1) {
			buttonsDown += (buttonState ? 1 : -1);
		}
	}
	
	public boolean hasButtonDown() {
		return (buttonsDown > 0);
	}
	
}
