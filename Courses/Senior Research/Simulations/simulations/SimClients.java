package simulations;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Random;

import net.philsprojects.net.ByteProtocol;
import net.philsprojects.net.Client;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.nio.NioSelector;
import net.philsprojects.service.ServiceInterrupt;
import net.philsprojects.stat.StatDatabase;
import net.philsprojects.stat.StatService;

public class SimClients 
{

	public static Random random = new Random();
	
	static class Range {
		public final int min, max, gap;
		public Range(int x) {
			min = max = x;
			gap = 1;
		}
		public Range(int a, int b) {
			min = (a < b ? a : b);
			max = (a > b ? a : b);
			gap = max - min + 1;
		}
		public int rnd() {
			return random.nextInt(gap) + min;
		}
	}
	
	static class SimClient {
		Client client;
		StatClient listener;
		int messages;
		long next;
		public SimClient() {
			listener = new StatClient();
			messages = MESSAGES.rnd();
			next = System.currentTimeMillis() + CLIENT_DELAY.rnd();
		}
		public void send(Pipeline pipe) {
			if (messages > 0 && System.currentTimeMillis() >= next) {
				if (client == null) {
					client = pipe.connect(address, listener);
				}
				byte[] data = new byte[MESSAGE_SIZE.rnd()];
				random.nextBytes(data);
				client.send(data);
				next += MESSAGE_DELAY.rnd();
				messages--;
			}
		}
		public boolean finished() {
			return (messages == 0 && listener.sentQueue.isEmpty());
		}
	}

	
	public static SocketAddress address;

	public static final Range[][] SIMULATIONS = {
		/* s1: Chat Server */ {	
			/*CLIENTS*/new Range(1000), 
			/*CLIENT_DELAY*/new Range(0, 10000),
			/*MESSAGES*/new Range(10),
			/*MESSAGE_SIZE*/new Range(16, 128),
			/*MESSAGE_DELAY*/new Range(10000)
		},

		/* s2: Game Server (light load) */ {
			/*CLIENTS*/new Range(10),
			/*CLIENT_DELAY*/new Range(0, 50),
			/*MESSAGES*/new Range(1000),
			/*MESSAGE_SIZE*/new Range(64, 128),
			/*MESSAGE_DELAY*/new Range(50),
		},

		/* s3: Game Server (heavy load) */ {
			/*CLIENTS*/new Range(100),
			/*CLIENT_DELAY*/new Range(0, 50),
			/*MESSAGES*/new Range(1000),
			/*MESSAGE_SIZE*/new Range(64, 128),
			/*MESSAGE_DELAY*/new Range(50),
		},

		/* s4: File Server */ {
			/*CLIENTS*/new Range(5),
			/*CLIENT_DELAY*/new Range(0, 10000),
			/*MESSAGES*/new Range(5),
			/*MESSAGE_SIZE*/new Range(1024, 65536),
			/*MESSAGE_DELAY*/new Range(10000),
		},

		/* s5: Web Server (light load) */ {
			/*CLIENTS*/new Range(100),
			/*CLIENT_DELAY*/new Range(0, 10000),
			/*MESSAGES*/new Range(1),
			/*MESSAGE_SIZE*/new Range(128, 4096),
			/*MESSAGE_DELAY*/new Range(0),
		},
		
		/* s6: Web Server (heavy load) */ {
			/*CLIENTS*/new Range(1000),
			/*CLIENT_DELAY*/new Range(0, 10000),
			/*MESSAGES*/new Range(1),
			/*MESSAGE_SIZE*/new Range(128, 4096),
			/*MESSAGE_DELAY*/new Range(0),
		},
	};


	public static Range CLIENTS;
	public static Range CLIENT_DELAY;
	public static Range MESSAGES;
	public static Range MESSAGE_SIZE;
	public static Range MESSAGE_DELAY;
	
	
	public static void main(String[] args) throws Exception
	{
		// Initialize Stats
		new Stat(); 

		// First argument = PORT
		int PORT = Integer.parseInt(args[0]);
		
		// Second argument = HOST
		String HOST = args[1];
		address = new InetSocketAddress( HOST, PORT );
		
		// Third argument = SIMULATION#
		int N = Integer.parseInt(args[2]);
		CLIENTS = SIMULATIONS[N][0];
		CLIENT_DELAY = SIMULATIONS[N][1];
		MESSAGES = SIMULATIONS[N][2];
		MESSAGE_SIZE = SIMULATIONS[N][3];
		MESSAGE_DELAY = SIMULATIONS[N][4];

		System.out.format("Connected to host %s:%d running simulation %d\n", HOST, PORT, N);
		
		/* NIO SETUP */ 
		NioSelector selector = new NioSelector();
		selector.getWorkers().setCapacity( 2 );
		Pipeline pipe = new net.philsprojects.net.nio.tcp.TcpPipeline(selector);
		/**/
		
		/* OIO SETUP * / 
		OioSelector selector = new OioSelector();
		Pipeline pipe = new net.philsprojects.net.oio.tcp.TcpPipeline(selector);
		/**/
		
		// The pipeline!
		pipe.setDefaultProtocol(byte[].class);
		pipe.addProtocol(byte[].class, new ByteProtocol());
		
		final long STARTED = System.currentTimeMillis();
		
		// Connect all clients.
		SimClient[] sim = new SimClient[ CLIENTS.rnd() ];
		for (int i = 0; i < sim.length; i++) {
			sim[i] = new SimClient();
		}

		boolean finished = false;
		long stopTime = Long.MAX_VALUE;
		while (!finished) {
			finished = true;
			long closest = Long.MAX_VALUE;
			int remaining = 0;
			for (int c = 0; c < sim.length; c++) {
				boolean done = sim[c].finished();
				if (!done) {
					sim[c].send(pipe);
					if (sim[c].messages > 0) {
						closest = Math.min(closest, sim[c].next);	
					}
					remaining += sim[c].messages;
				}
				finished &= done;
			}
			
			if (remaining % 10 == 0) {
				System.out.println(remaining);	
			}
			
			if (remaining == 0 && stopTime == Long.MAX_VALUE) {
				stopTime = System.currentTimeMillis();
			}
			
			if ((System.currentTimeMillis() - stopTime) >= 5000) {
				finished = true;
			}
			
			long wait = closest - System.currentTimeMillis();
			long actual = (remaining == 0 ? 10 : wait);
			if (closest != Long.MAX_VALUE && actual > 0) {
				Thread.sleep(actual);
			}
		}
		System.out.println("All data sent and received");
		
		
		int missed = 0;
		for (int c = 0; c < sim.length; c++) {
			missed += sim[c].listener.sentQueue.size();
		}
		System.out.format("%d messages missed.\n", missed);
		
		
		for (int c = 0; c < sim.length; c++) {
			sim[c].client.close();
		}
		System.out.println("Clients closed");
		
		// Stop the statistics thread as well
		StatService.get().stop(ServiceInterrupt.None, true, Long.MAX_VALUE);
		System.out.println("Statistics thread stopped");
		
		// Flush data to store
		for (StatDatabase db : Stat.client.getDatabases()) {
			db.getStore().flush();
		}
		
		// Export all stats to CSV
		Stat.exportAll();
		System.out.println("Statistics exported");
		
		// Stop all threads.
		selector.stop();
		System.out.println("Selector stopped");
		
		// Total runtime
		System.out.format("Testing ran for %.2f seconds.\n", (System.currentTimeMillis() - STARTED) * 0.001);
	}

}
