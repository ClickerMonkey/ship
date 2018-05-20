package simulations;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Scanner;

import net.philsprojects.net.ByteProtocol;
import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.Selector;
import net.philsprojects.net.Server;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.nio.NioSelector;
import net.philsprojects.net.oio.OioSelector;
import net.philsprojects.service.ServiceInterrupt;
import net.philsprojects.stat.StatDatabase;
import net.philsprojects.stat.StatService;
import net.philsprojects.util.Files;

public class SimServer extends ServerListenerAdapter 
{
	
	public static void main(String[] args) throws Exception
	{
		// Initialize Stats
		new Stat();
	
		// First argument = PORT
		int PORT = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		
		Pipeline pipe = null;
		Selector selector = null;
		/* NIO SETUP */
		if (N == 0) {
			selector = new NioSelector();
			selector.getWorkers().setCapacity( 5 );
			pipe = new net.philsprojects.net.nio.tcp.TcpPipeline((NioSelector)selector);
		}
		else {
			selector = new OioSelector();
			selector.getWorkers().setMinCapacity( 5 );
			selector.getWorkers().setMaxCapacity( 10000 );
			selector.getWorkers().setAllocateSize( 5 );
			pipe = new net.philsprojects.net.oio.tcp.TcpPipeline((OioSelector)selector);
		}
		
		pipe.setDefaultProtocol(byte[].class);
		pipe.addProtocol(byte[].class, new ByteProtocol());
		pipe.setServerListener(new SimServer());
		
		// Start accepting clients!
		Server server = pipe.listen(new InetSocketAddress( PORT ));
		
		// Wait for exit message
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) 
		{
			String cmd = in.nextLine();
			// Backup all server statistics to a given location
			if (cmd.equals("backup")) {
				System.out.print("Dst: ");
				
				// Flush all stat data out to their files first
				for (StatDatabase db : Stat.server.getDatabases()) {
					db.getStore().flush();
				}
				
				String backup = in.nextLine();

				File src = Stat.server.getDirectory();
				File dst = new File(backup);
				
				Files.copy(src, dst);
				
				System.out.format("[%s] backed up to [%s]\n", src.getAbsolutePath(), dst.getAbsolutePath());
			}
			else if (cmd.equals("clear stats")) {
			}
			else if (cmd.equals("exit")) {
				break;
			}
			else {
				System.out.println("Commands: backup, exit");
			}
		}
		
		// Close the server
		server.close();
		System.out.println("Server closed");
		
		// Export all stats to CSV
		Stat.exportAll();
		System.out.println("Statistics exported");
		
		// Stop selector
		selector.stop();
		System.out.println("Selector stopped");

		// Stop the statistics thread as well
		StatService.get().stop(ServiceInterrupt.None, true, Long.MAX_VALUE);
		System.out.println("Statistics thread stopped");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onServerAccept(Server server, Client client) 
	{
		super.onServerAccept(server, client);
		
		// Add the client listener which tracks statistics.
		client.getListeners().clear();
		client.getListeners().add(new StatServerClient());
	}
	
}
