package pfxEditor;
public class VelocityDefault implements IEmitterVelocity {

	public VelocityDefault() {
	}

	public void initialVelocity(Particle p) {
		p.velocity.set(0f, 0f, 0f);
	}

}
