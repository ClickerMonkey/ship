package axe;

import axe.TimeUnit;

public interface ScreenListener {
	public void onStart();
	public void onUpdate(TimeUnit elapsed);
	public void onDraw();
	public void onClose();
}
