import java.util.*;
import java.io.*;

class Node {
  int val;
  Node(int val) {
    this.val = val;
  }
}

class Structure {
  Queue<Node> queue;
  LinkedList<Node> ll;

  public Structure() {
    queue = new LinkedList<>();
    ll = new LinkedList<>();
  }

  public int add(int n) {
    Node node = new Node(n);
    queue.offer(node);

    while (!ll.isEmpty()) {
      Node last = ll.peekLast();
      if (last.val < n) {
        ll.offerLast(node);
        return last.val;
      }

      ll.pollLast();
    }

    ll.offerLast(node);
    return -1;
  }

  public int poll() {
    int res = -1;
    if (!queue.isEmpty()) {
      Node node = queue.poll();
      res = node.val;
      if (!ll.isEmpty() && ll.peekFirst() == node) {
        ll.pollFirst();
      }
    }

    return res;
  }
}

public class LastSmaller {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      int n = Integer.parseInt(line);
      Structure structure = new Structure();
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < n; i++) {
        line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String instr = st.nextToken();

        if (instr.equals("a")) {
          sb.append(String.format("%d\n", structure.add(Integer.parseInt(st.nextToken()))));
        } else {
          sb.append(String.format("%d\n", structure.poll()));
        }
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
