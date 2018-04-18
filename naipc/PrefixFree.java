import java.util.*;
import java.io.*;

class TrieNode {
  TrieNode[] children;
  char c;
  int key;
  boolean leaf;

  TrieNode(char c) {
    this.c = c;
    children = new TrieNode[26];
    key = -1;
    leaf = false;
  }
}

public class PrefixFree {

  static TrieNode root;
  static int globalKey;

  private static void addWord(String s) {
    TrieNode node = root;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      int index = c - 'a';
      if (node.children[index] == null) {
        node.children[index] = new TrieNode(c);
      }

      node = node.children[index];
    }

    node.leaf = true;
  }

  private static void inOrderTraversal(TrieNode root) {
    if (root == null) return;
    if (root.leaf) {
      root.key = globalKey;
      globalKey++;
    }

    for (int i = 0; i < 26; i++) {
      inOrderTraversal(root.children[i]);
    }
  }

  public static void main(String[] args) {
    FastReader fr = new FastReader();

    int n = fr.nextInt(), k = fr.nextInt();

    TreeSet<Integer> tree = new TreeSet<>();
    root = new TrieNode('R');
    globalKey = 0;

    // O(kN)
    for (int i = 0; i < n; i++) {
      addWord(fr.next());
      tree.add(i);
    }

    // O(N)
    inOrderTraversal(root);

    String test = fr.next();
    Queue<Integer> seq = new LinkedList<>();
    TrieNode node = root;

    for (int i = 0; i < test.length(); i++) {
      char c = test.charAt(i);
      node = node.children[(c-'a')];
      if (node.leaf) {
        seq.offer(node.key);
        node = root;
      }
    }

    int pos = 0, fact = 1, div = n - 1;
    final int P = 1000000007;

    for (int i = n - k + 1; i < n; i++) {
      fact = modMultiply(fact, i, P);
    }

    while (!seq.isEmpty()) {
      int key = seq.poll();
      int mult = tree.subSet(0, key).size();
      tree.remove(key);
      int sum = modMultiply(mult, fact, P);
      pos = modAdd(pos, sum, P);
      if (div == 0) break;
      fact /= div;
      div--;
    }

    System.out.print(String.format("%d\n", pos+1));
  }

  private static int modAdd(int a, int b, int P) {
    return (a % P + b % P) % P;
  }

  private static int modMultiply(int a, int b, int P) {
    int ans = 0;
    while (b > 0) {
      if ((b & 1) > 0) ans = (ans + a) % P;
      b >>= 1;
      a = a * 2 % P;
    }
    return ans;
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
