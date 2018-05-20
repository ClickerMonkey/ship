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
import com.philsprojects.chart.common.GridHorizontal;
import com.philsprojects.chart.common.LabelBar;
import com.philsprojects.chart.common.Padding;
import com.philsprojects.chart.common.ValueBar;
import com.philsprojects.chart.common.ValueFormat;
import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.data.DatatableDynamic;
import com.philsprojects.chart.definitions.DefinitionBar2D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillDelta;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.icons.IconCircle;
import com.philsprojects.chart.icons.IconDiamond;
import com.philsprojects.chart.icons.IconNGon;
import com.philsprojects.chart.icons.IconPie;
import com.philsprojects.chart.icons.IconSquare;
import com.philsprojects.chart.icons.IconStar;
import com.philsprojects.chart.icons.IconTriangle;
import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.outlines.OutlineFill;
import com.philsprojects.chart.painters.PainterBar2D;
import com.philsprojects.chart.publish.PrintScreenshot;
import com.philsprojects.chart.publish.SaveScreenshot;
import com.philsprojects.chart.publish.Screenshot.Type;
import com.philsprojects.chart.settings.SettingsBar2D;
import com.philsprojects.chart.settings.SettingsBar2D.RenderType;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestBarChart2D
{

    public static void main(String[] args)
    {
	new TestBarChart2D();
    }

    final Color[] COLORS = {Color.red, Color.blue, Color.yellow, Color.green, 
	    Color.cyan, Color.magenta, Color.gray, Color.white, Color.orange, 
	    Color.pink, Color.darkGray, Color.lightGray, Color.black,
    };

    final float LEGEND_ICON_SIZE = 20;
    final Icon[] LEGEND_ICONS = {
	    new IconCircle(LEGEND_ICON_SIZE),
	    new IconDiamond(LEGEND_ICON_SIZE),
	    new IconSquare(LEGEND_ICON_SIZE),
	    new IconTriangle(LEGEND_ICON_SIZE),
	    new IconStar(LEGEND_ICON_SIZE, 5, 0.4, 90),
	    new IconNGon(LEGEND_ICON_SIZE, 5, 30),
	    new IconPie(LEGEND_ICON_SIZE * 2, 90, 60, true),
    };

    final String[] LABELS = {
	    "eRX", "Relay Health", "Mail Order", "CVS", "Rite Aid", "Medicine Shoppe"
    };

    private int lists = 4;
    private int sets = 200;

    public TestBarChart2D()
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
	final SettingsBar2D settings = new SettingsBar2D();
	settings.setOffset(0f);
	settings.setSpacing(SPACING);
	settings.setClusterSpacing(SPACING * 0.3);
	settings.setBarWidth(SPACING);
	settings.setType(RenderType.Stacked);

	//=====================================================================
	// GRID - Chart backdrop.
	//=====================================================================
	final Grid grid = new GridHorizontal();
	grid.setEndFrequency(10f);
	grid.setFrequency(SPACING * 16);
	grid.setOffset(0);
	grid.setInterval(4);
	grid.setOutline(new OutlineDelta(new Color(64, 64, 120, 255), new Color(64, 64, 120, 0), 2f, 0.5f));
	grid.setStartFrequency(80f);


	//=====================================================================
	// AXIS - Chart axis'.
	//=====================================================================
	final Axis axisX = new AxisX();
	axisX.setColor(Color.black);
	axisX.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

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
	values.setFontFill(new FillDelta(new Color(64, 64, 120, 255), new Color(64, 64, 120, 0)));
	values.setPadding(new Padding(5));

	//=====================================================================
	// LABELBAR - Chart labels bar.
	//=====================================================================
	final LabelBar labels = new LabelBar();
	labels.setInterval(4);
	labels.setLabelAngle(45f);
	labels.setFont(font);
	labels.setFontFill(new FillSolid(new Color(64, 64, 120, 255)));
	labels.setPadding(new Padding(5));

	//=====================================================================
	// DEFINITIONS - Visual properties.
	//=====================================================================
	final DefinitionBar2D[] definitions = new DefinitionBar2D[lists]; 
	for (int i = 0; i < lists; i++)
	{
	    definitions[i] = PainterBar2D.createGradientDefinition(COLORS[i % COLORS.length], 230, 1f);
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
	final PainterBar2D painter = new PainterBar2D(settings, definitions, table);

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

	//=====================================================================
	// VALUES CANVAS - Value bar canvas.
	//=====================================================================
	final Canvas valuesCanvas = new Canvas(true, SIDE_X, SIDE_Y, SIDE_WIDTH, SIDE_HEIGHT) {
	    protected void onDraw(Graphics2D gr) {
		values.draw(gr);
	    }
	    @Override
	    protected boolean listensTo(Viewport view) {
		return (view == plotView);
	    }
	};

	//=====================================================================
	// LABELS CANVAS - Label bar canvas.
	//=====================================================================
	final Canvas labelsCanvas = new Canvas(true, LABEL_X, LABEL_Y, LABEL_WIDTH, LABEL_HEIGHT) {
	    protected void onDraw(Graphics2D gr) {
		labels.draw(gr);
	    }
	    @Override
	    protected boolean listensTo(Viewport view) {
		return (view == plotView);
	    }
	};

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

	//=====================================================================
	// LEGEND DEFINITIONS - Data list name visual properties.
	//=====================================================================
	Fill outlineFill = new FillLinear(new Color(255, 255, 255, 120), 
		new Color(0, 0, 0, 120), FillLinear.DIAGONAL_LEFT);

	LegendDefinition base = new LegendDefinition();
	base.setFont(new Font("Impact", Font.PLAIN, 14));
	base.setFontFill(new FillGradient(Color.lightGray, Color.black, FillGradient.VERTICAL));
	base.setIconOutline(new OutlineFill(outlineFill, 5f));

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
	component.setBackground(Color.white);

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

	for (int i = 0; i < sets; i++)
	{
	    ds[i] = new Dataset(df.format(cal.getTime()), lists, 1);

	    for (int j = 0; j < lists; j++)
		ds[i].setData(j, Math.random() * 60 + 5);

	    cal.add(Calendar.DAY_OF_YEAR, 1);
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
