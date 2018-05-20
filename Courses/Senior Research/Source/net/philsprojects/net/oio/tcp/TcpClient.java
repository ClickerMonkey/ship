package net.philsprojects.net.oio.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import net.philsprojects.net.AbstractClient;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;
import net.philsprojects.net.oio.OioWorker;

/**
 * A client which uses the TCP protocol and OIO.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TcpClient extends AbstractClient
{

	// The socket of the client.
	private Socket socket;
	
	// The stream to read input from the socket.
	private InputStream inputStream;
	
	// The stream to write output to the socket.
	private OutputStream outputStream;

	
	/**
	 * Instantiates a new TcpClient.
	 * 
	 * @param pipeline
	 * 		The pipeline the client should use.
	 * @param worker
	 * 		The worker handling the client.
	 * @param server
	 * 		The server which accepted the client, or null if the client was
	 * 		created by the pipeline.
	 */
	protected TcpClient(Pipeline pipeline, Worker worker, Server server) 
	{
		super(pipeline, worker, server);
	}

	/**
	 * Internally sets the socket of this client.
	 * 
	 * @param socket
	 * 		The socket of the client.
	 */
	protected void setSocket(Socket socket) throws IOException 
	{
		this.socket = socket;
		this.inputStream = new BufferedInputStream(socket.getInputStream());
		this.outputStream = new BufferedOutputStream(socket.getOutputStream());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSocket() 
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
	protected void onFlush() 
	{
		handleWrite();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRead() throws IOException 
	{
		// Continually read in bytes from the input stream...
		for (;;) {
			// Returns either -1 or the number of bytes read
			int read = input.drain(inputStream);
			// End of stream? close the connection
			if (read < 0) {
				close();
				break;
			}
			// If a message has finally been decoded break out. This
			// ensures each handleRead reads in one message at a time.
			if (decode() > 0) {
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWrite() throws IOException 
	{
		encode();
		output.fill(outputStream);
		outputStream.flush();

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
		return socket.getRemoteSocketAddress();
	}

}
