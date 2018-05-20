package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Stores a period name.
 * @author Christopher Eby, William Fisher
 */
public class PeriodMsg implements IsSerializable
{
	// Stores the period name
	private String periodName;

	/**
	 * Empty constructor for serializability.
	 */
	public PeriodMsg()
	{
		// Empty
	}
	
	/**
	 * Instantiates a new PeriodMsg.
	 * 
	 * @param periodName The name of the period.
	 */
	public PeriodMsg(String periodName)
	{
		this.periodName = periodName;
	}

	/**
	 * Sets the period name.
	 * 
	 * @param periodName
	 *            String
	 */
	public void setPeriodName(String periodName)
	{
		this.periodName = periodName;
	}

	/**
	 * Gets the period name.
	 * 
	 * @return String
	 */
	public String getPeriodName()
	{
		return this.periodName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof PeriodMsg)
		{
			PeriodMsg msg = (PeriodMsg)o;
			return msg.getPeriodName().equals(periodName);
		}
		return false;
	}
	
}
