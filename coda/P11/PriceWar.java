import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Use a segment tree.
 * Maintained values are the min price for a range.
 * Lazy values are s (the value a range is being set to),
 * d (the value a range is being decreased by),
 * and i (the value a range is being increased by).
 * s == -1 when there is no value being set.
 *
 * When you do a set, change s to the set value and
 * make i and d equal 0 for that range.
 *
 * When you do an update, check if s is nonnegative.
 * If so add the update value to s. If s becomes negative,
 * then set s to 0 and add the remainder to d.
 * If s is negative, then add the update value to i. If i
 * becomes negative, then set i to 0 and add the remainder to d.
 *
 * When you propogate, apply the set first (if it exists,
 * then the d value (making sure to keep the maintained value for
 * that range nonnegative) and then the i value.
 */
public class PriceWar {
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
				case "c":
					l = fr.nextInt()-1;
					r = fr.nextInt()-1;
					v = fr.nextInt();
					st.set(l, r, v);
                    break;
                case "q":
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
	int[] st;	// maintained values
	int[] s, i, d;	//lazy values
	int size;
	public SegmentTree(ArrayList<Integer> list){
		size = list.size();
		st = new int[4*size];
		s = new int[4*size];
		i = new int[4*size];
		d = new int[4*size];
		build(list, 0, size-1, 0);
	}
	private void build(ArrayList<Integer> list, int nl, int nr, int n){
		s[n] = -1;
		if(nl == nr){
			st[n] = list.get(nl);
		}else{
			int left = left(n);
			int right = right(n);
			int mid = (nl+nr)/2;
			build(list, nl, mid, left);
			build(list, mid+1, nr, right);
			st[n] = st[left] < st[right] ? st[left] : st[right];
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
	public int min(int l, int r){
		return min(0, 0, size - 1, l, r);
	}
	private int min(int n, int nl, int nr, int l, int r){
		prop(n, nl, nr);
		if(l == nl && r == nr) return st[n];
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			int a = min(left, nl, mid, l, mid);
			int b = min(right, mid+1, nr, mid+1, r);
			// Change next line if not RMQ
			return a < b ? a : b;
		}else if(r <= mid){
			return min(left, nl, mid, l, r);
		}else if(mid+1 <= l){
			return min(right, mid+1, nr, l, r);
		}
		return Integer.MIN_VALUE;
	}
	public void update(int l, int r, int value){
		update(0, 0, size-1, l, r, value);
	}
	private void update(int n, int nl, int nr, int l, int r, int value){
		if(l == nl && r == nr){
			if(s[n] > 0){
				if(s[n] + value >= 0){
					s[n] += value;
				}else{
					value += s[n];
					s[n] = 0;
					d[n]+=value;
				}
			}else{
				if(i[n] + value >= 0){
					i[n] += value;
				}else{
					value += i[n];
					i[n] = 0;
					d[n] += value;
				}
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
		int a = min(left, nl, mid, nl, mid);
		int b = min(right, mid+1, nr, mid+1, nr);
		st[n] = a < b ? a : b;
	}
	public void set(int l, int r, int value){
		set(0, 0, size-1, l, r, value);
	}
	private void set(int n, int nl, int nr, int l, int r, int value){
		if(l == nl && r == nr){
			s[n] = value;
			i[n] = 0;
			d[n] = 0;
			return;
		}
		prop(n, nl, nr);
		int mid = (nl+nr)/2;
		int left = left(n);
		int right = right(n);
		if(l <= mid && mid+1 <= r){
			set(left, nl, mid, l, mid, value);
			set(right, mid+1, nr, mid+1, r, value);
		}else if(r <= mid){
			set(left, nl, mid, l, r, value);
		}else if(mid+1 <= l){
			set(right, mid+1, nr, l, r, value);
		}
		int a = min(left, nl, mid, nl, mid);
		int b = min(right, mid+1, nr, mid+1, nr);
		st[n] = a < b ? a : b;
	}
	public void prop(int n, int nl, int nr){
		if(s[n] != -1){
			st[n] = s[n];
			if(nl != nr){
				s[left(n)] = s[n];
				s[right(n)] = s[n];
				d[left(n)] = 0;
				i[left(n)] = 0;
				d[right(n)] = 0;
				i[right(n)] = 0;
			}
			s[n] = -1;
		}
		st[n] += d[n];
		if(nl != nr){
			if(s[left(n)] > 0){
				if(s[left(n)] + d[n] >= 0){
					s[left(n)] += d[n];
				}else{
					d[left(n)]+=d[n]+s[left(n)];
					s[left(n)] = 0;
				}
			}else{
				if(i[left(n)] + d[n] >= 0){
					i[left(n)] += d[n];
				}else{
					d[left(n)] += d[n]+i[left(n)];
					i[left(n)] = 0;
				}
			}
			if(s[right(n)] > 0){
				if(s[right(n)] + d[n] >= 0){
					s[right(n)] += d[n];
				}else{
					d[right(n)]+=d[n]+s[right(n)];
					s[right(n)] = 0;
				}
			}else{
				if(i[right(n)] + d[n] >= 0){
					i[right(n)] += d[n];
				}else{
					d[right(n)] += d[n]+i[right(n)];
					i[right(n)] = 0;
				}
			}
		}
		d[n] = 0;
		st[n] = st[n] < 0 ? 0 : st[n];
		st[n] += i[n];
		if(nl != nr){
			if(s[left(n)] > 0) s[left(n)] += i[n];
			else i[left(n)] += i[n];
			if(s[right(n)] > 0) s[right(n)] += i[n];
			else i[right(n)] += i[n];
		}
		i[n] = 0;
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
