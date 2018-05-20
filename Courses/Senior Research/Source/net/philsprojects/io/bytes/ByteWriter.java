package net.philsprojects.io.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.philsprojects.io.BufferStream;

/**
 * 
 * @author Philip Diffenderfer
 *
 */
public class ByteWriter
{
	
	/**
	 * A callback to serialize an object to a ByteWriter.
	 * 
	 * @author Philip Diffenderfer
	 *
	 * @param <T>
	 * 		The type of the object to serialize.
	 */
	public interface WriteCallback<T> 
	{
		
		/**
		 * Serializes the given item to the given ByteWriter.
		 * 
		 * @param writer
		 * 		The ByteWriter to place the serialized item in.
		 * @param item
		 * 		The item to serialize.
		 */
		public void write(ByteWriter writer, T item);
	}


	// The underlying stream
	private final BufferStream stream;
	
	// The buffer we're writing to.
	private ByteBuffer buffer;
	
	
	/**
	 * Instantiates a new ByteWriter.
	 * 
	 * @param stream
	 * 		The stream to write to. If the stream does not have enough space for
	 * 		any data being written it will be expanded when required.
	 */
	public ByteWriter(BufferStream stream) 
	{
		this.stream = stream;
		this.buffer = stream.buffer();
	}

	/**
	 * Returns the total number of bytes in the underlying stream which have 
	 * been written.
	 * @return
	 */
	public int size() 
	{
		return buffer.position();
	}

	/**
	 * Returns the byte order of the writer.
	 * 
	 * @return
	 * 		The byte order of the writer.
	 */
	public ByteOrder order() 
	{
		return buffer.order();
	}

	/**
	 * Sets the byte order of the reader.
	 * 
	 * @param order
	 * 		The new byte order of the reader.
	 */
	public void order(ByteOrder order) 
	{
		buffer.order(order);
	}
	
	/**
	 * Ensures this writer has enough space to write the given number of bytes.
	 * 
	 * @param bytes
	 * 		The number of bytes the writer is about to write.
	 */
	private final void pad(int bytes) 
	{
		// Save the current byte order and restore it.
		ByteOrder order = buffer.order();
		stream.pad(bytes);
		buffer = stream.buffer();
		buffer.order(order);
	}
	
	/**
	 * An internal method for writing whether the object is null and then 
	 * returning that boolean.
	 * 
	 * @param o
	 * 		The object that is attempting to be written.
	 * @return
	 * 		Whether the object can be written. An object can't be written if its
	 * 		null, but can if it was not null.
	 */
	private final boolean putIsNotNull(Object o) 
	{
		pad(1);
		boolean exists = (o != null);
		putBoolean(exists);
		return exists;
	}
	
