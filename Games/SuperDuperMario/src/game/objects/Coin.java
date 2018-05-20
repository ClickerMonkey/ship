package game.objects;

import static game.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import game.Level;
import game.Tiles;
import game.mario.Mario;

/**
 * Any coin on the map that Mario can collect.
 * 
 * @author Philip Diffenderfer
 */
public class Coin extends TiledElement
{

    public Coin()
    {
	super(Tiles.get(COIN_ANIM), false, false, false, false, true, false, 0.75f);
    }

    @Override
    public void hit(ITiledEntity entity, int x, int y, int hitType)
    {
	if (!(entity instanceof Mario))
	    return;
	Level.getInstance().removeTile(x, y);
	//Initialize an effect that adds itself into the game
	new CoinEffect(x * TILE_WIDTH + TILE_WIDTH / 2f, y * TILE_HEIGHT + TILE_HEIGHT / 2f);

	Mario.addCoin();
	SoundLibrary.getInstance().play(SOUND_COIN);
    }

}
