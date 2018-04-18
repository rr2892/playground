import java.util.*;
import java.io.*;

public class CountSort {
  public static void main(String[] args) {
    int[] A = {-6, -9, 2, 8, 0, -1, -9, 15, 10, 15, 4, 7, 2, 7, 8, -5, -3, 11, 9};
    System.out.println(Arrays.toString(A));
    countSort(A);
    System.out.println(Arrays.toString(A));
  }

  private static void countSort(int[] A) {
    int n = A.length;
    int[] count = new int[8 * n + 1];
    int hash = 3 * n;
    for (int i = 0; i < n; i++) {
      count[A[i] + hash]++;
    }

    for (int i = 1; i <= 8 * n; i++) {
      count[i] += count[i-1];
    }

    int[] output = new int[n];
    for (int i = 0; i < n; i++) {
      output[count[A[i] + hash]-1] = A[i];
      count[A[i] + hash]--;
    }

    for (int i = 0; i < n; i++) {
      A[i] = output[i];
    }
  }
}
