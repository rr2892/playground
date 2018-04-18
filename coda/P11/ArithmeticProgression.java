import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Use a segment tree.
 * Maintained value is the sum.
 * Lazy value is lx and ly (the x and y values such that
 * the sum for the range will be increased by x + x+y + ... x+(k-1)*y
 * where k is the range).
 *
 * Note that there's a difference between propogating to the left and
 * right children. When you update the left child, you can just add the
 * parent's x and y to the left child's lx and ly. For the right child,
 * right lx = (right lx) + x + (nr+nl)/2-nl+1)*y, and right ly += y.
 */

public class ArithmeticProgression {
	static final int MOD = 1_000_000_007;
	public static void main(String[] args) {
		FastReader fr = new FastReader();
		StringBuilder sb = new StringBuilder();
		int n = fr.nextInt();
        int q = fr.nextInt();
        ArrayList<Integer> in = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            in.add(0);
        }
        SegmentTree st = new SegmentTree(in);
        for(int t = 0; t < q; t++){
			String s = fr.next();
			int l, r = 0;
			long x, y = 0;
            switch (s) {
                case "a":
                    l = fr.nextInt()-1;
                    r = fr.nextInt()-1;
					x = fr.nextLong();
					y = fr.nextLong();
                    st.update(l, r, x, y);
                    break;
                case "s":
					l = fr.nextInt()-1;
					r = fr.nextInt()-1;
					long sum = st.sum(l, r);
					sum = (sum % MOD + MOD) % MOD;
					sb.append(sum + "\n");
					break;
                default:
                    break;
            }
		}
		System.out.print(sb);
	}
}

/**
 * Segment tree with range updates and lazy propogation.
 */
class SegmentTree{
	long[] st;	//maintained values
	long[] lx, ly;	//lazy values
	int size;
	public SegmentTree(ArrayList<Integer> list){
		size = list.size();
		st = new long[4*size];
		lx = new long[4*size];
		ly = new long[4*size];
		build(list, 0, size-1, 0);
	}
	private void build(ArrayList<Integer> list, int nl, int nr, int n){
		if(nl == nr){
			st[n] = list.get(nl);
		}else{
			int left = left(n);
			int right = right(n);
			int mid = (nl+nr)/2;
			build(list, nl, mid, left);
			build(list, mid+1, nr, right);
			st[n] = st[left] + st[right];
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
		if(l == nl && r == nr) return st[n];
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			long a = sum(left, nl, mid, l, mid);
			long b = sum(right, mid+1, nr, mid+1, r);
			return a + b;
		}else if(r <= mid){
			return sum(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return sum(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}
	public void update(int l, int r, long x, long y){
		update(0, 0, size-1, l, r, x, y);
	}
	private void update(int n, int nl, int nr, int l, int r, long x, long y){
		if(l == nl && r == nr){
			lx[n] += x;
			ly[n] += y;
			return;
		}
		prop(n, nl, nr);
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		int k = r-l+1;
		if(l <= mid && mid+1 <= r){
			update(left, nl, mid, l, mid, x, y);
			update(right, mid+1, nr, mid+1, r, x+(mid-l+1)*y, y);
		}else if(r <= mid){
			update(left, nl, mid, l, r, x, y);
		}else if(mid+1 <= l){
			update(right, mid+1, nr, l, r, x, y);
		}
		long a = sum(left, nl, mid, nl, mid);
		long b = sum(right, mid+1, nr, mid+1, nr);
		st[n] = a + b;
	}
	public void prop(int n, int nl, int nr){
		int k = nr-nl+1;
		if(lx[n] != 0 || ly[n] != 0){
			st[n] += ((2*lx[n]+(k-1)*ly[n])*k)/2;
			if(nl != nr){
				lx[left(n)] += lx[n];
				ly[left(n)] += ly[n];
				lx[right(n)] += lx[n]+((nl+nr)/2-nl+1)*ly[n];
				ly[right(n)] += ly[n];
			}
			lx[n] = 0;
			ly[n] = 0;
		}
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

//source: http://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
