package ass2;

/**
 * Tests the CRC algorithm. If ran like an application this will generate the
 * data required by the project. If ran like a JUnit test this will test the
 * correctness of the implemented CRC algorithm.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TestCRC extends TestChecksum
{
	
	/**
	 * Executes the CRC Test.
	 */
	public static void main(String[] args) 
	{
		new TestCRC().run();
	}
	
	/**
	 * Instantiates a new TestCrc.
	 */
	public TestCRC() 
	{
		super(CRC._16_IBM);
	}

	/**
	 * Runs the required testing.
	 */
	public void run()
	{
		// #1a errorBits: {1,2,3,4,5,6,7,8,9,10}
		System.out.println("==1a==");
		for (int i = 1; i <= 10; i++) {
			System.out.format("%d\t%d\n", i, test(ITERATIONS, PAYLOAD_SIZE, i));
		}
		
		// #1b errorBits: {50,100,150,200,250,300,350,400,450,500}
		System.out.println("==1b==");
		for (int i = 50; i <= 500; i+=50) {
			System.out.format("%d%%\t%d\n", i, test(ITERATIONS, PAYLOAD_SIZE, (int)((i * 0.01) * PAYLOAD_SIZE)));
		}
		
		// #2 errorBits in range of 5 bytes: {1,2,3,...,38,39,40}
		System.out.println("==2==");
		for (int i = 1; i <= 40; i++) {
			System.out.format("%d\t%d\n", i, test(ITERATIONS, PAYLOAD_SIZE, i, 8, 48));
		}
	}

}
