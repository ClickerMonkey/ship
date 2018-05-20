package model3d;

public class LineDrawer {

	private final Canvas canvas;
	
	public LineDrawer(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		if (dx == 0 && dy == 0) {
			canvas.drawPixel(x1, y1);
			return;
		}
		int sx = Integer.signum(dx);
		int sy = Integer.signum(dy);
		int adx = sx * dx;
		int ady = sy * dy;
		if (sx == 0) {
			drawLineVertical(x1, y1, y2, sy);
		}
		else if (sy == 0) {
			drawLineHorizontal(y1, x1, x2, sx);
		}
		else if (adx == ady) {
			drawLineDiagonal(x1, y1, x2, y2, sx, sy);
		}
		else if (ady < adx) {
			drawLineLess(x1, y1, x2, y2, adx, ady, sx, sy);
		}
		else {
			drawLineGreater(x1, y1, x2, y2, adx, ady, sx, sy);
		}
	}

	public void drawLineHorizontal(int y, int x1, int x2, int sx) {
		int point = canvas.index(x1, y);
		int end = canvas.index(x2, y);
		canvas.drawPixel(point);
		while (point != end) {
			point += sx;
			canvas.drawPixel(point);
		}
	}

	public void drawLineVertical(int x, int y1, int y2, int sy) {
		int point = canvas.index(x, y1);
		int end = canvas.index(x, y2);
		int ly = sy * canvas.getWidth();
		canvas.drawPixel(point);
		while (point != end) {
			point += ly;
			canvas.drawPixel(point);
		}
	}

	public void drawLineDiagonal(int x1, int y1, int x2, int y2, int sx, int sy) {
		int point = canvas.index(x1, y1);
		int end = canvas.index(x2, y2);
		int skip = canvas.index(sx, sy);
		canvas.drawPixel(point);
		while (point != end) {
			point += skip;
			canvas.drawPixel(point);
		}
	}

	public void drawLineGreater(int x1, int y1, int x2, int y2, int adx, int ady, int sx, int sy) {
		int e = (adx << 1);
		int ne = ((adx - ady) << 1);
		int d = (e - ady);
		int f = (sy - 1) >> 1;
		canvas.drawPixel(x1, y1);
		while (y1 != y2) {
			if (d <= f) {
				d += e;
			} else {
				d += ne;
				x1 += sx;
			}
			y1 += sy;
			canvas.drawPixel(x1, y1);
		}
	}

	public void drawLineLess(int x1, int y1, int x2, int y2, int adx, int ady, int sx, int sy) {
		int e = (ady << 1);
		int ne = ((ady - adx) << 1);
		int d = (e - adx);
		int f = (sx - 1) >> 1;
		canvas.drawPixel(x1, y1);
		while (x1 != x2) {
			if (d <= f) {
				d += e;
			} else {
				d += ne;
				y1 += sy;
			}
			x1 += sx;
			canvas.drawPixel(x1, y1);
		}
	}
	
}
