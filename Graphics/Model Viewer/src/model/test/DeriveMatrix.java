package model.test;

public class DeriveMatrix {

	private static final String m0 = "0";
	private static final String m1 = "1";

	public static void main(String[] args) {
//		String[][] a = {
//			{"a00", "a01", "a02", "atx"},
//			{"a10", "a11", "a12", "aty"},
//			{"a20", "a21", "a22", "atz"},
//			{   m0,    m0,    m0,    m1},
//		};
//		String[][] b = {	//RotZ(t)
//			{  "c",  "-s",    m0,    m0},
//			{  "s",   "c",    m0,    m0},
//			{   m0,    m0,    m1,    m0},
//			{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//RotY(t)
//				{  "c",    m0,   "s",    m0},
//				{   m0,    m1,    m0,    m0},
//				{ "-s",    m0,   "c",    m0},
//				{   m0,    m0,    m0,    m1},	
//			};
//		String[][] b = {	//RotX(t)
//				{   m1,    m0,    m0,    m0},
//				{   m0,   "c",  "-s",    m0},
//				{   m0,   "s",   "c",    m0},
//				{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//Translate(dx,dy,dz)
//				{   m1,    m0,    m0,  "dx"},
//				{   m0,    m1,    m0,  "dy"},
//				{   m0,    m0,    m1,  "dz"},
//				{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//Scale(sx,sy,sz)
//				{ "sx",    m0,    m0,    m0},
//				{   m0,  "sy",    m0,    m0},
//				{   m0,    m0,  "sz",    m0},
//				{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//Shearxy(hx,hy)
//				{   m1,    m0,  "hx",    m0},
//				{   m0,    m1,  "hy",    m0},
//				{   m0,    m0,    m1,    m0},
//				{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//Shearxz(hx,hz)
//				{   m1,  "hx",    m0,    m0},
//				{   m0,    m1,    m0,    m0},
//				{   m0,  "hz",    m1,    m0},
//				{   m0,    m0,    m0,    m1},	
//		};
//		String[][] b = {	//
//				{"b00", "b01", "b02",    m0},
//				{"b10", "b11", "b12",    m0},
//				{"b20", "b21", "b22",    m0},
//				{   m0,    m0,    m0,    m1},	
//		};

		String[][] T = {
				{m1, m0, m0, "( -VRPx )"},
				{m0, m1, m0, "( -VRPy )"},
				{m0, m0, m1, "( -VRPz )"},
				{m0, m0, m0, m1},
		};
		String[][] R = {
				{"( VUPy*VPNz-VPNy*VUPz )", "( VPNx*VUPz-VUPx*VPNz )", "( VUPx*VPNy-VPNx*VUPy )", m0},
				{"( VPNy*(VUPx*VPNy-VPNx*VUPy)-(VPNx*VUPz-VUPx*VPNz)*VPNz )","( VPNz*(VUPy*VPNz-VPNy*VUPz)-VPNx*(VUPx*VPNy-VPNx*VUPy) )", "( VPNx*(VPNx*VUPz-VUPx*VPNz)-VPNy*(VUPy*VPNz-VPNy*VUPz) )", m0},
				{"( VPNx )", "( VPNy )","( VPNz )", m0},
				{m0, m0, m0, m1},	
		};
		String[][] Hpar = {
				{m1, m0, "( (PRPx-((umax+umin)/2)/PRPz )", m0},
				{m0, m1, "( (PRPy-((vmax+vmin)/2)/PRPz )", m0},
				{m0, m0, m1, m0},
				{m0, m0, m0, m1},
		};
		String[][] Tpar = {
				{m1, m0, m0, "( -(umax+umin)/2 )"},
				{m0, m1, m0, "( -(vmax+vmin)/2 )"},
				{m0, m0, m1, "( -F )"},
				{m0, m0, m0, m1},
		};
		String[][] Spar = {
				{"( 2/(umax-umin) )", m0, m0, m0},
				{m0, "( 2/(vmax-vmin) )", m0, m0},
				{m0, m0, "( 1/(F-B) )", m0},
				{m0, m0, m0, m1},
		};
		
		
		String[][] m = multiply(T, multiply(R, multiply(Hpar, multiply(Spar, Tpar))));
		
		int height = m.length;
		int width = m[0].length;
		
		int max[] = new int[width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				max[x] = Math.max(max[x], m[y][x].length());
			}
		}
		
		for (int y = 0; y < height; y++) {
			System.out.print("[");
			for (int x = 0; x < width; x++) {
				if (x > 0) System.out.print(",");
				System.out.format("  %" + max[x] + "s", m[y][x]);
			}
			System.out.println("]");
		}
	}
	
	/**
	 * a = first matrix
	 * b = second matrix
	 * m = resulting matrix
	 *
	 *                   b00 b01 b02 btx
	 *                   b10 b11 b12 bty 
	 *                   b20 b21 b22 btz
	 *                   0.0 0.0 0.0 1.0
	 *                   --- --- --- ---
	 * a00 a01 a02 atx | m00 m01 m02 mtx
	 * a10 a11 a12 aty | m10 m11 m12 mty
	 * a20 a21 a22 atz | m20 m21 m22 mtz
	 * 0.0 0.0 0.0 1.0 | 0.0 0.0 0.0 1.0
	 * 
	 * m00 = (a00*b00) + (a01*b10) + (a02*b20)
	 * m01 = (a00*b01) + (a01*b11) + (a02*b21)
	 * m02 = (a00*b02) + (a02*b12) + (a02*b22)
	 * mtx = (a00*btx) + (a01*bty) + (a02*btz) + atx
	 * 
	 * m10 = (a10*b00) + (a11*b10) + (a12*b20)
	 * m11 = (a10*b01) + (a11*b11) + (a12*b21)
	 * m12 = (a10*b02) + (a11*b12) + (a12*b22)
	 * mty = (a10*btx) + (a11*bty) + (a12*btz) + aty
	 * 
	 * m20 = (a20*b00) + (a21*b10) + (a22*b20)
	 * m21 = (a20*b01) + (a21*b11) + (a22*b21)
	 * m22 = (a20*b02) + (a21*b12) + (a22*b22)
	 * mtz = (a20*btx) + (a21*bty) + (a22*btz) + atz
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static String[][] multiply(String[][] a, String[][] b) {
		int aheight = a.length;
		int awidth = a[0].length;
		int bheight = b.length;
		int bwidth = b[0].length;
		
		if (awidth != bheight) {
			return null;
		}
		int length = awidth;
		
		String[][] m = new String[aheight][bwidth];
		
		for (int y = 0; y < aheight; y++) {
			for (int x = 0; x < bwidth; x++) {
				m[y][x] = "";
				
				for (int i = 0; i < length; i++) {
					String am = a[y][i];
					String bm = b[i][x];
					if (am == m0 || bm == m0) {
						continue;
					}
					if (m[y][x].length() > 0) {
						m[y][x] += " + ";
					}
					if (am == m1 && bm == m1) {
						m[y][x] += m1;
					}
					else if (am == m1) {
						m[y][x] += bm;
					}
					else if (bm == m1) {
						m[y][x] += am;
					}
					else {
						m[y][x] += am + "*" + bm;
					}
				}
				
				if (m[y][x] == "") {
					m[y][x] = m0;
				}
			}
		}
		
		return m;
	}
	
}
