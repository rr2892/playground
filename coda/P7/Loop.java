import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

/**
 * Idea: DFS from the vertices, finding all loops.
 * Try all points as the starting point in the loop.
 * Use this point as the top left point in the loop.
 * That means that there will be no points in the loop that are
 * higher or farther to the left as this point.
 * Make the first edge from the starting loop always go to the right.
 * After finding a loop, check if it's valid by meeting constraints.
 * Find the longest valid loop.
 *
 * This is not efficient enough for the extra subtask.
 */

public class Loop {
    static int n;
    static int[][] input;
    // Holds the direction of the next vertex in our dfs.
    static int[] next;
    // We map squares (x, y) to indices, i = x*n+y.
    // This is so that in our graph, there is an edge
    // a to b if the squares a and b share an edge.
    static ArrayList<ArrayList<Integer>> graph;
    // The start node for the dfs.
    // A loop is formed when the dfs returns to this node.
    static int start;
    public static void main(String[] args) {
		FastReader fr = new FastReader();
        n = fr.nextInt();
        input = new int[n][n];
        for(int i = 0; i < n; i++){
            String line = fr.next();
            for(int j = 0; j < n; j++){
                // Save '-' as 4.
                if(line.charAt(j) == '-') input[i][j] = 4;
                else input[i][j] = line.charAt(j) - '0';
            }
        }
        graph = new ArrayList<>();
        // iterate over the vertices of the grid.
        for(int i = 0; i < (n+1)*(n+1); i++){
            ArrayList<Integer> al = new ArrayList<>(4);
            int x = i/(n+1);
            int y = i%(n+1);
            // For every square in the grid, edges are added
            // in the order: up, down, left, right.
            // If the square is on an edge of the grid,
            // so that there is no square in a direction,
            // then null is added to the graph.

            // up
            if(x>0) al.add((x-1)*(n+1)+y);
            else al.add(null);
            // down
            if(x<n) al.add((x+1)*(n+1)+y);
            else al.add(null);
            // left
            if(y>0) al.add(x*(n+1)+y-1);
            else al.add(null);
            // right
            if(y<n) al.add(x*(n+1)+y+1);
            else al.add(null);
            graph.add(al);
        }
        next = new int[(n+1)*(n+1)];
        int max = -1;
        // Run dfs from every vertex in the grid.
        // Make this starting vertex the top left vertex of the loop.
        for(int i = 0; i < (n+1)*(n+1); i++){
            start = i;
            // Have the first edge from that vertex always go right.
            Integer v = graph.get(i).get(3);
            if(v == null) continue;
            next[i] = 4;
            int cur = dfs(v, 1);
            if(cur > max) max = cur;
            next[i] = 0;
        }
        System.out.println(max);
    }
    static int dfs(int u, int d){
        int max = -1;
        // Go in every direction (up, down, left, right)
        for(int ii = 0; ii < 4; ii++){
            Integer v = graph.get(u).get(ii);
            if(v == null) continue;
            if(v == start){
                // handles the case where the current vertex
                // is immediately after the starting vertex.
                if(next[v] == (ii % 2 == 0 ? ii+2 : ii)){
                    continue;
                }else if(d+1 > max){
                    next[u] = ii+1;
                    boolean valid = true;
                    // For every square in the grid, count the number of
                    // its edges that are in the loop. If this doesn't
                    // match the input, then the current loop is invalid.
                    for(int i = 0; i < n && valid; i++){
                        for(int j = 0; j < n && valid; j++){
                            if(input[i][j] < 4){
                                int sum = 0;
                                // AHH
                                int ul = i*(n+1)+j;
                                int ur = i*(n+1)+j+1;
                                int bl = (i+1)*(n+1)+j;
                                int br = (i+1)*(n+1)+j+1;
                                if(next[ul] == 4 || next[ur] == 3) sum++;
                                if(next[ul] == 2 || next[bl] == 1) sum++;
                                if(next[bl] == 4 || next[br] == 3) sum++;
                                if(next[ur] == 2 || next[br] == 1) sum++;
                                if(sum != input[i][j]) valid = false;
                            }
                        }
                    }
                    if(valid) max = d+1;
                }
            }else if(next[v] > 0) continue; // Don't close a loop unless you reach the start.
            // Don't go too far up or to the left.
            else if(v/(n+1) < start/(n+1) || (v/(n+1) == start/(n+1) && v%(n+1) < start%(n+1))) continue;
            else {
                next[u] = ii+1;
                int r = dfs(v, d+1);
                if(r > max) max = r;
            }
        }
        next[u] = 0;
        return max;
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
