package net.philsprojects.net.oio;

import java.util.Set;

import net.philsprojects.net.Acceptor;
import net.philsprojects.net.Server;
import net.philsprojects.task.TaskService;
import net.philsprojects.util.ConcurrentSet;
import net.philsprojects.util.NonNullRef;

public class OioAcceptor extends TaskService implements Acceptor
{

	private final NonNullRef<Server> serverRef;
	private final Set<Server> servers;
	
	public OioAcceptor() 
	{
		
		this.serverRef = new NonNullRef<Server>();
		this.servers = new ConcurrentSet<Server>();
		
		start();
	}
	
	@Override
	public void awake()
	{
		super.awake();
		if (serverRef.has()) {
			serverRef.get().close();
			serverRef.set(null);
		}
	}
	
	public void setServer(Server server)
	{
		serverRef.set(server);
	}
	
	public boolean hasServer() {
		return serverRef.has();
	}
	
	@Override
	protected void onExecute()
	{
		Server server = null;
		if (release.enter()) {
			try {
				server = serverRef.get();	
			}
			finally {
				release.exit();
			}
		}
		
		if (server != null) {
			if (release.enter()) {
				try {
					handleServer(server);
				}
				finally {
					release.exit();	
				}
			}
		}
	}
	
	private void handleServer(Server server)
	{
		if (!server.isClosed()) {
			if (release.enter()) {
				try {
					server.handleAccept();		
				}
				finally {
					release.exit();
				}
			}
		}
		if (server.isClosed()) {
			serverRef.set(null);
		}
	}

	@Override
	public Set<Server> getServers() {
		return servers;
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
		return !serverRef.has();
	}
	
}
