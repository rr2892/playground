import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Idea: Find the recursive formula and then solve using matrix exponentiation.
 * Formula: f(n) = 2*f(n-4) + 4*f(n-3) + f(n-2) + f(n-1) for n > 4.
 * Matrix: {{0, 1, 0, 0},
 *          {0, 0, 1, 0},
 *          {0, 0, 0, 1},
 *          {2, 4, 1, 1}}
 * Base cases: f(1) = 1, f(2) = 2, f(3) = 7, f(4) = 15.
 */
public class BlockSol {
    static final int MOD = 1_000_000_000 + 7;
	public static void main(String[] args) {
		FastReader fr = new FastReader();
        long n = fr.nextLong();
        // Recurrence matrix
        long[][] m = {{0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {2, 4, 1, 1}};
        // base cases
        long[] base = {1, 2, 7, 15};
        if(n < 5){
            System.out.println(base[(int)n-1]);
        }else{
            n-=4;
            // Identity matrix
            long[][] ans = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
            // Calculate m^(n-4)
            while(n > 0){
                if((n & 1) > 0){
                    ans = matMult(ans, m);
                }
                n >>= 1;
                m = matMult(m, m);
            }
            // Calculate m^(n-4) * base
            long[] ans2 = abMult(ans, base);
            System.out.println(ans2[3]);
        }
    }
    // Calculate A*B where A and B are n by n matrices.
    // All values are modded the given MOD.
    static long[][] matMult (long[][] a, long[][] b){
        int n = a.length;
        long[][] ans = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    ans[i][j]+=a[i][k]*b[k][j];
                }
                ans[i][j]%=MOD;
            }
        }
        return ans;
    }
    // Calcualte A*b where A is an n by n matrix and b is a n by 1 vector.
    // All values are modded by the given MOD.
    static long[] abMult(long[][] a, long[] b){
        int n = a.length;
        long[] ans = new long[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                ans[i]+=a[i][j]*b[j];
            }
            ans[i]%=MOD;
        }
        return ans;
    }
}

// Fast IO
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
