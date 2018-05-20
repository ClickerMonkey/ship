package dcl;

import com.philsprojects.chart.view.Viewport;

public class AxisSettings extends Settings 
{
	
	protected float offset;
	protected float stagger;
	protected float width;
	protected Order order;

	public AxisSettings(Viewport view) {
		super(view);
	}

	public float getOffset() {
		return offset;
	}

	public float getStagger() {
		return stagger;
	}

	public float getWidth() {
		return width;
	}

	public Order getOrder() {
		return order;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	public void setStagger(float stagger) {
		this.stagger = stagger;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
