package model3d;

public class Clipper {

	private static final int CLIP_CODE_TOP = 1;
	private static final int CLIP_CODE_BOTTOM = 2;
	private static final int CLIP_CODE_LEFT = 4;
	private static final int CLIP_CODE_RIGHT = 8;

	private int clipTop;
	private int clipBottom;
	private int clipLeft;
	private int clipRight;

	public void setClipRect(int x, int y, int width, int height) {
		setClipBounds(x, y, x + width, y + height);
	}
	
	public void setClipBounds(int left, int top, int right, int bottom) {
		clipLeft = left;
		clipTop = top;
		clipRight = right;
		clipBottom = bottom;
	}
	
	public void clipRect(int x, int y, int width, int height) {
		clipBounds(x, y, x + width, y + height);
	}
	
	public void clipBounds(int left, int top, int right, int bottom) {
		clipLeft = Math.max(clipLeft, left);
		clipTop = Math.max(clipTop, top);
		clipRight = Math.min(clipRight, right);
		clipBottom = Math.min(clipBottom, bottom);
	}
	
	public boolean clipLine(int[] x, int[] y)
	{
		// Compute out codes of start point
		boolean code0_left = x[0] < clipLeft;
		boolean code0_right = x[0] > clipRight;
		boolean code0_top = y[0] < clipTop;
		boolean code0_bottom = y[0] > clipBottom;
		boolean code0 = code0_left | code0_right | code0_top | code0_bottom;

		// Compute out codes of end point
		boolean code1_left = x[1] < clipLeft;
		boolean code1_right = x[1] > clipRight;
		boolean code1_top = y[1] < clipTop;
		boolean code1_bottom = y[1] > clipBottom;
		boolean code1 = code1_left | code1_right | code1_top | code1_bottom;
		
		// Trivially Accept
		if (!(code0 | code1)) {
			return true;
		}
		
		// Trivially Reject
		if ((code0_left && code1_left) ||
			(code0_right && code1_right) ||
			(code0_top && code1_top) ||
			(code0_bottom && code1_bottom)) {
			return false;
		}
		
		// If the first point is outside the clip rectangle
		if (code0) {
			// If its above the top side of the clip rectangle
			if (code0_top) {
				x[0] += (clipTop - y[0]) * (x[1] - x[0]) / (y[1] - y[0]);
				y[0] = clipTop;
			}
			// If its below the bottom side of the clip rectangle
			else if (code0_bottom) {
				x[0] += (clipBottom - y[0]) * (x[1] - x[0]) / (y[1] - y[0]);
				y[0] = clipBottom;
			}
			
			// Update left and right since it could've changed
			code0_left = x[0] < clipLeft;
			code0_right = x[0] > clipRight;
			
			// If its after the right side of the clip rectangle
			if (code0_right) {
				y[0] += (clipRight - x[0]) * (y[1] - y[0]) / (x[1] - x[0]);
				x[0] = clipRight;
			}
			// If its before the left side of the clip rectangle
			else if (code0_left) {
				y[0] += (clipLeft - x[0]) * (y[1] - y[0]) / (x[1] - x[0]);
				x[0] = clipLeft;
			}	
		}
		
		// If the second point is outside the clip rectangle
		if (code1) {
			// If its above the top side of the clip rectangle
			if (code1_top) {
				x[1] += (clipTop - y[1]) * (x[1] - x[0]) / (y[1] - y[0]);
				y[1] = clipTop;
			}
			// If its below the bottom side of the clip rectangle
			else if (code1_bottom) {
				x[1] += (clipBottom - y[1]) * (x[1] - x[0]) / (y[1] - y[0]);
				y[1] = clipBottom;
			}

			// Update left and right since it could've changed
			code1_left = x[1] < clipLeft;
			code1_right = x[1] > clipRight;
			
			// If its after the right side of the clip rectangle
			if (code1_right) {
				y[1] += (clipRight - x[1]) * (y[1] - y[0]) / (x[1] - x[0]);
				x[1] = clipRight;
			}
			// If its before the left side of the clip rectangle
			else if (code1_left) {
				y[1] += (clipLeft - x[1]) * (y[1] - y[0]) / (x[1] - x[0]);
				x[1] = clipLeft;
			}
		}
		
		return true;
	}
	
	public boolean clipPolygon(int[] x, int[] y) 
	{
		int i, points = x.length;
		
		int[] codes = new int[points];
		int total = 0;
		// Calculate the code of each point in the polygon.
		for (i = 0; i < points; i++) {
			codes[i] = getCode(x[i], y[i]);
			total += Integer.signum(codes[i]);
		}
		
		// Trivially Accept
		if (total == 0) {
			return true;
		}
		
		// Trivially Reject
		if (total == points) {
			return false;
		}
		
		// The resulting clipped polygon
//		int[] px = new int[points << 1];
//		int[] py = new int[points << 1];
//		int pcount = 0;
		
		// Clip against top side
		
		
		
		return true;	
	}

	private int getCode(int x, int y) {
		int code = 0;
		if (y > clipBottom) {
			code |= CLIP_CODE_BOTTOM;
		}
		if (y < clipTop) {
			code |= CLIP_CODE_TOP;
		}
		if (x > clipRight) {
			code |= CLIP_CODE_RIGHT;
		}
		if (y < clipLeft) {
			code |= CLIP_CODE_LEFT;
		}
		return code;
	}

}
