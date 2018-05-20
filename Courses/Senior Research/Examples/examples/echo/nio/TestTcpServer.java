package examples.echo.nio;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Set;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.nio.tcp.TcpPipeline;

public class TestTcpServer extends ClientListenerAdapter
{
	
	public static void main(String[] args) 
	{
		// Create the pipeline to use TCP and NIO
		Pipeline pipeline = new TcpPipeline();
		
		// Set the default protocol and add it.
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setServerListener(new ServerListenerAdapter());
		pipeline.setClientListener( new ClientListenerAdapter() {
			// Sends each message to all connected clients.
			@Override
			public void onClientReceive(Client client, Object data) 
			{
				// Also echo out the message to the command line.
				super.onClientReceive(client, data);
				
				// Send to all clients.
				Set<Client> clients = client.getServer().getClients();
				for (Client c : clients) {
					c.send(data);
				}
			}
		});
		
		// Start listening on port 34567.
		pipeline.listen(new InetSocketAddress(34567));
		
		// Wait for a newline to stop the server.
		new Scanner(System.in).nextLine();
		
		// Stop the server and close all client connections.
		pipeline.getSelector().stop();
	}
	
	
}
