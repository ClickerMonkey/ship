package net.philsprojects.game.controls;

import net.philsprojects.game.util.*;

public interface ControlScheme
{
	public boolean hasUniqueTexture();

	public String getUniqueTexture();

	public void draw(BoundingBox bounds);

	public void update(float deltatime);

}