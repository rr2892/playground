import java.util.*;
import java.io.*;

public class Partition {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      StringTokenizer st = new StringTokenizer(br.readLine());
      // PriorityQueue<Integer> minPQ = new PriorityQueue<>(), maxPQ = new PriorityQueue<>(Collections.reverseOrder());
      int minSum = 0, maxSum = 0;

      for (int i = 0; i < n; i++) {
        int k = Integer.parseInt(st.nextToken());
        if (k > 0) maxSum += k;
        else minSum += k;
      }

      System.out.print(String.format("%d\n", maxSum - minSum));

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
