import java.util.*;
import java.io.*;

class Node {
  char val;
  Node next, prev;
  Node(char val) {
    this.val = val;
  }
}

class Pair {
  char[] c1, c2;
  int i1, i2;
  Pair(String s1, String s2) {
    c1 = s1.toCharArray();
    c2 = s2.toCharArray();
    i1 = 0;
    i2 = 0;
  }
}

public class AncientAlphabet {
  static Node head, tail;
  static Map<Character, Node> map;

  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt(), k = fr.nextInt();
    map = new HashMap<>();

    for (int i = 0; i < k; i++) {
      if (i == 26) {
        i += 6;
        k += 6;
      }

      insert((char)(i + 65));
    }

    String[] dict = new String[n];

    for (int i = 0; i < n; i++) {
      dict[i] = fr.next();
    }

    Queue<Pair> queue = new LinkedList<>();

    for (int i = 0; i < n - 1; i++) {
      queue.offer(new Pair(dict[i], dict[i+1]));
    }

    boolean possible = true;

    while (!queue.isEmpty() && possible) {
      int size = queue.size();
      Set<Character> visited = new HashSet<>();
      for (int i = 0; i < size; i++) {
        Pair pair = queue.poll();
        if (pair.i1 >= pair.c1.length && pair.i2 < pair.c2.length) {
          possible = false;
          break;
        } else if (pair.i1 >= pair.c1.length || pair.i2 >= pair.c2.length) {
          continue;
        }

        if (visited.contains(pair.c2[pair.i2])) {
          possible = false;
          break;
        }

        if (pair.c1[pair.i1] == pair.c2[pair.i2]) {
          pair.i1++;
          pair.i2++;
          queue.offer(pair);
        } else {
          placeCAfterP(pair.c2[pair.i2], pair.c1[pair.i1]);
          visited.add(pair.c1[pair.i1]);
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    if (possible) {
      Node node = head;
      while (node != null) {
        sb.append(node.val);
        node = node.next;
      }
    } else {
      sb.append("impossible");
    }

    sb.append("\n");
    System.out.print(sb.toString());
  }

  private static void insert(char c) {
    Node node = new Node(c);
    if (head == null) {
      head = node;
      tail = node;
    } else {
      tail.next = node;
      node.prev = tail;
      tail = tail.next;
    }

    map.put(c, node);
  }

  private static void remove(Node node) {
    if (node.prev != null) node.prev.next = node.next;
    if (node.next != null) node.next.prev = node.prev;
    if (head == node) head = head.next;
    else if(tail == node) tail = tail.prev;
  }

  private static void placeCAfterP(char c, char p) {
    Node node = map.get(c), precede = map.get(p);
    remove(node);
    insertNodeAfterPreceding(node, precede);
  }

  private static void insertNodeAfterPreceding(Node node, Node precede) {
    if (precede.next != null) {
      precede.next.prev = node;
    } else {
      tail = node;
    }

    node.next = precede.next;
    node.prev = precede;
    precede.next = node;
  }
}

class FastReader {
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
