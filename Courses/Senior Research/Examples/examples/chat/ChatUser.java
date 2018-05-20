package examples.chat;

import examples.chat.ChatEvent.Type;
import net.philsprojects.net.Client;

public class ChatUser 
{

	public enum Status {
		Pending, Online, Offline;
	}
	
	public String name;
	public Client client;
	public ChatRoom room;
	public Status status = Status.Pending;
	
	public ChatUser(Client end) {
		client = end;
		end.attach(this);
	}
	
	public void leave() {
		if (room != null) {
			room.remove(this);
			room = null;
		}
	}
	
	public void send(ChatEvent event) {
		client.send(event);
	}
	
	public void signOff() {
		client.send(new ChatEvent(Type.Signoff, name));
		client.disconnect();
	}
	
	public int hashCode() {
		return (name == null ? super.hashCode() : name.hashCode());
	}
	
	public String toString() {
		return (name == null ? super.toString() : name);
	}
	
}
