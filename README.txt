Compiling the Application from the Command Line

- make sure you are in the correct directory
- first incantation: javac App.java
- second incantation: java App
- this will launch my GUI, and allow you to test my application

For Testing purposes
- javac Tester.java
- java Tester

Tips for Compiling with JML annotations

- must have a working OpenJML installation from: http://www.openjml.org/downloads/
- once downloaded, make note of where the Windows-solver is saved, and where the openjml.jar is saved
- we will be performing a static check of the application
- we will be checking the three back-end files of the application
--> SystemSolver.java, LinearSolver.java, QuadraticSolver.java

- to compile with openJML from the command line: 

java -jar (path to openjml.jar) -esc -prover z3_4_3 (path to executable solver) -no-purityCheck SystemSolver.java
java -jar (path to openjml.jar) -esc -prover z3_4_3 (path to executable solver) -no-purityCheck LinearSolver.java
java -jar (path to openjml.jar) -esc -prover z3_4_3 (path to executable solver) -no-purityCheck QuadraticSolver.java

- to find the path to openjml.jar, look where you downloaded the openJML zip file. The jar 
will be in the top directory
- to find the path to z3-4.3.2.exe, look where you downloaded the openJML zip file. The jar 
will be in the directory named Solvers-windows.
