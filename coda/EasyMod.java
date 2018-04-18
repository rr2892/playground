import java.util.*;
import java.io.*;

public class EasyMod {
  public static void main(String[] args){
    Scanner s = new Scanner(System.in);
    long n = s.nextLong(), m = s.nextLong();

    StringBuilder sb = new StringBuilder();

    double log2m = log2(m);
    long ans;

    if (log2m < n) {
      ans = m;
    } else {
      long pow2n = (long) Math.pow(2,n);
      while (m >= pow2n) {
        m -= pow2n;
      }
      ans = m;
    }

    sb.append(String.format("%d\n", ans));
    System.out.print(sb.toString());
  }

  private static double log2(double d) {
    return Math.log(d) / Math.log(2);
  }
}
