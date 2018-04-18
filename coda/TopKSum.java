import java.util.*;
import java.io.*;

public class TopKSum {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());

      line = br.readLine();
      st = new StringTokenizer(line);

      Queue<Integer> minPQ = new PriorityQueue<>();
      // Queue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
      long sum = 0;

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < n; i++) {
        int num = Integer.parseInt(st.nextToken());
        if (i < k) {
          minPQ.offer(num);
          sum += num;
          sb.append(String.format("%d\n", sum));
          continue;
        }

        minPQ.offer(num);
        sum += num;
        int polled = minPQ.poll();
        sum -= polled;

        sb.append(String.format("%d\n", sum));
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }
}
