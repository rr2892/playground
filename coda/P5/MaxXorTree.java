import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class MaxXorTree {
  static ArrayList<LinkedList<Edge>> g;
  static TrieNode root;
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt();
    g = new ArrayList<>();
    for(int i = 0; i < n; i++){
      g.add(new LinkedList<Edge>());
    }
    for(int i = 1; i < n; i++){
      int u = fr.nextInt()-1;
      int v = fr.nextInt()-1;
      int w = fr.nextInt();
      addEdge(u, v, w);
    }
    long[] xor = new long[n];
    boolean[] visited = new boolean[n];
    root = new TrieNode();
    TrieNode cur = root;
    for(int i = 0; i < 30; i++){
      cur.zero = new TrieNode();
      cur = cur.zero;
    }
    Queue<Integer> q = new LinkedList<>();
    q.add(0);
    long maxxor = 0;
    while(!q.isEmpty()){
      int u = q.poll();
      if(visited[u]) continue;
      visited[u] = true;
      for(Edge e : g.get(u)){
        int v = e.v;
        int w = e.w;
        if(visited[v]) continue;
        xor[v] = xor[u]^w;
        q.add(v);
        long pathxor = 0;
        cur = root;
        for(int i = 0; i < 30; i++){
          boolean isBitOn = ((xor[v] & (1<<(29-i))) > 0);
          if(isBitOn) {
            if(cur.one == null) cur.one = new TrieNode();
            cur = cur.one;
          }
          else {
            if(cur.zero == null) cur.zero = new TrieNode();
            cur = cur.zero;
          }
        }
        cur = root;
        for(int i = 0; i < 30; i++){
          boolean isBitOn = ((xor[v] & (1<<(29-i))) > 0);
          if(!isBitOn && cur.one != null){
            pathxor+=(1<<(29-i));
            cur = cur.one;
          }else if(isBitOn && cur.zero != null){
            pathxor+=(1<<(29-i));
            cur = cur.zero;
          }else if(!isBitOn){
            cur = cur.zero;
          }else{
            cur = cur.one;
          }
        }
        if(pathxor > maxxor) maxxor = pathxor;
      }
    }
    System.out.println(maxxor);
  }

  static void addEdge(int u, int v, int w){
    g.get(u).add(new Edge(u, v, w));
    g.get(v).add(new Edge(v, u, w));
  }
}

class Edge {
  int u, v, w;
  public Edge(int u, int v, int w){
    this.u = u;
    this.v = v;
    this.w = w;
  }
}

class TrieNode {
  TrieNode one, zero;
  public TrieNode(){}
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
