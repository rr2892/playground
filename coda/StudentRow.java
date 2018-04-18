import java.util.*;
import java.io.*;

class LLNode {
  LLNode next, previous;
  String name;

  public LLNode (String name) {
    this.name = name;
  }
}

class Row {

  LLNode head, tail;
  Map<String, LLNode> map;

  public Row(List<String> names) {
    map = new HashMap<>();

    for (String name : names) {
      LLNode node = new LLNode(name);
      map.put(name, node);
      addToLinkedList(node);
    }
  }

  public String left(String name) {
    LLNode node = map.get(name);
    if (node.previous == null) return "-1";
    return node.previous.name;
  }

  public String right(String name) {
    LLNode node = map.get(name);
    if (node.next == null) return "-1";
    return node.next.name;
  }

  public void exchange(String name1, String name2) {
    LLNode n1 = map.get(name1), n2 = map.get(name2);

    if (n1.next == n2) {
      removeFromLinkedList(n2);
      insertInLinkedListAfterNode(n2, n1.previous);
    } else if (n2.next == n1) {
      removeFromLinkedList(n1);
      insertInLinkedListAfterNode(n1, n2.previous);
    } else {
      LLNode n1prev = n1.previous, n2prev = n2.previous;
      // if (n1prev == null) System.out.println("n1prev is null");
      // if (n2prev == null) System.out.println("n2prev is null");
      removeFromLinkedList(n2);
      removeFromLinkedList(n1);
      insertInLinkedListAfterNode(n1, n2prev);
      insertInLinkedListAfterNode(n2, n1prev);
    }

    // printList();
  }

  private void printList() {
    LLNode node = head;
    System.out.println();
    int count = 0;
    while (node != null && count++ < 10) {
      System.out.print(node.name + " -> ");
      node = node.next;
    }
    System.out.println();
  }

  public void exit (String name) {
    if (map.containsKey(name)) {
      LLNode node = map.get(name);
      map.remove(name);
      removeFromLinkedList(node);
    }
  }

  private void addToLinkedList(LLNode node) {
    if (head == null) {
      head = node;
      tail = node;
    } else {
      node.previous = tail;
      tail.next = node;
      tail = node;
    }
  }

  private void insertInLinkedListAfterNode(LLNode node, LLNode beforeNode) {
    if (beforeNode == null) {
      node.next = head;
      node.previous = null;
      head.previous = node;
      head = node;
      return;
    }

    if (beforeNode.next != null) {
      beforeNode.next.previous = node;
      node.next = beforeNode.next;
    }

    beforeNode.next = node;
    node.previous = beforeNode;

    if (beforeNode == tail) {
      tail = node;
      tail.next = null;
    }
  }

  private void removeFromLinkedList(LLNode node) {
    // System.out.println("Head: " + head.name);
    if (node == head) {
      head = head.next;
    }

    if (node == tail) {
      tail = tail.previous;
    }

    if (node.next != null) {
      node.next.previous = node.previous;
    }

    if (node.previous != null) {
      node.previous.next = node.next;
    }
  }

}

public class StudentRow {
  public static void main (String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      int n = Integer.parseInt(br.readLine());
      List<String> names = new ArrayList<>();
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);

      for (int i = 0; i < n; i++) {
        names.add(st.nextToken());
      }

      Row row = new Row(names);
      StringBuilder sb = new StringBuilder();

      int q = Integer.parseInt(br.readLine());
      for (int i = 0; i < q; i++) {
        line = br.readLine();
        st = new StringTokenizer(line);
        String instr = st.nextToken(), name = st.nextToken();

        switch (instr) {
          case "r":
            sb.append(String.format("%s\n", row.right(name)));
            break;
          case "l":
            sb.append(String.format("%s\n", row.left(name)));
            break;
          case "x":
            row.exchange(name, st.nextToken());
            break;
          case "e":
            row.exit(name);
            break;
        }
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
