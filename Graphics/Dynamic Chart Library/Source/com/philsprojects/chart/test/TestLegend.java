package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.philsprojects.chart.Legend;
import com.philsprojects.chart.LegendDefinition;
import com.philsprojects.chart.Legend.Orientation;
import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.common.Padding;
import com.philsprojects.chart.fills.FillCyclic;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.fills.FillRadial;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.icons.IconCircle;
import com.philsprojects.chart.icons.IconDiamond;
import com.philsprojects.chart.icons.IconSquare;
import com.philsprojects.chart.icons.IconStar;
import com.philsprojects.chart.icons.IconTriangle;
import com.philsprojects.chart.outlines.OutlineFill;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.view.ViewportComponent;

public class TestLegend
{

	private static void createNew(int width, int height, Anchor anchor, Padding padding, Orientation orientation)
	{
		final int LISTS = 5;
		final Legend legend = new Legend(true, 0, 0, width, height, LISTS);
		legend.setAnchor(anchor);
		legend.setPadding(padding);
		legend.setOrientation(orientation);
		legend.setIconPadding(10f);
		legend.setLabelPadding(20f);
		legend.setMinLabelHeight(30f);

		LegendDefinition def1 = new LegendDefinition();
		{
			def1.setLabel("This is a label");
			def1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			def1.setFontFill(new FillSolid(Color.black));
			def1.setIcon(new IconCircle(20));
			def1.setIconFill(new FillLinear(Color.red.brighter(), Color.red.darker(), FillLinear.DIAGONAL_LEFT));
			def1.setIconOutline(new OutlineFill(new FillLinear(new Color(255, 255, 255, 120), 
					new Color(0, 0, 0, 120), FillLinear.DIAGONAL_LEFT), 5f));
		}
		legend.setDefinition(0, def1);

		LegendDefinition def2 = new LegendDefinition();
		{
			def2.setLabel("Another one");
			def2.setFont(new Font("Courier New", Font.BOLD, 16));
			def2.setFontOutline(new OutlineSolid(Color.black, 0.5f));
			def2.setIcon(new IconStar(20, 5, 0.4, 90.0));
			def2.setIconFill(new FillRadial(Color.red, Color.yellow));
			def2.setIconOutline(new OutlineSolid(Color.black, 1f));
		}
		legend.setDefinition(1, def2);

		LegendDefinition def3 = new LegendDefinition();
		{
			def3.setLabel("Third");
			def3.setFont(new Font("Times New Roman", Font.BOLD, 16));
			def3.setFontFill(new FillCyclic(Color.black, Color.blue, 0));
			def3.setIcon(new IconDiamond(20));
			def3.setIconFill(new FillCyclic(Color.black, Color.white, 0));
			def3.setIconOutline(new OutlineSolid(Color.black, 1f));
		}
		legend.setDefinition(2, def3);

		LegendDefinition def4 = new LegendDefinition();
		{
			def4.setLabel("Word");
			def4.setFont(new Font("Verdana", Font.BOLD, 16));
			def4.setFontFill(new FillLinear(
					new Color[] {Color.red, Color.blue, Color.gray}, 
					new float[] {0f, 0.5f, 1f}, FillLinear.HORIZONTAL, FillLinear.REPEAT, 4.0));
			def4.setIcon(new IconCircle(17));
			def4.setIconFill(new FillCyclic(Color.black, Color.white, 0));
			def4.setIconOutline(new OutlineSolid(Color.black, 1f));
		}
		legend.setDefinition(3, def4);

		LegendDefinition def5 = new LegendDefinition();
		{
			def5.setLabel("Fifth label");
			def5.setFont(new Font("Impact", Font.PLAIN, 14));
			def5.setFontFill(new FillGradient(Color.black, Color.blue, FillGradient.VERTICAL));
			def5.setIcon(new IconTriangle(17));
			def5.setIconFill(new FillCyclic(Color.black, Color.white, 0));
			def5.setIconOutline(new OutlineSolid(Color.black, 1f));
		}
		legend.setDefinition(4, def5);


		final ViewportComponent panel = new ViewportComponent(width, height);
		panel.addCanvas(legend);

		final JFrame window = new JFrame("Legend Testing.");
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	private static void createSimple()
	{
		final Legend legend = new Legend(true, 0, 0, 512, 256, 5);
		legend.setAnchor(Anchor.CenterLeft);
		legend.setPadding(new Padding(10));
		legend.setOrientation(Orientation.Vertical);
		legend.setIconPadding(8);
		legend.setLabelPadding(10);
		legend.setMinLabelHeight(22);

		final LegendDefinition base = new LegendDefinition();
		base.setFont(new Font("Impact", Font.BOLD, 14));
		base.setFontFill(new FillGradient(Color.gray, Color.black, FillGradient.VERTICAL));
		base.setIcon(new IconStar(13, 5, 0.7, 90.0));
		base.setIconFill(new FillRadial(Color.white, Color.gray));
		base.setIconOutline(new OutlineFill(base.getFontFill(), 2));


		legend.setDefinition(0, new LegendDefinition("eRX", base));
		legend.setDefinition(1, new LegendDefinition("Relay Health", base));
		legend.setDefinition(2, new LegendDefinition("Mail Order", base));
		legend.setDefinition(3, new LegendDefinition("CVS", base));
		legend.setDefinition(4, new LegendDefinition("Testing", base));


		final ViewportComponent panel = new ViewportComponent(512, 256);
		panel.addCanvas(legend);


		final JFrame window = new JFrame("Legend Testing.");
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	private static void createBasic()
	{
		final Legend legend = new Legend(true, 0, 0, 400, 60, 4);
		legend.setAnchor(Anchor.Center);
		legend.setPadding(new Padding(5));
		legend.setOrientation(Orientation.Horizontal);
		legend.setIconPadding(7);
		legend.setLabelPadding(15);
		legend.setMinLabelHeight(20);

		final LegendDefinition base = new LegendDefinition();
		base.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		base.setFontFill(new FillSolid(Color.black));
		base.setIconOutline(new OutlineSolid(Color.black, 1));

		LegendDefinition def;

		def = new LegendDefinition("eRX", base);
		def.setIconFill(new FillSolid(Color.blue));
		def.setIcon(new IconSquare(10));
		legend.setDefinition(0, def);

		def = new LegendDefinition("Relay Health", base);
		def.setIconFill(new FillSolid(Color.red));
		def.setIcon(new IconCircle(10));
		legend.setDefinition(1, def);

		def = new LegendDefinition("Mail Order", base);
		def.setIconFill(new FillSolid(Color.green));
		def.setIcon(new IconTriangle(10));
		legend.setDefinition(2, def);

		def = new LegendDefinition("CVS", base);
		def.setIconFill(new FillSolid(Color.yellow));
		def.setIcon(new IconDiamond(10));
		legend.setDefinition(3, def);



		final ViewportComponent panel = new ViewportComponent(400, 60);
		panel.addCanvas(legend);

		final JFrame window = new JFrame("Basic Legend Testing.");
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}



	public static void main(String[] args)
	{
		createSimple();
		createBasic();
		//	createNew(700, 100, Anchor.BottomCenter, new Padding(10), Orientation.Horizontal);
		createNew(200, 400, Anchor.CenterLeft, new Padding(5), Orientation.Vertical);
	}

}
