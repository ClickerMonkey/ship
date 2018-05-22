package net.philsprojects.mario.mario;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ICurve;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.ScreenManager;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.sprites.AnimatedSprite;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.SmoothCurve;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.objects.*;

public class Mario extends AnimatedSprite implements ITiledEntity
{

	private static int _totalCoins = 0;
	private static int _lives = 0;
	private static int _points = 0;

	public static void addCoin()
	{
		_totalCoins++;
	}

	public static int getTotalCoins()
	{
		return _totalCoins;
	}

	public static void addLife()
	{
		_lives++;
	}

	public static int getLives()
	{
		return _lives;
	}

	public static void addPoints(int points)
	{
		_points += points;
	}

	public static int getPoints()
	{
		return _points;
	}

	public static void clearData()
	{
		_totalCoins = 0;
		_lives = MARIO_STARTING_LIVES;
		_points = 0;
	}


	// Mario's variables
	private boolean _dead = false;
	private static float _boundsTopOffset = MARIO_BOUNDS_TOP * MARIO_SCALE;
	private static float _boundsLeftOffset = MARIO_BOUNDS_LEFT * MARIO_SCALE;
	private static float _boundsRightOffset = MARIO_BOUNDS_RIGHT * MARIO_SCALE;
	private static float _boundsBottomOffset = MARIO_BOUNDS_BOTTOM * MARIO_SCALE;

	private float _runSpeed = 230f;
	private float _jumpDuration = 0.5f;
	private float _jumpTime = 0f;
	private ICurve _jumpCurve = new SmoothCurve(_jumpDuration, new float[] { 920, 620, 340, 40 });
	private float _fallTime = 0f;
	private ICurve _fallCurve = new SmoothCurve(_jumpDuration, new float[] { 40, 340, 620, 920 });
	private int _direction = NONE;
	private boolean _jumping = false;
	private boolean _jumpLock = false;
	private boolean _falling = false;
	private boolean _onPlatform = false;

	private boolean _hurt = false;
	private float _hurtTime = 0f;
	private ICurve _hurtAlpha = new SmoothCurve(MARIO_HURT_DURATION, new float[] {1f, 0f, 1f, 0f, 1f, 0f, 1f});

	private Behavior _power = null;

	private final static int MODE_NORMAL = 0;
	private final static int MODE_BIG = 1;
	private final static int MODE_BIGGER = 2;
	private int _mode = MODE_NORMAL;

	public final static int POWER_NORMAL = 0;
	public final static int POWER_FIRE = 1;
	public final static int POWER_ICE = 2;
	public final static int POWER_STAR = 3;
	private int _currentPower = POWER_NORMAL;


	private BoundingBox _bounds = BoundingBox.zero();
	private BoundingBox _lastBounds = BoundingBox.zero();

	private GameEffect _dust = null;

	public Mario(int x, int y)
	{
		super("ITSA ME MARIO!", Level.getInstance().getEntityX(x, MARIO_WIDTH * MARIO_SCALE), Level.getInstance().getEntityY(y, _boundsBottomOffset), MARIO_WIDTH * MARIO_SCALE, MARIO_HEIGHT *
				MARIO_SCALE, 6);
		addAnimation(Tiles.get(MARIO_JUMPING));
		addAnimation(Tiles.get(MARIO_FALLING));
		addAnimation(Tiles.get(MARIO_WALKING));
		addAnimation(Tiles.get(MARIO_THROWING));
		addAnimation(Tiles.get(MARIO_STANDING));
		playAnimation(MARIO_STANDING);
		reset(-1, -1);
		_lives = MARIO_STARTING_LIVES;
	}

	public void reset(int x, int y)
	{
		resize(MODE_NORMAL);
		gotoNormal();
		if (x != -1 && y != -1)
		{
			setX(Level.getInstance().getEntityX(x, MARIO_WIDTH * MARIO_SCALE));
			setY(Level.getInstance().getEntityY(y, _boundsBottomOffset));
		}
		_shade = Color.white();
		_hurt = false;
		_jumpTime = 0f;
		_fallTime = 0f;
		_jumping = false;
		_falling = false;
		_direction = NONE;
		_enabled = true;
	}

