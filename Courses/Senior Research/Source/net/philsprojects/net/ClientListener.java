package net.philsprojects.net;

/**
 * A listener to the events of a client.
 *
 * @author Philip Diffenderfer
 *
 */
public interface ClientListener
{

	/**
	 * The client has been accepted or is connecting. This can be used to to
	 * tune the socket parameters before it binds to an address.
	 * 
	 * @param client
	 * 		The client to initialize.
	 */
	public boolean onClientOpen(Client client);
	
	/**
	 * The client has successfully connected and is ready reading and writing.
	 * 
	 * @param client
	 * 		The client that has connected.
	 */
	public void onClientConnect(Client client);

	/**
	 * The client is aboue to read from its socket. This can be used to do 
	 * custom handling of sockets.
	 * 
	 * @param client
	 * 		The client reading.
	 */
	public void onClientRead(Client client);
	
	/**
	 * The client has received an object which was decoded from its input.
	 * 
	 * @param client
	 * 		The client that received the data.
	 * @param data
	 * 		The data received by the client.
	 */
	public void onClientReceive(Client client, Object data);

	/**
	 * The client is about to write to its socket. This can be used to do custom 
	 * handling of sockets.
	 * 
	 * @param client
	 * 		The client writing.
	 */
	public void onClientWrite(Client client);
	
	/**
	 * The client has sent an object - encoded it to its output and will send it
	 * soon.
	 *  
	 * @param client
	 * 		The client that sent the data.
	 * @param data
	 * 		The data sent by the client.
	 */
	public void onClientSend(Client client, Object data);

	/**
	 * The client has attempted to send an object that does not have a matching
	 * protocol in the clients pipeline to properly encode the data.
	 * 
	 * @param client
	 * 		The client thats discarding the data.
	 * @param data
	 * 		The data that could not be encoded.
	 */
	public void onClientDiscard(Client client, Object data);
	
	/**
	 * An error has occurred with the client, this can be thrown from any of the
	 * above events.
	 * 
	 * @param client
	 * 		The client that threw the exception.
	 * @param e
	 * 		The excpetion thrown by performing some action on the client.
	 */
	public void onClientError(Client client, Exception e);
	
	/**
	 * The client has been requested to disconnect. When disconnecting the 
	 * client will refuse any more data to send and will not read from the 
	 * socket. Once all output has been flushed the client closes.
	 * 
	 * @param client
	 * 		The client being disconnected.
	 */
	public void onClientDisconnect(Client client);
	
	/**
	 * The client has been closed. This may happen manually, from disconnecting,
	 * or because an error was thrown and had to close the client.
	 * 
	 * @param client
	 * 		The client that was closed.
	 */
	public void onClientClose(Client client);
	
}