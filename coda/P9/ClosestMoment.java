import java.util.*;
import java.io.*;

class Car {
  int init_pos;
  int vi;

  Car(int init_pos, int vi) {
    this.init_pos = init_pos;
    this.vi = vi;
  }

  double pos(double time) {
    return init_pos + vi * time;
  }
}

public class ClosestMoment {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      List<Car> list = new ArrayList<>();

      //O(N)
      for (int i = 0; i < n; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int pos = Integer.parseInt(st.nextToken());
        int vi = Integer.parseInt(st.nextToken());
        list.add(new Car(pos, vi));
      }

      double l = 0, r = Double.MAX_VALUE;

      //O(N log N)
      while ((r - l) > 1E-6) {
        double m1 = l + (r - l) / 3;
        double m2 = r - (r - l) / 3;

        if (dist(list, m1) > dist(list, m2)) {
          l = m1;
        } else {
          r = m2;
        }
      }

      double t = (r + l) / 2;
      double finalDist = dist(list, t);
      System.out.print(String.format("%f\n", finalDist));

    } catch(IOException e) {
      System.exit(0);
    }
  }

  // O(N)
  static double dist(List<Car> list, double time) {
    double min = Double.MAX_VALUE, max = -1 * Double.MAX_VALUE;
    for (Car car : list) {
      double pos = car.pos(time);
      min = Math.min(min, pos);
      max = Math.max(max, pos);
    }

    return max - min;
  }
}
