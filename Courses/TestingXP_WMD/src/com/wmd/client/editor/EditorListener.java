package com.wmd.client.editor;

/**
 * A listener for editor events. This will notify the listener when an edit has
 * been resized, when it contains no tokens, etc.
 * 
 * @author Philip Diffenderfer
 * 
 */
public interface EditorListener
{

	/**
	 * Notifies the listener when a token has been added to the editor.
	 * 
	 * @param source
	 *            The editor the token has been added to.
	 * @param token
	 *            The token added to the editor.
	 */
	public void onTokenAdded(Editor source, Token token);

	/**
	 * Notifies the listener when a token has been added to the editor.
	 * 
	 * @param source
	 *            The editor the token has been added to.
	 * @param token
	 *            The token added to the editor.
	 */
	public void onTokenRemoved(Editor source);

	/**
	 * Notifies the listener when the editor has no tokens.
	 * 
	 * @param source
	 *            The editor without any tokens.
	 */
	public void onEmpty(Editor source);

}
