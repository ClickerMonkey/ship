package pfxEditor;
public class VelocityOutward implements IEmitterVelocity {

	private Range speeds;

	public VelocityOutward(float speedMin, float speedMax) {
		speeds = new Range(speedMin, speedMax);
	}

	public void initialVelocity(Particle p) {
		p.velocity = Math.angledVector(p.creationYaw, p.creationPitch, speeds
				.randomFloat());
	}

}
