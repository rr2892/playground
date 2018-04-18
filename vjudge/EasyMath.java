import java.util.*;
import java.io.*;

public class EasyMath {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      int n = Integer.parseInt(line);
      int count = 0;

      for (int l = 1; l <= n / 2; l++) {
        int rem = n - l;
        if (rem % l == 0) {
          count++;
        }
      }

      System.out.print(String.format("%d\n", count));
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
