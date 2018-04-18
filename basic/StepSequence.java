import java.util.*;
import java.io.*;



public class StepSequence {
  public static void main(String[] args) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      Solution solution = new Solution();

      try {
        String line = br.readLine();
        int n = Integer.parseInt(line);
        List<List<Integer>> res = solution.stepList(n);
        for (List<Integer> list : res) {
          System.out.print("[ ");
          for (int i : list) {
            System.out.print(i + " ");
          }
          System.out.print("]\n");
        }
      } catch (IOException e) {
        System.exit(0);
      }
  }
}

class Solution {
  public List<List<Integer>> stepList(int n) {
      List<List<Integer>> res = new ArrayList<List<Integer>>();
      List<Integer> list = new ArrayList<Integer>(Arrays.asList(0));
      res.add(list);

      return stepListR(res, n);
  }

  private List<List<Integer>> stepListR(List<List<Integer>> res, int n) {
    if (n == 0) {
      return res;
    }

    List<List<Integer>> newRes = new ArrayList<List<Integer>>();

    for (List<Integer> list : res) {
      List<Integer> stepUp = new ArrayList<Integer>(), stepDown = new ArrayList<Integer>();
      stepUp.addAll(list);
      stepDown.addAll(list);

      int last = list.get(list.size() - 1);
      stepUp.add(last + 1);
      stepDown.add(last - 1);

      newRes.add(stepUp);
      newRes.add(stepDown);
    }

    return stepListR(newRes, n - 1);
  }
}
