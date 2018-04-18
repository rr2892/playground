import java.util.*;
import java.io.*;

public class HardRectangle {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    StringBuilder sb = new StringBuilder();

    try {
      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line);
        int n = Integer.parseInt(st.nextToken());
        if (n == 0) {
          break;
        }

        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
          heights[i] = Integer.parseInt(st.nextToken());
        }

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
          if (stack.isEmpty() || heights[i] > heights[stack.peek()]) {
            stack.push(i);
          } else {
            int top = stack.pop();
            int width = stack.isEmpty() ? i : i - 1 - stack.peek();
            maxArea = Math.max(maxArea, heights[top] * width);
            i--;
          }
        }

        sb.append(String.format("%d\n", maxArea));
      }
    } catch (IOException e) {
      System.exit(0);
    }

    System.out.print(sb.toString());
  }
}
