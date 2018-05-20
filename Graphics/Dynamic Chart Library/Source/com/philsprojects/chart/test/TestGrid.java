package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import com.philsprojects.chart.common.Grid;
import com.philsprojects.chart.common.GridBoth;
import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestGrid
{

    public static void main(String[] args)
    {
	final OutlineDelta outline = new OutlineDelta(
		new Color(64, 64, 120, 255),		// Color at startFrequency
		new Color(64, 64, 120, 0),		// Color at endFrequency
		2f, 0.5f);

	final Grid grid = new GridBoth();
	grid.setFrequency(20f);
	grid.setStartFrequency(80f);
	grid.setEndFrequency(10f);
	grid.setInterval(2);
	grid.setOutline(outline);

	//	final Grid gridV = new GridVertical();
	//	gridV.setFrequency(30f);
	//	gridV.setStartFrequency(80f);
	//	gridV.setEndFrequency(10f);
	//	gridV.setInterval(3);
	//	gridV.setOutline(outline);
	//	
	//	final Grid gridH = new GridHorizontal();
	//	gridH.setFrequency(20f);
	//	gridH.setStartFrequency(80f);
	//	gridH.setEndFrequency(10f);
	//	gridH.setInterval(4);
	//	gridH.setOutline(outline);

	final Viewport view = new Viewport(true, 0, 0, 512, 512) {
	    public void onDraw(Graphics2D gr) {
		grid.draw(gr);
		//		gridV.draw(gr);
		//		gridH.draw(gr);
	    }
	};
	view.setBounds(512, 0, 512, 0);

	grid.setDestination(view);
	grid.setView(view);
	//	gridV.setDestination(view);
	//	gridV.setView(view);
	//	gridH.setDestination(view);
	//	gridH.setView(view);

	final ViewportComponent panel = new ViewportComponent(512, 512);
	panel.addCanvas(view);

	final JFrame window = new JFrame("Grid Testing.");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(panel);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);
    }

}
