package com.philsprojects.chart.publish;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class Screenshot implements Printable
{

    public enum Type
    {
	Png("png", BufferedImage.TYPE_4BYTE_ABGR),
	Jpeg("jpg", BufferedImage.TYPE_BYTE_INDEXED),
	Bitmap("bmp", BufferedImage.TYPE_3BYTE_BGR),
	Monochrome("bmp", BufferedImage.TYPE_BYTE_BINARY);

	public final String extension;
	public final int imageType;

	private Type(String extension, int imageType) {
	    this.extension = extension;
	    this.imageType = imageType;
	}
    }

    protected Type type;
    protected BufferedImage image;
    protected Component source;

    public Screenshot(Component component, Type type)
    {
	int width = component.getWidth();
	int height = component.getHeight();

	this.image = new BufferedImage(width, height, type.imageType);
	this.type = type;
	this.source = component;

	Graphics g = image.getGraphics();
	component.paint(g);
	g.dispose();
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
    {
	// There's only one page to print.
	if (pageIndex != 0)
	    return NO_SUCH_PAGE;

	// The dimensions and offsets of the page.
	double pageX = pageFormat.getImageableX();
	double pageY = pageFormat.getImageableY();
	double pageW = pageFormat.getImageableWidth();
	double pageH = pageFormat.getImageableHeight();

//	System.out.format("Page {%.1f, %.1f, %.1f, %.1f}\n", pageX, pageY, pageW, pageH);

	double shotW = image.getWidth();
	double shotH = image.getHeight();

//	System.out.format("Shot {%.1f x %.1f}\n", shotW, shotH);

	// Shrink the width of the screen shot maintaining the correct ratio
	// if the screen shot cannot fit on the page.
	if (shotW > pageW)
	{
	    double shrinkX = shotW - pageW;
	    double deltaX = shrinkX / shotW;
	    shotH -= shotH * deltaX;
	    shotW -= shotW * deltaX;

//	    System.out.format("Shot {%.1f x %.1f}\n", shotW, shotH);
	}

	// Shrink the height of the screen shot maintaining the correct ratio
	// if the screen shot cannot fit on the page.
	if (shotH > pageH)
	{
	    double shrinkY = shotH - pageH;
	    double deltaY = shrinkY / shotH;
	    shotH -= shotH * deltaY;
	    shotW -= shotW * deltaY;

//	    System.out.format("Shot {%.1f x %.1f}\n", shotW, shotH);
	}

	// Determine the upper left corner
	double shotX = (pageW - shotW) * 0.5;
	double shotY = (pageH - shotH) * 0.5;

//	System.out.format("Shot {%.1f, %.1f}\n", shotX, shotY);

//	// Round the bounds of the shot on the page down to pixels.
//	int imageX = (int)(pageX + shotX);
//	int imageY = (int)(pageY + shotY);
//	int imageW = (int)shotW;
//	int imageH = (int)shotH;
//
//	System.out.format("Image {%d, %d, %d, %d}\n", imageX, imageY, imageW, imageH);
//	
//	graphics.drawImage(image, imageX, imageY, imageW, imageH, null);

	// Create the graphics2D object and create settings for nice drawing.
	Graphics2D gr = (Graphics2D)graphics;
	
	gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	
	// Draw the image on the graphics object.
	AffineTransform transform = new AffineTransform();
	transform.scale(shotW / image.getWidth(), shotH / image.getHeight());
	transform.translate(pageX + shotX, pageY + shotY);
	
	gr.drawImage(image, transform, null);

	return PAGE_EXISTS;
    }

}