	/**
	 * Writes a boolean to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it will be expanded before it 
	 * is written to. A boolean takes up a single byte, a 0 for false and every
	 * other number means true.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putBoolean(boolean value) 
	{
		pad(1);
		buffer.put((byte)(value ? 1 : 0));
	}
	
	/**
	 * Writes a boolean array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putBooleanArray(boolean[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putBooleans(x);
		}
	}
	
	/**
	 * Writes an array of booleans to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putBooleans(boolean[] x) 
	{
		pad(x.length);
		for (int i = 0; i < x.length; i++) {
			buffer.put((byte)(x[i] ? 1 : 0));
		}
	}
	
	/**
	 * Writes a byte to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void put(byte value) 
	{
		pad(1);
		buffer.put(value);
	}

	/**
	 * Writes a byte to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putByte(byte value) 
	{
		pad(1);
		buffer.put(value);
	}

	/**
	 * Writes a byte array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putByteArray(byte[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putBytes(x);
		}
	}

	/**
	 * Writes an array of bytes to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putBytes(byte[] x) 
	{
		pad(x.length);
		buffer.put(x);
	}
	
	/**
	 * Writes an unsigned byte to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it, it will be 
	 * expanded before it is written to.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putUbyte(short value) 
	{
		pad(1);
		buffer.put((byte)value);
	}

	/**
	 * Writes an unsigned byte array to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. This will save the state of the array, so if its 
	 * null or empty and read back it will return null or empty. This is 
	 * accomplished by writing a single boolean to whether the array is null, if
	 * its not null the length of the array is then written as an unsigned short
	 * (0-65535) and finally the arrays elements are written. An array passed to
	 * this function must have fewer than 65536 elements to ensure when the 
	 * array is read back it remains valid.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUbyteArray(short[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putUbytes(x);
		}
	}

	/**
	 * Writes an array of unsigned bytes to the underlying stream. If the 
	 * underlying stream does not contain enough of free space to write it will 
	 * be expanded before it is written to.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUbytes(short[] x) 
	{
		pad(x.length);
		for (int i = 0; i < x.length; i++){
			buffer.put((byte)x[i]);
		}
	}

	/**
	 * Writes a char to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. A char takes up 2 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putChar(char value) 
	{
		pad(2);
		buffer.putChar(value);
	}
	
	/**
	 * Writes a char array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A char takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putCharArray(char[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putChars(x);
		}
	}

	/**
	 * Writes an array of chars to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A char takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putChars(char[] x) 
	{
		pad(x.length << 1);
		for (int i = 0; i < x.length; i++) {
			buffer.putChar(x[i]);
		}
	}
	
	/**
	 * Writes a short to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. A short takes up 2 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putShort(short value) 
	{
		pad(2);
		buffer.putShort(value);
	}

	/**
	 * Writes a short array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A short takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putShortArray(short[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putShorts(x);
		}
	}

	/**
	 * Writes an array of shorts to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A short takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putShorts(short[] x) 
	{
		pad(x.length << 1);
		for (int i = 0; i < x.length; i++) {
			buffer.putShort(x[i]);
		}
	}
	
	/**
	 * Writes an unsigned short to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it, it will be 
	 * expanded before it is written to. An unsigned short takes up 2 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putUshort(int value) 
	{
		pad(2);
		buffer.putShort((short)value);
	}

	/**
	 * Writes an unsigned short array to the underlying stream. If the 
	 * underlying stream does not contain enough of free space to write it will 
	 * be expanded before it is written to. This will save the state of the 
	 * array, so if its null or empty and read back it will return null or 
	 * empty. This is accomplished by writing a single boolean to whether the 
	 * array is null, if its not null the length of the array is then written as
	 * an unsigned short (0-65535) and finally the arrays elements are written. 
	 * An array passed to this function must have fewer than 65536 elements to 
	 * ensure when the array is read back it remains valid. An unsigned short 
	 * takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUshortArray(int[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putUshorts(x);
		}
	}

	/**
	 * Writes an array of unsigned shorts to the underlying stream. If the 
	 * underlying stream does not contain enough of free space to write it will 
	 * be expanded before it is written to. An unsigned short takes up 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUshorts(int[] x) 
	{
		pad(x.length << 1);
		for (int i = 0; i < x.length; i++) {
			putUshort(x[i]);
		}
	}
	
	/**
	 * Writes an int to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. An int takes up 4 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putInt(int value) 
	{
		pad(4);
		buffer.putInt(value);
	}
	
	/**
	 * Writes an int array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. An int takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putIntArray(int[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putInts(x);
		}
	}

	/**
	 * Writes an array of ints to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. An int takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putInts(int[] x) 
	{
		pad(x.length << 2);
		for (int i = 0; i < x.length; i++) {
			buffer.putInt(x[i]);
		}
	}
	
	/**
	 * Writes an unsigned int to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it, it will be 
	 * expanded before it is written to. An unsigned int takes up 4 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putUint(long value) 
	{
		pad(4);
		buffer.putInt((int)value);
	}
	
	/**
	 * Writes an unsigned int array to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. This will save the state of the array, so if its 
	 * null or empty and read back it will return null or empty. This is 
	 * accomplished by writing a single boolean to whether the array is null, if
	 * its not null the length of the array is then written as an unsigned short
	 * (0-65535) and finally the arrays elements are written. An array passed to
	 * this function must have fewer than 65536 elements to ensure when the 
	 * array is read back it remains valid. An unsigned int takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUintArray(long[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putUints(x);
		}
	}
	
	/**
	 * Writes an array of unsigned ints to the underlying stream. If the 
	 * underlying stream does not contain enough of free space to write it will 
	 * be expanded before it is written to. An unsigned int takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUints(long[] x) 
	{
		pad(x.length << 2);
		for (int i = 0; i < x.length; i++) {
			buffer.putInt((int)x[i]);
		}
	}
	
	/**
	 * Writes a long to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. A long takes up 8 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putLong(long value) 
	{
		pad(8);
		buffer.putLong(value);
	}
	
	/**
	 * Writes a long array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A long takes up 8 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putLongArray(long[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putLongs(x);
		}
	}

	/**
	 * Writes an array of longs to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A long takes up 8 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putLongs(long[] x) 
	{
		pad(x.length << 3);
		for (int i = 0; i < x.length; i++) {
			buffer.putLong(x[i]);
		}
	}
	
	/**
	 * Writes a float to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. A float takes up 4 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putFloat(float value) 
	{
		pad(4);
		buffer.putFloat(value);
	}
	
	/**
	 * Writes a float array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A float takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putFloatArray(float[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putFloats(x);
		}
	}

	/**
	 * Writes an array of floats to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A float takes up 4 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putFloats(float[] x) 
	{
		pad(x.length << 2);
		for (int i = 0; i < x.length; i++) {
			buffer.putFloat(x[i]);
		}
	}

	/**
	 * Writes a double to the underlying stream. If the underlying stream does
	 * not contain enough of free space to write it, it will be expanded before 
	 * it is written to. A double takes up 8 bytes.
	 * 
	 * @param value
	 * 		The value to write to the stream.
	 */
	public void putDouble(double value) 
	{
		pad(8);
		buffer.putDouble(value);
	}
	
