package net.philsprojects.net.nio.udp;

import java.nio.ByteBuffer;

import net.philsprojects.net.Client;

public class UdpPacket 
{

	private Client client;
	private ByteBuffer data;
	
	public UdpPacket(Client client, ByteBuffer data)
	{
		this.client = client;
		this.data = data;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public ByteBuffer getData() 
	{
		if (!data.hasRemaining()) {
			data.flip();
		}
		return data;
	}
	
}
