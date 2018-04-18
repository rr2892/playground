import java.util.*;
import java.io.*;

public class InsertBracket {

  // private static boolean matching(char open, char close) {
  //   if ((open == '(' && close == ')') || (open == '[' && close == ']')) {
  //     return true;
  //   }
  //
  //   return false;
  // }

  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      Stack<Character> stack = new Stack<>();

      for (int i = 0; i < line.length(); i++) {
        char close = line.charAt(i);
        if (!stack.isEmpty() && ((stack.peek() == '(' && close == ')') || (stack.peek() == '[' && close == ']'))) {
          stack.pop();
        } else {
          stack.push(close);
        }
      }

      StringBuilder sb = new StringBuilder();

      if (stack.isEmpty()) {
        sb.append("valid\n");
      } else if (stack.size() % 2 != 1) {
        sb.append("invalid no\n");
      } else {
        LinkedList<Character> list = new LinkedList<>();
        while (!stack.isEmpty()) {
          list.addFirst(stack.pop());
        }

        int head = 0, tail = list.size() - 1;

        while (head < tail) {
          char open = list.get(head), close = list.get(tail);
          if (!((open == '(' && close == ')') || (open == '[' && close == ']'))) {
            break;
          }

          head++;
          tail--;
        }

        if (head == tail) {
          sb.append("invalid yes\n");
        } else {
          sb.append("invalid no\n");
        }
      }

      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }

}
