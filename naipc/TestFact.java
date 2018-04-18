public class TestFact {
  public static void main(String[] args) {
    final long start = System.currentTimeMillis();

    int n = 10000000, k = 10000000, fact = 1;
    final int P = 1000000007;

    for (int i = n - k + 1; i < n; i++) {
      fact = modMultiply(fact, i, P);
    }

    final long end = System.currentTimeMillis() - start;
    System.out.printf("Time: %d\n", end);
  }

  private static int modMultiply(int a, int b, int P) {
    int ans = 0;
    while (b > 0) {
      if ((b & 1) > 0) ans = (ans + a) % P;
      b >>= 1;
      a = a * 2 % P;
    }
    return ans;
  }
}
