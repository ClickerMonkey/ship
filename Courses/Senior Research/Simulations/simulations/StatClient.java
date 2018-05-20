package simulations;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.stat.StatDatabase;

public class StatClient implements ClientListener 
{
	
	public static StatDatabase db_clients = Stat.client.take("client");
	public static StatDatabase db_between = Stat.client.take("between_messages");
	public static StatDatabase db_duration = Stat.client.take("duration");
	public static StatDatabase db_discards = Stat.client.take("discards");
	public static StatDatabase db_invalid = Stat.client.take("invalid");
	public static StatDatabase db_errors = Stat.client.take("errors");
	public static StatDatabase db_connect = Stat.client.take("connect");
	public static StatDatabase db_read = Stat.client.take("read");
	public static StatDatabase db_write = Stat.client.take("write");
	
	
	public long timeOpen;
	public Queue<Long> timeQueue = new ConcurrentLinkedQueue<Long>();
	public Queue<Object> sentQueue = new ConcurrentLinkedQueue<Object>();
	
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
		db_between.add(seconds(timeQueue.poll(), System.nanoTime()));
		byte[] expected = (byte[])sentQueue.poll();
		byte[] actual = (byte[])data;
		if (!Arrays.equals(expected, actual)) {
			db_invalid.add(1);
		}
	}
	@Override
	public void onClientWrite(Client client) {
		db_write.add(1);
	}
	@Override
	public void onClientSend(Client client, Object data) {
		timeQueue.offer(System.nanoTime());
		sentQueue.offer(data);
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
