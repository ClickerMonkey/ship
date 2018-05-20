package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.philsprojects.chart.Legend;
import com.philsprojects.chart.LegendDefinition;
import com.philsprojects.chart.Legend.Orientation;
import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.data.DatatableDynamic;
import com.philsprojects.chart.definitions.DefinitionPie3D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.icons.IconCircle;
import com.philsprojects.chart.icons.IconDiamond;
import com.philsprojects.chart.icons.IconNGon;
import com.philsprojects.chart.icons.IconPie;
import com.philsprojects.chart.icons.IconSquare;
import com.philsprojects.chart.icons.IconStar;
import com.philsprojects.chart.icons.IconTriangle;
import com.philsprojects.chart.outlines.OutlineFill;
import com.philsprojects.chart.painters.PainterPie3D;
import com.philsprojects.chart.publish.PrintScreenshot;
import com.philsprojects.chart.publish.SaveScreenshot;
import com.philsprojects.chart.publish.Screenshot.Type;
import com.philsprojects.chart.settings.SettingsPie3D;
import com.philsprojects.chart.settings.SettingsPie2D.Order;
import com.philsprojects.chart.settings.SettingsPie2D.RenderType;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestPieChart3D
{

    public static void main(String[] args)
    {
	new TestPieChart3D();
    }

    final Color[] COLORS = {Color.red, Color.blue, Color.yellow, Color.green, 
	    Color.cyan, Color.magenta, Color.gray, Color.white, Color.orange, 
	    Color.pink, Color.darkGray, Color.lightGray, Color.black,
    };

    final float ICON_SIZE = 16;

    final Icon[] ICONS = {
	    new IconCircle(ICON_SIZE),
	    new IconDiamond(ICON_SIZE),
	    new IconSquare(ICON_SIZE),
	    new IconTriangle(ICON_SIZE),
	    new IconStar(ICON_SIZE, 5, 0.4, 90),
	    new IconNGon(ICON_SIZE, 5, 30),
	    new IconPie(ICON_SIZE, 90, 60, true),
    };

    final String[] LABELS = {
	    "eRX", "Relay Health", "CVS", "Mail Order", "Rite Aid", "Medicine Shoppe"
    };


    private int lists = 7;

    public TestPieChart3D()
    {
	//=====================================================================
	// SETTINGS - Physical properties.
	//=====================================================================

	final SettingsPie3D settings = new SettingsPie3D();
	settings.setRadius(200.0);
	settings.setCenter(256.0, 256.0);
	settings.setDepth(60.0);
	settings.setStaggerOffset(20.0);
	settings.setOrder(Order.Clockwise);
	settings.setRenderType(RenderType.Equidistant);
	settings.setPitch(35.0);
	settings.setYaw(90.0);
	settings.setSeparation(20.0);

	//=====================================================================
	// DEFINITIONS - Visual properties.
	//=====================================================================
	DefinitionPie3D[] definitions = new DefinitionPie3D[lists]; 

	for (int i = 0; i < lists; i++)
	    definitions[i] = PainterPie3D.createRadialDefinition(COLORS[i % COLORS.length], 190, 2f);

	//=====================================================================
	// DATATABLE - Data source.
	//=====================================================================
	final Datatable table = new DatatableDynamic(lists, 1);

	double[][] values = new double[lists][1];
	for (int i = 0; i < lists; i++)
	    values[i][0] = Math.random() * 100.0 + Math.random() * 100.0;

	table.add("Title", values);

	//=====================================================================
	// PAINTER - Draws data source given settings and definitions.
	//=====================================================================
	final PainterPie3D painter = new PainterPie3D(settings, definitions, table);
	painter.setIndex(0);
	
	//=====================================================================
	// PLOT - Painter canvas.
	//=====================================================================
	final Fill backFill = new FillLinear(
		new Color[] {Color.white, Color.lightGray, Color.white},
		new float[] {0.35f, 0.5f, 0.9f},
		FillLinear.VERTICAL, FillLinear.NO_CYCLE, 1);

	final Viewport plot = new Viewport(true, 0, 0, 512, 512) {
	    protected void onDraw(Graphics2D gr) {
		backFill.setShape(bounds);
		backFill.select(gr);
		gr.fill(bounds);
		
		painter.draw(gr);
	    }
	};
	plot.setViewportFixed(512, 0, 512, 0);
	plot.setBackground(Color.lightGray);

	//=====================================================================
	// LEGEND - Data list names.
	//=====================================================================
	final Legend legend = new Legend(true, 512, 0, 150, 512, lists);
	legend.setAnchor(Anchor.CenterLeft);
	legend.setIconPadding(12);
	legend.setLabelPadding(12);
	legend.setMinLabelHeight(28);
	legend.setOrientation(Orientation.Vertical);
	legend.setBackground(Color.white);

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
	    def.setIcon(ICONS[i % ICONS.length]);

	    legend.setDefinition(i, def);
	}
	
	//=====================================================================
	// SETTINGS LISTENER - Redraws chart when settings change.
	//=====================================================================
	settings.setViewport(plot);

	//=====================================================================
	// COMPONENT - Handles all canvas'
	//=====================================================================
	final ViewportComponent component = new ViewportComponent(662, 512)
	{
	    private double lastX, currX;
	    private double lastY, currY;
	    @Override
	    public void mousePressed(MouseEvent e) {
		lastX = currX = e.getX();
		lastY = currY = e.getY();
	    }
	    @Override
	    public void mouseDragged(MouseEvent e) {
		synchronized (this) {
		    lastX = currX;
		    currX = e.getX();
		    lastY = currY;
		    currY = e.getY();
		    settings.addRotation((currX - lastX), (currY - lastY));
		}
	    }
	};
	component.addCanvas(plot);
	component.addKeyListener(new KeyListener() {
	    public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_1:
		    settings.setOrder(Order.Clockwise);
		    break;
		case KeyEvent.VK_2:
		    settings.setOrder(Order.CounterClockwise);
		    break;
		case KeyEvent.VK_3:
		    settings.setOrder(Order.RoundRobin);
		    break;
		case KeyEvent.VK_4:
		    settings.setRenderType(RenderType.CutOff);
		    break;
		case KeyEvent.VK_5:
		    settings.setRenderType(RenderType.Equidistant);
		    break;
		case KeyEvent.VK_6:
		    settings.setRenderType(RenderType.StickOut);
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
	legend.setListener(component);
	component.addCanvas(legend);
	
	//=====================================================================
	// DISPLAY CHART
	//=====================================================================
	displayPanel(component, "Pie Chart 3D");
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
