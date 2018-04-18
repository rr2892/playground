import java.util.*;
import java.io.*;

public class QuickSort {
  public static void main(String[] args) {
    int[] A = {7, 5, 6, 3, 2, 1, 4};
    int[] B = {1, 5, 6, 2, 3, 4};
    int[] C = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 2};
    int[] D = {2, 4, 6, 3, 1, 5};

    // double sum = 0, max = 0;
    // for (int i = 0; i < 100000; i++) {
    //   int res = findXY(C);
    //   sum += res;
    //   max = Math.max(max, res);
    // }
    // System.out.println(sum / 100000.0);
    // System.out.println(max);
    findXY(C);
  }

  private static void findXY(int[] A) {
    int len = A.length;
    Random rand = new Random();
    int index = rand.nextInt(len);
    int X = A[index], count = 0;
    while (A[index] == X) {
      index = rand.nextInt(len);
    }

    System.out.println(X + "," + A[index]);
  }

  private static int partition(int l, int r, int[] A) {
    int m = (l + r) / 2;
    int p = A[m];
    swap(m, r, A);
    int i = l - 1;
    for (int j = l; j < r; j++) {
      if (A[j] <= p) {
        i++;
        swap(i, j, A);
      }
    }

    swap(i + 1, r, A);
    System.out.println(Arrays.toString(A));
    return i + 1;
  }

  private static void quicksort(int l, int r, int[] A) {
    if (l >= r) {
      return;
    }

    int q = partition(l, r, A);
    System.out.println("q: " + q);
    if (l < q - 1) {
      quicksort(l, q - 1, A);
    } else {
      System.out.println("Left part recursive call didn't run for " + l + ", " + (q-1) + ".");
    }

    if (q + 1 < r) {
      quicksort(q + 1, r, A);
      System.out.println("A right side recursive call ran!");
    }
    else {
      System.out.println("Right part recursive call didn't run for " + (q+1) + ", " + r + ".");
    }
  }

  private static void swap(int i, int j, int[] A) {
    int temp = A[i];
    A[i] = A[j];
    A[j] = temp;
  }
}
