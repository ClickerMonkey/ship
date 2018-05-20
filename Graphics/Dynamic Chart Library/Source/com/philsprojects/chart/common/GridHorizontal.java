package com.philsprojects.chart.common;

import java.awt.Graphics2D;

import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public class GridHorizontal extends Grid
{

	public GridHorizontal()
	{

	}

	public GridHorizontal(Viewport view, Canvas destination)
	{
		super(view, destination);
	}

	@Override
	public void draw(Graphics2D gr)
	{
		draw(gr, frequency, 10);
	}

	public void draw(Graphics2D gr, double frequency, int max)
	{
		if (max <= 0) {
			return;
		}
		
		// Determine if this is the base frequency, if it is then do not
		// skip any lines.
		boolean isBaseFrequency = (frequency == this.frequency);

		// Convert the given frequency to screen coordinates (the length of
		// the frequency as pixels on the screen)
		double freqY = view.toScreenSizeY(frequency);

		// If the frequency is less then the visible threshold then we
		// can't draw any more lines...
		if (freqY <= endFrequency)
			return;

		// Recursively draw the next smallest frequency until the visible
		// threshold is reached.
		draw(gr, frequency / interval, max - 1);

		// Determine the normalized distance this frequency is away from
		// the most visible frequency.
		double deltaY = 1.0 - (freqY / startFrequency);

		// Calculate the additional offset the bottom padding causes
		double bottomPadding =  view.toWorldSizeY(padding.bottom);

		// Calculate the line number of the line closest to the bottom side
		// of the view port. This is used to calculate the y-coordinate to
		// start drawing as well as knowing which lines to skip.
		int lineNumberY = (int)Math.floor((view.getBottom() - offset - bottomPadding) / frequency);

		// Calculate the y-coordinate to start drawing the first line in
		// view port space.
		double startY = lineNumberY * frequency + offset;

		// Convert the y-coordinate to screen space.
		double actualY = view.toScreenY(startY);

		// The offset for any dashes in a line.
		double dashOffsetY = Math.abs(view.toScreenSizeX(view.getLeft()));

		// Set and select the outline based on the visibility of this frequency.
		outline.setDelta(deltaY, dashOffsetY);
		outline.select(gr);

		// Set the line's constant values. 
		line.x1 = padding.left;
		line.x2 = destination.getCanvasWidth() - padding.right;

		// Continuously draw lines starting from the bottom towards the top
		// until we've come to the top padding.
		while (actualY >= padding.top)
		{
			// If this is the base frequency (all lines drawn) or the line
			// is not a skipped line...
			if (isBaseFrequency || lineNumberY % interval != 0)
			{
				line.y1 = line.y2 = actualY;
				gr.draw(line);
			}

			actualY -= freqY;
			lineNumberY++;
		}
	}

}
