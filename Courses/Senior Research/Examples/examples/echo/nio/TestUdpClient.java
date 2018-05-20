package examples.echo.nio;

import java.net.InetSocketAddress;
import java.util.Scanner;

import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.nio.udp.UdpPipeline;


public class TestUdpClient
{

	public static void main(String[] args) 
	{
		Pipeline pipeline = new UdpPipeline();
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());

		Client client = pipeline.connect(new InetSocketAddress("127.0.0.1", 9876));
		
		Scanner input = new Scanner(System.in);
		while (input.hasNextLine()) {
			client.send(input.nextLine());
		}
		
		pipeline.getSelector().stop();
	}
	
}
