package net.philsprojects.games.gator4k;


import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.Random;

public class G extends Applet implements Runnable
{

	//==========================================================================
	// A P P L E T   V A R I A B L E S
	//==========================================================================
	static final int WIDTH = 500;
	static final int HEIGHT = 400;

	//==========================================================================
	// G A M E   V A R I A B L E S
	//==========================================================================
	// CONSTANTS
	static final int LEVEL_BUOY = 20;
	static final int LEVEL_GATOR = 15;
	static final int LEVEL_BUOY_INC = 4;
	static final int LEVEL_GATOR_INC = 5;
	static final float LEVEL_TIME_ADD = 5f;
	static final float LEVEL_TIME_OFFSET = 30f;
	static final int MONEY_GATOR = 10;
	static final int MONEY_SPEAR = -5;
	static final float FRAME_UPDATE_TIME = 0.3f;
	static final float MESSAGE_DURATION = 4f;
	// VARIABLES
	final BufferedImage buffer;
	final Image water_buffer;
	final MemoryImageSource water_source;
	final Random rnd = new Random();
	boolean running;
	long lastUpdate;
	float deltatime;
	
//	float frame_time;
//	float frame_rate;
//	int frame_count;
	
	int game_level;
	int game_lives;
	int game_money;
	float game_remaining;
	boolean game_reset;
	boolean game_next;
	boolean game_paused;

	//==========================================================================
	// B O A T
	//==========================================================================
	// CONSTANTS
	static final float BOAT_RIPPLE_SKIP = 3f;
	static final int BOAT_RIPPLE = 3;
	static final int BOAT_RADIUS = 10;
	static final int BOAT_QUART = 5;
	static final int BOAT_EIGTHT = 2;
	static final int BOAT_SIXTEEN = 1;
	static final float BOAT_WEIGHT = 10f;
	// VARIABLES
//	final AudioClip audioGator;
	final BufferedImage boat_image;
	float boat_fan;
	float boat_x = WIDTH / 2;
	float boat_y = HEIGHT / 2;
	float boat_velx, boat_vely;

	//==========================================================================
	// R I P P L E S
	//==========================================================================
	// VARIABLES
	int old_index = WIDTH;
	int new_index = WIDTH * (HEIGHT + 3);
	final short[] ripple_map = new short[WIDTH * (HEIGHT + 2) * 2];
	final int[] ripple = new int[WIDTH * HEIGHT];

	//==========================================================================
	// B U O Y
	//==========================================================================
	// CONSTANTS
	static final int BUOY_MAX = 128;
	static final int BUOY_RADIUS = 12;
	static final int BUOY_STRENGTH = 4;
	static final int BUOY_RIPPLE = 3;
	static final Color[] BUOY_COLOR = {
		Color.red, Color.black, Color.darkGray, Color.gray, Color.lightGray
	};
	// VARIABLES
	int buoy_count;
	final int[] buoy_x = new int[BUOY_MAX];
	final int[] buoy_y = new int[BUOY_MAX];
	final int[] buoy_health = new int[BUOY_MAX];

	//==========================================================================
	// G A T O R S
	//==========================================================================
	// CONSTANTS
	static final int GATOR_MAX = 128;
	static final int GATOR_RADIUS = 8;
	static final int GATOR_RIPPLE = 4;
	static final float GATOR_INTERVAL = 4f;
	static final float GATOR_BLINK = 3f;
	// VARIABLES
	int gator_count;
	final float[] gator_time = new float[GATOR_MAX];
	final int[] gator_x = new int[GATOR_MAX];
	final int[] gator_y = new int[GATOR_MAX];
	final BufferedImage gator_image_open;
	final BufferedImage gator_image_closed;

	//==========================================================================
	// G A T O R   B L O O D
	//==========================================================================
	// CONSTANTS
	public static final float BLOOD_LIFE_MIN = 1.2f;
	public static final float BLOOD_LIFE_MAX = 2.0f;
	public static final float BLOOD_SIZE_MIN = 4.0f;
	public static final float BLOOD_SIZE_MAX = 6.0f;
	public static final float BLOOD_SIZE_VEL_MIN = 10.0f;
	public static final float BLOOD_SIZE_VEL_MAX = 20.0f;
	public static final int BLOOD_BURST_MIN = 3;
	public static final int BLOOD_BURST_MAX = 8;
	public static final int BLOOD_MAX = 256;
	// VARIABLES
	int blood_count;
	final float[] blood_time = new float[BLOOD_MAX];
	final float[] blood_life = new float[BLOOD_MAX];
	final float[] blood_size = new float[BLOOD_MAX];
	final float[] blood_size_vel = new float[BLOOD_MAX];
	final int[] blood_x = new int[BLOOD_MAX];
	final int[] blood_y = new int[BLOOD_MAX];

