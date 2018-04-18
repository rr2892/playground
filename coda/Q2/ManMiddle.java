import java.util.*;
import java.io.*;

public class ManMiddle {
  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), m = fr.nextInt();
    ArrayList<HashSet<Integer>> adj = new ArrayList<>();
    Set<Integer> pathExists = new HashSet<>();

    for (int i = 0; i <= n; i++) {
      adj.add(new HashSet<Integer>());
    }

    for (int i = 0; i < m; i++) {
      int a = fr.nextInt(), b = fr.nextInt();
      if (b > a) {
        int temp = a;
        a = b;
        b = temp;
      }

      adj.get(a).add(b);
    }



    if (adj.get(1).contains(n)) {
      System.out.println("impossible");
    } else {
      Queue<Integer> q = new LinkedList<>();
      int count = 0, idx = 1;
      for (int j : adj.get(1)) {
        q.offer(j);
      }

      while (!q.isEmpty()) {
        int i = q.poll();
        // System.out.println(i);
        if (adj.get(i).contains(n)) {
          count++;
          pathExists.add(i);
          continue;
        }

        Iterator<Integer> iter = adj.get(i).iterator();

        while (iter.hasNext()) {
          int j = iter.next();
          if (!pathExists.contains(j)) {
            q.offer(j);
          }
        }
      }

      System.out.println(count);
    }


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
