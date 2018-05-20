package game.mario;

import static game.GameConstants.ICE_INTERVAL;
import static game.GameConstants.MARIO_ICETEXTURE;

public class IcePowerBehavior implements Behavior
{
    private boolean _throwing = false;
    private boolean _wantsToThrow = false;
    private float _throwTime = 0f;
    private Mario _mario = null;
    
    public IcePowerBehavior(Mario mario)
    {
	_mario = mario;
	_mario.changeTexture(MARIO_ICETEXTURE);
    }
    
    public void doAction()
    {
	_wantsToThrow = true;
    }

    public void endBehavior()
    {

    }

    public void update(float deltatime)
    {
	_throwTime += deltatime;
	if (_wantsToThrow && _throwTime >= ICE_INTERVAL)
	{
	    _mario.throwIceBall();
	    _wantsToThrow = false;
	    _throwTime = 0f;
	    _throwing = true;
	}
	if (_throwTime >= ICE_INTERVAL && _throwing)
	{
	    _throwing = false;
	}
	if (_throwing)
	{
	    //_mario.playAnimation(MARIO_THROWING);
	}
    }

}
