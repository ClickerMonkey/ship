package net.philsprojects.net.nio;

import java.nio.channels.SelectionKey;

import net.philsprojects.task.Task;

public class NioInterestTask extends Task<SelectionKey> 
{
	
	private SelectionKey key;
	private int interestOps;
	
	public NioInterestTask(SelectionKey key, int interestOps) 
	{
		this.key = key;
		this.interestOps = interestOps;
	}
	
	protected SelectionKey execute() 
	{
		key.interestOps(interestOps);
		return key;
	}
	
}