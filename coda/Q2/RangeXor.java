import java.util.*;
import java.io.*;

public class RangeXor {
  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), q = fr.nextInt();
    ArrayList<Integer> A = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      A.add(fr.nextInt());
    }

    SegmentTree st = new SegmentTree(A);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < q; i++) {
      String instr = fr.next();
      int l = fr.nextInt() - 1, r = fr.nextInt() - 1;
      if (instr.equals("q")) {
        sb.append(String.format("%d\n", st.sum(l, r)));
      } else {
        int v = fr.nextInt();
        st.update(l, r, v);
      }
    }

    System.out.print(sb.toString());

  }
}

/**
 * Segment tree with range updates and lazy propogation.
 * Sourced from problem set 11 solution.
 */
class SegmentTree{
	long[] sumTree; // Maintained values
	long[] update;	// Lazy values
	int size;
	public SegmentTree(ArrayList<Integer> list){
		size = list.size();
		sumTree = new long[4*size];
		update = new long[4*size];
		build(list, 0, size-1, 0);
	}
	private void build(ArrayList<Integer> list, int nl, int nr, int n){
		if(nl == nr){
			sumTree[n] = list.get(nl);
		}else{
			int left = left(n);
			int right = right(n);
			int mid = (nl+nr)/2;
			build(list, nl, mid, left);
			build(list, mid+1, nr, right);
			sumTree[n] = sumTree[left] ^ sumTree[right];
		}
	}
	/**
	 * Get left, right childs
	 */
	private int left(int n){
		return 2*n+1;
	}
	private int right(int n){
		return 2*n+2;
	}
	// l and r are inclusive.
	public long sum(int l, int r){
		return sum(0, 0, size - 1, l, r);
	}
	private long sum(int n, int nl, int nr, int l, int r){
		prop(n, nl, nr);
		if(l == nl && r == nr) return sumTree[n];
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			long a = sum(left, nl, mid, l, mid);
			long b = sum(right, mid+1, nr, mid+1, r);
			// Change next line if not RMQ
			return a ^ b;
		}else if(r <= mid){
			return sum(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return sum(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}

	public void update(int l, int r, int value){
		update(0, 0, size-1, l, r, value);
	}

	private void update(int n, int nl, int nr, int l, int r, int value){
		if(l == nl && r == nr){
      update[n] = update[n] ^ value;
			return;
		}
		prop(n, nl, nr);
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			update(left, nl, mid, l, mid, value);
			update(right, mid+1, nr, mid+1, r, value);
		}else if(r <= mid){
			update(left, nl, mid, l, r, value);
		}else if(mid+1 <= l){
			update(right, mid+1, nr, l, r, value);
		}
		long a = sum(left, nl, mid, nl, mid);
		long b = sum(right, mid+1, nr, mid+1, nr);
		sumTree[n] = a ^ b;
	}

	public void prop(int n, int nl, int nr){
		if(update[n] != 0){
			if ((nr-nl+1) % 2 == 1) {
              sumTree[n] ^= update[n];
      		}

			if(nl != nr){
				update[left(n)] ^= update[n];
				update[right(n)] ^= update[n];
			}
			update[n] = 0;
		}
	}
}

//source: http://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/

//
// //Segment Tree code from CP3
// //Updated by Ronald Rojas
// //Usage: Find Range Sum Queries and Update ranges in Log(n) time.
// class SegmentTree {         // the segment tree is stored like a heap array
//     private int[] st, A;
//     private int n;
//     private int left (int p) { return p << 1; } // same as binary heap operations
//     private int right(int p) { return (p << 1) + 1; }
//
//     private void build(int p, int L, int R) {
//         if (L == R)                            // as L == R, either one is fine
//             st[p] = A[L];                                         // store the index
//         else {                                // recursively compute the values
//             build(left(p) , L              , (L + R) / 2);
//             build(right(p), (L + R) / 2 + 1, R          );
//             int p1 = st[left(p)], p2 = st[right(p)];
//             st[p] = p1 ^ p2;
//         }
//     }
//
//     private int rsq(int p, int L, int R, int i, int j) {          // O(log n)
//         if (i >  R || j <  L) return -1; // current segment outside query range
//         if (L >= i && R <= j) return st[p];               // inside query range
//
//         //update children
//         // compute the min position in the left and right part of the interval
//         int p1 = rsq(left(p) , L              , (L+R) / 2, i, j);
//         int p2 = rsq(right(p), (L+R) / 2 + 1, R          , i, j);
//
//         if (p1 == -1) return p2;   // if we try to access segment outside query
//         if (p2 == -1) return p1;                               // same as above
//         return p1 ^ p2;
//     }
//
//     private int update(int p, int L, int R, int idx, int idz,int new_value) {
//         int i = idx, j = idz;
//
//         // if the current interval does not intersect
//         // the update interval, return this st node value!
//         if (i > R || j < L)
//             return st[p];
//
//         // if the current interval is included in the update range,
//         // update that st[node]
//
// 	      if(i == R && j ==L)
// 		      return st[p] = st[p] ^ new_value;
//
//         //update children
//
//         // compute the range in the
//         // left and right part of the interval
//         int p1, p2;
//         int M = (L + R) / 2;
//         p1 = update(left(p), L, M, idx, idz, new_value);
//         p2 = update(right(p), M+1, R, idx, idz, new_value);
//
//         // return the sum
//         return st[p] = p1 ^ p2;
//     }
//
//     //Update Children to correct values
//     private void prop(int p, int L, int R){
//         int r = (R-L) +1;
//         int lr =(L+R)/2; //left range
//         lr-=L;
//         lr++;
//         int rr = R; //right range
//         rr-=((L+R)/2 +1);
//         rr++;
//
//         int left = left(p);
//         int right = right(p);
//         if(st[p] == 0) {
//             st[left] = 0;
//             st[right] = 0;
//         }else if(st[p] == r){
//             st[left] = lr;
//             st[right] = rr;
//
//         }
//     }
//     public SegmentTree(int[] _A) {
//         A = _A; n = A.length;                   // copy content for local usage
//         st = new int[4 * n];
//         for (int i = 0; i < 4 * n; i++) st[i] = 0; // create vector with length `len' and fill it with zeroes
//         build(1, 0, n - 1);                                  // recursive build
//     }
//
//     public int rsq(int i, int j) { return rsq(1, 0, n - 1, i, j); } // overloading
//
//     public int update_range(int idx, int idz, int new_value){
//         return update(1, 0, n-1, idx,  idz, new_value);}
//
//     public int update_point(int idx, int new_value) {
//         return update(1, 0, n - 1, idx,idx, new_value); }
//
// }



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
