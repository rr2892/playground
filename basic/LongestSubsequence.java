import java.util.*;
import java.io.*;

public class LongestSubsequence {
  public static void main(String[] args) {
    int[] t1 = {1, 2, 3, 4, 5}, t2 = {2, 1, 3, 6, 4, 2};
    System.out.println(longestSub(t2));
  }

  private static int longestSub(int[] A) {
    int[] L = new int[A.length], P = new int[A.length];
    Arrays.fill(L, 1);
    Arrays.fill(P, -1);

    for (int i = 1; i < A.length; i++) {
      for (int j = 0; j < i; j++) {
        if (A[j] < A[i]) {
          if (P[j] == -1 || (A[j] - P[j]) % 2 != (A[i] - A[j]) % 2) {
            if (L[j] + 1 > L[i] || (L[j] + 1 == L[i] && A[j] < P[i])) {
              L[i] = L[j] + 1;
              P[i] = A[j];
              System.out.println(String.format("i: %d\tj: %d\tL[i]: %d\n", i, j, L[i]));
            }
          }
        }
      }
    }

    int max = L[0];
    for (int i = 1; i < L.length; i++) {
      max = Math.max(max, L[i]);
    }

    return max;
  }
}