	public void jump(boolean gettingAir)
	{
		if (!_jumping && !_jumpLock && gettingAir)
		{
			_jumpTime = 0f;
			SoundLibrary.getInstance().play(SOUND_JUMP);
		}
		if (_falling)
			_jumping = false;
		else
			_jumping = gettingAir;
	}

	public void fall(boolean isFalling)
	{
		if (!isFalling)
		{
			if (_jumpLock)
			{
				_jumpLock = false;
				_jumping = false;
			}
			_fallTime = 0f;
		}
		_falling = isFalling;
	}

	public void moveLeft()
	{
		_direction = BACKWARD;
		_flip = FLIP_X;
	}

	public void moveRight()
	{
		_direction = FORWARD;
		_flip = FLIP_NONE;
	}

	public void stop()
	{
		_direction = NONE;
	}


	/**
	 * Sets Mario's power to Normal.
	 */
	public void gotoNormal()
	{
		setPower(new NormalBehavior(this), new GroundDustEffect(), POWER_NORMAL);
	}

	/**
	 * Sets Mario's power to the star power.
	 */
	public void gotoStarPower()
	{
		setPower(new StarPowerBehavior(this), new GroundDustEffect(), POWER_STAR);
	}

	/**
	 * Sets Mario's power to the fire power.
	 */
	public void gotoFirePower()
	{
		setPower(new FirePowerBehavior(this), new FireDustEffect(), POWER_FIRE);
	}

	/**
	 * Sets Mario's power to the ice power.
	 */
	public void gotoIcePower()
	{
		setPower(new IcePowerBehavior(this), new IceDustEffect(), POWER_ICE);
	}

	/**
	 * Sets the power according to the behavior, dust effect, and the value.
	 */
	private void setPower(Behavior power, GameEffect dust, int newPower)
	{
		// End the previous power if any, set the new power. 
		// Stop creating the previous dust effect and set the new one.
		if (_power != null)
		{
			_power.endBehavior();
		}
		_power = power;
		if (_dust != null)
		{
			_dust.setManualBurst(false);
			_dust.stopCreating();
		}
		_dust = dust;
		_currentPower = newPower;
		SoundLibrary.getInstance().play(SOUND_POWERUP);
	}

	/**
	 * Occurs when Mario runs into an enemy or lands on spikes.
	 */
	public void takeHit()
	{
		if (_hurt)
			return;
		//If Mario is small and has no power then he's dead!
		if (_mode == MODE_NORMAL && _currentPower == POWER_NORMAL)
		{	
			_lives--;
			if (_lives < 0)
				_lives = 0;
			if (_lives == 0)
			{
				ScreenManager.getInstance().setScreen(SCREEN_SPLASH);
				SoundLibrary.getInstance().play(SOUND_DIE1);
			}
			else
			{
				Level.getInstance().resetMario();
			}
		}
		else
		{
			//If Mario has any power then remove the power.
			if (_currentPower != POWER_NORMAL)
			{
				gotoNormal();
			}
			//If Mario has no power but is big then shrink him down a size.
			else if (_mode == MODE_BIG)
			{
				resize(MODE_NORMAL);
			}
			else if (_mode == MODE_BIGGER)
			{
				resize(MODE_BIG);
			}
			_hurt = true;
			_hurtTime = 0f;
		}
	}

