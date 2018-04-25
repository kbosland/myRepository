public class QuadraticSolver {

	String ogEq;
	String eq;
	double[] vertex;
	double[] xInts;
	double yInt;

	double A;
	double B;
	double C;

	//@ ensures \valid equation
	public QuadraticSolver(String equation) {
		this.ogEq = equation;
		this.eq = equation;
		this.vertex = new double[2];
		this.xInts = new double[2];
		this.yInt = 0;
		this.A = 0;
		this.B = 0;
		this.C = 0;
		fixStrings();
		solve();
	}

	/* This function is used to add a 1 infront of every lone-variable with no coefficient, since a lone variable
	signifies that there is exactly 1 of that variable. I have to add a 1 in front of these variables to specify 
	because the parsing method that extracts the coefficient values searches for an actual coefficient infront of a 
	specified variable. This method is also used in my LinearSolver.java and QuadraticSolver.java classes*/
	
	public void fixStrings() {
		String temp = "";
		int N = this.eq.length();
	
		//@ loop_invariant 0 <= a < N
		//@ decreasing N - i
		for (int a = 0; a < N; a++) {
			if (this.eq.charAt(a) == 'x' || this.eq.charAt(a) == 'y') {
				if (a == 0) {
					temp = "1" + this.eq;
					this.eq = temp;
				} else if (this.eq.charAt(a - 1) == ' ') {
					temp = this.eq.substring(0, a - 1) + " 1" + this.eq.substring(a, this.eq.length());
					this.eq = temp;
				}
			}
		}
		
		if (this.eq.charAt(this.eq.length()-1) == 'x') {
			this.eq = this.eq + " + 0";
		}
	}

	/* This method is used to find the coefficient of the x^2 value. */
	
	public void findA() {
		int start = 0;
		int end = 0;
		int N = this.eq.length();
		
		//@ loop_invariant 0 <= i < N
		//@ decreasing N - i
		for (int i = 0; i < N; i++) {

			if (this.eq.charAt(i) == 'x' && i <= this.eq.length() - 3) {
				if (this.eq.charAt(i + 1) == '^' && this.eq.charAt(i + 2) == '2') {
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

	/* This method is used to find the coefficient of the x value */
	public void findB() {
		int start = 0;
		int end = 0;

		//@ loop_invariant 0 <= i < N
		//@ decreasing N - i
		for (int i = 0; i < this.eq.length(); i++) {
			if (this.eq.charAt(i) == 'x' && i <= this.eq.length() - 2) {
				if (this.eq.charAt(i + 1) != '^') {
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

	/* This method is used to find the C value of the Ax^2 + Bx + Y formula*/
	public void findC() {
		int end = this.eq.length();
		int start = end;
		
		if (this.eq.charAt(end-1) != 'x') {
			
		//@ loop_invariant end - 1 >= start >= 0
		//@ decreasing (end - 1) - start
		for (start = end - 1; start >= 0; start--) {
			if (this.eq.charAt(start) == ' ') {
				break;
			}
		}

		this.C = Double.parseDouble(this.eq.substring(start, end));
		} else {
			this.C = 0;
		}
	}

	/* This method calculates the vertex of the equation. This is completed by substituting -b/2a as the x value. We 
	substitute -b/2a because this is an efficient way to calculate the x value of the vertex of a parabola. Next, we 
	evaluate the expression after substituting in our x coordinate for the vertex, and we find the y value of the 
	vertex. We then return the vertex. */
	
	public void findVertex() {
		double[] vertex = new double[2];
		double x = (-this.B / (2 * this.A) == 0) ? 0 : -this.B / (2 * this.A);
		String subbedEq = this.eq;
		int N = this.eq.length();
		
		//@ loop_invariant 0 <= i < N
		//@ decreasing N - i
		for (int i = 0; i < N; i++) {
			if (subbedEq.charAt(i) == 'x') {
				subbedEq = subbedEq.substring(0, i) + "*" + Double.toString(x)
						+ subbedEq.substring(i + 1, subbedEq.length());
			}
		}

		double y = this.A * (Math.pow(x, 2)) + this.B * x + this.C;
		vertex[0] = (x == 0) ? 0 : x;
		vertex[1] = (y == 0) ? 0 : y;

		this.vertex = vertex;
	}

	/* This method is used to find the x intercepts of the parabola. Essentially, we run our extracted A, B and C values 
	through the quadratic formula. I then performed some checks to see if the equation has 1 or 2 roots. If it has 1 root, 
	we set the other to Positive.Infinity to to signify that the second intercept does not exist. Next, we have an if 
	statement to check if the quadratic formula is performing any illegal operations, in this case taking the square root 
	of a negative number. If so, we set both x intercepts to Positive.Infinity to signify that there are no x intercepts
	for this parabola. */
	
	public void findXInts() {
		double[] xInts = new double[2];

		double x1 = Math.round(((-this.B + (Math.sqrt(Math.pow(this.B, 2) - 4 * this.A * this.C))) / (2 * this.A)) * 100) / 100.00;
		double x2 = Math.round(((-this.B - (Math.sqrt(Math.pow(this.B, 2) - 4 * this.A * this.C))) / (2 * this.A)) * 100) / 100.00;

		xInts[0] = (x1 == 0) ? 0 : x1;
		xInts[1] = (x2 == 0) ? 0 : x2;
		xInts[1] = (x1 == x2) ? Double.POSITIVE_INFINITY : x2;
		
		if (Math.pow(this.B, 2) - 4*this.A*this.C < 0) {
			xInts[0] = Double.POSITIVE_INFINITY;
			xInts[1] = Double.POSITIVE_INFINITY;
		}

		this.xInts = xInts;
	}

	/* This method is to find the y intercepts of the quadratic. This is extremely easy, as we just need to find the value 
	of the translation up the Y axis for the graph.*/
	public void findYInt() {
		this.yInt = this.C;
	}

	/* This method simply creates the String with the data we have calculated to output the solution in a proper way. 
	It performs checks to see if there are any x intercepts, and outputs them accordingly. */
	public String solve() {
		findA();
		findB();
		findC();
		findVertex();
		findXInts();
		findYInt();
		
		String s = "";

		s += "Equation: " + this.ogEq + "\n";
		s += "Vertex of Function: (" + this.vertex[0] + ", " + this.vertex[1] + ")\n";
		
		if (this.xInts[0] == Double.POSITIVE_INFINITY && this.xInts[1] == Double.POSITIVE_INFINITY) {
			s += "X-Intercept(s): This function will not cross the X-Axis\n";
		} else if (this.xInts[0] == Double.POSITIVE_INFINITY) {
			 s += "X-Intercept(s): (" + this.xInts[1] + ", 0.0)\n";
		} else if (this.xInts[1] == Double.POSITIVE_INFINITY) {
			s += "X-Intercept(s): (" + this.xInts[0] + ", 0.0)\n";
		} else {
			s += "X-Intercept(s): (" + this.xInts[0] + ", 0.0) and (" + this.xInts[1] + ", 0.0)\n";
		}
		
		s += "Y-Intercept: (0.0, " + this.yInt + ")";
		
		return s;
	}

	public static void main(String[] args) {
		QuadraticSolver qs = new QuadraticSolver("y = x^2 + 2x");
	}
}
