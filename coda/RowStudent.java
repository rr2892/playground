import java.util.*;
import java.io.*;

class LLNode {
  LLNode next, previous;
  int id;

  public LLNode (int id) {
    this.id = id;
  }
}

class Row {

  LLNode head, tail;
  Map<Integer, LLNode> map;

  public Row(int n) {
    map = new HashMap<>();

    for (int i = 1; i <= n; i++) {
      LLNode node = new LLNode(i);
      map.put(i, node);
      addToLinkedList(node);
    }
  }

  public int left(int num) {
    if (!map.containsKey(num)) return -1;
    LLNode node = map.get(num);
    if (node.previous == null) return -1;
    return node.previous.id;
  }

  public int right(int num) {
    if (!map.containsKey(num)) return -1;
    LLNode node = map.get(num);
    if (node.next == null) return -1;
    return node.next.id;
  }

  public void moveLeft(int num) {
    if (!map.containsKey(num) || head == tail) return;
    LLNode node = map.get(num);
    removeFromLinkedList(node);
    insertBeforeHead(node);
  }

  public void moveRight(int num) {
    if (!map.containsKey(num) || head == tail) return;
    LLNode node = map.get(num);
    removeFromLinkedList(node);
    insertAfterTail(node);
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

  private void insertBeforeHead(LLNode node) {
      node.previous = null;
      node.next = head;
      head.previous = node;
      head = node;
  }

  private void insertAfterTail(LLNode node) {
    node.previous = tail;
    node.next = null;
    tail.next = node;
    tail = node;
  }
}

public class RowStudent {
  public static void main (String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      int n = Integer.parseInt(st.nextToken()), q = Integer.parseInt(st.nextToken());
      Row row = new Row(n);
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < q; i++) {
        line = br.readLine();
        st = new StringTokenizer(line);
        String instr = st.nextToken();
        int num = Integer.parseInt(st.nextToken());

        switch (instr) {
          case "l":
            sb.append(String.format("%d\n", row.left(num)));
            break;
          case "r":
            sb.append(String.format("%d\n", row.right(num)));
            break;
          case "ml":
            row.moveLeft(num);
            break;
          case "mr":
            row.moveRight(num);
            break;
        }
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
