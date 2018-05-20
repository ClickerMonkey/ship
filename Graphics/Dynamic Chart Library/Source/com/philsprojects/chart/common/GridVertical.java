package com.philsprojects.chart.common;

import java.awt.Graphics2D;

import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;

public class GridVertical extends Grid
{

	public GridVertical()
	{

	}

	public GridVertical(Viewport view, Canvas destination)
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
		double freqX = view.toScreenSizeX(frequency);

		// If the frequency is less then the visible threshold then we
		// can't draw any more lines...
		if (freqX <= endFrequency)
			return;

		// Recursively draw the next smallest frequency until the visible
		// threshold is reached.
		draw(gr, frequency / interval, max - 1);

		// Determine the normalized distance this frequency is away from
		// the most visible frequency.
		double deltaX = 1.0 - (freqX / startFrequency);

		// Calculate the additional offset the right padding causes
		double rightPadding =  view.toWorldSizeX(padding.right);

		// Calculate the line number of the line closest to the right side
		// of the view port. This is used to calculate the x-coordinate to
		// start drawing as well as knowing which lines to skip.
		int lineNumberX = (int)Math.floor((view.getRight() - offset - rightPadding) / frequency);

		// Calculate the x-coordinate to start drawing the first line in
		// view port space.
		double startX = lineNumberX * frequency + offset;

		// Convert the x-coordinate to screen space.
		double actualX = view.toScreenX(startX);

		// The offset for any dashes in a line.
		double dashOffsetX = Math.abs(view.toScreenSizeX(view.getBottom()));

		// Set and select the outline based on the visibility of this frequency.
		outline.setDelta(deltaX, dashOffsetX);
		outline.select(gr);
		
		// Set the line's constant values.
		line.y1 = padding.top;
		line.y2 = (destination.getCanvasHeight() - padding.bottom);

		// Continuously draw lines starting from the right towards the left
		// until we've come to the left padding.
		while (actualX >= padding.left)
		{
			// If this is the base frequency (all lines drawn) or the line
			// is not a skipped line...
			if (isBaseFrequency || lineNumberX % interval != 0)
			{
				line.x1 = line.x2 = actualX;
				gr.draw(line);
			}

			actualX -= freqX;
			lineNumberX--;
		}
	}

}
