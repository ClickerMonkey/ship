package graphing.testing;
import graphing.GraphController;
import graphing.GraphPanel;
import graphing.graphs.FunctionGraph;
import graphing.graphs.FunctionGraphListener;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class FunctionGraphWindow extends JFrame implements FunctionGraphListener 
{
	
	public static void main(String[] args)
	{
		new FunctionGraphWindow();
	}
	
	
	public FunctionGraphWindow()
	{
		FunctionGraph graph = new FunctionGraph(0, 0, 512, 512, 10.0, -10.0, 10.0, -10.0, 10000.0, -10000.0, 10000.0, -10000.0, 1000.0, 0.01, 1000.0, 0.01);
		graph.setVisuals(1.0, new Font("Tahoma", Font.PLAIN, 12), Color.darkGray, new Color(245, 245, 255), Color.darkGray, new Color(100, 150, 255));
		graph.setListener(this);
		
		GraphPanel panel = new GraphPanel(graph);
		
		new GraphController(panel);
		
		setTitle("Function Graph");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		pack();
		setVisible(true);
	}

	
	public double F(double x) 
	{
		return 20 * Math.sin(x / Math.PI);
	}

	
}
