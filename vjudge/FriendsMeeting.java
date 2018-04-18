import java.util.*;
import java.io.*;

public class FriendsMeeting {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int a = Integer.parseInt(br.readLine()), b = Integer.parseInt(br.readLine());

      boolean odd = false;
      int diff = Math.abs(b - a);
      if ((b - a) % 2 == 1) {
        odd = true;
        diff--;
      }

      int k = 1, sum = 0;
      for (; k <= diff / 2; k++) {
        sum += 2 * k;
      }

      if (odd) sum += k;

      System.out.print(String.format("%d\n", sum));

    } catch(IOException e) {
      System.exit(0);
    }
  }
}
