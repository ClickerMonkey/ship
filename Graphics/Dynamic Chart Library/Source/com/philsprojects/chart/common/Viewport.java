package com.philsprojects.chart.common;


/**
 * A viewport is a view into a 2-dimensional world. The viewport into the world
 * is specified by its left, top, right, and bottom bounds. The viewport can be
 * restricted with a maximum or minimum size. The viewports location can be 
 * restricted by the maximum bounds of the world. The viewport of the world can
 * translate along any axis in 2-dimensional space giving the appearance of
 * scrolling. The viewport can also be zoomed in or out at any point in the
 * world or simply the center of the viewpoint. Given a coordinate on the map,
 * it can be converted to a coordinate on screen space and visa versa.
 * 
 * @author Philip Diffenderfer
 */
public abstract class Viewport extends Canvas
{
//
//    public static final int RIGHT = 0;
//    public static final int LEFT = 1;
//    public static final int TOP = 2;
//    public static final int BOTTOM = 3;
//    public static final int MAX_RIGHT = 4;
//    public static final int MAX_LEFT = 5;
//    public static final int MAX_TOP = 6;
//    public static final int MAX_BOTTOM = 7;
//    public static final int MAX_WIDTH = 8;
//    public static final int MIN_WIDTH = 9;
//    public static final int MAX_HEIGHT = 10;
//    public static final int MIN_HEIGHT = 11;
//    public static final int INV_WIDTH = 12;
//    public static final int INV_HEIGHT = 13;
//    public static final int FIXED_RATIO = 14;
//    public static final int MIN_RATIO = 14;
//    public static final int MAX_RATIO = 15;
//
//    public static final int TOTAL_FIELDS = 16;
//
//    protected double[] fields = new double[TOTAL_FIELDS];

    // The x-coordinate of the right side of the viewport in world space.
    protected double right = 1.0;

    // The x-coordinate of the left side of the viewport in world space.
    protected double left = 0.0;

    // The y-coordinate of the top side of the viewport in world space.
    protected double top = 1.0;

    // The y-coordinate of the bottom side of the viewport in world space.
    protected double bottom = 0.0;


    // The maximum x-coordinate of the world. The viewports right side cannot 
    // go beyond this value.
    protected double maxRight = Double.MAX_VALUE;

    // The minimum x-coordinate of the world. The viewports left side cannot 
    // go beyond this value.
    protected double maxLeft = -Double.MAX_VALUE;

    // The maximum y-coordinate of the world. The viewports top side cannot 
    // go beyond this value.
    protected double maxTop = Double.MAX_VALUE;

    // The minimum y-coordinate of the world. The viewports bottom side cannot 
    // go beyond this value.
    protected double maxBottom = -Double.MAX_VALUE;


    // The maximum allowed width of the viewport in world space.
    protected double maxWidth = Double.MAX_VALUE;

    // The minimum allowed width of the viewport in world space.
    protected double minWidth = Double.MIN_VALUE;

    // The maximum allowed height of the viewport in world space.
    protected double maxHeight = Double.MAX_VALUE;

    // The minimum allowed height of the viewport in world space.
    protected double minHeight = Double.MIN_VALUE;


    // The inverse of the viewports width (different between right and left)
    protected double invWidth = 1.0;

    // The inverse of the viewports height (difference between bottom and top)
    protected double invHeight = 1.0;

    // The ratio, if specified, which restricts the width and height of the
    // view port to the same scale. If there is no fixed ratio then this is 0.
    // A ratio is determined by dividing the width of the viewport by the height.
    protected double fixedRatio = 0.0;

    // The maximum ratio which restricts the width and height relationship, or
    // their differences in size.
    protected double maxRatio = Double.MAX_VALUE;

    // The minimum ratio which restricts the width and height relationship, or
    // their difference in size.
    protected double minRatio = Double.MIN_VALUE;


    /**
     * Initializes a viewport setting its clipping rectangle within a component.
     * The clipping rectangle specifies the rectangular region in the component
     * that is reserved for this viewport to base calculations on.
     * 
     * @param antialiasing => Whether antialiased drawing is enabled on the map.
     * @param offsetX => The x offset in pixels of the clipping rectangle.
     * @param offsetY => The y offset in pixels of the clipping rectangle.
     * @param width => The width in pixels of the clipping rectangle.
     * @param height => The height in pixels of the clipping rectangle.
     */
    public Viewport(boolean antialiasing, int offsetX, int offsetY, int width, int height)
    {
	super(antialiasing, offsetX, offsetY, width, height);
    }

