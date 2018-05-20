package dbms.fd;

public class TestFdSet 
{

	public static void main(String[] args)
	{
		// Initial set of Fds to test closure and minimal cover.
		FdSet fds = new FdSet(new Fd[] {
				Fd.fromString("A->C"),
				Fd.fromString("A,C->D"),
				Fd.fromString("E->A,D"),
				Fd.fromString("E->H"),
		});
		System.out.println(fds);
		
		//======================================================================
		// Test A+ and E+ with-respect-to fds
		//======================================================================
		System.out.println("Closure of A:");
		System.out.println(fds.getClosure(new Set("A")));
		System.out.println("Closure of E:");
		System.out.println(fds.getClosure(new Set("E")));
		
		//======================================================================
		// Test the minimal cover of fds
		//======================================================================
		System.out.println("Minimal Cover:");
		System.out.println(fds.getMinimalCover());
	
		//======================================================================
		// Test lossless join decomposition
		//======================================================================
		System.out.println("Lossless Join:");
		
		// The schema of attributes
		System.out.print("Schema ");
		Set schema = Set.fromString("A,B,C,D,E,F,G,H,I,J");
		System.out.println(schema);

		System.out.println("Decomposition {");
		// The decomposition (subschema's) of the original schema.
		Set decomp[] = {
//				Set.fromString("A,B,C,D,E"),	// Makes lossless = true
				Set.fromString("A,B,C,D"),		// Makes lossless = false
				Set.fromString("D,E"),
				Set.fromString("B,F"),
				Set.fromString("F,G,H"),
				Set.fromString("D,I,J"),
		};
		for (Set s : decomp) {
			System.out.println("\t" + s + ",");
		}
		System.out.println("}");
		
		// Functional Dependencies on schema
		FdSet F = new FdSet(new Fd[] {
				Fd.fromString("A,B->C"),
				Fd.fromString("A->D,E"),
				Fd.fromString("B->F"),
				Fd.fromString("F->G,H"),
				Fd.fromString("D->I,J")
		});
		System.out.println(F);
		
		// Determine if the decomposition is lossless.
		Decomposition D = new Decomposition(schema, F, decomp);
		System.out.println("Is Lossless: " + D.isLossless());
		
		

		//======================================================================
		// Another lossless join decomposition (example in notes)
		//======================================================================
		schema = Set.fromString("Snum,City,Status");
		F = new FdSet(new Fd[] {
				Fd.fromString("Snum->City"),
				Fd.fromString("City->Status"),	
			});
		decomp = new Set[] {
				Set.fromString("Snum,City"),
				Set.fromString("City,Status"),
			};

		D = new Decomposition(schema, F, decomp);
		System.out.println("True ?= " + D.isLossless());

		decomp = new Set[] {
				Set.fromString("Snum,City"),
				Set.fromString("Snum,Status"),
			};
		D = new Decomposition(schema, F, decomp);
		System.out.println("True ?= " + D.isLossless());
		
	}
	
}
