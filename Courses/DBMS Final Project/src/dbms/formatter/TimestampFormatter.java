package dbms.formatter;

import oracle.sql.TIMESTAMP;

import dbms.common.OutputFormatter;

/**
 * A formatter for the TIMESTAMP data type.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TimestampFormatter implements OutputFormatter<TIMESTAMP>
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(TIMESTAMP item) 
	{
		// Try to convert it directly, on error choose default conversion.
		try {
			return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", item.dateValue());	
		}
		catch (Exception e) {
			return item.toString();
		}
	}

}
