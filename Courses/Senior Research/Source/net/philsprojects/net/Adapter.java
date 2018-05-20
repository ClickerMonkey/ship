package net.philsprojects.net;

/**
 * An adapter is anything which can read, write, accept, or connect. This is
 * used by workers and acceptors to process clients and servers.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Adapter 
{
	
	/**
	 * Notifies the server/client that it can now read from its socket.
	 */
	public void handleRead();
	
	/**
	 * Notifies the server/client that it can now write to its socket.
	 */
	public void handleWrite();
	
	/**
	 * Notifies the server/client it can now accept clients on its socket.
	 */
	public void handleAccept();
	
	/**
	 * Notifies the server/client it can now complete its pending connection.
	 */
	public void handleConnect();
	
}
