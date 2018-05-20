package com.philsprojects.chart.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.definitions.DefinitionPie3D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillCyclic;
import com.philsprojects.chart.fills.FillRadial;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;
import com.philsprojects.chart.settings.SettingsPie3D;
import com.philsprojects.chart.settings.SettingsPie2D.Order;
import com.philsprojects.chart.settings.SettingsPie2D.RenderType;


public class PainterPie3D 
{

	/**
	 * Creates a DataListDefinition that has a vertical glare across a 3D Pie Chart.
	 * 
	 * @param name => The name of the create definition.
	 * @param color => The color of the pie slice.
	 * @param alpha => The alpha of the pie slice.
	 * @param outline => The width of the outline stroke in pixels.
	 */
	public static DefinitionPie3D createGlareDefinition(Color color, int alpha, float outline)
	{
		DefinitionPie3D d = new DefinitionPie3D();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int lr = (int)((255 - r) * 0.6 + r);
		int lg = (int)((255 - g) * 0.6 + g);
		int lb = (int)((255 - b) * 0.6 + b);
		int dr = (int)(r * 0.75);
		int dg = (int)(g * 0.75);
		int db = (int)(b * 0.75);
		int mr = (lr + dr) >> 1;
		int mg = (lg + dg) >> 1;
		int mb = (lb + db) >> 1;
		int sr = (int)(r * 0.25);
		int sg = (int)(g * 0.25);
		int sb = (int)(b * 0.25);
		int inner = (int)((255 - alpha) * 0.3 + alpha);
		int outer = (int)((255 - alpha) * 0.6 + alpha);

		d.setSliceFill(new FillCyclic(new Color(lr, lg, lb, inner), new Color(dr, dg, db, outer)));
		d.setArcFill(new FillCyclic(new Color(mr, mg, mb, alpha),new Color(sr, sg, sb, alpha)));
		d.setSideFill(d.getArcFill());
		d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

		return d;
	}


	/**
	 * Creates a DataListDefinition that has a radial glare in the center of the 3D Pie Chart.
	 * 
	 * @param name => The name of the create definition.
	 * @param color => The color of the pie slice.
	 * @param alpha => The alpha of the pie slice.
	 * @param outline => The width of the outline stroke in pixels.
	 */
	public static DefinitionPie3D createRadialDefinition(Color color, int alpha, float outline)
	{
		DefinitionPie3D d = new DefinitionPie3D();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int lr = (int)((255 - r) * 0.6 + r);
		int lg = (int)((255 - g) * 0.6 + g);
		int lb = (int)((255 - b) * 0.6 + b);
		int dr = (int)(r * 0.75);
		int dg = (int)(g * 0.75);
		int db = (int)(b * 0.75);
		int sr = (int)(r * 0.25);
		int sg = (int)(g * 0.25);
		int sb = (int)(b * 0.25);
		int inner = (int)((255 - alpha) * 0.3 + alpha);
		int outer = (int)((255 - alpha) * 0.6 + alpha);

		d.setSliceFill(new FillRadial(new Color(lr, lg, lb, inner), new Color(dr, dg, db, outer)));
		d.setArcFill(new FillCyclic(new Color(dr, dg, db, alpha), new Color(sr, sg, sb, alpha)));
		d.setSideFill(d.getArcFill());
		d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

		return d;
	}

	/**
	 * Creates a DataListDefinition that has a solid color on a 3D Pie Chart.
	 * 
	 * @param name => The name of the create definition.
	 * @param color => The color of the pie slice.
	 * @param alpha => The alpha of the pie slice.
	 * @param outline => The width of the outline stroke in pixels.
	 */
	public static DefinitionPie3D createSolidDefinition(Color color, int alpha, float outline)
	{
		DefinitionPie3D d = new DefinitionPie3D();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int dr = (int)(r * 0.5);
		int dg = (int)(g * 0.5);
		int db = (int)(b * 0.5);
		int mr = (int)(r * 0.75);
		int mg = (int)(g * 0.75);
		int mb = (int)(b * 0.75);
		int side = (int)((255 - alpha) * 0.1 + alpha);

		d.setSliceFill(new FillSolid(new Color(r, g, b, alpha)));
		d.setArcFill(new FillSolid(new Color(mr, mg, mb, side)));
		d.setSideFill(new FillSolid(new Color(dr, dg, db, side)));
		d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

		return d;
	}


	// The current data set in the list.
	private int index = 0;

	// The number of lists for the associated plot.
	private final int lists;

