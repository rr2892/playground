import java.util.*;
import java.io.*;

public class RadixSort {
  public static void main(String[] args) {
    int[] A = {-6, -9, 2, 8, 0, -1, -9, 15, 10, 15, 4, 7, 2, 7, 8, -5, -3, 11, 9, 1240, 123, 23, -120, -20, -1351};
    System.out.println(Arrays.toString(A));
    radixSort(A);
    System.out.println(Arrays.toString(A));
  }

  private static void countSortNegs(int[] A) {
    int n = A.length;
    int[] count = new int[2], output = new int[n];

    for (int i = 0; i < n; i++) if (A[i] < 0) count[0]++;
    count[1] = count[0]--;

    for (int i = 0; i < n; i++) {
      output[(A[i] < 0)? count[0]-- : count[1]++] = A[i];
    }

    for (int i = 0; i < n; i++) {
      A[i] = output[i];
    }
  }

  private static void countSort(int[] A, int exp) {
    int n = A.length;
    int[] count = new int[10];

    for (int i = 0; i < n; i++) {
      count[Math.abs(A[i] / exp) % 10]++;
    }

    for (int i = 1; i < count.length; i++) {
      count[i] += count[i-1];
    }

    int[] output = new int[n];
    for (int i = n - 1; i >= 0; i--) {
      output[count[Math.abs(A[i] / exp) % 10] - 1] = A[i];
      count[Math.abs(A[i] / exp) % 10]--;
    }

    for (int i = 0; i < n; i++) {
      A[i] = output[i];
    }
  }

  private static void radixSort(int[] A) {
    int max = maxOf(A);
    int lim = (int)Math.pow(A.length, 3);
    for (int exp = 1; max / exp > 0; exp *= 10) {
      countSort(A, exp);
    }
    countSortNegs(A);
  }

  private static int maxOf(int[] A) {
    int max = A[0];
    for (int i = 1; i < A.length; i++) max = Math.max(max, A[i]);
    return max;
  }
}
