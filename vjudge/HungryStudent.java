import java.util.*;
import java.io.*;

public class HungryStudent {
  public static void fill(boolean[] dp, int count) {
    if (count > 100) return;
    dp[count] = true;
    fill(dp, count + 3);
    fill(dp, count + 7);
  }

  public static void main(String[] args) {
    boolean[] dp = new boolean[101];
    fill(dp, 0);

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    try {
      int n = Integer.parseInt(br.readLine());
      for (int i = 0; i < n; i++) {
        int k = Integer.parseInt(br.readLine());
        if (dp[k]) {
          sb.append("YES\n");
        } else {
          sb.append("NO\n");
        }
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }

}
