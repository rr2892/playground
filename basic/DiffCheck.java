import java.util.*;
import java.io.*;

public class DiffCheck {
  public static void main(String[] args) {
    int[] A = {3, 1, 6, 9, 9, 6, 15, 234};
    // System.out.println(difFind1(A, 3));
    int[] B = {3, 1, 6, 10, 15, 234};
    System.out.println(difFind2(A, B, 249));
    System.out.println(difFind2(A, B, 17));
  }

  private static int difFind1(int[] A, int c) {
    Set<Integer> complements = new HashSet<>();
    int count = 0, n = A.length;
    Arrays.sort(A);
    for (int i = 0; i < n; i++) {
      if (complements.contains(A[i])) ++count;
      complements.add(A[i] + c);
    }
    return count;
  }

  private static boolean difFind2(int[] A, int[] B, int c) {
    Set<Integer> complements = new HashSet<>();
    for (int i = 0; i < A.length; i++) complements.add(c - A[i]);
    for (int i = 0; i < B.length; i++) {
      if (complements.contains(B[i])) return true;
    }
    return false;
  }

  private static boolean difFind3(int[] A) {
    Arrays.sort(A);
    Map<Integer, Set<Integer>> map = new HashMap<>();
    int n = A.length;
    for (int i = 0; i < n; i++) {
      map.put(i, new HashSet<Integer>());
    }

    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if (map.get(j).contains(A[i] - A[j])) {
          return true;
        }
        map.get(i).add(A[i] - A[j]);
      }
    }

    return false;
  }
}
