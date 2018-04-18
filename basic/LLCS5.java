import java.util.*;
import java.io.*;

public class LLCS5 {
  public static void main(String[] args) {
    String A = "abccb", B = "babbc";
    System.out.print(String.format("%d\n", LLCSW(A, B)));
  }

  public static int LLCSW(String A, String B) {
    int[][] Look = new int[A.length() + 1][B.length() + 1];
    // for (int i = 0; i <= A.length(); i++) {
    //   Arrays.fill(Look[i], -1);
    // }

    int res = LLCS(A.length(), B.length(), A, B, Look);

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < Look.length; i++) {
      sb.append("$[ ");
      for (int j = 0; j < Look[i].length; j++) {
        sb.append(String.format("%d, ", Look[i][j]));
      }
      sb.append("]$ \\\\\n");
    }

    System.out.print(sb.toString());

    return res;
  }

  private static int LLCS(int i, int j, String A, String B, int[][] Look) {
    if (Look[i][j] != 0) {
      return Look[i][j];
    }

    if (i == 0 || j == 0) {
      Look[i][j] = 0;
    } else {
      if (A.charAt(i - 1) == B.charAt(j - 1)) {
        Look[i][j] = 1 + LLCS(i - 1, j - 1, A, B, Look);
      } else {
        Look[i][j] = Math.max(LLCS(i - 1, j, A, B, Look), LLCS(i, j - 1, A, B, Look));
      }
    }

    return Look[i][j];
  }
}
