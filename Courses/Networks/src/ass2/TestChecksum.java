package ass2;

import java.util.HashSet;
import java.util.Random;

/**
 * A class for testing the Checksum interface (getCode16 specifically).
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class TestChecksum 
{

	/**
	 * How many iterations to do for tests.
	 */
	public static final int ITERATIONS = 10000;
	
	/**
	 * The payload size for tests.
	 */
	public static final int PAYLOAD_SIZE = 1000;
	
	
	// Generates random numbers.
	protected final Random rnd = new Random();

	// The checksum being tested
	protected final Checksum checksum;
	
	
	/**
	 * Instantiates a new ChecksumTest.
	 * 
	 * @param checksum
	 * 		The checksum being tested
	 */
	public TestChecksum(Checksum checksum) 
	{
		this.checksum = checksum;
	}

	
	/**
	 * Runs the test method a given number of times and returns how many times
	 * false was returned, in other words how many times the error bits added
	 * resulted to the same CRC code. 
	 * 
	 * @param times
	 * 		The number of times to invoke the test method.
	 * @param payloadSize
	 * 		The number of bytes in the payload.
	 * @param errorBits
	 * 		The number of error bits to introduce to the payload.
	 * @return
	 * 		The number of errors that arose.
	 */
	public int test(int times, int payloadSize, int errorBits)
	{ 
		int errors = 0;

		for (int i = 0; i < times; i++) {
			if (!test(payloadSize, errorBits)) {
				errors++;
			}
		}
		
		return errors;
	}

	
	/**
	 * Runs the test method a given number of times and returns how many times
	 * false was returned, in other words how many times the error bits added
	 * resulted to the same CRC code. 
	 * 
	 * @param times
	 * 		The number of times to invoke the test method.
	 * @param payloadSize
	 * 		The number of bytes in the payload.
	 * @param errorBits
	 * 		The number of error bits to introduce to the payload.
	 * @param bitMin
	 * 		The minimum bit index of a range of possible bits to flip.
	 * @param bitMax
	 * 		The maximum bit index of a range of possible bits to flip.
	 * @return
	 * 		The number of errors that arose.
	 */
	public int test(int times, int payloadSize, int errorBits, int bitMin, int bitMax)
	{ 
		int errors = 0;

		for (int i = 0; i < times; i++) {
			if (!test(payloadSize, errorBits, bitMin, bitMax)) {
				errors++;
			}
		}
		
		return errors;
	}
	
	/**
	 * Performs a test with the current algorithm on a random payload of the
	 * given size. The checksum code will be calculated with this payload and 
	 * then the given number of random bits in the payload will be flipped and 
	 * the checksum code will be calculated again. The resulting checksum codes 
	 * should not be equal since the two payloads are different. If the checksum
	 * codes are not equal this will return true, otherwise false. 
	 * 
	 * @param byteCount
	 * 		The number of bytes in the payload.
	 * @param errorBits
	 * 		The number of error bits to introduce to the payload.
	 * @param bitMin
	 * 		The minimum bit index of a range of possible bits to flip.
	 * @param bitMax
	 * 		The maximum bit index of a range of possible bits to flip.
	 * @return
	 * 		True if introducing the error bits did result in a different checksum.
	 */
	protected boolean test(int byteCount, int errorBits, int bitMin, int bitMax) 
	{
		// Create a random payload with the given size.
		byte[] payload = createPayload(byteCount);
		
		// Calculate the CRC code...
		int crcA = checksum.getCode16(payload);
		
		// Flip the given number of errorBits
		flip( payload, errorBits, bitMin, bitMax );
		
		// Calculate the CRC code again
		int crcB = checksum.getCode16(payload);

		// These should not be equal
		return (crcA != crcB);
	}
	
	/**
	 * Performs a test with the current algorithm on a random payload of the
	 * given size. The checksum code will be calculated with this payload and 
	 * then the given number of random bits in the payload will be flipped and 
	 * the checksum code will be calculated again. The resulting checksum codes 
	 * should not be equal since the two payloads are different. If the checksum
	 * codes are not equal this will return true, otherwise false. 
	 * 
	 * @param byteCount
	 * 		The number of bytes in the payload.
	 * @param errorBits
	 * 		The number of error bits to introduce to the payload.
	 * @return
	 * 		True if introducing the error bits did result in a different checksum.
	 */
	protected boolean test(int byteCount, int errorBits) 
	{
		return test( byteCount, errorBits, 0, (byteCount << 3) - 1 );
	}

	/**
	 * Creates a random set of indices between min and max where the number of
	 * indices returned is count.
	 * 
	 * @param count
	 * 		The number of random indices create.
	 * @param min
	 * 		The minimum allowable index (inclusive).
	 * @param max
	 * 		The maximum allowable index (inclusive).
	 * @return
	 * 		An array of indices between min and max with a length of count.
	 */
	protected int[] createIndices(int count, int min, int max) 
	{
		HashSet<Integer> covered = new HashSet<Integer>();
		while (covered.size() < count) {
			covered.add( rnd.nextInt(max - min + 1) + min );
		}
		
		int[] indices = new int[count];
		for (Integer x : covered) {
			indices[--count] = x;
		}
		return indices;
	}
	
	/**
	 * Creates a random payload with the specified size.
	 * 
	 * @param byteCount
	 * 		The size of the payload in bytes.
	 * @return
	 * 		The payload created.
	 */
	protected byte[] createPayload(int byteCount) 
	{
		byte[] payload = new byte[byteCount];
		rnd.nextBytes(payload);
		return payload;
	}
	
	/**
	 * Flips a random bit in the given array of 8-bit numbers.
	 * 
	 * @param data
	 * 		The array of 8-bit numbers.
	 */
	protected void flipRandom(byte[] data) 
	{
		flip(data, rnd.nextInt(data.length << 3));
	}
	
	/**
	 * Flips the bit at the given bit index in the array of 8-bit numbers.
	 * 
	 * @param data
	 * 		The array of 8-bit numbers.
	 * @param bitIndex
	 * 		The index of the bit in the array.
	 */
	protected void flip(byte[] data, int bitIndex) 
	{
		data[bitIndex >> 3] ^= (1 << (bitIndex & 7));
	}
	
	/**
	 * Flips a set of bits between a given minimum and maximum bit index in the
	 * byte array.
	 *  
	 * @param data
	 * 		The array of 8-bit numbers.
	 * @param bits
	 * 		The number of bits to flip in the array.
	 * @param minIndex
	 * 		The minimum bit index of a range of possible bits to flip.
	 * @param maxIndex
	 * 		The maximum bit index of a range of possible bits to flip.
	 */
	protected void flip(byte[] data, int bits, int minIndex, int maxIndex) 
	{
		for (int i : createIndices(bits, minIndex, maxIndex)) {
			flip(data, i);
		}
	}
	
}
