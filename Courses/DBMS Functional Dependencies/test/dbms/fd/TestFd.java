package dbms.fd;

public class TestFd 
{

	public static void main(String[] args)
	{
		Set X = new Set("A");
		Set Y = new Set("B", "C");
		
		Fd fd = new Fd(X, Y);
		
		System.out.println(fd);
	}
	
}
