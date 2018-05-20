package net.philsprojects.net.oio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import net.philsprojects.net.AbstractClient;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;
import net.philsprojects.net.oio.OioWorker;

/**
 * A client for UDP using OIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class UdpClient extends AbstractClient
{

	// The socket of communication with the udp server.
	private DatagramSocket socket;
	
	// The address the client is connected to.
	private SocketAddress address;
	
	// The packet the client uses to send a receive data.
	private DatagramPacket packet;


	/**
	 * Instantiates a new UdpClient.
	 * 
	 * @param pipeline
	 * 		The pipeline the client should use.
	 * @param worker
	 * 		The worker handling the client.
	 * @param server
	 * 		The server which accepted the client, or null if the client was
	 * 		created by the pipeline.
	 */
	protected UdpClient(Pipeline pipeline, Worker worker, Server server) 
	{
		super(pipeline, worker, server);
	}

	/**
	 * Internally sets this client and ensures the input and output buffers for 
	 * this client are sized enough to receive entire packets without loosing
	 * discarded data.
	 * 
	 * @param channel
	 * 		The channel of the client.
	 * @throws IOException
	 * 		An error occurred retrieving the channels socket.
	 */
	protected void setSocket(DatagramSocket socket) throws IOException
	{
		int size = socket.getReceiveBufferSize();
		
		this.socket = socket;
		this.packet = new DatagramPacket(new byte[size], size);
		this.input.pad(socket.getReceiveBufferSize());
		this.output.pad(socket.getSendBufferSize());
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
	 * {@inheritDoc}
	 */
	@Override
	public DatagramSocket getSocket() 
	{
		return socket;
	}		

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClose() throws IOException 
	{
		((OioWorker)worker).setClient(null);
		socket.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onFlush() 
	{
		handleWrite();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRead() throws IOException 
	{
		socket.receive(packet);
		input.drain(packet.getData(), packet.getOffset(), packet.getLength());
		
		if (!input.isEmpty()) {
			decode();	
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWrite() 
	{
		encode();
//		output.fill(socket); // XXX

		if (output.isEmpty()) {
			if (isDisconnecting()) {
				synchronized (pending) {
					if (pending.size() == 0) {
						close();
					}
				}
			}
		}
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
	public SocketAddress getAddress() 
	{
		return address;
	}

}