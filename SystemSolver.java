import java.util.ArrayList;

public class SystemSolver {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;

	ArrayList<String> eq = new ArrayList<String>();

	public double[] results;
	public double[][] coeffs;

	public SystemSolver(ArrayList<String> data) {
		this.eq = data;
		this.coeffs = new double[data.size()][3];
		this.results = new double[data.size()];

		fixStrings();
		fillMatricies();
	}
	
	/* This function is used to add a 1 in front of every lone-variable with no coefficient, since a lone variable
	signifies that there is exactly 1 of that variable. I have to add a 1 in front of these variables to specify 
	because the parsing method that extracts the coefficient values searches for an actual coefficient infront of a 
	specified variable. This method is also used in my LinearSolver.java and QuadraticSolver.java classes*/
	
	public void fixStrings() {
		String temp = "";

		//@ loop_invariant 0 <= i < 3;
		//@ decreasing 3 - i
		for (int i = 0; i < this.eq.size(); i++) {
			int N = this.eq.get(i).length();
			
			//@ loop_invariant 0 <= a < N
			//@ decreasing N - i
			for (int a = 0; a < N; a++) {
				if (this.eq.get(i).charAt(a) == 'x' || this.eq.get(i).charAt(a) == 'y'
						|| this.eq.get(i).charAt(a) == 'z') {
					if (a == 0) {
						temp = "1" + this.eq.get(i);
						this.eq.set(i, temp);
					} else if (this.eq.get(i).charAt(a - 1) == ' ') {
						temp = this.eq.get(i).substring(0, a - 1) + " 1"
								+ this.eq.get(i).substring(a, this.eq.get(i).length());
						this.eq.set(i, temp);
					}
				}
			}
		}
	}

	/* This method is used to fill the coeffs array as well as the results array by searching the coefficient in front
	of each x, y and z variable. After it finds the start and end position of the coefficient of a variable, it checks
	if the substring of the equation between the start and end position is empty, and if so it assigns a 0 to this spot 
	in the coefficient array since there was no variable found. It extracts the digit for the results array by starting
	at the end of the string, and working backward until it finds a space which signals the end of the number. Then, the
	method parses the substring of the equation from the start and end index to a double, and assigns it to the results 
	array */
	
	public void fillMatricies() {

		// THIS LOOP IS TO FILL THE EQUATIONS ARRAY, WITH COEFFICIENTS OF X,Y AND Z
		// VARIABLES
		int start = 0;
		int end = 0;
		int index = 0;

		//@ loop_invariant 0 <= z <= 2;
		//@ decreasing 2-z;
		for (int z = 0; z < 3; z++) {
			index = 0;
			int N = this.eq.get(z).length();

			//@ loop_invariant 0 <= i < N;
			//@ decreasing N - i;
			for (int i = 0; i < N; i++) {
				if (this.eq.get(z).charAt(i) == 'x' || this.eq.get(z).charAt(i) == 'y'
						|| this.eq.get(z).charAt(i) == 'z') {
					if (i == 0) {
						start = 0;
						end = 1;
						break;
					} else {
						end = i;
						for (int j = end; j >= 0; j--) {
							if (this.eq.get(z).charAt(j) == ' ') {
								start = j + 1;
								break;
							}
						}
					}
				}
				
				//@ assert 0 <= start < N

				if (!this.eq.get(z).substring(start, end).equals("")) {
					double a;

					if (start > 1) {
						if (this.eq.get(z).charAt(start - 2) == '-') {
							a = (-1) * Double.parseDouble(this.eq.get(z).substring(start, end));
						} else {
							a = Double.parseDouble(this.eq.get(z).substring(start, end));
						}
					} else {
						a = Double.parseDouble(this.eq.get(z).substring(start, end));
					}

					if (this.eq.get(z).charAt(i) == 'x') {
						this.coeffs[z][X] = a;
					} else if (this.eq.get(z).charAt(i) == 'y') {
						this.coeffs[z][Y] = a;
					} else if (this.eq.get(z).charAt(i) == 'z') {
						this.coeffs[z][Z] = a;
					}
					
					start = 0;
					end = 0;
					index++;
				}
			}

			start = this.eq.get(z).length() - 1;
		
			//@ loop_invariant 0 =< i =< start
			//@ decreasing i
			for (int i = this.eq.get(z).length() - 1; i >= 0; i--) {
				if (this.eq.get(z).charAt(i) == ' ') {
					start = i;
					this.results[z] = Double.parseDouble(this.eq.get(z).substring(start + 1, this.eq.get(z).length()));
					break;
				}
			}

			start = 0;
		}
	}
	
	/* This method is used to find the determinant for each cofactor of the array using simple arithmetic. It is used solely
	in the inverse method. */
	
	public double findDeterminant2x2 (double a, double b, double c, double d) {
		return (a * d - c * b);
	}

	/* This method calculates and returns the inverse of the coefficient array. The strategy I utilized was to first find
	the cofactors matrix, and use this matrix to calculate the adjoint matrix. Then, I multiplied the adjoint matrix by 
	1/determinant, giving me the inverse. This is one of the most important steps in solving a system of linear equations
	by using matrices */
	