	/**
	 * Writes a double array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A double takes up 8 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putDoubleArray(double[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putDoubles(x);
		}
	}
	
	/**
	 * Writes an array of doubles to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A double takes up 8 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putDoubles(double[] x) 
	{
		pad(x.length << 3);
		for (int i = 0; i < x.length; i++) {
			buffer.putDouble(x[i]);
		}
	}
	
	/**
	 * Writes the remaining data in the given buffer to the underlying stream.
	 * If the underlying stream does not contain enough of free space to write 
	 * it will be expanded before it is written to.
	 * 
	 * @param x
	 * 		The buffer to transfer data from.
	 */
	public void putBuffer(ByteBuffer x) 
	{
		pad(x.remaining());
		buffer.put(x);
	}
	
	/**
	 * Writes a string to the underlying stream. If the underlying stream does 
	 * not contain enough of free space to write it will be expanded before it 
	 * is written to. This will save the state of the string, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the string is null, if its not 
	 * null the length of the string is then written as an unsigned short 
	 * (0-65535) and finally the strings characters are written. A string passed 
	 * to this function must have fewer than 65536 characters to ensure when the
	 * string is read back it remains valid. A string takes up 1 byte as the 
	 * null marker, an unsigned short for its length, and a byte for each of its 
	 * characters.
	 * 
	 * @param s
	 * 		The string to write to the stream.
	 */
	public void putString(String s) 
	{
		if (putIsNotNull(s)) {
			putUshort(s.length());
			putBytes(s.getBytes());
		}
	}

	/**
	 * Writes a string array to the underlying stream. If the underlying stream 
	 * does not contain enough of free space to write it will be expanded before 
	 * it is written to. This will save the state of the array, so if its null
	 * or empty and read back it will return null or empty. This is accomplished
	 * by writing a single boolean to whether the array is null, if its not null
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the arrays elements are written. An array passed to this 
	 * function must have fewer than 65536 elements to ensure when the array
	 * is read back it remains valid. A string takes up a byte for a null 
	 * marker, an unsigned short for the number of characters in it, and
	 * each character as a byte.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putStringArray(String[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putStrings(x);
		}
	}
	
	/**
	 * Writes an array of strings to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. A string takes up a byte for a null marker, an 
	 * unsigned short for the number of characters in it, and each character as 
	 * a byte.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putStrings(String[] x) 
	{
		for (int i = 0; i < x.length; i++) {
			putString(x[i]);
		}
	}

	/**
	 * Writes a unicode string to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. This will save the state of the string, so if 
	 * its null or empty and read back it will return null or empty. This is 
	 * accomplished by writing a single boolean to whether the string is null, 
	 * if its not null the length of the string is then written as an unsigned 
	 * short (0-65535) and finally the strings characters are written. A string 
	 * passed to this function must have fewer than 65536 characters to ensure 
	 * when the string is read back it remains valid. A string takes up 1 byte 
	 * as the null marker, an unsigned short for its length, and 2 bytes for 
	 * each of its characters.
	 * 
	 * @param s
	 * 		The string to write to the stream.
	 */
	public void putUnicode(String s) 
	{
		if (putIsNotNull(s)) {
			putUshort(s.length());
			putChars(s.toCharArray());
		}
	}

