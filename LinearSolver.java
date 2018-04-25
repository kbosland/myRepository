public class LinearSolver {
	
	String eq;
	double slope;
	double yInt;
	double xInt;
	//this is for the linear equation form Ay + Bx = C
	double A;
	double B;
	double C;

	public LinearSolver(String equation) {
		this.eq = equation;
		fixStrings();
	}

	/* This function is used to add a 1 infront of every lone-variable with no coefficient, since a lone variable
	signifies that there is exactly 1 of that variable. I have to add a 1 in front of these variables to specify 
	because the parsing method that extracts the coefficient values searches for an actual coefficient infront of a 
	specified variable. This method is also used in my LinearSolver.java and QuadraticSolver.java classes*/
	
	public void fixStrings() {
		String temp = "";

		int N = this.eq.length();
		
		//@ loop_invariant 0 <= i < N;
		//@ decreasing N - i
		for (int a = 0; a < N; a++) {
			if (this.eq.charAt(a) == 'x' || this.eq.charAt(a) == 'y') {
				if (a == 0) {
					temp = "1" + this.eq;
					this.eq = temp;
				} else if (this.eq.charAt(a - 1) == ' ') {
					temp = this.eq.substring(0, a - 1) + " 1"
							+ this.eq.substring(a, this.eq.length());
					this.eq = temp;
				}
			}
		}
	}
	
	/* This method is used to find the B value of the equation in the form Ay + Bx = C, coefficient of the x variable */
	public void findB() {
		int start = 0;
		int end = 0;
		int N = this.eq.length();

		//@ loop_invariant 0 <= i < N;
		//@ decreasing N - i
		for (int i = 0; i < N; i++) {
			if (this.eq.charAt(i) == 'x') {
				if (i == 0) {
					start = 0;
					end = 1;
					break;
				} else {
					end = i;
					
					//@loop_invariant end >= j >= 0
					//@decreasing j + end
					for (int j = end; j >= 0; j--) {
						if (this.eq.charAt(j) == ' ') {
							start = j + 1;
							break;
						}
					}
				}
			}
		}

		double b = 0;

		if (!this.eq.substring(start, end).equals("")) {

			if (start > 1) {
				if (this.eq.charAt(start - 2) == '-') {
					b = (-1) * Integer.parseInt(this.eq.substring(start, end));
				} else {
					b = Double.parseDouble(this.eq.substring(start, end));
				}
			} else {
				b = Double.parseDouble(this.eq.substring(start, end));
			}
		}
		
		this.B = b;
	}

	/* This method is used to find the A value of the equation in the form Ay + Bx = C, coefficient of the y variable */
	public void findA() {
		int start = 0;
		int end = 0;

		int N = this.eq.length();
		
		//@ loop_invariant 0 <= i < N;
		//@ decreasing N - i
		for (int i = 0; i < N; i++) {
			if (this.eq.charAt(i) == 'y') {
				if (i == 0) {
					start = 0;
					end = 1;
					break;
				} else {
					end = i;
					
					//@loop_invariant end >= j >= 0
					//@decreasing j + end
					for (int j = end; j >= 0; j--) {
						if (this.eq.charAt(j) == ' ') {
							start = j + 1;
							break;
						}
					}
				}
			}
		}

		double a = 0;

		if (!this.eq.substring(start, end).equals("")) {

			if (start > 1) {
				if (this.eq.charAt(start - 2) == '-') {
					a = (-1) * Integer.parseInt(this.eq.substring(start, end));
				} else {
					a = Double.parseDouble(this.eq.substring(start, end));
				}
			} else {
				a = Double.parseDouble(this.eq.substring(start, end));
			}

		}

		this.A = a;
	}

	/* This method is used to find the C value of the equation in the form Ay + Bx = C */
	public void findC() {
		int end = this.eq.length();
		int start = end;
		
		//@loop_invariant end - 1 >= start >= 0 
		//@decreasing (end - 1) + start
		for (start = end - 1; start >= 0; start--) {
			if (this.eq.charAt(start) == ' ') {
				break;
			}
		}
		
		this.C = Double.parseDouble(this.eq.substring(start, end));
	}

	/* This method is used to display the output of our calculations for the slope, y intercept and x intercept. 
	It also does the calculations for the slope, y intercept and the x intercept. It performs some checks on the
	variables tp see if the slope is undefined, equal to 0 or can be calculated from our B and A values */
	public String solve () {
		findB();
		findA();
		findC();
		
		String s = "";
		
		this.slope = - this.B / this.A;
		this.yInt = (this.C / this.A == 0.0) ? 0.0 : this.C / this.A;
		this.xInt = (this.C / - this.B == 0.0) ? 0.0 : this.C / this.B;
				
		s += "Equation: " + this.eq + "\n";
		
		if (this.A == 0) {
			s += "Slope: " + "Undefined!\n";
			s += "Y-Intercept: None!\n";
			s += "X-Intercept: (" + Math.abs(this.xInt) + ", 0)\n";
		} else if (this.B == 0) {
			s += "Slope: " + 0.0 + "\n";
			s += "Y-Intercept: (0, " + this.yInt + ")\n";
			s += "X-Intercept: None!\n";
		} else {
			s += "Slope: " + this.slope + "\n";
			s += "Y-Intercept: (0, " + this.yInt + ")\n";
			s += "X-Intercept: (" + this.xInt + ", 0)\n";
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		LinearSolver ls = new LinearSolver("2y + 2x = 0");
		ls.solve();
	}
}
