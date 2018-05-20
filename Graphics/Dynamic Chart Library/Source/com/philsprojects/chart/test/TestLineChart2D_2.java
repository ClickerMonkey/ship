package com.philsprojects.chart.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.philsprojects.chart.Legend;
import com.philsprojects.chart.LegendDefinition;
import com.philsprojects.chart.Legend.Orientation;
import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.common.Axis;
import com.philsprojects.chart.common.AxisX;
import com.philsprojects.chart.common.Grid;
import com.philsprojects.chart.common.GridBoth;
import com.philsprojects.chart.common.LabelBar;
import com.philsprojects.chart.common.Padding;
import com.philsprojects.chart.common.ValueBar;
import com.philsprojects.chart.common.ValueFormat;
import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.data.DatatableDynamic;
import com.philsprojects.chart.definitions.DefinitionLine2D;
import com.philsprojects.chart.fills.FillDelta;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.icons.IconStar;
import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.painters.PainterLine2D;
import com.philsprojects.chart.publish.PrintScreenshot;
import com.philsprojects.chart.publish.SaveScreenshot;
import com.philsprojects.chart.publish.Screenshot.Type;
import com.philsprojects.chart.settings.SettingsLine2D;
import com.philsprojects.chart.settings.SettingsLine2D.RenderType;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;


public class TestLineChart2D_2
{

	public static void main(String[] args)
	{
		new TestLineChart2D_2();
	}


	final Color[] COLORS = {
			Color.gray, Color.lightGray, Color.darkGray, Color.white, 
	};

	final float LEGEND_ICON_SIZE = 20;
	final Icon[] LEGEND_ICONS = {
			new IconStar(LEGEND_ICON_SIZE, 6, 0.4, 90),
	};

	final String[] LABELS = {
			"eRX", "Relay Health", "Mail Order", "CVS", "Rite Aid", "Medicine Shoppe"
	};

	private int lists = 2;
	private int sets = 200;

