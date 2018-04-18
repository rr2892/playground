import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
/**
 * Idea: Use a bitmask to represent the rows.
 * For every possible combination of rows, flip those rows
 * and then greedily flip columns until the goal is reached or
 * the goal is deemed unreachable.
 */
public class Bulb2Sol {
	public static void main(String[] args) {
		FastReader fr = new FastReader();
		int n = fr.nextInt();
		int m = fr.nextInt();
		int o = fr.nextInt();
		ArrayList<Pair> input = new ArrayList<>();
		for(int i = 0; i < n; i++){
			String line = fr.next();
			for(int j = 0; j < m; j++){
				// Saving the points (x, y) which are on in the input.
				// Saving all the points in a 2-d boolean array should also work.
				if(line.charAt(j) == '*') input.add(new Pair(i, j));
			}
		}
		int INF = 1_000_000_000;
		int minCount = INF;
		for(int mask = 0; mask < (1<<n); mask++){
            boolean[][] on = new boolean[n][m];
            // rebuild the input
            for(Pair p : input) on[p.x][p.y] = true;
            // Number of rows/cols that have been flipped.
            int count = 0;
            // Number of on bulbs at the moment.
            int sum = 0;
            // Flip the rows who's indexes are 1 in the bitmask
			for(int i = 0; i < n; i++){
				if((mask & (1<<i)) > 0){
					count++;
					for(int j = 0; j < m; j++){
						on[i][j] = !on[i][j];
					}
				}
            }
            // Number of on bits in each column
			int[] colSum = new int[m];
			for(int i = 0; i < m; i++){
				for(int j = 0; j < n; j++){
					if(on[j][i]) colSum[i]++;
				}
				sum+=colSum[i];
            }
            // You don't have to know which columns have which sum.
            // Sort to be able to flip the columns with the most on bits first.
            Arrays.sort(colSum);
            // Try flipping rows from largest number of on bulbs to the smallest.
            // Loop will exit when the number of on bulbs is less/equal to the goal.
			for(int i = m-1; i >= 0 && sum > o; i--){
				sum-=(colSum[i]-(n-colSum[i]));
				count++;
			}
			if(sum <= o && count < minCount) minCount = count;
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
