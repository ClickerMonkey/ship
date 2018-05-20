package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string by returning itself.
 * 
 * @author Philip Diffenderfer
 *
 */
public class NoValidator implements InputValidator<String> 
{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String parse(String s)
	{
		return s;
	}

}
