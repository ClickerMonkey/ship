package examples.chat;

import java.util.HashSet;
import java.util.Set;


public class ChatRoom 
{

	public final String name;
	private Set<ChatUser> users;
	
	public ChatRoom(String name) {
		this.name = name;
		this.users = new HashSet<ChatUser>();
	}
	
	public void broadcast(ChatEvent event) {
		for (ChatUser user : users) {
			user.client.send(event);
		}
	}
	
	public void add(ChatUser user) {
		users.add(user);
	}
	
	public void remove(ChatUser user) {
		users.remove(user);
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public String toString() {
		return name;
	}
	
}
