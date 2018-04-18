import java.util.*;
import java.io.*;

class Node {
  String name;
  int age;
  Set<Node> children;
  Node parent;
  String youngest, eldest;

  public Node(String name, int age) {
    this.name = name;
    this.age = age;
    children = new HashSet<>();
    parent = null;
    youngest = name;
    eldest = name;
  }
}

class Trie {
  Map<String, Node> map;

  public Trie() {
    map = new HashMap<>();
  }

  public void insert(Node node) {
    map.put(node.name, node);
  }

  public String union(String name1, String name2) {
    Node node1 = map.get(name1), node2 = map.get(name2);
    if (node1.parent != null) node1 = node1.parent;
    if (node2.parent != null) node2 = node2.parent;

    if (node1 != node2)

    if (node2.children.size() > node1.children.size()) {
      Node temp = node1;
      node1 = node2;
      node2 = temp;
    }

    Node y1 = map.get(node1.youngest), y2 = map.get(node2.youngest), e1 = map.get(node1.eldest), e2 = map.get(node2.eldest);
    if (y2.age < y1.age) {
      node1.youngest = node2.youngest;
    } else if (y2.age == y1.age && node2.youngest.compareTo(node1.youngest) < 0) {
      node1.youngest = node2.youngest;
    }

    if (e2.age > e1.age) {
      node1.eldest = node2.eldest;
    } else if (e2.age == e1.age && node2.eldest.compareTo(node1.eldest) < 0) {
      node1.eldest = node2.eldest;
    }

    for (Node child : node2.children) {
      child.parent = node1;
      node1.children.add(child);
    }

    node2.children = new HashSet<>();
    node2.parent = node1;
    node1.children.add(node2);

    return String.format("%s %s\n", node1.youngest, node1.eldest);
  }
}

public class Friends {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      Trie trie = new Trie();

      for (int i = 0; i < n; i++) {
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String name = st.nextToken();
        int age = Integer.parseInt(st.nextToken());
        Node node = new Node(name, age);
        trie.insert(node);
      }

      int q = Integer.parseInt(br.readLine());
      StringBuilder sb = new StringBuilder();
      // sb.append("\n");

      for (int i = 0; i < q; i++) {
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String name1 = st.nextToken(), name2 = st.nextToken();
        sb.append(trie.union(name1, name2));
      }

      System.out.print(sb.toString());

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
