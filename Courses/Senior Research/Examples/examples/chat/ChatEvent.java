package examples.chat;

import net.philsprojects.io.bytes.ByteReader;
import net.philsprojects.io.bytes.ByteWriter;
import net.philsprojects.io.bytes.ByteReader.ReadCallback;
import net.philsprojects.io.bytes.ByteWriter.WriteCallback;

public class ChatEvent implements ReadCallback<ChatEvent>, WriteCallback<ChatEvent>
{
	public enum Type {
		Signon, Signoff, JoinRoom, LeaveRoom, Message, GetRooms, GetUsers;
	}
	
	public Type type;
	public String user;
	public String message;
	public String room;
	
	public ChatEvent() {
	}
	
	public ChatEvent(Type type) {
		this(type, null, null, null);
	}
	
	public ChatEvent(Type type, String user) {
		this(type, user, null, null);
	}
	
	public ChatEvent(Type type, String user, String message) {
		this(type, user, message, null);
	}
	
	public ChatEvent(Type type, String user, String message, String room) {
		this.type = type;
		this.user = user;
		this.message = message;
		this.room = room;
	}
	
	public String display() {
		switch (type) {
		case Signon:
			return String.format("%s has signed on.", user);
		case Signoff:
			return String.format("%s has signed off.", user);
		case JoinRoom:
			return String.format("%s has joined %s.", user, room);
		case LeaveRoom:
			return String.format("%s has left %s.", user, room);
		case Message:
			return String.format("%s: %s", user, message);
		}
		return "";
	}
	
	
	@Override
	public ChatEvent read(ByteReader reader) {
		ChatEvent evt = new ChatEvent();
		evt.type = reader.getEnum(Type.class);
		evt.user = reader.getString();
		evt.message = reader.getString();
		evt.room = reader.getString();
		return (reader.isValid() ? evt : null);
	}

	@Override
	public void write(ByteWriter writer, ChatEvent evt) {
		writer.putEnum(evt.type);
		writer.putString(evt.user);
		writer.putString(evt.message);
		writer.putString(evt.room);
	}
	
	@Override
	public String toString() {
		return display();
	}
	
}
