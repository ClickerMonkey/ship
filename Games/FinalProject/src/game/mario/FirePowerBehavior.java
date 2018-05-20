package game.mario;

import static game.GameConstants.*;

public class FirePowerBehavior implements Behavior
{

    private boolean _throwing = false;
    private boolean _wantsToThrow = false;
    private float _throwTime = 0f;
    private Mario _mario = null;
    
    public FirePowerBehavior(Mario mario)
    {
	_mario = mario;
	_mario.changeTexture(MARIO_FIRETEXTURE);
    }
    
    public void doAction()
    {
	 _wantsToThrow = true;
    }

    public void update(float deltatime)
    {
	_throwTime += deltatime;
	if (_wantsToThrow && _throwTime >= FIREPOWER_INTERVAL)
	{
	    _mario.throwFireBall();
	    _wantsToThrow = false;
	    _throwTime = 0f;
	    _throwing = true;
	}
	if (_throwTime >= FIREPOWER_INTERVAL && _throwing)
	{
	    _throwing = false;
	}
	if (_throwing)
	{
	    //_mario.playAnimation(MARIO_THROWING);
	}
    }
    
    public void endBehavior()
    {
	
    }
    
}
