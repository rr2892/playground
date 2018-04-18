import java.util.*;
import java.io.*;

class TrieNode {
  String next, val;
  int freq;
  TrieNode[] children;
  boolean end, empty, me;

  TrieNode() {
    next = null;
    children = new TrieNode[26];
    freq = -1;
    me = false;
  }
}

public class AutoCompletion {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int n = Integer.parseInt(br.readLine());
      TrieNode root = new TrieNode();

      for (int i = 0; i < n; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        String s = st.nextToken();
        int freq = Integer.parseInt(st.nextToken());
        TrieNode node = root;

        for (int j = 0; j < s.length(); j++) {
          int index = s.charAt(j) - 'a';
          if (node.children[index] == null) {
            node.children[index] = new TrieNode();
          }

          node.empty = false;
          node = node.children[index];
        }

        node.val = s;
        node.end = true;
        node.empty = true;
        node.freq = freq;
      }

      fillNext(root);

      int q = Integer.parseInt(br.readLine());
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < q; i++) {
        String s = br.readLine();
        TrieNode node = root;

        for (int j = 0; j < s.length(); j++) {
          char c = s.charAt(j);
          if (c == '#') {
            sb.append(String.format("%s\n", node.next));
            break;
          }

          int index = c - 'a';
          if (node.children[index] == null) {
            s = (s.charAt(s.length() - 1) == '#') ? s.substring(0, s.length() - 1) : s;
            sb.append(String.format("%s\n", s));
            break;
          }

          node = node.children[index];
        }
      }

      System.out.print(sb.toString());
    } catch(IOException e) {
      System.exit(0);
    }
  }

  private static void fillNext(TrieNode node) {
    if (node.empty) {
      node.next = node.val;
      return;
    }

    int maxFreq = -1;

    for (int i = 0; i < 26; i++) {
      TrieNode child = node.children[i];
      if (child == null) continue;
      fillNext(child);

      String s = (child.me) ? child.val : child.next;
      int f = child.freq;

      if (node.next == null) {
        node.next = s;
        maxFreq = f;
        continue;
      }

      if (f > maxFreq || (f == maxFreq && s.compareTo(node.next) < 0)) {
          maxFreq = f;
          node.next = s;
      }
    }

    if (node.freq > maxFreq || (node.freq == maxFreq && node.val.compareTo(node.next) < 0)) {
      node.me = true;
    } else {
      node.freq = maxFreq;
    }
  }
}
