import java.math.*;
import java.io.*;
import java.util.*;

public class SuperArray {
    static int MAX = 500_010;
    /*Idea:
     * We create a large array that maintains the indices of the numbers
     * if there were no deletions. In addition we keep track of the elements
     * that have been deleted. So for any number that exists at index 'i' of
     * our large array we can find its real index as "d = i-RSQ(1, i)". Since
     * 'd' is monotinically increasing we can binary search for the real index
     * of an element in the array.
     * We can do the RSQ in log(n) time and the binary search in log(n) time.
     * Since we must do this for all queries 'q', the runtime is O( q * log(n) * log(n)).
     */
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        SegmentTree removes = new SegmentTree(new int[MAX]);
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n+5];
        int max = 1;
        StringBuilder out = new StringBuilder();
        for( int q = 0; q < n; q++){
            String[] in = br.readLine().split(" ");
            char c = in[0].charAt(0);
            int index = Integer.parseInt(in[1]);
            int miss;
            int to;
            int r;
            int l;
            switch(c){
                case 'a':
                    nums[max++] = index;
                    break;
                case 'd':
                case 'p':
                    index++;
                    l = index; r = max;
                    // binary search the index
                    while(l <= r){
                        int h = (r+l)/2;
                        miss = removes.rsq(1, h);
                        int v = h-miss;
                        if (v < index) l = h + 1;
                        else r = h - 1;
                    }
                    to = l;
                    if (c == 'd')
                      removes.update_point(to, 1);
                    else
                      out.append(nums[to]).append('\n');
            }
        }
        System.out.print(out);
    }
}

//Segment Tree code from CP3
//Updated by Ronald Rojas
//Usage: Find Range Sum Queries and Update ranges in Log(n) time.
class SegmentTree {         // the segment tree is stored like a heap array
    private int[] st, A;
    private int n;
    private int left (int p) { return p << 1; } // same as binary heap operations
    private int right(int p) { return (p << 1) + 1; }

    private void build(int p, int L, int R) {
        if (L == R)                            // as L == R, either one is fine
            st[p] = A[L];                                         // store the index
        else {                                // recursively compute the values
            int M = (L+R)/2;
            build(left(p) , L    , M);
            build(right(p), M + 1, R);
            st[p] = st[left(p)] + st[right(p)];
        } }

    private int rsq(int p, int L, int R, int i, int j) {          // O(log n)
        if (i >  R || j <  L) return 0; // current segment outside query range
        if (L >= i && R <= j) return st[p];               // inside query range

        // update children
        // compute the min position in the left and right part of the interval
        int M = (L+R)/2;
        return rsq(left(p) , L  , M, i, j) +
               rsq(right(p), M+1, R, i, j);
    }

    private void update(int p, int L, int R, int x, int new_value) {
        // if the current interval does not intersect
        // the update index, return
        if (x > R || x < L) return;
      	if (L == R) {
      	  st[p] = new_value;
      		return;
      	}
        // update children
        // compute the range in the left and right part of the interval
        int lp = left(p), rp = right(p), M = (L+R)/2;
        update(lp,   L, M, x, new_value);
        update(rp, M+1, R, x, new_value);
        // return the sum
        st[p] = st[lp]+st[rp];
    }
    public SegmentTree(int[] _A) {
        A = _A; n = A.length;                   // copy content for local usage
        st = new int[4 * n];
        build(1, 0, n - 1);                     // recursive build
    }

    public int rsq(int i, int j) { return rsq(1, 0, n - 1, i, j); } // overloading
    public void update_point(int idx, int new_value) {
        update(1, 0, n - 1, idx, new_value); }
}
