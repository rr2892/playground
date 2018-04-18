import java.math.*;
import java.io.*;
import java.util.*;

public class RedBlackSol {
    /*Idea:
     * keep track of which colors belong to the same set using union-find
     * We also keep track of sets of the opposite color using the array 'rev'
     * for cards that are the same union the cards( union(a,b)) and their cards that are
     * the opposite color( union(rev[a], rev[b])).
     * Always check that find(rev[a]) != find(b), which is a discrepency
     * For cards that are different union(a, rev[b]) and union(b, rev[a])
     * Always check that find(a) != find(b), which is a discrepency
     *
     * In addition we can maintain the size of each set through augmenting our union-find
     * algorithm.
     */
    static int set[];
    static int size[];
    static int rev[];
    static int color[];
    static int known[];
    static int n;
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        n = Integer.parseInt(in[0]);
        int Q = Integer.parseInt(in[1]);
        StringBuilder out = new StringBuilder();
        set = new int[n+2];
        size = new int[n+2];
        rev = new int[n+2];
        // unknown = 0, black = 1, red = 2
        color = new int[n+2];
        known = new int[3];
        known[1] = n; //The cards we know are black
        known[2] = n+1; // the cards we know are red

        // init union-find
        // every does not have a card of an opposite color
        // every set is of size 1
        for( int i = 0; i < n+2; i++){
            set[i] = i;
            rev[i] = -1;
            size[i] = 1;
        }
        size[n] = 0;
        size[n+1] = 0;
        color[n] = 1;
        color[n+1] = 2;
        boolean all = false;
        for( int q = 0; q < Q; q++){
            in = br.readLine().split(" ");
            int a;
            int b;
            int c;

            /* we must always
             * 1. Check for a discrepency
             * 2. if there is no discrepency combine the relevent sets
             * 3. color the sets and opposite sets when possible
             */
            switch(in[0].charAt(0)){
                case 'r':
                    a = Integer.parseInt(in[1])-1;
                    a = find(a);
                    if(color[a] == 1){
                        out.append("Q" + (q+1) + ": ?\n");
                    }else if(color[a] == 0){
                        a = find(a);
                        color[a] = 2;
                        if(rev[a] != -1) color[rev[a]] = 1;
                        comb(a, known[2]);
                    }
                    break;

                case 'b':
                    a = Integer.parseInt(in[1]) -1;
                    a = find(a);

                    if(color[a] == 2){
                        out.append("Q" + (q+1) + ": ?\n");
                    }else if( color[a] == 0){
                        a = find(a);
                        color[a] = 1;
                        if(rev[a] != -1) color[rev[a]] = 2;
                        comb(a, known[1]);
                    }
                    break;

                case 's':
                    a = Integer.parseInt(in[1]) -1;
                    b = Integer.parseInt(in[2]) -1;
                    a = find(a);
                    b = find(b);
                    if(contr(a,b)){
                        out.append("Q" + (q+1) + ": ?\n");
                        break;
                    }
                    comb(a,b);
                    break;
                case 'd':
                    a = Integer.parseInt(in[1]) -1;
                    b = Integer.parseInt(in[2]) -1;
                    a = find(a);
                    b = find(b);
                    if(a == b || contr(rev[a],b) || contr(rev[b],a)){
                        out.append("Q" + (q+1) + ": ?\n");
                        break;
                    }
                    if(rev[a] == -1){
                        rev[a] = b;
                        if(color[a] != 0 && color[b] == 0){
                            color[b] = (3 - color[a] %3);
                            comb(b, known[color[b]]);
                        }
                    }else
                        comb(rev[a],b);
                    if(rev[b] == -1){
                        rev[b] = a;
                        if(color[b] != 0 && color[a] == 0){
                            color[a] = (3 - color[b] %3);
                            comb(a, known[color[a]]);
                        }
                    }else
                        comb(rev[b], a);

                    break;
            }
            //Check if we know the colors of every card
            if(size[known[1]] + size[known[2]] == n){
                all = true;
                out.append("Q" + (q+1) + ": I know\n");
                break;
            }
        }
        StringBuilder line = new StringBuilder();
        for( int i = 0; i < n; i++){
            int a = find(set[i]);
            line.append( color[a] == 1 ? 'b' : 'r');
        }
        if(all)
            out.append(line).append('\n');
        else
            out.append("I am not sure\n");
        System.out.print(out.toString());
    }

    //check for contradiction
    public static boolean contr(int a, int b){
        if(a == -1 || b == -1)return false;
        a = find(a);
        b = find(b);
        //if we know the colors of both check if they are opposite colors
        if(color[a] != 0 && color[b] != 0 && color[a] != color[b])
            return true;
        int c = rev[a];
        int d = rev[b];
        if(c == -1 || d == -1)return false;
        c = find(c);
        d = find(d);
        // check if they are part of opposite sets when they are not colored
        if(a == d)return true;

        return false;
    }
    //  union the sets together do union(a, b), union(rev[a], rev[b]) if
    //  they exist and color them if possible.
    public static void comb(int a, int b){
        // find roots of all sets
        a = find(a);
        b = find(b);
        int c = rev[a];
        if(c != -1)
            c = find(c);
        rev[a] = c;
        int d = rev[b];
        if(d != -1)
            d = find(d);
        rev[b] = d;

        union(a,b);

        // set color to head of set if either card has a color
        color[find(a)] = Math.max(color[a], color[b]);
        int e = find(a);
        //add to color set if has color
        if(color[e] != 0){
            union(e, known[color[e]]);
            known[color[e]] = find(e);
        }

        //Set colors to opposite color sets
        if(c != -1){
            rev[c] = e;
            color[c] = (3 - color[e]) %3;
            if(color[c] != 0){
                union(c, known[color[c]]);
                known[color[c]] = find(c);
            }
        }
        if(d != -1){
            rev[d] = e;
            color[d] = (3 - color[e]) %3;
            if(color[d] != 0){
                union(d, known[color[d]]);
                known[color[d]] = find(d);
            }
        }
        // combine the sets of opposite colors if they exist
        int f = c;
        if( f== -1) f = d;
        if(c != -1 && d != -1){
            union(c,d);
            f = find(c);
        }
        if(f != -1){
            rev[a] = f;
            rev[b] = f;
        }

    }
    public static void union(int s1, int s2) {
        int a = find(s1);
        int b = find(s2);
        if(a == b)
            return;

        int c = Math.min(a,b);
        int d = Math.max(a,b);
        set[c] = d;
        // When we union two sets add the size of the second set
        // to the root set.
        size[d] += size[c];

    }
    public static int find(int index) {
        if (set[index] == index)return index;
        return set[index] = find(set[index]);
    }
}
