package com.wmd.client.service;

public class ServiceException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * You need this to ensure Serializibility.
	 */
	public ServiceException()
	{
	}

	public ServiceException(String msg)
	{
		super(msg);
	}

}
