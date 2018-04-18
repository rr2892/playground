import java.util.*;
import java.io.*;

public class EasyParentheses {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = 0;

    try {
      String s1 = br.readLine();
      StringTokenizer st1 = new StringTokenizer(s1);
      n = Integer.parseInt(st1.nextToken());
    } catch (IOException e) {
      System.exit(0);
    }

    StringBuilder sb = new StringBuilder();
    char[] open = {'(', '['};
    char[] close = {')', ']'};

    for (int i = 0; i < n; i++) {
      try {
        String s = br.readLine();
        int len = s.length();
        Stack<Character> stack = new Stack<>();
        boolean active = true;

        for (int j = 0; j < len && active; j++) {
          char c = s.charAt(j);
          boolean closed = false;

          for (int k = 0; k < 2 && active; k++) {
            if (c == close[k]) {
              if (stack.isEmpty() || stack.peek() != open[k]) {
                sb.append("No\n");
                active = false;
              } else {
                stack.pop();
              }

              closed = true;
              break;
            }
          }

          if (!closed) {
            stack.push(c);
          }
        }

        if (active) {
          if (stack.isEmpty()) {
            sb.append("Yes\n");
          } else {
            sb.append("No\n");
          }
        }

      } catch (IOException e) {
        System.exit(0);
      }
    }

    System.out.print(sb.toString());
  }
}
