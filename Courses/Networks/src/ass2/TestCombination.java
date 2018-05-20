package ass2;

/**
 * Tests the combination of CRC and Checksum.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TestCombination extends TestChecksum 
{
	
	/**
	 * Executes the Combination Test.
	 */
	public static void main(String[] args) 
	{
		new TestCombination().run();
	}

	// An instance to provide to test and to use to change the order.
	private Combination combo;
	
	/**
	 * Instantiates a new TestCombination.
	 */
	public TestCombination() 
	{
		super(new Combination());
		combo = (Combination)checksum;
	}
	
	/**
	 * Runs the required testing.
	 */
	public void run()
	{
		// Set intial order.
		combo.order = Order.ChecksumCrc;
		
		// #1a errorBits: {2}
		System.out.println("==1a==");
		System.out.format("%d\t%d\n", 2, test(ITERATIONS, PAYLOAD_SIZE, 2));
		
		// #1b errorBits: {250}
		System.out.println("==1b==");
		System.out.format("%d%%\t%d\n", 250, test(ITERATIONS, PAYLOAD_SIZE, (int)(0.25 * PAYLOAD_SIZE)));
		
		// #2 errorBits in range of 5 bytes: {2}
		System.out.println("==2==");
		System.out.format("%d\t%d\n", 2, test(ITERATIONS, PAYLOAD_SIZE, 2, 8, 48));

		// Change order, perform same tests.
		combo.order = Order.CrcChecksum;
		
		// #1a errorBits: {2}
		System.out.println("==1a==");
		System.out.format("%d\t%d\n", 2, test(ITERATIONS, PAYLOAD_SIZE, 2));
		
		// #1b errorBits: {250}
		System.out.println("==1b==");
		System.out.format("%d%%\t%d\n", 250, test(ITERATIONS, PAYLOAD_SIZE, (int)(0.25 * PAYLOAD_SIZE)));
		
		// #2 errorBits in range of 5 bytes: {2}
		System.out.println("==2==");
		System.out.format("%d\t%d\n", 2, test(ITERATIONS, PAYLOAD_SIZE, 2, 8, 48));
		
	}


	/**
	 * The order in which to do checksums.
	 * 
	 * @author Philip Diffenderfer
	 *
	 */
	private static enum Order 
	{
		/**
		 * First performs the Internet Checksum and then a CRC.
		 */
		ChecksumCrc, 
		/**
		 * First performs a CRC and then the Internet Checksum.
		 */
		CrcChecksum;
	}
	
	/**
	 * A combination of Internet Checksum and CRC.
	 * 
	 * @author Philip Diffenderfer
	 *
	 */
	private static class Combination extends AbstractChecksum 
	{
		public Order order = Order.ChecksumCrc;
		private InternetChecksum checksum = new InternetChecksum();
		private CRC crc = CRC._16_IBM;
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public long getCode32(byte[] data, int off, int len) 
		{
			switch (order) {
			case ChecksumCrc:
				
				short code1 = (short)checksum.getCode16(data, off, len);
				byte[] data1 = new byte[len + 2];
				System.arraycopy(data, off, data1, 0, len);
				data1[len + 0] = (byte)((code1 >> 8) & 0xFF);
				data1[len + 1] = (byte)((code1 >> 0) & 0xFF);
				
				return crc.getCode32(data1);
				
			case CrcChecksum:

				short code2 = (short)crc.getCode16(data, off, len);
				byte[] data2 = new byte[len + 2];
				System.arraycopy(data, off, data2, 0, len);
				data2[len + 0] = (byte)((code2 >> 8) & 0xFF);
				data2[len + 1] = (byte)((code2 >> 0) & 0xFF);
				
				return checksum.getCode32(data2);
			}
			return -1;
		}
	}
	
}
