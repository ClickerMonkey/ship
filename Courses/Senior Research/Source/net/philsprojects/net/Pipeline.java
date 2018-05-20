package net.philsprojects.net;

import java.net.SocketAddress;
import java.util.Set;

import net.philsprojects.io.buffer.BufferFactory;
import net.philsprojects.util.ConcurrentSet;

/**
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Pipeline 
{
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	public Server listen(SocketAddress address);
	
	/**
	 * 
	 * @param address
	 * @param serverListener
	 * @return
	 */
	public Server listen(SocketAddress address, ServerListener serverListener);
	
	
	/**
	 * 
	 * @param address
	 * @return
	 */
	public Client connect(SocketAddress address);
	
	/**
	 * 
	 * @param address
	 * @param clientListener
	 * @return
	 */
	public Client connect(SocketAddress address, ClientListener clientListener);

	
	/**
	 * 
	 * @return
	 * @see ConcurrentSet
	 */
	public Set<Server> getServers();
	
	/**
	 * 
	 * @return
	 * @see ConcurrentSet
	 */
	public Set<Client> getClients();
	
	
	/**
	 * Returns the default protocol data type. This is the first protocol 
	 * clients of pipeline.
	 * 
	 * @return
	 */
	public Class<?> getDefaultProtocol();
	
	/**
	 * 
	 * @param type
	 */
	public void setDefaultProtocol(Class<?> type);

	
	/**
	 * 
	 * @return
	 */
	public Selector getSelector();
	
	/**
	 * TODO remove?
	 * @param selector
	 */
	public void setSelector(Selector selector);
	
	
	/**
	 * 
	 * @param <D>
	 * @param type
	 * @param protocol
	 */
	public <D> void addProtocol(Class<D> type, Protocol<D> protocol);
	
	/**
	 * 
	 * @param <D>
	 * @param type
	 * @return
	 */
	public <D> Protocol<D> getProtocol(Class<D> type);
	
	
	/**
	 * 
	 * @return
	 */
	public ClientListener getClientListener();
	
	/**
	 * 
	 * @param listener
	 */
	public void setClientListener(ClientListener listener);
	
	
	/**
	 * Returns the factory which creates clients for this pipeline and its 
	 * servers.
	 * 
	 * @return
	 */
	public ClientFactory getClientFactory();
	
	/**
	 * Sets the factory which creates clients for this pipeline and its servers.
	 * This is typically done once by the 
	 * TODO remove?
	 * 
	 * @param factory
	 * 		The new f
	 */
	public void setClientFactory(ClientFactory factory);
	
	
	/**
	 * 
	 * @return
	 */
	public ServerListener getServerListener();
	
	/**
	 * 
	 * @param listener
	 */
	public void setServerListener(ServerListener listener);
	
	
	/**
	 * 
	 * @return
	 */
	public ServerFactory getServerFactory();
	
	/**
	 * TODO remove?
	 * @param factory
	 */
	public void setServerFactory(ServerFactory factory);
	
	
	/**
	 * 
	 * @return
	 */
	public BufferFactory getBufferFactory();
	
	/**
	 * 
	 * @param factory
	 */
	public void setBufferFactory(BufferFactory factory);
	
}