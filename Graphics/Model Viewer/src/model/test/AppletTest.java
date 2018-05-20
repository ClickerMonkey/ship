package model.test;

import model.util.Canvas;
import model.util.ImageApplet;

public class AppletTest extends ImageApplet 
{

	public AppletTest() {
		setSize(480, 320);
	}
	
	@Override
	public void initApplet(Canvas canvas) {
		canvas.setBackground(0, 0, 0);
	}
	
	@Override
	public void drawApplet(Canvas canvas) {
		canvas.clear();
		
		canvas.setColor(255, 0, 0);
		canvas.fillRect(25, 25, 100, 100);
		
		canvas.setColor(255, 255, 255);
		canvas.drawLine(10, 80, 120, 10);
		
//		canvas.setColor(0, 255, 0);
//		canvas.fillPolygon(
//				new int[] {	10, 40, 120, 120, 80, 40},
//				new int[] { 10, 10, 70,  110, 80, 100}
//		);
		
		dirty();
	}

}