    /**
     * Sets this viewport with a fixed size and fixed position. This viewport
     * cannot zoom or translate from the current position.
     * 
     * @param right => The x-coordinate of the right side of this viewport in world space.
     * @param left => The x-coordinate of the left side of this viewport in world space.
     * @param top => The y-coordinate of the top side of this viewport in world space.
     * @param bottom => The y-coordinate of the bottom side of this viewport in world space.
     */
    public void setViewportFixed(double right, double left, double top, double bottom)
    {
	double width = right - left;
	double height = top - bottom;
	setMaxBounds(right, left, top, bottom);
	setMaxSize(width, width, height, height);
	setBounds(right, left, top, bottom);
    }

    /**
     * Sets this viewport with a fixed size. This map cannot zoom but can
     * translate across the world.
     * 
     * @param right => The x-coordinate of the right side of this viewport in world space.
     * @param left => The x-coordinate of the left side of this viewport in world space.
     * @param top => The y-coordinate of the top side of this viewport in world space.
     * @param bottom => The y-coordinate of the bottom side of this viewport in world space.
     */
    public void setViewportFixedSize(double right, double left, double top, double bottom)
    {
	double width = right - left;
	double height = top - bottom;
	setMaxSize(width, width, height, height);
	setBounds(right, left, top, bottom);
    }

    @Override
    public final boolean listensTo(Viewport view)
    {
	return (view == this);
    }

    /** 
     * Zooms in or out on a focus point, scaling the viewport size.
     * 
     * @param scale => The scale of zooming where 1.0 has no effect on the
     * 		viewport, anything less then 1.0 shrinks the viewport (zooms in), and
     * 		anything greater then 1.0 enlarges the viewport (zooms out).
     * @param x => The x-coordinate to focus on while zooming.
     * @param y => The y-coordinate to focus on while zooming.
     */
    public final void zoom(double scale, double x, double y)
    {
	if (scale == 0.0)
	    return;

	double halfLeft, halfRight, halfBottom, halfTop;
	halfRight = (right  - x) * scale;
	halfLeft = (x - left) * scale;
	halfTop = (top - y) * scale;
	halfBottom = (y - bottom) * scale;

	right = x + halfRight;
	left = x - halfLeft;
	top = y + halfTop;
	bottom = y - halfBottom;
	correctBounds(x, y);
    }

    /** 
     * Zooms in or out on the center of the viewport, scaling the viewport size.
     * 
     * @param scale => The scale of zooming where 1.0 has no effect on the
     * 		viewport, anything less then 1.0 shrinks the viewport (zooms in), and
     * 		anything greater then 1.0 enlarges the viewport (zooms out).
     */
    public final void zoom(double scale)
    {
	// Use the center of the graph as the focus point
	zoom(scale, getCenterX(), getCenterY());
    }

    /** 
     * Translates the viewport on the x-axis in world space.
     * 
     * @param dx => The value to translate the viewport by horizontally.
     */
    public final void translateX(double dx)
    {
	right += dx;
	left += dx;
	correctBounds();
    }

    /** 
     * Translates the viewport on the y-axis in world space.
     * 
     * @param dy => The value to translate the viewport by vertically.
     */
    public final void translateY(double dy)
    {
	top += dy;
	bottom += dy;
	correctBounds();
    }

    /**
     * Translates the viewport on the x and y-axis in world space.
     * 
     * @param dx => The value to translate the viewport by horizontally.
     * @param dy => The value to translate the viewport by vertically.
     */
    public final void translate(double dx, double dy)
    {
	right += dx;
	left += dx;
	top += dy;
	bottom += dy;
	correctBounds();
    }

    /**
     * Centers the viewport at a point in world space while keeping the same size.
     * 
     * @param x => The center x-coordinate in world space.
     * @param y => The center y-coordinate in world space.
     */
    public final void center(double x, double y)
    {
	double halfWidth = getWidth() * 0.5;
	double halfHeight = getHeight() * 0.5;

	right = x + halfWidth;
	left = x - halfWidth;
	top = y + halfHeight;
	bottom = y - halfHeight;
	correctBounds();
    }


    /**
     * This corrects any bounds alignment with the max left, right, top,
     * and bottom. If the graph window is larger then the max its squeezed
     * into the max bounds. If the size is outside the bound restrictons
     * then center it on a the graph's center values.
     */
    public final void correctBounds()
    {
	correctBounds(getCenterX(), getCenterY());
    }

