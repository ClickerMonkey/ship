package ship.weekly;

import java.util.Arrays;
import java.util.Scanner;


public class Decryption
{

	public static void main(String[] args) {
		new Decryption();
	}
	
	public Decryption() 
	{
//		print("Message without encryption", "");
//		print("Message with flipping", "f");
//		print("Message with shifting", "s");
//		print("Message with rotating", "r");
//		print("Message with reversed encryption", "ff");
//		print("Message with all encryptions", "fsr");
//		print("Crazy encrypted message", "fsrrsfsrsf");
		
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		for (int caseNumber = 1; caseNumber <= caseCount; caseNumber++) 
		{
			char[] methods = input.next().toCharArray();
			
			int byteCount = input.nextInt();
			byte[] data = new byte[byteCount];
			for (int i = 0; i < byteCount; i++) {
				data[i] = (byte)input.nextInt();
			}
			
			byte[] out = decrypt(data, methods);

			System.out.format("Case%d: '%s'\n", caseNumber, new String(out));
		}
	}
	
	public void print(String message, String method) {
		char[] methods = method.toCharArray();
		byte[] out = encrypt(message.getBytes(), methods);
		System.out.println(method);
		System.out.print(out.length);
		for (byte b : out) {
			System.out.print(" " + (b & 0xFF));
		}
		System.out.println();
		
		byte[] in = decrypt(out, methods);
		String output = new String(in);
		if (!output.equals(message)) {
			System.out.println("CRAP, ERROR!");
		}
	}
	
	public byte[] decrypt(byte[] data, char[] methods) {
		int size = data.length;
		byte[] result = Arrays.copyOf(data, size);
		int index = methods.length;
		while (--index >= 0) {
			for (int i = 0; i < size; i++) {
				result[i] = decrypt(result[i], methods[index]); 
			}
		}
		return result;
	}
	
	
	public byte[] encrypt(byte[] data, char[] methods) {
		int size = data.length;
		byte[] result = Arrays.copyOf(data, size);
		int index = -1;
		while (++index < methods.length) {
			for (int i = 0; i < size; i++) {
				result[i] = encrypt(result[i], methods[index]);
			}
		}
		return result;
	}
	
	public byte decrypt(byte b, char m) {
		switch (m) {
			case 'f': return (byte)(~b);
			case 's': return (byte)(b - 13);
			case 'r': return (byte)(((b & 0x07) << 5) | ((b & 0xF8) >> 3));
		}
		return b;
	}
	
	public byte encrypt(byte b, char m) {
		switch (m) {
			case 'f': return (byte)(~b);
			case 's': return (byte)(b + 13);
			case 'r': return (byte)(((b & 0xE0) >> 5) | ((b & 0x1F) << 3));
		}
		return b;
	}
	
}
