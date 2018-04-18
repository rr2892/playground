import java.util.*;
import java.io.*;

class Node {
  int data;
  Set<Node> children;

  Node(int data) {
    this.data = data;
    children = new HashSet<>();
  }
}

class Struct {
  int min;
  int count;
  Struct(int min, int count) {
    this.min = min;
    this.count = count;
  }
}

public class SmallAncestors {
  static int count;

  public static void main(String[] args) {
    count = 0;
    Node n1 = new Node(1), n3 = new Node(3), n4 = new Node(4), n7 = new Node(7), n8 = new Node(8), n6 = new Node(6),
      n21 = new Node(2), n22 = new Node(2), n5 = new Node(5);
    n1.children.add(n3);
    n1.children.add(n4);
    n3.children.add(n7);
    n3.children.add(n8);
    n7.children.add(n6);
    n4.children.add(n21);
    n4.children.add(n22);
    n4.children.add(n5);

    System.out.println(ancestry(n1));
  }

  public static int ancestry(Node root) {
    return dfs(root).count;
  }

  private static Struct dfs(Node node) {
    if (node.children.size() == 0) {
      return new Struct(node.data, 1);
    }

    int min = Integer.MAX_VALUE, count = 0;
    for (Node child : node.children) {
      Struct struct = dfs(child);
      min = Math.min(min, struct.min);
      count += struct.count;
    }

    if (node.data < min) {
      count++;
    }

    return new Struct(Math.min(min, node.data), count);
  }
}
