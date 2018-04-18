import java.util.*;
import java.io.*;

public class P10_3 {
  public static void main(String[] args) {
    int[] A = {5,4,3,2,1};
    System.out.println(doubleCount(A));
  }
  private static int doubleCount(int[] A){
    TreeSet<Integer> bbst = new TreeSet<Integer>();
    int count = 0;
    for (int i = 0; i < A.length; i++) {
      if (bbst.contains(2*A[i])) count++;
      bbst.add(A[i]);
    }
    return count;
  }
}
