package ass2;

public class Lab2 
{

	public static void main(String[] args)
	{
		Checksum cs = new InternetChecksum();
		
		byte[] data1 = {(byte)182, (byte)187, (byte)184, (byte)140, (byte)144, (byte)183};
		int sum1 = cs.getCode16(data1);

		System.out.format("%x\n", sum1);
		
		byte[] data2 = {(byte)182, (byte)186, (byte)184, (byte)141, (byte)144, (byte)183};
		int sum2 = cs.getCode16(data2);
		
		System.out.format("%x\n", sum2);
	}
	
}
