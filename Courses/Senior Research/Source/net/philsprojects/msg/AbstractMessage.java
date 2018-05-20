package net.philsprojects.msg;

import net.philsprojects.net.Client;

public class AbstractMessage implements Message {

	protected Client client;
	protected final int typeId;
	
	public AbstractMessage(int typeId) {
		this.typeId = typeId;
	}

	public int getType() {
		return typeId;
	}
	
	public Client getSource() {
		return client;
	}

	public void setSource(Client client) {
		this.client = client;
	}

}
