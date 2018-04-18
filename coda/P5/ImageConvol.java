import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class ImageConvol {
  public static void main(String[] args) {
    FastReader fr = new FastReader();
    // Read in 'image' into an array of BitSets
    int n = fr.nextInt();
    int m = fr.nextInt();
    BitSet[] image = new BitSet[n];
    for(int i = 0; i < n; i++){
      BitSet bs = new BitSet(m);
      String str = fr.next();
      for(int j = 0; j < m; j++) {
        if (str.charAt(j) == '#') {
          bs.set(j);
        }
      }

      image[i] = bs;
    }
    // Read in 'pattern' into an array of BitSets
    // Read in ? into an array of BitSets called 'wildcard'
    int n2 = fr.nextInt();
    int m2 = fr.nextInt();
    BitSet[] pattern = new BitSet[n2];
    BitSet[] wildcard = new BitSet[n2];
    for(int i = 0; i < n2; i++) {
      BitSet bs = new BitSet(m2);
      BitSet wc = new BitSet(m2);
      String str = fr.next();
      for (int j = 0; j < m2; j++) {
        if (str.charAt(j) == '#') {
          bs.set(j);
        } else if(str.charAt(j) == '?') {
          wc.set(j);
        }
      }

      pattern[i] = bs;
      wildcard[i] = wc;
    }

    int matches = 0;
    BitSet match = new BitSet(m2);
    match.set(0, m2);
    // For every upper left corner (i, j), check if an n2xm2 sized rectangle matches the pattern and wildcard.
    for (int i = 0; i < n-n2+1; i++) {
      for (int j = 0; j < m-m2+1; j++) {
        boolean b = true;
        for (int k = 0; k < n2; k++) {
          BitSet bs = image[i+k].get(j, j+m2);
          bs.xor(pattern[k]);
          bs.flip(0, m2);
          bs.or(wildcard[k]);
          if (!bs.equals(match)) b = false;
        }
        if(b) matches++;
      }
    }
    System.out.println(matches);
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
