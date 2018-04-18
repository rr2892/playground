import java.math.*;
import java.io.*;
import java.util.*;

public class SmallestDiameterSol {

  public static ArrayList<Integer>[] g;
  public static boolean[] seen;
  public static Pair p;
  public static Pair p0 = new Pair(0, 0, 0);
  public static int inf = 1_000_000_000;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    ArrayList<Integer>[] g1 = parse(br);
    ArrayList<Integer>[] g2 = parse(br);
    Pair p1 = findMin(g1);
    Pair p2 = findMin(g2);
    int m1 = findMid(g1, p1);
    int m2 = findMid(g2, p2);
    int dist = (p1.d + 1) / 2 + (p2.d + 1) / 2 + 1;
    dist = Math.max(dist, Math.max(p1.d, p2.d));
    System.out.printf("%d\n%d %d\n", dist, m1 + 1, m2 + 1);
  }

  static int mid;

  public static int findMid(ArrayList<Integer>[] graph, Pair pair) {
    g = graph;
    seen = new boolean[graph.length];
    mid = -3;
    dfs(pair.x, pair.y, pair.d / 2);
    return mid;
  }

  public static boolean dfs(int index, int to, int distLeft) {
    if (seen[index]) return false;
    seen[index] = true;
    if (index == to) return true;

    for (int i = 0; i < g[index].size(); i++) {
      if (dfs(g[index].get(i), to, distLeft - 1)) {
        if (distLeft == 0) {
          mid = index;
        }
        return true;
      }
    }
    return false;
  }

  public static Pair findMin(ArrayList<Integer>[] graph) {
    g = graph;
    p = new Pair(-1000, -1000, -10000);
    seen = new boolean[graph.length];
    gca(0);
    return p;
  }

  public static Pair gca(int index) {
    if (seen[index]) return p0;
    seen[index] = true;
    Pair a = new Pair(index, index, 0);
    Pair b = new Pair(index, index, 0);
    for (int i = 0; i < g[index].size(); i++) {
      Pair c = gca(g[index].get(i));
      if (c == p0) continue;
      if (c.d > a.d) {
        b = a;
        a = c;
      } else if (c.d > b.d) {
        b = c;
      }
    }
    Pair c = new Pair(a.x, b.x, a.d + b.d);
    if (c.d > p.d) p = c;
    a.d++;
    b.d++;
    if (a.d > b.d) return a;
    return b;
  }

  public static ArrayList<Integer>[] parse(BufferedReader br) throws IOException {
    int n = Integer.parseInt(br.readLine());
    ArrayList<Integer>[] g = new ArrayList[n];
    for (int i = 0; i < n; i++)
      g[i] = new ArrayList<Integer>();

    for (int i = 1; i < n; i++) {
      String[] in = br.readLine().split(" ");
      int a = Integer.parseInt(in[0]) - 1;
      int b = Integer.parseInt(in[1]) - 1;
      g[a].add(b);
      g[b].add(a);
    }
    return g;
  }
}

class Pair {
  int x;
  int y;
  int d;

  public Pair(int x, int y, int d) {
    this.x = x;
    this.y = y;
    this.d = d;
  }
}
