package examples.chat;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import examples.chat.ChatEvent.Type;
import examples.chat.ChatUser.Status;

import net.philsprojects.net.Client;
import net.philsprojects.net.ClientListener;
import net.philsprojects.net.Pipeline;
import net.philsprojects.net.ServerListenerAdapter;
import net.philsprojects.net.nio.tcp.TcpPipeline;

public class ChatServer extends ServerListenerAdapter implements ClientListener 
{
	// Tests:
	// 1. Start several clients
	// 2. Close clients first
	// 3. Close server first

	public static void main(String[] args) 
	{
		ChatServer chat = new ChatServer();

		Pipeline pipeline = new TcpPipeline();
		pipeline.setDefaultProtocol(ChatEvent.class);
		pipeline.addProtocol(ChatEvent.class, new ChatProtocol());
		pipeline.setClientListener(chat);
		pipeline.setServerListener(chat);

		pipeline.listen(new InetSocketAddress(8998));
		
		new Scanner(System.in).nextLine();
		
		pipeline.getSelector().stop();
	}
	
	private Set<ChatUser> users = new HashSet<ChatUser>();
	private Map<String, ChatRoom> rooms = new HashMap<String, ChatRoom>();
	
	public ChatServer() {
		rooms.put("Room#1", new ChatRoom("Room#1"));
		rooms.put("Room#2", new ChatRoom("Room#2"));
	}
	
	@Override
	public void onClientConnect(Client end) {
		System.out.format("Client at %s connected.\n", end.getAddress());
		users.add(new ChatUser(end));
	}

	@Override
	public void onClientReceive(Client end, Object data) {
		System.out.format("Client at %s sent [%s]\n", end.getAddress(), data);
		
		ChatEvent event = (ChatEvent)data;
		ChatUser user = (ChatUser)end.attachment();
		ChatRoom room = user.room;
		
		switch (event.type) {
		case LeaveRoom:
			user.leave();
			break;
		case JoinRoom:
			ChatRoom join = rooms.get(event.room); 
			user.room = join;
			join.add(user);
			break;
		case Signon:
			user.name = event.user;
			user.status = Status.Online;
			break;
		case Signoff:
			if (user.status == Status.Online) {
				logoff(user);
			}
			break;
		}

		if (room != null) {
			room.broadcast(event);
		}
		else {
			for (ChatUser other : users) {
				other.send(event);
			}
		}

		print(event.display());
	}

	@Override
	public void onClientClose(Client end) {
		ChatUser user = (ChatUser)end.attachment();
		if (user.status == Status.Online) {
			logoff(user);
		}
		users.remove(user);
		System.out.format("Client at %s disconnected.\n", end.getAddress());
	}
	
	private void logoff(ChatUser user) {
		user.leave();
		ChatEvent event = new ChatEvent(Type.Signoff, user.name);
		for (ChatUser other : users) {
			if (other != user) {
				other.send(event);
			}
		}
		users.remove(user);
		user.client.close();
		user.status = Status.Offline;
	}
	
	private void print(String s) {
		if (s != null && s.trim().length() > 0) {
			System.out.println(s);
		}
	}

	@Override
	public void onClientSend(Client end, Object data) {

	}

	@Override
	public void onClientError(Client end, Exception e) { 
		System.err.println("Client Error");
//		e.printStackTrace();
	}
	
	@Override
	public void onClientRead(Client end) { 
		System.out.format("Client at %s read.\n", end.getAddress());
	}

	@Override
	public void onClientWrite(Client end) { 
		System.out.format("Client at %s written.\n", end.getAddress());		
	}

	@Override
	public void onClientDiscard(Client client, Object data) {
		System.out.format("Client at %s could not write [%s], it has been discarded.", client.getAddress(), data);
	}
	
	public void onClientDisconnect(Client client) { 
		
	}
	
	public boolean onClientOpen(Client client) { 
		return true; 
	}


}
