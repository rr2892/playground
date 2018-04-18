import java.math.*;
import java.io.*;
import java.util.*;

public class EscapeFire {
    /*
     * We can represent the board as a graph, with cells representing nodes and there are
     * always edges between neighboring cells.
     * To find the minimum time john needs we simply need to find the shortest path to any
     * of the helicopters.
     * First we will need to know when a cell catches on fire.
     * To find out when a cell catches on fire we can do another bfs where all of the sources are
     * the starting locations of the fires. Once we reach a cell we record the time it takes to reach
     * every cell, which is the same as the depth from the closest source.
     * We can simulate this with a bfs though the graph will some additions. We just need to make
     * sure to ignore nodes with are already on fire and also make sure to consider any helicopter
     * in the graph.
     * Runtime: O(n*m). Each cell can have at most 4 edges so the runtime is dependant on the size
     * of the grid.
     */
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        boolean[][] seen = new boolean[n][m];
        int[][] time = new int[n][m];
        char[][] board = new char[n][m];
        for( int i = 0; i < n; i++)
            board[i] = br.readLine().toCharArray();
        Node start = null;

        Queue<Node> q = new LinkedList<>();
        for( int i = 0; i < n; i++){
            for( int j = 0; j < m; j++){
                if(board[i][j] == 'F'){
                    q.add(new Node(i,j,0));
                }
                else if(board[i][j] == 'J'){
                    start = new Node(i,j,0);
                }

            }
        }
        seen = new boolean[n][m];
        int inf = 100_000_000;
        for( int i = 0; i < n; i++){
            for( int j = 0; j < m; j++)
                time[i][j] = inf;
        }
        //BFS the grid to find out when all the cells catch on fire
        while(!q.isEmpty()){
            Node node = q.remove();
            int x = node.i;
            int y = node.j;
            int t = node.t;
            if(seen[x][y])continue;
            seen[x][y] = true;
            if(board[x][y] == '#')continue;
            time[x][y] = t;
            if(x-1 >=0)
                q.add(new Node(x-1,y,t+1));
            if(x+1 < n)
                q.add(new Node(x+1, y, t+1));
            if(y-1 >=0)
                q.add(new Node(x,y-1, t+1));
            if(y +1 < m)
                q.add(new Node(x,y+1,t+1));
        }
        q.add(start);
        int ans = -1;
        seen = new boolean[n][m];
        //BFS to find the closest helicopter John can go to.
        while(!q.isEmpty()){
            Node node = q.remove();
            int x = node.i;
            int y = node.j;
            int t = node.t;
            if(seen[x][y])continue;
            seen[x][y] = true;
            // make sure the cell is not on fire already
            if(board[x][y] == '#' || t >= time[x][y])continue;
            if(board[x][y] == 'H'){
                ans = t;
                break;
            }
            if(x -1 >=0)
                q.add(new Node(x-1, y, t+1));
            if(x+1 < n)
                q.add(new Node(x+1, y, t+1));
            if(y -1 >= 0)
                q.add(new Node(x,y-1, t+1));
            if(y+1 < m)
                q.add(new Node(x,y+1, t+1));
        }
        System.out.println(ans);
    }
}
class Node{
    int i;
    int j;
    int t;
    public Node(int i , int j, int t){
        this.i = i;
        this.j = j;
        this.t = t;
    }
}
