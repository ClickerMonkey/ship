package net.philsprojects.net.nio.udp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListener;
import net.philsprojects.net.StringProtocol;

public class TestUdpServer extends ClientListenerAdapter implements ServerListener
{

	public static void main(String[] args)
	{
		TestUdpServer listener = new TestUdpServer();
		
		Pipeline pipeline = new UdpPipeline();
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setClientListener(listener);
		pipeline.setServerListener(listener);
		
		pipeline.listen(new InetSocketAddress(26524));
		
		new Scanner(System.in).nextLine();
		
		pipeline.getSelector().stop();
	}

	@Override
	public void onServerAccept(Server server, Client client) {
		System.out.format("Server %s accepted client %s\n", server.getAddress(), client.getAddress());
	}

	@Override
	public void onServerBind(Server server, SocketAddress address) {
		System.out.format("Server bound to %s\n", address);
	}

	@Override
	public void onServerError(Server server, Exception e) {
		System.err.print("Server Error: ");
		e.printStackTrace();
	}

	@Override
	public void onServerStart(Server server) {
		System.out.format("Server started on %s\n", server.getAddress());
	}

	@Override
	public void onServerStop(Server server) {
		System.out.format("Server stopped on %s\n", server.getAddress());
	}
	
	@Override
	public void onClientReceive(Client client, Object data) {
		super.onClientReceive(client, data);
		client.send(data);
	}
	
}
