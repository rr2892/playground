import java.util.*;
import java.io.*;

public class ChocolateBar {
  public static void main(String[] args) {
    // System.out.println(cubes(0));
    System.out.println(cubes(27));
    System.out.println(cubes(28));
  }

  private static int bar(int n) {
    int[] dp = new int[n + 1];
    dp[1] = 1;
    dp[2] = 2;

    for (int i = 0; i <= n; i++) {
      if (i > 2) {
        dp[i] += dp[i-1] + dp[i-2];
      }

      int rem = n - i;
      if (rem > 4 && rem % 2 == 0) {
        dp[i + rem/2]++;
      }
    }

    return dp[n];
  }

  private static int cubes(int n) {
    List<Integer> cubes = new ArrayList<>();
    cubes.add(0);
    while (cubes.get(cubes.size() - 1) < n) {
      cubes.add((int)Math.pow(cubes.size(), 3));
    }

    int[] dp = new int[n + 1];
    dp[0] = 1;

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j < cubes.size() && cubes.get(j) <= i; j++) {
        dp[i] += dp[i - cubes.get(j)];
        System.out.println("dp[" + (i - cubes.get(j)) + "]: " + dp[i - cubes.get(j)] + "\t dp[" + i + "]: " + dp[i]);
      }
    }

    return dp[n];
  }
}