	//    // The maximum number of pieces needed to draw the pie. This number
	//    // is 2 more then the number of lists to take into consideration
	//    // slices split on the x-axis.
	//    private final int maxSlices;

	// The data set containing.
	private final Datatable datatable;

	// The physical settings for the 3D pie. 
	private final SettingsPie3D settings;

	// The visual settings for the 3D pie.
	private final DefinitionPie3D[] definitions;

	// The slices of the current data set.
	private PieSlice[] slices;

	// The values of the current data set.
	private double[] values;

	// The ratio between converting values into pie slice sweep angles.
	private double valueToDegrees;


	/**
	 * 
	 * @param settings
	 * @param datatable
	 */
	public PainterPie3D(SettingsPie3D settings, DefinitionPie3D[] definitions, Datatable datatable)
	{
		this.settings = settings;
		this.definitions = definitions;
		this.datatable = datatable;
		this.lists = datatable.getListCount();
		this.slices = new PieSlice[lists];

		// Create the slices and their initial order.
		for (int i = 0; i < lists; i++)
		{
			slices[i] = new PieSlice();
		}
	}

	/**
	 * 
	 * @param index
	 */
	public void setIndex(int index)
	{
		this.index = index;

		// Grab the floating-point values from the data set
		// based on the index of the set to draw.
		values = getValues();

		// Calculate the valueToDegrees ratio which converts
		// a value to its respective pie slice sweep.
		valueToDegrees = valueToDegrees();

		updateDirection();
	}

	private void updateDirection()
	{
		// Create the pie slices based on the direction of the slices. The
		// first slice will always be the same, set the rest accordingly.
		slices[0].reset(values[0] * valueToDegrees, definitions[0]);

		Order o = settings.getOrder();
		int j = 1;
		for (int i = 1; i < lists; i++)
		{
			j = o.getIndex(i, lists);

			slices[i].reset(values[j] * valueToDegrees, definitions[j]);
		}
	}

	/**
	 * 
	 * @param gr
	 */
	public void draw(Graphics2D gr)
	{
		double cx = settings.getX();
		double cy = settings.getY();
		double width = settings.getWidth();
		double height = settings.getHeight();
		double depth = settings.getDepth();
		double stagger = settings.getStaggerOffset();
		double staggerRatio = stagger / 180.0;
		double yaw = settings.getYaw();

		// Set the bounds of the pie based on the center of the pie
		// taking into consideration any stagger offset between slices.
		Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, width, height);
		bounds.x = cx - (width * 0.5);
		bounds.y = cy - (height * 0.5) - (stagger * 0.5);

		// Clear all slices before any splitting is done.
		updateDirection();


		int total = 0;
		PieSegment[] segments = new PieSegment[lists + 2];

		double currSweep, nextSweep;
		for (int i = 0; i < lists; i++)
		{
			slices[i].setAngle(yaw);
			slices[i].setHeight(depth);
			slices[i].setHeightOffset(slices[i].getAngleBetween90() * staggerRatio);

			PieSegment[] split = slices[i].split(bounds);
			for (int j = 0; j < split.length; j++)
				(segments[total++] = split[j]).updateDistance();

			currSweep = slices[i].sweep;
			nextSweep = slices[(i + 1) % lists].sweep;
			yaw = wrapAngle(yaw + ((currSweep + nextSweep) * 0.5));
		}

		sortSegments(segments, total);

		for (int i = 0; i < total; i++)
			segments[i].draw(gr);


		//	// Set the slices based on their new orientation
		//	double currSweep, nextSweep;
		//	for (int i = 0; i < lists; i++)
		//	{
		//	slices[i].setAngle(yaw);
		//	slices[i].setHeight(depth);
		//	slices[i].setHeightOffset(slices[i].getAngleBetween90() * staggerRatio);

		//	// If the slice is on the x-axis then split it by giving
		//	// it the piece to set as the lower half. 
		//	if (slices[i].isOn90() && splits != 1)
		//	{
		//	slices[i].split(slices[lists]);
		//	splits = 1;
		//	}

		//	currSweep = slices[i].sweep;
		//	nextSweep = slices[(i + 1) % lists].sweep;
		//	yaw = wrapAngle(yaw + ((currSweep + nextSweep) * 0.5));
		//	}

		//	// Calculate how many total slices are going to be drawn.
		//	int total = splits + lists;

		//	// Reset the order of the split pieces always.
		//	order[lists] = lists;

		//	// Sort the drawing order of slices based on their distances from 
		//	// the 90 degrees mark.
		//	updateOrder(total);

