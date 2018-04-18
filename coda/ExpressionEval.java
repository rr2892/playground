import java.util.*;
import java.io.*;

public class ExpressionEval {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String infix = br.readLine();
      List<String> postfix = convertToPostfix(infix);
      // if (postfix != null) {
      //   for (String s : postfix) {
      //     System.out.print(s + " ");
      //   }
      //   System.out.println();
      // } else
      //   System.out.println("invalid");

      String result = evaluate(postfix);
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%s\n", result));
      System.out.print(sb.toString());
    } catch (IOException e) {
      System.exit(0);
    }
  }

  private static List<String> convertToPostfix(String infix) {
    Set<Character> digits = new HashSet<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));
    Set<Character> lowOps = new HashSet<>(Arrays.asList('+', '-'));
    Set<Character> highOps = new HashSet<>(Arrays.asList('*', '/'));
    Stack<Character> stack = new Stack<>();
    List<String> list = new ArrayList<>();
    StringBuilder num = new StringBuilder();
    boolean valid = true;
    boolean between = true;

    for (int i = 0; i < infix.length(); i++) {
      char c = infix.charAt(i);
      if (digits.contains(c)) {
        num.append(c);
        continue;
      }

      if (num.length() > 0) {
        list.add(num.toString());
        num = new StringBuilder();
        between = true;
      }

      if (c == '(') {
        stack.push(c);
        between = false;
      } else if (c == ')') {
        if (!between) {
          return null;
        }

        char popped = 'z';

        while (!stack.isEmpty()) {
          popped = stack.pop();
          if (popped == '(') break;
          list.add(String.valueOf(popped));
        }

        if (popped != '(') {
          return null;
        }
      } else if (lowOps.contains(c) || highOps.contains(c)) {
        boolean priority = (highOps.contains(c)) ? true : false;
        while (!stack.isEmpty()) {
          char top = stack.peek();
          if (top == '(') break;
          if (priority && lowOps.contains(top)) break;

          list.add(String.valueOf(stack.pop()));
        }

        stack.push(c);

      } else {
        return null;
      }
    }

    if (num.length() > 0)
      list.add(num.toString());

    while (!stack.isEmpty()) {
      if (stack.peek() == '(') {
        return null;
      }

      list.add(String.valueOf(stack.pop()));
    }

    return list;
  }

  private static String evaluate(List<String> list) {
    if (list == null || list.size() == 0) return "invalid";

    Set<String> operands = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
    Stack<Long> stack = new Stack<>();
    final long P = 1000000007;

    for (int i = 0; i < list.size(); i++) {
      String s = list.get(i);
      if (operands.contains(s)) {
        if (stack.size() < 2) {
          return "invalid";
        }

        long n2 = stack.pop(), n1 = stack.pop();
        long res = 0;

        switch (s) {
          case "+":
            res = (n1 % P + n2 % P) % P;
            break;
          case "-":
            res = (P - n2 + n1) % P;
            break;
          case "*":
            res = ((n1 % P) * (n2 % P)) % P;
            break;
          case "/":
            res = n1 / n2;
            break;
        }

        stack.push(res);
      } else {
        stack.push(Long.parseLong(s));
      }
    }

    if (stack.size() > 1) return "invalid";
    return String.format("%d", stack.pop());
  }
}
