import java.util.*;
import java.io.*;

public class RangeUpdates {
  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), q = fr.nextInt();
    int[] A = new int[n];
    for (int i = 0; i < n; i++) {
      A[i] = fr.nextInt();
    }

    SegmentTree tree = new SegmentTree(A);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < q; i++) {
      char instr = fr.next().charAt(0);
      int l = fr.nextInt(), r = fr.nextInt();
      switch(instr) {
        case 'a':
          tree.addVal(l-1, r-1, fr.nextInt());
          break;
        case 's':
          sb.append(String.format("%d\n", tree.rsq(l-1, r-1)));
          break;
        case 'm':
          sb.append(String.format("%d\n", tree.rmq(l-1, r-1)));
          break;
        default:
          System.out.println("Error reading input");
          System.exit(0);
      }
    }

    System.out.print(sb.toString());

  }
}

class Node {
  int sum, min;
}

//Segment Tree code from CP3
//Updated by Ronald Rojas
//Usage: Find Range Sum Queries and Update ranges in Log(n) time.
class SegmentTree {         // the segment tree is stored like a heap array
    private int[] A;
    private Node[] st;
    private int[] lazy;
    private int n;
    private int left (int p) { return p << 1; } // same as binary heap operations
    private int right(int p) { return (p << 1) + 1; }

    private void buildS(int p, int L, int R) {
        if (st[p] == null) st[p] = new Node();
        if (L == R)                            // as L == R, either one is fine
            st[p].sum = A[L];                                         // store the index
        else {                                // recursively compute the values
            int M = (L+R)/2;
            buildS(left(p) , L    , M);
            buildS(right(p), M + 1, R);
            st[p].sum = st[left(p)].sum + st[right(p)].sum;
        }
    }

    private void buildM(int p, int L, int R) {
        if (st[p] == null) st[p] = new Node();
        if (L == R)                            // as L == R, either one is fine
            st[p].min = A[L];                                         // store the index
        else {                                // recursively compute the values
            buildM(left(p) , L              , (L + R) / 2);
            buildM(right(p), (L + R) / 2 + 1, R          );
            int p1 = st[left(p)].min, p2 = st[right(p)].min;
            st[p].min = Math.min(p1,p2);
        }
    }

    private int rsq(int p, int L, int R, int i, int j) {          // O(log n)
        if (i >  R || j <  L) return 0; // current segment outside query range
        if (L >= i && R <= j) return st[p].sum;               // inside query range

        if(lazy[p] > 0) relax(p, L, R);

        // update children
        // compute the min position in the left and right part of the interval
        int M = (L+R)/2;
        return rsq(left(p) , L  , M, i, j) +
               rsq(right(p), M+1, R, i, j);
    }

    private int rmq(int p, int L, int R, int i, int j) {
      if (i > R || j < L) return Integer.MAX_VALUE;
      if (i <= L && j >= R) return st[p].min;

      if (lazy[p] > 0) relax(p, L, R);

      int M = (L + R) / 2;
      int ansLeft = rmq(left(p), L, M, i, j);
      int ansRight = rmq(right(p), M+1, R, i, j);
      if (ansLeft == Integer.MAX_VALUE) return ansRight;
      if (ansRight == Integer.MAX_VALUE) return ansLeft;
      return Math.min(ansLeft, ansRight);
    }

    void relax(int k, int nl, int nr) {
      int nm = (nl + nr) / 2;
      add(2*k+1, nl, nm, nl, nm, lazy[k]); // just use add() to add lazy propagation value downwards
      add(2*k+2, nm+1, nr, nm+1, nr, lazy[k]);
      lazy[k] = 0; // clear the lazy propagation value
    }

    void add(int k, int nl, int nr, int l, int r, int v){
      if (r < nl || l > nr) return;
      if (l <= nl && nr <= r) {
        st[k].sum += v * (nr - nl + 1); // the sum is increased by v * {length of the range}
        lazy[k] += v; // record the lazy propagation value
        return;
      }
      int nm = (nl + nr) / 2;
      if (lazy[k] > 0) relax(k, nl, nr); // there is value to propagate, we first relax
      add(2*k+1, nl, nm, l, r, v);
      add(2*k+2, nm+1, nr, l, r, v);
      st[k].sum = st[left(k)].sum + st[right(k)].sum;
    }





    //
    // private void update(int p, int L, int R, int x, int new_value) {
    //     // if the current interval does not intersect
    //     // the update index, return
    //     if (x > R || x < L) return;
    //   	if (L == R) {
    //   	  st[p] = new_value;
    //   		return;
    //   	}
    //     // update children
    //     // compute the range in the left and right part of the interval
    //     int lp = left(p), rp = right(p), M = (L+R)/2;
    //     update(lp,   L, M, x, new_value);
    //     update(rp, M+1, R, x, new_value);
    //     // return the sum
    //     st[p] = st[lp]+st[rp];
    // }
    public SegmentTree(int[] _A) {
        A = _A; n = A.length;                   // copy content for local usage
        st = new Node[4 * n];
        buildS(1, 0, n - 1);
        buildM(1, 0, n - 1);
        lazy = new int[4 * n];              // recursive build
    }

    public int rsq(int i, int j) { return rsq(1, 0, n - 1, i, j); } // overloading
    public int rmq(int i, int j) { return rmq(1, 0, n - 1, i, j); }
    public void addVal(int i, int j, int v) { add(1, 0, n - 1, i, j, v); }
    // public void update_point(int idx, int new_value) {
    //     update(1, 0, n - 1, idx, new_value); }
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