		//	// Draw the pie slices in the sorted order.
		//	for (int i = 0; i < total; i++)
		//	slices[order[i]].draw(gr, bounds);
	}


	private void sortSegments(PieSegment[] s, int total)
	{
		// Perform an Insertion sort on the array of slices.
		int j;
		PieSegment segment;
		for (int i = 1; i < total; i++)
		{
			j = i;
			segment = s[i];

			while ((j > 0) && (s[j - 1].distance > segment.distance))
				s[j] = s[--j];

			s[j] = segment;
		}
	}

	//  /**
	//  * 
	//  */
	//  private void updateOrder(int n)
	//  {
	//  // Perform an Insertion sort on the array of slices.
	//  int j, k;
	//  for (int i = 1; i < n; i++)
	//  {
	//  j = i;
	//  k = order[i];

	//  while ((j > 0) && (slices[order[j - 1]].minAngleFrom90 > slices[k].minAngleFrom90))
	//  order[j] = order[--j];

	//  order[j] = k;
	//  }
	//  }

	/**
	 * 
	 * @param angle
	 * @return
	 */
	private double wrapAngle(double angle)
	{
		while (angle >= 360) angle -= 360;
		while (angle < 0) angle += 360;
		return angle;
	}

	/**
	 * 
	 * @return
	 */
	private double[] getValues()
	{
		values = new double[lists];

		for (int i = 0; i < lists; i++)
			values[i] = datatable.get(index).getData(i, 0);

		return values;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	private double valueToDegrees()
	{
		double total = 0.0;

		for (int i = 0; i < lists; i++)
			total += values[i];

		return (total == 0.0 ? 0.0 : (1.0 / total * 360));
	}

	private class PieSegment
	{
		final DefinitionPie3D def;
		final Rectangle2D.Double bounds;
		final double depth;
//		final double depthOffset;
		double startAngle;
		double endAngle;
		double distance;
		boolean hasStartSide;
		boolean hasEndSide;

		public PieSegment(PieSlice slice, Rectangle2D.Double adjusted)
		{
			def = slice.def;
			depth = slice.depth;
//			depthOffset = slice.depthOffset;
			startAngle = slice.startAngle;
			endAngle = slice.endAngle;
			hasStartSide = true;
			hasEndSide = true;
			bounds = new Rectangle2D.Double();
			bounds.setFrame(adjusted);
		}

		public void draw(Graphics2D gr)
		{
			Fill top = def.getSliceFill();
			top.setShape(bounds);

			// Setup arc for slice.
			Arc2D.Double arc = new Arc2D.Double(Arc2D.PIE);
			arc.setFrame(bounds);
			arc.start = startAngle;
			arc.extent = (endAngle - startAngle);

			// Adjust the arc and bounds for the bottom
			arc.y += depth;
			bounds.y += depth;

			// Draw pie segment bottom.
			drawSlice(gr, arc, bounds);

			// Re-adjust the arc and bounds for the top.
			arc.y -= depth;
			bounds.y -= depth;

			// If the segment is on the top side then draw the arc first 
			// followed by the sides.
			if (distance <= 90)
			{
				drawArc(gr, bounds, startAngle, endAngle);
				if (endAngle > 90 && endAngle < 270)
				{
					if (hasStartSide)
						drawSide(gr, bounds, startAngle);
					if (hasEndSide)
						drawSide(gr, bounds, endAngle);
				}
				else
				{
					if (hasEndSide)
						drawSide(gr, bounds, endAngle);
					if (hasStartSide)
						drawSide(gr, bounds, startAngle);
				}
			}
			else
			{
				if (endAngle > 90 && endAngle < 270)
				{
					if (hasStartSide)
						drawSide(gr, bounds, startAngle);
					if (hasEndSide)
						drawSide(gr, bounds, endAngle);
				}
				else
				{
					if (hasEndSide)
						drawSide(gr, bounds, endAngle);
					if (hasStartSide)
						drawSide(gr, bounds, startAngle);
				}
				drawArc(gr, bounds, startAngle, endAngle);
			}

			drawSlice(gr, arc, bounds);

			if (hasStartSide)
				drawSideTop(gr, bounds, startAngle);
			if (hasEndSide)
				drawSideTop(gr, bounds, endAngle);

			drawArcTop(gr, bounds, startAngle, endAngle);
		}

		private void drawSlice(Graphics2D gr, Arc2D arc, Rectangle2D bounds)
		{
			Fill sliceFill = def.getSliceFill();
			if (sliceFill != null)
			{
				sliceFill.setShape(bounds);
				sliceFill.select(gr);

				// If the segment is NOT on the x-axis then don't change the
				// rendering hints from antialiasing.
				if (hasEndSide && hasStartSide)
				{
					gr.fill(arc);
				}
				else
				{
					Object oldValue = gr.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
					gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

					gr.fill(arc);

					gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldValue);
				}
			}
		}

		private void drawArc(Graphics2D gr, Rectangle2D bounds, double start, double end)
		{
			GeneralPath path = new GeneralPath();
			Arc2D.Double side = new Arc2D.Double(Arc2D.OPEN);
			side.setFrame(bounds);
			double extent = end - start;

			side.start = start;
			side.extent = extent;
			path.append(side, false);

			side.start = end;
			side.extent = -extent;
			side.y += depth;
			path.append(side, true);

			path.closePath();

			Fill arcFill = def.getArcFill();
			if (arcFill != null)
			{
				arcFill.setShape(bounds);
				arcFill.select(gr);
				gr.fill(path);
			}

			Outline outline = def.getSliceOutline();
			if (outline != null)
			{
				outline.setShape(bounds);
				outline.select(gr);
				gr.draw(path);
			}
		}

		private void drawArcTop(Graphics2D gr, Rectangle2D bounds, double start, double end)
		{
			Arc2D.Double side = new Arc2D.Double(Arc2D.OPEN);
			side.setFrame(bounds);
			double extent = end - start;

			side.start = start;
			side.extent = extent;

			Outline outline = def.getSliceOutline();
			if (outline != null)
			{
				outline.setShape(bounds);
				outline.select(gr);
				gr.draw(side);
			}
		}

		private void drawSide(Graphics2D gr, Rectangle2D bounds, double sideAngle)
		{
			double cx = bounds.getCenterX();
			double cy = bounds.getCenterY();
			double by = cy + depth;
			double rx = bounds.getWidth() * 0.5;
			double ry = bounds.getHeight() * 0.5;
			double theta = Math.toRadians(360 - sideAngle);
			double rcos = Math.cos(theta) * rx;
			double rsin = Math.sin(theta) * ry;

			GeneralPath path = new GeneralPath();

			path.moveTo(cx, cy);
			path.lineTo(cx + rcos, cy + rsin);
			path.lineTo(cx + rcos, by + rsin);
			path.lineTo(cx, by);
			path.closePath();


			Fill sideFill = def.getSideFill();
			if (sideFill != null)
			{
				sideFill.setShape(bounds);
				sideFill.select(gr);
				gr.fill(path);
			}

			Outline outline = def.getSliceOutline();
			if (outline != null)
			{
				outline.setShape(bounds);
				outline.select(gr);
				gr.draw(path);
			}
		}

		private void drawSideTop(Graphics2D gr, Rectangle2D bounds, double sideAngle)
		{
			double cx = bounds.getCenterX();
			double cy = bounds.getCenterY();
			double rx = bounds.getWidth() * 0.5;
			double ry = bounds.getHeight() * 0.5;
			double theta = Math.toRadians(sideAngle);
			double rcos = Math.cos(theta) * rx;
			double rsin = -Math.sin(theta) * ry;


			Outline outline = def.getSliceOutline();
			if (outline != null)
			{
				outline.setShape(bounds);
				outline.select(gr);
				gr.draw(new Line2D.Double(cx, cy, cx + rcos, cy + rsin));
			}
		}

		private void updateDistance()
		{
			distance = getAngleBetween(startAngle, endAngle);
		}

		public double getAngleBetween(double start, double end)
		{
			double startDistance = (start <= 90 ? (90 - start) : (360 - start + 90));
			double endDistance = (end >= 90 ? (end - 90) : (270 + end));

			return Math.min(endDistance, startDistance);
		}
	}

	private class PieSlice
	{
		DefinitionPie3D def;
		double sweep;

		double depth;
		double depthOffset;

		double angle;
		double startAngle;
		double endAngle;

		double angleBetween90;
		boolean onLeftAxis;
		boolean onRightAxis;

		public PieSlice()
		{
		}

		public PieSegment[] split(Rectangle2D.Double bounds)
		{
			// Calulate the adjusted bounds based on any separation.
			Rectangle2D.Double adjusted = calculateBounds(bounds);

			PieSegment[] segments = null;
			// Requires three segments to draw the single slice.
			if (onLeftAxis && onRightAxis)
			{
				// If the larger portion is up in the top quadrant...
				if (angle >= 0 && angle <= 180)
				{
					// Middle segment
					PieSegment s1 = new PieSegment(this, adjusted);
					s1.endAngle = 180;
					s1.startAngle = 0;
					s1.hasEndSide = false;
					s1.hasStartSide = false;

					// Left segment
					PieSegment s2 = new PieSegment(this, adjusted);
					s2.startAngle = 180;
					s2.hasStartSide = false;

					// Right segment
					PieSegment s3 = new PieSegment(this, adjusted);
					s3.endAngle = (startAngle > 0 ? 360 : 0);
					s3.hasEndSide = false;

					segments = new PieSegment[] {s1, s2, s3};
				}
				else
				{
					// Middle segment
					PieSegment s1 = new PieSegment(this, adjusted);
					s1.endAngle = 360;
					s1.startAngle = 180;
					s1.hasEndSide = false;
					s1.hasStartSide = false;

					// Left segment
					PieSegment s2 = new PieSegment(this, adjusted);
					s2.endAngle = 180;
					s2.hasEndSide = false;

					// Right segment
					PieSegment s3 = new PieSegment(this, adjusted);
					s3.startAngle = 360;
					s3.hasStartSide = false;

					segments = new PieSegment[] {s1, s2, s3};
				}
			}
			// Requires two segments, one above and one below the left x-axis.
			else if (onLeftAxis)
			{
				// Upper segment
				PieSegment s1 = new PieSegment(this, adjusted);
				s1.endAngle = 180;
				s1.hasEndSide = false;

				// Lower segment
				PieSegment s2 = new PieSegment(this, adjusted);
				s2.startAngle = 180;
				s2.hasStartSide = false;

				segments = new PieSegment[] {s1, s2};
			}
			// Requires two segments, one above and one below the left x-axis.
			else if (onRightAxis)
			{
				// Upper segment
				PieSegment s1 = new PieSegment(this, adjusted);
				s1.startAngle = (endAngle > 360 ? 360 : 0);
				s1.hasStartSide = false;

				// Lower segment
				PieSegment s2 = new PieSegment(this, adjusted);
				s2.endAngle = (startAngle > 0 ? 360 : 0);
				s2.hasEndSide = false;

				segments = new PieSegment[] {s1, s2};
			}
			// Requires no split!
			else
			{
				PieSegment s = new PieSegment(this, adjusted);
				segments = new PieSegment[] {s};
			}

			return segments;
		}

		public Rectangle2D.Double calculateBounds(Rectangle2D base)
		{
			double dist = settings.getDistanceFromCenter(sweep);
			double sepX = settings.getSeparationX(angle, dist); 
			double sepY = settings.getSeparationY(angle, dist);
			double offY = settings.getOffsetY(dist);

			Rectangle2D.Double adjusted = new Rectangle2D.Double();
			adjusted.setFrame(base);
			adjusted.x += sepX;
			adjusted.y += depthOffset + sepY;

			// Only if the pie type is CutOff should the adjusted
			// bounding rectangle be resized.
			if (settings.getRenderType() == RenderType.CutOff)
			{
				adjusted.x += dist;
				adjusted.y += offY;
				adjusted.width -= dist * 2.0;
				adjusted.height -= offY * 2.0;
			}

			return adjusted;
		}

		public void setAngle(double angle)
		{
			// Set the new central angle of the pie slice.
			this.angle = angle;

			// Update the start angle (startAngle < angle) based on the sweep
			startAngle = angle - (sweep * 0.5);

			// Update the end angle (endAngle > angle) based on the sweep.
			endAngle = angle + (sweep * 0.5);

			// Determine if the pie slice overlaps the left x-axis.
			onLeftAxis = (startAngle < 180 && endAngle > 180);

			// Determine if the pie slice overlaps the right x-axis.
			onRightAxis = ((startAngle < 0 && endAngle > 0) || (endAngle > 360 && startAngle < 360));

			// Determine the angle between the pies central angle and 90 degrees
			angleBetween90 = getAngleBetween(angle, angle);
		}

		public double getAngleBetween(double start, double end)
		{
			double startDistance = (start <= 90 ? (90 - start) : (360 - start + 90));
			double endDistance = (end >= 90 ? (end - 90) : (270 + end));

			return Math.min(endDistance, startDistance);
		}

		public void reset(double sweep, DefinitionPie3D def)
		{
			this.sweep = sweep;
			this.def = def;
		}

		public void setHeight(double height)
		{
			this.depth = height;
		}

		public void setHeightOffset(double heightOffset)
		{
			this.depthOffset = heightOffset;
		}

		public double getAngleBetween90()
		{
			return angleBetween90;
		}

	}

	/**
	 * @return the definitions
	 */
	public DefinitionPie3D[] getDefinitions()
	{
		return definitions;
	}

}
