package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string for a Boolean (t, true, f, false)
 * 
 * @author Philip Diffenderfer
 *
 */
public class BooleanValidator implements InputValidator<Boolean> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean parse(String s) 
	{
		// 't' and 'true' are both acceptable
		if (s.equalsIgnoreCase("t") || s.equalsIgnoreCase("true")) {
			return true;
		}
		// 'f' and 'false' are both acceptable
		if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("false")) {
			return false;
		}
		// Neither given
		return null;
	}

}
