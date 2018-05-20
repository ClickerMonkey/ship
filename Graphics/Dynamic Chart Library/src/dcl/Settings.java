package dcl;

import com.philsprojects.chart.view.Viewport;

public class Settings {

	protected Viewport view;

	public Settings(Viewport view) {
		this.view = view;
	}
	
	public Viewport getView() {
		return view;
	}

	public void setView(Viewport view) {
		this.view = view;
	}
	
	
}
