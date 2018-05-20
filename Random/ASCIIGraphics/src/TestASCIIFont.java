
public class TestASCIIFont 
{

	public static void main(String[] args)
	{
		ASCIIFont bubble = ASCIIFont.fromFile("BubbleGoth.font");
		ASCIIFont block = ASCIIFont.fromFile("Block.font");
		display(bubble.getString("        .:AXE:. "));
		display(bubble.getString("  .:GAME:.  "));
		display(bubble.getString(":ENGINE:"));
		display(block.getString("        .:AXE:. "));
		display(block.getString("    .:GAME:.  "));
		display(block.getString(":ENGINE:"));
		
		display(block.getString("PUSH"));
	}
	
	public static void display(String[] lines)
	{
		for (int i = 0; i < lines.length; i++)
			System.out.println(lines[i]);
	}
	
}
