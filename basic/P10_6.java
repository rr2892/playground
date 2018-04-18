import java.util.*;
import java.io.*;

public class P10_6 {
  public static void main(String[] args) {
    int[] A = {3, 1, 4, 1, 5, 9, 2, 6};
    System.out.println(Arrays.toString(countLess(A)));
  }

  static int[] countLess(int[] A) {
    int n = A.length;
    int[] B = new int[n];
    TreeSet<Integer> bbst = new TreeSet<>();
    for (int i = 0; i < n; i++) {
      B[i] = bbst.headSet(A[i]).size();
      bbst.add(A[i]);
    }
    return B;
  }

  int countPathsTo(int v, List<Set<Integer>> adj) {
    int n = adj.size();
    Set<Integer> exists = new HashSet<Integer>();
    for (int i = 0; i < adj.size(); i++) {
      if (i == v) continue;
      Queue<Integer> q = new LinkedList<>();
      Set<Integer> visited = new HashSet<>();
      q.offer(i);

      while (!q.isEmpty()) {
        int j = q.poll();
        if (adj.get(j).contains(v) || exists.contains(j)) {
          exists.add(i);
          break;
        }

        visited.add(j);
        for (int e : adj.get(j)) {
          if (!visited.contains(e)) {
            q.offer(e);
          }
        }
      }
    }

    return exists.size();
  }
}
