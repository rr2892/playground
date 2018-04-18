import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class AutoCompleteSol {
  // trie root
  static Node root;

  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt();
    root = new Node(null, "", 0);
    for (int i = 0; i < n; i++) {
      String str = fr.next();
      int val = fr.nextInt();
      addWord(str, val);
    }

    int q = fr.nextInt();
    StringBuilder sbb = new StringBuilder();

    for (int i = 0; i < q; i++) {
      String str = fr.next();
      Node node = root;
      StringBuilder sb = new StringBuilder();

      for (int j = 0; j < str.length(); j++) {
        char c = str.charAt(j);
        if (c == '#') {
          String str2 = sb.toString();
          if (node != null) {
            sb = new StringBuilder(node.word);
            // navigate down the trie to next auto completed word
            for (int k = str2.length(); k < sb.length(); k++) {
              node = node.get(sb.charAt(k));
            }
          }
          // if node == null, the current prefix doesn't autocomplete to anything
        } else {
          sb.append(c);
          if(node != null) node = node.get(c);
        }
      }

      sbb.append(sb.toString());
      sbb.append("\n");
    }

    System.out.print(sbb.toString());
  }

  static void addWord(String str, int val) {
    Node node = root;
    for (int i = 0; i < str.length()-1; i++) {
      char c = str.charAt(i);
      if(node.get(c) == null){
        node.put(c, new Node(c, str, val));
        node = node.get(c);
      } else {
        node = node.get(c);
        if(node.val < val || (node.val == val && node.word.compareTo(str) > 0)){
          node.word = str;
          node.val = val;
        }
      }
    }
  }
}

class Node {
  Character c;
  HashMap<Character, Node> outEdge;
  String word;
  int val;

  public Node(Character c, String word, int val){
    this.outEdge = new HashMap<>();
    this.c = c;
    this.word = word;
    this.val = val;
  }
  // Helper functions for accessing the HashMap
  public Node get(char c){
    return outEdge.get(c);
  }

  public void put(char c, Node node){
    this.outEdge.put(c, node);
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
