import java.util.*;
import java.io.*;

public class SumQueries {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      int n = Integer.parseInt(st.nextToken()), q = Integer.parseInt(st.nextToken());

      line = br.readLine();
      List<Integer> list = new ArrayList<>();
      st = new StringTokenizer(line);
      for (int i = 0; i < n; i++) {
        int num = Integer.parseInt(st.nextToken());
        list.add(num);
      }

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < q; i++) {
        int sum = Integer.parseInt(br.readLine());
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : list) {
          if (map.containsKey(sum - num)) {
            count += map.get(sum - num);
          }

          map.put(num, map.getOrDefault(num, 0) + 1);
        }

        sb.append(String.format("%d\n", count));
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
