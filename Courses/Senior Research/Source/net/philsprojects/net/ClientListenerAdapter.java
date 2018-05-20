package net.philsprojects.net;

/**
 * A basic ClientListener which prints all events out to stdout.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ClientListenerAdapter implements ClientListener 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientConnect(Client client) 
	{
		System.out.format("Client at %s connected.\n", client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientClose(Client client) 
	{
		System.out.format("Client at %s disconnected.\n", client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientError(Client client, Exception e) 
	{
		System.err.print("Client Error: ");
		e.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientReceive(Client client, Object data) 
	{
		System.out.format("Client at %s received [%s]\n", client.getAddress(), data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientSend(Client client, Object data) 
	{
		System.out.format("Client at %s sent [%s]\n", client.getAddress(), data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientRead(Client client) 
	{
		System.out.format("Before client %s read.\n", client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientWrite(Client client) 
	{
		System.out.format("Before client %s write.\n", client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientDiscard(Client client, Object data)
	{
		System.out.format("Client at %s could not write [%s], it has been discarded.", client.getAddress(), data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClientDisconnect(Client client) 
	{
		System.out.format("Client at %s has been requested to disconnect.\n", client.getAddress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onClientOpen(Client client) 
	{
		System.out.format("Client at %s initialized.\n", client.getAddress());
		return true;
	}

}
