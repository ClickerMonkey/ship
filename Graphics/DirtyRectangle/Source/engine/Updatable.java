package engine;

public interface Updatable {
	public void update(TimeUnit elapsed);
	public boolean isEnabled();
	public void setEnabled(boolean enabled);
}
