package net.philsprojects.msg;

import net.philsprojects.net.Client;

public interface Message 
{
	public int getType();
	public Client getSource();
	public void setSource(Client client);
}
