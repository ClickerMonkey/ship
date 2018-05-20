package net.philsprojects.net;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.philsprojects.util.ConcurrentSet;
import net.philsprojects.util.State;

/**
 * An abstract implementation of a Server.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class AbstractServer implements Server 
{
	
	// The pipeline of the server.
	protected final Pipeline pipeline;

	// The acceptor handling the server.
	protected final Acceptor acceptor;
	
	// The set of clients this server has accepted and are still connected.
	protected final Set<Client> clients;
	
	// The address the server is bound to.
	protected final SocketAddress address;
	
	// The state of the server.
	protected final State state;
	
	// The selector the server uses to create workers for accepted clients.
	protected Selector selector;
	
	// The listener of events on the server.
	protected List<ServerListener> serverListeners;
	
	// The default listener of events for client created by the server.
	protected ClientListener clientListener;
	
	
	/**
	 * Instantiates a new AbstractServer.
	 * 
	 * @param acceptor
	 * 		The acceptor handling the server.
	 * @param selector
	 * 		The selector the server uses to create workers for accepted clients.
	 * @param pipeline
	 * 		The pipeline of the server.
	 * @param address
	 * 		The address the server is bound to.
	 */
	public AbstractServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		this.selector = selector;
		this.pipeline = pipeline;
		this.acceptor = acceptor;
		this.address = address;
		this.clients = new ConcurrentSet<Client>();
		this.state = new State(Closed);
		this.serverListeners = new ArrayList<ServerListener>(1);
		this.clientListener = pipeline.getClientListener();
		
		this.register();
	}


	/**
	 * Registers this client with its worker, server, and pipeline.
	 */
	private void register()
	{
		if (acceptor != null) {
			acceptor.getServers().add(this);	
		}
		pipeline.getServers().add(this);
	}
	
	/**
	 * Unregisters this client with its worker, server, and pipeline.
	 */
	private void unregister()
	{
		if (acceptor != null) {
			acceptor.getServers().remove(this);	
		}
		pipeline.getServers().remove(this);
	}

	/**
	 * Handles the closing of the server. This will only be invoked once.
	 */
	protected abstract void onClose() throws IOException;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void close()
	{
		synchronized (state) 
		{
			if (state.has(Accepting | Disconnecting)) 
			{
				state.set(Closed);
				
				for (Client client : clients) {
					client.close();
				}
				
				try {
					onClose();	
				}
				catch (IOException e) {
					invokeError(e);
				}

				invokeStop();
				
				unregister();
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isClosed()
	{
		return state.equals(Closed);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Set<Client> getClients()
	{
		return clients;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final State state()
	{
		return state;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Acceptor getAcceptor()
	{
		return acceptor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Selector getSelector() 
	{
		return selector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setSelector(Selector selector) 
	{
		this.selector = selector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ServerListener> getListeners()
	{
		return serverListeners;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ClientListener getClientListener() 
	{
		return clientListener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setClientListener(ClientListener listener) 
	{
		this.clientListener = listener;;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final SocketAddress getAddress() 
	{
		return address;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Pipeline getPipeline() 
	{
		return pipeline;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isDisconnecting()
	{
		return state.equals(Disconnecting);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void disconnect()
	{
		state.cas(Accepting, Disconnecting);
	}
	
	public void invokeStart() {
		for (ServerListener sl : serverListeners) {
			sl.onServerStart(this);
		}
	}
	
	public void invokeBind(SocketAddress address) {
		for (ServerListener sl : serverListeners) {
			sl.onServerBind(this, address);
		}
	}
	
	public void invokeAccept(Client client) {
		for (ServerListener sl : serverListeners) {
			sl.onServerAccept(this, client);
		}
	}
	
	public void invokeError(Exception e) {
		for (ServerListener sl : serverListeners) {
			sl.onServerError(this, e);
		}
	}
	
	public void invokeStop() {
		for (ServerListener sl : serverListeners) {
			sl.onServerStop(this);
		}
	}
	
}