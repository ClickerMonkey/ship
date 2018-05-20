package model.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.Mesh;
import model.util.MeshLoader;
import model.util.World;

public class ModelTest extends GameScreen implements MouseMotionListener {

	public static void main(String[] args) {
		showWindow(new ModelTest(), "Model Test");
	}

	private final Object worldLock = new Object();
	private World world;
	private double yaw = -0.296705973;
	private double pitch = -1.8675023;
	private double scale = 20.0;

	public ModelTest() {
		super(512, 512, false);

		setBackground(Color.black);
		addMouseMotionListener(this);

		world = new World();


		try {
			MeshLoader loader = new MeshLoader();
//			Mesh mesh = loader.loadFromFile("Ferrari.x");
//			Mesh mesh = loader.loadFromFile("Axe.x");
//			Mesh mesh = loader.loadFromFile("Dragon.x");
//			Mesh mesh = loader.loadFromFile("Spaceship.x");
			Mesh mesh = loader.loadFromFile("Dinosaur.x");
//			Mesh mesh = loader.loadFromFile("Ship.x");
//			System.out.println("Mesh loaded");
//			mesh.pushWireframe(world);
			mesh.pushModel(world);
		} catch (Exception e) {
			e.printStackTrace();
		}

//				Mesh cube = new Mesh(Color.white);
//				// Corners
//				cube.addPoint( 1, 1, 1);
//				cube.addPoint( 1, 1,-1);
//				cube.addPoint(-1, 1,-1);
//				cube.addPoint(-1, 1, 1);
//				cube.addPoint( 1,-1, 1);
//				cube.addPoint( 1,-1,-1);
//				cube.addPoint(-1,-1,-1);
//				cube.addPoint(-1,-1, 1);
//				// Origin,Axis
////				cube.addPoint( 0, 0, 0);
////				cube.addPoint( 2, 0, 0);
////				cube.addPoint( 0, 2, 0);
////				cube.addPoint( 0, 0, 2);
//				
//				// Top
//				cube.addLine(0, 1, Color.white, 2.5f);
//				cube.addLine(1, 2, Color.white, 2.5f);
//				cube.addLine(2, 3, Color.white, 2.5f);
//				cube.addLine(3, 0, Color.white, 2.5f);
//				// Bottom
//				cube.addLine(4, 5, Color.green, 2.5f);
//				cube.addLine(5, 6, Color.green, 2.5f);
//				cube.addLine(6, 7, Color.green, 2.5f);
//				cube.addLine(7, 4, Color.green, 2.5f);
//				// Sides
//				cube.addLine(0, 4, Color.blue, 2.5f);
//				cube.addLine(1, 5, Color.blue, 2.5f);
//				cube.addLine(2, 6, Color.blue, 2.5f);
//				cube.addLine(3, 7, Color.blue, 2.5f);
//				
//				// Sides
//				cube.addQuad(1, 0, 4, 5, Color.red);
//				cube.addQuad(2, 1, 5, 6, Color.red);
//				cube.addQuad(3, 2, 6, 7, Color.red);
//				cube.addQuad(0, 3, 7, 4, Color.red);
//				// Top
//				cube.addQuad(0, 1, 2, 3, Color.red);
//				// Bottom
//				cube.addQuad(7, 6, 5, 4, Color.red);
//				
//				//Axis
//				cube.addLine(8, 9, Color.gray, 4f);
//				cube.addLine(8, 10, Color.gray, 4f);
//				cube.addLine(8, 11, Color.gray, 4f);
//				cube.addFace(9, 8, 10, Color.pink);
		//		//Corner
		//		world.addPrimitive(new Sphere(corners[0], 10, Color.blue));
		//		
//				cube.pushModel(world);

		updateWorld();
	}

	@Override
	public void draw(Graphics2D gr) {
		gr.setColor(Color.white);
		gr.drawString(getFPS() + "", 5, 15);
		gr.translate(256, 256);
		synchronized (worldLock) {
//			world.setMode(Mode.Line);
//			world.setDrawBackfaces(false);
			world.draw(gr);	
		}
		gr.translate(-256, -256);
	}

	@Override
	public void update(float deltatime) {

	}

	private int prevX, prevY, currX, currY;
	public void mouseDragged(MouseEvent e) {
		prevX = currX;
		prevY = currY;
		currX = e.getX();
		currY = e.getY();

		int dx = currX - prevX;
		int dy = prevY - currY;
		int modifiers = e.getModifiersEx();
		if ((modifiers & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
			yaw += dx * 0.01;
			pitch += dy * 0.01;
		}
		if ((modifiers & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
			scale += dy * 0.5;
		}
		
		updateWorld();
	}

	public void mouseMoved(MouseEvent e) {
		prevX = currX = e.getX();
		prevY = currY = e.getY();
	}

	private void updateWorld() {
		synchronized (worldLock) {
			world.getWorld().setEulerAngles(yaw, pitch);
			world.getWorld().scale(scale, scale, scale/**3*/);
			world.updateWorld();	
		}
	}

}
