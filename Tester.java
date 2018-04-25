import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {

		//TEST CASES FOR THE LINEAR SYSTEM OF EQUATIONS SOLVER
		
		System.out.println("TEST CASES FOR THE LINEAR SYSTEM OF EQUATIONS SOLVER");
		System.out.println("----------------------------------------------------\n");
		
		ArrayList<String> equations = new ArrayList<String>();
		double [] x = new double[3];
			
		//First test case is a regular linear system of equations
		String eq1 = "x + z = 6";
		String eq2 = "z - 3y = 7";
		String eq3 = "2x + y + 3z = 15";
		System.out.println("TEST CASE 1 - \nEquation 1 : x + z = 6\nEquation 2 : z + 3y = 7\nEquation 3 : 2x + y + 3z = 15");
		System.out.println("Expected Output: x = 2, y = -1, z = 4\n");
		
		//instantiating system solver and loading in the equations
		equations.add(eq1); equations.add(eq2); equations.add(eq3);
		SystemSolver s = new SystemSolver(equations);
		x = s.testing();
		
		//checking if the expected answers are given
		if (x[0] == 2 && x[1] == -1 && x[2] == 4) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		
		//Second test case is checking if expected output even when no x, y or z variables in equation
		eq1 = "0 = 0";
		eq2 = "0 = 0";
		eq3 = "0 = 0";
		equations = new ArrayList<String>();
		equations.add(eq1); equations.add(eq2); equations.add(eq3);
		s = new SystemSolver(equations);
		x = s.testing();
		
		System.out.println("TEST CASE 2 - \nEquation 1 : 0 = 0\nEquation 2 : 0 = 0\nEquation 3 : 0 = 0");
		System.out.println("Expected Output: x = 0, y = 0, z = 0\n");
		
		if (x[0] == 0 && x[1] == 0 && x[2] == 0) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Third test case is checking if our program fails to calculate when only given two equations with two variables - since we require 3 equations in our program
		eq1 = "x + y = 10";
		eq2 = "x - y = 2";
		eq3 = "0 = 0";
		equations = new ArrayList<String>();
		equations.add(eq1); equations.add(eq2); equations.add(eq3);
		s = new SystemSolver(equations);
		x = s.testing();
		
		System.out.println("TEST CASE 3 - \nEquation 1 : x + y = 10\nEquation 2 : x - y = 2\nEquation 3 : 0 = 0");
		System.out.println("Expected Output: x = 0, y = 0, z = 0\n");
				
		if (x[0] == 0 && x[1] == 0 && x[2] == 0) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Fourth test case is checking if our program fails to calculate when given equations with incorrect input format
		eq1 = "10 = x + y";
		eq2 = "z + y + x";
		eq3 = "x";
		equations = new ArrayList<String>();
		equations.add(eq1); equations.add(eq2); equations.add(eq3);
		
		System.out.println("TEST CASE 3 - \nEquation 1 : 10 = x + y\nEquation 2 : z + y + x\nEquation 3 : x");
		System.out.println("Expected Output: NumberFormatException\n");
		
		//this should result in a NumberFormatException, so going to add a try/catch statement
		try {
			s = new SystemSolver(equations);
			x = s.testing();
		} catch (NumberFormatException nfe) {
			System.out.println("TEST CASE PASSED\n\n");
		}
				
		//TEST CASES FOR THE LINEAR EQUATION SOLVER
		
		//First test case is for a regular linear equation
		eq1 = "2y - 3x = 7";
		LinearSolver ls = new LinearSolver(eq1);
		ls.solve();
		System.out.println("TEST CASES FOR THE LINEAR EQUATION SOLVER");
		System.out.println("----------------------------------------------------\n");
		
		System.out.println("Test Case 1 - Equation: 2y - 3x = 7");
		System.out.println("Expected Output: Slope = 1.5, Y-Int: (0, 3.5), X-Int: (-7/3, 0)\n");
						
		if (ls.slope == 1.5 && ls.yInt == 3.5 && (Math.round(ls.xInt) * 100)/100.00 == (Math.round(-2.3333333333) * 100)/100.00) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Second test case is for a linear equation with 0 slope
		eq1 = "-3y = 6";
		ls = new LinearSolver(eq1);
		ls.solve();
		
		System.out.println("Test Case 2 - Equation: -3y = 6");
		System.out.println("Expected Output: Slope = 0, Y-Int: (0, -2), X-Int: None\n");
						
		if (ls.slope == 0 && ls.yInt == -2 && ls.B == 0) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Third test case is for a linear equation with undefined slope
		eq1 = "0 = 4x - 3";
		ls = new LinearSolver(eq1);
		ls.solve();
		
		System.out.println("Test Case 3 - Equation: 0 = 4x - 3");
		System.out.println("Expected Output: Slope = Undefined, Y-Int: None, X-Int: (0.75, 0)\n");
						
		if (ls.A == 0 && ls.xInt == 0.75) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Fourth test case is for improper input format, expecting an error
		eq1 = "x^2 + 10y";

		System.out.println("Test Case 4 - Equation: x^2 + 10y");
		System.out.println("Expected Output: NumberFormatException\n");
		
		try {
			ls = new LinearSolver(eq1);
			ls.solve();
		} catch (NumberFormatException nfe) {
			System.out.println("TEST CASE PASSED\n\n");
		}
		
		//TEST CASES FOR THE QUADRATIC EQUATION SOLVER
		System.out.println("TEST CASES FOR THE QUADRATIC EQUATION SOLVER");
		System.out.println("----------------------------------------------------\n");
		
		//First test case is checking a regular parabola
		eq1 = "y = x^2 + 4x + 3";
		QuadraticSolver qs = new QuadraticSolver(eq1);
		qs.solve();
		
		System.out.println("Test Case 1 - Equation: y = x^2 + 4x + 3");
		System.out.println("Expected Output: Vertex = (-2, -1), Y-Int: (0, 3), X-Ints: (-3, 0) and (-1, 0)\n");
								
		if (qs.vertex[0] == -2 && qs.vertex[1] == -1 && qs.yInt == 3 && qs.xInts[1] == -3 && qs.xInts[0] == -1) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Second test case is checking a flipped parabola
		eq1 = "y = - x^2 + x + 5";
		qs = new QuadraticSolver(eq1);
		qs.solve();
		
		System.out.println("Test Case 2 - Equation: y = -x^2 + x + 5");
		System.out.println("Expected Output: Vertex = (0.5, 5.25), Y-Int: (0, 5), X-Ints: (-1.79, 0) and (2.79, 0)\n");
						
		if (qs.vertex[0] == 0.5 && qs.vertex[1] == 5.25 && qs.yInt == 5 && (Math.round(qs.xInts[0] * 100) / 100.00 == Math.round(-1.79 * 100) / 100.00) && (Math.round(qs.xInts[1] * 100) / 100.00 == Math.round(2.79 * 100) / 100.00 )) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Third test case is checking a parabola with no x intercept
		eq1 = "y = x^2 + 10";
		qs = new QuadraticSolver(eq1);
		qs.solve();
		
		System.out.println("Test Case 3 - Equation: y = x^2 + 10");
		System.out.println("Expected Output: Vertex = (0, 10), Y-Int: (0, 10), X-Ints: None\n");
						
		if (qs.vertex[0] == 0 && qs.vertex[1] == 10 && qs.yInt == 10 && qs.xInts[0] == Double.POSITIVE_INFINITY && qs.xInts[1] == Double.POSITIVE_INFINITY) {
			System.out.println("TEST CASE PASSED\n\n");
		} else {
			System.out.println("TEST CASE FAILED");
		}
		
		//Fourth test case is checking invalid input form
		eq1 = "x^2 + 10y";

		System.out.println("Test Case 4 - Equation: x^2 + 10y");
		System.out.println("Expected Output: NumberFormatException\n");
		
		try {
			qs = new QuadraticSolver(eq1);
			qs.solve();
		} catch (NumberFormatException nfe) {
			System.out.println("TEST CASE PASSED\n");
		}
		
		System.out.println("TEST CASES CONCLUDED");
		System.out.println("----------------------------------------------------\n");

	}
}