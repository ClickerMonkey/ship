package game;

import net.philsprojects.game.util.*;

/**
 * Contains values that every game object can get to, to be consistent in
 *      all code and it enables modification of game values very easy.
 *      
 * @author Philip Diffenderfer
 */
public class GameConstants
{    
    public static int TILE_WIDTH = 32;
    public static int TILE_HEIGHT = 32;
    
    //Constant Data

    public final static String TILESHEET_TEXTURE = "TileSheet.Texture";
    
    //===============================================================
    //=== SOUND DATA ================================================
    //===============================================================
    public final static String MUSIC_SPLASH = "Splash.mid";
    public final static String MUSIC_LEVEL = "Level.mid";
    public final static String SOUND_JUMP = "Jump.wav";
    public final static String SOUND_PAUSE = "Pause.wav";
    public final static String SOUND_DIE1 = "Die1.wav";
    public final static String SOUND_STAR = "StarPower.mid";
    public final static String SOUND_UGH = "Ugh.wav";
    public final static String SOUND_1UP = "1UP.wav";
    public final static String SOUND_COIN = "Coin.wav";
    public final static String SOUND_BUMP = "Bump.wav";
    public final static String SOUND_BALL = "BallHit.wav";
    public final static String SOUND_BLOCKBREAK = "BreakBlock.wav";
    public final static String SOUND_POWERUP = "PowerUp.wav";
    public final static String SOUND_POWERDOWN = "PowerDown.wav";
    public final static String SOUND_PIPE = "Pipe.wav";
    //===============================================================
    
    public final static String COIN_ANIM = "Coin.Anim";
    public final static float COIN_ANIM_SPEED = 0.1f;
    public final static SmoothCurve COIN_DISAPPEAR = new SmoothCurve(2.5f, new float[] {1f, 0f});
    
    public final static String PRIZEBOX_ANIM = "PrizeBox.Anim";
    public final static float PRIZEBOX_ANIM_SPEED = 0.1f;
    public final static String PRIZEBOX_HIT = "HardBlock";
    
    public final static String HARDBLOCK = "Block.Hard";
    
    public final static String BRICKBLOCK = "Block.Brick";
    public final static String BLOCK_PIECE1 = "Block.Brick.Piece1";
    public final static String BLOCK_PIECE2 = "Block.Brick.Piece2";
    public final static String BLOCK_PIECE3 = "Block.Brick.Piece3";
    public final static String BLOCK_PIECE4 = "Block.Brick.Piece4";
    public final static float BLOCK_PIECE_GRAVITY = -800f;
    public final static float BLOCK_PIECE_SPEED = 300f;
    public final static float BLOCK_PIECE_LIFE = 0.5f;
    
    public final static String MYSTERYBLOCK = "Block.Mystery";
    public final static String SPIKE = "Spike";
    
    //===============================================================
    //=== MARIO DATA ================================================
    //===============================================================
    public final static int MARIO_BOUNDS_LEFT = 41;
    public final static int MARIO_BOUNDS_RIGHT = 39;
    public final static int MARIO_BOUNDS_TOP = 6;
    public final static int MARIO_BOUNDS_BOTTOM = 20;
    public final static float MARIO_SCALE = 0.28f;
    public final static float MARIO_BIG_SCALE = 0.4f;
    public final static float MARIO_BIGGER_SCALE = 1f;
    public final static float MARIO_WIDTH = 167;
    public final static float MARIO_HEIGHT = 212;
    public final static float MARIO_HURT_DURATION = 2f;
    public final static int MARIO_STARTING_LIVES = 2;
    public final static String MARIO_TEXTURE = "Mario";
    public final static String MARIO_FIRETEXTURE = "MarioFire";
    public final static String MARIO_STARTEXTURE = "MarioStar";
    public final static String MARIO_ICETEXTURE = "MarioIce";
    public final static String MARIO_WALKING = "Mario.Running";
    public final static String MARIO_STANDING = "Mario.Standing";
    public final static String MARIO_JUMPING = "Mario.Jumping";
    public final static String MARIO_FALLING = "Mario.Falling";
    public final static String MARIO_THROWING = "Mario.Throwing";
    public final static String MARIO_SITTING = "Mario.Sitting";
    public final static String MARIO_HEAD = "Mario.Head";
    //===============================================================
    
    public final static String PIPE_LEFTSIDE = "Pipe.Left";
    public final static String PIPE_RIGHTSIDE = "Pipe.Right";
    public final static String PIPE_TOPLEFTSIDE = "Pipe.TopLeft";
    public final static String PIPE_TOPRIGHTSIDE = "Pipe.TopRight";

    public final static String GROUP_MARIO = "Group.Mario";
    public final static String GROUP_MARIO_PROJECTILE = "Group.Mario.Projectile";
    public final static String GROUP_ITEM = "Group.Item";
    public final static String GROUP_ENEMY = "Group.Enemy";
    public final static String GROUP_PLATFORM = "Group.Platform";

    public final static String PARTICLE_STAR = "Particle.Star";
    public final static String PARTICLE_SPHERE = "Particle.Sphere";
    public final static String PARTICLE_FIRE = "Particle.Fire";
    public final static String PARTICLE_SMOKE = "Particle.Smoke";
    public final static String PARTICLE_NIGHTSTAR = "Particle.NightStar";

