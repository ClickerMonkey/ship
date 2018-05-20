package net.philsprojects.game.controls;

public interface EventListener
{
	public <T extends Event> void controlEvent(Control source, T event);
}
