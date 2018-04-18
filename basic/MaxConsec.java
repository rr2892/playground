public class MaxConsec {

    public static void main(String[] args){

        int[] A = { 4, -5, 6, 6, 7, -9, 3};

        System.out.println(maxConseq(A));
    }

    public static int maxConseq(int[] A) {
      int max = Integer.MIN_VALUE;
      int sub = 0, maxNum = Integer.MIN_VALUE;

      for (int i = 0; i < A.length; i++) {
        maxNum = Math.max(maxNum, A[i]);
        if (A[i] >= 0) sub += A[i];
        else {
          max = Math.max(max, sub);
          sub = 0;
        }
      }

      if (maxNum < 0) return maxNum;
      return max;
    }


    public static void mergeSort(int[] A, int p, int r){
        if(p < r){
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q+1, r);
            merge(A, p, q, r);
        }
    }

    public static void merge(int[] A, int p, int q, int r){
        int[] temp = new int[A.length];
        for(int i = 0; i < A.length; i++)
            temp[i] = A[i];

        int i = p;
        int j = q+1;
        int k = p;

        while(i <= q && j <= r)
            if(temp[i] < temp[j])
                A[k++] = temp[i++];
            else
                A[k++] = temp[j++];

        while(i <= q)
            A[k++] = temp[i++];

        while(j <= r)
            A[k++] = temp[j++];
    }

}
