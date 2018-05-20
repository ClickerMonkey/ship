package dbms.validator;

import dbms.common.InputValidator;

/**
 * Validates a string for a decimal value. A min/max value can be given as well.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DecimalValidator implements InputValidator<Double>
{

	// The minimum value of a parsed double.
	private final double min;
	
	// The maximum value of a parsed double.
	private final double max;
	
	/**
	 * Instantiates a new DecimalValidator.
	 */
	public DecimalValidator() 
	{
		this(-Double.MAX_VALUE, Double.MAX_VALUE);
	}
	
	/**
	 * Instantiates a new DecimalValidator.
	 * 
	 * @param min => The minimum value a parsed double can be to be valid.
	 * @param max => The maximum value a parsed double can be to be valid.
	 */
	public DecimalValidator(double min, double max) 
	{
		this.min = min;
		this.max = max;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double parse(String s) 
	{
		try {
			double d = Double.parseDouble(s);
			if (d < min || d > max) {
				return null;
			}
			return d;
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

}
