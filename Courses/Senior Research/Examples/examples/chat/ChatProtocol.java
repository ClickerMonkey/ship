package examples.chat;

import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;
import net.philsprojects.net.AbstractProtocol;
import net.philsprojects.net.Client;

public class ChatProtocol extends AbstractProtocol<ChatEvent> 
{

	private static final ChatEvent EVENT = new ChatEvent();
	
	@Override
	public ChatEvent decode(ByteReader in, Client end) 
	{
		ChatEvent event = in.getItem(ChatEvent.class, EVENT);
		if (in.isValid()) {
			in.sync();
			return event;
		}
		return null;
	}

	@Override
	public void encode(ByteWriter out, ChatEvent data, Client end) 
	{
		out.putItem(data, EVENT);
	}

}
