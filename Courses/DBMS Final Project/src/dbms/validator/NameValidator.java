package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string for a group name.
 * 
 * @author Philip Diffenderfer
 *
 */
public class NameValidator implements InputValidator<String> 
{

	// A valid group name consists of (4,24) alphanumeric letters (and _ -)
	private final String REGEX_PARSE = "^[ a-zA-Z0-9_-]{4,64}$";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String parse(String s)
	{
		return (s.matches(REGEX_PARSE) ? s : null);
	}

}
