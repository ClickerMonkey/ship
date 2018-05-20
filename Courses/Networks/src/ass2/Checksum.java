package ass2;

/**
 * A utility capable of generating 8-bit, 16-bit, and 32-bit checksums.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface Checksum 
{

	/**
	 * Returns a 8-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @return
	 * 		The calculated 8-bit checksum.
	 */
	public short getCode8(byte[] data);

	/**
	 * Returns a 8-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @param off
	 * 		The offset in the data.
	 * @param len
	 * 		The number of bytes to process starting at offset.
	 * @return
	 * 		The calculated 8-bit checksum.
	 */
	public short getCode8(byte[] data, int off, int len);

	/**
	 * Returns a 16-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @return
	 * 		The calculated 16-bit checksum.
	 */	
	public int getCode16(byte[] data);

	/**
	 * Returns a 16-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @param off
	 * 		The offset in the data.
	 * @param len
	 * 		The number of bytes to process starting at offset.
	 * @return
	 * 		The calculated 16-bit checksum.
	 */
	public int getCode16(byte[] data, int off, int len);
	
	/**
	 * Returns a 32-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @return
	 * 		The calculated 32-bit checksum.
	 */
	public long getCode32(byte[] data);

	/**
	 * Returns a 32-bit checksum of the given data.
	 * 
	 * @param data
	 * 		The data to calculate the checksum of.
	 * @param off
	 * 		The offset in the data.
	 * @param len
	 * 		The number of bytes to process starting at offset.
	 * @return
	 * 		The calculated 32-bit checksum.
	 */
	public long getCode32(byte[] data, int off, int len);
	
}