    /**
     * This corrects any bounds alignment with the max left, right, top,
     * and bottom. If the graph window is larger then the max its squeezed
     * into the max bounds. If the size is outside the bound restrictions
     * then center it on a desired point.
     * 
     * @param desiredX => The desired x value of focus when correcting bounds.
     * @param desiredY => The desired y value of focus when correcting bounds.
     */
    public final void correctBounds(double desiredX, double desiredY)
    {
	// Correct the width and height of the window based on
	// the ratio adjustment (widthScale, heightScale).
	double widthScale = 1.0;
	widthScale = Math.max(widthScale, minWidth * invWidth);
	widthScale = Math.min(widthScale, maxWidth * invWidth);

	double heightScale = 1.0;
	heightScale = Math.max(heightScale, minHeight * invHeight);
	heightScale = Math.min(heightScale, maxHeight * invHeight);

	// Calculate the new adjusted bounds of the graph window.
	double diffRight, diffLeft, diffTop, diffBottom;
	diffRight = (right - desiredX) * widthScale;
	diffLeft = (desiredX - left) * widthScale;
	diffTop = (top - desiredY) * heightScale;
	diffBottom = (desiredY - bottom) * heightScale;

	// Set the new actual bounds.
	right = desiredX + diffRight;
	left = desiredX - diffLeft;
	top = desiredY + diffTop;
	bottom = desiredY - diffBottom;

	// Update the width and height
	double width = getWidth();
	double height = getHeight();

	// If the left and the right are both outside of the max
	// bounds then set them to the max's.
	if (width > getWorldWidth())
	{
	    left = maxLeft;
	    right = maxRight;
	}
	// If only the left is outside of the max bounds then shift it
	// over so its on the max left and it keeps the same width.
	else if (left < maxLeft)
	{
	    left = maxLeft;
	    right = maxLeft + width;
	}
	// If only the right is outside of the max bounds then shift it
	// over so its on the max right and it keeps the same width.
	else if (right > maxRight)
	{
	    right = maxRight;
	    left = maxRight - width;
	}

	// If the top and the bottom are both outside of the max
	// bounds then set them to the max's.
	if (height > getWorldHeight())
	{
	    bottom = maxBottom;
	    top = maxTop;
	}
	// If only the bottom is outside of the max bounds then shift it
	// over so its on the max bottom and it keeps the same height.
	else if (bottom < maxBottom)
	{
	    bottom = maxBottom;
	    top = maxBottom + height;
	}
	// If only the top is outside of the max bounds then shift it
	// over so its on the max top and it keeps the same height.
	else if (top > maxTop)
	{
	    top = maxTop;
	    bottom = maxTop - height;
	}

	// Update the inverse width and height
	invWidth = 1.0 / (right - left);
	invHeight = 1.0 / (top - bottom);
    }


    /**
     * Sets the location and size of the viewport in world space by setting
     * each side.
     * 
     * @param right => The x-coordinate of the right side of this viewport.
     * @param left => The x-coordinate of the left side of this viewport.
     * @param top => The y-coordinate of the top side of this viewport.
     * @param bottom => The y-coordinate of the bottom side of this viewport.
     */
    public final void setBounds(double right, double left, double top, double bottom)
    {
	this.right = right;
	this.left = left;
	this.top = top;
	this.bottom = bottom;
	correctBounds();
    }

    /**
     * Sets the maximum bounds of the world.
     * 
     * @param maxRight => The maximum right value of the viewport allowed.
     * @param maxLeft => The maximum left value of the viewport allowed.
     * @param maxTop => The maximum top value of the viewport allowed.
     * @param maxBottom => The maximum bottom value of the viewport allowed.
     */
    public final void setMaxBounds(double maxRight, double maxLeft, double maxTop, double maxBottom)
    {
	this.maxRight = maxRight;
	this.maxLeft = maxLeft;
	this.maxTop = maxTop;
	this.maxBottom = maxBottom;
	correctBounds();
    }

    /**
     * Sets the maximum and minimum viewport width and height allowed.
     * 
     * @param maxWidth => The maximum width of the viewport in world space.
     * @param minWidth => The minimum width of the viewport in world space.
     * @param maxHeight => The maximum height of the viewport in world space.
     * @param minHeight => The minimum height of the viewport in world space.
     */
    public final void setMaxSize(double maxWidth,  double minWidth, double maxHeight, double minHeight)
    {
	this.maxWidth = maxWidth;
	this.minWidth = minWidth;
	this.maxHeight = maxHeight;
	this.minHeight = minHeight;
	correctBounds();
    }

    /**
     * Sets the x-coordinate of the right side of the viewport.
     * 
     * @param right => The x-coordinate of the right side in world space.
     */
    public final void setRight(double right)
    {
	this.right = right;
	correctBounds();
    }

    /**
     * Sets the x-coordinate of the left side of the viewport.
     * 
     * @param left => The x-coordinate of the left side in world space.
     */
    public final void setLeft(double left)
    {
	this.left = left;
	correctBounds();
    }

