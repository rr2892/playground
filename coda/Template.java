import java.util.*;
import java.io.*;

class PowerCalc {
  long[] dp;
  final long P = 1000000007;

  public PowerCalc() {
    dp = new long[100001];
    dp[0] = 1;
    dp[1] = 10;
  }

  public long power(int exp) {
    if (dp[exp] == 0) {
      dp[exp] = ((dp[exp - 1] % P) * (10 % P)) % P;
    }

    return dp[exp];
  }
}

public class Template {
  public static void main (String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final long P = 1000000007;
    PowerCalc calc = new PowerCalc();

    try {
      String line = br.readLine();
      int b = 0, i = line.length() - 1, exp = 0;

      while(line.charAt(i) != '*') {
        b += (line.charAt(i) - '0') * (int)Math.pow(10, exp++);
        i--;
      }

      // System.out.println("b: " + b);

      i -= 2;

      long a = 0;
      exp = 0;

      while (i >= 0) {
        a += ((line.charAt(i) - '0') * calc.power(exp++)) % P;
        a = a % P;
        i--;
      }

      // System.out.println("a: "+ a);

      long ans = 1;

      for (int j = 0; j < b; j++) {
        ans = ((ans % P) * (a % P)) % P;
        // System.out.println(j + ": " + ans);
      }

      System.out.print(String.format("%d\n", ans));

    } catch (IOException e) {
      System.exit(0);
    }
  }
}
