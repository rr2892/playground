import java.util.*;
import java.io.*;

public class CompletePerm {
  public static void main(String[] args) {
    int[] permutation = new int[8];
    Arrays.fill(permutation, -1);
    findPermutation(permutation, 0);
    System.out.println(Arrays.toString(permutation));
  }

  private static void findPermutation(int[] permutation, int x) {
    if (x == permutation.length) {
      return;
    }

    for (int i = 0; i < permutation.length; i++) {
      if (permutation[i] == -1) {
        permutation[i] = x;
        findPermutation(permutation, x + 1);
        permutation[i] = -1;
      }
    }
  }
}
