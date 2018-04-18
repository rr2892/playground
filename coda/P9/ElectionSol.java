import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Idea: Binary Search on the number of votes
 * needed to be bought to win the election.
 * Set a min and max number of votes (lo and hi).
 * Guess a number of votes ((lo+hi)/2) and add that to John's total.
 * For every candidate whose number of votes is greater than
 * John's new total, sum the difference between that candidate and John + 1.
 * If the sum is larger than your guess, then your guess was too small,
 * set lo to your guess. Otherwise your guess worked and set hi to your guess.
 */

public class ElectionSol {
	public static void main(String[] args) {
		FastReader fr = new FastReader();
        int n = fr.nextInt();
        long[] v = new long[n];
        for(int i = 0; i < n; i++){
            v[i] = fr.nextLong();
		}
		// It's possible that John needs 0 votes to win.
		// Since my code will return hi, and hi will always be at least 1 greater
		// than lo, we set lo to -1 initially.
		long lo = -1;
		// There's up to 1000*10^9 votes in play,
		// so set hi greater than that.
        long hi = 1_000_000_000_000_000L;
        while(hi - lo > 1){
            long mid = (hi+lo)/2;
			long votes = mid;
			// The number of votes that John has.
            long jo = mid+v[0];
            for(int i = 1; i < n; i++){
                if(v[i] >= jo) votes-=v[i]-jo+1;
            }
            if(votes >= 0) hi = mid;
            else lo = mid;
        }
        System.out.println(hi);
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
