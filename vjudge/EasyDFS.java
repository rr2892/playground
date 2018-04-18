import java.util.*;
import java.io.*;

public class EasyDFS {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    StringBuilder sb = new StringBuilder();

    while (true) {
      int n = fr.nextInt(), m = fr.nextInt(), c = fr.nextInt() - 1;
      if (n == 0 && m == 0 && c == -1) {
        System.out.print(sb.toString());
        break;
      }

      Node[][] grid = new Node[n][m];

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          grid[i][j] = new Node();
        }
      }

      Node exit = new Node();

      for (int i = 0; i < n; i++) {
        char[] chs = fr.next().toCharArray();
        for (int j = 0; j < m; j++) {
          switch (chs[j]) {
            case 'N':
              grid[i][j].ptr = (i-1 >= 0) ? grid[i-1][j] : exit;
              break;
            case 'S':
              grid[i][j].ptr = (i+1 < n) ? grid[i+1][j] : exit;
              break;
            case 'W':
              grid[i][j].ptr = (j-1 >= 0) ? grid[i][j-1] : exit;
              break;
            case 'E':
              grid[i][j].ptr = (j+1 < m) ? grid[i][j+1] : exit;
              break;
          }
        }
      }

      Map<Integer, Integer> visited = new HashMap<>();
      Node node = grid[0][c];
      int steps = 1;

      while (node.idx != exit.idx) {
        visited.put(node.idx, steps);
        node = node.ptr;
        if (visited.containsKey(node.idx)) {
          int loop = steps - visited.get(node.idx) + 1;
          sb.append(String.format("%d step(s) before a loop of %d step(s)\n", steps-loop, loop));
          break;
        }
        steps++;
      }

      if (node.idx == exit.idx) {
        sb.append(String.format("%d step(s) to exit\n", steps-1));
      }
    }
  }
}

class Node {
  Node ptr;
  int idx;
  static int run_idx;

  static {
    run_idx = 0;
  }

  public Node() {
    this.idx = run_idx++;
  }
}

class FastReader{
  BufferedReader br;
  StringTokenizer st;

  FastReader(){
    br = new BufferedReader(new InputStreamReader(System.in));
  }

  String next(){
    while(st==null || !st.hasMoreTokens()){
      try{
        st = new StringTokenizer(br.readLine());
      }catch(IOException e){
        e.printStackTrace();
      }
    }
    return st.nextToken();
  }

  int nextInt(){
    return Integer.parseInt(next());
  }

  long nextLong(){
    return Long.parseLong(next());
  }

  double nextDouble(){
    return Double.parseDouble(next());
  }

  String nextLine(){
    String str = "";
    try{
      str = br.readLine();
    }catch(IOException e){
      e.printStackTrace();
    }
    return str;
  }
}
