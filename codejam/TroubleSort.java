import java.util.*;
import java.io.*;

public class TroubleSort {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    StringBuilder sb = new StringBuilder();
    int T = 50;
    List<int[]> L = new ArrayList<>();
    Random rand = new Random();
    for (int i = 0; i < T; i++) {
      L.add(new int[(rand.nextInt(100000) + 1)]);
      int[] A = L.get(i);
      for (int j = 0; j < A.length; j++) {
        A[j] = rand.nextInt(1000000000) + 1;
      }
      System.out.print(String.format("Test %d filled\n", (i+1)));
    }

    for (int i = 0; i < T; i++) {
      int n = L.get(i).length;
      int[] A = L.get(i);
      troubleSort(A);
      String res = null, extra = "";
      for (int j = 0; j < n - 1; j++) {
        if (A[j] > A[j+1]) {
          res = String.format("Case #%d: %d\n", (i+1), j);
          StringBuilder ext = new StringBuilder();
          int first = (j - 1 < 0)? j : j-1;
          ext.append(String.format("from index %d\n1: [", first));
          for (int k = first; k < j + 5; k++) {
            ext.append(String.format("%d, ", A[k]));
          }

          ext.append("]\n");
          extra = ext.toString();
          break;
        }
      }

      if (res == null) {
        res = String.format("Case #%d: OK\n", (i+1));
      }

      System.out.print(res);
      System.out.print(extra);
    }

    // System.out.print(sb.toString());
  }

  private static void troubleSort(int[] A) {
    boolean done = false;
    while (!done) {
      done = true;
      for (int i = 0; i < A.length - 2; i++) {
        if (A[i] > A[i+2]) {
          int temp = A[i];
          A[i] = A[i+2];
          A[i+2] = temp;
          done = false;
        }
      }
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
