package net.philsprojects.net.nio.tcp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import net.philsprojects.io.buffer.BufferFactoryBinary;
import net.philsprojects.net.AbstractPipeline;
import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListener;
import net.philsprojects.net.nio.NioRegisterTask;
import net.philsprojects.net.nio.NioSelector;
import net.philsprojects.net.nio.NioWorker;

/**
 * A pipeline which uses the TCP protocol and NIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TcpPipeline extends AbstractPipeline 
{

	/**
	 * Instantiates a new TcpPipeline. This method of instantiating should only
	 * be done if the application has a single pipeline OR the pipelines are
	 * based off of different technologies (NIO, OIO). The other method is
	 * preferred, this method is to keep single pipeline development simpler.
	 */
	public TcpPipeline() 
	{
		this(new NioSelector());
	}
	
	/**
	 * Instantiates a TcpPipeline.
	 * 
	 * @param selector
	 * 		The selector for the pipeline providing workers and acceptors.
	 */
	public TcpPipeline(NioSelector selector) 
	{
		super(selector);
		this.setBufferFactory(new BufferFactoryBinary(8, 16));
		this.setClientFactory(new TcpClientFactory());
		this.setServerFactory(new TcpServerFactory());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client connect(SocketAddress address, ClientListener clientListener) 
	{
		selector.start();
		
		NioWorker worker = (NioWorker)selector.getWorker();
		
		TcpClient client = newClient(worker, null);
		if (clientListener != null) {
			client.getListeners().add(clientListener);	
		}
		try {
			SocketChannel channel = SocketChannel.open();
			channel.configureBlocking(false);

			client.setSocket(channel);
			client.state().set(Client.Connecting);
			
			client.invokeOpen();
			
			channel.connect(address);
			
			NioRegisterTask task = new NioRegisterTask(channel, SelectionKey.OP_CONNECT, client);
			task.setHandler(worker);
			client.setKey( task.sync() );
		}
		catch (IOException e) {
			client.invokeError(e);
		}
		return client;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Server listen(SocketAddress address, ServerListener serverListener) 
	{
		selector.start();
		
		NioWorker acceptor = (NioWorker)selector.getAcceptor();
		
		TcpServer server = newServer(acceptor, address);
		if (serverListener != null) {
			server.getListeners().add(serverListener);	
		}
		server.setClientListener(clientListener);
		try {
			ServerSocketChannel channel = ServerSocketChannel.open();	
			channel.configureBlocking(false);
			
			server.setSocket(channel);
			server.invokeStart();
			
			channel.socket().bind(address);
			
			server.state().set(Server.Accepting);
			server.invokeBind(address);
			
			NioRegisterTask task = new NioRegisterTask(channel, SelectionKey.OP_ACCEPT, server);
			task.setHandler(acceptor);
			server.setKey( task.sync() );
		}
		catch (IOException e) {
			server.invokeError(e);
		}
		return server;
	}
	
	
}