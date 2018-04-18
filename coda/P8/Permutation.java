import java.util.*;
import java.io.*;

class Node {
  int id;
  int start, end;

  Node(int id, int start, int end) {
    this.id = id;
    this.start = start;
    this.end = end;
  }
}

public class Permutation {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      Queue<Node> pq = new PriorityQueue<>((n1, n2) -> (n1.end != n2.end) ? n1.end - n2.end : n1.start - n2.start);
      int ll = Integer.MAX_VALUE, rr = Integer.MIN_VALUE;

      for (int i = 0; i < n; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken()), end = Integer.parseInt(st.nextToken());
        Node node = new Node(i, start, end);
        ll = Math.min(ll, start);
        rr = Math.max(rr, end);
        pq.offer(node);
      }

      TreeSet<Integer> set = new TreeSet<Integer>();
      for (int i = ll; i <= rr; i++) {
        set.add(i);
      }

      Map<Integer, Integer> map = new HashMap<>();

      while (!pq.isEmpty()) {
        List<Node> list = new ArrayList<>();
        Node curr = pq.poll();
        list.add(curr);

        while(!pq.isEmpty() && pq.peek().end == curr.end) {
          list.add(pq.poll());
        }

        for (Node node : list) {
          Integer l = set.higher(node.start - 1);
          if (l == null || l > node.end) {
            System.out.print("no solution\n");
            System.exit(0);
          }

          map.put(node.id, l);
          set.remove(l);
        }
      }

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < n; i++) {
        sb.append(String.format("%d ", map.get(i)));
      }

      sb.append("\n");
      System.out.print(sb.toString());

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
