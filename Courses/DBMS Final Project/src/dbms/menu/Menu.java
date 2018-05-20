package dbms.menu;

import java.util.ArrayList;

import dbms.common.InputPrompt;
import dbms.validator.IntegerValidator;

/**
 * A menu of commands selected by the index of the command in the menu.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Menu 
{

	// The display string for each menu command.
	private final ArrayList<String> options;
	
	// The list of menu commands in this menu.
	private final ArrayList<MenuCommand> commands;
	
	/**
	 * Instantiates a new Menu.
	 */
	public Menu() 
	{
		this.options = new ArrayList<String>();
		this.commands = new ArrayList<MenuCommand>();
	}
	
	/**
	 * Adds a menu item (command) to this menu given its display string and
	 * command to invoke when this option is selected (by index).
	 *  
	 * @param option => The display string for the menu item to add.
	 * @param command => The command to invoke for the menu item to add.
	 */
	public void addItem(String option, MenuCommand command) 
	{
		options.add(option);
		commands.add(command);
	}
	
	/**
	 * Displays the options in this menu preceeded by their index.
	 */
	public void display() 
	{
		String format = "%d = %s\n";
		for (int i = 0; i < options.size(); i++) {
			System.out.format(format, i, options.get(i));
		}
	}
	
	/**
	 * Prompts the user for an option (command index) to invoke.
	 */
	public void nextCommand() 
	{
		// Prompt is an integer between 0 and options.size() - 1
		InputPrompt<Integer> optionPrompt = new InputPrompt<Integer>(
				"Option: ", new IntegerValidator(0, options.size() - 1));
		
		// Continually prompt until a valid option is chosen.
		int option = optionPrompt.getInput();

		// Execute the command at the selected index.
		commands.get(option).execute();
	}
	
}
