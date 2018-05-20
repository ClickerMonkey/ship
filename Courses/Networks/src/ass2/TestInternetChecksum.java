package ass2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the 16-bit checksum algorithm. If ran like an application this will 
 * generate the data required by the project. If ran like a JUnit test this will
 * test the correctness of the implemented checksum algorithm.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TestInternetChecksum extends TestChecksum
{

	/**
	 * Executes the Internet Checksum Test.
	 */
	public static void main(String[] args) 
	{
		new TestInternetChecksum().run();
	}
	
	/**
	 * Instantiates a new TestInternetChecksum.
	 */
	public TestInternetChecksum() 
	{
		super(new InternetChecksum());
	}

	/**
	 * 
	 */
	public void run()
	{
		final int ITERATIONS = 10000;
		final int PAYLOAD_SIZE = 1000;
		
		// #1a errorBits: {1,2,3,4,5,6,7,8,9,10}
		System.out.println("==1a==");
		for (int i = 1; i <= 10; i++) {
			System.out.format("%d\t%d\n", i, test(ITERATIONS, PAYLOAD_SIZE, i));
		}
		
		// #1b errorBits: {50,100,150,200,250,300,350,400,450,500}
		System.out.println("==1b==");
		for (int i = 50; i <= 500; i+=50) {
			System.out.format("%d%%\t%d\n", i / 10, test(ITERATIONS, PAYLOAD_SIZE, (int)((i * 0.01) * PAYLOAD_SIZE)));
		}
		
		// #2 errorBits in range of 5 bytes: {1,2,3,...,38,39,40}
		System.out.println("==2==");
		for (int i = 1; i <= 40; i++) {
			System.out.format("%d\t%d\n", i, test(ITERATIONS, PAYLOAD_SIZE, i, 8, 48));
		}
	}
	
	/**
	 * Takes a set of bytes, calculates the checksum, appends the checksum
	 * to the bytes, and computes the checksum of that as well and 0xFFFF should
	 * be returned to show that the algorithm is valid.
	 */
	@Test
	public void testChecksum()
	{
		// http://www.cs.berkeley.edu/~kfall/EE122/lec06/tsld023.htm
		byte[] data1 = Bytes.fromHex("e34f2396442799f3");
		
		// First checksum
		short sum1 = (short)checksum.getCode16(data1);
		
		// Checksum is known
		assertEquals((short)0x1AFF, sum1);
		
		// Append checksum to data
		byte[] data2 = Bytes.append(data1, sum1);
		
		// Compute checksum of data1 + sum1
		short sum2 = (short)checksum.getCode16(data2);

		// This will be 0xFFFF if correct.
		assertEquals((short)0xFFFF, sum2);
	}
	
	/**
	 * Tests the computation of a checksum if it has odd bytes.
	 */
	@Test
	public void testOddBytes() 
	{
		byte[] data1 = {0x1f, 0x05, 0x2F, 0x00, 0x00};
		byte[] data2 = {0x1f, 0x05, 0x2F, 0x00};
	
		assertEquals( checksum.getCode16(data1), checksum.getCode16(data2) );
	}

	
}
