import java.util.*;
import java.io.*;

public class ImageSimilarity {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    try {
      String line1 = br.readLine();
      StringTokenizer st1 = new StringTokenizer(line1);
      int r1 = Integer.parseInt(st1.nextToken()), c1 = Integer.parseInt(st1.nextToken());

      boolean[][] arr1 = new boolean[r1][c1];

      for (int i = 0; i < r1; i++) {
        String line = br.readLine();
        for (int j = 0; j < c1; j++) {
          arr1[i][j] = (line.charAt(j) == '#') ? true : false;
        }
      }

      String line2 = br.readLine();
      StringTokenizer st2 = new StringTokenizer(line2);
      int r2 = Integer.parseInt(st2.nextToken()), c2 = Integer.parseInt(st2.nextToken());

      boolean[][] arr2 = new boolean[r2][c2];

      for (int i = 0; i < r2; i++) {
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        for (int j = 0; j < c2; j++) {
          arr2[i][j] = (line.charAt(j) == '#') ? true : false;
        }
      }



      // Test correctness of input
      for (int i = 0; i < r1; i++) {
        for (int j = 0; j < c1; j++) {
          System.out.print(arr1[i][j] + " ");
        }
        System.out.println();
      }

      for (int i = 0; i < r2; i++) {
        for (int j = 0; j < c2; j++) {
          System.out.print(arr2[i][j] + " ");
        }
        System.out.println();
      }

      //arr1
      //arr1RH = reflectH(arr1)
      //arr1RV = reflectV(arr1)
      //arr1C = rotateC(arr1)
      //arr1CRH = reflectH(arr1C)
      //arr1CRV = reflectV(arr1C)
      //arr1CC = rotateCC(arr1)
      //arr1CCRH = reflectH(arr1CC)
      //arr1CCRV = reflectV(arr1CC)





    } catch(IOException e) {
      System.exit(0);
    }

  }
}
