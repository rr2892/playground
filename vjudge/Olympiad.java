import java.util.*;
import java.io.*;

public class Olympiad {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      int n = Integer.parseInt(line);
      line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      Set<Integer> set = new HashSet<Integer>();

      for (int i = 0; i < n; i++) {
        int g = Integer.parseInt(st.nextToken());
        set.add(g);
      }

      set.remove(0);
      System.out.print(String.format("%d\n", set.size()));

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
