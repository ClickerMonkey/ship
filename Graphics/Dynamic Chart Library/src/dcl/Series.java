package dcl;

import com.philsprojects.chart.view.Viewport;

public class Series 
{

	protected Viewport view;

	public Series(Viewport view) {
		this.view = view;
	}
	
	public Viewport getView() {
		return view;
	}

	public void setView(Viewport view) {
		this.view = view;
	}
	
}
