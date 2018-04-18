import java.util.*;
import java.io.*;

public class BracketStack {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line = "";

    try {
      line = br.readLine();
    } catch(IOException e) {
      System.exit(0);
    }

    Stack<Character> stack = new Stack<>();
    boolean holding = false;
    boolean valid = true;
    boolean oneChanged = false;

    Set<Character> open = new HashSet<>(Arrays.asList('(', '['));
    Set<Character> close = new HashSet<>(Arrays.asList(')', ']'));

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (open.contains(c)) {
        stack.push(c);
      } else if (stack.isEmpty()) {
        if (oneChanged) {
          valid = false;
          break;
        }

        if (holding) {
          oneChanged = true;
          holding = false;
        } else {
          holding = true;
        }
      } else if (!holding && (c == ')' && stack.peek() == '(') || (c == ']' && stack.peek() == '[')) {
        stack.pop();
      } else {
        if (oneChanged) {
          valid = false;
          break;
        }

        if (holding) {
          oneChanged = true;
          holding = false;
          // stack.pop();
          // System.out.println("Popped!");
          // i--;
        } else {
          holding = true;
        }
      }

      System.out.println(stack.toString() + "\t" + i);
    }

    StringBuilder sb = new StringBuilder();

    if (!valid) {
      sb.append("invalid no\n");
    } else if (stack.isEmpty() && !oneChanged && !holding) {
      sb.append("valid\n");
    } else {
      if (oneChanged && stack.isEmpty()) {
        sb.append("invalid yes\n");
      } else if (!oneChanged && holding && stack.size() == 1){
        sb.append("invalid yes\n");
      } else {
        sb.append("invalid no\n");
      }
    }

    System.out.print(sb.toString());
  }
}
