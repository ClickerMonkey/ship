package dbms.common;

import java.util.Scanner;

/**
 * A persistent prompt of some given data type. Text to display at the prompt
 * is given as well as the validator of input to determine whether what the
 * user typed in is desired.
 * 
 * @author Philip Diffenderfer
 *
 * @param <T> The type of input to get.
 */
public class InputPrompt<T> 
{

	// The text to display at prompt.
	private final String text;
	
	// The scanner for prompting for input.
	private final Scanner input;
	
	// The validator which determines if what the user typed in is right.
	private final InputValidator<T> validator;
	
	/**
	 * Instantiates a new InputPrompt.
	 * 
	 * @param text => The text to prompt with.
	 * @param validator => The validator for the input.
	 */
	public InputPrompt(String text, InputValidator<T> validator) 
	{
		this.text = text;
		this.validator = validator;
		this.input = new Scanner(System.in);
	}
	
	/**
	 * Persistently requests for input until valid input is given, then returns.
	 */
	public T getInput() 
	{
		System.out.print(text);
		String line = input.nextLine();
		T item = validator.parse(line);
		return (item == null ? getInput() : item);
	}
	
}
