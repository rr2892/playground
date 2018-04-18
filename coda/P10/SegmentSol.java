import java.io.*;
import java.util.*;
import java.lang.*;

public class SegmentSol {
  public static int inf = 1000000000;
  public static int MAXN = 100005;
  public static int N, M;
  public static int[] segTree = new int[4 * MAXN + 4];

  public static int countZeroes(int x) {
    int ans = 0;
    while (x % 2 == 0) {
      ans++;
      x /= 2;
    }
    return ans;
    //return java.lang.Integer.bitCount((-x&x)-1);
  }

  public static void update(int v, int l, int r, int p, int val) {
    if (l == r) {
      segTree[v] = val;
      return;
    }
    int mid = (l + r) / 2;
    if (p <= mid) update(2 * v, l, mid, p, val);
    else update(2 * v + 1, mid + 1, r, p, val);
    segTree[v] = segTree[2 * v] + segTree[2 * v + 1];
    return;
  }

  public static int get(int v, int l, int r, int ll, int rr) {
    if (l == ll && r == rr) return segTree[v];
    int mid = (l + r) / 2;
    int res = 0;
    if (ll <= mid) res += get(2 * v, l, mid, ll, Math.min(rr, mid));
    if (rr > mid) res += get(2 * v + 1, mid + 1, r, Math.max(ll, mid + 1), rr);
    return res;
  }

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    InputReader in = new InputReader(inputStream);
    N = in.nextInt();
    M = in.nextInt();
    Arrays.fill(segTree, 0);
    for (int i = 0; i < N; i++) {
      int x;
      x = in.nextInt();
      update(1, 0, N - 1, i, countZeroes(x));
    }
    for (int i = 0; i < M; i++) {
      char op = in.next().charAt(0);
      int a, b;
      a = in.nextInt();
      b = in.nextInt();
      if (op == 'q') {
        int res = get(1, 0, N - 1, a - 1, b - 1);
        System.out.println(res);
      } else {
        update(1, 0, N - 1, a - 1, countZeroes(b));
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
