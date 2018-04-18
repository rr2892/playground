import java.util.*;
import java.io.*;

public class XorQuads {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    int n = fr.nextInt();
    List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      list1.add(fr.nextInt());
    }

    for (int i = 0; i < n; i++) {
      list2.add(fr.nextInt());
    }

    Trie t1 = new Trie(list1);
    int count = 0;

    for (int i = 0; i < list2.size(); i++) {
      for (int j = i + 1; j < list2.size(); j++) {
        int num = list2.get(i) ^ list2.get(j);
        count += t1.checkXor(num);
      }
    }

    System.out.print(String.format("%d\n", count));

  }
}

class Trie {
  Node root;

  Trie(List<Integer> list) {
    root = new Node();
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        int n = list.get(i) ^ list.get(j);
        add(n);
      }
    }
  }

  void add(int n) {
    Node node = root;
    while (n > 0) {
      int index = n & 1;
      if (node.children[index] == null) {
        node.children[index] = new Node();
      }

      node = node.children[index];
      n = n >> 1;
    }

    node.freq++;
  }

  int checkXor(int n) {
    Node node = root;
    while (n > 0) {
      int index = n & 1;
      if (node.children[index] == null) {
        return 0;
      }

      node = node.children[index];
      n = n >> 1;
    }

    return node.freq;
  }
}

class Node {
  int freq;
  Node[] children;

  Node() {
    children = new Node[2];
    freq = 0;
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
