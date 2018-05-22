package net.philsprojects.game.effects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.*;
import net.philsprojects.game.effects.BurstParticleSystem;
import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.effects.SystemNodeList;
import net.philsprojects.game.effects.VelocityOrtho;
import net.philsprojects.game.effects.VolumeDefault;
import net.philsprojects.game.effects.VolumeLine;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;


public class TestSystemNodeList extends SceneWindow
{

	public static void main(String[] args)
	{
		new TestSystemNodeList();
	}

	private static final long serialVersionUID = 1L;

	public final static int WIDTH = 512;
	public final static int HEIGHT = 512;

	public SystemNodeList _fireNode;
	public Text _text;

	@Override
	public void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics)
	{
		textures.setDirectory("net/philsprojects/game/effects/");
		textures.add("Twinkle.png", "Twinkle");
		textures.add("Smoke.dds", "Smoke");
		textures.setDirectory("engine/controls/");
		textures.add("Numbers.png", "Numbers");

		_text = new Text("Numbers", "0123456789", 0, 0, 32, 36, 3, 20, 23);

		BurstParticleSystem<Particle> smoke = new BurstParticleSystem<Particle>(Particle.class, "Smoke");
		smoke.setBurstCount(1, 1);
		smoke.setBurstDelay(0.001f, 0.001f);
		smoke.setEmitterVelocity(new VelocityOrtho(-40, 40, 200, 250));
		smoke.setEmitterVolume(new VolumeDefault());
		smoke.setParticleAlphas(0f, 1f, 0f);
		smoke.setParticleColors(Color.gray(), Color.black());
		smoke.setParticleLife(0.5f, 0.1F);
		smoke.setParticleSizes(20f, 60f, 140f);
		smoke.setParticleType(Particle.FULL);
		smoke.setEmitterOffset(10f, 0f);
		smoke.setTile("Smoke");

		BurstParticleSystem<Particle> sparks = new BurstParticleSystem<Particle>(Particle.class, "Smoke");
		sparks.setBurstCount(1, 1);
		sparks.setBurstDelay(0.1f, 0.1f);
		sparks.setEmitterVelocity(new VelocityOrtho(-30, 30, 100, 100));
		sparks.setEmitterVolume(new VolumeLine(-10, 0, 10, 0, VolumeLine.WATERFALL));
		sparks.setParticleAlphas(0f, 0.7f, 0f);
		sparks.setParticleColors(Color.lightgray(), Color.white());
		sparks.setParticleLife(2f, 2F);
		sparks.setParticleSizes(20, 20);
		sparks.setParticleType(Particle.ADDITIVE);
		sparks.setEmitterOffset(0f, 100f);
		sparks.setTile("Twinkle");

		BurstParticleSystem<Particle> flames = new BurstParticleSystem<Particle>(Particle.class, "Smoke");
		flames.setBurstCount(1, 2);
		flames.setBurstDelay(0.01f, 0.01f);
		flames.setEmitterVelocity(new VelocityOrtho(-20, 20, 200, 200));
		flames.setEmitterVolume(new VolumeLine(-10, 0, 10, 0, VolumeLine.WATERFALL));
		flames.setParticleAlphas(1f, 0.4f, 0f);
		flames.setParticleAngles(0f, 360f);
		flames.setParticleColors(Color.red(), Color.orange(), Color.gray());
		flames.setParticleLife(0.6f, 0.6F);
		flames.setParticleSizes(30, 50);
		flames.setParticleType(Particle.ADDITIVE);
		flames.setTile("Smoke");


		_fireNode = new SystemNodeList("Fire");
		_fireNode.addNode(smoke);
		_fireNode.addNode(flames);
		_fireNode.addNode(sparks);
		_fireNode.initialize();
		_fireNode.setLocation(WIDTH / 2, -20f);

		SystemNodeList copy = _fireNode.getClone();
		copy.initialize();
		copy.setLocation(150f, 0f);
		_fireNode.addNode(copy);

		smoke = sparks = flames = null;
	}

	@Override
	public void update(float deltatime)
	{
		_fireNode.update(deltatime);
		this.setTitle(String.valueOf(getTimer().getFrameRate()));
	}

	@Override
	public void draw(GraphicsLibrary graphics)
	{	
		_fireNode.draw();
		_text.draw(String.valueOf((int)getTimer().getFrameRate()), 10, HEIGHT - 30, false);
	}

	@Override
	public void disposeData()
	{

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
	public Color requestBackgroundColor()
	{
		return Color.darkgray();
	}

	@Override
	public Rectangle requestCameraBounds()
	{
		return new Rectangle(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public int requestMaximumScreens()
	{
		return 0;
	}

	@Override
	public int requestMaximumSounds()
	{
		return 0;
	}

	@Override
	public int requestMaximumTextures()
	{
		return 5;
	}

	@Override
	public int requestMaximumTiles()
	{
		return 5;
	}

	@Override
	public int requestUpdatesPerSecond()
	{
		return 4;
	}

	@Override
	public int requestWindowHeight()
	{
		return WIDTH;
	}

	@Override
	public int requestWindowWidth()
	{
		return WIDTH;
	}

	@Override
	public float requestMinDelta() 
	{
		return 0.1f;
	}

}
