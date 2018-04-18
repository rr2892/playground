import java.io.*;
import java.util.*;

public class PalindromicNums {

  private static ArrayList<Long> numbers = new ArrayList<Long>();
  static void addNumber(int x, ArrayList<Integer> digits) {
    int sz = digits.size();
    // Get a mirror of x, e.g. x = 123 => y = 123321
    long y = x;
    for (int i = sz - 1; i >= 0; i--) y = y * 10 + digits.get(i);
    numbers.add(y);
    // Get a mirror of x except for the last digit, e.g. x = 123 => y = 12321
    y = x;
    for (int i = sz - 2; i >= 0; i--) y = y * 10 + digits.get(i);
    numbers.add(y);
  }

  static void enumeratePalindromic(int k, int x, ArrayList<Integer> digits) {
    if (k > 0) addNumber(x, digits); // add the palindromic numbers that has x as its first half
    if (k == 6) return;
    for (int d = 0; d < 10; d++) {
      if (k == 0 && d == 0) continue; // cannot have leading zero
      digits.add(d);
      enumeratePalindromic(k + 1, x * 10 + d, digits);
      digits.remove(digits.size() - 1);
    }
  }

  public static void main(String[] args) throws IOException {
    enumeratePalindromic(0, 0, new ArrayList<Integer>());
    Collections.sort(numbers);

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    int q = Integer.parseInt(br.readLine());
    while (q-- > 0) {
      String[] tokens = br.readLine().split(" ");
      long x = Long.parseLong(tokens[0]);
      int k = Integer.parseInt(tokens[1]);
      int index = Collections.binarySearch(numbers, x);
      if (index < 0) index = -index - 1;
      sb.append(String.format("%d\n", numbers.get(index + k - 1)));
    }
    System.out.print(sb);
  }
}
