package axe;

public enum Repeat 
{
	
	None (false, false, false),
	X (true, false, false),
	Y (false, true, false),
	Z (false, false, true),
	XY (true, true, false),
	YZ (false, true, true),
	XZ (true, false, true),
	XYZ (true, true, true);
	
	public final boolean x, y, z;
	
	private Repeat(boolean x, boolean y, boolean z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}
