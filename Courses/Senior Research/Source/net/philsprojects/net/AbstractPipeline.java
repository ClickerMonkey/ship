package net.philsprojects.net;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.philsprojects.io.buffer.BufferFactory;
import net.philsprojects.util.ConcurrentSet;

/**
 * A basic implementation of a Pipeline.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class AbstractPipeline implements Pipeline 
{
	
	// The map of protocols to their class type.
	protected final Map<Class<?>, Protocol<?>> protocols;
	
	// The set of servers started by this pipeline.
	protected final Set<Server> servers;
	
	// The set of clients connected to servers by this pipeline.
	protected final Set<Client> clients;
	
	// The default client listener for all clients.
	protected ClientListener clientListener;
	
	// The default server listener for all servers.
	protected ServerListener serverListener;
	
	// The factory which creates clients.
	protected ClientFactory clientFactory;
	
	// The factory which creates servers.
	protected ServerFactory serverFactory;
	
	// The factory which handles allocation and deallocated of ByteBuffers.
	protected BufferFactory bufferFactory;
	
	// The default (initial) protocol of a client.
	protected Class<?> defaultProtocol;
	
	// The selector used to get Acceptors and Workers.
	protected Selector selector;
	
	
	
	/**
	 * Instantiates an AbstractPipeline.
	 * 
	 * @param selector
	 * 		The selector the pipeline uses to get Acceptors and Workers.
	 */
	public AbstractPipeline(Selector selector) 
	{
		this.selector = selector;
		this.protocols = new HashMap<Class<?>, Protocol<?>>(); 
		this.servers = new ConcurrentSet<Server>();
		this.clients = new ConcurrentSet<Client>();
//		this.serverListener = new ServerListenerAdapter();
//		this.clientListener = new ClientListenerAdapter();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client connect(SocketAddress address) 
	{
		return connect(address, clientListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Server listen(SocketAddress address) 
	{
		return listen(address, serverListener);
	}

	/**
	 * Returns a new client.
	 * 
	 * @param <T>
	 * 		The type of server.
	 * @return
	 * 		The reference to a new client.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Client> T newClient(Worker worker, Server server) 
	{
		return (T)clientFactory.newClient(this, worker, server);
	}
	
	/**
	 * Returns a new server.
	 * 
	 * @param <T>
	 * 		The type of server.
	 * @param address
	 * 		The binded address of the server.
	 * @return
	 * 		The reference to a new server.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Server> T newServer(Acceptor acceptor, SocketAddress address) 
	{
		return (T)serverFactory.newServer(selector, this, acceptor, address);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Server> getServers()
	{
		return servers;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Client> getClients()
	{
		return clients;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientListener getClientListener() 
	{
		return clientListener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setClientListener(ClientListener listener) 
	{
		this.clientListener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServerListener getServerListener() 
	{
		return serverListener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setServerListener(ServerListener listener) 
	{
		this.serverListener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServerFactory getServerFactory() 
	{
		return serverFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setServerFactory(ServerFactory factory) 
	{
		this.serverFactory = factory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientFactory getClientFactory() 
	{
		return clientFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setClientFactory(ClientFactory factory) 
	{
		this.clientFactory = factory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferFactory getBufferFactory() 
	{
		return bufferFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBufferFactory(BufferFactory factory) 
	{
		this.bufferFactory = factory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Selector getSelector() 
	{
		return selector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelector(Selector selector) 
	{
		this.selector = selector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <D> void addProtocol(Class<D> type, Protocol<D> protocol) 
	{
		protocols.put(type, protocol);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <D> Protocol<D> getProtocol(Class<D> type) 
	{
		return (Protocol<D>)protocols.get(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultProtocol(Class<?> type) 
	{
		this.defaultProtocol = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getDefaultProtocol() 
	{
		return defaultProtocol;
	}
	
}