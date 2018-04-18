import java.io.*;
import java.util.*;

public class TriangleHSetND {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] tokens = br.readLine().split(" ");
    int n = Integer.parseInt(tokens[0]), m = Integer.parseInt(tokens[1]);
    ArrayList<HashSet<Integer>> adj = new ArrayList<HashSet<Integer>>();
    int [] edges = new int[m * 2];
    for (int i = 0; i < n; i++) adj.add(new HashSet<Integer>());
    for (int i = 0; i < m; i++) {
      int x, y;
      tokens = br.readLine().split(" ");
      x = Integer.parseInt(tokens[0]) - 1;
      y = Integer.parseInt(tokens[1]) - 1;
      if (y > x) adj.get(x).add(y);
      else adj.get(y).add(x);
      edges[2*i] = x;
      edges[2*i + 1] = y;
    }
    int ans = 0;
    for (int i = 0; i < m; i++) {
      int x = edges[2*i], y = edges[2*i + 1];
      // x.degree <= y.degree, now we check each neighbor of x to see if it is y's neighbor too
      if (adj.get(x).size() > adj.get(y).size()) {
        int t = x;
        x = y;
        y = t;
      }
      for (int z : adj.get(x)) {
        if (adj.get(y).contains(z)) ans++;
      }
    }
    System.out.println(ans);
  }
}