    /**
     * Sets the y-coordinate of the top side of the viewport.
     * 
     * @param top => The y-coordinate of the top side in world space.
     */
    public final void setTop(double top)
    {
	this.top = top;
	correctBounds();
    }

    /**
     * Sets the y-coordinate of the bottom side of the viewport.
     * 
     * @param bottom => The y-coordinate of the bottom side in world space.
     */
    public final void setBottom(double bottom) 
    {
	this.bottom = bottom;
	correctBounds();
    }

    /**
     * Sets the maximum x-coordinate of the world. The viewports right side 
     * cannot go beyond this value.
     * 
     * @param maxRight => The maximum x-coordinate in world space.
     */
    public final void setMaxRight(double maxRight)
    {
	this.maxRight = maxRight;
	correctBounds();
    }

    /**
     * Sets the minumum x-coordinate of the world. The viewports left side 
     * cannot go beyond this value.
     * 
     * @param maxLeft => The minimum x-coordinate in world space.
     */
    public final void setMaxLeft(double maxLeft)
    {
	this.maxLeft = maxLeft;
	correctBounds();
    }

    /**
     * Sets the maximum y-coordinate of the world. The viewports top side 
     * cannot go beyond this value.
     * 
     * @param maxTop => The maximum y-coordinate in world space.
     */
    public final void setMaxTop(double maxTop)
    {
	this.maxTop = maxTop;
	correctBounds();
    }

    /**
     * Sets the minimum y-coordinate of the world. The viewports bottom side 
     * cannot go beyond this value.
     * 
     * @param maxBottom => The minimum y-coordinate in world space.
     */
    public final void setMaxBottom(double maxBottom) 
    {
	this.maxBottom = maxBottom;
	correctBounds();
    }

    /**
     * Sets the maximum allowed width of the viewport.
     * 
     * @param maxWidth => The maximum width of the viewport in world space.
     */
    public final void setMaxWidth(double maxWidth)
    {
	this.maxWidth = maxWidth;
	correctBounds();
    }

    /**
     * Sets the minimum allowed width of the viewport.
     * 
     * @param minWidth => The minimum width of the viewport in world space.
     */
    public final void setMinWidth(double minWidth)
    {
	this.minWidth = minWidth;
	correctBounds();
    }

    /**
     * Sets the maximum allowed height of the viewport.
     * 
     * @param maxHeight => The maximum height of the viewport in world space.
     */
    public final void setMaxHeight(double maxHeight)
    {
	this.maxHeight = maxHeight;
	correctBounds();
    }

    /**
     * Sets the minimum allowed height of the viewport.
     * 
     * @param minHeight => The minimum height of the viewport in world space.
     */
    public final void setMinHeight(double minHeight)
    {
	this.minHeight = minHeight;
	correctBounds();
    }

    // TODO
    public final void fixRatio()
    {
	this.fixedRatio = (getWidth() / getHeight());
    }

    public final void setMinRatio(double minRatio)
    {
	this.minRatio = minRatio;
    }

    public final void setMinRatio(double width, double height)
    {
	this.minRatio = (width / height);
    }

    public final void setMaxRatio(double maxRatio)
    {
	this.minRatio = maxRatio;
    }

    public final void setMaxRatio(double width, double height)
    {
	this.maxRatio = (width / height);
    }

    /** 
     * Transforms an x-coordinate in world space to the equivalent x-coordinate 
     * on the screen.
     * 
     * @param x => The x-coordinate in world space to transform.
     */
    public final double toScreenX(double x)
    {
	double delta = (x - left) * invWidth;
	return (delta * bounds.width);
    }

    /** 
     * Transforms a y-coordinate in world space to the equivalent y-coordinate 
     * on the screen.
     * 
     * @param y => The y-coordinate in world space to transform.
     */
    public final double toScreenY(double y)
    {
	double delta = 1.0 - (y - bottom) * invHeight;
	return (delta * bounds.height);
    }

    /**
     * Transforms an x-coordinate in screen space to the equivalent x-coordinate
     * in world space.
     * 
     * @param x => The x-coordinate in screen space to transform.
     */
    public final double toWorldX(double x)
    {
	double delta = (x - bounds.x) * invBoundsWidth;
	return getWidth() * delta + left;
    }

    /**
     * Transforms a y-coordinate in screen space to the equivalent y-coordinate
     * in world space.
     * 
     * @param y => The y-coordinate in screen space to transform.
     */
    public final double toWorldY(double y)
    {
	double delta = 1.0 - (y - bounds.y) * invBoundsHeight;
	return getHeight() * delta + bottom;
    }

