package net.philsprojects.net.oio.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import net.philsprojects.net.AbstractClient;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.nio.udp.UdpPacket;

/**
 * A simulated client for UDP with OIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class UdpServerClient extends AbstractClient 
{

	// The server handling all I/O for this simulated client.
	private UdpServer server;
	
	// The address of the client.
	private SocketAddress address;
	

	/**
	 * Instantiates a new UdpServerClient.
	 * 
	 * @param pipeline
	 * 		The pipeline the client should use.
	 * @param server
	 * 		The server which accepted the client, or null if the client was
	 * 		created by the pipeline.
	 */
	protected UdpServerClient(Pipeline pipeline, Server server) 
	{
		// No worker, the server handles reading and writing.
		super(pipeline, null, server);
	}

	/**
	 * Internally sets the address of the client.
	 * 
	 * @param address
	 * 		The address of the client.
	 */
	protected void setAddress(SocketAddress address) 
	{
		this.address = address;
	}

	/**
	 * Internally sets the server of the client.
	 * 
	 * @param server
	 * 		The server which handles all reading and writing for this client.
	 */
	protected void setServer(UdpServer server) 
	{ 
		this.server = server;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onFlush() 
	{
		if (state.equals(Connected)) {
			invokeWrite();
			
			encode();
			
			if (output.size() > 0)
			{
				ByteBuffer data = bufferFactory.allocate(output.size());
				data.put(output.getReader());
				server.send(new UdpPacket(this, data));
				output.clear();	
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClose() throws IOException
	{
		server.unregister(this);
	}

	/**
	 * Reads the packet into this clients input.
	 * 
	 * @param packet
	 * 		The packet to read in.
	 */
	protected void read(ByteBuffer packet)
	{
		input.drain(packet);
		invokeRead();
		if (!input.isEmpty()) {
			decode();	
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SocketAddress getAddress() 
	{
		return address;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSocket() 
	{
		// The servers socket is this clients socket technically.
		return server.getSocket();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onConnect() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRead() 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWrite() 
	{
	}

}
