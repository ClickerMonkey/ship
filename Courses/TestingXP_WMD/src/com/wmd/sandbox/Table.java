package com.wmd.sandbox;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import com.wmd.server.db.Database;

public class Table<T extends Tuple>
{
	
	// The connection to the database.
	private Database database;

	// The name of the table.
	private final String name;
	
	// The class type of the tuples stored in this table
	private final Class<T> type;

	// The columns in the table
	private final Column[] columns;

	// The key-columns in the table
	private final int[] keys;

	// The indices of the columns that are automatically generated.
	private final int[] autos;

	// The map of column names to column index.
	private final HashMap<String, Column> map;

	// A builder of queries.
	private final QueryBuilder builder;

	// The last query string.
	private String queryString;


	/**
	 * Initializes a new Table given its name, the type of tuples stored in the
	 * table, and the array of column descriptors held in the table.
	 * 
	 * @param name The name of the table.
	 * @param type The tuple class type.
	 * @param columns The columns contained in the table.
	 */
	public Table(String name, Class<T> type, Column ... columns)
	{
		// Check the columns for validity.
		if (!validColumns(columns)) {
			throw new RuntimeException("The given columns are invalid!");
		}
		
		// Make sure the given type can create new tuples.
		if (!validType(type)) {
			throw new RuntimeException("Given type " + type.getName() + " must contain an empty constructor");
		}

		this.name = name;
		this.columns = columns;
		this.type = type;
		this.builder = new QueryBuilder(columns);
		this.keys = generateKeys(columns);
		this.autos = generateAutos(columns);
		this.map = generateMap(columns);
	}

