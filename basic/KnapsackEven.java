import java.util.*;
import java.io.*;

public class KnapsackEven {
  public static void main(String[] args) {
    int C = 8;
    int[] S = {1, 5, 3, 4}, V = {15, 10, 9, 5};
    System.out.print(String.format("\n%d\n", knapsack(C, S, V)));
  }

  private static int knapsack(int C, int[] S, int[] V) {
    int[][] Look = new int[C + 1][S.length + 1];
    boolean[][] Book = new boolean[C + 1][S.length + 1];

    for (int j = 1; j <= S.length; j++) {
      for (int i = 1; i <= C; i++) {
        if (i >= S[j - 1]) {
          int opt = V[j - 1] + Look[i - S[j - 1]][j - 1];
          if (opt > Look[i][j - 1]) {
            Look[i][j] = opt;
            Book[i][j] = !Book[i][j];
          } else {
            Look[i][j] = Look[i][j - 1];
          }
        } else {
          Look[i][j] = Look[i][j - 1];
        }
      }
    }

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < Look.length; i++) {
      sb.append("[ ");
      for (int j = 0; j < Look[i].length; j++) {
        sb.append(String.format("%d ", Look[i][j]));
      }
      sb.append("]\n");
    }

    System.out.print(sb.toString());

    int max = -1;
    for (int j = 1; j <= S.length; j++) {
      if (!Book[C][j]) max = Math.max(max, Look[C][j]);
    }
    return max;
  }
}
