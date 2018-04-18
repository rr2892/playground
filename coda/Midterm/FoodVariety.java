import java.util.*;
import java.io.*;

public class FoodVariety {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt(), m = fr.nextInt(), k = fr.nextInt();

    TreeSet<Restaurant> restSet = new TreeSet<>((r1, r2) -> r1.pos - r2.pos);

    for (int i = 0; i < n; i++) {
      int pos = fr.nextInt(), type = fr.nextInt();
      Restaurant r = new Restaurant(pos, type);
      restSet.add(r);
    }

    // List<Integer> custs = new ArrayList<>();

    int dist = 0;

    for (int i = 0; i < m; i++) {
      int pos = fr.nextInt();
      Set<Integer> types = new HashSet<>();
      int low = pos, high = pos;

      while (types.size() < k) {
        Restaurant up = restSet.higher(new Restaurant(high, 0));
        Restaurant down = restSet.lower(new Restaurant(low, 0));

        if (up != null && down != null && !types.contains(up.type) && !types.contains(down.type)) {
          if (Math.abs(up.pos - pos) < Math.abs(down.pos - pos)) {
            types.add(up.type);
            high = up.pos;
            dist = Math.max(dist, Math.abs(up.pos - pos));
          } else {
            types.add(down.type);
            low = down.pos;
            dist = Math.max(dist, Math.abs(down.pos - pos));
          }

          continue;
        }

        if (up != null && !types.contains(up.type)) {
          types.add(up.type);
          dist = Math.max(dist, Math.abs(up.pos - pos));
        }

        if (down != null && !types.contains(down.type)) {
          types.add(down.type);
          dist = Math.max(dist, Math.abs(down.pos - pos));
        }

        if (up != null) high = up.pos;
        if (down != null) low = down.pos;
      }
    }

    System.out.print(String.format("%d\n", dist));
  }
}

class Restaurant {
  int pos;
  int type;

  Restaurant(int pos, int type) {
    this.pos = pos;
    this.type = type;
  }
}

class FastReader{
  BufferedReader br;
  StringTokenizer st;

  FastReader(){
    br = new BufferedReader(new InputStreamReader(System.in));
  }

  String next(){
    while(st==null || !st.hasMoreTokens()){
      try{
        st = new StringTokenizer(br.readLine());
      }catch(IOException e){
        e.printStackTrace();
      }
    }
    return st.nextToken();
  }

  int nextInt(){
    return Integer.parseInt(next());
  }

  long nextLong(){
    return Long.parseLong(next());
  }

  double nextDouble(){
    return Double.parseDouble(next());
  }

  String nextLine(){
    String str = "";
    try{
      str = br.readLine();
    }catch(IOException e){
      e.printStackTrace();
    }
    return str;
  }
}
