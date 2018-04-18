import java.util.*;
import java.io.*;

public class Election {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      StringTokenizer st = new StringTokenizer(br.readLine());
      PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

      int john = Integer.parseInt(st.nextToken());
      for (int i = 1; i < n; i++) {
        pq.offer(Integer.parseInt(st.nextToken()));
      }

      int count = 0;

      while (pq.peek() >= john) {
        int v = pq.poll();
        v--;
        john++;
        count++;
        pq.offer(v);
      }

      System.out.print(String.format("%d\n", count));

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
