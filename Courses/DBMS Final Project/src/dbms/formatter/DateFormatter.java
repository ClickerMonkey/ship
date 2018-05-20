package dbms.formatter;

import oracle.sql.DATE;

import dbms.common.OutputFormatter;

/**
 * A formatter for the DATE data type.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DateFormatter implements OutputFormatter<DATE>
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(DATE item) 
	{
		// Try to convert it directly, on error choose default conversion.
		try {
			return String.format("%1$tY-%1$tm-%1$td", item.dateValue());	
		}
		catch (Exception e) {
			return item.toString();
		}
	}

}
