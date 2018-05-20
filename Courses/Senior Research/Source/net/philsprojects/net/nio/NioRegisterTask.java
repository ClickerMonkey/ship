package net.philsprojects.net.nio;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import net.philsprojects.task.Task;

public class NioRegisterTask extends Task<SelectionKey> 
{
	
	private SelectableChannel channel;
	private int interestOps;
	private Object attachment;
	
	public NioRegisterTask(SelectableChannel channel, int interestOps, Object attachment) 
	{
		this.channel = channel;
		this.interestOps = interestOps;
		this.attachment = attachment;
	}
	
	protected SelectionKey execute() 
	{
		SelectionKey key = null;
		try {
			Selector selector = ((NioWorker)getHandler()).getSelector();
			key = channel.register(selector, interestOps, attachment);	
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		}
		return key;
	}
	
}