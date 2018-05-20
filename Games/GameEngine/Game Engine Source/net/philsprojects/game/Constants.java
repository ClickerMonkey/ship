package net.philsprojects.game;

public class Constants
{

	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 4;
	public static final int WEST = 8;

	public static final int ONCE = 1;
	public static final int LOOP = 2;

	public static final int NONE = 0;
	public static final int FORWARD = 1;
	public static final int BACKWARD = -1;

	public static final int PINGPONG = 4;

	public static final int ONCE_FORWARD = 1;
	public static final int ONCE_BACKWARD = 2;
	public static final int LOOP_FORWARD = 8;
	public static final int LOOP_BACKWARD = 16;

	public static final int STATUS_WAITING = 1;
	public static final int STATUS_ALIVE = 2;
	public static final int STATUS_DYING = 4;
	public static final int STATUS_DEAD = 8;

	public static final int ALIGN_UPPERLEFT = 1;
	public static final int ALIGN_UPPERCENTER = 2;
	public static final int ALIGN_UPPERRIGHT = 4;
	public static final int ALIGN_MIDDLELEFT = 8;
	public static final int ALIGN_MIDDLECENTER = 16;
	public static final int ALIGN_MIDDLERIGHT = 32;
	public static final int ALIGN_LOWERLEFT = 64;
	public static final int ALIGN_LOWERCENTER = 128;
	public static final int ALIGN_LOWERRIGHT = 256;

	public static final int CURVE_SMOOTH = 1;
	public static final int CURVE_NORMAL = 2;

	public static final int FLIP_NONE = 1;
	public static final int FLIP_X = 2;
	public static final int FLIP_Y = 4;
	public static final int FLIP_XY = 8;

	public static final int ROTATE_NONE = 1;
	public static final int ROTATE_90 = 2;
	public static final int ROTATE_180 = 4;
	public static final int ROTATE_270 = 8;

	public static final int HIT_LEFT = 1;
	public static final int HIT_TOP = 2;
	public static final int HIT_RIGHT = 4;
	public static final int HIT_BOTTOM = 8;

	public static final int BACK_TO_FRONT = -1;
	public static final int FRONT_TO_BACK = 1;

}