	//==========================================================================
	// S P E A R S
	//==========================================================================
	// CONSTANTS
	static final int SPEAR_MAX = 128;
	static final float SPEAR_LIFE = 1.0f;
	static final float SPEAR_LENGTH = 15f;
	static final float SPEAR_VEL = 300f;
	static final float SPEAR_RELOAD = 0.4f;
	// VARIABLES
	int spear_count;
	final float[] spear_life = new float[SPEAR_MAX];
	final float[] spear_x = new float[SPEAR_MAX];
	final float[] spear_y = new float[SPEAR_MAX];
	final float[][] spear_vel = new float[SPEAR_MAX][2];
	float spear_time;

	//==========================================================================
	// S C O R E S
	//==========================================================================
	// CONSTANTS
	static final int SCORE_MAX = 10;
	// VARIABLES
	int score_count;
	final int[] scores = new int[SCORE_MAX];
	final int[] score_level = new int[SCORE_MAX];
	
	//==========================================================================
	// S P R I N G
	//==========================================================================
	// CONSTANTS
	static final float SPRING_LENGTH = 4f;
	static final float SPRINT_CONSTANT = 80f;
	static final float SPRING_FRICTION = 40f;
	
	//==========================================================================
	// M O U S E   E V E N T S
	//==========================================================================
	int mouse_x;
	int mouse_y;

	//==========================================================================
	// C A C H E
	//==========================================================================
	// CONSTANTS
	static final BasicStroke STROKE_SINGLE = new BasicStroke(1);
	static final BasicStroke STROKE_SPEAR = new BasicStroke(2);
	static final BasicStroke STROKE_TEETH = new BasicStroke(2, 
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, 
			new float[] {1, 1}, 1);
	
	static final Font FONT_GAME = new Font("Courier", Font.BOLD, 12); 
	
	// VARIABLES
	final float[] dir = new float[2];
	int i, j, k;
	
	// Instanties a new game setting up the
	public G() {
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		
		water_source = new MemoryImageSource(WIDTH, HEIGHT, ripple, 0, WIDTH);
		water_source.setAnimated(true);
		water_buffer = createImage(water_source);
		
//		audioGator = newAudioClip(getClass().getResource("c"));
		
		enableEvents(0x38); // 0x38
		Graphics2D gr;

		//==================================================================
		// Draw the boat on a buffered image
		//==================================================================
		boat_image = new BufferedImage(BOAT_RADIUS * 2, BOAT_RADIUS * 2, BufferedImage.TYPE_4BYTE_ABGR);
		gr = boat_image.createGraphics();
		// Boat outer
		gr.setColor(new Color(178, 77, 255));
		gr.fillRect(5, 5, 15, 10);
		// Boat inner
		gr.setColor(new Color(92, 0, 163));
		gr.fillRect(5, 7, 12, 5);
		// Boat fan casing
		gr.setColor(new Color(100, 100, 100, 200));
		gr.fillRect(0, 3, 5, 15);
		gr.dispose();
		
		//==================================================================
		// Draw the gator (eyes closed) on a buffered image
		//==================================================================
		gator_image_closed = new BufferedImage(GATOR_RADIUS * 3, GATOR_RADIUS * 2, BufferedImage.TYPE_4BYTE_ABGR);
		gr = gator_image_closed.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// TEETH
		gr.setColor(Color.white);
		gr.setStroke(STROKE_TEETH);
		gr.drawRoundRect(GATOR_RADIUS, GATOR_RADIUS / 2, GATOR_RADIUS * 2, GATOR_RADIUS, 4, 4);
		gr.setStroke(STROKE_SINGLE);
		// HEAD
		gr.setColor(new Color(0, 200, 0));
		gr.fillOval(0, 0, GATOR_RADIUS * 2, GATOR_RADIUS * 2);
		gr.fillRoundRect(GATOR_RADIUS, GATOR_RADIUS / 2, GATOR_RADIUS * 2, GATOR_RADIUS, 5, 5);
		// TAIL RIDGE
		gr.setColor(new Color(0, 100, 0));
		gr.drawLine(0, GATOR_RADIUS, GATOR_RADIUS, GATOR_RADIUS);
		gr.dispose();
		
		//==================================================================
		// Draw the gator (eyes open) on a buffered image
		//==================================================================
		gator_image_open = new BufferedImage(GATOR_RADIUS * 3, GATOR_RADIUS * 2, BufferedImage.TYPE_4BYTE_ABGR);
		gr = gator_image_open.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.drawImage(gator_image_closed, 0, 0, null);
		// EYES
		gr.setColor(Color.white);
		gr.fillOval(GATOR_RADIUS + GATOR_RADIUS / 2 - 2, GATOR_RADIUS / 2 - 2, 4, 4);
		gr.fillOval(GATOR_RADIUS + GATOR_RADIUS / 2 - 2, GATOR_RADIUS + GATOR_RADIUS / 2 - 2, 4, 4);
		// PUPILS
		gr.setColor(Color.black);
		gr.fillOval(GATOR_RADIUS + GATOR_RADIUS / 2, GATOR_RADIUS / 2 - 1, 2, 2);
		gr.fillOval(GATOR_RADIUS + GATOR_RADIUS / 2, GATOR_RADIUS + GATOR_RADIUS / 2 - 1, 2, 2);
		gr.dispose();
	}

