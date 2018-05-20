package dcl;

public enum Order 
{

	BackToFront(1, 0, 1),
	FrontToBack(-1, 1, 0);
	
	private int step;
	private int start;
	private int end;
	
	private Order(int step, int start, int end) {
		this.step = step;
		this.start = start;
		this.end = end;
	}

	public int getStep() {
		return step;
	}
	
	public int getStart(int length) {
		return start * (length - 1);
	}
	
	public int getEnd(int length) {
		return (end * (length + 1)) - 1;
	}
	
}
