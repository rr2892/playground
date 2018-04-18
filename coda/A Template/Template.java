import java.util.*;
import java.io.*;

public class Template {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
    } catch(IOException e) {
      System.exit(0);
    }
  }
}
