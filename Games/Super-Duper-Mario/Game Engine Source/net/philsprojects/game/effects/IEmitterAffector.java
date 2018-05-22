package net.philsprojects.game.effects;

import net.philsprojects.game.util.Iterator;

public interface IEmitterAffector //extends IBinary
{

	void affectParticle(Iterator<? extends IParticle> particles, float deltatime);

}
