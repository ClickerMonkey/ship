package net.philsprojects.net.nio.tcp;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.net.ClientListenerAdapter;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Protocol;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.StringProtocol;
import net.philsprojects.net.nio.NioSelector;

public class TestTcpPipeline extends BaseTest 
{
	
	private NioSelector selector;
	private Pipeline pipe;
	private Protocol<String> protocol;
	
	@Before
	public void testBefore() {
		selector = new NioSelector();
		selector.start();
		
		protocol = new StringProtocol();
		
		pipe = new TcpPipeline(selector);
		pipe.setDefaultProtocol(String.class);
		pipe.addProtocol(String.class, protocol);
		pipe.setClientListener(new ClientListenerAdapter());
		pipe.setServerListener(new ServerListenerAdapter());
	}
	
	@After
	public void testAfter() {
		selector.stop();
	}

	@Test
	public void testDefault()
	{
		broadcast();
		assertNotNull( pipe.getBufferFactory() );

		assertSame( pipe.getClientFactory().getClass(), TcpClientFactory.class );
		assertSame( pipe.getServerFactory().getClass(), TcpServerFactory.class );
		
		assertSame( protocol, pipe.getProtocol(String.class) );
	
		assertSame( selector, pipe.getSelector() );
		
		assertSame( String.class, pipe.getDefaultProtocol() );
	}
	
	@Test
	public void testListen()
	{
		broadcast();
		SocketAddress address = new InetSocketAddress(3874); 
		
		Server server = pipe.listen(address);
		
		assertNotNull( server.getAcceptor() );
		assertSame( address, server.getAddress() );
		assertSame( pipe, server.getPipeline() );
		assertNotNull( server.getClientListener() );
		assertNotNull( server.getSocket() );
		assertNotNull( server.getListeners().get(0) );
		assertFalse( server.isClosed() );
		
		server.close();
		assertTrue( server.isClosed() );
	}
	
	@Test
	public void testConnect()
	{
		broadcast();
		SocketAddress address = new InetSocketAddress(3874); 
		
		Server server = pipe.listen(address);
		sleep(500);
		Client client = pipe.connect(address);
		sleep(100);
		client.close();
		sleep(100);
		server.close();
	}
	
	@Test
	public void testFewClients()
	{
		broadcast();

		final int CLIENTS = 10;
		final SocketAddress address = new InetSocketAddress(3874); 

		Server server = pipe.listen(address);
		server.setClientListener(new ClientListener() {
			public void onClientReceive(Client client, Object data) {
				client.send(data);
			}
			public void onClientConnect(Client client) { }
			public void onClientSend(Client client, Object data) { }
			public void onClientWrite(Client client) { }
			public void onClientRead(Client client) { }
			public void onClientError(Client client, Exception e) { }
			public void onClientClose(Client client) { }
			public void onClientDiscard(Client client, Object data) { }
			public void onClientDisconnect(Client client) { }
			public boolean onClientOpen(Client client) { return true; }
		});
		
		final AtomicInteger sent = new AtomicInteger();
		
		GroupTask.initialize(CLIENTS);
		GroupTask.add(new Runnable() {
			public void run() {
				Client client = pipe.connect(address, new ClientListener() {
					public void onClientReceive(Client client, Object data) {
						String msg = (String)data;
						if (msg.length() > 1) {
							client.send(msg.substring(1));
							sent.incrementAndGet();
						}
						else {
							client.close();
						}
					}
					public void onClientConnect(Client client) { }
					public void onClientSend(Client client, Object data) { }
					public void onClientWrite(Client client) { }
					public void onClientRead(Client client) { }
					public void onClientError(Client client, Exception e) { }
					public void onClientClose(Client client) { }
					public void onClientDiscard(Client client, Object data) { }
					public void onClientDisconnect(Client client) { }
					public boolean onClientOpen(Client client) { return true; }
				});
				client.send("Hello world, for each character in this string a message will be echoed back.");
			}
		}, CLIENTS);
		GroupTask.execute();
		
		sleep(1000);
		
		System.out.println(sent.get());
	}
	
}
