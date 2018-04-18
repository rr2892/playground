import java.util.*;
import java.io.*;

public class MissingGnomes {
  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), m = fr.nextInt();
    Set<Integer> mset = new HashSet<>();
    Queue<Integer> qn = new LinkedList<>(), qm = new LinkedList<>();

    for (int i = 0; i < m; i++) {
      int next = fr.nextInt();
      mset.add(next);
      qm.offer(next);
    }

    for (int i = 1; i <= n; i++) {
      if (!mset.contains(i)) {
        qn.offer(i);
      }
    }

    StringBuilder sb = new StringBuilder();

    while (!qn.isEmpty() && !qm.isEmpty()) {
      int polled = (qn.peek() < qm.peek()) ? qn.poll() : qm.poll();
      sb.append(String.format("%d\n", polled));
    }

    while (!qn.isEmpty()) sb.append(String.format("%d\n", qn.poll()));
    while (!qm.isEmpty()) sb.append(String.format("%d\n", qm.poll()));

    System.out.print(sb.toString());
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
