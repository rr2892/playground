import java.util.*;
import java.io.*;

public class P10_4 {
  public static void main(String[] args) {
    int[] A = {10, 2, 6, 8, 5, 4};
    System.out.println(Arrays.toString(minDiffs(A)));
  }

  private static int[] minDiffs(int[] A) {
    TreeSet<Integer> bbst = new TreeSet<>();
    bbst.add(A[0]);
    int[] res = new int[A.length - 1];
    res[0] = Math.abs(A[1] - A[0]);

    for (int i = 2; i < A.length; i++) {
      Integer lower = bbst.lower(A[i]), higher = bbst.higher(A[i]);
      System.out.println(i + "\t" + lower + "\t" + higher);
      if (lower == null && higher == null) continue;
      if (lower != null && higher != null) {
        res[i-1] = Math.min(res[i-2], Math.min(Math.abs(A[i] - higher), Math.abs(A[i] - lower)));
      } else if (lower == null) {
        res[i-1] = Math.min(res[i-2], Math.abs(A[i] - higher));
      } else {
        res[i-1] = Math.min(res[i-2], Math.abs(A[i] - lower));
      }
      bbst.add(A[i]);
    }

    return res;
  }
}