	// Initializes the applet requesting its size, setting up the initial
	// background ripple map, and starts the thread which runs the applet.
	public void init() {
		setSize(WIDTH, HEIGHT);
		updateRippleEffect();
		running = true;
		game_paused = true;
		game_reset = true;
		new Thread(this).start();
	}

	// The game loop executed by a thread.
	public void run() {
		
		lastUpdate = System.nanoTime();
		while (running) {

			//==================================================================
			// Update deltatime - time in seconds since the last game loop
			//==================================================================
			long time = System.nanoTime();
			deltatime = (time - lastUpdate) * 0.000000001f;
			lastUpdate = time;

			//==================================================================
			// If the game is paused then freeze all updating
			//==================================================================
			if (!game_paused) {
				
				//==================================================================
				// Update the time since the last spear throw
				//==================================================================
				spear_time += deltatime;
	
				//==================================================================
				// U P D A T E   B O A T
				//==================================================================
				// Update the fan rotation
				boat_fan += deltatime * 5;
				if (boat_fan > 1) {
					boat_fan = 0;
				}
	
				//==================================================================
				// Update the position of the boat with respect to the mouse 
				// position. Apply spring physics between the boat and mouse. 
				dir[0] = mouse_x - boat_x;
				dir[1] = mouse_y - boat_y; 
				float distance = normalize(dir);
				if (distance > 1f) {
					// Force of the spring between the masses
					float force = Math.abs(SPRING_LENGTH - distance) * -SPRINT_CONSTANT;
					float scale = deltatime / BOAT_WEIGHT;
					// Adjust the boats velocity with the springs force
					boat_velx -= ((dir[0] * force) + boat_velx * SPRING_FRICTION) * scale;
					boat_vely -= ((dir[1] * force) + boat_vely * SPRING_FRICTION) * scale;
					// Updates the boats position
					boat_x += boat_velx * deltatime;
					boat_y += boat_vely * deltatime;				
				}
				
				//==================================================================
				// Update the ripples behind the boat. 
				addRipple((int)boat_x, (int)boat_y, BOAT_RIPPLE);
	
				//==================================================================
				// Handle the boat hitting the gators
				//==================================================================
				final int BOAT_GATOR = BOAT_RADIUS + GATOR_RADIUS;
				for (i = 0; i < gator_count; i++) {
					// If the boat and gator intersect then kill the gator
					if (intersects(boat_x, boat_y, gator_x[i], gator_y[i], BOAT_GATOR)) {
						killGator(i);
					}
				}
				
				//==================================================================
				// Handle the spears hitting the gators
				//==================================================================
				for (i = 0; i < spear_count; i++) {
					for (j = 0; j < gator_count; j++) {
						// If the spear and gator intersect remove them both
						if (intersects(spear_x[i], spear_y[i], gator_x[j], gator_y[j], GATOR_RADIUS)) {
							killGator(j);
							killSpear(i);
						}
					}
				}
				
				//==================================================================
				// Handle the boat bouncing off of the buoys
				//==================================================================
				final int BOAT_BUOY = BOAT_RADIUS + BUOY_RADIUS;
				final int BOAT_BUOY_SQ = BOAT_BUOY * BOAT_BUOY;
				for (i = 0; i < buoy_count; i++) 
				{
					dir[0] = boat_x - buoy_x[i];
					dir[1] = boat_y - buoy_y[i];
					// Has the boat intersected the buoy?
					if (dir[0] * dir[0] + dir[1] * dir[1] < BOAT_BUOY_SQ) {
						// Normalize the vector and get the distance between.
						float dist = normalize(dir);
						// How far was the boat inserted into the buoy
						float insert = (BOAT_BUOY + 2) - dist;
						// How fast was the boat going
						float speed = (float)Math.sqrt(boat_velx * boat_velx + boat_vely * boat_vely);
						// Move the boat outside of the buoy (+2 pixels)
						boat_x -= (boat_velx / speed) * insert;
						boat_y -= (boat_vely / speed) * insert;
						
						// Reflect the velocity of the boat to the normal of the buoy
						float dot = 2 * (-dir[1] * boat_velx + dir[0] * boat_vely);
						boat_velx = (dot * -dir[1]) - boat_velx;
						boat_vely = (dot * dir[0]) - boat_vely;
						// Decrease the buoy's health
						buoy_health[i]--;

						// Add a ripple under the buoy
						addRipple(buoy_x[i], buoy_y[i], BUOY_RIPPLE);
	
						// If the buoy has been completely destroyed...
						if (buoy_health[i] == 0) {
							// Remove the buoy from the list.
							buoy_count--;
							buoy_x[i] = buoy_x[buoy_count];
							buoy_y[i] = buoy_y[buoy_count];
							buoy_health[i] = buoy_health[buoy_count];
							
							// Decrease a game life
							game_lives--;
							// If there are no more lives the restart the game.
							if (game_lives <= 0) {
								saveScore();
								game_reset = true;
								game_paused = true;
							}
						}
					}
				}			
	
				//==================================================================
				// Update the Blood Effect
				//==================================================================
				for (i = 0; i < blood_count; i++) {
					blood_time[i] += deltatime;
					blood_size[i] += blood_size_vel[i] * deltatime;
					if (blood_time[i] > blood_life[i]) {
						blood_count--;
						blood_x[i] = blood_x[blood_count];
						blood_y[i] = blood_y[blood_count];
						blood_time[i] = blood_time[blood_count];
						blood_life[i] = blood_life[blood_count];
						blood_size[i] = blood_size[blood_count];
						blood_size_vel[i] = blood_size_vel[blood_count];
					}
				}
	
				//==================================================================
				// Update the Spears
				//==================================================================
				for (i = 0; i < spear_count; i++) {
					spear_x[i] += spear_vel[i][0] * SPEAR_VEL * deltatime;
					spear_y[i] += spear_vel[i][1] * SPEAR_VEL * deltatime;
					spear_life[i] -= deltatime;
					if (spear_life[i] < 0) {
						killSpear(i);
					}
				}

				//==================================================================
				// Update the Gators
				//==================================================================
				for (i = 0; i < gator_count; i++) {
					gator_time[i] += deltatime;
					if (gator_time[i] > GATOR_INTERVAL) {
						gator_time[i] = 0;
					}
				}
				
				//==============================================================
				// Randomly add ripples
				//==============================================================
				i = rnd.nextInt(200);
				if (i == 0) {
					i = rnd.nextInt(buoy_count);
					addRipple(buoy_x[i], buoy_y[i], BUOY_RIPPLE);
				}
				else if (i == 1) {
					i = rnd.nextInt(gator_count);
					addRipple(gator_x[i], gator_y[i], GATOR_RIPPLE);
				}
				
				//==================================================================
				// Update the Ripple effect if the game isn't paused.
				//==================================================================
				updateRippleEffect();	
	
				//==============================================================
				// Update to the next level (or reset)
				//==============================================================
				game_remaining -= deltatime;
				if (game_remaining < 0) {
					if (game_level > 0) {
						saveScore();
					}
					game_reset = true;
					game_paused = true;
				}
			}
			
			//==============================================================
			// Update Frames-Per-Second
			//==============================================================
//			frame_time += deltatime;
//			frame_count++;
//			if (frame_time > FRAME_UPDATE_TIME) {
//				frame_rate = frame_count / frame_time;
//				frame_count = 0;
//				frame_time -= FRAME_UPDATE_TIME;
//			}
			
			if (game_reset) {
				game_lives = 0;
				game_level = 0;
				game_money = -(int)game_remaining;
				blood_count = 0;
				spear_count = 0;
				boat_x = WIDTH / 2;
				boat_y = HEIGHT / 2;
				boat_velx = 0;
				boat_vely = 0;
				mouse_x = WIDTH / 2 + 1;
				mouse_y = HEIGHT / 2 + 1;
				game_next = true;
				game_reset = false;
				game_paused = true;
			}
			if (game_next) {
				//=============================================================
				// PLACE BUOYS
				//=============================================================
				int x = 0, y = 0, total = LEVEL_BUOY + (game_level * LEVEL_BUOY_INC);
				buoy_count = 0;
				while (--total >= 0) {
					for (i = 0; i < 100; i++) {
						x = rnd.nextInt(WIDTH - (BUOY_RADIUS * 2)) + BUOY_RADIUS;
						y = rnd.nextInt(HEIGHT - (BUOY_RADIUS * 2)) + BUOY_RADIUS;
						if (canPlace(x, y, BUOY_RADIUS)) {
							break;
						}
					}
					buoy_x[buoy_count] = x;
					buoy_y[buoy_count] = y;
					buoy_health[buoy_count] = BUOY_STRENGTH;
					buoy_count++;
				}

				//=============================================================
				// PLACE GATORS
				//=============================================================
				total = LEVEL_GATOR + (game_level * LEVEL_GATOR_INC);
				gator_count = 0;
				while (--total >= 0) {
					for (i = 0; i < 100; i++) {
						x = rnd.nextInt(WIDTH - (GATOR_RADIUS * 2)) + GATOR_RADIUS;
						y = rnd.nextInt(HEIGHT - (GATOR_RADIUS * 2)) + GATOR_RADIUS;
						if (canPlace(x, y, GATOR_RADIUS)) {
							break;
						}
					}
					gator_x[gator_count] = x;
					gator_y[gator_count] = y;
					gator_time[gator_count] = rnd.nextFloat() * GATOR_INTERVAL;
					gator_count++;
				}
				
				game_level++;
				game_lives++;
				game_money += (int)game_remaining;
				game_remaining = game_level * LEVEL_TIME_ADD + LEVEL_TIME_OFFSET;
				game_next = false;
			}
			
			//==================================================================
			// D R A W I N G
			//==================================================================
			Graphics2D gr = (Graphics2D)buffer.getGraphics();
			// Draw the water effect
			gr.drawImage(water_buffer, 0, 0, null);
			gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//==================================================================
			// Draw the Blood Effect
			//==================================================================
			for (i = 0; i < blood_count; i++) {
				final float t = blood_time[i] / blood_life[i];
				final int alpha = (int)((1 - t) * 255);
				final int size = (int)blood_size[i];
				gr.setColor(new Color(255, 0, 0, alpha));
				gr.fillOval(blood_x[i] - size, blood_y[i] - size, size * 2, size * 2);
			}

			//==================================================================
			// Draw the Buoys
			//==================================================================
			final int FRACTION = BUOY_RADIUS * 4 / 5;
			
			for (i = 0; i < buoy_count; i++) {
//				int pixel_index = buoy_x[i] + (buoy_y[i] * WIDTH);
//				int pixel = (ripple[pixel_index] >> 8) & 0xFF;
//				int left = ((ripple[pixel_index - 1] >> 8) & 0xFF) - pixel;
//				int right = ((ripple[pixel_index + 1] >> 8) & 0xFF) - pixel;
//				int top = ((ripple[pixel_index - WIDTH] >> 8) & 0xFF) - pixel;
//				int bottom = ((ripple[pixel_index + WIDTH] >> 8) & 0xFF) - pixel;
//				int dx = (right - left) >> 4;
//				int dy = (top - bottom) >> 4;
				
				gr.setColor(BUOY_COLOR[buoy_health[i]]);
				gr.translate(buoy_x[i], buoy_y[i]);
				gr.fillOval(-BUOY_RADIUS, -BUOY_RADIUS, 
						BUOY_RADIUS * 2, BUOY_RADIUS * 2);
				gr.setColor(BUOY_COLOR[buoy_health[i] - 1]);
//				gr.translate(dx, dy);
				gr.fillOval(-BUOY_RADIUS / 2, -BUOY_RADIUS / 2, 
						BUOY_RADIUS, BUOY_RADIUS);
//				gr.translate(-dx, -dy);
				gr.drawLine(-FRACTION, -FRACTION, FRACTION, FRACTION);
				gr.drawLine(FRACTION, -FRACTION, -FRACTION, FRACTION);
				gr.translate(-buoy_x[i], -buoy_y[i]);
			}

			//==================================================================
			// Draw the gators
			//==================================================================
			for (i = 0; i < gator_count; i++) {
				dir[0] = boat_x - gator_x[i];
				dir[1] = boat_y - gator_y[i];
				normalize(dir);
				
				AffineTransform orig = gr.getTransform();
				gr.translate(gator_x[i], gator_y[i]);
				gr.rotate(Math.atan2(dir[1], dir[0]));
				if (gator_time[i] < GATOR_BLINK) {
					gr.drawImage(gator_image_open, -GATOR_RADIUS, -GATOR_RADIUS, null);
				}
				else {
					gr.drawImage(gator_image_closed, -GATOR_RADIUS, -GATOR_RADIUS, null);
				}
				gr.setTransform(orig);
			}

			//==================================================================
			// Draw the boat
			//==================================================================
			dir[0] = mouse_x - boat_x;
			dir[1] = mouse_y - boat_y;
			normalize(dir);
				
			AffineTransform orig = gr.getTransform();
			gr.translate(boat_x, boat_y);
			gr.rotate(Math.atan2(dir[1], dir[0]));
			gr.drawImage(boat_image, -BOAT_RADIUS, -BOAT_RADIUS, null);
			// Top fan blade
			int a = (int)(boat_fan * 255);
			gr.setColor(new Color(a, a, a));
			gr.fillRect(-10, -6, 3, 6);
			// Bottom fan blade
			a = 255 - a;
			gr.setColor(new Color(a, a, a));
			gr.fillRect(-10, 0, 3, 6);
			gr.setTransform(orig);

			//==================================================================
			// Draw the spears
			//==================================================================
			gr.setStroke(STROKE_SPEAR);
			gr.setColor(new Color(158, 74, 0));
			for (i = 0; i < spear_count; i++) {
				gr.drawLine(
						(int)spear_x[i], 
						(int)spear_y[i], 
						(int)(spear_x[i] + (spear_vel[i][0] * SPEAR_LENGTH)),
						(int)(spear_y[i] + (spear_vel[i][1] * SPEAR_LENGTH)));
			}
			gr.setStroke(STROKE_SINGLE);

			//==================================================================
			// Draw FPS and Game State
			//==================================================================
//			gr.setColor(new Color(128, 128, 128, 128));
			gr.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0, 128), 0, 20, new Color(255, 255, 255, 128)));
			gr.fillRect(0, 0, WIDTH, 20);
			gr.setColor(Color.white);
			gr.setFont(FONT_GAME);
