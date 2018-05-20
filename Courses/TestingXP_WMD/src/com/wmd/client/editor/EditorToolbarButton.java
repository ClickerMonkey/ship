package com.wmd.client.editor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Creates a button to be inserted into the EditorToolbar (for adding entities).
 * 
 * @author Eric C. Abruzzese and Philip Diffenderfer
 * 
 */
public class EditorToolbarButton extends Button implements ClickHandler
{

	/*
	 * The factory used for the creation of the entity (exponent, integer,
	 * fraction, etc)
	 */
	private TokenFactory factory;

	/**
	 * @param label
	 *            The text to be displayed inside the button
	 * @param factory
	 *            The factory of an appropriate for the entity to be inserted.
	 */
	public EditorToolbarButton(String label, TokenFactory factory)
	{
		super.setText(label);
		super.addClickHandler(this);
		this.factory = factory;
	}

	/**
	 * Adds an entity to the most recently focused editor.
	 * 
	 * @param event
	 *            The click event to handle.
	 */
	@Override
	public void onClick(ClickEvent event)
	{
		Editor.addToLastFocused(this.factory);
	}

}
