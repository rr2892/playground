import java.util.*;
import java.io.*;

public class Cuts {
  public static void main(String[] args) {
    int[] A = {3, 3, 5};
    int C = 10;
    System.out.println(num_cuts(A, C));
  }

  public static int num_cuts(int[] A, int C) {
    int[] P = new int[A.length + 1];
    for (int i = 1; i <= A.length; i++) {
      P[i] = P[i - 1] + A[i - 1];
    }

    return num_cuts_r(P, 0, A.length - 1, C);
  }

  private static int num_cuts_r(int[] P, int i, int j, int C) {
    if (i == j || P[j + 1] - P[i] <= C) {
      return 1;
    }

    int min = Integer.MAX_VALUE;

    for (int k = i; k < j; k++) {
      int m = num_cuts_r(P, i, k, C) + num_cuts_r(P, k + 1, j, C);
      min = Math.min(min, m);
    }

    return min;
  }
}
