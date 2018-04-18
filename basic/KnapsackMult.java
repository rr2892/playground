import java.util.*;
import java.io.*;

public class KnapsackMult {
  public static void main(String[] args) {
    int C = 8;
    int[] S = {1, 2, 5, 6}, V = {2, 3, 4, 5};
    System.out.print(String.format("\n%d\n", knapsack(C, S, V)));
  }

  private static int knapsack(int C, int[] S, int[] V) {
    int[][] T = new int[V.length + 1][C + 1];
    for (int i = 0; i < T.length; i++) {
      Arrays.fill(T[i], 1);
    }

    for (int i = 1; i <= V.length; i++) {
      for (int j = 1; j <= C; j++) {
        if (j - S[i - 1] < 0) {
          T[i][j] = T[i - 1][j];
        } else {
          T[i][j] = Math.max(T[i - 1][j], V[i - 1] * T[i - 1][j - S[i - 1]]);
        }
      }
    }


    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < T.length; i++) {
      sb.append("[ ");
      for (int j = 0; j < T[i].length; j++) {
        sb.append(String.format("%d ", T[i][j]));
      }
      sb.append("]\n");
    }

    System.out.print(sb.toString());

    return T[V.length][C];
  }
}