//			gr.drawString(String.format("%.2f", frame_rate), 5, 16);
			gr.drawString(String.format("Level: %d", game_level), 80, 16);
			gr.drawString(String.format("Lives: %d", game_lives), 155, 16);
			gr.drawString(String.format("Time: %.1f", game_remaining), 230, 16);
			gr.drawString(String.format("Money: $%d", game_money), 325, 16);

			//==================================================================
			// Draw Top 10 Scores
			//==================================================================
			if (game_paused) {	
//				gr.setColor(new Color(128, 128, 128, 200));
				gr.setFont(FONT_GAME);
				gr.setPaint(new GradientPaint(150, 60, new Color(0, 0, 0, 128), 350, 310, new Color(255, 255, 255, 128)));
				gr.fillRect(150, 40, 200, 270);
				
				gr.setColor(new Color(255, 255, 255, 200));
				gr.fillRect(158, 68, 184, 234);
				gr.setColor(Color.white);
				gr.drawString("PAUSED", WIDTH / 2 - 25, 60);
				gr.setColor(Color.black);
				gr.drawString("Top 10", 230, 80);
				gr.drawString("Level", 190, 100);
				gr.drawString("Money", 275, 100);
				for (i = 0; i < score_count; i++) {
					gr.drawString(String.format("%5d%12d", score_level[i], scores[i]), 190, 120 + i * 18);
				}
			}

			//==================================================================
			// Draw to the applet
			//==================================================================
			paint(getGraphics());
		}
	}

	// Saves the current score to the top 10
	private void saveScore() {
		// Update the score list
		for (j = 0; j < score_count; j++) {
			if (game_money > scores[j]) {
				break;
			}
		}
		// Can it be added?
		if (j < SCORE_MAX) {
			// Move any scores
			for (k = Math.min(score_count, SCORE_MAX - 1); k > j; k--) {
				scores[k] = scores[k - 1];
				score_level[k] = score_level[k - 1];
			}
			// Save score finally
			scores[j] = game_money;
			score_level[j] = game_level;
			// Was it added?
			if (score_count < SCORE_MAX) {
				score_count++;
			}
		}
	}
	
	// Removes the given gator from the list. Also adds a blood and ripple at
	// the gators location. If there are no more gators then this will make sure
	// the game proceeds to the next level. This also adds to the money counter.
	private void killGator(int i) {
		addBlood(gator_x[i], gator_y[i]);
		addRipple(gator_x[i], gator_y[i], GATOR_RIPPLE);
		gator_count--;
		gator_x[i] = gator_x[gator_count];
		gator_y[i] = gator_y[gator_count];
		gator_time[i] = gator_time[gator_count];
		
		if (gator_count == 0) {
			game_next = true;
		}
		
		game_money += MONEY_GATOR;
//		audioGator.play();
	}

	// Removes the given spear from the list.
	private void killSpear(int i) {
		spear_count--;
		spear_x[i] = spear_x[spear_count];
		spear_y[i] = spear_y[spear_count];
		spear_vel[i][0] = spear_vel[spear_count][0];
		spear_vel[i][1] = spear_vel[spear_count][1];
		spear_life[i] = spear_life[spear_count];
	}

	// Adds a pool of blood at the given location
	private final void addBlood(int x, int y) {
		int bursts = rnd.nextInt(BLOOD_BURST_MAX - BLOOD_BURST_MIN) + BLOOD_BURST_MIN;
		if (bursts + blood_count >= BLOOD_MAX) {
			bursts = BLOOD_MAX - blood_count - 1;
		}
		while (--bursts >= 0) {
			int dx = rnd.nextInt(GATOR_RADIUS * 2) - GATOR_RADIUS;
			int dy = rnd.nextInt(GATOR_RADIUS * 2) - GATOR_RADIUS;

			float life = rnd.nextFloat() * (BLOOD_LIFE_MAX - BLOOD_LIFE_MIN) + BLOOD_LIFE_MIN;
			float size = rnd.nextFloat() * (BLOOD_SIZE_MAX - BLOOD_SIZE_MIN) + BLOOD_SIZE_MIN;
			float size_vel = rnd.nextFloat() * (BLOOD_SIZE_VEL_MAX - BLOOD_SIZE_VEL_MIN) + BLOOD_SIZE_VEL_MIN;
			
			blood_x[blood_count] = x + dx;
			blood_y[blood_count] = y + dy;
			blood_time[blood_count] = 0f;
			blood_life[blood_count] = life;
			blood_size[blood_count] = size;
			blood_size_vel[blood_count] = size_vel;
			blood_count++;
		}
	}

	// Adds a ripple at the given point with the given radius
	private void addRipple(int x, int y, int radius) {
		for (j = y - radius; j < y + radius;j++) {
			for (k = x - radius; k < x + radius; k++) {
				if (j >= 0 && j < HEIGHT && k >= 0 && k < WIDTH) {
					ripple_map[old_index + (j * WIDTH) + k] += 512;            
				} 
			}
		}
	}

	// Updates the ripple effect
	private void updateRippleEffect() {
		int data, map_index;
		// Swap the buffers
		i = old_index;
		old_index = new_index;
		new_index = i;
		// Reset pointers into the image (i) and previous buffer (map_index)
		i = 0;
		map_index = old_index;
		// For every pixel on the ripple image...
		for (j = 0; j < HEIGHT; j++) {
			for (k = 0; k < WIDTH; k++) {
				// Get the value of the current point based on the previous
				// ripple map buffer.
				data = ((ripple_map[map_index - WIDTH] + 
						 ripple_map[map_index + WIDTH] + 
						 ripple_map[map_index - 1] + 
						 ripple_map[map_index + 1]) >> 1);
				
				// Adjust based on current point and then slowly fade it.
				data -= ripple_map[new_index + i];
				data -= data >> 5;
				// Update the current point
				ripple_map[new_index + i] = (short)data;

				// Transform data into a color component
				data = ((1024 - data) >> 4) - 20;
				if (data > 255) data = 255;
				if (data < 0) data = 0;
				// Set the color at this point between white and blue
				ripple[i++] = 0xFF0000FF | (data << 16) | (data << 8);
				map_index++;
			}
		}
		// Nofity the image that pixels have been set.
		water_source.newPixels();
	}

	// Given a circle can it be placed on the field so it doesn't overlap with
	// the boat, any buoys, and any gators.
	private boolean canPlace(float x, float y, float r) {
		if (intersects(x, y, boat_x, boat_y, r + BOAT_RADIUS)) {
			return false;
		}
		for (k = 0; k < buoy_count; k++) {
			if (intersects(x, y, buoy_x[k], buoy_y[k], r + BUOY_RADIUS)) {
				return false;
			}
		}
		for (k = 0; k < gator_count; k++) {
			if (intersects(x, y, gator_x[k], gator_y[k], r + GATOR_RADIUS)) {
				return false;
			}
		}
		return true;
	}
	
	// Returns true if the two circles intersect. dist is the sum of their radii
	private boolean intersects(float cx1, float cy1, float cx2, float cy2, float dist) {
		float dx = cx2 - cx1;
		float dy = cy2 - cy1;
		return (dx * dx + dy * dy <= dist * dist);
	}
	
	// Normalizes the given vector and returns the magnitude of the vector
	private float normalize(float[] vec) {
		float dot = vec[0] * vec[0] + vec[1] * vec[1];
		if (dot != 0f) {
			dot = (float)Math.sqrt(dot);
			vec[0] /= dot;
			vec[1] /= dot;
			return dot;
		}
		return 0f;
	}

	// Paints the game
	public void paint(Graphics g) {
		update(g);
	}
	
	// Paints the game
	public void update(Graphics g) {
		if (g != null) {
			g.drawImage(buffer, 0, 0, null);
		}
	}

	// Processes all key events
	public void processKeyEvent(KeyEvent e) {
		int code = e.getKeyChar();
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (code == 'p') {
				game_paused = !game_paused;
			}
			else if (code == '\n') {
				game_reset = true;
			}
		}
	}

	// Processes all mouse events
	public void processMouseEvent(MouseEvent e) {
		if (e.getButton() == 1 && e.getID() == MouseEvent.MOUSE_PRESSED) {
			if (game_paused || spear_time < SPEAR_RELOAD || spear_count == SPEAR_MAX) {
				return;
			}
			spear_time = 0f;
				
			spear_x[spear_count] = boat_x;
			spear_y[spear_count] = boat_y;
			spear_vel[spear_count][0] = mouse_x - boat_x;
			spear_vel[spear_count][1] = mouse_y - boat_y;
			normalize(spear_vel[spear_count]);
			spear_life[spear_count] = SPEAR_LIFE;
			spear_count++;
			
			game_money += MONEY_SPEAR;
		}
	}

	// Processes all mouse motion events.
	public void processMouseMotionEvent(MouseEvent e) {
		if (!game_paused) {
			mouse_x = e.getX();
			mouse_y = e.getY();	
		}
	}

}
