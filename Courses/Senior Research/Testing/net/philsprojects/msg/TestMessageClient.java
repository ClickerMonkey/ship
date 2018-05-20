package net.philsprojects.msg;

import java.net.InetSocketAddress;

import net.philsprojects.io.buffer.BufferFactory;
import net.philsprojects.io.buffer.BufferFactoryBinary;
import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;
import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.nio.NioSelector;
import net.philsprojects.net.nio.tcp.TcpPipeline;

public class TestMessageClient
{
	
	public static class PrintMessage extends AbstractMessage {
		private String message;
		public PrintMessage(String message) {
			super(Type);
			this.message = message;
		}
		public String toString() {
			return message;
		}
		public static int Type = 1;
		public static MessageFactory Factory = new MessageFactory() {
			public void onWrite(ByteWriter out, Message msg) {
				out.putString(((PrintMessage)msg).message);
			}
			public Message onRead(ByteReader in) {
				return new PrintMessage(in.getString());
			}
		};
	}

	public static void main(String[] args) 
	{
		NioSelector selector = new NioSelector();
		selector.start();

		BufferFactory factory = new BufferFactoryBinary(8, 16);
		
		MessageProtocol msg = new MessageProtocol(factory);
		msg.register(PrintMessage.Type, PrintMessage.Factory);
		
		Pipeline pipe = new TcpPipeline(selector);
		pipe.setBufferFactory(factory);
		pipe.setDefaultProtocol(Message.class);
		pipe.addProtocol(Message.class, msg);
		
		Client client = pipe.connect(new InetSocketAddress(7645));
		client.send(new PrintMessage("Hello World!"));
	}

}
