package dbms.fd;

public class TestSet
{

	public static void main(String[] args)
	{
		System.out.println("Basic Operations");
		
		Set A = new Set("1", "2", "3");
		Set B = new Set("2", "3", "4");

		System.out.print("A = "); A.print();
		System.out.print("B = "); B.print();
		System.out.print("A union B  = "); Set.union(A, B).print();
		System.out.print("A intersect B = "); Set.intersect(A, B).print();
		System.out.print("A difference B = "); Set.difference(A, B).print();
		System.out.print("A complement B = "); Set.complement(A, B).print();
		System.out.print("B complement A = "); Set.complement(B, A).print();
	}
	
}
