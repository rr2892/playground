import java.util.*;
import java.io.*;

public class SavingUniverse {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    StringBuilder sb = new StringBuilder();
    int T = fr.nextInt();
    for (int i = 0; i < T; i++) {
      int D = fr.nextInt();
      List<Integer> list = new ArrayList<>();
      list.add(0);
      String str = fr.next();
      int sum = 0, idx = 0;

      for (int j = 0; j < str.length(); j++) {
        char c = str.charAt(j);
        if (c == 'S') {
          list.set(idx, list.get(idx) + 1);
          sum += (1 << idx);
        } else {
          list.add(0);
          idx++;
        }
      }

      int swaps = 0;
      while (idx > 0 && sum > D) {
        if (list.get(idx) == 0) {
          idx--;
          continue;
        }

        list.set(idx, list.get(idx) - 1);
        list.set(idx - 1, list.get(idx - 1) + 1);
        sum = sum - (1 << idx) + (1 << (idx - 1));;
        swaps++;
      }

      String res = (sum <= D) ? String.format("%d", swaps) : "IMPOSSIBLE";
      sb.append(String.format("Case #%d: %s\n", (i+1), res));
    }

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
