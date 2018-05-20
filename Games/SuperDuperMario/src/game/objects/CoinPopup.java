package game.objects;

import static game.GameConstants.*;
import net.philsprojects.game.sprites.Sprite;
import game.Tiles;

/**
 * The effect that happens when Mario hits the bottom of a block that contains a coin in it. 
 *     It pops upwards and at the end of its life its transparency is 0.
 *     
 * @author Philip Diffenderfer
 */
public class CoinPopup extends Sprite
{

    private float _time = 0f;
    
    public CoinPopup(int x, int y)
    {
	super(COINPOPUP, (x * TILE_WIDTH) - (COINPOPUP_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, COINPOPUP_WIDTH, COINPOPUP_HEIGHT, Tiles.get(COIN_ANIM));
    }
    
    @Override
    public void update(float deltatime)
    {
	super.update(deltatime);
	setY(getY() + COINPOPUP_RISESPEED * deltatime);
	setAlpha(1f - _time / COINPOPUP_LIFETIME);
	_time += deltatime;
	if (_time >= COINPOPUP_LIFETIME)
	    _enabled = false;
    }
    
}
