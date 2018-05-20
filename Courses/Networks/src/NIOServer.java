import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class NIOServer implements Runnable {

	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer();
		server.bind(9876);
		server.start();
		
		Scanner input = new Scanner(System.in);
		while (!input.nextLine().equals("exit")) {
			// wait for exit
		}
		
		server.stop();
	}
	
	private volatile boolean running;
	private Thread thread;
	private Selector selector;
	private ServerSocketChannel server;
	
	public NIOServer() {
		thread = new Thread(this);
	}
	
	public void bind(int port) throws IOException {
		selector = Selector.open();
		
		server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void start() {
		if (!running) {
			running = true;
			thread.start();	
		}	
	}
	
	public void stop() {
		if (running) {
			running = false;
			selector.wakeup();	
		}
	}
	
	public void run() {
		while (running) {
			
			// If no events occurred continue only if running.
			if (!select()) {
				continue;
			}
			
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			
			while (keys.hasNext()) {
				SelectionKey key =  keys.next();
				keys.remove();
				
				key.interestOps(key.interestOps() & ~key.readyOps());
				
				if (key.isAcceptable()) {
					handleAccept(key);
				} else if (key.isConnectable()) {
					handleConnect(key);
				} else {
					if (key.isReadable()) {
						handleRead(key);
					}
					if (key.isValid() && key.isWritable()) {
						handleWrite(key);
					}
				}				
			}
		}
	}
	
	/**
	 * Wait for atleast 1 I/O event to occur, or for the selector to
	 * be interrupted.
	 */
	private boolean select() 
	{
		int keys = 0;
		try {
			// Performs the actual blocking waiting 
			keys = selector.select();
		}
		catch (IOException e) {
			// Swallow exceptions
		}
		// Return true if any events occured. This may be 0 if something has
		// requested to stop the server.
		return (keys > 0);
	}
	 
	private void handleAccept(SelectionKey key) {
		try {
			SocketChannel client = server.accept();
			client.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			key.cancel();
		}
	}
	
	private void handleConnect(SelectionKey key) {
		try {
			key.channel().register(selector, SelectionKey.OP_WRITE);
		} catch (ClosedChannelException e) {
			key.cancel();
		}
	}
	
	private void handleRead(SelectionKey key) {
		
	}
	
	private void handleWrite(SelectionKey key) {
		
	}
	
}
