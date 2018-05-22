package net.philsprojects.mario.mario;

import static net.philsprojects.game.Constants.LOOP_FORWARD;
import static net.philsprojects.mario.GameConstants.MARIO_STARTEXTURE;
import static net.philsprojects.mario.GameConstants.SOUND_STAR;
import static net.philsprojects.mario.GameConstants.STAR_DURATION;
import net.philsprojects.game.ChangerColor;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.util.Color;
import net.philsprojects.mario.objects.GameEffect;

public class StarPowerBehavior implements Behavior
{
	private GameEffect _starPowerEffects = null;
	private ChangerColor _starPower = new ChangerColor("StarPower", LOOP_FORWARD, 0.4f, new Color[] { Color.red(), Color.orange(), Color.yellow(), Color.green(), Color.blue(), Color.purple() });
	private Mario _mario = null;
	private float _time = 0f;

	public StarPowerBehavior(Mario mario)
	{
		_mario = mario;
		_mario.changeTexture(MARIO_STARTEXTURE);
		_starPower.start();
		_starPower.update(0f);
		_starPowerEffects = new StarPowerEffect(_mario);
		SoundLibrary.getInstance().loop(SOUND_STAR);
	}

	public void doAction()
	{

	}

	public void endBehavior()
	{
		_mario.setShade(Color.white());
		_starPowerEffects.stopCreating();
		SoundLibrary.getInstance().stop(SOUND_STAR);
	}

	public void update(float deltatime)
	{
		_starPower.update(deltatime);
		_starPowerEffects.setLocation(_mario.getCenter());
		_starPowerEffects.setParticleColors(_starPower.getColor(), _starPower.getColor());
		_mario.setShade(_starPower.getColor());	
		// Check how long Mario has had the Star Effect
		_time += deltatime;
		if (_time >= STAR_DURATION)
		{
			_mario.gotoNormal();
		}
	}

}
