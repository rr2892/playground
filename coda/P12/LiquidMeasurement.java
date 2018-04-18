import java.io.*;
import java.util.*;

public class LiquidMeasurement {
  static int NUMTUBE = 5;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] tokens = br.readLine().split(" ");

    int[] s = new int[NUMTUBE];
    for (int i = 0; i < NUMTUBE; i++) s[i] = Integer.parseInt(tokens[i]);
    int v = Integer.parseInt(tokens[NUMTUBE]);

    PriorityQueue<PQTuple> pq = new PriorityQueue<PQTuple>();
    TreeMap<DistTuple, Integer> dist = new TreeMap<DistTuple, Integer>();
    int[] vol = new int[NUMTUBE];
    vol[0] = s[0];
    PQTuple initial = new PQTuple(vol, 0);
    dist.put(initial.vols, 0);
    pq.add(initial);
    while (!pq.isEmpty()) {
      PQTuple now = pq.poll();
      if (dist.get(now.vols) < now.d) continue;
      if (now.vols.vol[0] == v) {
        System.out.println(now.d);
        return;
      }
      for (int i = 0; i < NUMTUBE; i++) {
        for (int j = 0; j < NUMTUBE; j++) {
          if (i == j) continue;
          int pour = Math.min(now.vols.vol[i], s[j] - now.vols.vol[j]);
          DistTuple newDist = new DistTuple(now.vols.vol);
          newDist.vol[i] -= pour;
          newDist.vol[j] += pour;
          if (!dist.containsKey(newDist) || dist.get(newDist) > now.d + pour) {
            dist.put(newDist, now.d + pour);
            pq.add(new PQTuple(newDist.vol, now.d + pour));
          }
        }
      }
    }
    System.out.println(-1);
  }

  static class DistTuple implements Comparable<DistTuple> {
    int[] vol;
    DistTuple(int[] _vol) {
      vol = Arrays.copyOf(_vol, NUMTUBE);
    }
    @Override
    public int compareTo(DistTuple ano) {
      for (int i = 0; i < NUMTUBE; i++) if (vol[i] != ano.vol[i]) return vol[i] - ano.vol[i];
      return 0;
    }
  }

  static class PQTuple implements Comparable<PQTuple> {
    int d;
    DistTuple vols;
    PQTuple(int[] _vol, int _d) {
      vols = new DistTuple(_vol);
      d = _d;
    }
    @Override
    public int compareTo(PQTuple ano) {
      return d - ano.d;
    }
  }

}
