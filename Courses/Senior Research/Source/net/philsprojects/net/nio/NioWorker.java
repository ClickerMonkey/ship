package net.philsprojects.net.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Adapter;
import net.philsprojects.net.Client;
import net.philsprojects.net.Server;
import net.philsprojects.net.Worker;
import net.philsprojects.task.Task;
import net.philsprojects.task.TaskService;
import net.philsprojects.util.ConcurrentSet;

/**
 * 
 * @author Philip Diffenderfer
 *
 */
public class NioWorker extends TaskService implements Worker, Acceptor
{
	
	//
	private final Set<Client> clients;
	
	//
	private final Set<Server> servers;
	
	//
	private Selector selector;
	
	/**
	 * 
	 * @throws IOException
	 */
	public NioWorker() throws IOException 
	{
		super(false);
		
		this.selector = Selector.open();
		
		this.clients = new ConcurrentSet<Client>();
		this.servers = new ConcurrentSet<Server>();
		
		start();
	}
	
	/**
	 * 
	 * @return
	 */
	public Selector getSelector() 
	{
		return selector;
	}
	
	/**
	 * The service is already registered with the release, invoke the parents 
	 * awake and this services awake by nudging the selector from blocking.
	 */
	@Override
	public void awake()
	{
		super.awake();
		selector.wakeup();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean addEvent(Task<?> task) 
	{
		boolean added = super.addEvent(task); 
		if (added) {
			selector.wakeup();
		}
		return added;
	}

	/**
	 * 
	 */
	@Override
	protected void onExecute() 
	{
		try {
			// Enter the blockable area if the service has been interrupted.
			if (release.enter()) {
				try {
					// Select all ready events...
					selector.select();	
				}
				// If select throws an error (which will happen  its blocking
				// and this service has been requested to pause or stop).
				finally {
					release.exit();	
				}
			}
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
		
		// Only iterate the events if some were given (uninterrupted).
		Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
		
		// Iterate through each selected key in the set...
		while (keys.hasNext()) 
		{
			// Grab the next key and remove it from the set.
			SelectionKey key = keys.next();
			keys.remove();
			
			// If the key has been cancelled, skip over it.
			if (!key.isValid()) {
				continue;
			}
			
			// The adapter that handles all events.
			Adapter adapter = (Adapter)key.attachment();
			
			// If the key is acceptable assume its attachment is a Server.
			if (key.isAcceptable()) {
				// Accept the connection, then continue processing keys.
				adapter.handleAccept();
			}
			else {
				// If the key is connectable, handle the connection.
				if (key.isConnectable()) {
					adapter.handleConnect();
				}
				// If the key is readable then handle reading, but only if
				// the key is still valid. The previous connect may have
				// cancelled the key.
				if (key.isValid() && key.isReadable()) {
					adapter.handleRead();
				}
				// If the key is writable then handle writing, but only
				// if the key is still valid. The previous reading or
				// connecting may have cancelled the key.
				if (key.isValid() && key.isWritable()) {
					adapter.handleWrite();
				}
			}
		}
	}

	/**
	 * 
	 */
	@Override
	protected void onStart() 
	{
		// If the selector has not been started...
		if (selector == null || !selector.isOpen())
		{
			try {
				selector = Selector.open();
			}
			catch (IOException e) {
				// TODO
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 */
	@Override
	protected void onStop() 
	{
		try {
			// Go through each key registered with the selector.
			for (SelectionKey key : selector.keys()) 
			{
				// Invoke the close method on the server or client to ensure its
				// sockets are closed, key is cancelled, and listeners are
				// notified that it has been closed.
				Object obj = key.attachment();
				if (obj instanceof Server) {
					((Server)obj).close();
				}
				if (obj instanceof Client) {
					((Client)obj).close();
				}
			}
			// Close the selector.
			selector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPause() 
	{
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume()
	{
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Client> getClients() 
	{
		return clients;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Server> getServers() 
	{
		return servers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReusable() 
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUnused() 
	{
		return clients.isEmpty() && servers.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void free() 
	{
		stop();
	}
	
}