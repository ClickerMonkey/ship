package game.mario;

import static game.GameConstants.MARIO_TEXTURE;

public class NormalBehavior implements Behavior
{
    
    public NormalBehavior(Mario mario)
    {
	mario.changeTexture(MARIO_TEXTURE);
    }
    
    public void doAction()
    {

    }

    public void endBehavior()
    {

    }

    public void update(float deltatime)
    {

    }
}