	public void update(float deltatime)
	{
		super.update(deltatime);
		// If Mario is running then increase is speed
		if (_direction != NONE)
			setX(getX() + _runSpeed * deltatime * _direction);
		// If Mario is jumping (increasing in vertical height)
		if (_jumping)
		{
			// Mario is jumping and not falling.
			_jumpLock = true;
			_falling = false;
			// If Mario has been in the air for less then the maximum
			//     seconds Mario can be in the air.
			if (_jumpTime < _jumpDuration)
			{
				// Update the Y-location by the current value on the jump curve.
				setY(getY() + _jumpCurve.getValue(_jumpTime) * deltatime);
				_jumpTime += deltatime;
			}
			else
			{
				// Jump duration has been exceeded, hes now falling.
				_jumping = false;
				fall(true);
			}
		}
		// Mario is on some type of ground.
		boolean onGround = Level.getInstance().isOnGround(this);
		if (!onGround && !_jumping)
			fall(true);
		if (_falling)
		{
			setY(getY() - _fallCurve.getValue(_fallTime) * deltatime);
			_fallTime += deltatime;
		}
		// Update Animations with animation precedence
		if (_direction != NONE)
			playAnimation(MARIO_WALKING);
		else
			playAnimation(MARIO_STANDING);
		if (_falling && !onGround && !_onPlatform)
			playAnimation(MARIO_FALLING);
		if (_jumping)
			playAnimation(MARIO_JUMPING);
		// If Mario is a ground of some type and hes walking then show dust.
		if ((onGround || _onPlatform) && _direction != NONE && _dust != null)
			_dust.tryBurst();
		// Update the current power behavior
		_power.update(deltatime);
		// Set the location of the dust effect at Mario's feet.
		_dust.setEmitterOffset((_bounds.getLeft() + _bounds.getRight()) / 2f + (_direction * _bounds.getWidth() / 2f), _bounds.getBottom());
		// Update Mario's bounds
		updateBounds();
		// Mario is not on a platform at the end of the update.
		_onPlatform = false;
		// If Mario is hurt then make him blink for the total duration until his safety time is up
		if (_hurt)
		{
			setAlpha(_hurtAlpha.getValue(_hurtTime));
			_hurtTime += deltatime;
			_hurt = (_hurtTime < MARIO_HURT_DURATION);
		}
	}

	public boolean acceptsEntityHit()
	{
		return !_dead;
	}

	public boolean acceptsTileHit()
	{
		return !_dead;
	}

	public String getGroupID()
	{
		return GROUP_MARIO;
	}

	public void hitEntity(ITiledEntity entity, int hitType)
	{
		if (entity.getGroupID() == GROUP_ITEM)
		{
			if (entity instanceof RedMushroom)
			{
				resize(_mode + 1);
			}	    
			else if (entity instanceof GreenMushroom)
			{
				addLife();
			}
			else if (entity instanceof Star)
			{
				gotoStarPower();
			}
			else if (entity instanceof Flower)
			{
				gotoFirePower();
			}
			else if (entity instanceof IceBeet)
			{
				gotoIcePower();
			}
		}
		else if (entity.getGroupID() == GROUP_ENEMY)
		{
			// The Enemy Mario hit.
			Enemy e = (Enemy)entity;
			// If Mario is invincible then kill the enemy.
			if (_currentPower == POWER_STAR)
			{
				e.kill();
				return;
			}
			// If Mario has hit a plant then take a hit always and exit.
			if (entity instanceof Plant)
			{
				takeHit();
				return;
			}
			// If Mario hits the enemies top or halfway up its left and right side
			//    then kill the enemy
			boolean enemyKilled;
			enemyKilled = (hitType == HIT_BOTTOM);
			if (hitType == HIT_RIGHT || hitType == HIT_LEFT)
			{
				float overlap = entity.getBounds().getTop() - _bounds.getBottom();
				enemyKilled = enemyKilled || (overlap <= _bounds.getHeight() / 4);
			}
			if (!enemyKilled)
				takeHit();
			else if (_falling)
				e.kill();
		} 
		else if (entity.getGroupID() == GROUP_PLATFORM)
		{
			_onPlatform = true;
		}
	}

