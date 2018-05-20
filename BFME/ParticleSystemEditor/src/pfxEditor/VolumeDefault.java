package pfxEditor;

public class VolumeDefault implements IEmitterVolume {

	public VolumeDefault() {
	}

	public void initialVolume(Particle p) {
		p.creationPitch = Range.random(0f, 360f);
		p.creationYaw = Range.random(0f, 360f);
		p.location.set(0f, 0f, 0f);
	}

}
