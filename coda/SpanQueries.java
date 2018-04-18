import java.util.*;
import java.io.*;

class Spanner {
  Map<Integer, Integer> map;
  PriorityQueue<Integer> minPQ;
  PriorityQueue<Integer> maxPQ;

  public Spanner() {
    map = new HashMap<>();
    minPQ = new PriorityQueue<>();
    maxPQ = new PriorityQueue<>(Collections.reverseOrder());
  }

  public void add(int n) {
    if (!map.containsKey(n)) {
      map.put(n, 1);
      minPQ.offer(n);
      maxPQ.offer(n);
    } else {
      map.put(n, map.get(n) + 1);
    }
  }

  public void delete(int n) {
    if (!map.containsKey(n)) {
      return;
    }

    if (map.get(n) == 1) {
      map.remove(n);
    } else {
      map.put(n, map.get(n) - 1);
    }
  }

  public int span() {
    if (map.isEmpty()) {
      return -1;
    }

    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

    while (!minPQ.isEmpty() && !map.containsKey(minPQ.peek())) {
      minPQ.poll();
    }

    min = minPQ.peek();

    while (!maxPQ.isEmpty() && !map.containsKey(maxPQ.peek())) {
      maxPQ.poll();
    }

    max = maxPQ.peek();

    return max - min;
  }
}

public class SpanQueries {
  public static void main (String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      String line = br.readLine();
      int q = Integer.parseInt(line);
      Spanner spanner = new Spanner();
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < q; i++) {
        line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String instr = st.nextToken();
        if (instr.equals("s")) {
          sb.append(String.format("%d\n", spanner.span()));
        } else {
          int n = Integer.parseInt(st.nextToken());

          if (instr.equals("a")) {
            spanner.add(n);
          } else {
            spanner.delete(n);
          }
        }
      }

      System.out.print(sb.toString());

    } catch (IOException e) {
      System.exit(0);
    }

  }
}
