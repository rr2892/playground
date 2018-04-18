import java.math.*;
import java.io.*;
import java.util.*;

public class Robots {
    /*Idea:
     * We don't keed to keep track of which robots are where, just if there is a robot at each of the'n' locations. This allows us to reduce the problem to 2^n states. We can represent these states using a bitmask and transition to each state by either pressing the black or the white button. It will take 'n' time to transition the robots at every location to their new location. O((2^n) * n);
     */

    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] W = new int[n];
        int[] B = new int[n];
        String[] in = br.readLine().split(" ");
        for( int i = 0; i < n; i++){
            W[i] = Integer.parseInt(in[i]);
            W[i]--;
        }
        in = br.readLine().split(" ");
        for( int i = 0; i < n; i++){
            B[i] = Integer.parseInt(in[i]);
            B[i]--;
        }
        int size = (1 << n);
        int[] seen = new int[size];
        int[] rev = new int[size];
        Queue<Integer> q = new LinkedList<>();
        q.add(size - 1);
        seen[size -1] = -1;
        rev[size -1] = -1;
        int ans = -1;
        while(!q.isEmpty()){
            int mask = q.remove();
            //if one bit is on then all the robots are at the same location
            if(Integer.bitCount(mask) == 1){
                ans = mask;
                break;
            }
            int nmask = 0;
            for( int i = 0; i < n; i++){
                int l = (mask >> i) & 1;
                nmask = nmask | (l << W[i]);
            }
            if(seen[nmask] == 0){
                seen[nmask] = 1;
                rev[nmask] = mask;
                q.add(nmask);
            }
            nmask = 0;
            for(int i = 0; i < n; i++){
                int l = (mask >> i) & 1;
                nmask = nmask | (l << B[i]);
            }
            if(seen[nmask] == 0){
                seen[nmask] = 2;
                rev[nmask] = mask;
                q.add(nmask);
            }
        }
        if(ans == -1){
            System.out.println("impossible");
            System.exit(0);
        }
        Stack<Integer> st  = new Stack<>();
        while(seen[ans] != -1){
            st.push(seen[ans]);
            ans = rev[ans];
        }
        StringBuilder out = new StringBuilder();
        while(!st.isEmpty()){
            int a = st.pop();
            out.append(a == 1 ? 'w' : 'b');
        }
        System.out.println(out);

    }

}