	public void resize(int mode)
	{
		float scale = (mode == MODE_NORMAL ? MARIO_SCALE : (mode == MODE_BIG ? MARIO_BIG_SCALE : MARIO_BIGGER_SCALE));
		_size.set(MARIO_WIDTH * scale, MARIO_HEIGHT * scale);
		_boundsTopOffset = MARIO_BOUNDS_TOP * scale;
		_boundsLeftOffset = MARIO_BOUNDS_LEFT * scale;
		_boundsRightOffset = MARIO_BOUNDS_RIGHT * scale;
		_boundsBottomOffset = MARIO_BOUNDS_BOTTOM * scale;
		if (mode > _mode)
			SoundLibrary.getInstance().play(SOUND_POWERUP);
		else if (mode < _mode)
			SoundLibrary.getInstance().play(SOUND_POWERDOWN);
		_mode = mode;
		updateBounds();
	}

	public void hitTile(TiledElement element, int x, int y, int hitType)
	{
		if (_mode == MODE_BIGGER && hitType != HIT_BOTTOM && element instanceof BrickBlock)
		{
			Level.getInstance().removeTile(x, y);
			Level.getInstance().addObject(new BlockBreakEffect(element.getBounds(x, y), _bounds));
		}
		else
		{
			if (hitType == HIT_BOTTOM)
			{
				fall(false);
			}
			if (hitType == HIT_TOP)
			{
				jump(false);
				fall(true);
			}
			if (hitType == HIT_RIGHT || hitType == HIT_LEFT)
			{
				//SoundLibrary.getInstance().play(SOUND_UGH, false);
			}
		}
	}

	public void doSpecial()
	{
		_power.doAction();
	}

	protected void throwFireBall()
	{
		float x = (_flip == FLIP_NONE ? getX() + getWidth() - _boundsRightOffset : getX() + _boundsLeftOffset);
		float y = (getY() + getHeight() / 3f * 2f);
		int direction = (_flip == FLIP_NONE ? 1 : -1);
		Level.getInstance().addEntity(new FireBall(x, y, direction, _jumping));
		SoundLibrary.getInstance().play(SOUND_BALL);
	}

	protected void throwIceBall()
	{
		float x = (_flip == FLIP_NONE ? getX() + getWidth() - _boundsRightOffset : getX() + _boundsLeftOffset);
		float y = (getY() + getHeight() / 3f * 2f);
		int direction = (_flip == FLIP_NONE ? 1 : -1);
		Level.getInstance().addEntity(new IceBall(x, y, direction, _jumping));
		SoundLibrary.getInstance().play(SOUND_BALL);
	}

	public void updateBounds()
	{
		_lastBounds = _bounds;
		_bounds = new BoundingBox(getX() + _boundsLeftOffset, getY() + getHeight() - _boundsTopOffset, getX() + getWidth() - _boundsRightOffset, getY() + _boundsBottomOffset);
	}

	public void setLeft(float left)
	{
		setX(left - _boundsLeftOffset);
		updateBounds();
	}

	public void setRight(float right)
	{
		setX(right - getWidth() + _boundsRightOffset);
		updateBounds();
	}

	public void setTop(float top)
	{
		setY(top - getHeight() - _boundsTopOffset);
		updateBounds();
	}

	public void setBottom(float bottom)
	{
		setY(bottom - _boundsBottomOffset);
		updateBounds();
	}

	@Override
	public BoundingBox getBounds()
	{
		return _bounds;
	}

	public BoundingBox getLastBounds()
	{
		return _lastBounds;
	}

	public boolean isUserDrawn()
	{
		return true;
	}

	public int getCurrentPower()
	{
		return _currentPower;
	}

	public int getDirection()
	{
		return _direction;
	}

	protected Vector getCenter()
	{
		return new Vector(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
	}

	public boolean removingEntity()
	{	
		if (_power != null)
			_power.endBehavior();
		if (_dust != null)
			_dust.stopCreating();
		SoundLibrary.getInstance().play(SOUND_DIE1);
		Level.getInstance().resetMario();
		_lives--;
		if (_lives <= 0)
		{
			ScreenManager.getInstance().setScreen(SCREEN_SPLASH);
		}
		return false;
	}

	public boolean correctsIntersection()
	{
		return false;
	}

}
