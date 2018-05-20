package ship.hs2010;

import java.util.Scanner;


public class KeyboardEncryption
{
	
	public static void main(String[] args) {
		new KeyboardEncryption();
	}

	private char[] encrypt = new char[256];
	private char[] decrypt = new char[256];
	
	public KeyboardEncryption() {
		this.loadTables();

//		System.out.println(translate("you kids are just to much to handle", encrypt));
		
		Scanner input = new Scanner(System.in);
		
		int caseCount = Integer.parseInt(input.nextLine());
		
		while (--caseCount >= 0) {
			String line = input.nextLine();
			String message = translate(line, decrypt);
			
			System.out.println(message);
		}
	}
	
	private String translate(String s, char[] table) {
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char letter = s.charAt(i);
			if (table[letter] != 0) {
				result[i] = table[letter];
			}
			else {
				result[i] = letter;
			}
		}
		return String.valueOf(result);
	}
	
	private void loadTables() {
		addEncryptionLine("qwertyuiop[");
		addEncryptionLine("asdfghjkl;");
		addEncryptionLine("zxcvbnm,");
	}
	
	private void addEncryptionLine(String line) {
		for (int i = 1; i < line.length(); i++) {
			setEncryption(line.charAt(i - 1), line.charAt(i));
		}
	}
	
	private void setEncryption(char original, char encrypted) {
		encrypt[original] = encrypted;
		decrypt[encrypted] = original;
	}
	
}
