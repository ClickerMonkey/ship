package com.philsprojects.chart.common;

import java.awt.Rectangle;
import java.awt.Shape;

import com.philsprojects.chart.view.Canvas;

public abstract class Component extends Canvas
{

	protected Anchor anchor = Anchor.Default;
	protected Padding padding = Padding.Default;

	public Component(boolean antialiasing, Rectangle bounds)
	{
		super(antialiasing, bounds);
	}

	public Component(boolean antialiasing, int offsetX, int offsetY, int width, int height)
	{
		super(antialiasing, offsetX, offsetY, width, height);
	}

	public Component(boolean antialiasing, Shape clipping)
	{
		super(antialiasing, clipping);
	}

	public void setAnchor(Anchor anchor)
	{
		this.anchor = anchor;
		requestRedraw();
	}

	public void setPadding(Padding padding)
	{
		this.padding.set(padding);
		requestRedraw();
	}

	public void setPaddingLeft(float left)
	{
		this.padding.left = left;
		requestRedraw();
	}

	public void setPaddingRight(float right)
	{
		this.padding.right = right;
		requestRedraw();
	}

	public void setPaddingTop(float top)
	{
		this.padding.top = top;
		requestRedraw();
	}

	public void setPaddingBottom(float bottom)
	{
		this.padding.bottom = bottom;
		requestRedraw();
	}

	public Anchor getAnchor()
	{
		return anchor;
	}

	public float getPaddingLeft()
	{
		return padding.left;
	}

	public float getPaddingRight()
	{
		return padding.right;
	}

	public float getPaddingTop()
	{
		return padding.top;
	}

	public float getPaddingBottom()
	{
		return padding.bottom;
	}

}
