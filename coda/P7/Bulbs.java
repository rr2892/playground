import java.util.*;
import java.io.*;

public class Bulbs {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
      int grid = 0;

      for (int i = 0; i < n; i++) {
        line = br.readLine();
        for (int j = 0; j < m; j++) {
          if (line.charAt(j) == '*') {
            grid += 1;
          }
          grid = grid << 1;
        }
      }

      print(grid, n*m);

      System.out.print(String.format("%d\n", minPresses(grid, n, m, new HashMap<Integer, Set<Integer>>())));
    } catch(IOException e) {
      System.exit(0);
    }
  }

  private static int minPresses(int grid, int n, int m, Map<Integer, Set<Integer>> used) {
    if (grid == 0) {
      return 0;
    }

    int min = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (used.containsKey(i) && used.get(i).contains(j)) continue;
        int res = doSwitch(grid, i, j, n, m);
        if (!used.containsKey(i)) used.put(i, new HashSet<Integer>());
        used.get(i).add(j);
        min = Math.min(min, 1 + minPresses(res, n, m, used));
        used.get(i).remove(j);
      }
    }

    return min;
  }

  private static int doSwitch(int grid, int i, int j, int n, int m) {
    int res = grid;

    int k = n * m - (i * m + (j + 1));
    int mask = 1 << k;
    res = ((grid & mask) > 0) ? res & ~mask : res | mask;

    int kd = k - m;
    if (kd >= 0) {
      mask = 1 << kd;
      res = ((grid & mask) > 0) ? res & ~mask : res | mask;
    }

    int ku = k + m;
    if (ku < n * m) {
      mask = 1 << ku;
      res = ((grid & mask) > 0) ? res & ~mask : res | mask;
    }

    int kl = k + 1;
    if (kl % m != 0) {
      mask = 1 << kl;
      res = ((grid & mask) > 0) ? res & ~mask : res | mask;
    }

    int kr = k - 1;
    if (k % m != 0) {
      mask = 1 << kr;
      res = ((grid & mask) > 0) ? res & ~mask : res | mask;
    }



    return res;
  }

  private static void print(int grid, int lim) {
    String s = "";
    for (int i = 0; i < lim; i++) {
     if ((grid & 1) > 0) {
       s = "1" + s;
     } else {
       s = "0" + s;
     }
     grid = grid >> 1;
    }

    System.out.print(String.format("%s\n", s));
  }
}
