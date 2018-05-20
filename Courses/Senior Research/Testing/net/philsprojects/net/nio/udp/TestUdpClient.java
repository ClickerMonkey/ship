package net.philsprojects.net.nio.udp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.StringProtocol;

public class TestUdpClient extends ClientListenerAdapter
{

	public static void main(String[] args) 
	{
		// Listens for client events.
		TestUdpClient listener = new TestUdpClient();
		
		// Creates a selector and initializes the pipeline.
		Pipeline pipeline = new UdpPipeline();
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setClientListener(listener);
		
		// This is the IP of this machine, if it changes the IP must be updated.
		SocketAddress address = new InetSocketAddress("157.160.141.126", 26524); 
		
		// Connect to the UDP server at the given address.
		Client client = pipeline.connect(address);
		
		// For each line of input, send to the server.
		Scanner input = new Scanner(System.in);
		while (input.hasNextLine()) {
			client.send(input.nextLine());
		}
		
		// Stop the selector
		pipeline.getSelector().stop();
	}

}
