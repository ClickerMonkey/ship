package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string for a User Alias.
 * 
 * @author Philip Diffenderfer
 *
 */
public class AliasValidator implements InputValidator<String> 
{

	// A valid alias consists of (4,24) alphanumeric letters (and _ -)
	private final String REGEX_PARSE = "^[a-zA-Z0-9_-]{4,24}$";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String parse(String s)
	{
		return (s.matches(REGEX_PARSE) ? s : null);
	}

}
