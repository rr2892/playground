import java.io.*;
import java.util.*;

public class SimpleMax
{
  public static void main (String[] args) throws Exception
  {
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();
    int max = Integer.MIN_VALUE;

    for (int i = 0; i < n; i++) {
    	int next = s.nextInt();
      max = Math.max(next, max);
    }

    System.out.println(max);
  }
}
