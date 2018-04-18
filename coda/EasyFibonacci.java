import java.util.*;
import java.io.*;

public class EasyFibonacci {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();

    int prev2 = 0, prev1 = 1, fib = prev1 + prev2;
    StringBuilder sb = new StringBuilder("");

    for (int i = 1; i <= n; i++) {
      if (i == fib) {
        prev2 = prev1;
        prev1 = fib;
        fib = prev1 + prev2;
        sb.append("O");
      } else {
        sb.append("o");
      }
    }

    sb.append("\n");
    System.out.print(sb.toString());
  }
}
