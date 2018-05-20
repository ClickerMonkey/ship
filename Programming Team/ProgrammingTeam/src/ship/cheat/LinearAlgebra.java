package ship.cheat;

public class LinearAlgebra
{

	public class Fraction {
		public int num, den;
		// Initializes a new fraction
		public Fraction(int num, int den) {
			set(num, den);
		}
		// Sets the numerator and denominator of the fraction and simplifies
		public void set(int numerator, int denominator) {
			num = numerator;
			den = denominator;
			simplify();
		}
		// Sets this fraction from another fraction
		public void set(Fraction f) {
			set(f.num, f.den);
		}
		// Adds the given fraction to this fraction
		public void add(Fraction f) {
			set((num * f.den) + (den * f.num), (den * f.den));
		}
		// Subtracts the given fraction from this fraction
		public void sub(Fraction f) {
			set((num * f.den) - (den * f.num), (den * f.den));
		}
		// Multiplies this fraction by the given fraction
		public void mul(Fraction f) {
			set((num * f.num), (den * f.den));
		}
		// Divides this fraction by the given fraction
		public void div(Fraction f) {
			set((num * f.den), (den * f.num));
		}
		// Returns the inverse of this fraction
		public Fraction inverse() {
			return new Fraction(den, num);
		}
		// Returns the reciprical of this fraction
		public Fraction reciprical() {
			return new Fraction(-den, num);
		}
		// Returns the negation of this fraction
		public Fraction negative() {
			return new Fraction(-num, den);
		}
		// Simplifies this fraction and corrects any signs
		public void simplify() {
			int gcd = (int)NumberTheory.gcd(num, den);
			num /= gcd;
			den /= gcd;
			// Maintain signed numerator and positive denoninator
			if (den < 0 || (num < 0 && den < 0)) {
				den = -den;
				num = -num;
			}
		}
	}

	public int columnCount;
	public int rowCount;
	public Fraction[][] matrix;
	// Creates an mxn matrix of fractions
	public void createMatrix(int rows, int columns) {
		rowCount = rows;
		columnCount = columns;
		matrix = new Fraction[rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				matrix[r][c] = new Fraction(0, 1);
			}
		}
	}
	// Switches the two given rows
	public void rowSwitch(int row1, int row2) {
		Fraction[] row = matrix[row1];
		matrix[row1] = matrix[row2];
		matrix[row2] = row;
	}
	// Multiplies a row by a given fraction
	public void rowScale(int row, Fraction scalar) {
		for (int c = 0; c < columnCount; c++) {
			matrix[row][c].mul(scalar);
		}
	}
	// Multiplies a row by a given fraction and adds it to another row
	public void rowAddScaled(int addToRow, int timesRow, Fraction timesBy) {
		Fraction scalar = new Fraction(1, 1);
		for (int c = 0; c < columnCount; c++) {
			scalar.set(matrix[timesRow][c]);
			scalar.mul(timesBy);
			matrix[addToRow][c].add(scalar);
		}
	}
	// Computes Row Reduced Echelon Form
	public void rref() {
		int pivot = 0;
		for (int c = 0; c < columnCount; c++) {
			// Find the next pivot row (a non zero pivot)
			int row = pivot;
			while (row < rowCount && (matrix[row][c].num == 0))
				row++;
			// If there are no more pivots then exit
			if (row == rowCount)
				return;
			// If the selected pivot is different then the expected pivot then switch
			if (row != pivot)
				rowSwitch(row, pivot);
			// Times the pivot row by the inverse of the pivot (making it 1)
			rowScale(pivot, matrix[pivot][c].inverse());
			// For every row (minus the pivot) make the current column a zero
			for (int r = 0; r < rowCount; r++) {
				if (r != pivot) {
					rowAddScaled(r, pivot, matrix[r][c].negative());
				}
			}
			// Increase the pivot row
			pivot++;
			// Exit once the pivot is beyond the matrix
			if (pivot == rowCount)
				return;
		}
	}

}
