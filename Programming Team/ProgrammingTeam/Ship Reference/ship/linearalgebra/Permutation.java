package ship.linearalgebra;


public class Permutation
{

	void permute(int[] values, int k) {
		if (k == 0) {
			// use values
		}
		else {
			for (int i = 0; i < k; i++) {
				permute(values, k - 1);
				swap(values, 0, i);
			}
		}
	}
	void swap(int[] v, int i, int j) {
		v[i] ^= v[j];
		v[j] ^= v[i];
		v[i] ^= v[j];
	}
	
}
