package net.philsprojects.net;

import java.net.SocketAddress;
import java.util.List;
import java.util.Set;

import net.philsprojects.util.ConcurrentSet;
import net.philsprojects.util.State;


/**
 * 
 * TODO set of clients accepted on the server
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Server extends Adapter
{
	public static final int Accepting = State.create(0);
	public static final int Disconnecting = State.create(1);
	public static final int Closed = State.create(2);
	
	
	public Pipeline getPipeline();
	
	public SocketAddress getAddress();
	
	public Object getSocket();
	
	public boolean isClosed();
	public void close();
	
	// stops accepting clients and waits for clients to finish
	// notifies client the server requests a shut down
	public boolean isDisconnecting();
	public void disconnect();
	
	/**
	 * 
	 * @return
	 * @see ConcurrentSet
	 */
	public Set<Client> getClients();
	
	public Acceptor getAcceptor();
	
	public List<ServerListener> getListeners();
	
	public ClientListener getClientListener();
	public void setClientListener(ClientListener listener);
	
	public Selector getSelector();
	public void setSelector(Selector selector);
	
	public State state();
	
}