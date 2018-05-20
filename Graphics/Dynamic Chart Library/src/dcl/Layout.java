package dcl;

public enum Layout 
{

	Stacked(true, false),
	Percent(true, true),
	Layered(false, false),
	LayeredPercent(false, true);
	
	public final boolean isStacked;
	public final boolean isScaled;
	
	private Layout(boolean stacked, boolean scaled) {
		this.isStacked = stacked;
		this.isScaled = scaled;
	}
	
}
