package dirty.test;

import static org.junit.Assert.*;
import dirty.Rectangle;

import org.junit.Test;

public class TestRectangle {

	@Test
	public void testIntersects() {
		// 0123456
		//0   DD 
		//1   DD 
		//2EEC**AA
		//3EECCCAA
		//4EE***B
		//5  FFBB

		Rectangle A = new Rectangle(5, 2, 6, 3);
		Rectangle B = new Rectangle(4, 4, 5, 5);
		Rectangle C = new Rectangle(2, 2, 4, 4);
		Rectangle D = new Rectangle(3, 0, 4, 2);
		Rectangle E = new Rectangle(0, 2, 1, 4);
		Rectangle F = new Rectangle(2, 4, 3, 5);
		
		assertFalse(C.intersects(A));
		assertTrue(C.intersects(B));
		assertTrue(C.intersects(C));
		assertTrue(C.intersects(D));
		assertFalse(C.intersects(E));
		assertTrue(C.intersects(F));
	}
	
	@Test
	public void testContains() {
		
	}
	
	@Test
	public void testIntersection() {
		
	}
	
	@Test
	public void testUnion() {
		//  012345
		//0 AAA DD
		//1 ABA DD
		//2 ACC
		//3 ACCEE
		//4 AAAEE
		
		Rectangle A = new Rectangle(0, 0, 2, 4);
		Rectangle B = new Rectangle(1, 1, 1, 1);
		Rectangle C = new Rectangle(1, 2, 2, 3);
		Rectangle D = new Rectangle(4, 0, 5, 1);
		Rectangle E = new Rectangle(3, 3, 4, 4);
		
		Rectangle U = new Rectangle();
		
		U.union(A, B);
		assertTrue(A.equals(U));
		
		U.union(A, C);
		assertTrue(A.equals(U));
		
		U.union(A, E);
		assertTrue(new Rectangle(0, 0, 4, 4).equals(U));
		
		U.union(D, C);
		assertTrue(new Rectangle(1, 0, 5, 3).equals(U));
	}
	
	@Test
	public void testComplementX() {
		
	}
	
	@Test
	public void testComplementY() {
		
	}
	
	@Test
	public void testComplementZ() {
		
	}
	
}
