package pfxEditor;

import static pfxEditor.Vector.*;

public class VolumeLine implements IEmitterVolume {

	private Vector _start = Vector.zero();
	private Vector _end = Vector.zero();

	public VolumeLine(Vector start, Vector end) {
		_start = start;
		_end = end;
	}

	public void initialVolume(Particle p) {
		p.location = add(multiply(subtract(_start, _end), Range.random(0f, 1f)), _start);
	}

}
