package net.philsprojects.net.nio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.Worker;
import net.philsprojects.net.nio.tcp.TcpPipeline;

public class TestNioWorker extends BaseTest 
{

	@Test
	public void testDefault() throws IOException
	{
		NioWorker w = new NioWorker();
		
		assertTrue( w.isRunning() );
		assertNotNull( w.getSelector() );
		assertTrue( w.getSelector().isOpen() );
		assertTrue( w.isUnused() );
		assertTrue( w.isReusable() );
		assertEquals( 0, w.getClients().size() );
		assertEquals( 0, w.getServers().size() );
		
		w.stop();
	}
	
	@Test
	public void testServers()
	{
		broadcast();
		
		NioSelector selector = new NioSelector();
		selector.start();
		
		Pipeline pipeline = new TcpPipeline(selector);
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setClientListener(new ClientListenerAdapter());
		pipeline.setServerListener(new ServerListenerAdapter());

		Acceptor accept = selector.getAcceptor();
		
		assertEquals( 0, accept.getServers().size() );
		
		Server server = pipeline.listen(new InetSocketAddress(8998));
		
		assertEquals( 1, accept.getServers().size() );
		assertTrue( accept.getServers().contains(server) );
		
		server.close();

		assertEquals( 0, accept.getServers().size() );
		assertFalse( accept.getServers().contains(server) );
		
		selector.stop();
	}
	
	@Test
	public void testClients()
	{
		broadcast();
		
		NioSelector selector = new NioSelector();
		selector.start();
		
		Pipeline pipeline = new TcpPipeline(selector);
		pipeline.setDefaultProtocol(String.class);
		pipeline.addProtocol(String.class, new StringProtocol());
		pipeline.setClientListener(new ClientListenerAdapter());
		pipeline.setServerListener(new ServerListenerAdapter());

		Worker work = selector.getWorker();
		
		assertEquals( 0, work.getClients().size() );
		
		SocketAddress address = new InetSocketAddress(8998);
		Server server = pipeline.listen(address);
		Client client = pipeline.connect(address);

		sleep(1000);
		
		// the client and server side connections exist over one worker
		assertEquals( 2, work.getClients().size() );
		assertTrue( work.getClients().contains(client) );
		
		sleep(500);
		
		client.close();

		sleep(500);
		
		assertEquals( 0, work.getClients().size() );
		assertFalse( work.getClients().contains(client) );
		
		server.close();
		selector.stop();
	}
	
}
