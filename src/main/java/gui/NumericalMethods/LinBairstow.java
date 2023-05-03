package gui.NumericalMethods;
import java.io.*;

public class LinBairstow {
  private double[] a;
  private double[] b;
  private double[] c;
  private int n;

  public LinBairstow()
  {
    a = new double[20];
    b = new double[20];
    c = new double[20];
  }

  public void readInput() throws IOException
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    n = Integer.parseInt(reader.readLine());

    String[] tokens = reader.readLine().split(" ");

    for (int i = 0; i < n + 1; i++)
      a[i] = Double.parseDouble(tokens[i]);
  }

  public void solve()
  {
    int i, j;
    double r1, r2, du, dv, u, v, r, dr;
    double sq, det, nu, nv, error;
    double epsilon = 1e-8;

    System.out.println("The polynomial is:");
    for (i = n; i >= 0; i--) {
      if (i == 0)
        System.out.print(a[i]);
      else
        System.out.print(a[i] + "*x^" + i + "  ");
    }

    System.out.print("\n\n");
    while (n > 3) {
      u = 0;
      v = 0;
      error = 1;
      c[n] = b[n] = a[n];

      while (error > epsilon) {
        b[n-1] = a[n-1] + u * b[n];
        c[n-1] = b[n-1] + u * c[n];

        for (i = n - 2; i > 0; i--) {
          b[i] = a[i] + u * b[i+1] + v * b[i+2];
          c[i] = b[i] + u * c[i+1] + v * c[i+2];
        }

        b[0] = a[0] + u * b[1] + v * b[2];

        det = (c[2] * c[2]) - c[1] * c[3];

        nu = b[0] * c[3] - b[1] * c[2];
        nv = b[1] * c[1] - b[0] * c[2];
        
        if (det == 0) {
          du = dv = 1;
        } else {
          du = nu / det;
          dv = nv / det;
        }
        
        u += du;
        v += dv;

        error = Math.sqrt(du * du + dv * dv);
      }


      sq = u * u + 4 * v;

      if (sq < 0) {
        r1 = u/2;
        r2 = Math.sqrt(-sq)/2;

        System.out.println(r1 + " + " + r2 + "i");
        System.out.println(r1 + " - " + r2 + "i");
      } else {
        r1 = u/2 + Math.sqrt(sq)/2;
        r2 = u/2 - Math.sqrt(sq)/2;

        System.out.println(r1);
        System.out.println(r2);
      }

      n -= 2;

      for (i = 0; i < n + 1; i++)
        a[i] = b[i+2];
    }


    // When n = 3 we need to implement Horner's method to reduce the degree
    // by one. Horner's method uses the Newton-Raphson method to find r.
    //
    // f(x) = a[0] + a[1] * x + a[2] * x^2 + ... + a[n] * x^n
    //      = (x - r)(b[1] + b[2] * x + ... + b[n] * x^(n-1)) + b[0]
    //      = b[0] - r * b[1] + x(b[1] - r*b[2]) + x^2(b[2] - r * b[3]) + 
    //         ... + x^(n-1)(b[n-1] - r * b[n]) + b[n] * x^n
    //
    //    Generalizing:
    //    b[n] = a[n]
    //    b[i] = a[i] + r * b[i+1]   for i = n - 1, n - 2, ..., 1, 0
    //
    //    b[0] = f(r)
    //
    //    When n = 3:
    //      b[3] = a[3]
    //      b[2] = a[2] + r * b[3]
    //      b[1] = a[1] + r * b[2]
    //      b[0] = a[0] + r * b[1]
    //
    //  Source: http://faculty.cbu.ca/dkeefe/chem302/Chapter%201_WS_part2.pdf

    
    
    
    if (n == 3) {
      r = 0;
      error = 1;
      b[n] = a[n];

      while (error > epsilon) {
        b[2] = a[2] + r * b[3];
        b[1] = a[1] + r * b[2];
        b[0] = a[0] + r * b[1];

        double d = a[1] + (2 * a[2] * r) + (3 * a[3] * r * r);

        if (d == 0)
          dr = 1;
        else
          dr = -b[0] / d; // b[0] = f(x)

        r -= dr;
        error = Math.abs(dr);
      }

      System.out.println(r);
      n--;

      for (i = 0; i < n + 1; i++)
        a[i] = b[i + 1];
    }


    if (n == 2) {
      u = -a[1] / a[2];
      v = -a[0] / a[2];
      sq = u * u + 4 * v;

      if (sq < 0) {
        r1 = u/2;
        r2 = Math.sqrt(-sq)/2;

        System.out.println(r1 + " + " + r2 + "i");
        System.out.println(r1 + " - " + r2 + "i");
      } else {
        r1 = u/2 + Math.sqrt(sq)/2;
        r2 = u/2 - Math.sqrt(sq)/2;

        System.out.println(r1);
        System.out.println(r2);
      }
    } else if (n == 1) {
      System.out.println(-a[0] / a[1]);
    }

    System.out.println("\nRoot finding process has finished.");

  }

  public static void main(String[] args) throws IOException
  {
    LinBairstow linBairstow = new LinBairstow();

    linBairstow.readInput();
    linBairstow.solve();
  }
}

