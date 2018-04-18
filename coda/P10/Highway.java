import java.math.*;
import java.io.*;
import java.util.*;

public class Highway {
    static ArrayList<Integer>[] g;
    static ArrayList<Integer>[] e;
    static boolean[] seen;
    static int[] ranges;
    static int[] first;
    static SegmentTree st ;
    static int index= 0;
    /* Idea:
     * This is exactly the LCA to RMQ mentioned in class.
     * We root and DFS the tree and maintain the first time we see
     * a node and every time we revisit a node.
     * Then we do a RMQ from node 'a' to node 'b' to find
     * the distance of the LCA to root,
     * then root find the distance from root to 'a' and root to 'b'.
     * The distance from 'a' to 'b' is "dist(a) + dist(b) - (2*dist(lca))"
     */

    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        n++;
        g = new ArrayList[n];
        e = new ArrayList[n];
        for( int i = 0; i < n; i++){
            g[i] = new ArrayList<>();
            e[i] = new ArrayList<>();
        }
        seen = new boolean[n];
        first = new int[n];
        ranges = new int[n*3];

        for( int i = 2; i < n; i++){
            String[] in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            int c = Integer.parseInt(in[2]);
            g[a].add(b);
            g[b].add(a);
            e[a].add(c);
            e[b].add(c);
        }
        dfs(1, 0);
        st = new SegmentTree(ranges);
        StringBuilder  out = new StringBuilder();
        int qq = Integer.parseInt(br.readLine());
        for( int q = 0; q < qq; q++){
            String[] in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            a = first[a];
            b = first[b];
            if(b < a){
                int c = a;
                a = b;
                b = c;
            }
            int lca = st.rsq(a,b);
            int dist = ranges[a] + ranges[b] -(2*lca);
            out.append(dist).append('\n');
        }
        System.out.print(out.toString());
    }
    public static boolean dfs(int node,int d){
        if(seen[node])return false;
        first[node] = index;
        seen[node] = true;
        ranges[index] = d;
        index++;
        for( int i = 0; i< g[node].size(); i++){
            if(dfs(g[node].get(i), d + e[node].get(i))){
                ranges[index] = d;
                index++;
            }
        }
        return true;
    }

}
//Segment Tree code from CP3
//Updated by Ronald Rojas
//Usage: Find Range Sum Queries and Update ranges in Log(n) time.
class SegmentTree {         // the segment tree is stored like a heap array
    private int[] st, A;
    static int inf = Integer.MAX_VALUE/2;
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
            st[p] = Math.min(p1,p2);
        } }

    private int rsq(int p, int L, int R, int i, int j) {          // O(log n)
        if (i >  R || j <  L) return inf; // current segment outside query range
        if (L >= i && R <= j) return st[p];               // inside query range

        //update children
        // compute the min position in the left and right part of the interval
        int p1 = rsq(left(p) , L              , (L+R) / 2, i, j);
        int p2 = rsq(right(p), (L+R) / 2 + 1, R          , i, j);

        if (p1 == inf) return p2;   // if we try to access segment outside query
        if (p2 == inf) return p1;                               // same as above
        return Math.min(p1,p2);
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
        return st[p] = Math.min(p1,p2);
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
