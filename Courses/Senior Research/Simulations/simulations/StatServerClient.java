package simulations;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.stat.StatDatabase;

public class StatServerClient implements ClientListener 
{
	public static StatDatabase db_clients = Stat.server.take("client");
	public static StatDatabase db_between = Stat.server.take("between_messages");
	public static StatDatabase db_duration = Stat.server.take("duration");
	public static StatDatabase db_discards = Stat.server.take("discards");
	public static StatDatabase db_errors = Stat.server.take("errors");
	public static StatDatabase db_connect = Stat.server.take("connect");
	public static StatDatabase db_read = Stat.server.take("read");
	public static StatDatabase db_write = Stat.server.take("write");
	
	
	private long timeOpen;
	private Queue<Long> timeQueue = new ConcurrentLinkedQueue<Long>();
	
	private float seconds(long snanos, long enanos) {
		return (enanos - snanos) * 0.000000001f;
	}

	@Override
	public boolean onClientOpen(Client client) {
		timeOpen = System.nanoTime();
		db_clients.add(1);
		return true;
	}
	@Override
	public void onClientConnect(Client client) {
		db_connect.add(seconds(timeOpen, System.nanoTime()));
	}
	@Override
	public void onClientRead(Client client) {
		db_read.add(1);
	}
	@Override
	public void onClientReceive(Client client, Object data) {
		timeQueue.offer(System.nanoTime());
		client.send(data);
	}
	@Override
	public void onClientWrite(Client client) {
		db_write.add(1);
	}
	@Override
	public void onClientSend(Client client, Object data) {
		db_between.add(seconds(timeQueue.poll(), System.nanoTime()));
	}
	@Override
	public void onClientDiscard(Client client, Object data) {
		db_discards.add(1);
	}
	@Override
	public void onClientError(Client client, Exception e) {
		db_errors.add(1);
	}
	@Override
	public void onClientDisconnect(Client client) {
		
	}
	@Override
	public void onClientClose(Client client) {
		db_duration.add(seconds(timeOpen, System.nanoTime()));
	}
	
	
	
}