    //===============================================================
    //=== MARIO'S POWERS DATA =======================================
    //===============================================================
    public final static int FIREBALL_TIMEFOLD = 10;
    public final static float FIREBALL_LIFETIME = 3f;
    public final static float FIREBALL_SPEED = 300f;
    public final static float FIREBALL_HALFSIZE = 10f;
    public final static float FIREBALL_GRAVITY = -5000f;
    public final static float FIREBALL_JUMP = 600f;
    public final static float FIREBALL_TERMINAL = -500f;
    public final static float FIREPOWER_INTERVAL = 0.3f;
    
    public final static int ICE_TIMEFOLD = 5;
    public final static float ICE_LIFETIME = 3f;
    public final static float ICE_SPEED = 300f;
    public final static float ICE_HALFSIZE = 10f;
    public final static float ICE_GRAVITY = -5000f;
    public final static float ICE_JUMP = 600f;
    public final static float ICE_TERMINAL = -500f;
    public final static float ICE_INTERVAL = 0.3f;

    public final static String STAR = "Star";
    public final static float STAR_WIDTH = 40;
    public final static float STAR_HEIGHT = 40;
    public final static float STAR_SPEED = 180f;
    public final static float STAR_GRAVITY = -300f;
    public final static float STAR_TERMINAL = -300f;
    public final static float STAR_DURATION = 30f;
    //===============================================================
    
    public final static String ICE_PATCH = "IcePatch";
    public final static int ICE_PATCH_WIDTH = 40;
    public final static int ICE_PATCH_HEIGHT = 14;
    public final static float ICE_PATCH_DURATION = 1f;
    
    public final static int GROUNDDUST_SIZE = 25;
    
    public final static int ICEDUST_SIZE = 25;
    
    public final static int FIREDUST_SIZE = 25;
    
    //===============================================================
    //=== ENTITY DATA ===============================================
    //===============================================================
    public final static String MUSHROOM_GREEN = "Mushroom.Green";
    public final static float MUSHROOM_GREEN_WIDTH = 40;
    public final static float MUSHROOM_GREEN_HEIGHT = 40;
    public final static float MUSHROOM_GREEN_SPEED = 200f;
    public final static float MUSHROOM_GREEN_GRAVITY = -300f;
    public final static float MUSHROOM_GREEN_TERMINAL = -300f;
    
    public final static String MUSHROOM_RED = "Mushroom.Red";
    public final static float MUSHROOM_RED_WIDTH = 40;
    public final static float MUSHROOM_RED_HEIGHT = 40;
    public final static float MUSHROOM_RED_SPEED = 150f;
    public final static float MUSHROOM_RED_GRAVITY = -150f;
    public final static float MUSHROOM_RED_TERMINAL = -300f;
    
    public final static String FLOWER = "Flower";
    public final static float FLOWER_WIDTH = 40;
    public final static float FLOWER_HEIGHT = 40;
    
    public final static String BEET = "Beet";
    public final static float BEET_WIDTH = 40;
    public final static float BEET_HEIGHT = 40;
    
    public final static String PLANT = "Plant";
    public final static String PLANT_OPEN = "Plant.Open";
    public final static String PLANT_CLOSED = "Plant.Closed";
    public final static String PLANT_CHOMP = "Plant.Chomping";
    public final static float PLANT_WIDTH = 56;
    public final static float PLANT_HEIGHT = 64;
    public final static float PLANT_HIDDEN_DURATION = 5f;
    public final static float PLANT_APPEAR_TIME = 1.5f;
    public final static float PLANT_VISIBLE_DURATION = 3f;
    public final static float PLANT_CHOMP_SPEED = 0.1f;
    public final static float DEATH_EFFECT_SIZE = 56;
    
    public final static String PLATFORM = "Platform";
    
    public final static String DROPPLATFORM = "Platform.Drop";
    public final static float DROPPLATFORM_WAIT = 0.4f;
    public final static float DROPPLATFORM_WIDTH = 96f;
    public final static float DROPPLATFORM_HEIGHT = 32f;
    public final static float DROPPLATFORM_GRAVITY = -150;
    public final static float DROPPLATFORM_TERMINAL = -300f;
    
    public final static String GOOMBA = "Goomba";
    public final static String GOOMBA_ANIM = "Goomba.Anim";
    public final static String GOOMBA_DEAD = "Goomba.Dead";
    public final static float GOOMBA_ANIM_SPEED = 0.3f;
    public final static float GOOMBA_WIDTH = 40f;
    public final static float GOOMBA_HEIGHT = 40f;
    public final static float GOOMBA_SPEED = 80f;
    public final static float GOOMBA_GRAVITY = -100f;
    public final static float GOOMBA_TERMINAL = -200f;
    
    public final static String COINPOPUP = "CoinPopup";
    public final static float COINPOPUP_LIFETIME = 0.75f;
    public final static float COINPOPUP_RISESPEED = 80f;
    public final static float COINPOPUP_WIDTH = 24f;
    public final static float COINPOPUP_HEIGHT = 24f;
    
    public final static String POINTPOPUP = "PointPopup";
    public final static float POINTPOPUP_RISESPEED = 80f;
    public final static float POINTPOPUP_LIFETIME = 1.5f;
    //===============================================================

    public final static String SCREEN_SPLASH = "Screen.Splash";
    public final static String SCREEN_SPLASH_TEXTURE = "Screen.Splash.Texture";
    public final static String SCREEN_LEVEL = "Screen.Level";
    
}