	//@ assignable determinant, minor1, minor2, minor3;
	public double [][] inverse() {
		double [][] cofactor = new double [this.coeffs.length][this.coeffs[0].length];
		double determinant = 0;
		double minor1;
		double minor2;
		double minor3;
		
		//filling the cofactor matrix from the coefficients matrix
		cofactor[0][0] = findDeterminant2x2(this.coeffs[1][1], this.coeffs[1][2], this.coeffs[2][1], this.coeffs[2][2]);
		cofactor[0][1] = -1 * findDeterminant2x2(this.coeffs[1][0], this.coeffs[1][2], this.coeffs[2][0], this.coeffs[2][2]);
		cofactor[0][2] = findDeterminant2x2(this.coeffs[1][0], this.coeffs[1][1], this.coeffs[2][0], this.coeffs[2][1]);
		
		//saving these cofactor values for determinant calculation
		minor1 = cofactor[0][0];
		minor2 = -1 * cofactor[0][1];
		minor3 = cofactor[0][2];
		
		cofactor[1][0] = -1 * findDeterminant2x2(this.coeffs[0][1], this.coeffs[0][2], this.coeffs[2][1], this.coeffs[2][2]);
		cofactor[1][1] = findDeterminant2x2(this.coeffs[0][0], this.coeffs[0][2], this.coeffs[2][0], this.coeffs[2][2]);
		cofactor[1][2] = -1 * findDeterminant2x2(this.coeffs[0][0], this.coeffs[0][1], this.coeffs[2][0], this.coeffs[2][1]);
				
		cofactor[2][0] = findDeterminant2x2(this.coeffs[0][1], this.coeffs[0][2], this.coeffs[1][1], this.coeffs[1][2]);
		cofactor[2][1] = -1 * findDeterminant2x2(this.coeffs[0][0], this.coeffs[0][2], this.coeffs[1][0], this.coeffs[1][2]);
		cofactor[2][2] = findDeterminant2x2(this.coeffs[0][0], this.coeffs[0][1], this.coeffs[1][0], this.coeffs[1][1]);
		
		//swapping variables over the diagonal to form the adjoint matrix
		double temp = cofactor[1][0];
		cofactor[1][0] = cofactor[0][1];
		cofactor[0][1] = temp;
		
		temp = cofactor[2][0];
		cofactor[2][0] = cofactor[0][2];
		cofactor[0][2] = temp;
		
		temp = cofactor[2][1];
		cofactor[2][1] = cofactor[1][2];
		cofactor[1][2] = temp;

		//finding the determinant of the matrix
		determinant = this.coeffs[0][0] * minor1 + this.coeffs[0][1] * minor2 + this.coeffs[0][2] * minor3;
				
		//multiplying each entry in the adjoint by 1/determinant
		
		//@ loop_invariant 0 <= i < 3;
		//@ decreasing 3 - i;
		for (int i = 0; i < this.coeffs.length; i++) {
			
			//@ loop_invariant 0 <= j < 3
			//@ decreasing 3 - i;
			for (int j = 0; j < this.coeffs[0].length; j++) {
				cofactor[i][j] = cofactor[i][j] * (1.0 / determinant);
			}
		}
				
		return cofactor;
	}
	
	/* This method uses loops to calculate the result of two matrices being multipled together. */
	
	//@ assignable product, A;
	public double [] multiply () {
		double [] product = new double [this.coeffs.length];
		double [][] A = inverse();
		
		//@ loop_invariant 0 <= row < 3;
		//@ decreasing 3 - row;
		for (int row = 0; row < this.coeffs.length; row++) {
			product[row] = this.results[0] * A[row][0] + this.results[1] * A[row][1] + this.results[2] * A[row][2];
		}
		
		return product;
	}

	public String solve() {
		double [] x = multiply();
		
		return "The value of X is: " + Math.round((x[0]) * 100) / 100.00 + "\n" + "The value of Y is: " + Math.round((x[1]) * 100) / 100.00 + "\n" + "The value of Z is: " + Math.round((x[2]) * 100) / 100.00;
	}
	
	/* This method returns the values calculated for x, y and z in a simple array for test cases in Tester.Java*/
	public double[] testing() {
		double [] x = multiply();
		x[0] = Math.round((x[0]) * 100) / 100.00;
		x[1] = Math.round((x[1]) * 100) / 100.00;
		x[2] = Math.round((x[2]) * 100) / 100.00;
		return x;
	}

	public void displayData() {

		System.out.println("Equations\n------------------");
		//@ loop_invariant 0 <= i < 3
		//@ decreasing 3 - i
		for (int i = 0; i < this.eq.size(); i++) {
			System.out.println(this.eq.get(i));
		}
		System.out.println();
		System.out.println("Coefficients\n------------------");
		
		//@ loop_invariant 0 <= i < 3
		//@ decreasing 3 - i
		for (int i = 0; i < this.coeffs.length; i++) {
			//@ loop_invariant 0 <= j < 3
			//@ decreasing 3 - j
			for (int j = 0; j < this.coeffs[0].length; j++) {
				System.out.print(this.coeffs[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Results\n------------------");
		
		//@ loop_invariant 0 <= i < 3
		//@ decreasing 3 - i
		for (int i = 0; i < this.results.length; i++) {
			System.out.print(this.results[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		String eq1 = "x + z = 6";
		String eq2 = "z - 3y = 7";
		String eq3 = "2x + y + 3z = 15";

		ArrayList<String> equations = new ArrayList<String>();
		equations.add(eq1);
		equations.add(eq2);
		equations.add(eq3);

		SystemSolver s = new SystemSolver(equations);
		System.out.println(s.solve());

	}
}