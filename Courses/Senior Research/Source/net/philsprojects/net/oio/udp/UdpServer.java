package net.philsprojects.net.oio.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import net.philsprojects.io.buffer.BufferFactory;
import net.philsprojects.net.AbstractServer;
import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.nio.NioInterestTask;
import net.philsprojects.net.nio.NioWorker;
import net.philsprojects.net.nio.udp.UdpPacket;

public class UdpServer extends AbstractServer
{

	private DatagramChannel channel;
	private SelectionKey key;
	private BufferFactory factory;
	private ByteBuffer input;
	private Queue<UdpPacket> packets;
	private Map<SocketAddress, UdpServerClient> clientMap;
	

	protected UdpServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		super(selector, pipeline, acceptor, address);
		this.clientMap = new ConcurrentHashMap<SocketAddress, UdpServerClient>();
		this.packets = new ArrayDeque<UdpPacket>();
		this.factory = pipeline.getBufferFactory();
	}

	protected void setSocket(DatagramChannel channel) throws IOException 
	{
		this.channel = channel;
		this.input = factory.allocate(channel.socket().getReceiveBufferSize());
	}

	protected void setKey(SelectionKey key) 
	{
		this.key = key;
	}

	@Override
	public void handleAccept() 
	{
	}

	@Override
	public void onClose() throws IOException 
	{
		channel.close();
		key.cancel();
	}

	@Override
	public DatagramChannel getSocket() 
	{
		return channel;
	}

	@Override
	public void handleConnect() 
	{
	}

	protected void send(UdpPacket packet) {
		synchronized (packets) {
			packets.offer(packet);
			
			NioInterestTask task = new NioInterestTask(key, SelectionKey.OP_WRITE);
			task.setHandler((NioWorker)acceptor);
			task.async();
		}
	}
	
	protected void unregister(UdpServerClient client) {
		clientMap.remove(client.getAddress());
	}
	
	@Override
	public void handleRead() 
	{
		try {
			SocketAddress from = channel.receive(input);
			input.flip();
			
			if (!input.hasRemaining()) {
				return;
			}
			
			UdpServerClient client = clientMap.get(from);
			if (client == null) {
				client = new UdpServerClient(pipeline, this);
				client.setAddress(from);
				client.setServer(this);
				client.getListeners().add(clientListener);

				invokeAccept(client);
				client.invokeConnect();
				
				clientMap.put(from, client);
			}
			
			client.read(input);
			
			input.clear();
		}
		catch (IOException e) {
			invokeError(e);
			close();
		}
	}

	@Override
	public void handleWrite() 
	{
		try {
			for (;;) {
				UdpPacket packet = null;
				synchronized (packets) {
					packet = packets.poll();
				}
				if (packet == null) {
					break;
				}
				channel.send(packet.getData(), packet.getClient().getAddress());
			}
			key.interestOps(SelectionKey.OP_READ);
		}
		catch (IOException e) {
			invokeError(e);
			close();
		}
	}
	

}