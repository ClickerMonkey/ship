package net.philsprojects.net.oio;

import java.util.Set;

import net.philsprojects.net.Client;
import net.philsprojects.net.Worker;
import net.philsprojects.task.TaskService;
import net.philsprojects.util.ConcurrentSet;
import net.philsprojects.util.NonNullRef;

public class OioWorker extends TaskService implements Worker
{

	private final NonNullRef<Client> clientRef;
	private final Set<Client> clients;
	
	public OioWorker() 
	{
		super(false);
		
		this.clientRef = new NonNullRef<Client>();
		this.clients = new ConcurrentSet<Client>();
		
		start();
	}
	
	@Override
	public void awake()
	{
		super.awake();
		clientRef.wakeup();
		if (clientRef.has()) {
			clientRef.get().close();
			clientRef.set(null);
		}
	}
	
	public void setClient(Client client)
	{
		clientRef.set(client);
	}
	
	public boolean hasClient() {
		return clientRef.has();
	}
	
	@Override
	protected void onExecute()
	{
		Client client = null;
		if (release.enter()) {
			try {
				client = clientRef.get();
			}
			finally {
				release.exit();
			}
		}
		
		if (client != null) {
			if (release.enter()) {
				try {
					handleClient(client);
				}
				finally {
					release.exit();	
				}
			}
		}
	}
	
	private void handleClient(Client client)
	{
		if (!client.isClosed()) {
			if (release.enter()) {
				try {
					client.handleRead();	
				}
				finally {
					release.exit();
				}
			}
		}
		if (client.isClosed()) {
			clientRef.set(null);
		}
	}
	
	@Override
	public Set<Client> getClients() {
		return clients;
	}
	
	@Override
	public void free() {
		stop();
	}

	@Override
	public boolean isReusable() {
		return false;
	}

	@Override
	public boolean isUnused() {
		return !clientRef.has();
	}
	
	
	
}
