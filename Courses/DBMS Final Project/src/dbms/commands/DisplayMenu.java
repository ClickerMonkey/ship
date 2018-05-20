package dbms.commands;

import dbms.menu.Menu;
import dbms.menu.MenuCommand;

/**
 * The MenuCommand which displays its parent menu.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DisplayMenu implements MenuCommand 
{

	// The menu to display.
	private final Menu menu;
	
	/**
	 * Instantiates a DisplayMenu.
	 * 
	 * @param menu => The menu to display.
	 */
	public DisplayMenu(Menu menu) 
	{
		this.menu = menu;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		menu.display();
	}

}
