import java.math.*;
import java.io.*;
import java.util.*;

/*
 * Idea:
 * There are only a few special water levels that matter, the up to 10^5 levels that
 * are quieried in the question. We then therefore read all the queries and sort them
 * from heightest to lowest then simulate the water falling from infinity to Q[1] to Q[2] .. Q[n]
 * for any querie in our list of queries 'Q'.
 * To simulate the water falling from Q[i] to Q[i+1] we can simulate this using union-find.
 * If we have a set 'S' that tells you which cells are part of the same islands we can
 * iterate through every cell that has just emerged from the water and union it with
 * every other neighboring cell.
 * To find the next cell the has emerged we can sort the cells from highest through smallest
 * and iterate through that list.
 * We can save all of these answers to output them later.
 */
public class Ronald{
    static int set[];
    static int sets = 0; // 'sets' keep track of how many sets we have
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] in = br.readLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        int q = Integer.parseInt(in[2]);
        ArrayList<Node> list = new ArrayList<>();
        int size = n*m;
        set = new int[size];
        boolean[] used = new boolean[size];
        //init union find
        for( int i = 0; i < size; i++){
            set[i] = i;
        }
        for( int i = 0; i < n; i++){
            in = br.readLine().split(" ");
            for( int j = 0; j < m; j ++){
                //read board
                list.add(new Node(i,j, (i*m) + j ,Integer.parseInt(in[j])));
            }
        }
        //sort board from highest cell to lowest cell
        Collections.sort(list, Collections.reverseOrder());
        ArrayList<Integer> Q = new ArrayList<>();
        ArrayList<Integer> A = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for( int i = 0; i < q; i++){
            int a = Integer.parseInt(br.readLine());
            Q.add(a);
            A.add(a);
        }
        //sort queries from highest water level to lowest water level
        Collections.sort(Q, Collections.reverseOrder());
        int index = 0;
        for( int i = 0; i < q; i++){
            int h = Q.get(i);
            while(index < list.size()){
                Node node = list.get(index);
                if(node.h <= h)break;
                //this is a newly emerged cell so we have one more island
                used[node.i] = true;
                index++;
                sets++;
                // try to union the newly emerged cell with its neighboring cells
                // if the neighbors are also above land.
                if(node.x +1 != n && used[node.i + m]) union(node.i, node.i + m);
                if(node.x != 0 && used[node.i - m]) union(node.i, node.i -m);
                if(node.y +1 != m && used[node.i + 1]) union(node.i , node.i + 1);
                if(node.y != 0 && used[node.i - 1]) union(node.i, node.i -1);
            }
            //save answer
            map.put(h, sets);
        }
        StringBuilder out = new StringBuilder();
        //write the answer to all queries
        for( int i = 0; i < q; i++){
            int a = A.get(i);
            out.append(map.get(a)).append('\n');
        }
        System.out.print(out.toString());
    }
    public static void union(int s1, int s2) {
        int a = find(s1);
        int b = find(s2);
        // if they are not connected then we need to combine these island and
        // will have one less island.
        if(a != b)sets--;
        set[Math.min(a, b)] = Math.max(a, b);
    }
    public static int find(int index) {
        if (set[index] == index)return index;
        return set[index] = find(set[index]);
    }


}

class Node implements Comparable<Node>{
    int x;
    int y;
    int i;
    int h;
    public Node( int x, int y, int i, int h){
        this.x = x;
        this.y = y;
        this.i = i;
        this.h = h;
    }
    public int compareTo(Node that){
        return this.h - that.h;
    }
}
