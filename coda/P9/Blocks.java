import java.util.*;
import java.io.*;

public class Blocks {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final int P = 1000000007;

    try {
      String line = br.readLine();
      long n = Long.parseLong(line);
      int[] ways = {2, 4, 1, 1};
      long[] mat = {1, 2, 7, 15, 0};

      for (long i = 5; i <= n; i++) {
        long next = 0;
        for (int k = 0; k < 4; k++) {
          long addend = ((ways[k] % P) * (mat[k] % P)) % P;
          next = (next % P + addend % P) % P;
          mat[k] = mat[k + 1];
        }

        mat[3] = next;
      }

      long res = (n >= 5) ? mat[3] : mat[(int)(n-1)];
      System.out.print(String.format("%d\n", res));
    } catch(IOException e) {
      System.exit(0);
    }
  }
}
