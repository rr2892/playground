import java.util.*;
import java.io.*;

public class BracketSequence {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line = "";

    try {
      line = br.readLine();
    } catch(IOException e) {
      System.exit(0);
    }

    Stack<Character> stack = new Stack<>();
    Set<Character> opened = new HashSet<>(Arrays.asList('(', '['));
    Set<Character> closed = new HashSet<>(Arrays.asList(')', ']'));

    for (int i = 0; i < line.length(); i++) {
      char bracket = line.charAt(i);
      if (!stack.isEmpty() && ((bracket == ')' && stack.peek() == '(') || (bracket == ']' && stack.peek() == '['))) {
        stack.pop();
      } else {
        stack.push(bracket);
      }
    }

    StringBuilder sb = new StringBuilder();

    if (stack.isEmpty()) {
      sb.append("valid\n");
    } else if (stack.size() % 2 != 0) {
      sb.append("invalid no\n");
    } else {
      LinkedList<Character> list = new LinkedList<>();
      while (!stack.isEmpty()) {
        list.addFirst(stack.pop());
      }

      int head = 0, tail = list.size() - 1;
      boolean oneChanged = false, valid = true;

      while (head < tail) {
        char open = list.get(head), close = list.get(tail);
        if (!((open == '(' && close == ')') || (open == '[' && close == ']'))) {
          if ((closed.contains(open) && opened.contains(close)) || oneChanged) {
            valid = false;
            break;
          }

          oneChanged = true;
        }

        head ++;
        tail --;
      }

      if (valid) {
        sb.append("invalid yes\n");
      } else {
        sb.append("invalid no\n");
      }
    }

    System.out.print(sb.toString());
  }

}
