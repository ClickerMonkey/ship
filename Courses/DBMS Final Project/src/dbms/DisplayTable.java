package dbms;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import dbms.common.OutputFormatter;
import dbms.formatter.ClobFormatter;
import dbms.formatter.DateFormatter;
import dbms.formatter.TimestampFormatter;

/**
 * A helper class to display a set of data as a table. The table has headers
 * and the width of the columns are determined by the maximum width of the items
 * in that column.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DisplayTable 
{

	// How many rows have been set in this table.
	private int rowCount;
	
	// How many columns exist in this table.
	private final int columnCount;
	
	// The headers of each of the columns.
	private final String[] headers;
	
	// The optional formatters for each column.
	private final OutputFormatter<?>[] formatters;
	
	// The table data as rows of strings.
	private final ArrayList<String[]> rows;
	
	
	/**
	 * Instantiates a new DisplayTable.
	 * 
	 * @param columnCount => The number of columns in the table.
	 */
	public DisplayTable(int columnCount) 
	{
		this.columnCount = columnCount;
		this.headers = new String[columnCount];
		this.formatters = new OutputFormatter<?>[columnCount];
		this.rows = new ArrayList<String[]>();
	}
	
	/**
	 * Sets the header of the given column. If the column index does not exist
	 * on the table then this has no affect to the headers.
	 * 
	 * @param column => The index of the column to set the header of.
	 * @param value => The header of the column.
	 */
	public void setHeader(int column, String value) 
	{
		if (column < 0 || column >= columnCount) {
			return;
		}
		headers[column] = value;
	}
	
	/**
	 * Sets the formatter of the given column. A column can have a formatter
	 * if the plain old toString() method is not sufficient for displaying the 
	 * items in the given column.
	 * 
	 * @param column => The index of the column to set the formatter for.
	 * @param formatter => The formatter for data in the column.
	 */
	public void setFormatter(int column, OutputFormatter<?> formatter) 
	{
		if (column < 0 || column >= columnCount) {
			return;
		}
		formatters[column] = formatter;
	}
	
	/**
	 * Adds an empty row in the table (filled with nulls).
	 */
	public void addRow() 
	{
		String[] row = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			row[i] = "null";
		}
		rows.add(row);
	}
	
	/**
	 * Sets the value in a cell given its row and column indices. If the value
	 * given is null then "null" is placed in the cell. If the given column has
	 * a formatter then it is used to convert the value to a string. If the
	 * formatter fails then it resorts to the toString() method of the value. If
	 * the given indices are outside the bounds of this table then nothing occurs.
	 * 
	 * @param row => The index of the row in the table.
	 * @param column => The index of the column in the table.
	 * @param value => The value to place in the cell.
	 */
	@SuppressWarnings("unchecked")
	public void set(int row, int column, Object value) 
	{
		// Skip an out-of-bounds cell.
		if (row < 0 || row >= rows.size() || column < 0 || column >= columnCount) {
			return;
		}
		// If the given value is null explicitly set the cell to null
		if (value == null) {
			rows.get(row)[column] = "null";
		} 
		else {
			// If this column has a formatter...
			if (formatters[column] != null) {
				// Get the formatter and convert the value.
				OutputFormatter<Object> format = (OutputFormatter<Object>)formatters[column]; 
				String parsed = format.toString(value);
				// If there was a problem converting revert to toString().
				if (parsed == null) {
					parsed = value.toString();
				}
				// Set the cell using the parsed value.
				rows.get(row)[column] = parsed;
			} else {
				// Set the cell using the toString() method.
				rows.get(row)[column] = value.toString();	
			}	
		}
		// Keep track of the maximum row set.
		rowCount = Math.max(rowCount, row + 1);
	}
	
	/**
	 * Sets the value in a cell of the last row added given its column index. 
	 * If the value given is null then "null" is placed in the cell. If the 
	 * given column has a formatter then it is used to convert the value to a 
	 * string. If the formatter fails then it resorts to the toString() method 
	 * of the value. If the given indices are outside the bounds of this table 
	 * then nothing occurs.
	 * 
	 * @param column => The index of the column in the table.
	 * @param value => The value to place in the cell.
	 */
	public void set(int column, Object value) 
	{
		set(rows.size() - 1, column, value);
	}
	
	/**
	 * Converts the contents of this table to a string representation returned
	 * as a StringBuilder.
	 */
	public StringBuilder build() 
	{
		// The max width of each column
		int[] max = new int[columnCount];
		
		// Initialize max as the header widths (at minimum 4 to hold null)
		for (int i = 0; i < columnCount; i++) {
			if (headers[i] != null) {
				max[i] = headers[i].length();
			}
		}
		
		// Go through every row, and for each column capture the maximum 
		// width of every item in that column.
		for (int i = 0; i < rowCount; i++) {
			String[] row = rows.get(i);
			for (int k = 0; k < columnCount; k++) {
				if (row[k] != null) {
					max[k] = Math.max(max[k], row[k].length());	
				}
			}
		}
		
		// Get the total width of all of the columns (including separators and
		// the newline characters).
		int total = (columnCount - 1) * 3;
		for (int maximum : max) {
			total += maximum;
		}

		// Calculate the capacity of the builder based on the number of rows 
		// (also taking into account the header and dash rows).
		int capacity = (rowCount + 2) * total;
		StringBuilder builder = new StringBuilder(capacity);

		// Build all of the formatting strings for each column.
		String[] formats = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			formats[i] = "%-" + max[i] + "s";
		}
		
		// Display the header
		for (int i = 0; i < columnCount; i++) {
			if (i > 0) {
				builder.append(" | ");
			}
			builder.append(String.format(formats[i], headers[i]));
		}
		builder.append('\n');
		
		// Display the bar below the header
		for (int i = 0; i < columnCount; i++) {
			if (i > 0) {
				builder.append("-+-");
			}
			for (int k = 0; k < max[i]; k++) {
				builder.append('-');
			}
		}
		builder.append('\n');
		
		// Display the table data
		for (int i = 0; i < rowCount; i++) {
			String[] row = rows.get(i);
			for (int k = 0; k < columnCount; k++) {
				if (k > 0) {
					builder.append(" | ");
				}
				builder.append(String.format(formats[k], row[k]));
			}
			builder.append('\n');
		}

		return builder;
	}
	
	/**
	 * Prints this table out to Standout Out.
	 */
	public void display() 
	{
		System.out.print(build());
	}
	
	/**
	 * Returns the number of columns in this table.
	 */
	public int getColumns() 
	{
		return columnCount;
	}
	
	/**
	 * Returns the number of rows added to this table.
	 */
	public int getRows() 
	{
		return rows.size();
	}

	/**
	 * Returns the number of rows set in this table. 
	 */
	public int getRowsSet() 
	{
		return rowCount;
	}
	
	/**
	 * Returns an empty DisplayTable based on given ResultSetMetaData. The
	 * meta data given is used to determine how many columns should exist in
	 * the table as well as the headers of the table (column names).
	 * 
	 * @param data => The MetaData of a ResultSet
	 * @return A DisplayTable initialized with headers.
	 */
	public static DisplayTable fromMetaData(ResultSetMetaData data)
	{
		try {
			// Get the number of columns...
			int columnCount = data.getColumnCount();
			DisplayTable table = new DisplayTable(columnCount);
			
			// For each column....
			for (int i = 0; i < columnCount; i++) {
				// Set the header to the column name.
				table.setHeader(i, data.getColumnName(i + 1));
				
				// Set an appropriate formatter if the column type is a type
				// that does not appear natural with toString().
				switch (data.getColumnType(i + 1)) {
				case Types.TIMESTAMP:
					table.setFormatter(i, new TimestampFormatter());
					break;
				case Types.CLOB:
					table.setFormatter(i, new ClobFormatter());
					break;
				case Types.DATE:
					table.setFormatter(i, new DateFormatter());
					break;
				}
			}
			
			// Return the built table.
			return table;	
		}
		catch (SQLException e) {
			return null;
		}
	}
	
}
