package gui.NumericalMethods;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Jacobi {

  public static final int MAX_ITERATIONS = 100;
  private double[][] M;
  
  public Jacobi(double [][] matrix) { M = matrix; }
  public Jacobi() { }
  

  public String print(){
	StringBuilder builder = new StringBuilder();
	int n = M.length;
	for (int i = 0; i < n; i++) {
		builder.append("x"+(i+1)+" : ");
		for (int j = 0; j < n + 1; j++) {
		  if(j == n) 
			  builder.append(" = ");
		  
		  builder.append(M[i][j] + " ");
	  }
	  builder.append("\n");
	}
	return builder.toString();
  }

  public boolean transformToDominant(int r, boolean[] V, int[] R)
  {
    int n = M.length;
    if (r == M.length) {
      double[][] T = new double[n][n+1];
      for (int i = 0; i < R.length; i++) {
        for (int j = 0; j < n + 1; j++)
          T[i][j] = M[R[i]][j];
      }

      M = T;
      
      return true;
    }

    for (int i = 0; i < n; i++) {
      if (V[i]) continue;

      double sum = 0;
      
      for (int j = 0; j < n; j++)
        sum += Math.abs(M[i][j]);

      if (2 * Math.abs(M[i][r]) > sum) { // diagonally dominant?
        V[i] = true;
        R[r] = i;

        if (transformToDominant(r + 1, V, R))
          return true;

        V[i] = false;
      }
    }

    return false;
  }
  
  
  /**
   * Returns true if is possible to transform M(data member) to a diagonally
   * dominant matrix, false otherwise.
  */
  public boolean makeDominant()
  {
    boolean[] visited = new boolean[M.length];
    int[] rows = new int[M.length];

    Arrays.fill(visited, false);
    
    return transformToDominant(0, visited, rows);
  }



  
  public String solve()
  {
    StringBuilder builder = new StringBuilder();
    int iterations = 0;
    int n = M.length;
    double epsilon = 1e-15;
    double[] X = new double[n]; // Approximations
    double[] P = new double[n]; // Prev
    Arrays.fill(X, 0);
    Arrays.fill(P, 0);

    while (true) {
      for (int i = 0; i < n; i++) {
        double sum = M[i][n]; // b_n

        for (int j = 0; j < n; j++)
          if (j != i)
            sum -= M[i][j] * P[j];

        // Update x_i but it's no used in the next row calculation
        // but up to de next iteration of the method
        X[i] = 1/M[i][i] * sum;
      }

      builder.append("X_" + iterations + " = {");
      for (int i = 0; i < n; i++)
    	  builder.append(X[i] + " ");
      builder.append("}"+"\n");

      iterations++;
      if (iterations == 1) continue;

      boolean stop = true;
      for (int i = 0; i < n && stop; i++)
        if (Math.abs(X[i] - P[i]) > epsilon)
          stop = false;

      if (stop || iterations == MAX_ITERATIONS) break;
      P = (double[])X.clone();
    }
    return builder.toString();
  }

  public String jacobiSolver(double M[][],int n) {
    StringBuilder builder = new StringBuilder();
    Jacobi jacobi = new Jacobi(M);
    PrintWriter writer = new PrintWriter(System.out, true);
    if (!jacobi.makeDominant()) {
    	builder.append("The system isn't diagonally dominant: " + 
                     "The method cannot guarantee convergence.\n");
    }

    writer.println();
    builder.append(jacobi.print()).append(jacobi.solve());
    
    return builder.toString();
  }
  
}


