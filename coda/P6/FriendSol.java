import java.math.*;
import java.io.*;
import java.util.*;
/*Idea:
 *
 * This is an augmentation of the union-find algorithm,
 * First we maintain two sets, one each for the youngest and
 * oldest person. The head of each of these sets will be the
 * youngest or oldest person in the set. To do this we simply
 * need to change how we decide the head of the set as it was
 * defined in the problem statement.
 */

public class FriendSol {
    static String[] names;
    static int[] ages;
    public static void main(String[] args ) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        names = new String[n];
        ages = new int[n];
        setMax = new int[n];
        setMin = new int[n];
        // map is used to remember the names we associate for each index of the set
        HashMap<String, Integer> map = new HashMap<>();
        //init  union-find
        for( int i = 0; i < n; i++){
            setMax[i] = i;
            setMin[i] = i;
        }
        for( int i = 0; i < n; i++){
            String[] in = br.readLine().split(" ");
            names[i] = in[0];
            ages[i] = Integer.parseInt(in[1]);
            map.put(in[0], i);
        }

        int m = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();
        for( int i = 0; i < m; i++){
            String[] in = br.readLine().split(" ");
            int a = map.get(in[0]);
            int b = map.get(in[1]);
            unionMax(a,b);
            unionMin(a,b);
            out.append(names[setMin[find(a, setMin)]]).append(' ').append(names[setMax[find(b, setMax)]]).append('\n');
        }
        System.out.print(out.toString());

    }

    static int setMax[];
    static int setMin[];
    /* The special union algorithm to find the oldest person in the set.
     * if they are not the same age choose the older person, otherwise
     * choose the lexographically smallest name.
     */
    public static void unionMax(int s1, int s2) {
        int a = find(s1, setMax);
        int b = find(s2, setMax);
        int c = -1;
        if(ages[a] - ages[b] != 0) c = ages[a] - ages[b] > 0 ? a : b;
        else c = names[a].compareTo(names[b]) > 0 ? b : a;
        int d = c == a ? b : a;
        setMax[d] = c;
    }
    /* The special union algorithm to find the youngest person in the set.
     * if they are not the same age choose the younger person, otherwise
     * choose the lexographically smallest name.
     */
    public static void unionMin(int s1, int s2) {
        int a = find(s1, setMin);
        int b = find(s2, setMin);
        int c = -1;
        if(ages[a] - ages[b] != 0) c = ages[a] - ages[b] > 0 ? b : a;
        else c = names[a].compareTo(names[b]) > 0 ? b : a;
        int d = c == a ? b : a;
        setMin[d] = c;
    }

    public static int find(int index, int[] set) {
        if (set[index] == index)return index;
        return set[index] = find(set[index],set);
    }

}
