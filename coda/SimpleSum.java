import java.io.*;
import java.util.*;

public class SimpleSum
{
  public static void main (String[]  args) throws Exception
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    StringBuilder sb = new StringBuilder();

    while ((line = br.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line);
      int sum = 0, tokens = st.countTokens();

      for (int i = 0; i < tokens; i++) {
        sum += Integer.parseInt(st.nextToken());
      }

      sb.append(String.format("%d\n", sum));
    }

    System.out.print(sb.toString());
  }
}
