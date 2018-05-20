package game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.*;
import net.philsprojects.game.util.*;

import static game.GameConstants.*;

public class Game extends SceneWindow
{
    // Run the Game!
    public static void main(String[] args)
    {
	new Game();
    }
    
    // Stupid serialVersionUID...
    private static final long serialVersionUID = -7902961349292172558L;

    //Inner window is 768 pixels wide and 512 pixels high.
    private static final int GAME_WIDTH = 768;
    private static final int GAME_HEIGHT = 512;
    // The default background Color.
    private static final Color BACKGROUND = new Color(79, 139, 225);

    //Manages all the screens of the game.
    private ScreenManager _manager;

    /**
     * Load all the textures and sounds. Initialize the Sprite Tiles and setup the Screen manager.
     */
    @Override
    public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
    {
	textures.setDirectory("images/");
	textures.add("TileSheet.png", TILESHEET_TEXTURE);
	textures.add("MarioNormal.png", MARIO_TEXTURE);
	textures.add("MarioFire.png", MARIO_FIRETEXTURE);
	textures.add("MarioStar.png", MARIO_STARTEXTURE);
	textures.add("MarioIce.png", MARIO_ICETEXTURE);
	textures.add("Tower1.png", "Tower");
	textures.add("Cloud.png", "Cloud");
	textures.add("Star.png", PARTICLE_STAR);
	textures.add("NightStar.png", PARTICLE_NIGHTSTAR);
	textures.add("Smoke.dds", PARTICLE_SMOKE);
	textures.add("Sphere.png", PARTICLE_SPHERE);
	textures.add("SplashScreen.png", SCREEN_SPLASH_TEXTURE);

	sounds.setDirectory("sounds/");
	sounds.add(SOUND_1UP, SOUND_1UP);
	sounds.add(SOUND_BALL, SOUND_BALL);
	sounds.add(SOUND_BLOCKBREAK, SOUND_BLOCKBREAK);
	sounds.add(SOUND_BUMP, SOUND_BUMP);
	sounds.add(SOUND_COIN, SOUND_COIN);
	sounds.add(SOUND_DIE1, SOUND_DIE1);
	sounds.add(SOUND_JUMP, SOUND_JUMP);
	sounds.add(SOUND_PAUSE, SOUND_PAUSE);
	sounds.add(SOUND_POWERDOWN, SOUND_POWERDOWN);
	sounds.add(SOUND_POWERUP, SOUND_POWERUP);
	sounds.add(SOUND_STAR, SOUND_STAR);
	sounds.add(SOUND_UGH, SOUND_UGH);
	sounds.add(SOUND_PIPE, SOUND_PIPE);
	sounds.add(MUSIC_SPLASH, MUSIC_SPLASH);
	sounds.add(MUSIC_LEVEL, MUSIC_LEVEL);
	
	Tiles.intialize();
	
	_manager = screens;
	screens.addScreen(new SplashScreen());
	screens.addScreen(new LevelScreen());
	screens.setScreen(SCREEN_SPLASH);
    }

    /**
     * Update the current screen.
     */
    @Override
    public void update(float deltatime)
    {
	_manager.update(deltatime);
    }

    /**
     * Draw the current screen.
     */
    @Override
    public void draw(GraphicsLibrary graphics)
    {
	_manager.draw();
    }

    /**
     * Dispose the tile data and dispose the current Screen.
     */
    @Override
    public void disposeData()
    {
	Tiles.dispose();
	ScreenManager.getInstance().dispose();
    }

    @Override
    public void keyDown(KeyEvent key)
    {
    }

    @Override
    public void keyUp(KeyEvent key)
    {
    }

    @Override
    public void mouseDown(MouseEvent mouse)
    {
    }

    @Override
    public void mouseMove(MouseEvent mouse)
    {
    }

    @Override
    public void mouseUp(MouseEvent mouse)
    {
    }

    @Override
    public int requestWindowHeight()
    {
	return GAME_HEIGHT;
    }

    @Override
    public int requestWindowWidth()
    {
	return GAME_WIDTH;
    }

    @Override
    public Color requestBackgroundColor()
    {
	return BACKGROUND;
    }

    @Override
    public Rectangle requestCameraBounds()
    {
	return new Rectangle(0, 0, GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public int requestMaximumTextures()
    {
	return 23;
    }

    @Override
    public int requestUpdatesPerSecond()
    {
	return 4;
    }

    @Override
    public int requestMaximumSounds()
    {
	return 23;
    }

    @Override
    public int requestMaximumScreens()
    {
	return 7;
    }
    
    @Override
    public int requestMaximumTiles()
    {
	return 53;
    }

}