	/**
	 * Checks the given array of columns for validity. An array of columns is
	 * valid if all columns are accounted for (based on their index) and there
	 * are no repitition of column names. The array of columns is first sorted
	 * based on their index, ergo when this method returns the columns will be
	 * sorted in ascending order (0 -> columnCount-1).
	 * 
	 * @param columns The array of columns.
	 * @return True if all columns are valid.
	 */
	private boolean validColumns(Column[] columns) 
	{
		// Sort columns based on index (ascending)
		Arrays.sort(columns);

		// Make sure every column is accounted for 
		int columnIndex = 0;
		for (Column column : columns) {
			if (column.getIndex() != columnIndex) {
				return false;
			}
			columnIndex++;
		}

		// Make sure no duplicate column names exist
		for (Column x : columns) {
			for (Column y : columns) {
				if (x != y && x.getName().equals(y.getName())) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns true if the given class type is a valid tuple. A valid tuple has
	 * an empty constructor.
	 * 
	 * @param type The class type to validate.
	 * @return True if tuples can be created with the empty constructor.
	 */
	private boolean validType(Class<T> type)
	{
		try {
			Class<?> params[] = {}; 
			if (type.getConstructor(params) != null) {
				return true;
			}
		}
		catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * Generates an array of indices pointing to all keys in the given set of
	 * columns. The array returned will be ordered in ascending order.
	 * 
	 * @param columns The set of columns.
	 * @return The array of indices pointing to key columns.
	 */
	private int[] generateKeys(Column[] columns) 
	{
		// First count how many keys there are
		int keyCount = 0;
		for (Column column : columns) {
			if (column.isKey()) {
				keyCount++;
			}
		}
		// Second get the index of all key columns.
		int index = 0;
		int[] keys = new int[keyCount];
		for (Column column : columns) {
			if (column.isKey()) {
				keys[index++] = column.getIndex();
			}
		}
		return keys;
	}

	/**
	 * Generates an array of indices pointing to all automatically generated
	 * values in the given set of columns. The array returned will be ordered in
	 *  ascending order.
	 * 
	 * @param columns The set of columns.
	 * @return The array of indices pointing to auto columns.
	 */
	private int[] generateAutos(Column[] columns) 
	{
		// First count how many autos there are
		int autoCount = 0;
		for (Column column : columns) {
			if (column.isAuto()) {
				autoCount++;
			}
		}
		// Second get the index of all key columns.
		int index = 0;
		int[] autos = new int[autoCount];
		for (Column column : columns) {
			if (column.isAuto()) {
				autos[index++] = column.getIndex();
			}
		}
		return autos;
	}

	/**
	 * Generates a map of column-names to its respective column.
	 * 
	 * @param columns The columns to map.
	 * @return A HashMap containing the map from column-name to column.
	 */
	private HashMap<String, Column> generateMap(Column[] columns) 
	{
		HashMap<String, Column> map = new HashMap<String, Column>();
		for (Column column : columns) {
			map.put(column.getName(), column);
		}
		return map;
	}

	/**
	 * 
	 */
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public String getName() {
		return name;
	}
	
	public Class<T> getTupleType() {
		return type;
	}
	
	public T createTuple() {
		try {
			T tuple = type.newInstance();
			tuple.setTable(this);
			return tuple;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T[] createTuples(int length) {
		return (T[])Array.newInstance(type, length);
	}

	public String getQueryString() {
		return queryString;
	}

	public Column getColumn(String name) {
		return map.get(name);
	}
	public Column getColumn(int index) {
		return columns[index];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public T projectTuple(T keys, String ... columns) {
		return null;
	}
	public T[] project(T predicate, String ... columns) {
		return null;
	}

	public boolean selectTuple(T tuple) throws SQLException
	{
		if (tuple == null || tuple.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		if (hasNullKeys(tuple)) {
			throw new SQLException("Null key(s) exist in the given tuple");
		}

		if (database == null) {
			throw new SQLException("No database specified");
		}
		
		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		// SELECT * FROM table WHERE key_name=value AND ...
		builder.append("SELECT * FROM ");
		builder.append(name);
		builder.append(" WHERE ");
		builder.appendAssignment(tuple, true, keys, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		PreparedStatement query  = link.prepareStatement(queryString);
		int parameter = 1;
		for (int j = 0; j < keys.length; j++) {
			query.setObject(parameter, tuple.get(keys[j]));
			parameter++;
		}	

		query.executeQuery();

		ResultSet results = query.getResultSet();
		if (!results.next()) {
			return false;
		}

		for (Column column : columns) {
			if (!column.isKey()) {
				int index = column.getIndex();
				tuple.set(index, results.getObject(index + 1));	
			}
		}

		return true;
	}

	/**
	 * Performs a selection on this table for all tuples that match the given
	 * predicate.
	 * 
	 * @param predicate
	 * @return
	 * @throws SQLException
	 */
	public T[] select(T predicate) throws SQLException
	{
		// Given predicate must be non-null and belong to this table.
		if (predicate == null || predicate.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		// Get the connection to the database.
		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		// Get the columns which are the conditions for the select
		int[] write = getWritableColumns(predicate, true);
		if (write.length == 0) {
			throw new SQLException("No columns specified");
		}

		// Build the query string in the format:
		// SELECT * FROM table WHERE column_name=value AND ...
		builder.append("SELECT * FROM ");
		builder.append(name);
		builder.append(" WHERE ");
		builder.appendAssignment(predicate, true, write, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		// Create the prepared statement, and pass in the shown arguments to
		// make sure we can get the row count from the result set.
		PreparedStatement query = link.prepareStatement(queryString, 
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		// Set the parameters in the query based on the conditional columns in
		// the predicate.
		int parameter = 1;
		for (int j = 0; j < write.length; j++) {
			query.setObject(parameter++, predicate.get(write[j]));
		}	

		// Execute the query get back all results
		ResultSet results = query.executeQuery();

		// Calculate the number of rows returned
		results.last();
		int rowCount = results.getRow();
		results.beforeFirst();
		
		// Create the new array of items based on the predicate type.
		T[] found = createTuples(rowCount);
		
		int index = 0;
		while (results.next()) {
			// Initialize a new tuple (of the given type) using empty constructor
			T item = createTuple();
			
			// Set the values in the item to the values returned in the result.
			for (int i = 0; i < columns.length; i++) {
				item.set(i, results.getObject(i + 1));
			}
			
			// Add the item to the return list.
			found[index++] = item;
		}

		// Return all tuples that met the given predicate!
		return found;
	}

	// Inserts the given tuple into the database. If the tuple is succesfully
	// added auto generated fields from the table are set in the tuple for
	// access.
	// Returns null on error
	public boolean insert(T tuple) throws SQLException
	{
		if (tuple == null || tuple.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		// Column names of non-null and non-auto fields
		int write[] = getWritableColumns(tuple, true);
		if (write.length == 0) {
			throw new SQLException("No columns specified");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		// INSERT INTO table (column_name) VALUES (value);
		builder.append("INSERT INTO ");
		builder.append(name);
		builder.append(" (");
		builder.appendColumnNames(write, ", ");
		builder.append(") VALUES (");
		builder.appendList(tuple, write);
		builder.append(");");
		queryString = builder.takeString();

		PreparedStatement query  = link.prepareStatement(queryString, autos);

		int parameter = 1;
		for (int i = 0; i < write.length; i++) {
			if (!tuple.isForceNull(write[i])) {
				query.setObject(parameter, tuple.get(write[i]));
				parameter++;
			}
		}

		if (query.executeUpdate() != 1) {
			throw new SQLException("Query had no affect");
		}

		ResultSet result = query.getGeneratedKeys();
		if (!result.next()) {
			throw new SQLException("Auto generated keys were not returned");
		}
		parameter = 1;
		for (int i = 0; i < autos.length; i++) {
			tuple.set(autos[i], result.getObject(parameter));
			parameter++;
		}

		return true;
	}

	public int update(T predicate, T set) throws SQLException 
	{
		// Given predicate must be non-null and belong to this table.
		if (predicate == null || set == null) {
			throw new SQLException("Null tuple");
		}

		// Get the columns which are the conditions for the select
		int writeSet[] = getWritableColumns(set, true);
		int writeWhere[] = getWritableColumns(predicate, true);
		if (writeSet.length == 0 || writeWhere.length == 0) {
			throw new SQLException("No columns specified");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		// Get the connection to the database.
		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		// UPDATE table SET column_name=value, ... WHERE condition=value AND ...
		builder.append("UPDATE ");
		builder.append(name);
		builder.append(" SET ");
		builder.appendAssignment(set, false, writeSet, ", ");
		builder.append(" WHERE ");
		builder.appendAssignment(predicate, true, writeWhere, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		PreparedStatement query  = link.prepareStatement(queryString);

		int parameter = 1;
		for (int i = 0; i < writeSet.length; i++) {
			if (!set.isForceNull(writeSet[i])) {
				query.setObject(parameter++, set.get(writeSet[i]));
			}
		}
		for (int i = 0; i < writeWhere.length; i++) {
			if (!predicate.isForceNull(writeWhere[i])) {
				query.setObject(parameter++, predicate.get(writeWhere[i]));
			}
		}
		
		return query.executeUpdate();
	}

	// Updates the tuple in the database. Uses the keys
	// Returns false on error
	public boolean updateTuple(T tuple) throws SQLException 
	{
		if (tuple == null || tuple.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		if (hasNullKeys(tuple)) {
			throw new SQLException("Null key(s) exist in the given tuple");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		// Column names of non-null and non-auto fields
		int write[] = getWritableColumns(tuple, false);
		if (write.length == 0) {
			throw new SQLException("No columns specified");
		}

		// UPDATE table SET column_name=value, ...
		// WHERE key_name=key_value AND ...;
		builder.append("UPDATE ");
		builder.append(name);
		builder.append(" SET ");
		builder.appendAssignment(tuple, false, write, ", ");
		builder.append(" WHERE ");
		builder.appendAssignment(tuple, true, keys, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		PreparedStatement query = link.prepareStatement(queryString);

		int parameter = 1;
		for (int i = 0; i < write.length; i++) {
			if (!tuple.isForceNull(write[i])) {
				query.setObject(parameter, tuple.get(write[i]));
				parameter++;
			}
		}
		for (int j = 0; j < keys.length; j++) {
			query.setObject(parameter, tuple.get(keys[j]));
			parameter++;
		}

		return (query.executeUpdate() == 1);
	}

	public int delete(T predicate) throws SQLException 
	{
		if (predicate == null || predicate.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		int[] write = getWritableColumns(predicate, true);
		if (write.length == 0) {
			throw new SQLException("No columns specified");
		}

		// DELETE FROM table WHERE condition=value
		builder.append("DELETE FROM ");
		builder.append(name);
		builder.append(" WHERE ");
		builder.appendAssignment(predicate, true, write, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		PreparedStatement query = link.prepareStatement(queryString);

		int parameter = 1;
		for (int i = 0; i < write.length; i++) {
			if (!predicate.isNull(write[i])) {
				query.setObject(parameter, predicate.get(write[i]));
				parameter++;
			}
		}

		return query.executeUpdate();
	}

	// Delets the tuple based on its keys.
	public boolean deleteTuple(T tuple) throws SQLException
	{
		if (tuple == null || tuple.getTable() != this) {
			throw new SQLException("Null tuple or wrong table");
		}

		if (hasNullKeys(tuple)) {
			throw new SQLException("Null key(s) exist in the given tuple");
		}

		// Check if a database was specified
		if (database == null) {
			throw new SQLException("No database specified");
		}

		Connection link = database.getLink();
		if (link == null) {
			throw new SQLException("Error connecting to database");
		}

		builder.append("DELETE FROM ");
		builder.append(name);
		builder.append(" WHERE ");
		builder.appendAssignment(tuple, true, keys, " AND ");
		builder.append(";");
		queryString = builder.takeString();

		PreparedStatement query = link.prepareStatement(queryString);

		int parameter = 1;
		for (int j = 0; j < keys.length; j++) {
			query.setObject(parameter, tuple.get(keys[j]));
			parameter++;
		}

		return (query.executeUpdate() == 1);
	}

	/**
	 * Returns the indices of all columns that have been set in the given tuple.
	 * Auto-generated columns are ignored, and if the column is a key and
	 * writeKeys is false then that column is ignored as well. If a column has 
	 * been set to NULL in a given column then it is accepted as a writable 
	 * column (unless it falls under the previous two ignore conditions). The
	 * array of indices returned is ordered in ascending order.
	 * 
	 * @param tuple The tuple to obtain indices from.
	 * @param writeKeys Whether set keys should be counted for writing.
	 * @return An array of column indices.
	 */
	private int[] getWritableColumns(T tuple, boolean writeKeys)
	{
		// Count how many columns meet the criteria
		int count = 0;
		for (Column column : columns) {
			// Do the criterie checking here. If any of the following are true
			// then skip the current column.
			if (!tuple.isWritable(column.getIndex())) continue;
			if (column.isAuto()) continue;
			if (column.isKey() && !writeKeys) continue;
			
			// The current column meets the writable criteria.
			count++;
		}
		
		// Save the column indices in an array.
		int index = 0;
		int[] indices = new int[count];
		for (Column column : columns) {
			// Do the criterie checking here. If any of the following are true
			// then skip the current column.
			if (!tuple.isWritable(column.getIndex())) continue;
			if (column.isAuto()) continue;
			if (column.isKey() && !writeKeys) continue;
			
			// Save the column index in the indices array.
			indices[index++] = column.getIndex();
		}
		
		// Return the writable columns in ascending order.
		return indices;
	}

	/**
	 * Returns true if any of the keys in the given tuple are null.
	 * 
	 * @param tuple The tuple to check for null keys.
	 * @return True if atleast one null key was found, false if all keys are non-null.
	 */
	protected boolean hasNullKeys(Tuple tuple) 
	{
		for (int i = 0; i < keys.length; i++) {
			if (tuple.isNull(keys[i])) {
				return true;
			}
		}
		return false;
	}

}
