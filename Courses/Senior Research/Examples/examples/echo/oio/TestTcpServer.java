package examples.echo.oio;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Set;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.oio.tcp.TcpPipeline;

public class TestTcpServer extends ClientListenerAdapter
{
	
	public static void main(String[] args) 
	{
		Pipeline pipeline = new TcpPipeline();
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setServerListener(new ServerListenerAdapter());
		pipeline.setClientListener( new TestTcpServer() );
		
		pipeline.listen(new InetSocketAddress(34567));
		
		new Scanner(System.in).nextLine();
		
		pipeline.getSelector().stop();
	}
	
	@Override
	public void onClientReceive(Client client, Object data) 
	{
		super.onClientReceive(client, data);
		
		Set<Client> clients = client.getServer().getClients();
		
		for (Client c : clients) {
			c.send(data);
		}
	}
	
}
