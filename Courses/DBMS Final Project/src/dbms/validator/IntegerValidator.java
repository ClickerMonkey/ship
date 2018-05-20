package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string for an integer value. A min/max value can be given as well.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class IntegerValidator implements InputValidator<Integer> 
{

	// The minimum value of a parsed integer.
	private final int min;
	
	// The maximum value of a parsed integer.
	private final int max;

	/**
	 * Instantiates a new IntegerValidator.
	 */
	public IntegerValidator() {
		
		this(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	/**
	 * Instantiates a new IntegerValidator.
	 * 
	 * @param min => The minimum value a parsed integer can be to be valid.
	 * @param max => The maximum value a parsed integer can be to be valid.
	 */
	public IntegerValidator(int min, int max) 
	{
		this.min = min;
		this.max = max; 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer parse(String s) 
	{
		try {
			int i = Integer.parseInt(s);
			if (i < min || i > max) {
				return null;
			}
			return i;
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

}
