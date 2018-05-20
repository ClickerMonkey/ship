package net.philsprojects.net.nio.tcp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import net.philsprojects.net.AbstractClient;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;
import net.philsprojects.net.nio.NioInterestTask;
import net.philsprojects.net.nio.NioWorker;

/**
 * A client which uses the TCP protocol and NIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TcpClient extends AbstractClient
{
	
	// The selectable channel registered to some selector to receive events.
	private SocketChannel channel;
	
	// The key registered to the client.
	private SelectionKey key;

	/**
	 * Instantiates a new TcpClient.
	 * 
	 * @param pipeline
	 * 		The pipeline the client should use.
	 * @param worker
	 * 		The worker handling the client.
	 * @param server
	 * 		The server which accepted the client, or null if the client was
	 * 		created by the pipeline.
	 */
	protected TcpClient(Pipeline pipeline, Worker worker, Server server) 
	{
		super(pipeline, worker, server);
	}

	/**
	 * Internally sets the socket of this client.
	 * 
	 * @param channel
	 * 		The socket of the client.
	 */
	protected void setSocket(SocketChannel channel) 
	{
		this.channel = channel;
	}

	/**
	 * Internally sets the selection key of this client.
	 * 
	 * @param key
	 * 		The selection key of this client.
	 */
	protected void setKey(SelectionKey key) 
	{
		this.key = key;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SocketChannel getSocket() 
	{
		return channel;
	}		
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClose() throws IOException
	{
		channel.close();
		key.cancel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onFlush() 
	{
		NioInterestTask task = new NioInterestTask(key, SelectionKey.OP_WRITE);
		task.setHandler((NioWorker)worker);
		task.async();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRead() throws IOException 
	{
		if (input.drain(channel) < 0) {
			close();
		}
		else if (!input.isEmpty()) {
			decode();	
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWrite() throws IOException
	{
		encode();
		output.fill(channel);
		
		if (output.isEmpty()) {
			if (isDisconnecting()) {
				synchronized (pending) {
					if (pending.size() == 0) {
						close();
					}
				}
			}
			else {
				key.interestOps(SelectionKey.OP_READ);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onConnect() throws IOException  
	{
		channel.finishConnect();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SocketAddress getAddress() 
	{
		return channel.socket().getRemoteSocketAddress();
	}

}