package net.philsprojects.net.nio.tcp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import net.philsprojects.net.AbstractServer;
import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Worker;
import net.philsprojects.net.nio.NioRegisterTask;
import net.philsprojects.net.nio.NioWorker;

public class TcpServer extends AbstractServer 
{
	
	protected ServerSocketChannel server;
	protected SelectionKey key;
	
	protected TcpServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		super(selector, pipeline, acceptor, address);
	}
	
	protected void setSocket(ServerSocketChannel server) 
	{
		this.server = server;
	}
	
	protected void setKey(SelectionKey key) 
	{
		this.key = key;
	}
	
	@Override
	public void handleAccept() 
	{
		if (!state.equals(Accepting)) {
			return;
		}
		
		NioWorker worker = (NioWorker)selector.getWorker();
		try {
			TcpClient client = newClient(worker);
			if (clientListener != null) {
				client.getListeners().add(clientListener);	
			}
			
			SocketChannel channel = server.accept();
			channel.configureBlocking(false);
			
			client.setSocket(channel);
			client.state().set(Client.Connecting);

			invokeAccept(client);
			
			client.invokeOpen();
			
			client.state().set(Client.Connected);
			
			NioRegisterTask task = new NioRegisterTask(channel, SelectionKey.OP_READ, client);
			task.setHandler(worker);
			client.setKey( task.sync() );
			
			client.invokeConnect();
		}
		catch (IOException e) {
			invokeError(e);
		}
	}
	
	private TcpClient newClient(Worker worker) 
	{
		return ((TcpPipeline)pipeline).newClient(worker, this);
	}

	@Override
	protected void onClose() throws IOException
	{
		server.close();	
		key.cancel();
	}
	
	@Override
	public ServerSocketChannel getSocket() 
	{
		return server;
	}

	@Override
	public void handleConnect() 
	{
	}

	@Override
	public void handleRead() 
	{
	}

	@Override
	public void handleWrite() 
	{
	}
	
}