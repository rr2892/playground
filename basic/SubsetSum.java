import java.util.*;
import java.io.*;

public class SubsetSum {
  public static void main(String[] args) {
    int[] A = {1,2,3,3,4,5};
    int T = 7;
    System.out.println(subsetSum(A, T));
  }


  private static int subsetSum2(int[] A, int T) {
    int n = A.length;
    int[][] L = new int[T + 1][n + 1];
    L[0][0] = 1;

    for (int j = 1; j <= n; j++) {
      for (int i = 0; i <= T; i++) {
        L[i][j] = (i >= A[j - 1]) ? L[i][j - 1] + L[i - A[j - 1]][j - 1] : L[i][j - 1];
      }
    }

    return L[T][n];
  }

  private static int subsetSum(int[] A, int T) {
    int n = A.length;
    int[][] L = new int[T + 1][n + 1];
    L[0][0] = 1;

    StringBuilder[] sb = new StringBuilder[T + 1];
    for (int k = 0; k <= T; k++) {
      sb[k] = new StringBuilder();
      sb[k].append(String.format("%d\t", k));
      sb[k].append(String.format("%d\t", L[k][0]));
    }

    for (int j = 1; j <= n; j++) {
      for (int i = 0; i <= T; i++) {
        if (i >= A[j - 1]) {
          L[i][j] = L[i][j - 1] + L[i - A[j - 1]][j - 1];
        } else {
          L[i][j] = L[i][j - 1];
        }

        sb[i].append(String.format("%d\t", L[i][j]));
      }
    }

    StringBuilder res = new StringBuilder();
    res.append("L\tA\t");
    for (int k = 0; k < A.length; k++) {
      res.append(String.format("%d\t", A[k]));
    }
    res.append("\n");
    for (int k = 0; k <= T; k++) {
      res.append(String.format("%s\n", sb[k].toString()));
    }

    System.out.print(res.toString());

    return L[T][n];
  }
}
