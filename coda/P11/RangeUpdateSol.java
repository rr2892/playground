import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Use a segment tree.
 * Maintained values are min, max, and sum.
 * Lazy values are the update value being added to a range,
 * and a boolean flag representing negation.
 * When we propogate the update for a range, we just add
 * the value to the min, max, and sum for that range.
 * When we propogate the negation, we make sum = -sum,
 * min = -max, and max = -min.
 */
 public class RangeUpdateSol {

	public static void main(String[] args) {
		FastReader fr = new FastReader();
		StringBuilder sb = new StringBuilder();
		int n = fr.nextInt();
        int q = fr.nextInt();
        ArrayList<Integer> in = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            in.add(fr.nextInt());
        }
        SegmentTree st = new SegmentTree(in);
        for(int t = 0; t < q; t++){
			String s = fr.next();
			int l, r, v = 0;
            switch (s) {
                case "a":
                    l = fr.nextInt()-1;
                    r = fr.nextInt()-1;
                    v = fr.nextInt();
                    if(v == 0) break;
                    st.update(l, r, v);
                    break;
				case "n":
					l = fr.nextInt()-1;
					r = fr.nextInt()-1;
					st.update(l, r, 0);
                    break;
				case "s":
					l = fr.nextInt()-1;
					r = fr.nextInt()-1;
					sb.append(st.sum(l, r) + "\n");
                    break;
                case "m":
					l = fr.nextInt()-1;
					r = fr.nextInt()-1;
					sb.append(st.min(l, r) + "\n");
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
	long[] minTree, maxTree, sumTree; // Maintained values
	long[] update; boolean[] neg;	// Lazy values
	int size;
	public SegmentTree(ArrayList<Integer> list){
		size = list.size();
		sumTree = new long[4*size];
		minTree = new long[4*size];
		maxTree = new long[4*size];
		update = new long[4*size];
		neg = new boolean[4*size];
		build(list, 0, size-1, 0);
	}
	private void build(ArrayList<Integer> list, int nl, int nr, int n){
		if(nl == nr){
			sumTree[n] = minTree[n] = maxTree[n] = list.get(nl);
		}else{
			int left = left(n);
			int right = right(n);
			int mid = (nl+nr)/2;
			build(list, nl, mid, left);
			build(list, mid+1, nr, right);
			sumTree[n] = sumTree[left] + sumTree[right];
			minTree[n] = minTree[left] < minTree[right] ? minTree[left] : minTree[right];
			maxTree[n] = maxTree[left] > maxTree[right] ? maxTree[left] : maxTree[right];
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
			return a + b;
		}else if(r <= mid){
			return sum(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return sum(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}
	// l and r are inclusive.
	public long min(int l, int r){
		return min(0, 0, size - 1, l, r);
	}
	private long min(int n, int nl, int nr, int l, int r){
		prop(n, nl, nr);
		if(l == nl && r == nr) return minTree[n];
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			long a = min(left, nl, mid, l, mid);
			long b = min(right, mid+1, nr, mid+1, r);
			// Change next line if not RMQ
			return a < b ? a : b;
		}else if(r <= mid){
			return min(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return min(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}
	// l and r are inclusive.
	public long max(int l, int r){
		return max(0, 0, size - 1, l, r);
	}
	private long max(int n, int nl, int nr, int l, int r){
		prop(n, nl, nr);
		if(l == nl && r == nr) return maxTree[n];
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			long a = max(left, nl, mid, l, mid);
			long b = max(right, mid+1, nr, mid+1, r);
			// Change next line if not RMQ
			return a > b ? a : b;
		}else if(r <= mid){
			return max(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return max(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}
	public void update(int l, int r, int value){
		update(0, 0, size-1, l, r, value);
	}
	private void update(int n, int nl, int nr, int l, int r, int value){
		if(l == nl && r == nr){
			if(value == 0) {
				neg[n] = !neg[n];
				update[n] = -update[n];
			}else{
				update[n]+=value;
			}
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
		sumTree[n] = a + b;
		a = min(left, nl, mid, nl, mid);
		b = min(right, mid+1, nr, mid+1, nr);
		minTree[n] = a < b ? a : b;
		a = max(left, nl, mid, nl, mid);
		b = max(right, mid+1, nr, mid+1, nr);
		maxTree[n] = a > b ? a : b;
	}
	public void prop(int n, int nl, int nr){
		if(neg[n]){
			sumTree[n] = -sumTree[n];
			minTree[n] ^= maxTree[n];
			maxTree[n] ^= minTree[n];
			minTree[n] ^= maxTree[n];
			minTree[n] = -minTree[n];
			maxTree[n] = -maxTree[n];
			if(nl != nr){
				neg[left(n)] = !neg[left(n)];
				update[left(n)] = -update[left(n)];
				neg[right(n)] = !neg[right(n)];
				update[right(n)] = -update[right(n)];
			}
			neg[n] = false;
		}
		if(update[n] != 0){
			sumTree[n] += (nr-nl+1)*update[n];
			minTree[n] += update[n];
			maxTree[n] += update[n];
			if(nl != nr){
				update[left(n)] += update[n];
				update[right(n)] += update[n];
			}
			update[n] = 0;
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
