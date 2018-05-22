package net.philsprojects.mario;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.Constants;
import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.TileLibrary;
import net.philsprojects.game.sprites.SpriteTileStatic;
import net.philsprojects.game.util.Iterator;


/**
 * Loads all tile data used in the game by the names given defined
 *      in the GameConstants class. All Animated sprite tiles animation
 *      speed is also in the GameConstants class.
 *      
 * @author Philip Diffenderfer
 */
public final class Tiles
{
	private static TileLibrary _tiles;

	public static void intialize()
	{
		_tiles = TileLibrary.getInstance();
		_tiles.add(GOOMBA_ANIM, TILESHEET_TEXTURE, LOOP_FORWARD, 2, 2, 40, 40, 120, 120, GOOMBA_ANIM_SPEED); //Animated
		_tiles.add(GOOMBA_DEAD, TILESHEET_TEXTURE, 120, 120, 40, 40); //Static
		_tiles.add(PRIZEBOX_ANIM, TILESHEET_TEXTURE, LOOP_FORWARD, 5, 5, 40, 40, 0, 160, PRIZEBOX_ANIM_SPEED); //Animated
		_tiles.add(COIN_ANIM, TILESHEET_TEXTURE, LOOP_FORWARD, 4, 4, 40, 40, 0, 200, COIN_ANIM_SPEED); //Animated
		_tiles.add(STAR, TILESHEET_TEXTURE, 200, 0, 40, 40); //Static
		_tiles.add(MUSHROOM_GREEN, TILESHEET_TEXTURE, 200, 120, 40, 40); //Static
		_tiles.add(MUSHROOM_RED, TILESHEET_TEXTURE, 201, 41, 38, 38); //Static
		_tiles.add(MYSTERYBLOCK, TILESHEET_TEXTURE, 120, 80, 40, 40); //Static
		_tiles.add(HARDBLOCK, TILESHEET_TEXTURE, 160, 80, 40, 40); //Static
		_tiles.add(BRICKBLOCK, TILESHEET_TEXTURE, 200, 80, 40, 40); //Static
		_tiles.add(BLOCK_PIECE1, TILESHEET_TEXTURE, 0, 281, 40, 40); //Static
		_tiles.add(BLOCK_PIECE2, TILESHEET_TEXTURE, 40, 281, 40, 40); //Static
		_tiles.add(BLOCK_PIECE3, TILESHEET_TEXTURE, 80, 281, 40, 40); //Static
		_tiles.add(BLOCK_PIECE4, TILESHEET_TEXTURE, 120, 281, 40, 40); //Static
		_tiles.add(PIPE_LEFTSIDE, TILESHEET_TEXTURE, 120, 40, 40, 40); //Static
		_tiles.add(PIPE_RIGHTSIDE, TILESHEET_TEXTURE, 160, 40, 40, 40); //Static
		_tiles.add(PIPE_TOPLEFTSIDE, TILESHEET_TEXTURE, 120, 0, 40, 40); //Static
		_tiles.add(PIPE_TOPRIGHTSIDE, TILESHEET_TEXTURE, 160, 0, 40, 40); //Static
		_tiles.add(ICE_PATCH, TILESHEET_TEXTURE, 160, 200, 40, 7); //Static
		_tiles.add(FLOWER, TILESHEET_TEXTURE, 200, 160, 40, 40); //Static
		_tiles.add(BEET, TILESHEET_TEXTURE, 200, 200, 40, 40); //Static
		_tiles.add(PLANT_CLOSED, TILESHEET_TEXTURE, 240, 160, 70, 84); //Static
		_tiles.add(PLANT_OPEN, TILESHEET_TEXTURE, 310, 160, 70, 84); //Static
		_tiles.add(PLANT_CHOMP, TILESHEET_TEXTURE, LOOP_FORWARD, 2, 2, 70, 84, 240, 160, PLANT_CHOMP_SPEED); //Animated
		_tiles.add(PLATFORM, TILESHEET_TEXTURE, 240, 0, 120, 40); //Static
		_tiles.add(SPIKE, TILESHEET_TEXTURE, 240, 40, 40, 40); //Static
		// Map tiles are set up in North.East.South.West for what they are
		// Either G(Grass) D(Dirt) A(Air)
		_tiles.add("GADD", TILESHEET_TEXTURE, 0, 0, 40, 40); //Static
		_tiles.add("DDDD", TILESHEET_TEXTURE, 40, 0, 40, 40); //Static
		_tiles.add("GDDA", TILESHEET_TEXTURE, 80, 0, 40, 40); //Static
		_tiles.add("DADD", TILESHEET_TEXTURE, 0, 40, 40, 40); //Static
		_tiles.add("DDAD", TILESHEET_TEXTURE, 40, 40, 40, 40); //Static
		_tiles.add("DDDA", TILESHEET_TEXTURE, 80, 40, 40, 40); //Static
		_tiles.add("DGDD", TILESHEET_TEXTURE, 0, 80, 40, 40); //Static
		_tiles.add("GDDD", TILESHEET_TEXTURE, 40, 80, 40, 40); //Static
		_tiles.add("DDDG", TILESHEET_TEXTURE, 80, 80, 40, 40); //Static
		_tiles.add("GDAA", TILESHEET_TEXTURE, 0, 120, 40, 40); //Static
		_tiles.add("GDAD", TILESHEET_TEXTURE, 40, 120, 40, 40); //Static
		_tiles.add("GAAD", TILESHEET_TEXTURE, 80, 120, 40, 40); //Static
		//Mario's Animations
		_tiles.add(MARIO_STANDING, MARIO_TEXTURE, 0, 0, 167, 212); //Static
		_tiles.add(MARIO_JUMPING, MARIO_TEXTURE, 334, 424, 167, 212); //Static
		_tiles.add(MARIO_FALLING, MARIO_TEXTURE, 167, 424, 167, 212); //Static
		_tiles.add(MARIO_THROWING, MARIO_TEXTURE, Constants.ONCE_FORWARD, 3, 167, 212, 0.1f, new int[] {0, 0, 6, 6, 6, 0, 0}); //Framed
		_tiles.add(MARIO_WALKING, MARIO_TEXTURE, Constants.LOOP_FORWARD, 3, 167, 212, 0.05f, new int[] {0, 3, 4, 3, 0, 1, 2, 1}); //Framed
		_tiles.add(MARIO_SITTING, MARIO_TEXTURE, 334, 212, 167, 212); //Static
		_tiles.add(MARIO_HEAD, TILESHEET_TEXTURE, 0, 320, 128, 128); //Static
		// Landscapes
		_tiles.add("Tower", "Tower", 0, 0, 119, 256); //Static
		_tiles.add("Cloud", "Cloud", 0, 0, 244, 53); //Static
	}

	public static ISpriteTile get(String name)
	{
		ISpriteTile tile =_tiles.get(name);
		if (tile instanceof SpriteTileStatic)
			return tile;
		else
			return tile.getClone();
	}

	public static void dispose()
	{
		if (_tiles == null)
			return;
		Iterator<ISpriteTile> iterator = _tiles.getIterator();
		while (iterator.hasNext())
			iterator.getNext().dispose();
		iterator = null;
		_tiles = null;
	}

}
