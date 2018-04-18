import java.lang.*;
import java.io.*;
import java.math.*;
import java.util.*;

/**
 * Idea: Try every possible way of pressing buttons in the first row, using a bitmask.
 * Then for each row i, i from 1 to n-1, there is a unique selection of buttons in that row
 * that can be pressed to turn off the bulbs in row i-1.
 * After pressing the buttons in row n-1, if there are any bulbs still on in row n-1,
 * then the configuration for row 0 was invalid.
 */

public class BulbSol {
	public static void main(String[] args) {
		FastReader fr = new FastReader();
        int n = fr.nextInt();
        int m = fr.nextInt();
		ArrayList<Pair> input = new ArrayList<>();
		for(int i = 0; i < n; i++){
			String line = fr.next();
			for(int j = 0; j < m; j++){
				// Saving the points (x, y) which are on in the input.
				// Saving all the points in a 2-d boolean array would also work.
				if(line.charAt(j)=='*') input.add(new Pair(i, j));
			}
		}
		int INF = 1_000_000_000;
		int minCount = INF;
		for(int mask = 0; mask < (1<<m); mask++){
			boolean[][] on = new boolean[n][m];
			// Track the number of buttons pressed.
			int count = 0;
			// rebuild the input
			for(Pair p : input){
				on[p.x][p.y] = true;
			}
			// Use the given mask to press the buttons in the first row
			// and flip the bulbs as needed.
			for(int i = 0; i < m; i++){
				if((mask & (1 << i)) > 0){
					count++;
					if(i > 0) on[0][i-1] = !on[0][i-1];
					on[0][i] = !on[0][i];
					if(n > 1) on[1][i] = !on[1][i];
					if(i < m-1) on[0][i+1] = !on[0][i+1];
				}
			}
			// Use the next row of buttons to flip off the bulbs in the current row.
			for(int j = 0; j < n-1; j++){
				for(int k = 0; k < m; k++){
					if(on[j][k]){
						count++;
						on[j][k] = !on[j][k];
						on[j+1][k] = !on[j+1][k];
						if(j < n-2) on[j+2][k] = !on[j+2][k];
						if(k > 0) on[j+1][k-1] = !on[j+1][k-1];
						if(k < m-1) on[j+1][k+1] = !on[j+1][k+1];
					}
				}
			}
			// check if the last row is all off.
			boolean b = true;
			for(int j = 0; j < m; j++){
				if(on[n-1][j]) b = false;
			}
			if(b) minCount = (count < minCount) ? count : minCount;
		}
		if(minCount < INF) System.out.println(minCount);
		else System.out.println(-1);
	}
}

class Pair{
	int x, y;
	public Pair(int _x, int _y){
		this.x = _x;
		this.y = _y;
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
