import java.util.*;
import java.io.*;

public class NetworkTest {
  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), m = fr.nextInt();
    Network network = new Network(n);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < m; i++) {
      int n1 = fr.nextInt(), n2 = fr.nextInt();
      int newPairs = network.addEdge(n1, n2);
      sb.append(String.format("%d\n", newPairs));
    }

    System.out.print(sb.toString());
  }
}

class Network {
  Map<Integer, Node> map;

  Network(int n) {
    map = new HashMap<>();
    for (int i = 1; i <= n; i++) {
      map.put(i, new Node(i));
    }
  }

  int addEdge(int n1, int n2) {
    Node node1 = map.get(n1), node2 = map.get(n2);

    if (node1.parent == node2 || node1.children.contains(node2)) {
      return 0;
    }

    // go to parent nodes in both trees
    if (node1.parent != null) node1 = node1.parent;
    if (node2.parent != null) node2 = node2.parent;

    if (node1 == node2) {
      return 0;
    }

    int res = (node1.children.size() + 1) * (node2.children.size() + 1);

    // node1 will refer to the larger node
    if (node2.children.size() > node1.children.size()) {
      Node temp = node1;
      node1 = node2;
      node2 = temp;
    }

    for (Node child : node2.children) {
      child.parent = node1;
      node1.children.add(child);
    }

    node2.children = new HashSet<Node>();
    node2.parent = node1;
    node1.children.add(node2);

    return res;
  }
}

class Node {
  int id;
  Set<Node> children;
  Node parent;

  Node(int id) {
    this.id = id;
    children = new HashSet<>();
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
