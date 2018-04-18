import java.util.*;
import java.io.*;

public class OperatorMax {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      List<Integer> list = new ArrayList<>();

      while (st.hasMoreTokens()) {
        list.add(Integer.parseInt(st.nextToken()));
      }

      int res = max_r(list, 1, list.get(0));
      System.out.print(String.format("%d\n", res));
    } catch (IOException e) {
      System.exit(0);
    }
  }

  int max(int[] A) {
    return max_r(A, 1, A[0]);
  }

  int max_r(int[] A, int i, int total) {
    return (i == A.length) ? total : Math.max(max_r(A, i + 1, total * A[i]), max_r(A, i + 1, total + A[i]));
  }

  private static int max_r(List<Integer> list, int index, int num) {
    if (index == list.size()) {
      return num;
    }

    return Math.max(max_r(list, index + 1, num * list.get(index)), max_r(list, index + 1, num + list.get(index)));
  }
}
