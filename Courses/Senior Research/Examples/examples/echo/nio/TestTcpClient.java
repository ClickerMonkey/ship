package examples.echo.nio;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

import net.philsprojects.net.ByteProtocol;
import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.nio.tcp.TcpPipeline;


public class TestTcpClient implements ClientListener
{

	public static void main(String[] args) 
	{
		// Create the pipeline to use TCP and NIO
		Pipeline pipeline = new TcpPipeline();
		
		// Set the default protocol and add it.
		pipeline.setDefaultProtocol(byte[].class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.addProtocol(byte[].class, new ByteProtocol());
		pipeline.setClientListener(new TestTcpClient());

		// Connect to the server on 34567.
		Client client = pipeline.connect(new InetSocketAddress("www.philsprojects.net", 6078));
		
		// For every line typed send to the server.
		Scanner input = new Scanner(System.in);
		while (input.hasNextLine()) {
			client.send(input.nextLine().getBytes());
		}
		
		// Once input has stopped disconnect the client.
		pipeline.getSelector().stop();
	}

	private long timeOpen;
	private Queue<Long> timeQueue = new ArrayDeque<Long>();

	private double elapsed(long s, long e) {
		return (e - s) * 0.000000001;
	}
	
	@Override
	public boolean onClientOpen(Client client) {
		timeOpen = System.nanoTime();
		return true;
	}

	@Override
	public void onClientConnect(Client client) {
		System.out.format("Client connected in %fs.\n", elapsed(timeOpen, System.nanoTime()));
	}

	@Override
	public void onClientDiscard(Client client, Object data) {
		System.out.format("Unknown data [%s] discarded.\n", data);
	}

	@Override
	public void onClientRead(Client client) {
		
	}

	@Override
	public void onClientReceive(Client client, Object data) {
		System.out.format("Response time %f\n", elapsed(timeQueue.poll(), System.nanoTime()));
	}

	@Override
	public void onClientWrite(Client client) {
		
	}


	@Override
	public void onClientSend(Client client, Object data) {
		timeQueue.offer(System.nanoTime());
	}

	@Override
	public void onClientError(Client client, Exception e) {
		System.err.println("Client error");
		e.printStackTrace();
	}

	@Override
	public void onClientDisconnect(Client client) {
		System.out.println("Disconnect requested.");
	}

	@Override
	public void onClientClose(Client client) {
		System.out.format("Client closed after being connected for %fs\n", elapsed(timeOpen, System.nanoTime()));
	}
	
}
