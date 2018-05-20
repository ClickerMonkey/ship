package ass2;

import java.util.Arrays;

/**
 * A utility for byte manipulation.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Bytes 
{

	/**
	 * Converts a hexadecimal string to an array of bytes.
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] fromHex(String hex) 
	{
		if ((hex.length() & 1) == 1) {
			hex = '0' + hex;
		}
		
		char[] chars = hex.toUpperCase().toCharArray();
		int bytes = (chars.length >> 1);

		byte[] data = new byte[bytes];
		
		for (int i = 0; i < bytes; i++) {
			data[i] = byteFromHex(chars, i << 1);
		}
		
		return data;
	}
	
	/**
	 * Converts hex to a byte.
	 * 
	 * @param data
	 * @param off
	 * @return
	 */
	private static byte byteFromHex(char[] data, int off) 
	{
		int j = data[off + 0];
		int k = data[off + 1];

		int msb = (j > '9' ? j - 'A' + 10 : j - '0');
		int lsb = (k > '9' ? k - 'A' + 10 : k - '0');
		
		return (byte)((msb << 4) | lsb);
	}
	
	/**
	 * Converts the binary string to an array of bytes.
	 * 
	 * @param binary
	 * @return
	 */
	public static byte[] fromBinary(String binary)
	{
		int pad = (binary.length() & 7);
		
		for (int i = 0; i < pad; i++) {
			binary = '0' + binary;
		}
		
		char[] chars = binary.toCharArray();
		int bytes = (chars.length >> 3);
		
		byte[] data = new byte[bytes];
		
		for (int i = 0; i < bytes; i++) {
			data[i] = byteFromBinary(chars, i << 3);
		}
		
		return data;
	}
	
	/**
	 * Converts a binary section to a byte.
	 * 
	 * @param data
	 * @param off
	 * @return
	 */
	private static byte byteFromBinary(char[] data, int off)
	{
		int b = 0;
		b |= (data[off++] - '0') << 7;
		b |= (data[off++] - '0') << 6;
		b |= (data[off++] - '0') << 5;
		b |= (data[off++] - '0') << 4;
		b |= (data[off++] - '0') << 3;
		b |= (data[off++] - '0') << 2;
		b |= (data[off++] - '0') << 1;
		b |= (data[off++] - '0') << 0;
		return (byte)b;
	}
	
	/**
	 * Appends the byte to the end of the given array and returns it.
	 * 
	 * @param data
	 * @param x
	 * @return
	 */
	public static byte[] append(byte[] data, byte x) 
	{
		data = Arrays.copyOf(data, data.length + 1);
		data[data.length - 1] = x;
		return data;
	}
	
	/**
	 * Appends the short to the end of the given array and returns it.
	 * 
	 * @param data
	 * @param x
	 * @return
	 */
	public static byte[] append(byte[] data, short x)
	{
		data = Arrays.copyOf(data, data.length + 2);
		data[data.length - 2] = (byte)((x >> 8) & 0xFF);
		data[data.length - 1] = (byte)((x >> 0) & 0xFF);
		return data;
	}
	
	/**
	 * Appends the int to the end of the given array and returns it.
	 * 
	 * @param data
	 * @param x
	 * @return
	 */
	public static byte[] append(byte[] data, int x)
	{
		data = Arrays.copyOf(data, data.length + 4);
		data[data.length - 4] = (byte)((x >> 24) & 0xFF);
		data[data.length - 3] = (byte)((x >> 16) & 0xFF);
		data[data.length - 2] = (byte)((x >>  8) & 0xFF);
		data[data.length - 1] = (byte)((x >>  0) & 0xFF);
		return data;
	}
	
}
