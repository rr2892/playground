import java.util.*;
import java.io.*;

public class Triangle {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt(), m = fr.nextInt();
    Set<Integer> set = new HashSet<>();
    Node[] map = new Node[n+1];
    for (int i = 1; i <= n; i++) {
      map[i] = new Node(i);
    }

    for (int i = 0; i < m; i++) {
      int n1 = fr.nextInt(), n2 = fr.nextInt();
      if (n2 > n1) {
        int temp = n1;
        n1 = n2;
        n2 = temp;
      }

      map[n1].edges.add(n2);
    }

    int count = 0;

    for (int i = 1; i <= n; i++) {
      for (int j : map[i].edges) {
        Set<Integer> intersection = new HashSet<>(map[i].edges);
        intersection.retainAll(map[j].edges);
        count += intersection.size();
      }
    }

    System.out.print(String.format("%d\n", count));
  }
}

class Node {
  int id;
  Set<Integer> edges;

  Node(int id) {
    this.id = id;
    edges = new HashSet<>();
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
