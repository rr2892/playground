import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Idea: Ternary Search on the time (as mentioned in the slides).
 * That is, choose a min time, lo = 0 and a max time, hi = 1e9.
 * Choose 2 mid times, m1 = lo + (h1-lo)/3, m2 = lo + (hi-lo)/3
 * Find how close the cars are during these two times.
 * Use the relative result to reduce the search space.
 */
public class ClosestMomentSol {
    // Inputs
    static int[] x;
    static int[] v;
	public static void main(String[] args) {
		FastReader fr = new FastReader();
        int n = fr.nextInt();
        x = new int[n];
        v = new int[n];
        for(int i = 0; i < n; i++){
            x[i] = fr.nextInt();
            v[i] = fr.nextInt();
        }
        // Ternary Seach
        double lo = 0;
        double hi = 1e9;
        for(int i = 0; i < 100; i++){
            double m1 = lo + (hi-lo)/3;
            double m2 = m1 + (hi-lo)/3;
            double d1 = f(m1);
            double d2 = f(m2);
            if(d1 > d2){
                lo = m1;
            }else if(d1 < d2){
                hi = m2;
            }else{
                lo = m1;
                hi = m2;
            }
        }
        System.out.println(f(lo));
    }
    // For a given time t, find the leftmost and rightmost car
    // and calculate their distance from one another.
    static double f(double t){
        int n = x.length;
        // Arbitrary base min and max values.
        // Set higher if needed.
        double lo = 1e6;
        double hi = -1e6;
        for(int i = 0; i < n; i++){
            double p = x[i]+v[i]*t;
            lo = p < lo ? p : lo;
            hi = p > hi ? p : hi;
        }
        return hi-lo;
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
