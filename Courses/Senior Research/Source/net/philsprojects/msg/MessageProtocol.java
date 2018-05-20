package net.philsprojects.msg;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import net.philsprojects.io.BufferStream;
import net.philsprojects.io.buffer.BufferFactory;
import net.philsprojects.io.buffer.BufferFactoryDirect;
import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;
import net.philsprojects.net.AbstractProtocol;
import net.philsprojects.net.Client;

public class MessageProtocol extends AbstractProtocol<Message> 
{
	
	public static byte STX = 0x02;
	public static byte ETX = 0x03;
	public static int HEADER = 1 + 4 + 4;
	
	private Map<Integer, MessageFactory> factories;
	private BufferFactory factory = new BufferFactoryDirect(); 
	
	
	public MessageProtocol(BufferFactory factory) 
	{
		this.factory = factory;
		this.factories = new HashMap<Integer, MessageFactory>();
	}
	
	public void register(int typeId, MessageFactory factory) 
	{
		factories.put(typeId, factory);
	}

	public Message decode(ByteReader in, Client end) 
	{
		if (!in.has(HEADER)) {
			return null; // NOT ENOUGH DATA
		}
		if (in.getByte() != STX) {
			return null; // INVALID DATA!
		}
		
		int type = in.getInt();
		int length = in.getInt();
		
		if (!in.has(length + 1)) {
			return null; // NOT ENOUGH DATA
		}
		
		MessageFactory factory = factories.get(type);
		Message msg = factory.onRead(in);
		
		if (msg == null || !in.isValid()) { 
			return null; // INVALID FACTORY
		}
		
		msg.setSource(end);
		
		if (in.getByte() != ETX) {
			return null; // INVALID DATA!
		}
		
		in.sync();
		
		return msg;
	}
	
	public void encode(ByteWriter out, Message data, Client end) 
	{
		BufferStream stream = new BufferStream(null, factory);
		ByteWriter writer = new ByteWriter(stream);
		
		MessageFactory factory = factories.get(data.getType());
		factory.onWrite(writer, data);
		
		ByteBuffer buffer = stream.getReader();
		
		out.putByte(STX);
		out.putInt(buffer.remaining() + 1);
		out.putBuffer(buffer);
		out.putByte(ETX);
	}

}
