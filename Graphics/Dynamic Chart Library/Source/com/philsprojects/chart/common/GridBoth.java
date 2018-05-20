package com.philsprojects.chart.common;

import java.awt.Graphics2D;

import com.philsprojects.chart.outlines.Outline;

public class GridBoth extends Grid
{

	protected float offsetX;
	protected float offsetY;
	protected float frequencyX;
	protected float frequencyY;
	protected float startFrequencyX;
	protected float startFrequencyY;
	protected float endFrequencyX;
	protected float endFrequencyY;
	protected int intervalX;
	protected int intervalY;
	protected Outline outlineX;
	protected Outline outlineY;
	
	
	@Override
	public void draw(Graphics2D gr)
	{
		draw(gr, frequencyX, frequencyY, 10);
	}

	public void draw(Graphics2D gr, double frequencyX, double frequencyY, int max)
	{
		if (max <= 0) {
			return;
		}
		
		// Determine if this is the base frequency, if it is then do not
		// skip any lines.
		boolean isBaseFrequency = (frequencyX == this.frequencyX);

		// Convert the given frequency to screen coordinates (the length of
		// the frequency as pixels on the screen)
		double freqY = view.toScreenSizeY(frequencyX);
		double freqX = view.toScreenSizeX(frequencyY);

		// If the frequency is less then the visible threshold then we
		// can't draw any more lines...
		if (freqY <= endFrequencyY && freqX <= endFrequencyX)
			return;

		// Recursively draw the next smallest frequency until the visible
		// threshold is reached.
		draw(gr, frequencyX / intervalX, frequencyY / intervalY, max - 1);

		// Only draw the frequency if it's visible.
		if (freqY > endFrequencyY)
		{
			// Determine the normalized distance this frequency is away from
			// the most visible frequency.
			double deltaY = 1.0 - (freqY / startFrequencyY);

			// Calculate the additional offset the bottom padding causes
			double bottomPadding =  view.toWorldSizeY(padding.bottom);

			// Calculate the line number of the line closest to the bottom side
			// of the view port. This is used to calculate the y-coordinate to
			// start drawing as well as knowing which lines to skip.
			int lineNumberY = (int)Math.floor((view.getBottom() - offsetY - bottomPadding) / frequencyY);

			// Calculate the y-coordinate to start drawing the first line in
			// view port space.
			double startY = lineNumberY * frequencyY + offsetY;

			// Convert the y-coordinate to screen space.
			double actualY = view.toScreenY(startY);

			// The offset for any dashes in a line.
			double dashOffsetY = Math.abs(view.toScreenSizeX(view.getRight()));

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

		// Only draw the frequency if it's visible.
		if (freqX > endFrequencyX)
		{
			// Determine the normalized distance this frequency is away from
			// the most visible frequency.
			double deltaX = 1.0 - (freqX / startFrequencyX);

			// Calculate the additional offset the right padding causes
			double rightPadding =  view.toWorldSizeX(padding.right);

			// Calculate the line number of the line closest to the right side
			// of the view port. This is used to calculate the x-coordinate to
			// start drawing as well as knowing which lines to skip.
			int lineNumberX = (int)Math.floor((view.getRight() - offsetX - rightPadding) / frequencyX);

			// Calculate the x-coordinate to start drawing the first line in
			// view port space.
			double startX = lineNumberX * frequencyX + offsetX;

			// Convert the x-coordinate to screen space.
			double actualX = view.toScreenX(startX);

			// The offset for any dashes in a line.
			double dashOffsetX = Math.abs(view.toScreenSizeX(view.getBottom()));

			// Set and select the outline based on the visibility of this frequency.
			outline.setDelta(deltaX, dashOffsetX);
			outline.select(gr);
			
			// Set the line's constant values.
			line.y1 = padding.top;
			line.y2 = destination.getCanvasHeight() - padding.bottom;

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


	/**
	 * @param offset the offset to set
	 */
	public void setOffset(float offset)
	{
		this.offset = offset;
		this.offsetX = offset;
		this.offsetY = offset;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(float frequency)
	{
		this.frequency = frequency;
		this.frequencyX = frequency;
		this.frequencyY = frequency;
	}


	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval)
	{
		this.interval = interval;
		this.intervalX = interval;
		this.intervalY = interval;
	}


	/**
	 * @param startFrequency the startFrequency to set
	 */
	public void setStartFrequency(float startFrequency)
	{
		this.startFrequency = startFrequency;
		this.startFrequencyX = startFrequency;
		this.startFrequencyY = startFrequency;
	}


	/**
	 * @param endFrequency the endFrequency to set
	 */
	public void setEndFrequency(float endFrequency)
	{
		this.endFrequency = endFrequency;
		this.endFrequencyX = endFrequency;
		this.endFrequencyY = endFrequency;
	}
	
	/**
	 * @param offsetX the offsetX to set
	 */
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @param offsetY the offsetY to set
	 */
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @param frequencyX the frequencyX to set
	 */
	public void setFrequencyX(float frequencyX) {
		this.frequencyX = frequencyX;
	}

	/**
	 * @param frequencyY the frequencyY to set
	 */
	public void setFrequencyY(float frequencyY) {
		this.frequencyY = frequencyY;
	}

	/**
	 * @param startFrequencyX the startFrequencyX to set
	 */
	public void setStartFrequencyX(float startFrequencyX) {
		this.startFrequencyX = startFrequencyX;
	}

	/**
	 * @param startFrequencyY the startFrequencyY to set
	 */
	public void setStartFrequencyY(float startFrequencyY) {
		this.startFrequencyY = startFrequencyY;
	}

	/**
	 * @param endFrequencyX the endFrequencyX to set
	 */
	public void setEndFrequencyX(float endFrequencyX) {
		this.endFrequencyX = endFrequencyX;
	}

	/**
	 * @param endFrequencyY the endFrequencyY to set
	 */
	public void setEndFrequencyY(float endFrequencyY) {
		this.endFrequencyY = endFrequencyY;
	}

	/**
	 * @param intervalX the intervalX to set
	 */
	public void setIntervalX(int intervalX) {
		this.intervalX = intervalX;
	}

	/**
	 * @param intervalY the intervalY to set
	 */
	public void setIntervalY(int intervalY) {
		this.intervalY = intervalY;
	}

	/**
	 * @param outlineX the outlineX to set
	 */
	public void setOutlineX(Outline outlineX) {
		this.outlineX = outlineX;
	}

	/**
	 * @param outlineY the outlineY to set
	 */
	public void setOutlineY(Outline outlineY) {
		this.outlineY = outlineY;
	}

	/**
	 * @return the offsetX
	 */
	public float getOffsetX() {
		return offsetX;
	}

	/**
	 * @return the offsetY
	 */
	public float getOffsetY() {
		return offsetY;
	}

	/**
	 * @return the frequencyX
	 */
	public float getFrequencyX() {
		return frequencyX;
	}

	/**
	 * @return the frequencyY
	 */
	public float getFrequencyY() {
		return frequencyY;
	}

	/**
	 * @return the startFrequencyX
	 */
	public float getStartFrequencyX() {
		return startFrequencyX;
	}

	/**
	 * @return the startFrequencyY
	 */
	public float getStartFrequencyY() {
		return startFrequencyY;
	}

	/**
	 * @return the endFrequencyX
	 */
	public float getEndFrequencyX() {
		return endFrequencyX;
	}

	/**
	 * @return the endFrequencyY
	 */
	public float getEndFrequencyY() {
		return endFrequencyY;
	}

	/**
	 * @return the intervalX
	 */
	public int getIntervalX() {
		return intervalX;
	}

	/**
	 * @return the intervalY
	 */
	public int getIntervalY() {
		return intervalY;
	}

	/**
	 * @return the outlineX
	 */
	public Outline getOutlineX() {
		return outlineX;
	}

	/**
	 * @return the outlineY
	 */
	public Outline getOutlineY() {
		return outlineY;
	}

}
