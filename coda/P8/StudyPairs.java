import java.util.*;
import java.io.*;

public class StudyPairs {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int n = Integer.parseInt(st.nextToken()), x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken());
      List<Integer> A = new ArrayList<>(), B = new ArrayList<>();

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < n; i++) {
        A.add(Integer.parseInt(st.nextToken()));
      }

      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < n; i++) {
        B.add(Integer.parseInt(st.nextToken()));
      }

      Map<Integer, Set<Integer>> pairs = new HashMap<>();
      Map<Integer, Integer> counts = new HashMap<>();

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (Math.abs(A[i] - B[j]) <= x && A[i] + B[j] <= y) {
            if (!pairs.containsKey(i)) pairs.put(i, new HashSet<Integer>());
            pairs.get(i).add(j);
            counts.put(B[j], counts.getOrDefault(B[j], 0) + 1);
          }
        }
      }

      Set<Integer> used = new HashSet<>();

      for (int i = 0; i < n; i++) {
        int min = Integer.MAX_VALUE, minj = -1;
        for (int j : pairs.get(i)) {
          if (used.contains(j)) continue;
          int count = counts.get(B[j]);
          if (count < min) {
            min = count;
            minj = j;
          }

          if (minj == -1) System.exit(0);
        }

        used.add(j);
        
      }



    } catch(IOException e) {
      System.exit(0);
    }
  }
}
