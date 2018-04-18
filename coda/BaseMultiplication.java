import java.util.*;
import java.io.*;

class PowerDPCalc {
  int b;
  long[] dp;
  final static long P = (long)Math.pow(10, 9) + 7;

  PowerDPCalc(int b, int len) {
    this.b = b;
    dp = new long[len + 1];
    dp[0] = 1;
  }

  long power(int exp) {
    if (dp[exp] == 0) {
      dp[exp] = dp[exp - 1] * b % P;
    }
    return dp[exp];
  }
}

public class BaseMultiplication {
  final static long P = (long)Math.pow(10, 9) + 7;

  public static void main(String[] args) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int b = Integer.parseInt(br.readLine().trim());
      String s1 = br.readLine(), s2 = br.readLine();
      int len = Math.max(s1.length(), s2.length());
      PowerDPCalc myCalc = new PowerDPCalc(b, len);

      long n1 = convertNum(b, s1, myCalc);
      long n2 = convertNum(b, s2, myCalc);
      long ans = (n1 % P) * (n2 % P) % P;

      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%d\n", ans));
      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }

  private static long convertNum(int b, String s, PowerDPCalc myCalc) {
    int mult = 0;
    long n = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      int digit = convert(s.charAt(i));
      long addend = (myCalc.power(mult) % P) * (digit) % P;
      n = (n % P + addend % P) % P;
      mult++;
    }

    return n;
  }

  private static int convert(char c) {
    if (c >= 'a' && c <= 'z') {
      return (int)c - 87;
    }

    if (c >= 'A' && c <= 'Z') {
      return (int)c - 29;
    }

    return (int)c - 48;
  }

}
