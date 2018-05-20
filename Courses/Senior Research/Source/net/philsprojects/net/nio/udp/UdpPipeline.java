package net.philsprojects.net.nio.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

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
 * A pipeline which uses the UDP protocol and NIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class UdpPipeline extends AbstractPipeline 
{

	/**
	 * Instantiates a new UdpPipeline. This method of instantiating should only
	 * be done if the application has a single pipeline OR the pipelines are
	 * based off of different technologies (NIO, OIO). The other method is
	 * preferred, this method is to keep single pipeline development simpler.
	 */
	public UdpPipeline() 
	{
		this (new NioSelector());
	}
	
	/**
	 * Instantiates a new UdpPipeline.
	 * 
	 * @param selector
	 * 		The selector for the pipeline providing workers and acceptors.
	 */
	public UdpPipeline(NioSelector selector) 
	{
		super(selector);
		this.setBufferFactory(new BufferFactoryBinary(8, 16));
		this.setClientFactory(new UdpClientFactory());
		this.setServerFactory(new UdpServerFactory());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client connect(SocketAddress address, ClientListener clientListener) 
	{
		selector.start();
		
		NioWorker worker = (NioWorker)selector.getWorker();
		
		UdpClient client = newClient(worker, null);
		if (clientListener != null) {
			client.getListeners().add(clientListener);	
		}
		try {
			DatagramChannel channel = DatagramChannel.open();
			channel.configureBlocking(false);

			client.setSocket(channel);
			client.setAddress(address);
			client.state().set(Client.Connecting);
			client.invokeOpen();
			
			channel.connect(address);
			
			client.state().set(Client.Connected);

			client.invokeConnect();
			
			NioRegisterTask task = new NioRegisterTask(channel, SelectionKey.OP_READ, client);
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
		
		UdpServer server = newServer(acceptor, address);
		if (serverListener != null) {
			server.getListeners().add(serverListener);	
		}
		server.setClientListener(clientListener);
		try {
			DatagramChannel channel = DatagramChannel.open();
			channel.configureBlocking(false);

			server.setSocket(channel);
			server.invokeStart();

			channel.socket().bind(address);
			
			server.state().set(Server.Accepting);
			server.invokeBind(address);
			
			NioRegisterTask task = new NioRegisterTask(channel, SelectionKey.OP_READ, server);
			task.setHandler(acceptor);
			server.setKey( task.sync() );
		}
		catch (IOException e) {
			server.invokeError(e);
		}
		return server;
	}
	
}