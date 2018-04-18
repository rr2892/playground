import java.math.*;
import java.io.*;
import java.util.*;

public class TimeMachine {

    /* To find out if the answers are -infinity or infinity we simply need to check
     * for a negative cycle or positive cycle respectively.
     * We can use Bellman-Ford to find the minimum and maximum possible times and whether
     * there are negative or positive cycle.
     * Finding the minimum and negative cycles is just applying the Bellman-Ford algorithm.
     * To find the maximum and positive cycle you may just negate all the edge weights.
     * Runtime: O(n*m)
     */
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        ArrayList<Integer>[] g = new ArrayList[n];
        ArrayList<Integer>[] e = new ArrayList[n];
        for( int i = 0; i < n; i++){
            g[i] = new ArrayList<>();
            e[i] = new ArrayList<>();
        }
        for( int i = 0; i < m; i++){
            in = br.readLine().split(" ");
            int a = Integer.parseInt(in[0]);
            int b = Integer.parseInt(in[1]);
            a--;
            b--;
            int c = Integer.parseInt(in[2]);
            g[a].add(b);
            e[a].add(c);
        }
        long[] min = new long[n];
        long[] max = new long[n];
        long inf = (long)10 * Integer.MAX_VALUE;
        for( int i = 0; i < n; i++){
            max[i] = inf;
            min[i] = inf;
        }
        max[0] = 0;
        min[0] = 0;
        for( int i = 0; i < n; i++){
            for( int j = 0; j < n; j++){
                for( int k = 0; k < g[j].size(); k++){
                    long w = e[j].get(k);
                    int to = g[j].get(k);
                    //regular bellman-ford minimum distance
                    if(min[to] > min[j] + w && min[j] != inf){
                        min[to] = min[j] +w;
                    }
                    //bellman-ford for maximum distance
                    if(max[to] > max[j] -w && max[j] != inf){
                        max[to] = max[j] -w;
                    }
                }
            }
        }
        boolean neg = false;
        boolean pos = false;
        long ansMax = 0;
        long ansMin = 0;
        for(int j =0; j < n; j++){
            if(ansMax > max[j])
                ansMax =max[j];
            if(ansMin > min[j])
                ansMin = min[j];
            for( int k = 0; k < g[j].size();k++){
                long w = e[j].get(k);
                int to = g[j].get(k);
                if(min[to] > min[j] + w && min[j] < inf){
                    neg = true;
                }
                if(max[to] > max[j] -w  && max[j] < inf){
                    pos = true;
                }
            }
        }
        System.out.println("" + ( neg ? "-infinity" : ansMin) + " " + (pos ? "infinity" : -ansMax));
    }
}
