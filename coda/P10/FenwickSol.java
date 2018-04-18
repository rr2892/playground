import java.io.*;
import java.util.*;
import java.lang.*;

public class FenwickSol {
  public static int inf = 1000000000;
  public static int MAXN = 100005;
  public static int N, M;
  public static int[] s = new int[MAXN];

  public static int countZeroes(int x) {
    return java.lang.Integer.bitCount((-x&x)-1);
  }

  public static void add(int k, int v) {
    k++;
    for (; k < MAXN; k += (k & -k)) s[k] += v;
    return;
  }

  public static int get(int k) {
    if (k < 0) return 0;
    k++;
    int ans = 0;
    for (; k > 0; k -= (k & -k)) ans += s[k];
    return ans;
  }

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    InputReader in = new InputReader(inputStream);
    N = in.nextInt();
    M = in.nextInt();
    Arrays.fill(s, 0);
    for (int i = 1; i <= N; i++) {
      int x;
      x = in.nextInt();
      add(i, countZeroes(x));
    }
    for (int i = 0; i < M; i++) {
      char op = in.next().charAt(0);
      int a, b;
      a = in.nextInt();
      b = in.nextInt();
      if (op == 'q') {
        int res = get(b) - get(a - 1);
        System.out.println(res);
      } else {
        int v = get(a) - get(a - 1);
        int nv = countZeroes(b);
        add(a, nv - v);
      }
    }
  }

  static class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
      reader = new BufferedReader(new InputStreamReader(stream), 32768);
      tokenizer = null;
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
