package com.wmd.client.editor;

/**
 * A factory that creates a specific type of token. The token it creates is
 * empty.
 * 
 * @author Philip Diffenderfer
 * 
 */
public interface TokenFactory
{

	/**
	 * Creates a token of a specific type.
	 * 
	 * @return The token created.
	 */
	public Token createToken(Editor parent);

}