    /**
     * Translates a length on the x-axis in the world to a length on the x-axis
     * on the screen in pixels.
     * 
     * @param size => The size in world space to translate to the size on screen.
     */
    public final double toScreenSizeX(double size)
    {
	return (size * bounds.width * invWidth);
    }

    /**
     * Translates a length on the y-axis in the world to a length on the y-axis
     * on the screen in pixels.
     * 
     * @param size => The size in world space to translate to the size on screen.
     */
    public final double toScreenSizeY(double size)
    {
	return (size * bounds.height * invHeight);
    }

    /**
     * Translates a length on the x-axis on the screen in pixels to the length
     * on the x-axis in world space.
     * 
     * @param size => The size on the screen in pixels to translate to the size 
     * 		in world space.
     */
    public final double toWorldSizeX(double size)
    {
	return (size * getWidth() * invBoundsWidth);
    }

    /**
     * Translates a length on the y-axis on the screen in pixels to the length
     * on the y-axis in world space.
     * 
     * @param size => The size on the screen in pixels to translate to the size 
     * 		in world space.
     */
    public final double toWorldSizeY(double size)
    {
	return (size * getHeight() * invBoundsHeight);
    }

    /**
     * Returns the x-coordinate of the right side of the viewport in world space.
     */
    public final double getRight()
    {
	return right;
    }

    /**
     * Returns the x-coordinate of the left side of the viewport in world space.
     */
    public final double getLeft()
    {
	return left;
    }

    /**
     * Returns the y-coordinate of the top side of the viewport in world space.
     */
    public final double getTop()
    {
	return top;
    }

    /**
     * Returns the y-coordinate of the bottom side of the viewport in world space.
     */
    public final double getBottom()
    {
	return bottom;
    }

    /**
     * Returns the height of the viewport in world space.
     */
    public final double getHeight()
    {
	return (top - bottom);
    }

    /**
     * Returns the width of the viewport in world space.
     */
    public final double getWidth()
    {
	return (right - left);
    }

    /**
     * Returns the x-coordinate of the center of the viewport in world space.
     */
    public final double getCenterX()
    {
	return (right + left) * 0.5;
    }

    /**
     * Returns the y-coordinate of the center of the viewport in world space.
     */
    public final double getCenterY()
    {
	return (top + bottom) * 0.5;
    }

    /**
     * Returns the minimum x-coordinate of the world. The viewports left side 
     * cannot go beyond this value.
     */
    public final double getMaxLeft()
    {
	return maxLeft;
    }

    /**
     * Returns the maximum x-coordinate of the world. The viewports right side 
     * cannot go beyond this value.
     */
    public final double getMaxRight()
    {
	return maxRight;
    }

    /**
     * Returns the maximum y-coordinate of the world. The viewports top side 
     * cannot go beyond this value.
     */
    public final double getMaxTop()
    {
	return maxTop;
    }

    /**
     * Returns the minimum y-coordinate of the world. The viewports bottom side 
     * cannot go beyond this value.
     */
    public final double getMaxBottom()
    {
	return maxBottom;
    }

    /**
     * Returns the width of the world. A viewports width cannot exceed this.
     */
    public final double getWorldWidth()
    {
	return (maxRight - maxLeft);
    }

    /**
     * Returns the height of the world. A viewports height cannot exceed this.
     */
    public final double getWorldHeight()
    {
	return (maxTop - maxBottom);
    }	

    /**
     * Returns the maximum allowed width of the viewport in world space.
     */
    public final double getMaxWidth()
    {
	return maxWidth;
    }

    /**
     * Returns the minimum allowed width of the viewport in world space.
     */
    public final double getMinWidth()
    {
	return minWidth;
    }

    /**
     * Returns the maximum allowed height of the viewport in world space.
     */
    public final double getMaxHeight()
    {
	return maxHeight;
    }

    /**
     * Returns the minimum allowed height of the viewport in world space.
     */
    public final double getMinHeight()
    {
	return minHeight;
    }

    /**
     * Returns the ratio, if specified, which restricts the width and height of 
     * the view port to the same scale. If there is no fixed ratio then this is 0.
     * A ratio is determined by dividing the width of the viewport by the height.
     */
    public final double getFixedRatio()
    {
	return fixedRatio;
    }

    /**
     * Returns the maximum ratio which restricts the width and height
     * relationship, or their differences in size.
     */
    public final double getMaxRatio()
    {
	return maxRatio;
    }

    /**
     * Returns the minmum ratio which restricts the width and height
     * relationship, or their differences in size.
     */
    public final double getMinRatio()
    {
	return minRatio;
    }


}
