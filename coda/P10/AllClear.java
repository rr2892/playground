import java.math.*;
import java.io.*;
import java.util.*;

public class AllClear {
    /*Idea:
     * We iterate from left to right destroying all of the remaining blocks
     * left in the column. For every column 'i' we destroy, if we destroy 'j'
     * blocks we will also destroy 'j' blocks for the next 'k' columns.
     * So for any column 'i' that we are currently considering we need to know
     * how many blocks we have already destroyed. We can maintain this through
     * RSQ. If we destroy a column 'i', then the next 'k' columns will also be lower
     * by 'j' amount. So we can do RSQ(i-k+1,i) to find "out of the last k blocks
     * how many have we shot the laser"
     * I do RSQ(i, i+k-1) for easier indexing.
     */
    public static void main(String[] args ) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int k = Integer.parseInt(in[1]);
        in = br.readLine().split(" ");
        long[] arr = new long[n];
        SegmentTree st = new SegmentTree(new int[n +k+10]);
        long tot = 0;
        for( int i = 0; i < n; i++){

            int a = Integer.parseInt(in[i]);
            int sum = st.rsq(i, i+k-1);
            if(a <= sum)continue;
            sum = a - sum;
            st.update_point(i+k-1, sum);
            tot += sum;
        }
        System.out.println(tot);



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
            build(left(p) , L              , (L + R) / 2);
            build(right(p), (L + R) / 2 + 1, R          );
            int p1 = st[left(p)], p2 = st[right(p)];
            st[p] = p1+p2;
        } }

    private int rsq(int p, int L, int R, int i, int j) {          // O(log n)
        if (i >  R || j <  L) return -1; // current segment outside query range
        if (L >= i && R <= j) return st[p];               // inside query range

        //update children
        // compute the min position in the left and right part of the interval
        int p1 = rsq(left(p) , L              , (L+R) / 2, i, j);
        int p2 = rsq(right(p), (L+R) / 2 + 1, R          , i, j);

        if (p1 == -1) return p2;   // if we try to access segment outside query
        if (p2 == -1) return p1;                               // same as above
        return p1+p2;
    }

    private int update(int p, int L, int R, int idx, int idz,int new_value) {
        int i = idx, j = idz;

        // if the current interval does not intersect
        // the update interval, return this st node value!
        if (i > R || j < L)
            return st[p];

        // if the current interval is included in the update range,
        // update that st[node]

	if(i == R && j ==L)
		return st[p] = new_value;

        //update children

        // compute the range in the
        // left and right part of the interval
        int p1, p2;
        p1 = update(left(p) , L              , (L + R) / 2, idx, idz, new_value);
        p2 = update(right(p), (L + R) / 2 + 1, R          , idx, idz, new_value);

        // return the sum
        return st[p] = p1+p2;
    }

    //Update Children to correct values
    private void prop(int p, int L, int R){
        int r = (R-L) +1;
        int lr =(L+R)/2; //left range
        lr-=L;
        lr++;
        int rr = R; //right range
        rr-=((L+R)/2 +1);
        rr++;

        int left = left(p);
        int right = right(p);
        if(st[p] == 0) {
            st[left] = 0;
            st[right] = 0;
        }else if(st[p] == r){
            st[left] = lr;
            st[right] = rr;

        }
    }
    public SegmentTree(int[] _A) {
        A = _A; n = A.length;                   // copy content for local usage
        st = new int[4 * n];
        for (int i = 0; i < 4 * n; i++) st[i] = 0; // create vector with length `len' and fill it with zeroes
        build(1, 0, n - 1);                                  // recursive build
    }

    public int rsq(int i, int j) { return rsq(1, 0, n - 1, i, j); } // overloading

    public int update_range(int idx, int idz, int new_value){
        return update(1, 0, n-1, idx,  idz, new_value);}

    public int update_point(int idx, int new_value) {
        return update(1, 0, n - 1, idx,idx, new_value); }

}
