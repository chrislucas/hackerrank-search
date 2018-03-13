import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/icecream-parlor/problem
 * DONE
 * */

public class ICP {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    private static int bsearch(long [] array, int l, long target) {
        int r = array.length-1;
        while (l<=r) {
            int mid = (r-l)/2+l;
            if( array[mid] <  target) {
                l = mid + 1;
            }
            else if(array[mid] > target) {
                r = mid - 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    private static void solver() {
        try {
            int cases = Integer.parseInt(reader.readLine().trim());
            while (cases-- > 0) {
                long budget = Long.parseLong(reader.readLine().trim());
                int size = Integer.parseInt(reader.readLine().trim());
                long [] arrayo = new long[size];
                long [] arrayc = new long[size];
                StringTokenizer tk = new StringTokenizer(reader.readLine().trim(), " ");
                //HashMap<Integer, Integer> map = new HashMap<>();
                for (int i = 0; tk.hasMoreTokens() ; i++) {
                    arrayo[i] = Long.parseLong(tk.nextToken());
                    arrayc[i] = arrayo[i];
                    //map.put(arrayc[i], i);
                }
                Arrays.sort(arrayo);
                int idx = -1, k=0;
                while (k < size && idx == -1) {
                    if(arrayo[k] < budget)
                        idx = bsearch(arrayo, k+1,budget-arrayo[k++]);
                }
                int p = 0, q = 0;
                for (int i = 0; i < arrayc.length ; i++) {
                    if(arrayo[k] == arrayc[i]) {
                        p = i+1;
                        break;
                    }
                }
                for (int i = 0; i <arrayc.length ; i++) {
                    if(arrayo[idx] == arrayc[i]) {
                        q = i+1;
                        break;
                    }
                }
                if(p > q)
                    writer.printf("%d %d\n", q, p);
                else if (p == q)
                    writer.printf("%d %d\n", p, q+1);
                else
                    writer.printf("%d %d\n", p, q);

            }

        } catch (IOException ioex) { }
    }

    public static void main(String[] args) {
        solver();
    }
}
