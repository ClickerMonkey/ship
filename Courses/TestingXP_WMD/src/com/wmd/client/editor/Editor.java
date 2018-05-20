package com.wmd.client.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Decimal;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Exponent;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Newline;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.SquareRoot;
import com.wmd.client.entity.Symbol;
import com.wmd.client.entity.Text;
import com.wmd.client.entity.Unit;

/**
 * An editor contains a list of tokens (which in turn may be editors). An editor
 * consists of a HorizontalPanel wrapped with a FocusPanel.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class Editor extends Token implements KeyPressHandler, FocusHandler,
BlurHandler, EditorListener
{

	/**
	 * The minimum width of an editor in pixels (with respect to font size).
	 */
	public static final float MIN_WIDTH = 2.0f / 3.0f;

	/**
	 * The minimum height of an editor in pixels (with respect to font size).
	 */
	public static final float MIN_HEIGHT = 3.0f / 2.0f;

	/**
	 * The editor which was last focused.
	 */
	private static Editor editorFocused = null;

	// A mapping of keys to hot key listeners. A hot key is a combination of the
	// Control key and a given character.
	private final HashMap<Character, HotKeyListener> hotKeys;

	// The list of listeners waiting for events on this editor.
	private final List<EditorListener> listeners = new LinkedList<EditorListener>();

	// The container holding the tokens.
	private final HorizontalPanel box = new HorizontalPanel();

	// The panel that enables focus on this editor.
	private final FocusPanel panel = new FocusPanel(box);

	// Whether this editor is currently focused.
	private boolean focused = false;

	/**
	 * Initializes a new Editor without a parent editor.
	 */
	public Editor()
	{
		this(null);
	}

	/**
	 * Initializes a new Editor.
	 * 
	 * @param parent
	 *            The parent to the editor.
	 */
	public Editor(Editor parent)
	{
		super(parent);

		// The focus panel is the main widget
		initWidget(panel);

		// Make sure the box has proper styles
		box.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		box.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

		// Listen to all focusable events
		panel.addKeyPressHandler(this);
		panel.addFocusHandler(this);
		panel.addBlurHandler(this);

		// Listen to itself.
		addListener(this);

		// Add the default hot keys.
		hotKeys = new HashMap<Character, HotKeyListener>();
		// Ctrl-f = Adds fraction
		hotKeys.put('f', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(FractionToken.getFactory());
			}
		});
		// Ctrl-e = Adds exponent
		hotKeys.put('e', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(ExponentToken.getFactory());
			}
		});
		// Ctrl-q = Adds equation
		hotKeys.put('q', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(Editor.getFactory());
			}
		});
		// Ctrl-i = Adds Integer
		hotKeys.put('i', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(IntegerToken.getFactory());
			}
		});
		// Ctrl-Enter = Adds newline
		hotKeys.put('\n', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(NewlineToken.getFactory());
			}
		});
		// Ctrl-d = Adds decimal
		hotKeys.put('d', new HotKeyListener()
		{
			public void onHotKeyPressed(char key)
			{
				add(DecimalToken.getFactory());
			}
		});

		// Ensures this editor is the minimum size
		ensureMinimumSize();
	}

	/**
	 * Adds a listener for events on this editor.
	 * 
	 * @param listener
	 *            The listener to add.
	 */
	public void addListener(EditorListener listener)
	{
		if (listener != null)
		{
			listeners.add(listener);
		}
	}

	/**
	 * Adds a hot key listener for a specific key combination (+Ctrl).
	 * 
	 * @param key
	 *            The key that triggers the listener.
	 * @param listener
	 *            The listener to notify when the hot key is pressed.
	 */
	public void addHotKeyListener(char key, HotKeyListener listener)
	{
		hotKeys.put(key, listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void widgetInitialized(Widget widget, Style style)
	{
		style.setVerticalAlign(VerticalAlign.MIDDLE);
		style.setBorderColor("#999");
		style.setBorderStyle(BorderStyle.SOLID);
		style.setBorderWidth(1, Style.Unit.PX);
	}

	/**
	 * Sets the focus of this editor.
	 * 
	 * @param focus
	 *            Whether to focus this editor.
	 */
	public void setFocus(boolean focus)
	{
		panel.setFocus(focus);
	}

	/**
	 * Invoked when this editor has focus (or its children have focus) and a key
	 * has been pressed.
	 * 
	 * @param event
	 *            The event containing the key information.
	 */
	public void onKeyPress(KeyPressEvent event)
	{
		// If this editor is not specifically focused then ignore. This ensures
		// that the children won't affect this editor
		if (!focused)
		{
			return;
		}

		// Get the character typed on the keyboard.
		char c = event.getCharCode();

		// If the Ctrl key is being pressed then check for hot keys.
		if (event.isControlKeyDown())
		{
			HotKeyListener listener = hotKeys.get(c);
			if (listener != null)
			{
				// Notify listener
				listener.onHotKeyPressed(c);
				// Stop combination from affecting browser.
				event.preventDefault();
				event.stopPropagation();
			}
			// Ignore any other ctrl key presses.
			return;
		}

		// Whether the normal actions of a key should be ignored.
		boolean preventDefault = false;

		// The token to add (or remove if removedToken is true).
		Token token = null;

		switch (c)
		{
		case ' ':
			token = new SpaceToken(this);
			break;
		case '\t':
			preventDefault = true;
			token = new TabToken(this);
			break;
		case '\b':
			preventDefault = true;
			int index = box.getWidgetCount() - 1;
			if (index >= 0)
			{
				box.remove(index);
			}
			notifyTokenRemove();
			break;
		default:
			token = new CharToken(this, c);
		}

		// If the key will potentially remove focus from the editor. (backspace
		// or tab for example).
		if (preventDefault)
		{
			event.preventDefault();
			event.stopPropagation();
		}

		// If the token was specified then add it to the box.
		if (token != null)
		{
			box.add(token);
		}

		editorChanged(token);
	}

	/**
	 * Notifies editor that it has been changed (maybe a token was added) and it
	 * needs to notify listeners, ensure it's maintaining its minimum size, and
	 * notify a parent editor that this token editor has been updated.
	 * 
	 * @param token
	 *            The token added (if any)
	 */
	private void editorChanged(Token token)
	{
		// If this editor contains no tokens then notify the listeners
		if (box.getWidgetCount() == 0)
		{
			notifyEmpty();
		}
		// Else an item has been added (given token is non-null)
		else if (token != null)
		{
			notifyTokenAdded(token);
		}

		// Ensure the editor does not shrink past its minimum size.
		ensureMinimumSize();

		// If a parent exists let it know this token has updated
		if (getEditor() != null)
		{
			getEditor().tokenUpdate(this);
		}
	}

	/**
	 * Ensures this editor is maintaining a minimum size.
	 */
	private void ensureMinimumSize()
	{
		int minWidth = (int) (getFontSize() * MIN_WIDTH);
		int minHeight = (int) (getFontSize() * MIN_HEIGHT);
		if (getWidth() < minWidth)
		{
			setWidth(minWidth);
		}
		if (getHeight() < minHeight)
		{
			setHeight(minHeight);
		}
	}

	/**
	 * Notifies all listeners that a token has been added.
	 * 
	 * @param token
	 *            The token added.
	 */
	private void notifyTokenAdded(Token token)
	{
		for (EditorListener listener : listeners)
		{
			listener.onTokenAdded(this, token);
		}
	}

	/**
	 * Notifies all listeners that the editor has no tokens.
	 */
	private void notifyEmpty()
	{
		for (EditorListener listener : listeners)
		{
			listener.onEmpty(this);
		}
	}

	/**
	 * Notifies all listeners that the editor had a token removed.
	 */
	private void notifyTokenRemove()
	{
		for (EditorListener listener : listeners)
		{
			listener.onTokenRemoved(this);
		}
	}

	/**
	 * Adds a fraction to the editor.
	 */
	public void add(TokenFactory factory)
	{
		if (focused)
		{
			Token token = factory.createToken(this);
			box.add(token);
			editorChanged(token);
		} else
		{
			int widgetCount = box.getWidgetCount();
			for (int i = 0; i < widgetCount; i++)
			{
				Token t = (Token) box.getWidget(i);
				t.propagateAdd(factory);
			}
		}
	}

	/**
	 * Triggered when this editor obtains focus.
	 */
	public void onFocus(FocusEvent event)
	{
		focused = true;
	}

	/**
	 * Triggered when this editor loses focus.
	 */
	public void onBlur(BlurEvent event)
	{
		focused = false;
		editorFocused = this;
	}

	/**
	 * Sets the width of the editor in pixels.
	 * 
	 * @param pixels
	 *            The width in pixels.
	 */
	public void setWidth(int pixels)
	{
		panel.setWidth(pixels + "px");
	}

	/**
	 * Sets the height of the editor in pixels.
	 * 
	 * @param pixels
	 *            The height in pixels.
	 */
	public void setHeight(int pixels)
	{
		panel.setHeight((pixels - 5) + "px");
	}

	/**
	 * Notifies this editor that the given token has changed (in height).
	 * 
	 * @param token
	 *            The token which changed.
	 */
	public void tokenUpdate(Token token)
	{
		int height = token.getHeight();
		// Resize the editor if the token is taller
		if (height > getHeight())
		{
			setHeight(height);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onEmpty(Editor source)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	public void onTokenAdded(Editor source, Token token)
	{
		// We're expecting ourself
		if (source == this)
		{
			int width = box.getOffsetWidth();
			int height = box.getOffsetHeight();
			panel.setPixelSize(width, height);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onTokenRemoved(Editor source)
	{
		onTokenAdded(source, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void propagateAdd(TokenFactory factory)
	{
		add(factory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isAnEntity()
	{
		return true;
	}

	/**
	 * Converts all the contents of this Editor into an EntityContainer.
	 * 
	 * @return An EntityContainer.
	 */
	@Override
	public Entity toEntity()
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();

		int entityCount = this.box.getWidgetCount();
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < entityCount; i++)
		{
			Token token = (Token) this.box.getWidget(i);

			// These are tokens which append to a currently running text
			// entity. As soon as another type is hit (or the last token
			// is hit) then a TextEntity should be created for it.
			if (!token.isAnEntity())
			{
				text.append(token.toAppendableString());
			}
			// A non-text entity was hit
			else
			{
				// If text exists, create a TextEntity for it.
				if (text.length() > 0)
				{
					entities.add(new Text(text.toString()));
					// Remove all text from buffer
					text = new StringBuilder();
				}

				entities.add(token.toEntity());
			}
		}

		// If text exists, create a TextEntity for it.
		if (text.length() > 0)
		{
			entities.add(new Text(text.toString()));
			// Remove all text from buffer
			text = new StringBuilder();
		}
		
		return new EntityContainer(entities);
	}

	/**
	 * Given an entity this
	 * @param e
	 * @return
	 */
	public boolean fromEntity(Entity e)
	{
		// Null entity can't be converted.
		if (e == null)
		{
			return false;
		}
	
		// First remove all entities from this editor.
		box.clear();
		
		// The list of child entities from the given entity.
		ArrayList<Entity> entities = null;

		// Grab the list of entities from the acceptable types.
		if (e instanceof Answer) 
		{
			entities = ((Answer)e).getEntities();
		}
		else if (e instanceof Question)
		{
			entities = ((Question)e).getEntities();
		}
		else if (e instanceof EntityContainer)
		{
			entities = ((EntityContainer)e).getEntities();
		}

		// If the entity given wasn't a list then handle it as a single type.
		if (entities == null)
		{
			Token toke = getEntityToken(e);
			if (toke != null)
			{
				box.add(toke);
			}
			return true;
		}

		// Iterate through the child entities
		for (Entity child : entities)
		{
			Token toke = getEntityToken(child);
			if (toke != null)
			{
				box.add(toke);	
			}
		}

		// Successful conversion
		return true;
	}
	
	/**
	 * Adds the given entity as a child to this editor.
	 *  
	 * @param e The entity to add as a token.
	 */
	private Token getEntityToken(Entity e)
	{
		if (e instanceof Decimal)
		{
			Decimal d = (Decimal)e;
			return new DecimalToken(this, d.getWhole(), d.getDecimal());
		}
		else if (e instanceof EntityContainer)
		{
			EntityContainer ec = (EntityContainer)e;
			Editor child = new Editor(this);
			child.fromEntity(ec);
			return child;
		}
		else if (e instanceof Exponent)
		{
			Exponent x = (Exponent)e;
			return new ExponentToken(this, x.getBase(), x.getExponent());
		}
		else if (e instanceof Fraction)
		{
			Fraction f = (Fraction)e;
			return new FractionToken(this, f.getNumerator(), f.getDenominator());
		}
		else if (e instanceof Integer)
		{
			Integer i = (Integer)e;
			return new IntegerToken(this, i.getInteger());
		}
		else if (e instanceof Newline)
		{
			return new NewlineToken(this);
		}
		else if (e instanceof SquareRoot)
		{
			SquareRoot sr = (SquareRoot)e;
			return new SquareRootToken(this, sr.getRoot());
		}
		else if (e instanceof Symbol)
		{
			Symbol s = (Symbol)e;
			return new SymbolToken(this, s.getSymbol());
		}
		else if (e instanceof Unit)
		{
			Unit u = (Unit)e;
			return new UnitsToken(this, u.getOptions(), u.getCorrect());
		}
		else if (e instanceof Text)
		{
			Text t = (Text)e;
			// Get the array of characters from the text string.
			char[] chars = t.getText().toCharArray();
			// For each character in the text...
			for (char c : chars)
			{
				switch (c)
				{
				case ' ':
					box.add(new SpaceToken(this));
					break;
				case '\t':
					box.add(new TabToken(this));
					break;
				default:
					box.add(new CharToken(this, c));
					break;
				}
			}
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String toAppendableString()
	{
		return null;
	}

	/**
	 * Creates the factory used to instantiate tokens of this type.
	 */
	private static TokenFactory factory = new TokenFactory()
	{
		@Override
		public Token createToken(Editor parent)
		{
			return new Editor(parent);
		}
	};

	/**
	 * Returns the factory associated with this token.
	 * 
	 * @return This tokens factory.
	 */
	public static TokenFactory getFactory()
	{
		return factory;
	}

	/**
	 * Adds the given token to the editor with focus last.
	 * 
	 * @param factory
	 *            The factory to use to create a token.
	 */
	public static void addToLastFocused(TokenFactory factory)
	{
		if (editorFocused != null)
		{
			editorFocused.setFocus(true);
			editorFocused.add(factory);	
		}
	}

	/**
	 * The unit selector listener for any editor.
	 */
	private static UnitSelectorListener listener = new UnitSelectorListener() 
	{
		@Override
		public void unitsSelected(ArrayList<String> units, String correct)
		{
			addToLastFocused(UnitsToken.getFactory(units, correct));
		}
	};

	/**
	 * @return The unit selector listener for any editor.
	 */
	public static UnitSelectorListener getUnitSelectorListener() 
	{
		return listener;
	}

}
