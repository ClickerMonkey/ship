package net.philsprojects.msg;

import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;

public interface MessageFactory 
{
	
	public Message onRead(ByteReader in);
	public void onWrite(ByteWriter out, Message msg);
	
}
