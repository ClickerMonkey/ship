package examples.echo.oio;

import java.net.InetSocketAddress;
import java.util.Scanner;

import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.oio.tcp.TcpPipeline;


public class TestTcpClient
{

	public static void main(String[] args) 
	{
		Pipeline pipeline = new TcpPipeline();
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());

		Client client = pipeline.connect(new InetSocketAddress(34567));
		
		Scanner input = new Scanner(System.in);
		while (input.hasNextLine()) {
			client.send(input.nextLine());
		}
		
		pipeline.getSelector().stop();
	}
	
}
