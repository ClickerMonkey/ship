package dbms;

import dbms.commands.*;
import dbms.menu.Menu;

/**
 * The core to Database Management Systems Final Project Application.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Application {

	/**
	 * Starts the application.
	 */
	public static void main(String[] args) 
	{
		new Application();
	}
	
	// The main menu for this application
	private Menu mainMenu;
	
	/**
	 * Instantiates a new Application.
	 */
	public Application() 
	{
		buildMenu();
		
		// Try to connect to the database...
		if (Database.get().connect()) {
			// Notify user that we're now connected!
			System.out.println("Connected");
			
			// Display the menu for this first time.
			mainMenu.display();
			
			// Continually prompt for a command until the application exits.
			while (true) {
				mainMenu.nextCommand();
			}
		}
		// Error connecting to the database.
		System.out.println("Error connecting");
	}
	
	/**
	 * Builds the main menu to the application.
	 */
	private void buildMenu() 
	{
		mainMenu = new Menu();
		mainMenu.addItem("Display this Menu", new DisplayMenu(mainMenu));
		mainMenu.addItem("Output table", new OutputTable());
		mainMenu.addItem("Display User", new DisplayUser());
		mainMenu.addItem("Display User Comments", new DisplayUserComments());
		mainMenu.addItem("Display Memberships between two dates", new DisplayMemberships());
		mainMenu.addItem("Add Group", new AddGroup());
		mainMenu.addItem("Add Comment", new AddComment());
		mainMenu.addItem("Delete Comment", new DeleteComment());
		mainMenu.addItem("Delete Group", new DeleteGroup());
		mainMenu.addItem("Rate a comment", new RateComment());
		mainMenu.addItem("Get Child Pages", new DisplayChildren());
		mainMenu.addItem("Exit", new ExitCommand());	
	}
	
}
