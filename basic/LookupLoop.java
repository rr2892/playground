import java.util.*;
import java.io.*;

public class LookupLoop {
  public static void main(String[] args) {
    int[] A = {10, 1, 11, 2, 3, 7, 5, 8};
    int n = A.length;
    Integer[] lookup = new Integer[n];
    Arrays.fill(lookup, 0);
    for (int i = n - 1; i >= 0; i--) {
      lookup[i] = 1;
      for (int j = i + 1; j < n; j++) {
        System.out.println("i: " + i + "\tj: " + j);
        if (A[j] > A[i]) {
          lookup[i] = Math.max(lookup[i], 1 + lookup[j]);
        }
      }

      System.out.println(Arrays.toString(lookup));
    }
  }
}
