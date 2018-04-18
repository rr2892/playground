import java.util.*;
import java.io.*;

class Tree {
  Map<Integer, Set<Integer>> map;

  public Tree(int n) {
    map = new HashMap<>();
    for (int i = 1; i <= n; i++) {
      map.put(i, new HashSet<>());
    }
  }

  public void addEdge(int i1, int i2) {
    map.get(i1).add(i2);
    map.get(i2).add(i1);
  }

  public Stack<Integer> dfs(int i) {
    return dfs_r(i, new HashSet<Integer>(), new Stack<Integer>());
  }

  private Stack<Integer> dfs_r(int i, Set<Integer> visited, Stack<Integer> stack) {
    Set<Integer> edges = map.get(i);
    Stack<Integer> copy = (Stack<Integer>)stack.clone();
    for (int k : edges) {
      if (visited.contains(k)) continue;
      visited.add(k);
      Stack<Integer> temp = (Stack<Integer>)copy.clone();
      temp.push(k);
      Stack<Integer> res = dfs_r(k, visited, temp);
      if (res.size() > stack.size()) {
        stack = res;
      }
    }

    return stack;
  }
}

public class DiameterJoin {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n1 = Integer.parseInt(br.readLine());
      Tree t1 = new Tree(n1);

      for (int i = 0; i < n1 - 1; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i1 = Integer.parseInt(st.nextToken()), i2 = Integer.parseInt(st.nextToken());
        t1.addEdge(i1, i2);
      }

      int n2 = Integer.parseInt(br.readLine());
      Tree t2 = new Tree(n2);

      for (int i = 0; i < n2 - 1; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i1 = Integer.parseInt(st.nextToken()), i2 = Integer.parseInt(st.nextToken());
        t2.addEdge(i1, i2);
      }

      Stack<Integer> y1 = t1.dfs(1), y2 = t2.dfs(2);
      Stack<Integer> d1 = t1.dfs(y1.pop()), d2 = t2.dfs(y2.pop());

      int pop1 = d1.size() / 2, pop2 = d2.size() / 2;
      int addend = d1.size() % 2 + d2.size() % 2;

      for (int i = 0; i < pop1; i++) d1.pop();
      for (int i = 0; i < pop2; i++) d2.pop();

      int node1 = d1.pop(), node2 = d2.pop();
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%d\n", pop1 + 1 + pop2 + addend));
      sb.append(String.format("%d %d\n", node1, node2));
      System.out.print(sb.toString());


    } catch (IOException e) {
      System.exit(0);
    }
  }

}
