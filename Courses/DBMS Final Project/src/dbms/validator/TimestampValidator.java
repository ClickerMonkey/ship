package dbms.validator;

import java.sql.Timestamp;

import dbms.common.InputValidator;

/**
 * Validates a string to a Timestamp. The string must be in the format
 * 'yyyy-mm-dd'.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TimestampValidator implements InputValidator<Timestamp> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp parse(String s) 
	{
		try {
			return Timestamp.valueOf(s + " 00:00:00");
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}

}
