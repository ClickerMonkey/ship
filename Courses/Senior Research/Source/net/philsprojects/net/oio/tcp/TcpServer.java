package net.philsprojects.net.oio.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import net.philsprojects.net.AbstractServer;
import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Worker;
import net.philsprojects.net.oio.OioAcceptor;
import net.philsprojects.net.oio.OioWorker;

public class TcpServer extends AbstractServer
{

	private ServerSocket serverSocket;
	
	public TcpServer(Selector selector, Pipeline pipeline, Acceptor acceptor, SocketAddress address) 
	{
		super(selector, pipeline, acceptor, address);
	}

	protected void setSocket(ServerSocket socket) 
	{
		this.serverSocket = socket;
	}

	@Override
	public void handleAccept() 
	{
		if (!state.equals(Accepting)) {
			return;
		}
		
		try {

			Socket socket = serverSocket.accept();

			OioWorker worker = (OioWorker)selector.getWorker();
			
			if (worker == null) {
				socket.close();
				return;
			}
			
			TcpClient client = newClient(worker);
			if (clientListener != null) {
				client.getListeners().add(clientListener);	
			}
			
			client.setSocket(socket);	
			client.state().set(Client.Connecting);
			
			invokeAccept(client);
			
			client.invokeOpen();

			client.state().set(Client.Connected);
			
			worker.setClient(client);
			
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
	public void onClose() throws IOException 
	{
		((OioAcceptor)acceptor).setServer(null);
		serverSocket.close();
	}

	@Override
	public ServerSocket getSocket() 
	{
		return serverSocket;
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
