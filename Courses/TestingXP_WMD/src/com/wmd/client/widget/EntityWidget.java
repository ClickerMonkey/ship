package com.wmd.client.widget;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.wmd.client.entity.Entity;

/**
 * An EntityWidget is a Widget with textboxes that correspond to the values for
 * the type of Entity which it contains.
 * 
 * @author AJ Marx, Chris Koch, Phil
 * @param <E>
 *            An Entity type (i.e. Integer, Fraction).
 */
public abstract class EntityWidget<E extends Entity> extends Composite
{

	// The entity that represents the correct answer for this wigdet.
	protected final E correctEntity;

	// The entity that represents the current answer in this widget. The values
	// in this widget will reflect the textboxes of this widget everytime the
	// getEntity() method is invoked.
	protected E entity;

	/**
	 * Initializes a new EntityWidget given its correct entity.
	 * 
	 * @param correctEntity
	 *            The correct entity;
	 */
	protected EntityWidget(E correctEntity, E defaultEntity)
	{
		this.correctEntity = correctEntity;
		this.entity = defaultEntity;
	}

	/**
	 * @return The Entity that represents the correct answer.
	 */
	public final E getCorrectEntity()
	{
		return correctEntity;
	}

	/**
	 * @return The Entity that is represented by the values in this Widget.
	 */
	public final E getEntity()
	{
		this.updateEntity();
		return entity;
	}

	/**
	 * Set the value that will be represented by this Widget.
	 * 
	 * @param entity
	 */
	public final void setEntity(E entity)
	{
		this.entity = entity;
		this.updateWidget();
	}

	/**
	 * Returns whether or not the values in the Widget match the corresponding
	 * values in the correct Entity.
	 * 
	 * @return True if it's correct, false otherwise.
	 */
	public abstract boolean isCorrect();

	/**
	 * Updates the Widget so that the correct values are displayed in the
	 * Widget.
	 */
	public abstract void showAnswer();

	/**
	 * Requests focus for this widget.
	 */
	public abstract void requestFocus();

	/**
	 * Updates the textboxes in this widget to the entity set.
	 */
	protected abstract void updateWidget();

	/**
	 * Updates the entity with the values in this widget.
	 */
	protected abstract void updateEntity();

	/**
	 * Sets the read-ability of this widget.
	 * 
	 * @param readOnly
	 *            Whether the widget should be readonly.
	 */
	public abstract void setReadOnly(boolean readOnly);

	/**
	 * This KeyPressHandler listens to key presses, and prevents any
	 * 'non-allowable' key presses to reach the target. Only numbers, tabs,
	 * spaces, and arrow keys are allowable.
	 * 
	 */
	protected static class NumbersOnly implements KeyPressHandler
	{
		public static final char allowable[] = new char[]
		{ '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '\t', '\b', 37, 38,
				39, 40 };

		public void onKeyPress(KeyPressEvent event)
		{
			char c = event.getCharCode();
			for (char a : allowable)
				if (c == a)
					return;
			event.preventDefault();
		}
	}

	/**
	 * This key listener listens for key presses, and adjusts the size of the
	 * TextBox that this listener listens to.
	 * 
	 * @author AJ Marx, Chris Koch
	 * 
	 */
	public static class AdjustSize implements KeyUpHandler
	{
		private TextBox box;

		/**
		 * @param field
		 *            - The TextBox to be adjusted
		 */
		public AdjustSize(TextBox field)
		{
			box = field;
			box.setVisibleLength(1);
			box.setTextAlignment(TextBoxBase.ALIGN_CENTER);
		}

		public void onKeyUp(KeyUpEvent event)
		{
			box.setVisibleLength(Math.max(1, box.getValue().length()));
		}
	}
}