	/**
	 * Writes a unicode string array to the underlying stream. If the underlying 
	 * stream does not contain enough of free space to write it will be expanded 
	 * before it is written to. This will save the state of the array, so if its 
	 * null or empty and read back it will return null or empty. This is 
	 * accomplished by writing a single boolean to whether the array is null, if
	 * its not null the length of the array is then written as an unsigned short
	 * (0-65535) and finally the arrays elements are written. An array passed to 
	 * this function must have fewer than 65536 elements to ensure when the 
	 * array is read back it remains valid. A string takes up a byte for a null 
	 * marker, an unsigned short for the number of characters in it, and
	 * each character as 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUnicodeArray(String[] x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			putUnicodes(x);
		}
	}

	/**
	 * Writes an array of unicode strings to the underlying stream. If the 
	 * underlying stream does not contain enough of free space to write it will 
	 * be expanded before it is written to. A string takes up a byte for a null 
	 * marker, an unsigned short for the number of characters in it, and each 
	 * character as 2 bytes.
	 * 
	 * @param x
	 * 		The array to write to the stream.
	 */
	public void putUnicodes(String[] x) 
	{
		for (int i = 0; i < x.length; i++) {
			putUnicode(x[i]);
		}
	}
	
	/**
	 * Writes an enum to the underlying stream. If the underlying stream does 
	 * not contain enough of free space to write it will be expanded before it 
	 * is written to. This will save the state of the enum, so if its null and
	 * read back it will return null. An enum takes up one boolean for a null
	 * marker, and an unsigned short for the ordinal of the given enum.
	 * 
	 * @param <T>
	 * 		The enum type.
	 * @param x
	 * 		The enum to write to the stream.
	 */
	public <T extends Enum<T>> void putEnum(Enum<T> x) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.ordinal());
		}
	}
	
	/**
	 * Writes an object to the underlying stream using serialization. The
	 * object being written must be serializable or at least fit the 
	 * requirements to be serialized. If the underlying stream does not contain 
	 * enough of free space to write it will be expanded before it is written 
	 * to. This will save the state of the object, so if its null and read back 
	 * it will return null.
	 * 
	 * @param o
	 * 		The object to write to the stream.
	 */
	public void putObject(Object o) 
	{
		putItem(o, new SerializerCallback()); 
	}
	
	/**
	 * Writes an object to the underlying stream using a callback which 
	 * serializes the object. If the underlying stream does not contain enough 
	 * of free space to write it will be expanded before it is written to.
	 * 
	 * @param <T>
	 * 		The type of the written object.
	 * @param item
	 * 		The object to write to the stream.
	 * @param callback
	 * 		The callback to invoke to serialize the object with this writer.
	 */
	public <T> void putItem(T item, WriteCallback<T> callback) 
	{
		if (putIsNotNull(item)) {
			callback.write(this, item);
		}
	}
	
	/**
	 * Writes an array to the underlying stream. If the underlying stream does 
	 * not contain enough of free space to write it will be expanded before it 
	 * is written to. This will save the state of the array, so if its null or 
	 * empty and read back it will return null or empty. This is accomplished by
	 * writing a single boolean to whether the array is null, if its not null 
	 * the length of the array is then written as an unsigned short (0-65535) 
	 * and finally the elements in the array are written.
	 * 
	 * @param <T>
	 * 		The type of the written object.
	 * @param x
	 * 		The array of objects to write to the stream.
	 * @param callback
	 * 		The callback to invoke to serialize the objects with this writer.
	 */
	public <T> void putArray(T[] x, WriteCallback<T> callback) 
	{
		if (putIsNotNull(x)) {
			putUshort(x.length);
			for (int i = 0; i < x.length; i++) {
				if (putIsNotNull(x[i])) {
					callback.write(this, x[i]);
				}
			}
		}
	}
	
}
