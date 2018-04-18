import java.util.*;
import java.io.*;

public class CollectingCoins {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      Map<Integer, PriorityQueue<Integer>> pairs = new HashMap<>();
      for (int i = 0; i < n; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
        if (!pairs.containsKey(x)) pairs.put(x, new PriorityQueue<Integer>());
        pairs.get(x).add(y);
      }

      int trips = 0;

      while (!pairs.isEmpty()) {
        int ymax = 0;

        for (int x = 1; x <= 100; x++) {
          if (!pairs.containsKey(x)) continue;

          PriorityQueue<Integer> pq = pairs.get(x);
          Queue<Integer> tq = new LinkedList<>();

          while (!pq.isEmpty()) {
            int y = pq.poll();
            if (y >= ymax) {
              ymax = y;
            } else {
              tq.offer(y);
            }
          }

          while (!tq.isEmpty()) {
            pq.offer(tq.poll());
          }

          if (pq.isEmpty()) {
            pairs.remove(x);
          }
        }

        trips++;
      }

      System.out.print(String.format("%d\n", trips));

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
