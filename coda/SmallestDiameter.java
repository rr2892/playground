import java.util.*;
import java.io.*;

class Node {
  int id;
  Set<Node> children;
  Node left, right;
  int leftDist, rightDist;
  // int minDist;

  public Node(int id) {
    this.id = id;
    children = new HashSet<>();
    leftDist = 0;
    rightDist = 0;
    // minDist = 0;
  }
}

public class SmallestDiameter {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n1 = Integer.parseInt(br.readLine());
      Map<Integer, Node> map1 = new HashMap<>();

      for (int i = 1; i <= n1; i++) {
        map1.put(i, new Node(i));
      }

      Node root1 = null, root2 = null;

      System.out.println("\nTree 1");
      for (int i = 0; i < n1 - 1; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i1 = Integer.parseInt(st.nextToken()), i2 = Integer.parseInt(st.nextToken());
        if (root1 == null) root1 = map1.get(i1);

        map1.get(i2).children.add(map1.get(i1));
        map1.get(i1).children.add(map1.get(i2));

        System.out.println("Node " + i1 + " has child " + i2);
      }

      int n2 = Integer.parseInt(br.readLine());
      Map<Integer, Node> map2 = new HashMap<>();

      for (int i = 1; i <= n2; i++) {
        map2.put(i, new Node(i));
      }

      System.out.println("Tree 2");
      for (int i = 0; i < n2 - 1; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i1 = Integer.parseInt(st.nextToken()), i2 = Integer.parseInt(st.nextToken());
        if (root2 == null) root2 = map2.get(i1);

        map2.get(i2).children.add(map2.get(i1));
        map2.get(i1).children.add(map2.get(i2));

        System.out.println("Node " + i1 + " has child " + i2);
      }

      leafDistance(root1);
      leafDistance(root2);
      Node p1 = findParent(root1), p2 = findParent(root2);

      int p1r = p1.rightDist;
      while (p1.leftDist > p1r) {
        p1 = p1.left;
        p1r++;
      }

      int p2r = p2.rightDist;
      while (p2.leftDist > p2r) {
        p2 = p2.left;
        p2r++;
      }

      System.out.println("\np1: " + p1.id + "\np1.leftDist: " + p1.leftDist + "\np1.rightDist: " + p1.rightDist);
      System.out.println("\np2: " + p2.id + "\np2.leftDist: " + p2.leftDist + "\np2.rightDist: " + p2.rightDist);

      int diameter = p1.leftDist + 1 + p2.leftDist;

      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%d\n%d %d\n", diameter, p1.id, p2.id));
      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }

  private static int leafDistance(Node root) {
    if (root == null) {
      return -1;
    }

    if (root.children.isEmpty()) {
      return 0;
    }

    // root.minDist = Integer.MAX_VALUE;

    for (Node child : root.children) {
      child.children.remove(root);
      int childDist = 1 + leafDistance(child);
      if (childDist > root.leftDist) {
        root.rightDist = root.leftDist;
        root.leftDist = childDist;
        if (root.left != null) {
          root.right = root.left;
        }

        root.left = child;
      } else if (childDist > root.rightDist) {
        root.rightDist = childDist;
        root.right = child;
      }

      // root.minDist = Math.min(root.minDist, childDist);
    }

    return Math.max(root.leftDist, root.rightDist);
  }

  private static Node findParent(Node root) {
    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);
    Node maxNode = root;

    while(!queue.isEmpty()) {
      Node node = queue.poll();
      if (node.leftDist + node.rightDist > maxNode.leftDist + maxNode.rightDist) {
        maxNode = node;
      }

      for (Node child : node.children) {
        queue.offer(child);
      }
    }

    return maxNode;
  }
}