	public TestLineChart2D_2()
	{
		final int PADDING_TOP = 10;
		final int PADDING_LEFT = 10;
		final int PADDING_RIGHT = 10;
		final int PADDING_BOTTOM = 10;

		final int HEIGHT = 400;
		final int WIDTH = 400;

		final int SIDE_X = PADDING_LEFT;
		final int SIDE_Y = PADDING_TOP;
		final int SIDE_WIDTH = 80;
		final int SIDE_HEIGHT = HEIGHT;

		final int PLOT_X = PADDING_LEFT + SIDE_WIDTH;
		final int PLOT_Y = PADDING_TOP;
		final int PLOT_WIDTH = WIDTH;
		final int PLOT_HEIGHT = HEIGHT;

		final int LABEL_X = PLOT_X;
		final int LABEL_Y = PADDING_TOP + PLOT_HEIGHT;
		final int LABEL_WIDTH = WIDTH;
		final int LABEL_HEIGHT = 60;

		final int LEGEND_X = PLOT_X + PLOT_WIDTH;
		final int LEGEND_Y = PADDING_TOP;
		final int LEGEND_WIDTH = 140;
		final int LEGEND_HEIGHT = HEIGHT;

		final int CHART_WIDTH = LEGEND_X + LEGEND_WIDTH + PADDING_RIGHT;
		final int CHART_HEIGHT = LABEL_Y + LABEL_HEIGHT + PADDING_BOTTOM;

		final float SPACING = 10f;


		final Font font = new Font("Arial", Font.PLAIN, 12);

		//=====================================================================
		// SETTINGS - Physical properties.
		//=====================================================================
		final SettingsLine2D settings = new SettingsLine2D();
		settings.setOffset(0f);
		settings.setSpacing(SPACING);
		settings.setType(RenderType.Clustered);

		//=====================================================================
		// GRID - Chart backdrop.
		//=====================================================================
		final Grid grid = new GridBoth();
		grid.setEndFrequency(10f);
		grid.setFrequency(SPACING * 16);
		grid.setOffset(0);
		grid.setInterval(4);
		grid.setOutline(new OutlineDelta(new Color(44, 255, 44, 255), new Color(127, 255, 44, 0), 1f, 0.5f));
		grid.setStartFrequency(160);


		//=====================================================================
		// AXIS - Chart axis'.
		//=====================================================================
		final Axis axisX = new AxisX();
		axisX.setColor(new Color(0, 255, 0));
		axisX.setStroke(new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

		//=====================================================================
		// VALUEBAR - Chart values bar.
		//=====================================================================
		final ValueBar values = new ValueBar(ValueFormat.Decimal2);
		values.setEndFrequency(0);
		values.setFrequency(SPACING * 16);
		values.setOffset(0);
		values.setInterval(4);
		values.setStartFrequency(80f);
		values.setAnchor(Anchor.CenterRight);
		values.setFont(font);
		values.setFontFill(new FillDelta(new Color(44, 255, 44, 255), new Color(44, 255, 44, 0)));
		values.setPadding(new Padding(5));

		//=====================================================================
		// LABELBAR - Chart labels bar.
		//=====================================================================
		final LabelBar labels = new LabelBar();
		labels.setInterval(4);
		labels.setLabelAngle(45f);
		labels.setFont(font);
		labels.setFontFill(new FillSolid(new Color(44, 255, 44, 255)));
		labels.setPadding(new Padding(5));

		//=====================================================================
		// DEFINITIONS - Visual properties.
		//=====================================================================
		final DefinitionLine2D[] definitions = new DefinitionLine2D[lists]; 
		for (int i = 0; i < lists; i++)
		{
			definitions[i] = PainterLine2D.createSolidDefinition(COLORS[i % COLORS.length], 255, 3f);
		}

		//=====================================================================
		// DATATABLE - Data source.
		//=====================================================================
		final Datatable table = new DatatableDynamic(lists, 1);

		for (Dataset d : generateDatasets(lists, sets))
			table.add(d);

		//=====================================================================
		// PAINTER - Draws data source given settings and definitions.
		//=====================================================================
		final PainterLine2D painter = new PainterLine2D(settings, definitions, table);

		//=====================================================================
		// PLOT - Painter canvas.
		//=====================================================================
		final Viewport plotView = new Viewport(true, PLOT_X, PLOT_Y, PLOT_WIDTH, PLOT_HEIGHT) {
			protected void onDraw(Graphics2D gr) {
				grid.draw(gr);
				axisX.draw(gr, this, this);
				painter.draw(gr);
			}
		};
		plotView.setBounds(320, -SPACING, 200, -50);
		plotView.setMaxLeft(-SPACING);
		plotView.setMaxBottom(-100);
		plotView.setMaxRight(sets * SPACING);
		plotView.setMaxTop(700);
		plotView.setBackground(new Color(32, 32, 32));

		//=====================================================================
		// VALUES CANVAS - Value bar canvas.
		//=====================================================================
		final Canvas valuesCanvas = new Canvas(true, SIDE_X, SIDE_Y, SIDE_WIDTH, SIDE_HEIGHT) {
			protected void onDraw(Graphics2D gr) {
				values.draw(gr);
			}
			protected boolean listensTo(Viewport view) {
				return (view == plotView);
			}
		};
		valuesCanvas.setBackground(Color.black);

		//=====================================================================
		// LABELS CANVAS - Label bar canvas.
		//=====================================================================
		final Canvas labelsCanvas = new Canvas(true, LABEL_X, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT) {
			protected void onDraw(Graphics2D gr) {
				labels.draw(gr);
			}
			protected boolean listensTo(Viewport view) {
				return (view == plotView);
			}
		};
		labelsCanvas.setBackground(Color.black);

		//=====================================================================
		// LEGEND - Data list names.
		//=====================================================================
		Legend legend = new Legend(true, LEGEND_X, LEGEND_Y, LEGEND_WIDTH, LEGEND_HEIGHT, lists);
		legend.setAnchor(Anchor.CenterLeft);
		legend.setIconPadding(15f);
		legend.setLabelPadding(10f);
		legend.setMinLabelHeight(30f);
		legend.setOrientation(Orientation.Vertical);
		legend.setPadding(new Padding(10));
		legend.setBackground(Color.black);

		//=====================================================================
		// LEGEND DEFINITIONS - Data list name visual properties.
		//=====================================================================
		LegendDefinition base = new LegendDefinition();
		base.setFont(new Font("Impact", Font.PLAIN, 14));
		base.setFontFill(new FillGradient(Color.white, Color.gray, FillGradient.VERTICAL));
		base.setIconOutline(new OutlineSolid(new Color(44, 255, 44), 1f));

		Color color;
		LegendDefinition def;

		for (int i = 0; i < lists; i++)
		{
			color = COLORS[i % COLORS.length];
			def = new LegendDefinition(LABELS[i % LABELS.length], base);
			def.setIconFill(new FillLinear(color.brighter(), color.darker(), FillLinear.DIAGONAL_LEFT));
			def.setIcon(LEGEND_ICONS[i % LEGEND_ICONS.length]);

			legend.setDefinition(i, def);
		}

		//=====================================================================
		// SETTINGS LISTENER - Redraws chart when settings change.
		//=====================================================================
		settings.setViewport(plotView);

		//=====================================================================
		// COMPONENT - Handles all canvas'
		//=====================================================================
		final ViewportComponent component = new ViewportComponent(CHART_WIDTH, CHART_HEIGHT);
		component.addCanvas(plotView);
		component.addCanvas(valuesCanvas);
		component.addCanvas(labelsCanvas);
		component.addCanvas(legend);
		component.setBackground(Color.black);

		component.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					settings.setType(RenderType.Clustered);
					break;
				case KeyEvent.VK_2:
					settings.setType(RenderType.Percent);
					break;
				case KeyEvent.VK_3:
					settings.setType(RenderType.Stacked);
					break;
				case KeyEvent.VK_P:
					if (e.isControlDown())
						PrintScreenshot.open(component, Type.Png);
					break;
				case KeyEvent.VK_S:
					if (e.isControlDown())
						SaveScreenshot.open(component, Type.Png);
					break;
				}
			}
			public void keyReleased(KeyEvent e) { }
			public void keyTyped(KeyEvent e) {  }
		});

		//=====================================================================
		// FINAL REGISTERING
		//=====================================================================
		grid.setDestination(plotView);
		grid.setView(plotView);
		values.setView(plotView);
		values.setDestination(valuesCanvas);
		labels.setTable(table);
		labels.setSettings(settings);

		//=====================================================================
		// DISPLAY CHART
		//=====================================================================
		displayPanel(component, "Line Chart 2D");
	}


	private static Dataset[] generateDatasets(int lists, int sets)
	{
		Dataset[] ds = new Dataset[sets];
		SimpleDateFormat df = new SimpleDateFormat("MMM dd");
		Calendar cal = new GregorianCalendar();

		ds[0] = new Dataset(df.format(cal.getTime()), lists, 1);
		for (int j = 0; j < lists; j++)
			ds[0].setData(j, Math.random() * 60 + 10);

		for (int i = 1; i < sets; i++)
		{
			cal.add(Calendar.DAY_OF_YEAR, 1);

			ds[i] = new Dataset(df.format(cal.getTime()), lists, 1);

			for (int j = 0; j < lists; j++)
				ds[i].setData(j, Math.abs(ds[i - 1].getData(j, 0) + (Math.random() * 19 - 9)));
		}

		return ds;
	}


	private void displayPanel(JPanel panel, String title)
	{
		JFrame window = new JFrame("PainterPie3D Testing.");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
