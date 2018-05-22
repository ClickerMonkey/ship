package net.philsprojects.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface IBinary
{
	public void write(DataOutputStream writer) throws Exception;

	public void read(DataInputStream reader) throws Exception;

}
