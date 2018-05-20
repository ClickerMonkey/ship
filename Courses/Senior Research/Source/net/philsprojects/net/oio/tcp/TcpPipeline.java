package net.philsprojects.net.oio.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import net.philsprojects.io.buffer.BufferFactoryBinary;
import net.philsprojects.net.AbstractPipeline;
import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListener;
import net.philsprojects.net.oio.OioAcceptor;
import net.philsprojects.net.oio.OioSelector;
import net.philsprojects.net.oio.OioWorker;

/**
 * A pipeline which uses the TCP protocol and OIO.
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
		this(new OioSelector());
	}

	/**
	 * Instantiates a TcpPipeline.
	 * 
	 * @param selector
	 * 		The selector for the pipeline providing workers and acceptors.
	 */
	public TcpPipeline(OioSelector selector) 
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
		
		OioWorker worker = (OioWorker)selector.getWorker();
		
		TcpClient client = newClient(worker, null);
		if (clientListener != null) {
			client.getListeners().add(clientListener);	
		}
		try {
			Socket socket = new Socket();

			client.setSocket(socket);
			client.state().set(Client.Connecting);
			client.invokeOpen();
			
			socket.connect(address);

			client.state().set(Client.Connected);

			client.invokeConnect();
			
			worker.setClient(client);
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
		
		OioAcceptor acceptor = (OioAcceptor)selector.getAcceptor();
		
		TcpServer server = newServer(acceptor, address);
		if (serverListener != null) {
			server.getListeners().add(serverListener);	
		}
		server.setClientListener(clientListener);
		try {
			ServerSocket socket = new ServerSocket();

			server.setSocket(socket);
			server.invokeStart();
			
			socket.bind(address);
			
			server.state().set(Server.Accepting);
			server.invokeBind(address);
			
			acceptor.setServer(server);
		}
		catch (IOException e) {
			server.invokeError(e);
		}
		return server;
	}

}
