package ass2;

public class InternetChecksum extends AbstractChecksum
{
	
	/**
	 * Instantiates a new InternetChecksum.
	 */
	public InternetChecksum() 
	{
	}

	@Override
	public long getCode32(byte[] data, int off, int len) 
	{
		// If the length is off, append a zero byte to the end of it
		if ((len & 1) == 1) {
			data = Bytes.append(data, (byte)0);
		}
		// Divide len by 2 since we are combining two bytes into a short
		len >>= 1;
		// Set a 32-bit checksum integer, C to 0;
		int C = 0;
		// for each 16-bit group in M
		while (--len >= 0) {
			// Treat the 16 bits as an integer and add to C;
			C += ((data[off++] & 0xFF) << 8) | (data[off++] & 0xFF);
		}
		// Extract the high-order 16 bits of C and add them to C;
		C = (C >>> 16) + (C & 0xFFFF);
		// The inverse of the low-order 16 bits of C is the checksum;
		C = ~C & 0xFFFF;
		// If the checksum is zero, substitute the all 1s form of zero.
		C = (C == 0 ? 0xFFFF : C);
		
		return C;
	}
	
}
